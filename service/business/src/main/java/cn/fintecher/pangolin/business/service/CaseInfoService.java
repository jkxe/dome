package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.exception.GeneralException;
import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.model.request.DivisionExceptionRequest;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.flow.CaseRoamService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.strategy.CaseStrategy;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import cn.fintecher.pangolin.model.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static cn.fintecher.pangolin.entity.QCaseInfo.caseInfo;

/**
 * @author : xiaqun
 * @Description : 催收业务
 * @Date : 16:45 2017/7/17
 */

@Service("caseInfoService")
public class CaseInfoService {


    final Logger log = LoggerFactory.getLogger(CaseInfoService.class);


    @Inject
    CaseInfoRepository caseInfoRepository;

    @Inject
    CaseAssistRepository caseAssistRepository;

    @Inject
    CaseAssistApplyRepository caseAssistApplyRepository;

    @Inject
    CaseTurnRecordRepository caseTurnRecordRepository;

    @Inject
    CasePayApplyRepository casePayApplyRepository;

    @Inject
    CaseFollowupRecordRepository caseFollowupRecordRepository;

    @Inject
    SysParamRepository sysParamRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Inject
    CasePayFileRepository casePayFileRepository;

    @Inject
    PersonalContactRepository personalContactRepository;

    @Autowired
    CaseInfoExceptionService caseInfoExceptionService;

    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    CaseRepairRepository caseRepairRepository;

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    PersonalAddressRepository personalAddressRepository;

    @Inject
    ReminderService reminderService;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    CaseAdvanceTurnApplayRepository caseAdvanceTurnApplayRepository;

    @Inject
    CaseFlowupFileRepository caseFlowupFileRepository;

    @Inject
    CaseInfoReturnRepository caseInfoReturnRepository;

    @Inject
    CaseInfoRemarkRepository caseInfoRemarkRepository;

    @Inject
    RunCaseStrategyService runCaseStrategyService;

    @Inject
    OutsourcePoolRepository outsourcePoolRepository;

    @Inject
    CaseInfoVerificationRepository caseInfoVerificationRepository;

    @Inject
    CaseInfoJudicialRepository caseInfoJudicialRepository;

    @Inject
    RestTemplate restTemplate;

    @Inject
    CaseRepairRecordRepository caseRepairRecordRepository;

    @Inject
    AccMapService accMapService;
    @Inject
    EntityManager entityManager;

    @Autowired
    FlowTaskRepository flowTaskRepository;

    @Autowired
    FlowApprovalRepository flowApprovalRepository;
    @Autowired
    private CaseRoamService caseRoamService;


    /**
     * @Description 重新分配
     */
    public void reDistribution(ReDistributionParams reDistributionParams, User tokenUser) {
        if (reDistributionParams.getUserName().contains("administrator")) {
            throw new RuntimeException("不能分配给超级管理员");
        }
        User user = userRepository.findByUserName(reDistributionParams.getUserName());
        if (Objects.isNull(user)) {
            throw new RuntimeException("查不到该用户");
        }
        if (Objects.equals(user.getStatus(), 1)) {
            throw new RuntimeException("该用户已停用");
        }
        if (Objects.equals(user.getType(), User.Type.SYNTHESIZE.getValue())) {
            throw new RuntimeException("只能给电催或者外访人员分案");
        }
        CaseInfo caseInfo = caseInfoRepository.findOne(reDistributionParams.getCaseId());
        QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
        if (Objects.equals(reDistributionParams.getIsAssist(), false)) { //不是协催案件
            if (Objects.equals(user.getType(), User.Type.TEL.getValue())) { //分配给15-电催
                setAttribute(caseInfo, user, tokenUser);
                //查询是否有协催案件
                CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                        and(qCaseAssist.assistStatus.in(CaseInfo.AssistStatus.ASSIST_COLLECTING.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue())));
                if (Objects.nonNull(caseAssist)) { //如果有协催案件，则同步更换当前催收员
                    caseAssist.setCurrentCollector(user);
                    caseAssistRepository.save(caseAssist);
                }
            } else if (Objects.equals(user.getType(), User.Type.VISIT.getValue())) { //分配给16-外访
                if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标识
                    if (Objects.equals(caseInfo.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue())) { //有协催申请
                        CaseAssistApply caseAssistApply = getCaseAssistApply(reDistributionParams.getCaseId(), tokenUser, "流转强制拒绝", CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                        caseAssistApplyRepository.saveAndFlush(caseAssistApply);
                    } else { //有协催案件
                        CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(reDistributionParams.getCaseId()).
                                and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                        if (Objects.isNull(caseAssist)) {
                            throw new RuntimeException("协催案件未找到");
                        }
                        caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
                        caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                        caseAssist.setOperator(tokenUser); //操作员
                        caseAssistRepository.saveAndFlush(caseAssist);

                        //协催结束新增一条流转记录
                        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                        caseTurnRecord.setId(null); //主键置空
                        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                        caseTurnRecord.setDepartId(caseInfo.getCurrentCollector().getDepartment().getId()); //部门ID
                        caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                        caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接受人ID
                        if (Objects.nonNull(caseInfo.getLatelyCollector())) {
                            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
                        }
                        caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
                        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                        caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员
                        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                        caseTurnRecordRepository.saveAndFlush(caseTurnRecord);
                    }
                }
                setAttribute(caseInfo, user, tokenUser);

                //同步更新原案件协催员，协催方式，协催标识，协催状态
                caseInfo.setAssistCollector(null); //协催员置空
                caseInfo.setAssistWay(null); //协催方式置空
                caseInfo.setAssistFlag(0); //协催标识 0-否
                caseInfo.setAssistStatus(null); //协催状态置空
            }
        } else { //是协催案件
            if (!Objects.equals(user.getType(), User.Type.VISIT.getValue())) {
                throw new RuntimeException("协催案件不能分配给外访以外人员");
            }
            CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(reDistributionParams.getCaseId()).
                    and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
            if (Objects.isNull(caseAssist)) {
                throw new RuntimeException("协催案件未找到");
            }
            caseAssist.setLatelyCollector(caseAssist.getAssistCollector()); //上一个协催员
            caseAssist.setAssistCollector(user); //协催员
            caseAssist.setDepartId(user.getDepartment().getId()); //部门
            caseAssist.setOperator(tokenUser); //操作员
            caseAssist.setCaseFlowinTime(ZWDateUtil.getNowDateTime()); //流入时间
            caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseAssist.setHoldDays(0); //持案天数归0
            caseAssist.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());
            caseAssist.setHasLeaveDays(0);
            caseAssist.setMarkId(CaseInfo.Color.NO_COLOR.getValue());//打标
            caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue());//协催待催收
            caseAssist.setCaseFlowinTime(new Date());
            caseAssistRepository.saveAndFlush(caseAssist);

            //同步更新原案件协催员
            caseInfo.setAssistCollector(user); //协催员
        }
        caseInfoRepository.saveAndFlush(caseInfo);

        //分配完成新增流转记录
        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
        caseTurnRecord.setId(null); //主键置空
        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
        caseTurnRecord.setDepartId(user.getDepartment().getId()); //部门ID
        caseTurnRecord.setReceiveUserRealName(user.getRealName()); //接受人名称
        caseTurnRecord.setReceiveDeptName(user.getDepartment().getName()); //接收部门名称
        caseTurnRecord.setReceiveUserId(user.getId()); //接受人ID
        if (Objects.nonNull(caseInfo.getLatelyCollector())) {
            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
        }
        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
        caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员用户名
        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseTurnRecordRepository.saveAndFlush(caseTurnRecord);
    }

//    /**
//     * @Description 客户信息
//     */
//    public Personal getCustInfo(String caseId) {
//        CaseInfo caseInfo = caseInfoRepository.findOne(caseId); //获得案件
//        if (Objects.isNull(caseInfo)) {
//            throw new RuntimeException("该案件未找到");
//        }
//        //获取客户基本信息
//        return caseInfo.getPersonalInfo();
//    }

    /**
     * @Description 申请还款操作
     */
    public void doPay(PayApplyParams payApplyParams, User tokenUser) {
        CaseInfo caseInfo = caseInfoRepository.findOne(payApplyParams.getCaseId());
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
        if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue())) {
            throw new RuntimeException("该案件已结案!");
        }
        if (Objects.equals(caseInfo.getHandUpFlag(), CaseInfo.HandUpFlag.YES_HANG.getValue())) {
            throw new RuntimeException("挂起案件不允许做还款操作");
        }
        if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.OVER_PAYING.getValue())
                || Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.EARLY_PAYING.getValue())
                || Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.REPAID.getValue())) {
            throw new RuntimeException("该案件正在还款中或已还款，不允许再次还款");
        }
        if (BigDecimal.ZERO.compareTo(payApplyParams.getPayAmt()) > 0) {
            throw new RuntimeException("还款金额不能小于0");
        }
        if (Objects.equals(payApplyParams.getDerateFlag(), 1)) {
            if (BigDecimal.ZERO.compareTo(payApplyParams.getDerateFee()) > 0) {
                throw new RuntimeException("减免金额不能小于0");
            }
        }
        //更新案件状态
        if (Objects.equals(payApplyParams.getPayaType(), CasePayApply.PayType.DERATEOVERDUE.getValue())
                || Objects.equals(payApplyParams.getPayaType(), CasePayApply.PayType.ALLOVERDUE.getValue())
                || Objects.equals(payApplyParams.getPayaType(), CasePayApply.PayType.PARTOVERDUE.getValue())) { //还款类型为减免逾期还款、全额逾期还款、部分逾期还款
//            if (Objects.isNull(caseInfo.getOverdueAmount())
//                    || Objects.equals(BigDecimal.ZERO.compareTo(caseInfo.getOverdueAmount()), 0)) { //如果逾期总金额为空或为0
//                throw new RuntimeException("逾期本期应还金额为0，不允许还款");
//            }
            caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.OVER_PAYING.getValue()); //催收状态 22-逾期还款中
        } else { //还款类型为减免提前结清、全额提前结清、部分提前结清
//            if (Objects.isNull(caseInfo.getEarlySettleAmt())
//                    || Objects.equals(BigDecimal.ZERO.compareTo(caseInfo.getEarlySettleAmt()), 0)) { //如果提前结清总金额为空或为0
//                throw new RuntimeException("提前结清本期应还金额为0，不允许还款");
//            }
            caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.EARLY_PAYING.getValue()); //催收状态 23-提前结清中
        }
        caseInfo.setOperator(tokenUser); //操作员
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseInfoRepository.saveAndFlush(caseInfo);

        //新增还款审批记录
        CasePayApply casePayApply = new CasePayApply();
        casePayApply.setCaseId(caseInfo.getId()); //案件ID
        casePayApply.setCaseNumber(caseInfo.getCaseNumber()); //案件编号
        casePayApply.setPersonalName(caseInfo.getPersonalInfo().getName()); //客户姓名
        casePayApply.setPersonalId(caseInfo.getPersonalInfo().getId()); //客户信息ID
        casePayApply.setCollectionType(caseInfo.getCollectionType()); //催收类型
        casePayApply.setDeptCode(tokenUser.getDepartment().getCode()); //部门Code 修改 胡艳敏(记录还款人的部门code和ID)
        casePayApply.setDepartId(tokenUser.getDepartment().getId()); //部门Id 修改 胡艳敏
        casePayApply.setApplyPayAmt(payApplyParams.getPayAmt()); //申请还款金额
        casePayApply.setApplyDerateAmt(Objects.isNull(payApplyParams.getDerateFee()) ? new BigDecimal(0) : payApplyParams.getDerateFee()); //申请减免金额
        casePayApply.setPayType(payApplyParams.getPayaType()); //还款类型
        casePayApply.setPayWay(payApplyParams.getPayWay()); //还款方式
        casePayApply.setDerateFlag(payApplyParams.getDerateFlag()); //减免标识
        casePayApply.setApproveDerateRemark(payApplyParams.getDerateDescripton()); //减免费用备注
        if (Objects.equals(payApplyParams.getDerateFlag(), 1)) { //减免标识 1-有减免
            casePayApply.setApproveStatus(CasePayApply.ApproveStatus.DERATE_TO_AUDIT.getValue()); //审批状态 55-减免待审核
        } else { //减免标识 0-没有减免
            casePayApply.setApproveStatus(CasePayApply.ApproveStatus.PAY_TO_AUDIT.getValue()); //审批状态 57-还款待审核
        }
        casePayApply.setPayMemo(payApplyParams.getPayDescripton()); //还款说明
        casePayApply.setApplyUserName(tokenUser.getUserName()); //申请人
        casePayApply.setApplyRealName(tokenUser.getRealName()); //申请人姓名
        casePayApply.setApplyDeptName(tokenUser.getDepartment().getName()); //申请人部门名称
        casePayApply.setApplyDate(ZWDateUtil.getNowDateTime()); //申请时间
        casePayApply.setCompanyCode(caseInfo.getCompanyCode()); //公司code码
        casePayApply.setPersonalPhone(caseInfo.getPersonalInfo().getMobileNo()); //客户手机号
        casePayApply.setPrincipalId(caseInfo.getPrincipalId().getId()); //委托方ID
        casePayApply.setPrincipalName(caseInfo.getPrincipalId().getName()); //委托方名称
        casePayApply.setBatchNumber(caseInfo.getBatchNumber()); //批次号
        casePayApply.setCaseAmt(caseInfo.getOverdueAmount()); //案件金额
        casePayApplyRepository.saveAndFlush(casePayApply);
        //保存还款凭证文件id到case_pay_file
        List<String> fileIds = payApplyParams.getFileIds();
        if (Objects.nonNull(fileIds) && fileIds.size() > 0) {
            for (String id : fileIds) {
                String fileId = id.trim();
                CasePayFile casePayFile = new CasePayFile();
                casePayFile.setFileid(fileId);
                casePayFile.setCaseNumber(caseInfo.getCaseNumber());
                casePayFile.setOperatorTime(ZWDateUtil.getNowDateTime());
                casePayFile.setOperator(tokenUser.getUserName());
                casePayFile.setOperatorName(tokenUser.getRealName());
                casePayFile.setPayId(casePayApply.getId());
                casePayFile.setCaseId(caseInfo.getId());
                casePayFileRepository.saveAndFlush(casePayFile);
            }
        }

        //消息提醒
        List<User> userList = userService.getAllHigherLevelManagerByUser(tokenUser.getId());
        List<String> managerIdList = new ArrayList<>();
        for (User user : userList) {
            managerIdList.add(user.getId());
        }
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        sendReminderMessage.setTitle("客户 [" + caseInfo.getPersonalInfo().getName() + "] 还款申请");
        sendReminderMessage.setContent(payApplyParams.getPayDescripton());
        sendReminderMessage.setType(ReminderType.REPAYMENT);
        sendReminderMessage.setCcUserIds(managerIdList.toArray(new String[managerIdList.size()]));
        reminderService.sendReminder(sendReminderMessage);
    }

    /**
     * @Description 还款撤回
     */
    public void payWithdraw(String payApplyId, User tokenUser) {
        CasePayApply casePayApply = casePayApplyRepository.findOne(payApplyId);
        if (Objects.isNull(casePayApply)) {
            throw new RuntimeException("该还款审批未找到");
        }
        if (Objects.equals(casePayApply.getApproveStatus(), CasePayApply.ApproveStatus.REVOKE.getValue())) {
            throw new RuntimeException("还款已撤回，不能再次撤回");
        }

        //修改原案件催收状态
        CaseInfo caseInfo = caseInfoRepository.findOne(casePayApply.getCaseId());
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
        if (Objects.equals(caseInfo.getHandUpFlag(), 1)) {
            throw new RuntimeException("挂起案件不允许操作");
        }
        if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.REPAID.getValue()) //已还款
                || Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue()) //已结案
                || Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.PART_REPAID.getValue()) //部分已还款
                || Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OUT.getValue())) { //已委外
            throw new RuntimeException("已还款或已结案案件不允许操作");
        }
        if (Objects.equals(casePayApply.getDerateFlag(), 1)) { //有减免标识
            if (!Objects.equals(casePayApply.getApproveStatus(), CasePayApply.ApproveStatus.DERATE_TO_AUDIT.getValue())) { //减免待审核
                throw new RuntimeException("非待审核状态的还款申请不能撤回");
            }
        } else { //没有减免标识
            if (!Objects.equals(casePayApply.getApproveStatus(), CasePayApply.ApproveStatus.PAY_TO_AUDIT.getValue())) { //还款待审核
                throw new RuntimeException("非待审核状态的还款申请不能撤回");
            }
        }
        casePayApply.setApproveStatus(CasePayApply.ApproveStatus.REVOKE.getValue()); //还款审批状态 54-撤回
        casePayApply.setOperatorUserName(tokenUser.getUserName()); //操作人用户名
        casePayApply.setOperatorRealName(tokenUser.getRealName()); //操作人名称
        casePayApply.setOperatorDate(ZWDateUtil.getNowDateTime()); //操作时间
        casePayApplyRepository.saveAndFlush(casePayApply);

        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.COLLECTIONING.getValue()); //案件催收状态 21-催收中
        caseInfoRepository.saveAndFlush(caseInfo);
    }

    /**
     * @Description 结案
     */
    public void endCase(EndCaseParams endCaseParams, User tokenUser) {
        CaseInfo caseInfo = caseInfoRepository.findOne(endCaseParams.getCaseId());
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
        if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue())) {
            throw new RuntimeException("该案件已结案");
        }
        caseInfo.setOperator(tokenUser); //操作人
        caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件打标为无色
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseInfo.setEndRemark(endCaseParams.getEndRemark()); //结案说明
        caseInfo.setEndType(endCaseParams.getEndType()); //结案方式
        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.CASE_OVER.getValue()); //催收状态 24-已结案

        QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
        if (Objects.equals(endCaseParams.getIsAssist(), false)) { //不是协催案件
            if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标识
                if (Objects.equals(caseInfo.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue())) { //有协催申请
                    CaseAssistApply caseAssistApply = getCaseAssistApply(endCaseParams.getCaseId(), tokenUser, endCaseParams.getEndRemark(), CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                    caseAssistApplyRepository.saveAndFlush(caseAssistApply);
                } else { //有协催案件
                    CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(endCaseParams.getCaseId()).
                            and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                    if (Objects.isNull(caseAssist)) {
                        throw new RuntimeException("协催案件未找到");
                    }
                    caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
                    caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                    caseAssist.setOperator(tokenUser); //操作员
                    caseAssistRepository.saveAndFlush(caseAssist);
                }
            }
            caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
            caseInfoRepository.saveAndFlush(caseInfo);
//            if (Objects.equals(endCaseParams.getEndType(), CaseInfo.EndType.CLOSE_CASE.getValue())) {
//                caseInfo.setCasePoolType(CaseInfo.CasePoolType.DESTORY.getValue());
//                CaseInfoVerification verification = new CaseInfoVerification();
//                verification.setCaseInfo(caseInfo);
//                verification.setOperator(tokenUser.getRealName());
//                verification.setOperatorTime(ZWDateUtil.getNowDateTime());
//                verification.setCompanyCode(caseInfo.getCompanyCode());
//                // 核销说明
//                verification.setState(endCaseParams.getEndRemark());
//                verification.setPackingStatus(CaseInfoVerification.PackingStatus.NO_PACKED.getValue());
//                caseInfoVerificationRepository.save(verification);
//            } else
            if (Objects.equals(endCaseParams.getEndType(), CaseInfo.EndType.JUDGMENT_CLOSED.getValue())) {
                caseInfo.setEndType(CaseInfo.EndType.JUDGMENT_CLOSED.getValue());
                caseInfo.setCasePoolType(CaseInfo.CasePoolType.JUDICIAL.getValue());
                CaseInfoJudicial judicial = new CaseInfoJudicial();
                judicial.setCaseInfo(caseInfo);
                judicial.setOperatorRealName(tokenUser.getRealName());
                judicial.setCompanyCode(caseInfo.getCompanyCode());
                judicial.setOperatorTime(ZWDateUtil.getNowDateTime());
                judicial.setOperatorUserName(tokenUser.getUserName());
                judicial.setState(endCaseParams.getEndRemark());
                caseInfoJudicialRepository.save(judicial);
            }
        } else { //是协催案件
            CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(endCaseParams.getCaseId()).
                    and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
            if (Objects.isNull(caseAssist)) {
                throw new RuntimeException("协催案件未找到");
            }
            caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
            caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseAssist.setOperator(tokenUser); //操作员
            caseAssistRepository.saveAndFlush(caseAssist);
            if (Objects.equals(caseInfo.getAssistWay(), CaseAssist.AssistWay.ONCE_ASSIST.getValue())) { //单次协催
                //同步更新原案件状态
                caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
                caseInfo.setAssistCollector(null); //协催员置空
                caseInfo.setAssistWay(null); //协催方式置空
                caseInfo.setAssistFlag(0); //协催标识 0-否
                caseInfoRepository.saveAndFlush(caseInfo);

                //同时新增一条流转记录
                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                caseTurnRecord.setId(null); //主键置空
                caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                caseTurnRecord.setDepartId(caseInfo.getCurrentCollector().getDepartment().getId()); //部门ID
                caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
                caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接受人ID
                caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
                caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员用户名
                caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                caseTurnRecordRepository.saveAndFlush(caseTurnRecord);
            } else { //全程协催，原案件催收状态为已结案
                //同步更新原案件状态
                caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
                caseInfoRepository.saveAndFlush(caseInfo);
            }
//            if (Objects.equals(endCaseParams.getEndType(), CaseInfo.EndType.CLOSE_CASE.getValue())) {
//                caseInfo.setCasePoolType(CaseInfo.CasePoolType.DESTORY.getValue());
//                CaseInfoVerification verification = new CaseInfoVerification();
//                verification.setCaseInfo(caseInfo);
//                verification.setOperator(tokenUser.getRealName());
//                verification.setOperatorTime(ZWDateUtil.getNowDateTime());
//                verification.setCompanyCode(caseInfo.getCompanyCode());
//                // 核销说明
//                verification.setState(endCaseParams.getEndRemark());
//                verification.setPackingStatus(CaseInfoVerification.PackingStatus.NO_PACKED.getValue());
//                caseInfoVerificationRepository.save(verification);
//            }
            if (Objects.equals(endCaseParams.getEndType(), CaseInfo.EndType.JUDGMENT_CLOSED.getValue())) {
                caseInfo.setEndType(CaseInfo.EndType.JUDGMENT_CLOSED.getValue());
                caseInfo.setCasePoolType(CaseInfo.CasePoolType.JUDICIAL.getValue());
                CaseInfoJudicial judicial = new CaseInfoJudicial();
                judicial.setCaseInfo(caseInfo);
                judicial.setOperatorRealName(tokenUser.getRealName());
                judicial.setCompanyCode(caseInfo.getCompanyCode());
                judicial.setOperatorTime(ZWDateUtil.getNowDateTime());
                judicial.setOperatorUserName(tokenUser.getUserName());
                judicial.setState(endCaseParams.getEndRemark());
                caseInfoJudicialRepository.save(judicial);
            }
        }
    }

    /**
     * @Description 添加跟进记录
     */
    public CaseFollowupRecord saveFollowupRecord(CaseFollowupParams caseFollowupParams, User tokenUser) throws ParseException {
//        CaseInfo caseInfo = caseInfoRepository.findOne(caseFollowupParams.getCaseId()); //获取案件信息
        if (Objects.isNull(caseFollowupParams.getCaseNumber())) {
            throw new RuntimeException("数据异常!");
        }
        Iterable<CaseInfo> all = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(caseFollowupParams.getCaseNumber()));
        List<CaseInfo> list = new ArrayList<>();
        if (all.iterator().hasNext()) {
            all.forEach(obj -> {
                list.add(obj);
            });
        } else {
            throw new RuntimeException("该案件未找到");
        }
        CaseFollowupRecord caseFollowupRecord = new CaseFollowupRecord();
        BeanUtils.copyProperties(caseFollowupParams, caseFollowupRecord);
        caseFollowupRecord.setId(null);
        caseFollowupRecord.setCaseId(caseFollowupParams.getCaseId());
        caseFollowupRecord.setCaseNumber(caseFollowupParams.getCaseNumber());
        caseFollowupRecord.setPersonalId(caseFollowupParams.getPersonalId());
        caseFollowupRecord.setSeatType(caseFollowupParams.getSeatType());
        caseFollowupRecord.setResult(caseFollowupParams.getResult());
        caseFollowupRecord.setConversationType(caseFollowupParams.getConversationType());
        caseFollowupRecord.setAgentName(caseFollowupParams.getAgentName());
        caseFollowupRecord.setRingingDuration(caseFollowupParams.getRingingDuration());
        caseFollowupRecord.setDialTime(caseFollowupParams.getDialTime());
        caseFollowupRecord.setHangUpTime(caseFollowupParams.getHangUpTime());
        caseFollowupRecord.setCompanyCode(tokenUser.getCompanyCode());
        caseFollowupRecord.setOperator(tokenUser.getUserName()); //操作人
        caseFollowupRecord.setOperatorName(tokenUser.getRealName()); //操作人姓名
        caseFollowupRecord.setOperatorDeptName(tokenUser.getDepartment().getName()); // 操作人部门
        caseFollowupRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseFollowupRecord.setFollowPerson(tokenUser.getRealName());
        caseFollowupRecord.setDetail(caseFollowupParams.getDetail());
        caseFollowupRecord.setCaseFollowupType(CaseFollowupRecord.CaseFollowupType.INNER.getValue());
        caseFollowupRecord.setFollnextDate(ZWDateUtil.getFormatDate(caseFollowupParams.getFollnextDate())); //下次跟进时间
        if (Strings.isNullOrEmpty(caseFollowupParams.getFellowWorkers())) {
            caseFollowupRecord.setFellowWorkers(caseFollowupParams.getFellowWorkers());
        }
        caseFollowupRecordRepository.saveAndFlush(caseFollowupRecord);

        //同步更新案件
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue(), list.get(i).getCollectionStatus())
                    || Objects.equals(CaseInfo.CollectionStatus.PART_REPAID.getValue(), list.get(i).getCollectionStatus())) {
                list.get(i).setCollectionStatus(CaseInfo.CollectionStatus.COLLECTIONING.getValue());//首次跟进将催收状态变为催收中
            }
            list.get(i).setFollowupTime(caseFollowupRecord.getOperatorTime()); //最新跟进时间
            list.get(i).setFollowupBack(caseFollowupRecord.getCollectionFeedback()); //催收反馈
            list.get(i).setPromiseAmt(caseFollowupRecord.getPromiseAmt()); //承诺还款金额
            list.get(i).setPromiseTime(caseFollowupRecord.getPromiseDate()); //承诺还款日期
            list.get(i).setOperator(tokenUser); //操作人
            list.get(i).setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        }
        caseInfoRepository.save(list);

//        //承诺还款提醒
//        if (Objects.nonNull(caseFollowupParams.getCollectionFeedback()) && caseFollowupParams.getCollectionFeedback().equals(EffectiveCollection.PROMISE.getValue())) {
//            SendReminderMessage sendReminderMessage = new SendReminderMessage();
//            sendReminderMessage.setTitle("客户 [" + caseInfo.getPersonalInfo().getName() + "] 承诺今日还款");
//            sendReminderMessage.setUserId(userRepository.findByUserName(caseFollowupRecord.getOperator()).getId());
//            sendReminderMessage.setRemindTime(caseFollowupParams.getPromiseDate());
//            sendReminderMessage.setContent("客户 [" + caseInfo.getPersonalInfo().getName() + "] 承诺今日还款 [" + caseFollowupParams.getPromiseAmt() + "] 元");
//            sendReminderMessage.setType(ReminderType.FLLOWUP);
//            reminderService.sendReminderCalendarMessage(sendReminderMessage);
//        }
//
//        //消息提醒
//        if (Objects.nonNull(caseFollowupParams.getFollnextDate())) {
//            SendReminderMessage sendReminderMessage = new SendReminderMessage();
//            sendReminderMessage.setTitle("客户 [" + caseInfo.getPersonalInfo().getName() + "] 的跟进提醒");
//            sendReminderMessage.setUserId(userRepository.findByUserName(caseFollowupRecord.getOperator()).getId());
//            sendReminderMessage.setRemindTime(ZWDateUtil.getFormatDate(caseFollowupParams.getFollnextDate()));
//            sendReminderMessage.setContent(caseFollowupParams.getFollnextContent());
//            sendReminderMessage.setType(ReminderType.FLLOWUP);
//            reminderService.sendReminderCalendarMessage(sendReminderMessage);
//        }

        return caseFollowupRecord;
    }

    /**
     * @Description 协催申请
     */
    public void saveAssistApply(AssistApplyParams assistApplyParams, User tokenUser) {
        CaseInfo caseInfo = caseInfoRepository.findOne(assistApplyParams.getCaseId());
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
//        if (!Objects.equals(caseInfo.getCollectionType(), CaseInfo.CollectionType.TEL.getValue())) {
//            throw new RuntimeException("非电催案件不允许申请协催");
//        }
        QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
        if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标识
            CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(assistApplyParams.getCaseId()).
                    and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
            if (Objects.isNull(caseAssist)) { //有协催申请
                throw new RuntimeException("该案件已经提交了协催申请，不允许重复提交");
            } else { //有协催案件
                throw new RuntimeException("该案件正在协催，不允许重复申请");
            }
        }
        //获得失效日数
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.ASSIST_APPLY_CODE).and(qSysParam.type.eq(Constants.TYPE_TEL)).and(qSysParam.companyCode.eq(tokenUser.getCompanyCode())));
        if (Objects.isNull(sysParam)) {
            throw new RuntimeException("协催失效配置天数未找到");
        }
        Integer days = Integer.parseInt(sysParam.getValue());
        Long nowtime = ZWDateUtil.getNowDateTime().getTime();
        Long invalidTime = nowtime + days * 86400000;
        Date applyInvalidTime = new Date(invalidTime); //失效日期

        //新增协催申请记录
        CaseAssistApply caseAssistApply = new CaseAssistApply();
        caseAssistApply.setCaseId(assistApplyParams.getCaseId()); //案件ID
        caseAssistApply.setCaseNumber(caseInfo.getCaseNumber()); //案件编号
        caseAssistApply.setPersonalName(caseInfo.getPersonalInfo().getName()); //客户姓名
        caseAssistApply.setPersonalPhone(caseInfo.getPersonalInfo().getMobileNo()); // 客户电话
        caseAssistApply.setPersonalId(caseInfo.getPersonalInfo().getId()); //客户信息ID
        caseAssistApply.setCollectionType(caseInfo.getCollectionType()); //催收类型
        caseAssistApply.setDeptCode(caseInfo.getDepartment().getCode()); //部门Code
        caseAssistApply.setPrincipalId(caseInfo.getPrincipalId().getId()); //委托方ID
        caseAssistApply.setPrincipalName(caseInfo.getPrincipalId().getName()); //委托方名称
        caseAssistApply.setOverdueAmount(caseInfo.getOverdueAmount()); //逾期总金额
        caseAssistApply.setOverdueDays(caseInfo.getOverdueDays()); //逾期总天数
        caseAssistApply.setOverduePeriods(caseInfo.getOverduePeriods()); //逾期期数
        caseAssistApply.setHoldDays(caseInfo.getHoldDays()); //持案天数
        caseAssistApply.setLeftDays(caseInfo.getLeftDays()); //剩余天数
        caseAssistApply.setAreaId(ZWStringUtils.isEmpty(caseInfo.getArea()) ? null : caseInfo.getArea().getId()); //省份编号
        caseAssistApply.setAreaName(ZWStringUtils.isEmpty(caseInfo.getArea()) ? null : caseInfo.getArea().getAreaName()); //城市名称
        caseAssistApply.setApplyUserName(tokenUser.getUserName()); //申请人
        caseAssistApply.setApplyRealName(tokenUser.getRealName()); //申请人姓名
        caseAssistApply.setApplyDeptName(tokenUser.getDepartment().getName()); //申请人部门名称
        caseAssistApply.setApplyReason(assistApplyParams.getApplyReason()); //申请原因
        caseAssistApply.setApplyDate(ZWDateUtil.getNowDateTime()); //申请时间
        caseAssistApply.setApplyInvalidTime(applyInvalidTime); //申请失效日期
        caseAssistApply.setAssistWay(assistApplyParams.getAssistWay()); //协催方式
        caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.TEL_APPROVAL.getValue()); //审批状态 32-电催待审批
        caseAssistApply.setProductSeries(Objects.isNull(caseInfo.getProduct()) ? null : Objects.isNull(caseInfo.getProduct().getProductSeries()) ? null : caseInfo.getProduct().getProductSeries().getId()); //产品系列ID
        caseAssistApply.setProductId(Objects.isNull(caseInfo.getProduct()) ? null : caseInfo.getProduct().getId()); //产品ID
        caseAssistApply.setProductSeriesName(Objects.isNull(caseInfo.getProduct()) ? null : Objects.isNull(caseInfo.getProduct().getProductSeries()) ? null : caseInfo.getProduct().getProductSeries().getSeriesName()); //产品系列名称
        caseAssistApply.setProductName(Objects.isNull(caseInfo.getProduct()) ? null : caseInfo.getProduct().getProductName()); //产品名称
        caseAssistApply.setCompanyCode(caseInfo.getCompanyCode()); //公司code码
        caseAssistApplyRepository.saveAndFlush(caseAssistApply);

        //更新原案件
        caseInfo.setAssistFlag(1); //协催标识
        caseInfo.setAssistWay(assistApplyParams.getAssistWay()); //协催方式
        caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue()); //协催状态
        caseInfo.setOperator(tokenUser); //操作人
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseInfoRepository.saveAndFlush(caseInfo);
    }

    /**
     * @Description 判断用户下有没有正在催收的案件
     */
    public CollectionCaseModel haveCollectionCase(User user) {
        QCaseInfo qCaseInfo = caseInfo;
        Iterable<CaseInfo> caseInfos = caseInfoRepository.findAll(qCaseInfo.currentCollector.eq(user).
                and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                and(qCaseInfo.companyCode.eq(user.getCompanyCode()))); //获取催收员为该用户并且催收状态不为已结案的所有案件
        Iterator<CaseInfo> it = caseInfos.iterator();
        return todoIt(it);
    }

    /**
     * @Description 判断用户下有没有结案的案件
     */
    public CollectionCaseModel haveEndCase(User user) {
        QCaseInfo qCaseInfo = caseInfo;
        Iterable<CaseInfo> caseInfos = caseInfoRepository.findAll(qCaseInfo.currentCollector.eq(user).
                and(qCaseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                and(qCaseInfo.companyCode.eq(user.getCompanyCode()))); //获取催收员为该用户并且催收状态为已结案的所有案件
        Iterator<CaseInfo> it = caseInfos.iterator();
        return todoIt(it);
    }

    /**
     * @Description 判断机构下有没有正在催收的案件
     */
    public CollectionCaseModel haveCollectionCase(Department department) {
        QCaseInfo qCaseInfo = caseInfo;
        Iterable<CaseInfo> caseInfos = caseInfoRepository.findAll(qCaseInfo.department.code.startsWith(department.getCode()).
                and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                and(qCaseInfo.companyCode.eq(department.getCompanyCode()))); //获取部门下状态不为结案的所有案件
        Iterator<CaseInfo> it = caseInfos.iterator();
        return todoIt(it);
    }

    /**
     * @Description 判断机构下有没有结案的案件
     */
    public CollectionCaseModel haveEndCase(Department department) {
        QCaseInfo qCaseInfo = caseInfo;
        Iterable<CaseInfo> caseInfos = caseInfoRepository.findAll(qCaseInfo.department.code.startsWith(department.getCode()).
                and(qCaseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                and(qCaseInfo.companyCode.eq(department.getCompanyCode()))); //获取部门下状态为结案的所有案件
        Iterator<CaseInfo> it = caseInfos.iterator();
        return todoIt(it);
    }

    /**
     * @Description 构建collectionCaseModel
     */
    private CollectionCaseModel todoIt(Iterator<CaseInfo> it) {
        Integer num = 0;
        List<String> caseIds = new ArrayList<>();
        CollectionCaseModel collectionCaseModel = new CollectionCaseModel();
        if (it.hasNext()) { //查到的集合不为空
            while (it.hasNext()) {
                CaseInfo caseInfo = it.next();
                caseIds.add(caseInfo.getId());
                num++;
            }
            collectionCaseModel.setNum(num);
            collectionCaseModel.setCaseIds(caseIds);
        } else {
            collectionCaseModel.setNum(num);
            collectionCaseModel.setCaseIds(caseIds);
        }
        return collectionCaseModel;
    }

    /**
     * @Description 获取案件分配信息
     */
    public BatchDistributeModel getBatchDistribution(User tokenUser, List<Integer> modelType) {
        Iterable<User> users = userService.getAllUser(tokenUser.getDepartment().getId(), 0);
        Iterator<User> it = users.iterator();
        List<User> userList = new ArrayList<>();
        while (it.hasNext()) {
            User user = it.next();
            if (modelType.contains(user.getType())) {
                userList.add(user);
            }
        }
        Iterator<User> newUsers = userList.iterator();
        Integer avgCaseNum = 0; //人均案件数
        Integer userNum = 0; //登录用户部门下的所有启用用户总数
        Integer caseNum = 0; //登录用户部门下的所有启用用户持有未结案案件总数
        List<BatchInfoModel> batchInfoModels = new ArrayList<>();
        while (newUsers.hasNext()) {
            BatchInfoModel batchInfoModel = new BatchInfoModel();
            User user = newUsers.next();
            Integer caseCount = caseInfoRepository.getCaseCount(user.getId());
            BigDecimal allAccountBalance = caseInfoRepository.getUserAccountBalance(user.getId());
            batchInfoModel.setCaseCount(caseCount); //持有案件数
            batchInfoModel.setAllAccountBalance(allAccountBalance);//当前持有案件总账户余额
            batchInfoModel.setCollectionUser(user); //催收人
            batchInfoModels.add(batchInfoModel);
            userNum++;
            caseNum = caseNum + caseCount;
        }
        if (userNum != 0) {
            avgCaseNum = (caseNum % userNum == 0) ? caseNum / userNum : (caseNum / userNum + 1);
        }
        BatchDistributeModel batchDistributeModel = new BatchDistributeModel();
        batchDistributeModel.setAverageNum(avgCaseNum);
        batchDistributeModel.setBatchInfoModelList(batchInfoModels);
        return batchDistributeModel;
    }

    /**
     * @Description 批量分配
     * isAuto 是否是定时任务调用
     */
    public String batchCase(BatchDistributeModel batchDistributeModel, User tokenUser, Boolean isAuto) {
        List<BatchInfoModel> batchInfoModels = batchDistributeModel.getBatchInfoModelList();
        List<String> caseIds = batchDistributeModel.getCaseIds();

        List<CaseInfo> caseInfoList = caseInfoRepository.findAll(caseIds);
        String information = "";
        if (!isAuto) {
            for (CaseInfo info : caseInfoList) {
                if (caseRoamService.exitRecordApply(info)) {
                    information = information + info.getCaseNumber() + ",";
                }
            }
        }
        if (StringUtils.isNotBlank(information)) {
            String substring = information.substring(0, information.length() - 1);
            information = "案件编号为" + substring + "的案件已在流转流程中,请重新选择案件";
            log.info(information);
            return information;
        }

        List<CaseAssist> caseAssists = new ArrayList<>();
        List<CaseAssistApply> caseAssistApplies = new ArrayList<>();
        List<CaseInfo> caseInfos = new ArrayList<>();
        List<CaseTurnRecord> caseTurnRecords = new ArrayList<>();
        QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
        int flag = 0;
        for (BatchInfoModel batchInfoModel : batchInfoModels) {
            Integer caseCount = batchInfoModel.getDistributionCount(); //分配案件数
            if (0 == caseCount) {
                continue;
            }
            if (!Objects.equals(batchInfoModel.getCollectionUser().getType(), User.Type.VISIT.getValue())) { //分配给外访以外
                for (int i = 0; i < caseCount; i++) {
                    CaseInfo info = caseInfoList.get(flag);
//                    CaseInfo info = caseInfoRepository.findOne(caseIds.get(flag)); //获得案件信息
                    flag++;
                    Iterator<CaseInfo> iterator = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(info.getCaseNumber()).and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))).iterator();
                    List<CaseInfo> byCaseNumber = IteratorUtils.toList(iterator);
                    if (byCaseNumber.size() == 0) {
                        throw new RuntimeException("数据异常!");
                    }
                    for (CaseInfo caseInfo : byCaseNumber) {
                        Department department = batchInfoModel.getCollectionUser().getDepartment();
                        Integer collectionType = null;
                        Integer type = department.getType();
                        if (Objects.equals(type, Department.Type.TELEPHONE_COLLECTION.getValue())) {//电催
                            collectionType = CaseInfo.CollectionType.TEL.getValue();
                        } else if (Objects.equals(type, Department.Type.OUTBOUND_COLLECTION.getValue())) {//外访
                            collectionType = CaseInfo.CollectionType.VISIT.getValue();
                        }
                        caseInfo.setCollectionType(collectionType);

                        // 共债以案件维度处理数据
                        if (Objects.equals(byCaseNumber.get(0).getAssistFlag(), 1)) { //有协催标识
                            if (!Objects.equals(byCaseNumber.get(0).getCollectionType(), CaseInfo.CollectionType.VISIT.getValue())) { //是协催案件
                                if (isAuto) {
                                    continue;
                                } else {
                                    throw new RuntimeException("协催案件不能分配给外访以外的人员");
                                }
                            }
                            setAttribute(caseInfo, batchInfoModel.getCollectionUser(), tokenUser);
                            //查询是否有协催案件
                            CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                                    and(qCaseAssist.assistStatus.in(CaseInfo.AssistStatus.ASSIST_COLLECTING.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue())));
                            if (Objects.nonNull(caseAssist)) { //如果有协催案件，则同步更换当前催收员
                                caseAssist.setCurrentCollector(batchInfoModel.getCollectionUser());
                                caseAssists.add(caseAssist);
                            }
                        } else { //没有协催标识
                            setAttribute(caseInfo, batchInfoModel.getCollectionUser(), tokenUser);
                        }
                        caseInfos.add(caseInfo);
                    }
                    //分配完成新增流转记录
                    CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                    BeanUtils.copyProperties(info, caseTurnRecord); //将案件信息复制到流转记录
                    caseTurnRecord.setId(null); //主键置空
                    caseTurnRecord.setCaseId(info.getId()); //案件ID
                    caseTurnRecord.setCaseNumber(info.getCaseNumber());
                    caseTurnRecord.setDepartId(batchInfoModel.getCollectionUser().getDepartment().getId()); //部门ID
                    caseTurnRecord.setReceiveUserRealName(batchInfoModel.getCollectionUser().getRealName()); //接受人名称
                    caseTurnRecord.setReceiveDeptName(batchInfoModel.getCollectionUser().getDepartment().getName()); //接收部门名称
                    caseTurnRecord.setReceiveUserId(info.getCurrentCollector().getId()); //接受人ID
                    caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue()); // 触发动作
                    if (Objects.nonNull(info.getLatelyCollector())) {
                        caseTurnRecord.setCurrentCollector(info.getLatelyCollector().getId()); //当前催收员ID
                    }
                    caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                    caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员用户名
                    caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                    caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.INNER.getValue());
                    caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
                    caseTurnRecord.setTurnApprovalStatus(220);
                    caseTurnRecords.add(caseTurnRecord);
                }
            } else { //分配给外访
                for (int i = 0; i < caseCount; i++) {
                    CaseInfo info = caseInfoRepository.findOne(caseIds.get(flag)); //获得案件信息
                    flag++;
                    Iterator<CaseInfo> iterator = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(info.getCaseNumber()).and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))).iterator();
                    List<CaseInfo> byCaseNumber = IteratorUtils.toList(iterator);
                    if (byCaseNumber.size() == 0) {
                        throw new RuntimeException("数据异常!");
                    }
                    for (CaseInfo caseInfo : byCaseNumber) {
                        if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标识
                            if (Objects.equals(caseInfo.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue())) { //有协催申请
                                CaseAssistApply caseAssistApply = getCaseAssistApply(caseInfo.getId(), tokenUser, "案件流转强制拒绝", CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                                caseAssistApplies.add(caseAssistApply);
                            } else { //有协催案件
                                CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                                        and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                                if (Objects.isNull(caseAssist)) {
                                    throw new RuntimeException("协催案件未找到");
                                }
                                caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue()); //协催状态 29-协催催收中
                                caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                                caseAssist.setOperator(tokenUser); //操作员
                                caseAssist.setAssistCollector(batchInfoModel.getCollectionUser());
                                caseAssists.add(caseAssist);

                                //协催结束新增一条流转记录
//                                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
//                                BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
//                                caseTurnRecord.setId(null); //主键置空
//                                caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
//                                caseTurnRecord.setDepartId(caseInfo.getCurrentCollector().getDepartment().getId()); //部门ID
//                                caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
//                                caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
//                                caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接受人ID
//                                if (Objects.nonNull(caseInfo.getLatelyCollector())) {
//                                    caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
//                                }
//                                caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue());
//                                caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
//                                caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员用户名
//                                caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
//                                caseTurnRecords.add(caseTurnRecord);
                            }
                            //同步更新原案件协催员，协催方式，协催标识，协催状态
                            caseInfo.setAssistCollector(null); //协催员置空
                            caseInfo.setAssistWay(null); //协催方式置空
                            caseInfo.setAssistFlag(0); //协催标识 0-否
                            caseInfo.setAssistStatus(null); //协催状态置空
                            setAttribute(caseInfo, batchInfoModel.getCollectionUser(), tokenUser);
                        } else { //没有协催标识
                            CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                                    and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                            if (Objects.nonNull(caseAssist)) {
                                caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                                caseAssist.setOperator(tokenUser); //操作员
                                caseAssist.setLatelyCollector(caseAssist.getAssistCollector());
                                caseAssist.setAssistCollector(batchInfoModel.getCollectionUser());
                                caseAssists.add(caseAssist);
                            }
                            setAttribute(caseInfo, batchInfoModel.getCollectionUser(), tokenUser);
                        }
                        caseInfos.add(caseInfo);
                    }
                    //分配完成新增流转记录
                    CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                    BeanUtils.copyProperties(info, caseTurnRecord); //将案件信息复制到流转记录
                    caseTurnRecord.setId(null); //主键置空
                    caseTurnRecord.setCaseId(info.getId()); //案件ID
                    caseTurnRecord.setDepartId(batchInfoModel.getCollectionUser().getDepartment().getId()); //部门ID
                    caseTurnRecord.setReceiveUserRealName(batchInfoModel.getCollectionUser().getRealName()); //接受人名称
                    caseTurnRecord.setReceiveDeptName(batchInfoModel.getCollectionUser().getDepartment().getName()); //接收部门名称
                    caseTurnRecord.setReceiveUserId(batchInfoModel.getCollectionUser().getId()); //接受人ID
                    caseTurnRecord.setTurnApprovalStatus(220);
                    if (Objects.nonNull(info.getLatelyCollector())) {
                        caseTurnRecord.setCurrentCollector(info.getLatelyCollector().getId()); //记录分配后的前催收员ID
                    }
                    caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                    caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员用户名
                    caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                    caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue());
                    caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.OUTBOUND.getValue());
                    caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
                    caseTurnRecords.add(caseTurnRecord);
                }
            }
        }
        caseInfoRepository.save(caseInfos);
        caseAssistRepository.save(caseAssists);
        caseAssistApplyRepository.save(caseAssistApplies);
        caseTurnRecordRepository.save(caseTurnRecords);
        return information;
    }


    /**
     * @Description 案件颜色打标
     */

    public void caseMarkColor(CaseMarkParams caseMarkParams, User tokenUser) {
        List<String> caseIds = caseMarkParams.getCaseIds();
        for (String caseId : caseIds) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("案件未找到");
            }
            caseInfo.setCaseMark(caseMarkParams.getColorNum()); //打标
            caseInfo.setOperator(tokenUser); //操作人
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRepository.saveAndFlush(caseInfo);
        }
    }

    /**
     * @Description 修改联系人电话状态
     */
    public PersonalContact modifyPhoneStatus(PhoneStatusParams phoneStatusParams, User tokenUser) {
        PersonalContact personalContact = personalContactRepository.findOne(phoneStatusParams.getPersonalContactId());
        if (Objects.isNull(personalContact)) {
            throw new RuntimeException("该联系人信息未找到");
        }
        personalContact.setPhoneStatus(phoneStatusParams.getPhoneStatus()); //电话状态
        personalContact.setOperator(tokenUser.getUserName()); //操作人
        personalContact.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        personalContactRepository.saveAndFlush(personalContact);
        return personalContact;
    }

    /**
     * @Description 重新分配案件字段复制
     */
    private CaseInfo setAttribute(CaseInfo caseInfo, User user, User tokenUser) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("查不到该用户");
        }
        if (Objects.equals(user.getStatus(), 1)) {
            throw new RuntimeException("该用户已停用");
        }
        if (Objects.equals(user.getType(), User.Type.SYNTHESIZE.getValue())) {
            throw new RuntimeException("只能给电催或者外访人员分案");
        }
        caseInfo.setLatelyCollector(caseInfo.getCurrentCollector()); //上一个催收员
        caseInfo.setCurrentCollector(user); //当前催收员
        caseInfo.setHoldDays(0); //持案天数归0
        caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件标记为无色
        caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1); //流转次数加一
        caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //流入时间
        caseInfo.setLeaveCaseFlag(0); //留案标识置0
        caseInfo.setFollowupBack(null); //催收反馈置空
        caseInfo.setFollowupTime(null); //跟进时间置空
        caseInfo.setPromiseAmt(new BigDecimal(0)); //承诺还款金额置0
        caseInfo.setPromiseTime(null); //承诺还款时间置空
        caseInfo.setDepartment(user.getDepartment());
        if (Objects.equals(user.getType(), User.Type.TEL.getValue())) {
            caseInfo.setCollectionType(CaseInfo.CollectionType.TEL.getValue());
        } else if (Objects.equals(user.getType(), User.Type.VISIT.getValue())) {
            caseInfo.setCollectionType(CaseInfo.CollectionType.VISIT.getValue());
        } else if (Objects.equals(user.getType(), User.Type.JUD.getValue())) {
            caseInfo.setCollectionType(CaseInfo.CollectionType.JUDICIAL.getValue());
        } else if (Objects.equals(user.getType(), User.Type.OUT.getValue())) {
            caseInfo.setCollectionType(CaseInfo.CollectionType.outside.getValue());
        } else if (Objects.equals(user.getType(), User.Type.REMINDER.getValue())) {
            caseInfo.setCollectionType(CaseInfo.CollectionType.remind.getValue());
        } else if (Objects.equals(user.getType(), User.Type.SYNTHESIZE.getValue())) {
            caseInfo.setCollectionType(CaseInfo.CollectionType.COMPLEX.getValue());
        }
        caseInfo.setOperator(tokenUser); //操作员
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseInfo.setDepartment(user.getDepartment()); //部门
        return caseInfo;
    }

    /**
     * @Description 获得协催申请并set值
     */
    public CaseAssistApply getCaseAssistApply(String caseId, User tokenUser, String memo, Integer value) {
        List<Integer> list = new ArrayList<>(); //协催审批状态列表
        list.add(CaseAssistApply.ApproveStatus.TEL_APPROVAL.getValue()); // 32-电催待审批
        list.add(CaseAssistApply.ApproveStatus.VISIT_APPROVAL.getValue()); // 34-外访待审批
        QCaseAssistApply qCaseAssistApply = QCaseAssistApply.caseAssistApply;
        CaseAssistApply caseAssistApply = caseAssistApplyRepository.findOne(qCaseAssistApply.caseId.eq(caseId)
                .and(qCaseAssistApply.approveStatus.in(list)));
        if (Objects.isNull(caseAssistApply)) {
            throw new RuntimeException("协催申请记录未找到");
        }
        if (Objects.equals(caseAssistApply.getCollectionType(), CaseInfo.CollectionType.TEL.getValue())) { // 15-电催
            caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.TEL_COMPLETE.getValue()); //审批状态 33-电催完成
            caseAssistApply.setApprovePhoneResult(value); //电催审批结果 40-强制拒绝
            caseAssistApply.setApprovePhoneUser(tokenUser.getUserName()); //电催审批人用户名
            caseAssistApply.setApprovePhoneName(tokenUser.getRealName()); //电催审批人姓名
            caseAssistApply.setApprovePhoneDatetime(ZWDateUtil.getNowDateTime()); //电催审批时间
            caseAssistApply.setApprovePhoneMemo(memo); //电催审批意见  需要写成常量
        } else if (Objects.equals(caseAssistApply.getCollectionType(), CaseInfo.CollectionType.VISIT.getValue())) { // 16-外访
            caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.VISIT_COMPLETE.getValue()); //审批状态 35-外访完成
            caseAssistApply.setApproveOutResult(value); //外访审批结果 40-强制拒绝
            caseAssistApply.setApproveOutUser(tokenUser.getUserName()); //外访审批人用户名
            caseAssistApply.setApproveOutName(tokenUser.getRealName()); //外访审批人姓名
            caseAssistApply.setApproveOutDatetime(ZWDateUtil.getNowDateTime()); //外访审批时间
            caseAssistApply.setApproveOutMemo(memo); //外访审批意见  需要写成常量
        }
        return caseAssistApply;
    }

    /**
     * @Description 电催添加修复信息
     */
    public PersonalContact saveRepairInfo(RepairInfoModel repairInfoModel, User tokenUser) {
        PersonalContact personalContact = new PersonalContact();
        personalContact.setId(ShortUUID.uuid());
        personalContact.setPersonalId(repairInfoModel.getPersonalId()); //客户信息ID
        personalContact.setRelation(repairInfoModel.getRelation()); //关系
        personalContact.setName(repairInfoModel.getName()); //姓名
        personalContact.setPhone(repairInfoModel.getPhone()); //电话号码
        personalContact.setPhoneStatus(repairInfoModel.getPhoneStatus()); //电话状态
        personalContact.setSocialType(repairInfoModel.getSocialType()); //社交帐号类型
        personalContact.setSocialValue(repairInfoModel.getSocialValue()); //社交帐号内容
        personalContact.setMail(repairInfoModel.getMail()); //邮箱地址
        personalContact.setSource(Constants.DataSource.REPAIR.getValue()); //数据来源 147-修复
        personalContact.setOperator(tokenUser.getUserName()); //操作人
        personalContact.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        personalContactRepository.saveAndFlush(personalContact);

        PersonalAddress personalAddress = new PersonalAddress();
        personalAddress.setPersonalId(repairInfoModel.getPersonalId()); //客户信息ID
        personalAddress.setRelation(repairInfoModel.getRelation()); //关系
        personalAddress.setName(repairInfoModel.getName()); //姓名
        personalAddress.setDetail(repairInfoModel.getAddress()); //地址
        personalAddress.setStatus(repairInfoModel.getAddressStatus()); //地址状态
        personalAddress.setSource(Constants.DataSource.REPAIR.getValue()); //数据来源 147-修复
        personalAddress.setType(repairInfoModel.getType()); //地址类型
        personalAddress.setOperator(tokenUser.getUserName()); //操作人
        personalAddress.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        personalAddress.setLivingProvinceCode(repairInfoModel.getLivingProvinceCode());
        personalAddress.setLivingProvinceName(repairInfoModel.getLivingProvinceName());
        personalAddress.setLivingCityCode(repairInfoModel.getLivingCityCode());
        personalAddress.setLivingCityName(repairInfoModel.getLivingCityName());
        personalAddress.setLivingAreaCode(repairInfoModel.getLivingAreaCode());
        personalAddress.setLivingAreaName(repairInfoModel.getLivingAreaName());
        personalAddressRepository.saveAndFlush(personalAddress);  //保存客户联系人地址信息
        insertRepairFileInfo(repairInfoModel.getFileIds(), repairInfoModel.getCaseId(), tokenUser);

        return personalContact;
    }

    public void insertRepairFileInfo(List<String> fileIds, String caseId, User tokenUser) {
        //如果上传文件了将上传文件保存到caserepairrecord 中，然后在催收执行页中查看附件信息
        if (Objects.nonNull(fileIds) && fileIds.size() > 0) {
            try {
                for (String fid : fileIds) {
                    ResponseEntity<UploadFile> uploadFileResponseEntity = restTemplate.exchange(Constants.FILEID_SERVICE_URL.concat("uploadFile/getUploadFile/").concat(fid), HttpMethod.GET, null, UploadFile.class);
                    if (Objects.nonNull(uploadFileResponseEntity) && uploadFileResponseEntity.hasBody()) {
                        CaseRepairRecord caseRepairRecord = new CaseRepairRecord();
                        caseRepairRecord.setFileId(uploadFileResponseEntity.getBody().getId());
                        caseRepairRecord.setCaseId(caseId);
                        caseRepairRecord.setOperator(tokenUser.getUserName());
                        caseRepairRecord.setOperatorTime(ZWDateUtil.getNowDateTime());
                        caseRepairRecord.setRepairMemo(null);
                        caseRepairRecord.setFileUrl(uploadFileResponseEntity.getBody().getUrl());
                        caseRepairRecord.setFileType(uploadFileResponseEntity.getBody().getType());
                        caseRepairRecordRepository.saveAndFlush(caseRepairRecord);
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    /**
     * @Description 查看凭证
     */
    public List<UploadFile> getRepaymentVoucher(String casePayId) {
        //下载外访资料
        List<UploadFile> uploadFiles = new ArrayList<>();
        QCasePayFile qCasePayFile = QCasePayFile.casePayFile;
        Iterable<CasePayFile> caseFlowupFiles = casePayFileRepository.findAll(qCasePayFile.payId.eq(casePayId));
        Iterator<CasePayFile> it = caseFlowupFiles.iterator();
        if (it.hasNext()) {
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                CasePayFile casePayFile = it.next();
                String id = casePayFile.getFileid();
                sb.append(id).append(",");
            }
            String ids = sb.toString();
            ParameterizedTypeReference<List<UploadFile>> responseType = new ParameterizedTypeReference<List<UploadFile>>() {
            };
            ResponseEntity<List<UploadFile>> entity = restTemplate.exchange(Constants.FILEID_SERVICE_URL.concat("uploadFile/getAllUploadFileByIds/").concat(ids),
                    HttpMethod.GET, null, responseType);
            if (!entity.hasBody()) {
                throw new RuntimeException("下载失败");
            }
            return entity.getBody();//文件对象;
        } else {
            return uploadFiles;
        }
    }

    public List<UploadFile> getFollowupFile(String followId) {
        //下载跟进记录凭证
        List<UploadFile> uploadFiles = new ArrayList<>();//文件对象集合
        QCaseFlowupFile qCaseFlowupFile = QCaseFlowupFile.caseFlowupFile;
        Iterable<CaseFlowupFile> caseFlowupFiles = caseFlowupFileRepository.findAll(qCaseFlowupFile.followupId.id.eq(followId));
        Iterator<CaseFlowupFile> it = caseFlowupFiles.iterator();
        if (it.hasNext()) {
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                CaseFlowupFile file = it.next();
                String id = file.getFileid();
                sb.append(id).append(",");
            }
            String ids = sb.toString();
            ParameterizedTypeReference<List<UploadFile>> responseType = new ParameterizedTypeReference<List<UploadFile>>() {
            };
            ResponseEntity<List<UploadFile>> entity = restTemplate.exchange(Constants.FILEID_SERVICE_URL.concat("uploadFile/getAllUploadFileByIds/").concat(ids),
                    HttpMethod.GET, null, responseType);
            if (!entity.hasBody()) {
                throw new RuntimeException("下载失败");
            } else {
                uploadFiles = entity.getBody();//文件对象
            }
            return uploadFiles;
        } else {
            return uploadFiles;
        }
    }

    /**
     * @Description 分配前判断是否有协催案件或协催标识
     */
    public List<String> checkCaseAssist(CheckAssistParams checkAssistParams) {
        List<String> list = new ArrayList<>();
        String information;
        for (String caseId : checkAssistParams.getList()) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId); //遍历每一个案件
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("所选案件的案件信息未找到");
            }
            if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标识
                information = "案件编号为" + caseInfo.getCaseNumber() + "的案件已申请协催或存在协催案件";
                list.add(information);
            }
        }
        return list;
    }

    /**
     * @Descripion 留案操作
     */
    public LeaveCaseModel leaveCase(LeaveCaseParams leaveCaseParams, User tokenUser) {
        //获得所持有未结案的案件总数
        Integer caseNum = caseInfoRepository.getCaseCount(tokenUser.getId());

        //查询已留案案件数
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        int flagNum = (int) caseInfoRepository.count(qCaseInfo.currentCollector.id.eq(tokenUser.getId()).and(qCaseInfo.leaveCaseFlag.eq(1)));

        //获得留案比例
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParam;
        String companyCode;
        if (Objects.isNull(tokenUser.getCompanyCode())) {
            if (Objects.isNull(leaveCaseParams.getCompanyCode())) {
                throw new RuntimeException("请选择公司");
            }
            companyCode = leaveCaseParams.getCompanyCode();
        } else {
            companyCode = tokenUser.getCompanyCode();
        }
        if (Objects.equals(leaveCaseParams.getType(), 0)) { //电催
            sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.SYS_PHNOEFLOW_LEAVERATE).and(qSysParam.companyCode.eq(companyCode)));
        } else { //外访
            sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.SYS_OUTBOUNDFLOW_LEAVERATE).and(qSysParam.companyCode.eq(companyCode)));
        }
        Double rate = Double.parseDouble(sysParam.getValue()) / 100;

        //计算留案案件是否超过比例
        Integer leaveNum = (int) Math.floor(caseNum * rate); //可留案的案件数
        List<String> caseIds = leaveCaseParams.getCaseIds();
        for (String caseId : caseIds) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("所选案件未找到");
            }
            if (!Objects.equals(caseInfo.getCurrentCollector().getId(), tokenUser.getId())) {
                throw new RuntimeException("只能对自己所持有的案件进行留案操作");
            }
            if (Objects.equals(caseInfo.getLeaveCaseFlag(), 1)) {
                throw new RuntimeException("所选案件存在已经留案的案件");
            }
            if (flagNum >= leaveNum) {
                throw new RuntimeException("所选案件数量超过可留案案件数");
            }
            caseInfo.setLeaveCaseFlag(1); //留案标志
            caseInfo.setOperator(tokenUser); //操作人
            caseInfo.setLeaveDate(ZWDateUtil.getNowDate());
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRepository.saveAndFlush(caseInfo);
            flagNum++;
        }
        LeaveCaseModel leaveCaseModel = new LeaveCaseModel();
        leaveCaseModel.setCaseNum(leaveNum - flagNum);
        return leaveCaseModel;
    }

    /**
     * @Description 取消留案操作
     */
    public LeaveCaseModel cancelLeaveCase(LeaveCaseParams leaveCaseParams, User tokenUser) {
        List<String> caseIds = leaveCaseParams.getCaseIds();
        for (String caseId : caseIds) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("所选案件未找到");
            }
            if (Objects.equals(caseInfo.getLeaveCaseFlag(), CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue())) {
                throw new RuntimeException("所选案件存在非留案案件");
            }
            if (!Objects.equals(caseInfo.getCurrentCollector().getId(), tokenUser.getId())) {
                throw new RuntimeException("只能对自己所持有的案件进行取消留案操作");
            }
            caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue()); //留案标识置为 0-非留案
            caseInfo.setLeaveDate(null);
            caseInfo.setHasLeaveDays(0); //留案天数归0
            caseInfo.setOperator(tokenUser); //操作人
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRepository.saveAndFlush(caseInfo);
        }
        //获得所持有未结案的案件总数
        Integer caseNum = caseInfoRepository.getCaseCount(tokenUser.getId());

        //查询已留案案件数
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        int flagNum = (int) caseInfoRepository.count(qCaseInfo.currentCollector.id.eq(tokenUser.getId()).and(qCaseInfo.leaveCaseFlag.eq(1)));

        //获得留案比例
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParam;
        String companyCode;
        if (Objects.isNull(tokenUser.getCompanyCode())) {
            if (Objects.isNull(leaveCaseParams.getCompanyCode())) {
                throw new RuntimeException("请选择公司");
            }
            companyCode = leaveCaseParams.getCompanyCode();
        } else {
            companyCode = tokenUser.getCompanyCode();
        }
        if (Objects.equals(leaveCaseParams.getType(), 0)) { //电催
            sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.SYS_PHNOEFLOW_LEAVERATE).and(qSysParam.companyCode.eq(companyCode)));
        } else { //外访
            sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.SYS_OUTBOUNDFLOW_LEAVERATE).and(qSysParam.companyCode.eq(companyCode)));
        }
        Double rate = Double.parseDouble(sysParam.getValue()) / 100;
        Integer leaveNum = (int) Math.floor(caseNum * rate); //可留案的案件数
        LeaveCaseModel leaveCaseModel = new LeaveCaseModel();
        leaveCaseModel.setCaseNum(leaveNum - flagNum);
        return leaveCaseModel;
    }

    /**
     * @Description 申请提前流转
     */
    public void advanceCirculation(AdvanceCirculationParams advanceCirculationParams, User tokenUser) {
        List<String> caseIds = advanceCirculationParams.getCaseIds();
        for (String caseId : caseIds) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("所选案件未找到");
            }
            if (Objects.equals(caseInfo.getCirculationStatus(), CaseInfo.CirculationStatus.PHONE_WAITING.getValue())
                    || Objects.equals(caseInfo.getCirculationStatus(), CaseInfo.CirculationStatus.VISIT_WAITING.getValue())) {
                throw new RuntimeException("所选案件已提交提前流转申请");
            }
            if (Objects.equals(advanceCirculationParams.getType(), 0)) {
                caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.PHONE_WAITING.getValue()); //小流转审批状态 197-电催流转待审批
            } else {
                caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.VISIT_WAITING.getValue()); //小流转审批状态 200-外访流转待审批
            }
            caseInfo.setOperator(tokenUser); //操作人
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRepository.saveAndFlush(caseInfo);
            //将数据插入到流转申请表中
            CaseAdvanceTurnApplay caseAdvanceTurnApplay = new CaseAdvanceTurnApplay();
            BeanUtils.copyProperties(caseInfo, caseAdvanceTurnApplay);
            if (Objects.equals(advanceCirculationParams.getType(), 0)) {
                caseAdvanceTurnApplay.setCollectionType(0);//电催
            } else {
                caseAdvanceTurnApplay.setCollectionType(1);//外访
            }
            caseAdvanceTurnApplay.setDeptCode(tokenUser.getDepartment().getCode());
            caseAdvanceTurnApplay.setCaseId(caseInfo.getId());
            caseAdvanceTurnApplay.setPersonalName(caseInfo.getPersonalInfo().getName());
            caseAdvanceTurnApplay.setApplayRealName(tokenUser.getRealName());
            caseAdvanceTurnApplay.setApplayUserName(tokenUser.getUserName());
            caseAdvanceTurnApplay.setApplayDeptName(tokenUser.getDepartment().getName());
            caseAdvanceTurnApplay.setApplayReason(advanceCirculationParams.getReason());
            caseAdvanceTurnApplay.setApplayDate(ZWDateUtil.getNowDateTime());
            caseAdvanceTurnApplay.setApproveResult(CaseAdvanceTurnApplay.CirculationStatus.PHONE_WAITING.getValue());//待审批
            caseAdvanceTurnApplayRepository.save(caseAdvanceTurnApplay);
        }
        //消息提醒
        List<User> userList = userService.getAllHigherLevelManagerByUser(tokenUser.getId());
        List<String> managerIdList = new ArrayList<>();
        for (User user : userList) {
            managerIdList.add(user.getId());
        }
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        sendReminderMessage.setTitle("案件提前流转申请");
        sendReminderMessage.setContent("您有 [" + caseIds.size() + "] 条提前流转案件申请需要审批");
        sendReminderMessage.setType(ReminderType.CIRCULATION);
        sendReminderMessage.setCcUserIds(managerIdList.toArray(new String[managerIdList.size()]));
        reminderService.sendReminder(sendReminderMessage);
    }

    /**
     * @Description 审批小流转案件
     */
    public void approvalCirculation(CirculationApprovalParams circulationApprovalParams, User tokenUser) {
        CaseAdvanceTurnApplay caseAdvanceTurnApplay = caseAdvanceTurnApplayRepository.findOne(circulationApprovalParams.getApproveId());//审核申请信息
        if (Objects.isNull(caseAdvanceTurnApplay)) {
            throw new RuntimeException("该申请未找到");
        }
        CaseInfo caseInfo = caseInfoRepository.findOne(caseAdvanceTurnApplay.getCaseId()); //获取案件信息
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
        String userIdForRemind = caseInfo.getCurrentCollector().getId();
        if (Objects.equals(circulationApprovalParams.getResult(), 0)) { //审批通过
            if (Objects.equals(circulationApprovalParams.getType(), 0)) { //电催小流转
                caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.PHONE_PASS.getValue()); //198-电催流转通过
                if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标志
                    if (Objects.equals(caseInfo.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue())) { //有协催申请
                        CaseAssistApply caseAssistApply = getCaseAssistApply(caseInfo.getId(), tokenUser, "流转强制拒绝", CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                        caseAssistApplyRepository.saveAndFlush(caseAssistApply);
                    } else { //有协催案件
                        QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
                        CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                                and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                        if (Objects.isNull(caseAssist)) {
                            throw new RuntimeException("协催案件未找到");
                        }
                        caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
                        caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                        caseAssist.setOperator(tokenUser); //操作员
                        caseAssistRepository.saveAndFlush(caseAssist);

                        //协催结束新增一条流转记录
                        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                        caseTurnRecord.setId(null); //主键置空
                        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                        caseTurnRecord.setDepartId(caseInfo.getCurrentCollector().getDepartment().getId()); //部门ID
                        caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                        caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
                        caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接受人ID
                        if (Objects.isNull(caseInfo.getLatelyCollector())) {
                            caseTurnRecord.setCurrentCollector(null);
                        } else {
                            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
                        }
                        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                        caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员
                        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                        caseTurnRecordRepository.saveAndFlush(caseTurnRecord);
                    }
                }
                //更新原案件状态
                caseInfo.setCaseType(CaseInfo.CaseType.PHNONEFAHEADTURN.getValue());
            } else { //外访小流转
                caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.VISIT_PASS.getValue()); //201-外访流转通过
                caseInfo.setCaseType(CaseInfo.CaseType.OUTFAHEADTURN.getValue());
            }
            //更新原案件
            caseInfo.setAssistCollector(null); //协催员置空
            caseInfo.setAssistWay(null); //协催方式置空
            caseInfo.setAssistFlag(0); //协催标识 0-否
            caseInfo.setAssistStatus(null); //协催状态置空
            caseInfo.setFollowupBack(null); //催收反馈置空
            caseInfo.setFollowupTime(null); //跟进时间置空
            caseInfo.setPromiseAmt(null); //承诺还款金额置空
            caseInfo.setPromiseTime(null); //承诺还款时间置空
            caseInfo.setLatelyCollector(caseInfo.getCurrentCollector()); //上一个催收员变为当前催收员
            caseInfo.setCurrentCollector(null); //当前催收员变为审批人
            caseInfo.setHoldDays(0); //持案天数归0
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件标记为无色
            caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1); //流转次数加一
            caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //流入时间
            caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态 20-待催收
            caseInfo.setLeaveCaseFlag(0); //留案标识置0

            //通过后添加一条流转记录 提前流转 审批不增加流转记录 祁吉贵 代码已删除
            caseAdvanceTurnApplay.setApproveResult(CaseAdvanceTurnApplay.CirculationStatus.PHONE_PASS.getValue());//通过
        } else { //审批拒绝
            if (Objects.equals(circulationApprovalParams.getType(), 0)) { //电催小流转
                caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.PHONE_REFUSE.getValue()); //199-电催流转拒绝
            } else { //外访小流转
                caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.VISIT_REFUSE.getValue()); //202-外访流转拒绝
            }
//            caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //案件类型恢复为193-案件分配
            caseAdvanceTurnApplay.setApproveResult(CaseAdvanceTurnApplay.CirculationStatus.PHONE_REFUSE.getValue());//拒绝
        }
        caseAdvanceTurnApplay.setApproveRealName(tokenUser.getRealName());
        caseAdvanceTurnApplay.setApproveDatetime(ZWDateUtil.getNowDateTime());
        caseAdvanceTurnApplay.setApproveUserName(tokenUser.getUserName());
        caseAdvanceTurnApplayRepository.save(caseAdvanceTurnApplay);
        caseInfo.setOperator(tokenUser); //操作人
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseInfoRepository.saveAndFlush(caseInfo);

        //消息提醒
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        sendReminderMessage.setUserId(userIdForRemind);
        sendReminderMessage.setTitle("客户 [" + caseInfo.getPersonalInfo().getName() + "] 的案件提前流转申请" + (Objects.equals(circulationApprovalParams.getResult(), 0) ? "已通过" : "被拒绝"));
        sendReminderMessage.setContent(caseAdvanceTurnApplay.getApproveMemo());
        sendReminderMessage.setType(ReminderType.CIRCULATION);
        reminderService.sendReminder(sendReminderMessage);
    }

    /**
     * @Description 查询客户联系人
     */
    public List<PersonalContact> getPersonalContact(String personalId) {
        OrderSpecifier<Date> sortOrder = QPersonalContact.personalContact.operatorTime.desc();
        OrderSpecifier<Integer> sourceOrder = QPersonalContact.personalContact.source.desc();
        QPersonalContact qPersonalContact = QPersonalContact.personalContact;
        Predicate pre=qPersonalContact.personalId.eq(personalId).and(qPersonalContact.name.isNotEmpty().or(qPersonalContact.phone.isNotEmpty()));
        Iterable<PersonalContact> personalContacts = personalContactRepository.findAll(
                pre,sortOrder, sourceOrder);
        return IterableUtils.toList(personalContacts);
    }

    /**
     * @Description 外方添加修复信息
     */
    public PersonalAddress saveVisitRepairInfo(RepairInfoModel repairInfoModel, User tokenUser) {
        PersonalAddress personalAddress = new PersonalAddress();
        personalAddress.setPersonalId(repairInfoModel.getPersonalId()); //客户信息ID
        personalAddress.setRelation(repairInfoModel.getRelation()); //关系
        personalAddress.setName(repairInfoModel.getName()); //姓名
        personalAddress.setDetail(repairInfoModel.getAddress()); //地址
        personalAddress.setStatus(repairInfoModel.getAddressStatus()); //地址状态
        personalAddress.setSource(Constants.DataSource.REPAIR.getValue()); //数据来源 147-修复
        personalAddress.setType(repairInfoModel.getType()); //地址类型
        personalAddress.setOperator(tokenUser.getUserName()); //操作人
        personalAddress.setLivingProvinceCode(repairInfoModel.getLivingProvinceCode());
        personalAddress.setLivingProvinceName(repairInfoModel.getLivingProvinceName());
        personalAddress.setLivingCityCode(repairInfoModel.getLivingCityCode());
        personalAddress.setLivingCityName(repairInfoModel.getLivingCityName());
        personalAddress.setLivingAreaCode(repairInfoModel.getLivingAreaCode());
        personalAddress.setLivingAreaName(repairInfoModel.getLivingAreaName());
        personalAddress.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        personalAddressRepository.saveAndFlush(personalAddress);  //保存客户联系人地址信息

        PersonalContact personalContact = new PersonalContact();
        personalContact.setId(ShortUUID.uuid());
        personalContact.setPersonalId(repairInfoModel.getPersonalId()); //客户信息ID
        personalContact.setRelation(repairInfoModel.getRelation()); //关系
        personalContact.setName(repairInfoModel.getName()); //姓名
        personalContact.setPhone(repairInfoModel.getPhone()); //电话号码
        personalContact.setPhoneStatus(repairInfoModel.getPhoneStatus()); //电话状态
        personalContact.setSocialType(repairInfoModel.getSocialType()); //社交帐号类型
        personalContact.setSocialValue(repairInfoModel.getSocialValue()); //社交帐号内容
        personalContact.setMail(repairInfoModel.getMail()); //邮箱地址
        personalContact.setAddress(repairInfoModel.getAddress());//  联系地址
        personalContact.setAddressStatus(repairInfoModel.getAddressStatus());
        personalContact.setUpdateTime(ZWDateUtil.getNowDateTime());// 更新时间
        personalContact.setSource(Constants.DataSource.REPAIR.getValue()); //数据来源 147-修复
        personalContact.setOperator(tokenUser.getUserName()); //操作人
        personalContact.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        personalContactRepository.saveAndFlush(personalContact);

//        PersonalContact personalContact = new PersonalContact();
//
//        personalContactRepository.save(personalContact);//保存客户联系人相关信息
        insertRepairFileInfo(repairInfoModel.getFileIds(), repairInfoModel.getCaseId(), tokenUser);
        return personalAddress;
    }

    /**
     * @Description 查询客户联系人地址
     */
    public List<PersonalAddress> getPersonalAddress(String personalId) throws Exception {
        OrderSpecifier<Date> sortOrder = QPersonalAddress.personalAddress.operatorTime.desc();
        QPersonalAddress qPersonalAddress = QPersonalAddress.personalAddress;
        Iterable<PersonalAddress> personalAddresses1 = personalAddressRepository.findAll(qPersonalAddress.source.eq(Constants.DataSource.IMPORT.getValue()).
                and(qPersonalAddress.personalId.eq(personalId))); //查询导入的联系人信息
        Iterable<PersonalAddress> personalAddresses2 = personalAddressRepository.findAll(qPersonalAddress.source.eq(Constants.DataSource.REPAIR.getValue()).
                and(qPersonalAddress.personalId.eq(personalId)), sortOrder); //查询修复的联系人信息
        if (!personalAddresses1.iterator().hasNext() && !personalAddresses2.iterator().hasNext()) {
            return new ArrayList<>();
        }
        List<PersonalAddress> personalAddressList = IteratorUtils.toList(personalAddresses1.iterator());
        List<PersonalAddress> personalAddressList1 = IteratorUtils.toList(personalAddresses2.iterator());
        personalAddressList1.addAll(personalAddressList);
        //查询保存联系人经纬度信息  （影响地址查询速度  前端直接调用personalController/getMapInfo查询经纬度信息）
        /*for(PersonalAddress personalAddress : personalAddressList1){
            MapModel mapModel = accMapService.getAddLngLat(personalAddress.getDetail());
            personalAddress.setLatitude(BigDecimal.valueOf(mapModel.getLatitude()));
            personalAddress.setLongitude(BigDecimal.valueOf(mapModel.getLongitude()));
            personalAddressRepository.save(personalAddress);
        }*/
        return personalAddressList1;
    }

    /**
     * @Descriprion 修改地址状态
     */
    public PersonalAddress modifyAddressStatus(PhoneStatusParams phoneStatusParams, User tokenUser) {
        PersonalAddress personalAddress = personalAddressRepository.findOne(phoneStatusParams.getPersonalAddressId());
        if (Objects.isNull(personalAddress)) {
            throw new RuntimeException("该联系人信息未找到");
        }
        personalAddress.setStatus(phoneStatusParams.getAddressStatus()); //地址状态
        personalAddress.setOperator(tokenUser.getUserName()); //操作人
        personalAddress.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        personalAddressRepository.saveAndFlush(personalAddress);
        return personalAddress;
    }

    @Transactional
    public void distributeRepairCase(AccCaseInfoDisModel accCaseInfoDisModel, User user) throws Exception {
        //案件列表
        List<CaseInfo> caseInfoObjList = new ArrayList<>();
        //流转记录列表
        List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();
        List<CaseRepair> caseRepairList = new ArrayList<>();
        //选择的案件ID列表
        List<String> caseInfoList = accCaseInfoDisModel.getCaseIdList();
        //每个机构或人分配的数量
        List<Integer> disNumList = accCaseInfoDisModel.getCaseNumList();
        //已经分配的案件数量
        int alreadyCaseNum = 0;
        //接收案件列表信息
        List<String> deptOrUserList = null;
        //机构分配
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            //所要分配 机构id
            deptOrUserList = accCaseInfoDisModel.getDepIdList();
        } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
            //得到所有用户ID
            deptOrUserList = accCaseInfoDisModel.getUserIdList();
        }
        for (int i = 0; i < (deptOrUserList != null ? deptOrUserList.size() : 0); i++) {
            //如果按机构分配则是机构的ID，如果是按用户分配则是用户ID
            String deptOrUserid = deptOrUserList.get(i);
            Department department = null;
            User targetUser = null;
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                department = departmentRepository.findOne(deptOrUserid);
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                targetUser = userRepository.findOne(deptOrUserid);
            }
            //需要分配的案件数据
            Integer disNum = disNumList.get(i);
            for (int j = 0; j < disNum; j++) {
                //检查输入的案件数量是否和选择的案件数量一致
                if (alreadyCaseNum > caseInfoList.size()) {
                    throw new Exception("选择的案件总量与实际输入的案件数量不匹配");
                }
                String caseId = caseInfoList.get(alreadyCaseNum);
                CaseRepair caseRepair = caseRepairRepository.findOne(caseId);
                if (targetUser != null && Objects.isNull(targetUser.getCompanyCode())) {
                    throw new Exception("不能把案件分配给超级管理员");
                }
                if (Objects.equals(caseRepair.getCaseId().getCollectionType(), CaseInfo.CollectionType.TEL.getValue())
                        && !Objects.equals(user.getType(), User.Type.SYNTHESIZE.getValue())
                        && Objects.nonNull(user.getType())) {
                    if (!Objects.equals(user.getType(), User.Type.TEL.getValue())) {
                        throw new Exception("当前用户不可以分配电催案件");
                    }
                    if (Objects.nonNull(department) && !Objects.equals(department.getType(), Department.Type.TELEPHONE_COLLECTION.getValue())) {
                        throw new Exception("电催案件不能分配给电催以外机构");
                    }
                    if (targetUser != null && !Objects.equals(targetUser.getType(), User.Type.TEL.getValue())) {
                        throw new Exception("电催案件不能分配给电催以外人员");
                    }
                }
                if (Objects.equals(caseRepair.getCaseId().getCollectionType(), CaseInfo.CollectionType.VISIT.getValue())
                        && !Objects.equals(user.getType(), User.Type.SYNTHESIZE.getValue())
                        && Objects.nonNull(user.getType())) {
                    if (!Objects.equals(user.getType(), User.Type.VISIT.getValue())) {
                        throw new Exception("当前用户不可以分配外访案件");
                    }
                    if (Objects.nonNull(department) && !Objects.equals(department.getType(), Department.Type.OUTBOUND_COLLECTION.getValue())) {
                        throw new Exception("外访案件不能分配给外访以外机构");
                    }
                    if (targetUser != null && !Objects.equals(targetUser.getType(), User.Type.VISIT.getValue())) {
                        throw new Exception("外访案件不能分配给外访以外人员");
                    }
                }

                if (Objects.nonNull(caseRepair)) {
                    CaseInfo caseInfo = new CaseInfo();
                    BeanUtils.copyProperties(caseRepair.getCaseId(), caseInfo);
                    if (Objects.nonNull(department)) {
                        caseInfo.setDepartment(department);
                        caseInfo.setCaseFollowInTime(null);
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()); //催收状态-待分配
                        if (Objects.equals(department.getType(), Department.Type.SYNTHESIZE_MANAGEMENT.getValue())) {
                            caseInfo.setCollectionType(CaseInfo.CollectionType.COMPLEX.getValue());
                        }
                        if (Objects.equals(department.getType(), Department.Type.TELEPHONE_COLLECTION.getValue())) {
                            caseInfo.setCollectionType(CaseInfo.CollectionType.TEL.getValue());
                        }
                        if (Objects.equals(department.getType(), Department.Type.OUTBOUND_COLLECTION.getValue())) {
                            caseInfo.setCollectionType(CaseInfo.CollectionType.VISIT.getValue());
                        }
                    }
                    if (targetUser != null) {
                        caseInfo.setDepartment(targetUser.getDepartment());
                        caseInfo.setCurrentCollector(targetUser);
                        if (Objects.equals(targetUser.getType(), User.Type.SYNTHESIZE.getValue())) {
                            caseInfo.setCollectionType(CaseInfo.CollectionType.COMPLEX.getValue());
                        }
                        if (Objects.equals(targetUser.getType(), User.Type.TEL.getValue())) {
                            caseInfo.setCollectionType(CaseInfo.CollectionType.TEL.getValue());
                        }
                        if (Objects.equals(targetUser.getType(), User.Type.VISIT.getValue())) {
                            caseInfo.setCollectionType(CaseInfo.CollectionType.VISIT.getValue());
                        }
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                        caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime());
                    }
                    caseInfo.setOperator(user);
                    caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
                    caseInfo.setLatelyCollector(caseInfo.getCurrentCollector()); //上一个催收员
                    caseInfo.setHoldDays(0); //持案天数归0
//                    caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //流转类型-案件分配
                    caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1); //流转次数加一
                    caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //流入时间
                    caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue()); //留案标识默认-非留案
                    caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件标记为无色
                    //案件列表
                    caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
                    caseInfoObjList.add(caseInfo);

                    if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //协催标识
                        if (Objects.equals(caseInfo.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue())) { //有协催申请
                            CaseAssistApply caseAssistApply = getCaseAssistApply(caseInfo.getId(), user, "修复分配拒绝", CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                            caseAssistApplyRepository.saveAndFlush(caseAssistApply);
                        } else {
                            //结束协催案件
                            CaseAssist one = caseAssistRepository.findOne(QCaseAssist.caseAssist.caseId.eq(caseInfo)
                                    .and(QCaseAssist.caseAssist.assistStatus.notIn(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                            if (Objects.nonNull(one)) {
                                one.setAssistCloseFlag(0); //手动结束
                                one.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催结束
                                one.setOperator(user);
                                one.setOperatorTime(new Date());
                                one.setCaseFlowinTime(new Date()); //流入时间
                                caseAssistRepository.saveAndFlush(one);
                            }
                            caseInfo.setAssistFlag(0); //协催标识置0
                            caseInfo.setAssistStatus(null);//协催状态置空
                            caseInfo.setAssistWay(null);
                            caseInfo.setAssistCollector(null);
                        }
                    }

                    //案件流转记录
                    CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                    BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                    caseTurnRecord.setId(null); //主键置空
                    caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                    caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
                    caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                    if (Objects.nonNull(caseInfo.getCurrentCollector())) {
                        caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
                        caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接收人ID
                        caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                    } else {
                        caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
                    }
                    caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
                    caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                    caseTurnRecord.setCirculationType(2);
                    caseTurnRecordList.add(caseTurnRecord);
                    //更新修复池
                    caseRepair.setRepairStatus(CaseRepair.CaseRepairStatus.DISTRIBUTE.getValue());
                    caseRepair.setOperatorTime(ZWDateUtil.getNowDateTime());
                    caseRepair.setOperator(user);
                    caseRepairList.add(caseRepair);
                }
                alreadyCaseNum = alreadyCaseNum + 1;
            }
        }
        //保存流转记录
        caseTurnRecordRepository.save(caseTurnRecordList);
        //保存修复信息
        caseRepairRepository.save(caseRepairList);
        //保存案件信息
        caseInfoRepository.save(caseInfoObjList);
    }

    /**
     * @Description 获取特定用户案件分配信息
     */
    public BatchDistributeModel getAttachBatchDistribution(List<String> userIds) {
        Iterator<String> it = userIds.iterator();
        Integer avgCaseNum = 0; //人均案件数
        Integer userNum = 0; //登录用户部门下的所有启用用户总数
        Integer caseNum = 0; //登录用户部门下的所有启用用户持有未结案案件总数
        List<BatchInfoModel> batchInfoModels = new ArrayList<>();
        while (it.hasNext()) {
            BatchInfoModel batchInfoModel = new BatchInfoModel();
            String userId = it.next();
            Integer caseCount = caseInfoRepository.getCaseCount(userId);
            batchInfoModel.setCaseCount(caseCount); //持有案件数
            batchInfoModel.setCollectionUser(userRepository.findOne(userId)); //催收人
            batchInfoModels.add(batchInfoModel);
            userNum++;
            caseNum = caseNum + caseCount;
        }
        if (userNum != 0) {
            avgCaseNum = (caseNum % userNum == 0) ? caseNum / userNum : (caseNum / userNum + 1);
        }
        BatchDistributeModel batchDistributeModel = new BatchDistributeModel();
        batchDistributeModel.setAverageNum(avgCaseNum);
        batchDistributeModel.setBatchInfoModelList(batchInfoModels);
        return batchDistributeModel;
    }

    /**
     * @Description 获取特定机构案件分配信息
     */
    public Long getDeptBatchDistribution(String deptId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfo.caseInfo.department.id.eq(deptId));
        builder.and(QCaseInfo.caseInfo.collectionStatus.in(20, 21, 22, 23, 25));
        return caseInfoRepository.count(builder);
    }

    public PreviewModel distributePreview(AccCaseInfoDisModel accCaseInfoDisModel, User user) {
        //选择的案件ID列表
        List<String> caseInfoList = accCaseInfoDisModel.getCaseIdList();
        //包含共债案件的ID列表
        List<String> debtList = new ArrayList<>();
        PreviewModel previewModel = new PreviewModel();
        List<CaseInfo> caseInfoYes = new ArrayList<>(); //可分配案件
        if (Objects.equals(accCaseInfoDisModel.getDisType(), 0) || !Objects.equals(accCaseInfoDisModel.getIsNumAvg(), 0)) {
            caseInfoYes = caseInfoRepository.findAll(caseInfoList);
        }
        Integer isDebt = accCaseInfoDisModel.getIsDebt();
        if (user != null) {
            if (Objects.equals(isDebt, 1)) {
                for (int i = 0; i < caseInfoYes.size(); i++) {
                    CaseInfo caseInfo = caseInfoYes.get(i);
                    String personalName = caseInfo.getPersonalInfo().getName();
                    String idCard = caseInfo.getPersonalInfo().getIdCard();
                    Object[] objDept = (Object[]) caseInfoRepository.findCaseByDept(personalName, idCard, user.getCompanyCode());//按机构分
                    if (Objects.nonNull(objDept)) {
                        debtList.add(caseInfo.getId());
                        caseInfoYes.remove(caseInfo);
                        i--;
                        continue;
                    }
                    Object[] objCollector = (Object[]) caseInfoRepository.findCaseByCollector(personalName, idCard, user.getCompanyCode());//按催员分
                    if (Objects.nonNull(objCollector)) {
                        debtList.add(caseInfo.getId());
                        caseInfoYes.remove(caseInfo);
                        i--;
                    }
                }
            }
        }

        //案件列表
        List<CaseInfo> caseInfoObjList = new ArrayList<>();
        //每个机构或人分配的数量
        List<Integer> disNumList = accCaseInfoDisModel.getCaseNumList();
        List<CaseInfoInnerDistributeModel> list = new ArrayList<>();
        //已经分配的案件数量
        int alreadyCaseNum = 0;
        //接收案件列表信息
        List<String> deptOrUserList = null;
        //机构分配
        Integer rule = accCaseInfoDisModel.getIsNumAvg();
        if (Objects.equals(rule, 1)) {
            int caseNum = caseInfoYes.size();
            int deptOrUserNum;
            if (Objects.isNull(accCaseInfoDisModel.getDepIdList()) || Objects.equals(accCaseInfoDisModel.getDepIdList().size(), 0)) {
                deptOrUserNum = accCaseInfoDisModel.getUserIdList().size();
            } else {
                deptOrUserNum = accCaseInfoDisModel.getDepIdList().size();
            }
            List<Integer> caseNumList = new ArrayList<>(deptOrUserNum);
            for (int i = 0; i < deptOrUserNum; i++) {
                caseNumList.add(caseNum / deptOrUserNum);
            }
            if (caseNum % deptOrUserNum != 0) {
                for (int i = 0; i < caseNum % deptOrUserNum; i++) {
                    caseNumList.set(i, caseNumList.get(i) + 1);
                }
            }
            disNumList = caseNumList;
        }

        if (Objects.equals(rule, 2)) {
            BeanUtils.copyProperties(preview(accCaseInfoDisModel, caseInfoYes), previewModel);
            debtList.addAll(previewModel.getCaseIds());
            previewModel.setCaseIds(debtList);
            return previewModel;
        }
        if (Objects.equals(rule, 3)) {
            BeanUtils.copyProperties(previewIntegrate(accCaseInfoDisModel, caseInfoYes), previewModel);
            debtList.addAll(previewModel.getCaseIds());
            previewModel.setCaseIds(debtList);
            return previewModel;
        }
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            //所要分配 机构id
            deptOrUserList = accCaseInfoDisModel.getDepIdList();
        } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
            //得到所有用户ID
            deptOrUserList = accCaseInfoDisModel.getUserIdList();
        }
        for (int i = 0; i < (deptOrUserList != null ? deptOrUserList.size() : 0); i++) {
            //如果按机构分配则是机构的ID，如果是按用户分配则是用户ID
            String deptOrUserid = deptOrUserList.get(i);
            CaseInfoInnerDistributeModel caseInfoInnerDistributeModel = new CaseInfoInnerDistributeModel();
            caseInfoInnerDistributeModel.setDistributeType(accCaseInfoDisModel.getDisType());
            if (Objects.equals(rule, 1)) {
                caseInfoInnerDistributeModel.setCaseDistributeCount(disNumList.get(i));
            }
            if (Objects.equals(accCaseInfoDisModel.getDisType(), 0)) {
                previewModel.getNumList().add(caseInfoList.size());
                caseInfoInnerDistributeModel.setCaseDistributeCount(caseInfoYes.size());
            }
            Department department = null;
            User targetUser = null;
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                department = departmentRepository.findOne(deptOrUserid);
                caseInfoInnerDistributeModel.setDepartmentName(department.getName());
                caseInfoInnerDistributeModel.setCaseCurrentCount(caseInfoRepository.getDeptCaseCount(department.getId()));
                // 将案件总金额字段由 caseMoneyCurrentCount 换为 accountBalance zmm 2018-07-25
                caseInfoInnerDistributeModel.setAccountBalance(caseInfoRepository.getDeptCaseAmt(department.getId()));
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                targetUser = userRepository.findOne(deptOrUserid);
                caseInfoInnerDistributeModel.setUserName(targetUser.getUserName());
                caseInfoInnerDistributeModel.setUserRealName(targetUser.getRealName());
                caseInfoInnerDistributeModel.setCaseCurrentCount(caseInfoRepository.getCaseCount(targetUser.getId()));
                // 将案件总金额字段由 caseMoneyCurrentCount 换为 accountBalance zmm 2018-07-25
                caseInfoInnerDistributeModel.setAccountBalance(caseInfoRepository.getUserCaseAmt(targetUser.getId()));
            }
            if (Objects.equals(rule, 0)) {
                alreadyCaseNum = alreadyCaseNum + 1;
            } else {
                //需要分配的案件数据
                Integer disNum = disNumList.get(i);
                for (int j = 0; j < disNum; j++) {
                    //检查输入的案件数量是否和选择的案件数量一致
                    CaseInfo caseInfo = caseInfoYes.get(alreadyCaseNum);
                    caseInfoInnerDistributeModel.setCaseDistributeMoneyCount(caseInfoInnerDistributeModel.getCaseDistributeMoneyCount().add(caseInfo.getOverdueCapital()));
                    alreadyCaseNum = alreadyCaseNum + 1;
                }
            }
            caseInfoInnerDistributeModel.setCaseTotalCount(caseInfoInnerDistributeModel.getCaseCurrentCount() + caseInfoInnerDistributeModel.getCaseDistributeCount());
            caseInfoInnerDistributeModel.setCaseMoneyTotalCount(caseInfoInnerDistributeModel.getCaseMoneyCurrentCount().add(caseInfoInnerDistributeModel.getCaseDistributeMoneyCount()));
            list.add(caseInfoInnerDistributeModel);
        }
        previewModel.setUserOrDepartIds(deptOrUserList);
        previewModel.setCaseIds(accCaseInfoDisModel.getCaseIdList());
        previewModel.setNumList(disNumList);
        previewModel.setList(list);
        return previewModel;
    }

    //金额平均分配预览
    private PreviewModel preview(AccCaseInfoDisModel accCaseInfoDisModel, List<CaseInfo> caseInfoYes) {
        PreviewModel previewModel = new PreviewModel();
        List<DisModel> disModels = new ArrayList<>();
        Collections.sort(caseInfoYes, (o1, o2) -> {
            return o2.getOverdueCapital().compareTo(o1.getOverdueCapital());
        });
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            for (String id : accCaseInfoDisModel.getDepIdList()) {
                DisModel model = new DisModel();
                model.setId(id);
                disModels.add(model);
            }
        }
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
            for (String id : accCaseInfoDisModel.getUserIdList()) {
                DisModel model = new DisModel();
                model.setId(id);
                disModels.add(model);
            }
        }
        for (CaseInfo caseInfo : caseInfoYes) {
            Collections.sort(disModels, (o1, o2) -> {
                return o1.getAmt().compareTo(o2.getAmt());
            });
            disModels.get(0).setAmt(disModels.get(0).getAmt().add(caseInfo.getOverdueCapital()));
            disModels.get(0).getCaseIds().add(caseInfo.getId());
            disModels.get(0).getCaseInfos().add(caseInfo);
        }
        List<CaseInfoInnerDistributeModel> list = new ArrayList<>();
        for (DisModel model : disModels) {
            CaseInfoInnerDistributeModel caseInfoInnerDistributeModel = new CaseInfoInnerDistributeModel();
            caseInfoInnerDistributeModel.setDistributeType(accCaseInfoDisModel.getDisType());
            caseInfoInnerDistributeModel.setCaseDistributeCount(model.getCaseIds().size());
            caseInfoInnerDistributeModel.setCaseDistributeMoneyCount(model.getAmt());
            previewModel.getCaseIds().addAll(model.getCaseIds());
            previewModel.getUserOrDepartIds().add(model.getId());
            previewModel.getNumList().add(model.getCaseIds().size());
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                Department department = departmentRepository.findOne(model.getId());
                caseInfoInnerDistributeModel.setDepartmentName(department.getName());
                caseInfoInnerDistributeModel.setCaseCurrentCount(caseInfoRepository.getDeptCaseCount(department.getId()));
                caseInfoInnerDistributeModel.setAccountBalance(caseInfoRepository.getDeptCaseAmt(department.getId()));
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                User targetUser = userRepository.findOne(model.getId());
                caseInfoInnerDistributeModel.setUserName(targetUser.getUserName());
                caseInfoInnerDistributeModel.setUserRealName(targetUser.getRealName());
                caseInfoInnerDistributeModel.setCaseCurrentCount(caseInfoRepository.getCaseCount(targetUser.getId()));
                caseInfoInnerDistributeModel.setAccountBalance(caseInfoRepository.getUserCaseAmt(targetUser.getId()));
            }
            caseInfoInnerDistributeModel.setCaseTotalCount(caseInfoInnerDistributeModel.getCaseCurrentCount() + caseInfoInnerDistributeModel.getCaseDistributeCount());
            caseInfoInnerDistributeModel.setCaseMoneyTotalCount(caseInfoInnerDistributeModel.getCaseMoneyCurrentCount().add(caseInfoInnerDistributeModel.getCaseDistributeMoneyCount()));
            list.add(caseInfoInnerDistributeModel);
        }
        previewModel.setList(list);
        return previewModel;
    }

    //综合分配预览
    private PreviewModel previewIntegrate(AccCaseInfoDisModel accCaseInfoDisModel, List<CaseInfo> caseInfoYes) {
        PreviewModel previewModel = new PreviewModel();
        List<DisModel> disModels = new ArrayList<>();
        Collections.sort(caseInfoYes, (o1, o2) -> {
            return o2.getOverdueCapital().compareTo(o1.getOverdueCapital());
        });
        int start = (int) Math.round(caseInfoYes.size() * 0.25);
        int end = (int) Math.round(caseInfoYes.size() * 0.75);
        List<CaseInfo> numAvgList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            numAvgList.add(caseInfoYes.get(start));
            caseInfoYes.remove(start);
        }
//        List<CaseInfo> amtAvgList = caseInfoYes;
        //先数量平均分配
        int caseNum = numAvgList.size();
        int deptOrUserNum;
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            deptOrUserNum = accCaseInfoDisModel.getDepIdList().size();
            for (String id : accCaseInfoDisModel.getDepIdList()) {
                DisModel model = new DisModel();
                model.setId(id);
                disModels.add(model);
            }
        } else {
            deptOrUserNum = accCaseInfoDisModel.getUserIdList().size();
            for (String id : accCaseInfoDisModel.getUserIdList()) {
                DisModel model = new DisModel();
                model.setId(id);
                disModels.add(model);
            }
        }
        List<Integer> caseNumList = new ArrayList<>(deptOrUserNum);
        for (int i = 0; i < deptOrUserNum; i++) {
            caseNumList.add(caseNum / deptOrUserNum);
            disModels.get(i).setNum(caseNum / deptOrUserNum);
        }
        if (caseNum % deptOrUserNum != 0) {
            for (int i = 0; i < caseNum % deptOrUserNum; i++) {
                caseNumList.set(i, caseNumList.get(i) + 1);
                disModels.get(i).setNum(caseNumList.get(i));
            }
        }
        Collections.shuffle(numAvgList);
        for (DisModel model : disModels) {
            List<CaseInfo> temp = numAvgList.subList(0, model.getNum());
            numAvgList = numAvgList.subList(model.getNum(), numAvgList.size());
            for (CaseInfo caseInfo : temp) {
                model.setAmt(model.getAmt().add(caseInfo.getOverdueCapital()));
                model.getCaseIds().add(caseInfo.getId());
                model.getCaseInfos().add(caseInfo);
            }
        }
        for (CaseInfo caseInfo : caseInfoYes) {
            Collections.sort(disModels, (o1, o2) -> {
                return o1.getAmt().compareTo(o2.getAmt());
            });
            disModels.get(0).setAmt(disModels.get(0).getAmt().add(caseInfo.getOverdueCapital()));
            disModels.get(0).getCaseIds().add(caseInfo.getId());
            disModels.get(0).getCaseInfos().add(caseInfo);
        }
        List<CaseInfoInnerDistributeModel> list = new ArrayList<>();
        for (DisModel model : disModels) {
            CaseInfoInnerDistributeModel caseInfoInnerDistributeModel = new CaseInfoInnerDistributeModel();
            caseInfoInnerDistributeModel.setDistributeType(accCaseInfoDisModel.getDisType());
            caseInfoInnerDistributeModel.setCaseDistributeCount(model.getCaseIds().size());
            caseInfoInnerDistributeModel.setCaseDistributeMoneyCount(model.getAmt());
            previewModel.getCaseIds().addAll(model.getCaseIds());
            previewModel.getUserOrDepartIds().add(model.getId());
            previewModel.getNumList().add(model.getCaseIds().size());
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                Department department = departmentRepository.findOne(model.getId());
                caseInfoInnerDistributeModel.setDepartmentName(department.getName());
                caseInfoInnerDistributeModel.setCaseCurrentCount(caseInfoRepository.getDeptCaseCount(department.getId()));
                caseInfoInnerDistributeModel.setAccountBalance(caseInfoRepository.getDeptCaseAmt(department.getId()));
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                User targetUser = userRepository.findOne(model.getId());
                caseInfoInnerDistributeModel.setUserName(targetUser.getUserName());
                caseInfoInnerDistributeModel.setUserRealName(targetUser.getRealName());
                caseInfoInnerDistributeModel.setCaseCurrentCount(caseInfoRepository.getCaseCount(targetUser.getId()));
                caseInfoInnerDistributeModel.setAccountBalance(caseInfoRepository.getUserCaseAmt(targetUser.getId()));
            }
            caseInfoInnerDistributeModel.setCaseTotalCount(caseInfoInnerDistributeModel.getCaseCurrentCount() + caseInfoInnerDistributeModel.getCaseDistributeCount());
            caseInfoInnerDistributeModel.setCaseMoneyTotalCount(caseInfoInnerDistributeModel.getCaseMoneyCurrentCount().add(caseInfoInnerDistributeModel.getCaseDistributeMoneyCount()));
            list.add(caseInfoInnerDistributeModel);
        }
        previewModel.setList(list);
        return previewModel;
    }

    /**
     * 内催待分配案件分配
     *
     * @param accCaseInfoDisModel
     * @param user
     * @param b
     * @throws Exception
     */
    public String distributeCeaseInfo(AccCaseInfoDisModel accCaseInfoDisModel, User user, boolean b) throws Exception {
        //选择的案件ID列表
        List<String> caseInfoList = accCaseInfoDisModel.getCaseIdList();
        //检查案件状态（待分配 待催收 催收中 承诺还款 可以分配）
        List<CaseInfo> caseInfoYes = new ArrayList<>(); //可分配案件
        String information = "";


        Iterable<CaseInfo> all1 = caseInfoRepository.findAll(QCaseInfo.caseInfo.id.in(caseInfoList).and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())));
        caseInfoYes = IteratorUtils.toList(all1.iterator());
        if (b) {
            for (CaseInfo caseInfo : caseInfoYes) {
                if (caseRoamService.exitRecordApply(caseInfo)) {
                    information = information + caseInfo.getCaseNumber() + ",";
                }
            }
        }
        if (StringUtils.isNotBlank(information)) {
            String substring = information.substring(0, information.length() - 1);
            information = "案件编号为" + substring + "的案件已在流转流程中,请重新选择案件";
            log.info(information);
            return information;
        }
        //案件列表
        List<CaseInfo> caseInfoObjList = new ArrayList<>();
        //流转记录列表
        List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();
        //每个机构或人分配的数量
        List<Integer> disNumList = accCaseInfoDisModel.getCaseNumList();
        //已经分配的案件数量
        int alreadyCaseNum = 0;
        //接收案件列表信息
        List<String> deptOrUserList = null;
        //机构分配
        Integer isDebt = accCaseInfoDisModel.getIsDebt();//是否共债优先
        if (Objects.equals(isDebt, 1)) {
            for (int i = 0; i < caseInfoYes.size(); i++) {
                CaseInfo caseInfo = caseInfoYes.get(i);
                String personalName = caseInfo.getPersonalInfo().getName();
                String idCard = caseInfo.getPersonalInfo().getIdCard();
                Object[] objDept = (Object[]) caseInfoRepository.findCaseByDept(personalName, idCard, user.getCompanyCode());//按机构分
                if (Objects.nonNull(objDept)) {
                    Department department = departmentRepository.findOne(objDept[0].toString());
                    String caseNumber = caseInfo.getCaseNumber();
                    BooleanBuilder builder = new BooleanBuilder();
                    builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseNumber));
                    builder.and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
                    Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
                    List<CaseInfo> caseInfos = IterableUtils.toList(all);
                    for (CaseInfo info : caseInfos) {
                        info.setDepartment(department); //部门
                        info.setLatelyCollector(caseInfo.getCurrentCollector()); //上个催收员
                        info.setCurrentCollector(null); //当前催收员置空
                        setCollectionType(info, department, null);
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                        updateCase(caseInfo, user);
                    }
                    caseInfoYes.remove(caseInfo);
                    i--;
                    continue;
                }
                Object[] objCollector = (Object[]) caseInfoRepository.findCaseByCollector(personalName, idCard, user.getCompanyCode());//按催员分
                if (Objects.nonNull(objCollector)) {
                    User collector = userRepository.findOne(objCollector[0].toString());

                    BooleanBuilder builder = new BooleanBuilder();
                    builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseInfo.getCaseNumber()));
                    Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
                    List<CaseInfo> caseInfos = IterableUtils.toList(all);
                    for (CaseInfo info : caseInfos) {
                        info.setDepartment(collector.getDepartment());
                        info.setCurrentCollector(collector);
                        setCollectionType(info, null, collector);
                        info.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                        updateCase(caseInfo, user);
                    }
                    caseInfoYes.remove(caseInfo);
                    i--;
                }
            }
        }
        Integer isNumAvg = accCaseInfoDisModel.getIsNumAvg();//是否数量平均
        if (Objects.equals(isNumAvg, 1)) {
            int caseNum = caseInfoYes.size();
            int deptOrUserNum;
            if (Objects.isNull(accCaseInfoDisModel.getDepIdList()) || Objects.equals(accCaseInfoDisModel.getDepIdList().size(), 0)) {
                deptOrUserNum = accCaseInfoDisModel.getUserIdList().size();
            } else {
                deptOrUserNum = accCaseInfoDisModel.getDepIdList().size();
            }
            List<Integer> caseNumList = new ArrayList<>(deptOrUserNum);
            for (int i = 0; i < deptOrUserNum; i++) {
                caseNumList.add(caseNum / deptOrUserNum);
            }
            if (caseNum % deptOrUserNum != 0) {
                for (int i = 0; i < caseNum % deptOrUserNum; i++) {
                    caseNumList.set(i, caseNumList.get(i) + 1);
                }
            }
            disNumList = caseNumList;
        }

        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            //所要分配 机构id
            deptOrUserList = accCaseInfoDisModel.getDepIdList();
        } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
            //得到所有用户ID
            deptOrUserList = accCaseInfoDisModel.getUserIdList();
        }
        for (int i = 0; i < (deptOrUserList != null ? deptOrUserList.size() : 0); i++) {
            //如果按机构分配则是机构的ID，如果是按用户分配则是用户ID
            String deptOrUserid = deptOrUserList.get(i);
            Department department = null;
            User targetUser = null;
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                department = departmentRepository.findOne(deptOrUserid);
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                targetUser = userRepository.findOne(deptOrUserid);
            }
            //需要分配的案件数据
            Integer disNum = disNumList.get(i);
            for (int j = 0; j < disNum; j++) {
                //检查输入的案件数量是否和选择的案件数量一致
                if (alreadyCaseNum == caseInfoYes.size()) {
                    return information;
                }
//                String caseId = caseInfoYes.get(alreadyCaseNum).getId();
//                CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
                CaseInfo caseInfo = caseInfoYes.get(alreadyCaseNum);
//                caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //案件类型-案件分配
                //按照部门分
                if (Objects.nonNull(department)) {
                    BooleanBuilder builder = new BooleanBuilder();
                    builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseInfo.getCaseNumber()));
                    builder.and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
                    Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
                    List<CaseInfo> caseInfos = IterableUtils.toList(all);
                    for (CaseInfo info : caseInfos) {
                        info.setDepartment(department); //部门
                        info.setLatelyCollector(caseInfo.getCurrentCollector()); //上个催收员
                        info.setCurrentCollector(null); //当前催收员置空

                        try {
                            setCollectionType(info, department, null);
                        } catch (final Exception e) {
                            log.error(e.getMessage());
                        }
                        info.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                        info.setExceptionFlag(0);// 分案的时候改变异常类型
                    }
                    caseInfoObjList.addAll(caseInfos);
                }
                //按照用户分
                if (targetUser != null) {
                    BooleanBuilder builder = new BooleanBuilder();
                    builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseInfo.getCaseNumber()));
                    builder.and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
                    Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
                    List<CaseInfo> caseInfos = IterableUtils.toList(all);
                    for (CaseInfo info : caseInfos) {
                        info.setDepartment(targetUser.getDepartment());
                        info.setCurrentCollector(targetUser);
                        try {
                            setCollectionType(info, null, targetUser);
                        } catch (Exception e) {
                            //此方法为异步调用 抛出的错误信息都接受不到
                            log.error(e.getMessage(), e);
                        }
                        info.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                        info.setExceptionFlag(0);// 分案的时候改变异常类型
                    }
                    caseInfoObjList.addAll(caseInfos);
                }
                List<CaseInfo> list = new ArrayList<>();
                List<String> caseNums = new ArrayList<>();
                for (int k = 0; k < caseInfoObjList.size(); k++) {
                    if (caseNums.contains(caseInfoObjList.get(k).getCaseNumber())) {
                        continue;
                    }
                    caseNums.add(caseInfoObjList.get(k).getCaseNumber());
                    list.add(caseInfoObjList.get(k));
                }

                //设置CaseInfo和添加流转记录
                if (accCaseInfoDisModel.isFlag()) {
                    setCaseInfoAuto(list, user, caseTurnRecordList, b);
                } else {
                    setCaseInfo(list, user, caseTurnRecordList, b);
                }
                caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
                //案件列表
//                caseInfoObjList.add(caseInfo);
                alreadyCaseNum = alreadyCaseNum + 1;
            }
        }
        //保存案件信息
        caseInfoRepository.save(caseInfoObjList);
        //保存流转记录
        caseTurnRecordRepository.save(caseTurnRecordList);
        return information;
    }

    /**
     * 案件重新分配
     *
     * @param accCaseInfoDisModel
     * @param user
     * @throws Exception
     */
    @Transactional
    public String distributeCeaseInfoAgain(AccCaseInfoDisModel accCaseInfoDisModel, User user) throws Exception {
        //选择的案件ID列表
        List<String> caseInfoList = accCaseInfoDisModel.getCaseIdList();
        //检查案件状态（待分配 待催收 催收中 承诺还款 可以分配）
        String information = "";
        List<CaseInfo> caseInfoYes = caseInfoRepository.findAll(caseInfoList);
        for (CaseInfo caseInfo : caseInfoYes) {
            if (caseRoamService.exitRecordApply(caseInfo)) {
                information = information + caseInfo.getCaseNumber() + ",";
            }
            if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue())) { //24-已结案
                throw new RuntimeException("已结案案件不能重新分配");
            }
            if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OUT.getValue())) { //166已委外
                throw new RuntimeException("已委外案件不能重新分配");
            }
        }
        if (StringUtils.isNotBlank(information)) {
            String substring = information.substring(0, information.length() - 1);
            information = "案件编号为" + substring + "的案件已在流转流程中,请重新选择案件";
            log.info(information);
            return information;
        }
        //流转记录列表
        List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();
        // 协催案件表
        List<CaseAssist> caseAssistList = new ArrayList<>();
        //每个机构或人分配的数量
        List<Integer> disNumList = accCaseInfoDisModel.getCaseNumList();
        //已经分配的案件数量
        int alreadyCaseNum = 0;
        //接收案件列表信息
        List<String> deptOrUserList = null;
        //机构分配
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            //所要分配 机构id
            deptOrUserList = accCaseInfoDisModel.getDepIdList();
        } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
            //得到所有用户ID
            deptOrUserList = accCaseInfoDisModel.getUserIdList();
        }
        List<CaseInfo> list = new ArrayList<>();
        for (int i = 0; i < (deptOrUserList != null ? deptOrUserList.size() : 0); i++) {
            //如果按机构分配则是机构的ID，如果是按用户分配则是用户ID
            String deptOrUserid = deptOrUserList.get(i);
            Department department = null;
            User targetUser = null;
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                department = departmentRepository.findOne(deptOrUserid);
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                targetUser = userRepository.findOne(deptOrUserid);
            }
            //需要分配的案件数据
            Integer disNum = disNumList.get(i);
            for (int j = 0; j < disNum; j++) {
                //检查输入的案件数量是否和选择的案件数量一致
                if (alreadyCaseNum == caseInfoYes.size()) {
                    return information;
                }
                String caseId = caseInfoYes.get(alreadyCaseNum).getId();
                CaseInfo caseInfo1 = caseInfoRepository.findOne(caseId);
                BooleanBuilder builder = new BooleanBuilder();
                builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseInfo1.getCaseNumber()));
                builder.and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
                Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
                List<CaseInfo> byCaseNumber = IterableUtils.toList(all);
                for (int k = 0; k < byCaseNumber.size(); k++) {
                    CaseInfo caseInfo = byCaseNumber.get(k);
//                caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //案件类型-案件分配
                    //按照部门分
                    if (Objects.nonNull(department)) {
                        caseInfo.setDepartment(department); //部门
                        caseInfo.setLatelyCollector(caseInfo.getCurrentCollector()); //上个催收员
                        caseInfo.setCurrentCollector(null); //当前催收员置空
                        try {
                            setCollectionType(caseInfo, department, null);
                        } catch (final Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                    }
                    //按照用户分
                    if (targetUser != null) {
                        caseInfo.setDepartment(targetUser.getDepartment());
                        caseInfo.setLatelyCollector(caseInfo.getCurrentCollector()); //上个催收员
                        caseInfo.setCurrentCollector(targetUser);
                        try {
                            setCollectionType(caseInfo, null, targetUser);
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                    }
                    //处理协催案件
                    if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //协催标识
                        //结束协催案件
                        CaseAssist one = caseAssistRepository.findOne(QCaseAssist.caseAssist.caseId.eq(caseInfo)
                                .and(QCaseAssist.caseAssist.assistStatus.notIn(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                        if (Objects.nonNull(one)) {
                            one.setAssistCloseFlag(0); //手动结束
                            one.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催结束
                            one.setOperator(user);
                            one.setOperatorTime(new Date());
                            one.setCaseFlowinTime(new Date()); //流入时间
                            caseAssistList.add(one);
                        }
                        caseInfo.setAssistFlag(0); //协催标识置0
                        caseInfo.setAssistStatus(null);//协催状态置空
                        caseInfo.setAssistWay(null);
                        caseInfo.setAssistCollector(null);
                    }
                    caseInfo.setExceptionFlag(0);
                    list.add(caseInfo);
                }
                //设置CaseInfo和添加流转记录
                setCaseInfoAgain(list, user, caseTurnRecordList, true);
                //案件列表
                // 分案的时候改变异常类型
                alreadyCaseNum = alreadyCaseNum + 1;
            }
        }
        //保存案件信息
        caseInfoRepository.save(list);
        //保存流转记录
        caseTurnRecordRepository.save(caseTurnRecordList);
        //保存协催案件
        caseAssistRepository.save(caseAssistList);
        return information;
    }

    public void setCaseInfo(List<CaseInfo> caseInfoObjList, User user, List<CaseTurnRecord> caseTurnRecordList, boolean b) {
        for (CaseInfo caseInfo : caseInfoObjList) {
            //案件类型
            //        caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //流转类型-案件分配
            caseInfo.setCaseFollowInTime(new Date()); //案件流入时间
            caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1);//流转次数
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件打标-无色
            caseInfo.setFollowupBack(null); //催收反馈置空
            caseInfo.setFollowupTime(null);//跟进时间置空
            caseInfo.setPromiseAmt(new BigDecimal(0));//承诺还款置0
            caseInfo.setPromiseTime(null);//承诺还款日期置空
            caseInfo.setCirculationStatus(null);//小流转状态
            caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标识
            caseInfo.setLeaveDate(null);//留案操作日期
            caseInfo.setHasLeaveDays(0);//留案天数
            caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
            caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());//是否挂起
            caseInfo.setOperator(user);
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
        }
        CaseInfo caseInfo = caseInfoObjList.get(0);
        //案件流转记录
        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
        caseTurnRecord.setId(null); //主键置空
        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
        caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
        if (Objects.nonNull(caseInfo.getCurrentCollector())) {
            caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
            caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接收人ID
            caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
        } else {
            caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
        }
        if (Objects.nonNull(caseInfo.getLatelyCollector())) {
            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //记录分配后的前催收员ID
        }
        caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
        caseTurnRecord.setTurnApprovalStatus(220);
        caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.CORE.getValue());
        caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
        if (b) {
            caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue());
        } else {
            caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.MANUAL_DIVISION.getValue());
        }
        caseTurnRecordList.add(caseTurnRecord);
    }

    public void setCaseInfoAgain(List<CaseInfo> caseInfoObjList, User user, List<CaseTurnRecord> caseTurnRecordList, boolean b) {
        for (CaseInfo caseInfo : caseInfoObjList) {
            //案件类型
//            caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //流转类型-案件分配
            caseInfo.setCaseFollowInTime(new Date()); //案件流入时间
            caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1);//流转次数
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件打标-无色
            caseInfo.setFollowupBack(null); //催收反馈置空
            caseInfo.setFollowupTime(null);//跟进时间置空
            caseInfo.setPromiseAmt(new BigDecimal(0));//承诺还款置0
            caseInfo.setPromiseTime(null);//承诺还款日期置空
            caseInfo.setCirculationStatus(null);//小流转状态
            caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标识
            caseInfo.setLeaveDate(null);//留案操作日期
            caseInfo.setHasLeaveDays(0);//留案天数
            caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
            caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());//是否挂起
            caseInfo.setOperator(user);
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
        }
        CaseInfo caseInfo = caseInfoObjList.get(0);
        //案件流转记录
        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
        caseTurnRecord.setId(null); //主键置空
        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
        caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
        if (Objects.nonNull(caseInfo.getCurrentCollector())) {
            caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
            caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接收人ID
            caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
        } else {
            caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
        }
        if (Objects.nonNull(caseInfo.getLatelyCollector())) {
            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //记录分配后的前催收员ID
        }
        caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
        caseTurnRecord.setTurnApprovalStatus(220);
        if (Objects.nonNull(caseInfo.getCollectionType())) {
            if (caseInfo.getCollectionType().equals(CaseInfo.CollectionType.TEL.getValue())) {
                caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.INNER.getValue());
            } else if (caseInfo.getCollectionType().equals(CaseInfo.CollectionType.outside.getValue())) {
                caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.OUTER.getValue());
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.OUTER.getValue());
            } else if (caseInfo.getCollectionType().equals(CaseInfo.CollectionType.VISIT.getValue())) {
                caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.OUTBOUND.getValue());
            } else if (caseInfo.getCollectionType().equals(CaseInfo.CollectionType.SPECIAL.getValue())) {
                caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.SPECIAL.getValue());
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.SPECIAL.getValue());
            } else if (caseInfo.getCollectionType().equals(CaseInfo.CollectionType.STOP.getValue())) {
                caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.STOP.getValue());
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.STOP.getValue());
            }
        }
        if (b) {
            caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue());
        } else {
            caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.MANUAL_DIVISION.getValue());
        }
        caseTurnRecordList.add(caseTurnRecord);
    }

    public void setCaseInfoAuto(List<CaseInfo> caseInfoObjList, User user, List<CaseTurnRecord> caseTurnRecordList, boolean b) {
        for (CaseInfo caseInfo : caseInfoObjList) {
            //案件类型
//        caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //流转类型-案件分配
            caseInfo.setCaseFollowInTime(new Date()); //案件流入时间
            caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1);//流转次数
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件打标-无色
            caseInfo.setFollowupBack(null); //催收反馈置空
            caseInfo.setFollowupTime(null);//跟进时间置空
            caseInfo.setPromiseAmt(new BigDecimal(0));//承诺还款置0
            caseInfo.setPromiseTime(null);//承诺还款日期置空
            caseInfo.setCirculationStatus(null);//小流转状态
            caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标识
            caseInfo.setLeaveDate(null);//留案操作日期
            caseInfo.setHasLeaveDays(0);//留案天数
            caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
            caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());//是否挂起
            caseInfo.setOperator(user);
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
        }

        CaseInfo caseInfo = caseInfoObjList.get(0);
        //案件流转记录
        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
        caseTurnRecord.setId(null); //主键置空
        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
        caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
        if (Objects.nonNull(caseInfo.getCurrentCollector())) {
            caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
            caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接收人ID
            caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
        } else {
            caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
        }
        if (Objects.nonNull(caseInfo.getLatelyCollector())) {
            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //记录分配后的前催收员ID
        }
        caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
        caseTurnRecord.setTurnApprovalStatus(220);
        caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.CORE.getValue());
        caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
        if (b) {
            caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue());
        } else {
            caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.MANUAL_DIVISION.getValue());
        }
        caseTurnRecordList.add(caseTurnRecord);
    }

    /**
     * 根据部门/用户Type设置案件催收类型
     *
     * @param caseInfo
     * @param department
     * @param user
     */
    public void setCollectionType(CaseInfo caseInfo, Department department, User user) throws Exception {
        Integer type = null;
        if (Objects.nonNull(department)) {
            type = department.getType();
        }
        if (Objects.nonNull(user)) {
            type = user.getDepartment().getType();
        }
        Integer poolType = null;
        if (Objects.equals(type, Department.Type.TELEPHONE_COLLECTION.getValue())) {//电催
            poolType = CaseInfo.CasePoolType.INNER.getValue();
        } else if (Objects.equals(type, Department.Type.OUTBOUND_COLLECTION.getValue())) {//外访
            poolType = CaseInfo.CasePoolType.INNER.getValue();
        } else if (Objects.equals(type, Department.Type.OUTSOURCING_COLLECTION.getValue())) {//委外
            poolType = CaseInfo.CasePoolType.OUTER.getValue();
        } else if (Objects.equals(type, Department.Type.SPECIAL_COLLECTION.getValue())) {//特殊
            poolType = CaseInfo.CasePoolType.SPECIAL.getValue();
        } else if (Objects.equals(type, Department.Type.STOP_COLLECTION.getValue())) {//停催
            poolType = CaseInfo.CasePoolType.STOP.getValue();
        }
        if (Objects.equals(poolType, caseInfo.getCasePoolType())) {
            if (type == null) throw new RuntimeException("不能给此用户分配案件!");
            switch (type) {
                case 1: //电话催收
                    caseInfo.setCollectionType(CaseInfo.CollectionType.TEL.getValue());
                    break;
                case 2: //外访催收
                    caseInfo.setCollectionType(CaseInfo.CollectionType.VISIT.getValue());
                    break;
                case 4: //委外催收
                    caseInfo.setCollectionType(CaseInfo.CollectionType.outside.getValue());
                    break;
                case 508: //特殊
                    caseInfo.setCollectionType(CaseInfo.CollectionType.SPECIAL.getValue());
                    break;
                case 506: //停催
                    caseInfo.setCollectionType(CaseInfo.CollectionType.STOP.getValue());
                    break;
                default:
                    throw new RuntimeException("不能给此用户分配案件!");
            }
        } else {
            throw new RuntimeException("此用户类型与催收类型不匹配!");
        }
    }

    public void turnCaseConfirm(List<String> caseIds, User user) {
        List<CaseInfo> caseInfos = caseInfoRepository.findAll(caseIds);
        List<CaseTurnRecord> caseTurnRecords = new ArrayList<>();
        List<CaseInfo> caseInfoResults = new ArrayList<>();
        for (int i = 0; i < caseInfos.size(); i++) {
            CaseInfo caseInfo = caseInfos.get(i);
            setAttribute(caseInfo, user, user);
            caseInfo.setCollectionType(CaseInfo.CollectionType.VISIT.getValue());
            caseInfo.setCaseType(CaseInfo.CaseType.OUTSMALLTURN.getValue());
            caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.VISIT_PASS.getValue());
            caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
            caseInfoResults.add(caseInfo);
            CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
            BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
            caseTurnRecord.setId(null); //主键置空
            caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
            caseTurnRecord.setDepartId(user.getDepartment().getId()); //部门ID
            caseTurnRecord.setReceiveUserRealName(user.getRealName()); //接受人名称
            caseTurnRecord.setReceiveDeptName(user.getDepartment().getName()); //接收部门名称
            caseTurnRecord.setCirculationType(2);
            caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
            caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseTurnRecords.add(caseTurnRecord);
        }
        caseInfoRepository.save(caseInfoResults);
        caseTurnRecordRepository.save(caseTurnRecords);
    }

    public void turnCaseDistribution(BatchDistributeModel batchDistributeModel, User tokenUser) {
        List<BatchInfoModel> batchInfoModels = batchDistributeModel.getBatchInfoModelList();
        List<String> caseIds = batchDistributeModel.getCaseIds();
        List<CaseInfo> caseInfos = new ArrayList<>();
        List<CaseTurnRecord> caseTurnRecords = new ArrayList<>();
        for (BatchInfoModel batchInfoModel : batchInfoModels) {
            Integer caseCount = batchInfoModel.getDistributionCount(); //分配案件数
            if (0 == caseCount) {
                continue;
            }
            for (int i = 0; i < caseCount; i++) {
                CaseInfo caseInfo = caseInfoRepository.findOne(caseIds.get(i)); //获得案件信息
                User user = batchInfoModel.getCollectionUser();
                if (!Objects.equals(user.getType(), User.Type.VISIT.getValue())) {
                    throw new RuntimeException("外访案件不能分配给非外访人员");
                }
                setAttribute(caseInfo, user, tokenUser);
//                caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //流转类型-案件分配
                caseInfo.setExceptionFlag(0);// 分案的时候改变异常类型
                caseInfos.add(caseInfo);
                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                caseTurnRecord.setId(null); //主键置空
                caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                caseTurnRecord.setDepartId(user.getDepartment().getId()); //部门ID
                caseTurnRecord.setReceiveUserRealName(user.getRealName()); //接受人名称
                caseTurnRecord.setReceiveDeptName(user.getDepartment().getName()); //接收部门名称
                caseTurnRecord.setCirculationType(2);
                caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
                caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                caseTurnRecords.add(caseTurnRecord);
            }
        }
        caseInfoRepository.save(caseInfos);
        caseTurnRecordRepository.save(caseTurnRecords);
    }

    public void receiveCaseAssist(String id, User user) {
        synchronized (this) {
            CaseAssist caseAssist = caseAssistRepository.findOne(id);
            //更改协催案件信息
            if (!Objects.equals(caseAssist.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue())) {
                throw new RuntimeException("该协催已被他人抢走");
            }
            caseAssist.setDepartId(user.getDepartment().getId()); //协催部门ID
            caseAssist.setAssistCollector(user); //协催员
            caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue()); //协催状态 待催收
            caseAssist.setOperator(user); //操作员
            caseAssist.setCaseFlowinTime(ZWDateUtil.getNowDateTime()); //流入时间
            caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseAssist.setHoldDays(0); //持案天数归0
            caseAssist.setLeaveCaseFlag(0);
            caseAssist.setMarkId(CaseInfo.Color.NO_COLOR.getValue()); //颜色 无色
            //更改案件信息
            CaseInfo caseInfo = caseAssist.getCaseId();
            caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue()); //协催状态
            caseInfo.setAssistCollector(user); //协催员
            caseAssist.setCaseId(caseInfo);
            caseAssistRepository.save(caseAssist);
        }
    }

    /**
     * 获取强制流转案件
     *
     * @param companyCode
     * @return
     */
   /* public List<CaseInfo> getForceTurnCase(String companyCode) {
        List<CaseInfo> caseInfoList = new ArrayList<>();
        QSysParam qSysParam = QSysParam.sysParam;
        //电催案件
        SysParam phoneFlowBigDaysRemind = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_PHNOEFLOW_BIGDAYSREMIND))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
       *//* SysParam phoneFlowBigDays = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_PHNOEFLOW_BIGDAYS))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));*//*
        if (Objects.nonNull(phoneFlowBigDaysRemind) && Objects.nonNull(phoneFlowBigDays)) {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qCaseInfo.holdDays.between(Integer.valueOf(phoneFlowBigDays.getValue()) - Integer.valueOf(phoneFlowBigDaysRemind.getValue()),
                    Integer.valueOf(phoneFlowBigDays.getValue())).
                    and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                    and(qCaseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())).
                    and(qCaseInfo.companyCode.eq(companyCode)).
                    and(qCaseInfo.currentCollector.isNotNull()).
                    and(qCaseInfo.leaveCaseFlag.ne(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())));
            caseInfoList.addAll(IterableUtils.toList(caseInfoRepository.findAll(builder)));
        }
        //外访案件
        SysParam outBoundBigDaysRemind = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_OUTBOUNDFLOW_BIGDAYSREMIND))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
        SysParam outBoundBigDays = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_OUTBOUNDFLOW_BIGDAYS))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
        if (Objects.nonNull(outBoundBigDaysRemind) && Objects.nonNull(outBoundBigDays)) {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qCaseInfo.holdDays.between(Integer.valueOf(outBoundBigDays.getValue()) - Integer.valueOf(outBoundBigDaysRemind.getValue()),
                    Integer.valueOf(outBoundBigDays.getValue())).
                    and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                    and(qCaseInfo.collectionType.eq(CaseInfo.CollectionType.VISIT.getValue())).
                    and(qCaseInfo.companyCode.eq(companyCode)).
                    and(qCaseInfo.currentCollector.isNotNull()).
                    and(qCaseInfo.leaveCaseFlag.ne(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())));
            caseInfoList.addAll(IterableUtils.toList(caseInfoRepository.findAll(builder)));
        }

        return caseInfoList;
    }*/


    /**
     * 获取若干天无进展案件
     *
     * @param companyCode
     * @return
     */
    public List<CaseInfo> getNowhereCase(String companyCode) {
        List<CaseInfo> caseInfoList = new ArrayList<>();
        QSysParam qSysParam = QSysParam.sysParam;
        //电催案件
        SysParam sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_PHONEREMIND_DAYS))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
        if (Objects.nonNull(sysParam)) {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qCaseInfo.followupTime.isNull().
                    and(qCaseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())).
                    and(qCaseInfo.holdDays.gt(Integer.valueOf(sysParam.getValue()))).
                    and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                    and(qCaseInfo.companyCode.eq(companyCode)).
                    and(qCaseInfo.currentCollector.isNotNull()).
                    and(qCaseInfo.leaveCaseFlag.ne(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())));
            caseInfoList.addAll(IterableUtils.toList(caseInfoRepository.findAll(builder)));
        }
        //外访案件
        SysParam sysParam1 = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_OUTREMIND_DAYS))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
        if (Objects.nonNull(sysParam1)) {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qCaseInfo.followupTime.isNull().
                    and(qCaseInfo.collectionType.eq(CaseInfo.CollectionType.VISIT.getValue())).
                    and(qCaseInfo.holdDays.gt(Integer.valueOf(sysParam1.getValue()))).
                    and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())).
                    and(qCaseInfo.companyCode.eq(companyCode)).
                    and(qCaseInfo.currentCollector.isNotNull()).
                    and(qCaseInfo.leaveCaseFlag.ne(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())));
            caseInfoList.addAll(IterableUtils.toList(caseInfoRepository.findAll(builder)));
        }
        return caseInfoList;
    }

    /**
     * 获取若干天无进展的单次协催案件
     *
     * @return
     */
    public List<CaseAssist> getNowhereCaseAssist(String companyCode) {
        List<CaseAssist> caseAssistList = new ArrayList<>();
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                .and(qSysParam.code.eq(Constants.SYS_ASSISTREMIND_DAYS))
                .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
        if (Objects.nonNull(sysParam)) {
            QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and((qCaseAssist.assistStatus.in(28, 117, 118)).
                    and(qCaseAssist.holdDays.gt(Integer.valueOf(sysParam.getValue()))).
                    and(qCaseAssist.assistWay.eq(CaseAssist.AssistWay.ONCE_ASSIST.getValue())).
                    and(qCaseAssist.assistCollector.isNotNull()).
                    and(qCaseAssist.companyCode.eq(companyCode)));
            caseAssistList.addAll(IterableUtils.toList(caseAssistRepository.findAll(builder)));
        }
        return caseAssistList;
    }

    /**
     * @Description 修改案件备注
     */
    public void modifyCaseMemo(ModifyMemoParams modifyMemoParams, User tokenUser) {
        CaseInfo caseId = caseInfoRepository.findOne(modifyMemoParams.getCaseId()); //获取案件信息
        if (Objects.isNull(caseId)) {
            throw new RuntimeException("该案件未找到");
        }
        List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseId.getCaseNumber());
        for (CaseInfo info : byCaseNumber) {
            //保存备注信息
            CaseInfoRemark caseInfoRemark = new CaseInfoRemark();
            caseInfoRemark.setCaseId(info.getId()); //案件ID
            caseInfoRemark.setRemark(modifyMemoParams.getMemo()); //案件备注信息
            caseInfoRemark.setOperatorUserName(tokenUser.getUserName()); //操作人用户名
            caseInfoRemark.setOperatorRealName(tokenUser.getRealName()); //操作人姓名
            caseInfoRemark.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRemark.setCompanyCode(tokenUser.getCompanyCode()); //公司code码
            caseInfoRemarkRepository.save(caseInfoRemark);
        }

        //消息提醒
        if (Objects.nonNull(caseId.getCurrentCollector())) { //当前案件有催收员
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setUserId(caseId.getCurrentCollector().getId());
            sendReminderMessage.setTitle("案件 [" + caseId.getCaseNumber() + "] 的备注信息已修改");
            sendReminderMessage.setContent(caseId.getMemo());
            sendReminderMessage.setType(ReminderType.MEMO_MODIFY);
            sendReminderMessage.setMode(ReminderMode.COMMON); //普通消息
            reminderService.sendReminder(sendReminderMessage);
        }
    }

    /**
     * @Description 案件退案
     */
    public void returnCase(ReturnCaseParams returnCaseParams, User tokenUser) {
        List<String> caseIds = returnCaseParams.getCaseIds();
        if (Objects.isNull(caseIds) || caseIds.isEmpty()) {
            throw new RuntimeException("请选择案件");
        }
        for (String caseId : caseIds) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("所选案件为找到");
            }
            if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue())) {
                throw new RuntimeException("所选案件已结案，不能退案");
            }
            CaseInfoReturn caseInfoReturn = new CaseInfoReturn();
            caseInfoReturn.setCaseId(caseInfo); //案件信息
            caseInfoReturn.setCaseNumber(caseInfo.getCaseNumber());
            caseInfoReturn.setReason(returnCaseParams.getReason()); //退案原因
            caseInfoReturn.setOperator(tokenUser.getUserName()); //操作人用户名
            caseInfoReturn.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoReturnRepository.save(caseInfoReturn);
        }
    }

    /**
     * 内催回收案件移入待分配池
     *
     * @param idList
     * @param user
     */
    public void moveToDistribution(CaseInfoIdList idList, User user) {
        if (Objects.isNull(idList.getIds()) || idList.getIds().isEmpty()) {
            throw new RuntimeException("请选择要移动的案件");
        }
        try {
            BooleanBuilder builder = new BooleanBuilder();
            QCaseInfoReturn qCaseInfoReturn = QCaseInfoReturn.caseInfoReturn;
            builder.and(qCaseInfoReturn.id.in(idList.getIds()));
            Iterable<CaseInfoReturn> all = caseInfoReturnRepository.findAll(builder);
            Iterator<CaseInfoReturn> iterator = all.iterator();
            List<CaseAssist> caseAssistList = new ArrayList<>();
            while (iterator.hasNext()) {
                CaseInfoReturn caseInfoReturn = iterator.next();
                CaseInfo next = caseInfoReturn.getCaseId();
                next.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());//未回收
                next.setCollectionType(null);//催收类型
                next.setCaseType(null);//案件类型
                next.setDepartment(null);//部门
                next.setLatelyCollector(null);//上个催收员
                next.setCurrentCollector(null);
                next.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());//待分配
                //处理协催案件
                if (Objects.equals(next.getAssistFlag(), 1)) { //协催标识
                    //结束协催案件
                    CaseAssist one = caseAssistRepository.findOne(QCaseAssist.caseAssist.caseId.eq(caseInfo)
                            .and(QCaseAssist.caseAssist.assistStatus.notIn(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                    if (Objects.nonNull(one)) {
                        one.setAssistCloseFlag(0); //手动结束
                        one.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催结束
                        one.setOperator(user);
                        one.setOperatorTime(new Date());
                        one.setCaseFlowinTime(new Date()); //流入时间
                        caseAssistList.add(one);
                    }
                    next.setAssistFlag(0); //协催标识置0
                    next.setAssistStatus(null);//协催状态置空
                    next.setAssistWay(null);
                    next.setAssistCollector(null);
                }
                next.setFollowUpNum(0);
                next.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件打标-无色
                next.setFollowupBack(null); //催收反馈置空
                next.setFollowupTime(null);//跟进时间置空
                next.setPromiseAmt(new BigDecimal(0));//承诺还款置0
                next.setPromiseTime(null);//承诺还款日期置空
                next.setCirculationStatus(null);//小流转状态
                next.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标识
                next.setLeaveDate(null);//留案操作日期
                next.setHasLeaveDays(0);//留案天数
                next.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());//是否挂起
                next.setOperator(user);
                next.setOperatorTime(ZWDateUtil.getNowDateTime());
                caseInfoRepository.save(next);
                caseInfoReturnRepository.delete(caseInfoReturn);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("移动失败!");
        }
    }

    /**
     * @Description 更新案件信息，流转记录
     */
    public void updateCase(CaseInfo caseInfo, User user) throws Exception {
        //案件类型
//        caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //流转类型-案件分配
        caseInfo.setCaseFollowInTime(new Date()); //案件流入时间
        caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1);//流转次数
        caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件打标-无色
        caseInfo.setFollowupBack(null); //催收反馈置空
        caseInfo.setFollowupTime(null);//跟进时间置空
        caseInfo.setPromiseAmt(new BigDecimal(0));//承诺还款置0
        caseInfo.setPromiseTime(null);//承诺还款日期置空
        caseInfo.setCirculationStatus(null);//小流转状态
        caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标识
        caseInfo.setLeaveDate(null);//留案操作日期
        caseInfo.setHasLeaveDays(0);//留案天数
        caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());//是否挂起
        caseInfo.setOperator(user);
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
        //案件流转记录
        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
        caseTurnRecord.setId(null); //主键置空
        caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
        caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
        if (Objects.nonNull(caseInfo.getCurrentCollector())) {
            caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
            caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接收人ID
            caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
        } else {
            caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
        }
        caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
        caseInfoRepository.saveAndFlush(caseInfo);
        caseTurnRecordRepository.saveAndFlush(caseTurnRecord);
    }

    /**
     * 内催 策略分配
     *
     * @param caseStrategies 全部的策略
     * @param caseInfos      全部的案件
     */
    @Async
    public CaseInfoInnerStrategyResultModel innerStrategyDistribute(List<CaseStrategy> caseStrategies, List<CaseInfoDistributedModel> caseInfos, User user) throws Exception {
        CaseInfoInnerStrategyResultModel caseInfoInnerStrategyResultModel = new CaseInfoInnerStrategyResultModel();
        List<CaseInfoInnerDistributeModel> infoInnerDistributeUserModels = new ArrayList<>();
        List<CaseInfoInnerDistributeModel> infoInnerDistributeDepartModels = new ArrayList<>();
        for (CaseStrategy caseStrategy : caseStrategies) {

            List<CaseInfoDistributedModel> checkedList = new ArrayList<>(); // 策略匹配到的案件
            KieSession kieSession = null;
            try {
                kieSession = runCaseStrategyService.runCaseRule(checkedList, caseStrategy, Constants.CASE_INFO_DISTRIBUTE_RULE);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
            List<CaseInfoDistributedModel> caseInfoList = caseInfos;
            Iterator<CaseInfoDistributedModel> iterator = caseInfoList.iterator();
            if (StringUtils.isNotBlank(caseStrategy.getStrategyText())) {
                if (caseStrategy.getStrategyText().contains(Constants.STRATEGY_AREA_ID)) {
                    while (iterator.hasNext()) {
                        CaseInfoDistributedModel next = iterator.next();
                        if (Objects.isNull(next.getCity())) {
                            iterator.remove();
                        }
                    }
                }
                if (caseStrategy.getStrategyText().contains(Constants.STRATEGY_PRODUCT_SERIES)) {
                    while (iterator.hasNext()) {
                        CaseInfoDistributedModel next = iterator.next();
                        if (Objects.isNull(next.getSeriesName())) {
                            iterator.remove();
                        }
                    }
                }
                for (CaseInfoDistributedModel caseInfo : caseInfoList) {
                    kieSession.insert(caseInfo);//插入
                    kieSession.fireAllRules();//执行规则
                }
                kieSession.dispose();
                if (checkedList.isEmpty()) {
                    continue;
                }
                List<String> ids = new ArrayList<>();
                List<Integer> caseNumList = new ArrayList<>();
                checkedList.forEach(e -> ids.add(e.getCaseId()));
                AccCaseInfoDisModel accCaseInfoDisModel = new AccCaseInfoDisModel();
                accCaseInfoDisModel.setCaseIdList(ids);
                accCaseInfoDisModel.setCaseNumList(caseNumList);
                accCaseInfoDisModel.setDepIdList(caseStrategy.getDepartments());
                accCaseInfoDisModel.setIsDebt(0);
                accCaseInfoDisModel.setIsNumAvg(1);
                accCaseInfoDisModel.setDisType(caseStrategy.getAssignType());
                accCaseInfoDisModel.setUserIdList(caseStrategy.getUsers());
                distributeCeaseInfo(accCaseInfoDisModel, user, true);
                List<CaseInfoInnerDistributeModel> caseInfoInnerDistributeModelTemp = distributePreview(accCaseInfoDisModel, user).getList();
                caseInfos.removeAll(checkedList);
                if (accCaseInfoDisModel.getDisType() == 0) { //分配到机构
                    infoInnerDistributeDepartModels.addAll(caseInfoInnerDistributeModelTemp);
                } else {
                    infoInnerDistributeUserModels.addAll(caseInfoInnerDistributeModelTemp);
                }
                caseInfos.removeAll(checkedList);
            }
            //生成策略分配结果
            if (!infoInnerDistributeDepartModels.isEmpty()) {
                List<CaseInfoInnerDistributeModel> newDistributeModel = new ArrayList<>();
                setModelValue(infoInnerDistributeDepartModels, newDistributeModel);
                caseInfoInnerStrategyResultModel.setInnerDistributeDepartModelList(newDistributeModel);
            }
            if (!infoInnerDistributeUserModels.isEmpty()) {
                List<CaseInfoInnerDistributeModel> newDistributeModel = new ArrayList<>();
                setModelValue(infoInnerDistributeUserModels, newDistributeModel);
                caseInfoInnerStrategyResultModel.setInnerDistributeUserModelList(newDistributeModel);
            }
        }
        return caseInfoInnerStrategyResultModel;
    }

    public List<CaseInfoDistributedModel> findModelList(List<Object[]> objects, List<CaseInfoDistributedModel> all) throws ParseException {

        for (int i = 0; i < objects.size(); i++) {
            Object[] obj = objects.get(i);
            CaseInfoDistributedModel distributedModel = new CaseInfoDistributedModel();
            if (Objects.nonNull(objects.get(i))) {
                distributedModel.setCaseId(Objects.isNull(obj[0]) ? null : obj[0].toString());
                distributedModel.setCity(Objects.isNull(obj[1]) ? null : Integer.valueOf(obj[1].toString()));
                distributedModel.setOverdueAmount(Objects.isNull(obj[2]) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[2].toString())));
                distributedModel.setOverdueDays(Objects.isNull(obj[3]) ? null : Integer.valueOf(obj[3].toString()));
                distributedModel.setPrincipalName(Objects.isNull(obj[4]) ? null : obj[4].toString());
                distributedModel.setBatchNumber(Objects.isNull(obj[5]) ? null : obj[5].toString());
                distributedModel.setSeriesName(Objects.isNull(obj[6]) ? null : obj[6].toString());
                //distributedModel.setOverduePeriods(Objects.isNull(obj[7]) ? null : Integer.valueOf(obj[7].toString()));
                distributedModel.setProvince(Objects.isNull(obj[7]) ? null : Integer.valueOf(obj[7].toString()));
                distributedModel.setOverduePeriods(Objects.isNull(obj[8]) ? null : Integer.valueOf(obj[8].toString()));
                distributedModel.setCaseNumber(Objects.isNull(obj[9]) ? null : obj[9].toString());
                distributedModel.setIdCard(Objects.isNull(obj[10]) ? null : obj[10].toString());
                distributedModel.setPayStatus(Objects.isNull(obj[11]) ? null : obj[11].toString());
                distributedModel.setScore(Objects.isNull(obj[12]) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[12].toString())));
                distributedModel.setDelegationDate(Objects.isNull(obj[13]) ? null : ZWDateUtil.getUtilDate(obj[13].toString(), "yyyy-MM-dd"));
                distributedModel.setCloseDate(Objects.isNull(obj[14]) ? null : ZWDateUtil.getUtilDate(obj[14].toString(), "yyyy-MM-dd"));
                distributedModel.setPhone(Objects.isNull(obj[15]) ? null : obj[15].toString());
                distributedModel.setPersonalName(Objects.isNull(obj[16]) ? null : obj[16].toString());
                distributedModel.setCommissionRate(Objects.isNull(obj[17]) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[17].toString())));
                distributedModel.setProductName(Objects.isNull(obj[18]) ? null : obj[18].toString());
                distributedModel.setContractAmount(Objects.isNull(obj[19]) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(obj[19].toString())));
                if (obj.length == 21) {
                    distributedModel.setOutSourceName(Objects.isNull(obj[20]) ? null : obj[20].toString());
                }
                all.add(distributedModel);
            }
        }
        return all;
    }

    /**
     * 给策略分配的返回结果赋值
     *
     * @param infoInnerDistributeDepartModels
     * @param newDistributeModel
     */
    private void setModelValue(List<CaseInfoInnerDistributeModel> infoInnerDistributeDepartModels, List<CaseInfoInnerDistributeModel> newDistributeModel) {
        for (CaseInfoInnerDistributeModel oldDistributeModel : infoInnerDistributeDepartModels) {
            boolean state = false;
            for (CaseInfoInnerDistributeModel newDistributeModelTemp : newDistributeModel) {
                if (newDistributeModelTemp.getUserName().equals(oldDistributeModel.getUserName())) {
                    //当前
                    Integer caseCurrentCount = newDistributeModelTemp.getCaseCurrentCount();
                    caseCurrentCount += oldDistributeModel.getCaseCurrentCount();
                    newDistributeModelTemp.setCaseCurrentCount(caseCurrentCount);
                    BigDecimal caseMoneyCurrentCount = newDistributeModelTemp.getCaseMoneyCurrentCount();
                    caseMoneyCurrentCount = caseMoneyCurrentCount.add(oldDistributeModel.getCaseMoneyCurrentCount());
                    newDistributeModelTemp.setCaseMoneyCurrentCount(caseMoneyCurrentCount);
                    //刚才分配的
                    Integer caseDistributeCount = newDistributeModelTemp.getCaseDistributeCount();
                    caseDistributeCount += oldDistributeModel.getCaseDistributeCount();
                    newDistributeModelTemp.setCaseDistributeCount(caseDistributeCount);
                    BigDecimal caseDistributeMoneyCount = newDistributeModelTemp.getCaseDistributeMoneyCount();
                    caseDistributeMoneyCount = caseDistributeMoneyCount.add(oldDistributeModel.getCaseDistributeMoneyCount());
                    newDistributeModelTemp.setCaseDistributeMoneyCount(caseDistributeMoneyCount);
                    //最后的
                    Integer caseTotalCount = newDistributeModelTemp.getCaseTotalCount();
                    caseTotalCount += oldDistributeModel.getCaseTotalCount();
                    newDistributeModelTemp.setCaseTotalCount(caseTotalCount);
                    BigDecimal caseMoneyTotalCount = newDistributeModelTemp.getCaseMoneyTotalCount();
                    caseMoneyTotalCount = caseMoneyTotalCount.add(oldDistributeModel.getCaseMoneyTotalCount());
                    newDistributeModelTemp.setCaseMoneyTotalCount(caseMoneyTotalCount);
                    state = true;
                }
            }
            if (!state) {
                newDistributeModel.add(oldDistributeModel);
            }
        }
    }


    /**
     * @Description 计算共债案件数量
     */
    public CommonCaseCountModel getCommonCaseCount(String caseId, User user) {
        CaseInfo caseInfo = caseInfoRepository.findOne(caseId); //获取案件信息
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
        CommonCaseCountModel commonCaseCountModel = new CommonCaseCountModel();
        if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue())) { //案件催收状态为已结案
            commonCaseCountModel.setCount(0);
            return commonCaseCountModel;
        }
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qCaseInfo.personalInfo.idCard.eq(caseInfo.getPersonalInfo().getIdCard()));
        booleanBuilder.and(qCaseInfo.personalInfo.name.eq(caseInfo.getPersonalInfo().getName()));
        booleanBuilder.and(qCaseInfo.collectionStatus.notIn(CaseInfo.CollectionStatus.CASE_OVER.getValue(), CaseInfo.CollectionStatus.CASE_OUT.getValue()));
        if (Objects.nonNull(user.getCompanyCode())) {
            booleanBuilder.and(qCaseInfo.companyCode.eq(user.getCompanyCode()));
        }
        //计算共债案件数量
        long count = caseInfoRepository.count(booleanBuilder);
        commonCaseCountModel.setCount((int) count - 1);
        return commonCaseCountModel;
    }

    /**
     * @Descirption 案件到期提醒
     */
    public CommonCaseCountModel maturityRemind(String caseId, User tokenUser) {
        CommonCaseCountModel commonCaseCountModel = new CommonCaseCountModel();
        CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
        if (Objects.isNull(caseInfo)) {
            throw new RuntimeException("该案件未找到");
        }
        long day = caseInfo.getCloseDate().getTime() - new Date().getTime();
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParam = sysParamRepository.findOne(qSysParam.code.eq("SysParam.caseinfo.remind").
                and(qSysParam.companyCode.eq(tokenUser.getCompanyCode())).
                and(qSysParam.type.eq("9001")));
        if (Objects.nonNull(sysParam)) {
            long value = Long.valueOf(sysParam.getValue());
            commonCaseCountModel.setFlag(day <= value * 86400000);
            return commonCaseCountModel;
        } else {
            commonCaseCountModel.setFlag(false);
            return commonCaseCountModel;
        }
    }

    /**
     * @Description 一键审批提前流转
     */
    public void approveAllAdvanceTurn(Integer result, Integer flag, String remark, User tokenUser) throws Exception {
        //result 审批结果 0-通过 1-拒绝
        //flag 类型 0-电催 1-外访
        QCaseAdvanceTurnApplay qCaseAdvanceTurnApplay = QCaseAdvanceTurnApplay.caseAdvanceTurnApplay;
        Iterable<CaseAdvanceTurnApplay> caseAdvanceTurnApplays;
        if (Objects.equals(0, flag)) { //电催审批
            //获取所有未审批的电催提前流转申请
            caseAdvanceTurnApplays = caseAdvanceTurnApplayRepository.findAll(qCaseAdvanceTurnApplay.deptCode.startsWith(tokenUser.getDepartment().getCode()). //权限控制
                    and(qCaseAdvanceTurnApplay.collectionType.eq(0)). //电催
                    and(qCaseAdvanceTurnApplay.companyCode.eq(tokenUser.getCompanyCode())). //公司code码
                    and(qCaseAdvanceTurnApplay.approveResult.eq(CaseAdvanceTurnApplay.CirculationStatus.PHONE_WAITING.getValue()))); //213-待审批
        } else { //外访审批
            //获取所有未审批的外访提前流转申请
            caseAdvanceTurnApplays = caseAdvanceTurnApplayRepository.findAll(qCaseAdvanceTurnApplay.deptCode.startsWith(tokenUser.getDepartment().getCode()). //权限控制
                    and(qCaseAdvanceTurnApplay.collectionType.eq(1)). //电催
                    and(qCaseAdvanceTurnApplay.companyCode.eq(tokenUser.getCompanyCode())). //公司code码
                    and(qCaseAdvanceTurnApplay.approveResult.eq(CaseAdvanceTurnApplay.CirculationStatus.PHONE_WAITING.getValue()))); //213-待审批
        }
        if (!caseAdvanceTurnApplays.iterator().hasNext()) {
            throw new GeneralException("没有审批");
        }
        List<CaseAdvanceTurnApplay> caseAdvanceTurnApplayList = IterableUtils.toList(caseAdvanceTurnApplays);
        for (CaseAdvanceTurnApplay caseAdvanceTurnApplay : caseAdvanceTurnApplayList) {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseAdvanceTurnApplay.getCaseId());
            if (Objects.isNull(caseInfo)) {
                throw new GeneralException("案件信息未找到");
            }
            String userIdForRemind = caseInfo.getCurrentCollector().getId();
            if (Objects.equals(0, result)) { //通过
                if (Objects.equals(0, flag)) {
                    caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.PHONE_PASS.getValue()); //198-电催流转通过
                    if (Objects.equals(caseInfo.getAssistFlag(), 1)) { //有协催标志
                        if (Objects.equals(caseInfo.getAssistStatus(), CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue())) { //有协催申请
                            CaseAssistApply caseAssistApply = getCaseAssistApply(caseInfo.getId(), tokenUser, "流转强制拒绝", CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                            caseAssistApplyRepository.saveAndFlush(caseAssistApply);
                        } else { //有协催案件
                            QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
                            CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                                    and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                            if (Objects.isNull(caseAssist)) {
                                throw new RuntimeException("协催案件未找到");
                            }
                            caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue()); //协催状态 29-协催完成
                            caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                            caseAssist.setOperator(tokenUser); //操作员
                            caseAssistRepository.saveAndFlush(caseAssist);

                            //协催结束新增一条流转记录
                            CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                            BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                            caseTurnRecord.setId(null); //主键置空
                            caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                            caseTurnRecord.setDepartId(caseInfo.getCurrentCollector().getDepartment().getId()); //部门ID
                            caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                            caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
                            caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接受人ID
                            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
                            caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                            caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员
                            caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                            caseTurnRecordRepository.saveAndFlush(caseTurnRecord);
                        }
                    }
                    //更新原案件状态
                    caseInfo.setCaseType(CaseInfo.CaseType.PHNONEFAHEADTURN.getValue());
                } else {
                    caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.VISIT_PASS.getValue()); //201-外访流转通过
                    caseInfo.setCaseType(CaseInfo.CaseType.OUTFAHEADTURN.getValue());
                }
                //更新原案件
                caseInfo.setAssistCollector(null); //协催员置空
                caseInfo.setAssistWay(null); //协催方式置空
                caseInfo.setAssistFlag(0); //协催标识 0-否
                caseInfo.setAssistStatus(null); //协催状态置空
                caseInfo.setFollowupBack(null); //催收反馈置空
                caseInfo.setFollowupTime(null); //跟进时间置空
                caseInfo.setPromiseAmt(null); //承诺还款金额置空
                caseInfo.setPromiseTime(null); //承诺还款时间置空
                caseInfo.setLatelyCollector(caseInfo.getCurrentCollector()); //上一个催收员变为当前催收员
                caseInfo.setCurrentCollector(null); //当前催收员变为审批人
                caseInfo.setHoldDays(0); //持案天数归0
                caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件标记为无色
                caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1); //流转次数加一
                caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //流入时间
                caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态 20-待催收
                caseInfo.setLeaveCaseFlag(0); //留案标识置0

                //通过后添加一条流转记录 提前流转 审批不增加流转记录 祁吉贵 代码已删除
                caseAdvanceTurnApplay.setApproveResult(CaseAdvanceTurnApplay.CirculationStatus.PHONE_PASS.getValue());//通过
            } else { //拒绝
                if (Objects.equals(flag, 0)) {
                    caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.PHONE_REFUSE.getValue()); //199-电催流转拒绝
                } else {
                    caseInfo.setCirculationStatus(CaseInfo.CirculationStatus.VISIT_REFUSE.getValue()); //202-外访流转拒绝
                }
//                caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //案件类型恢复为193-案件分配
                caseAdvanceTurnApplay.setApproveResult(CaseAdvanceTurnApplay.CirculationStatus.PHONE_REFUSE.getValue());//拒绝
            }
            caseAdvanceTurnApplay.setApproveRealName(tokenUser.getRealName());
            caseAdvanceTurnApplay.setApproveDatetime(ZWDateUtil.getNowDateTime());
            caseAdvanceTurnApplay.setApproveUserName(tokenUser.getUserName());
            caseAdvanceTurnApplay.setApproveMemo(remark);
            caseAdvanceTurnApplayRepository.save(caseAdvanceTurnApplay);
            caseInfo.setOperator(tokenUser); //操作人
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRepository.saveAndFlush(caseInfo);

            //消息提醒
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setUserId(userIdForRemind);
            sendReminderMessage.setTitle("客户 [" + caseInfo.getPersonalInfo().getName() + "] 的案件提前流转申请" + (result == 0 ? "已通过" : "被拒绝"));
            sendReminderMessage.setContent(caseAdvanceTurnApplay.getApproveMemo());
            sendReminderMessage.setType(ReminderType.CIRCULATION);
            reminderService.sendReminder(sendReminderMessage);
        }
    }

    /**
     * @Description 撤销分案
     */
    @Transactional
    public void revokeCaseDistribute(String batchNumber, User user) throws GeneralException {
        //判断时间 需求 超过系统设置的时间不能撤销
        SysParam sysParam = getRevertCaseTime(user);
        Iterable<CaseInfo> caseInfoRepositoryAll = caseInfoRepository.findAll(QCaseInfo.caseInfo.batchNumber.eq(batchNumber).
                and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode())).
                and(QCaseInfo.caseInfo.collectionStatus.notIn(CaseInfo.CollectionStatus.CASE_OVER.getValue(), CaseInfo.CollectionStatus.CASE_OUT.getValue())).
                and(QCaseInfo.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue())).
                and(QCaseInfo.caseInfo.collectionType.in(CaseInfo.CollectionType.COMPLEX.getValue(), CaseInfo.CollectionType.TEL.getValue(), CaseInfo.CollectionType.VISIT.getValue())), new Sort(Sort.Direction.ASC, "operatorTime"));
        if (!caseInfoRepositoryAll.iterator().hasNext()) {
            throw new GeneralException("没有查询到撤销案件信息");
        }
        if (caseInfoRepositoryAll.iterator().next().getCollectionStatus() != CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()) {
            throw new GeneralException("该批次中有案件已操作,不能撤回");
        }
        if (DateTime.now().isAfter(caseInfoRepositoryAll.iterator().next().getOperatorTime().getTime() + Integer.parseInt(sysParam.getValue()) * 60000)) {
            throw new GeneralException("撤销案件超出了系统设置的撤回时间");
        }
        updateRevertCaseInfo(caseInfoRepositoryAll);
    }

    /**
     * @Description 撤销分案
     */
    @Transactional
    public void revertCaseInfoDistributeByCaseId(CaseInfoIdList caseInfoIdList, User user) throws GeneralException {
        //判断时间 需求 超过系统设置的时间不能撤销
        SysParam sysParam = getRevertCaseTime(user);
        Iterable<CaseInfo> caseInfoRepositoryAll = caseInfoRepository.findAll(QCaseInfo.caseInfo.id.in(caseInfoIdList.getIds()), new Sort(Sort.Direction.ASC, "operatorTime"));
        if (!caseInfoRepositoryAll.iterator().hasNext()) {
            throw new GeneralException("没有查询到撤销案件信息");
        }
        if (DateTime.now().isAfter(caseInfoRepositoryAll.iterator().next().getOperatorTime().getTime() + Integer.parseInt(sysParam.getValue()) * 60000)) {
            throw new GeneralException("撤销案件超出了系统设置的撤回时间");
        }
        updateRevertCaseInfo(caseInfoRepositoryAll);
    }

    /**
     * 更新撤销的案件信息
     *
     * @param caseInfoRepositoryAll
     */
    private void updateRevertCaseInfo(Iterable<CaseInfo> caseInfoRepositoryAll) {
        for (CaseInfo caseInfo : caseInfoRepositoryAll) {
            caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
            caseInfo.setDepartment(null);
            caseInfo.setCurrentCollector(null);
            caseInfo.setHoldDays(0);
        }
        caseInfoRepository.save(caseInfoRepositoryAll);
    }

    /**
     * 获取系统参数 撤销案件的时长
     *
     * @param user
     * @return
     * @throws GeneralException
     */
    private SysParam getRevertCaseTime(User user) throws GeneralException {
        QSysParam qSysParam = QSysParam.sysParam;
        SysParam sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(user.getCompanyCode()).
                and(qSysParam.code.eq(Constants.SYS_REVOKE_DISTRIBUTE)).
                and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
        if (Objects.isNull(sysParam)) {
            throw new GeneralException("请设置系统参数-案件撤销时长");
        }
        return sysParam;
    }

    public List<DivisionExceptionModel> getDivisionExceptionList(DivisionExceptionRequest divisionExceptionRequest) {
        StringBuilder sql = new StringBuilder("SELECT t.* from\n" +
                "(\n" +
                "SELECT ci.case_number,ci.overdue_amount,ci.overdue_periods,ci.exception_type,ci.exception_check_time,ci.case_follow_in_time,p.`name`,p.certificates_number,p.mobile_no,ci.exception_flag\n" +
                "\tfrom case_info ci LEFT JOIN personal p on ci.personal_id = p.id \n" +
                "UNION \n" +
                "SELECT ci.case_number,ci.overdue_amount,ci.overdue_periods,ci.exception_type,ci.exception_check_time, null,p.`name`,p.certificates_number,p.mobile_no,ci.exception_flag\n" +
                "\tfrom case_info_distributed ci LEFT JOIN personal p on ci.personal_id = p.id \n" +
                ") t where 1=1 and t.exception_flag = 1");

        if (StringUtils.isNotBlank(divisionExceptionRequest.getCaseNumber())) {
            sql.append(" and t.case_number = '").append(divisionExceptionRequest.getCaseNumber()).append("'");
        }
        if (divisionExceptionRequest.getExceptionType() != null) {
            sql.append(" and t.exception_type = ").append(divisionExceptionRequest.getExceptionType());
        }
        if (StringUtils.isNotBlank(divisionExceptionRequest.getExceptionCheckTimeStart())) {
            sql.append(" and t.exception_check_time >= '").append(divisionExceptionRequest.getExceptionCheckTimeStart()).append("'");
        }
        if (StringUtils.isNotBlank(divisionExceptionRequest.getExceptionCheckTimeEnd())) {
            String substring = divisionExceptionRequest.getExceptionCheckTimeEnd().substring(0, 10) + "23:59:59";
            sql.append(" and t.exception_check_time <= '").append(substring).append("'");
        }
        ArrayList<DivisionExceptionModel> divisionExceptionModels = new ArrayList<>();

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        for (Object[] object : resultList) {
            DivisionExceptionModel divisionExceptionModel = new DivisionExceptionModel();
            divisionExceptionModel.setCaseNumber(checkValueIsNull(object[0]));
            divisionExceptionModel.setOverdueAmount(checkBigDecimal(object[1]));
            divisionExceptionModel.setOverduePeriods(checkInteger(object[2]));
            divisionExceptionModel.setExceptionType(checkInteger(object[3]));
            divisionExceptionModel.setExceptionCheckTime(checkDate(object[4]));
            divisionExceptionModel.setCaseFollowInTime(checkDate(object[5]));
            divisionExceptionModel.setCustomerName(checkValueIsNull(object[6]));
            divisionExceptionModel.setCustomerIdCard(checkValueIsNull(object[7]));
            divisionExceptionModel.setCustomerMobileNo(checkValueIsNull(object[8]));
            divisionExceptionModels.add(divisionExceptionModel);
        }
        return divisionExceptionModels;
    }

    private String checkValueIsNull(Object obj) {
        if (Objects.nonNull(obj)) {
            return String.valueOf(obj.equals("null") ? "" : obj);
        } else {
            return null;
        }
    }

    private BigDecimal checkBigDecimal(Object obj) {
        if (Objects.nonNull(obj)) {
            return new BigDecimal(String.valueOf(obj.equals("null") ? "" : obj));
        } else {
            return null;
        }
    }

    private Integer checkInteger(Object obj) {
        if (Objects.nonNull(obj)) {
            return Integer.valueOf(String.valueOf(obj.equals("null") ? "" : obj));
        } else {
            return null;
        }
    }

    private Date checkDate(Object obj) {
        if (Objects.nonNull(obj)) {
            return (Date) obj;
        } else {
            return null;
        }
    }

    /**
     * 分案，流转，留案，协催流程过滤数据
     *
     * @param divisionModel
     * @param caseIdList
     * @return
     */
    public DivisionModel divisionCaseCheck(DivisionModel divisionModel, List<String> caseIdList) {
        String description = "所选案件：";
        StringBuilder leaveCaseNumberBuilder = new StringBuilder();//留案
        StringBuilder assistCaseNumberBuilder = new StringBuilder();//协催
        StringBuilder circulationNumberBuilder = new StringBuilder();//流转
        List<String> divisionCaseIdList = new ArrayList<>();
        for (String caseId : caseIdList) {

            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (Objects.nonNull(caseInfo)) {
                //查询案件是否在审批流程中
                Iterable<FlowApproval> flowApprovals = flowApprovalRepository.findAll(QFlowApproval.flowApproval.caseNumber.eq(caseInfo.getCaseNumber())
                        .and(QFlowApproval.flowApproval.processState.eq(FlowApproval.ProcessState.PROCESS_STATE_NORMAL.getValue())));
                List<FlowApproval> flowApprovalList = IterableUtils.toList(flowApprovals);
                /**
                 * 判断审批流程中案件,（同一案件留案和协催可以同时存在）
                 */
                if (flowApprovalList != null && !flowApprovalList.isEmpty()) {
                    for (FlowApproval flowApproval : flowApprovalList) {
                        //获取审批链类型
                        Integer taskType = flowTaskRepository.getFlowTaskTypeByNodeId(flowApproval.getNodeId());
                        //分案和流转
                        if (divisionModel.getType() == 1 || divisionModel.getType() == 2) {
                            if (taskType.equals(FlowTask.TaskType.Leave_case.getValue())) {//留案
                                leaveCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else if (taskType.equals(FlowTask.TaskType.Assist_case.getValue())) {//协催
                                assistCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else if (taskType.equals(FlowTask.TaskType.Circulation.getValue())) {//流转
                                circulationNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else {
                                divisionCaseIdList.add(caseInfo.getId()); //其他申请流程不限制分案
                            }
                        }
                        //协催
                        if (divisionModel.getType() == 3) {
                            if (taskType.equals(FlowTask.TaskType.Assist_case.getValue())) {//协催
                                assistCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else if (taskType.equals(FlowTask.TaskType.Circulation.getValue())) {//流转
                                circulationNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else {
                                divisionCaseIdList.add(caseInfo.getId());//留案审批中案件
                            }
                        }
                        //留案
                        if (divisionModel.getType() == 4) {
                            if (taskType.equals(FlowTask.TaskType.Leave_case.getValue())) {//留案
                                leaveCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else if (taskType.equals(FlowTask.TaskType.Circulation.getValue())) {//流转
                                circulationNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                            } else {
                                divisionCaseIdList.add(caseInfo.getId());//协催审批中案件
                            }
                        }
                    }

                    /**
                     * 判断非流程中案件
                     */
                } else {
                    //分案和流转
                    if (divisionModel.getType() == 1 || divisionModel.getType() == 2) {
                        if (caseInfo.getLeaveCaseFlag().equals(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())) {
                            leaveCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                        } else if (caseInfo.getAssistFlag().equals(CaseInfo.AssistFlag.YES_ASSIST.getValue())) {
                            assistCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                        } else {
                            divisionCaseIdList.add(caseInfo.getId());
                        }
                    }
                    //协催
                    if (divisionModel.getType() == 3) {
                        if (caseInfo.getAssistFlag().equals(CaseInfo.AssistFlag.YES_ASSIST.getValue())) {
                            assistCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                        } else {
                            divisionCaseIdList.add(caseInfo.getId());
                        }
                    }
                    //留案
                    if (divisionModel.getType() == 4) {
                        if (caseInfo.getLeaveCaseFlag().equals(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())) {
                            leaveCaseNumberBuilder.append(caseInfo.getCaseNumber()).append(",");
                        } else {
                            divisionCaseIdList.add(caseInfo.getId());
                        }
                    }
                }
            }
        }
        //提示语
        if (!isNotEmpty(leaveCaseNumberBuilder) && !isNotEmpty(assistCaseNumberBuilder) && !isNotEmpty(circulationNumberBuilder)) {
            description = "操作成功";
        } else if (divisionModel.getType() == 1 || divisionModel.getType() == 2) {//分案，流转
            if (isNotEmpty(leaveCaseNumberBuilder)) {
                description = description + "申请号为" + leaveCaseNumberBuilder.toString() + "已留案或留案审批流程中；";
            }
            if (isNotEmpty(assistCaseNumberBuilder)) {
                description = description + "申请号为" + assistCaseNumberBuilder.toString() + "在协催中或协催审批流程中；";
            }
            if (isNotEmpty(circulationNumberBuilder)) {
                description = description + "申请号为" + circulationNumberBuilder.toString() + "在流转审批流程中；";
            }
            if (divisionModel.getType() == 1) {
                description = description + "不允许进行“分配/分案”操作！";
            } else {
                description = description + "不允许进行申请流转操作！";
            }

        } else if (divisionModel.getType() == 3) {//协催
            if (isNotEmpty(circulationNumberBuilder)) {
                description = description + "申请号为" + circulationNumberBuilder.toString() + "在流转审批流程中；";
            }
            if (isNotEmpty(assistCaseNumberBuilder)) {
                description = description + "申请号为" + assistCaseNumberBuilder.toString() + "在协催中或协催审批流程中；";
            }
            description = description + "不允许进行申请协催操作！";
        } else if (divisionModel.getType() == 4) {
            if (isNotEmpty(circulationNumberBuilder)) {
                description = description + "申请号为" + circulationNumberBuilder.toString() + "在流转审批流程中；";
            }
            if (isNotEmpty(leaveCaseNumberBuilder)) {
                description = description + "申请号为" + leaveCaseNumberBuilder.toString() + "已留案或留案审批流程中；";
            }
            description = description + "不允许进行申请留案操作！";
        }
        divisionModel.setDescription(description);
        divisionModel.setCaseIdList(divisionCaseIdList);
        return divisionModel;
    }

    public boolean isNotEmpty(StringBuilder builder) {
        if (builder == null) {
            return false;
        } else if (builder.toString().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}