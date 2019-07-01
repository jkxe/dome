package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.model.ReDisRecoverCaseParams;
import cn.fintecher.pangolin.business.model.RecoverCaseParams;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by sunyanping on 2017/9/27.
 */
@Service
public class RecoverCaseService {

    private final Logger logger = LoggerFactory.getLogger(RecoverCaseService.class);

    @Inject
    private CaseInfoRepository caseInfoRepository;
    @Inject
    private CaseInfoReturnRepository caseInfoReturnRepository;
    @Inject
    private CaseAssistRepository caseAssistRepository;
    @Inject
    private OutsourcePoolRepository outsourcePoolRepository;
    @Inject
    private CaseAssistApplyRepository caseAssistApplyRepository;
    @Autowired
    private CaseRecordApplyRepository caseRecordApplyRepository;

    public List<String> recoverCase(RecoverCaseParams recoverCaseParams, User user) {
        List<String> ids = recoverCaseParams.getIds();
        if (recoverCaseParams.getIds().isEmpty()) {
            throw new RuntimeException("请选择要回收的案件!");
        }
        if (StringUtils.isBlank(recoverCaseParams.getReason())) {
            throw new RuntimeException("回收说明不能为空!");
        }
        List<String> list = new ArrayList<>();
        Iterable<CaseRecordApply> all1 = caseRecordApplyRepository.findAll(QCaseRecordApply.caseRecordApply.caseNumber.in(recoverCaseParams.getIds()));
        if (all1.iterator().hasNext()){
            // 去掉
            all1.forEach(obj->{list.add(obj.getCaseNumber());});
        }
        for (int i = 0; i < ids.size(); i++) {
            if (list.contains(ids.get(i))){
                ids.remove(i);
            }
        }
        try {
            Iterable<CaseInfo> all = caseInfoRepository.findAll(QCaseInfo.caseInfo.id.in(ids));
            Iterator<CaseInfo> iterator = all.iterator();
            QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
            QCaseAssistApply qCaseAssistApply = QCaseAssistApply.caseAssistApply;
            while (iterator.hasNext()) {
                CaseInfo caseInfo = iterator.next();
                caseInfo.setOperator(user);
                caseInfo.setOperatorTime(ZWDateUtil.getNowDate());
                caseInfo.setRecoverRemark(CaseInfo.RecoverRemark.RECOVERED.getValue());
                //回收案件不属于任何机构和催收员 祁吉贵
                caseInfo.setDepartment(null);
                caseInfo.setCurrentCollector(null);

                CaseInfoReturn caseInfoReturn = new CaseInfoReturn();
                caseInfoReturn.setCaseId(caseInfo);
                caseInfoReturn.setCaseNumber(caseInfo.getCaseNumber());
                caseInfoReturn.setSource(CaseInfoReturn.Source.INTERNALCOLLECTION.getValue()); //内催
                caseInfoReturn.setReason(recoverCaseParams.getReason());
                caseInfoReturn.setOperator(user.getId());
                caseInfoReturn.setOperatorTime(new Date());
                caseInfoReturn.setCompanyCode(user.getCompanyCode());

                //判断是否有协催申请
                CaseAssistApply caseAssistApply = caseAssistApplyRepository.findOne(qCaseAssistApply.caseId.eq(caseInfo.getId()).
                        and(qCaseAssistApply.approveStatus.in(32, 33, 34)));
                if (Objects.nonNull(caseAssistApply)) {
                    caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.VISIT_COMPLETE.getValue());
                    caseAssistApply.setApproveOutResult(CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
                    caseAssistApplyRepository.save(caseAssistApply);
                }

                //判断是否有协催案件
                CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(caseInfo.getId()).
                        and(qCaseAssist.assistStatus.in(CaseInfo.AssistStatus.ASSIST_COLLECTING.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue())));
                if (Objects.nonNull(caseAssist)) {
                    caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue());
                    caseAssist.setOperator(user);
                    caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime());
                    caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue());
                    caseAssistRepository.save(caseAssist);
                }
                caseInfoReturnRepository.save(caseInfoReturn);
                caseInfoRepository.save(caseInfo);
            }
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("操作失败!");
        }
    }

    public List<String> reDisRecoverCase(ReDisRecoverCaseParams params, User user) {
        List<String> ids = params.getIds();
        if (Objects.isNull(params) || params.getIds().isEmpty()) {
            throw new RuntimeException("请选择重新分配的案件");
        }
        List<String> list = new ArrayList<>();
        Iterable<CaseRecordApply> all1 = caseRecordApplyRepository.findAll(QCaseRecordApply.caseRecordApply.caseNumber.in(params.getIds()));
        if (all1.iterator().hasNext()){
            // 去掉
            all1.forEach(obj->{list.add(obj.getCaseNumber());});
        }
        for (int i = 0; i < ids.size(); i++) {
            if (list.contains(ids.get(i))){
                ids.remove(i);
            }
        }
//        if (Objects.isNull(params) || Objects.isNull(params.getCloseDate())) {
//            throw new RuntimeException("请选择案件到期时间");
//        }
        Iterable<CaseInfoReturn> all = caseInfoReturnRepository.findAll(QCaseInfoReturn.caseInfoReturn.caseNumber.in(ids));
        Iterator<CaseInfoReturn> iterator = all.iterator();
        if (iterator.hasNext()){
            List<CaseInfo> caseInfoList = new ArrayList<>();
            List<CaseAssist> caseAssistList = new ArrayList<>();
            List<CaseInfoReturn> caseInfoReturnList = new ArrayList<>();
            List<OutsourcePool> outsourcePoolList = new ArrayList<>();
            while (iterator.hasNext()) {
                CaseInfoReturn caseInfoReturn = iterator.next();
                Integer source = caseInfoReturn.getSource();
                if (Objects.equals(source, CaseInfoReturn.Source.INTERNALCOLLECTION.getValue())) { // 内催回收的案件
                    CaseInfo caseInfo = caseInfoReturn.getCaseId();
                    if (!caseInfo.getCollectionStatus().equals(CaseInfo.CollectionStatus.CASE_OVER.getValue())){
                        setAttr(caseInfo, caseAssistList, caseInfoList, outsourcePoolList, user, params);
                    }
                    caseInfoReturnList.add(caseInfoReturn);
                }
                if (Objects.equals(source, CaseInfoReturn.Source.OUTSOURCE.getValue())) { // 委外回收的案件
                    CaseInfo caseInfo = caseInfoReturn.getCaseId();
                    if (!caseInfo.getCollectionStatus().equals(CaseInfo.CollectionStatus.CASE_OVER.getValue())) {
                        setAttr(caseInfo, caseAssistList, caseInfoList, outsourcePoolList, user, params);
                    }
                    caseInfoReturnList.add(caseInfoReturn);
                }
            }
            caseAssistRepository.save(caseAssistList);
            caseInfoRepository.save(caseInfoList);
            outsourcePoolRepository.save(outsourcePoolList);
            caseInfoReturnRepository.delete(caseInfoReturnList);
        }
        return list;
    }

    private void setAttr(CaseInfo caseInfo, List<CaseAssist> caseAssistList, List<CaseInfo> caseInfoList, List<OutsourcePool> outsourcePoolList, User user, ReDisRecoverCaseParams params) {
        caseInfo.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());//未回收
        caseInfo.setCollectionType(null);//催收类型
        caseInfo.setCaseType(null);//案件类型
        caseInfo.setDepartment(null);//部门
        caseInfo.setLatelyCollector(null);//上个催收员
        caseInfo.setCurrentCollector(null);
        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());//待分配
        caseInfo.setFollowUpNum(0);
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
        caseInfo.setCompanyCode(user.getCompanyCode());
        if (Objects.equals(params.getType(), 0)) { // 分到内催池
            caseInfo.setCasePoolType(CaseInfo.CasePoolType.INNER.getValue()); // 内催
//            caseInfo.setCloseDate(params.getCloseDate()); // 案件到期日期
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
        } else if (Objects.equals(params.getType(), 1)) { // 分到委外池
            caseInfo.setCasePoolType(CaseInfo.CasePoolType.OUTER.getValue()); // 委外
            OutsourcePool outsourcePool = new OutsourcePool();
            outsourcePool.setCaseInfo(caseInfo);
            outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode()); //待委外
            outsourcePool.setOperateTime(new Date());
            outsourcePool.setOperator(user.getId());
            outsourcePool.setCaseNumber(caseInfo.getCaseNumber());
//            outsourcePool.setOverOutsourceTime(params.getCloseDate()); // 到期时间
            outsourcePool.setOverduePeriods(caseInfo.getPayStatus());
            BigDecimal b2 = caseInfo.getRealPayAmount();//实际还款金额
            if (Objects.isNull(b2)) {
                b2 = BigDecimal.ZERO;
            }
            BigDecimal b1 = caseInfo.getOverdueAmount();//原案件金额
            outsourcePool.setContractAmt(b1.subtract(b2));//委外案件金额=原案件金额-实际还款金额
            outsourcePool.setCompanyCode(user.getCompanyCode());
            outsourcePoolList.add(outsourcePool);
        } else {
            throw new RuntimeException("选择的要分配的目标池未知");
        }
        caseInfoList.add(caseInfo);
    }
}
