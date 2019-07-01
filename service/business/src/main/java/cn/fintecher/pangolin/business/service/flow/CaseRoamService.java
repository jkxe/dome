package cn.fintecher.pangolin.business.service.flow;

import cn.fintecher.pangolin.business.model.ApplyCaseInfoRoamParams;
import cn.fintecher.pangolin.business.model.CaseInfoRoamModel;
import cn.fintecher.pangolin.business.model.CaseTurnModel;
import cn.fintecher.pangolin.business.model.ProcessApprovalModel;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.SaveCaseTurnRecordService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: wangzhao
 * @Description
 * @Date 2018/6/14  14:33
 **/
@Service("CaseRoamService")
public class CaseRoamService {

    final Logger log = LoggerFactory.getLogger(CaseRoamService.class);

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    ProcessBaseService processBaseService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CaseTurnRecordRepository caseTurnRecordRepository;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    CaseRecordApplyRepository caseRecordApplyRepository;

    @Autowired
    AntiFraudRecordRepository antiFraudRecordRepository;

    @Autowired
    LawsuitRecordRepository lawsuitRecordRepository;

    @Autowired
    SaveCaseTurnRecordService saveCaseTurnRecordService;

    @Autowired
    OutsourcePoolRepository outsourcePoolRepository;

    @Autowired
    CaseAssistApplyRepository caseAssistApplyRepository;

    @Autowired
    OutSourceWhipRepository outSourceWhipRepository;

    @Autowired
    CaseInfoReturnRepository caseInfoReturnRepository;
    @Inject
    SysParamRepository sysParamRepository;
    /**
     * 获取当前用户下待审批的流转案件
     *
     * @return
     */
    public List<CaseInfoRoamModel> getCaseInfoRoamList(String taskId, User user) {
        List<CaseInfoRoamModel> list = new ArrayList<>();
        List<FlowApproval> flowApprovalList = taskInfoService.getCaseNumberByUser(user, taskId);
        if (flowApprovalList != null && flowApprovalList.size() != 0) {
            for (FlowApproval flowApproval : flowApprovalList) {
                FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval);
                if (Objects.nonNull(flowNode)) {
                    FlowTask flowTask = processBaseService.getFlowTaskByNode(flowNode);//为了获取任务名称
                    Role role = roleRepository.findOne(flowNode.getRoleId());//为了获取角色名称
                    if (Objects.nonNull(flowTask)) {
                        String caseNumber = flowApproval.getCaseNumber();
                        List<CaseInfo> caseInfoList = caseInfoRepository.findByCaseNumber(caseNumber);
                        if (caseInfoList != null && !caseInfoList.isEmpty()) {
                            CaseInfo caseInfo = caseInfoList.get(0);
                            BooleanBuilder booleanBuilder = new BooleanBuilder();
                            booleanBuilder.and(QCaseRecordApply.caseRecordApply.caseNumber.eq(caseInfo.getCaseNumber()));//案件编号
                            //缺少审批流程id
                            Iterator<CaseRecordApply> iterator = caseRecordApplyRepository.findAll(booleanBuilder).iterator();
                            if (iterator.hasNext()) {
                                CaseRecordApply caseRecordApply = iterator.next();
                                //查询审批表
                                Iterator<CaseTurnRecord> caseTurnRecordIterator = caseTurnRecordRepository.findAll(QCaseTurnRecord.caseTurnRecord.caseNumber.eq(caseRecordApply.getCaseNumber())
                                        .and(QCaseTurnRecord.caseTurnRecord.turnApprovalStatus.eq(CaseTurnRecord.TurnApprovalStatus.WAIT_APPROVAL.getValue()))
                                        .and(QCaseTurnRecord.caseTurnRecord.circulationType.eq(CaseTurnRecord.circulationTypeEnum.APPROVAL.getValue()))).iterator();
                                CaseInfoRoamModel caseInfoRoamModel = new CaseInfoRoamModel();
                                caseInfoRoamModel.setCaseId(caseRecordApply.getCaseId()); //案件id
                                caseInfoRoamModel.setCaseNumber(caseInfo.getCaseNumber());//案件编号
                                caseInfoRoamModel.setBatchNumber(caseInfo.getBatchNumber());//批次号
//                                caseInfoRoamModel.setPrincipalName(caseInfo.getPrincipalId().getName());//委托方名称
                                caseInfoRoamModel.setPersonalName(caseInfo.getPersonalInfo().getName());//客户姓名
                                caseInfoRoamModel.setPersonalMobileNo(caseInfo.getPersonalInfo().getMobileNo());//客户手机号
                                caseInfoRoamModel.setPersonalIdCard(caseInfo.getPersonalInfo().getCertificatesNumber());//客户身份证号码
                                List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseInfo.getCaseNumber());
                                for (int i = 0; i < byCaseNumber.size(); i++) {
                                    if (Objects.nonNull(byCaseNumber.get(i).getOverdueAmount())){
                                        caseInfoRoamModel.setOverdueAmount(caseInfoRoamModel.getOverdueAmount().add(byCaseNumber.get(i).getOverdueAmount()));//逾期总金额
                                    }
                                    if (Objects.nonNull(caseInfoRoamModel.getOverduePeriods()) && Objects.nonNull(byCaseNumber.get(i).getOverduePeriods()) && caseInfoRoamModel.getOverduePeriods() < byCaseNumber.get(i).getOverduePeriods()){
                                        caseInfoRoamModel.setOverduePeriods(byCaseNumber.get(i).getOverduePeriods());//逾期期数
                                    }
                                    if (Objects.nonNull(caseInfoRoamModel.getOverdueDays()) && Objects.nonNull(byCaseNumber.get(i).getOverdueDays()) && caseInfoRoamModel.getOverdueDays() < byCaseNumber.get(i).getOverdueDays()){
                                        caseInfoRoamModel.setOverdueDays(byCaseNumber.get(i).getOverdueDays());//逾期天数
                                    }
                                }
                                caseInfoRoamModel.setFollowupBack(caseInfo.getFollowupBack());//催收反馈
                                caseInfoRoamModel.setCollectionStatus(caseInfo.getCollectionStatus());//催收状态
                                caseInfoRoamModel.setContractAmount(caseInfo.getContractAmount());//合同金额
                                caseInfoRoamModel.setRealPayAmount(caseInfo.getRealPayAmount());//实际还款金额
                                caseInfoRoamModel.setHoldDays(caseInfo.getHoldDays());//持按天数
                                caseInfoRoamModel.setLeftDays(caseInfo.getLeftDays());//剩余天数
                                caseInfoRoamModel.setApprovalId(caseRecordApply.getApprovalId());//审批流程id
                                caseInfoRoamModel.setApplyUser(caseRecordApply.getApplyUser());//申请人
                                caseInfoRoamModel.setApplyTime(caseRecordApply.getApplyTime());//申请时间
                                caseInfoRoamModel.setTaskId(flowTask.getId());//任务id
                                caseInfoRoamModel.setTaskName(flowTask.getTaskName());//任务名称
                                caseInfoRoamModel.setRoleId(role.getId());//角色id
                                caseInfoRoamModel.setRoleName(role.getName());//角色名称
                                caseInfoRoamModel.setCaseRecordApplyId(caseRecordApply.getId());

                                if(caseTurnRecordIterator.hasNext()){
                                    CaseTurnRecord caseTurnRecord = caseTurnRecordIterator.next();

                                    caseInfoRoamModel.setGoalType(Objects.isNull(caseTurnRecord) ? null : caseTurnRecord.getTurnToPool());//流转去向
                                    caseInfoRoamModel.setSourceType(Objects.isNull(caseTurnRecord) ? null : caseTurnRecord.getTurnFromPool());//流转来源
                                    caseInfoRoamModel.setTurnApprovalStatus(Objects.isNull(caseTurnRecord) ? null :caseTurnRecord.getTurnApprovalStatus());//流转审核状态
                                    caseInfoRoamModel.setTurnDescribe(Objects.isNull(caseTurnRecord) ? null : caseTurnRecord.getTurnDescribe());//流转说明
                                    caseInfoRoamModel.setApprovalOpinion(Objects.isNull(caseTurnRecord) ? null : caseTurnRecord.getApprovalOpinion());//审批意见
                                }

                                list.add(caseInfoRoamModel);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 案件流转申请功能
     *
     * @param applyCaseInfoRoamParams
     * @param user
     */
    @Transactional
    public void applyCaseInfoRoam(ApplyCaseInfoRoamParams applyCaseInfoRoamParams, User user) {
        CaseTurnModel caseTurnModel = new CaseTurnModel();
        Iterator<CaseInfo> iterator = caseInfoRepository.findAll(QCaseInfo.caseInfo.id.in(applyCaseInfoRoamParams.getCaseId())).iterator();
        while (iterator.hasNext()) {
            CaseInfo caseInfo = iterator.next();
            if (Objects.isNull(caseInfo)) {
                throw new RuntimeException("该案件未找到");
            }
            //判断催收来源
            Integer turnFromPool = saveCaseTurnRecordService.getTurnFromPool(caseInfo);
            //判断是否申请角色
            if (taskInfoService.existsApply(applyCaseInfoRoamParams.getTaskId(), user)) {
                //判断是否该案件是否正在申请中
                if (!exitApply(caseInfo, applyCaseInfoRoamParams.getPooltype())) {
                    //判断是否申请协催
                    if(noCaseAssistApproval(caseInfo.getCaseNumber())) {
                        if(caseInfo.getAssistFlag() == 0) {
                            String approvalId = taskInfoService.applyCaseInfo(caseInfo.getCaseNumber(), applyCaseInfoRoamParams.getTaskId(), user,applyCaseInfoRoamParams.getTurnDescribe());//流程申请
                            CaseRecordApply caseRecordApply = new CaseRecordApply();
                            caseRecordApply.setCaseId(caseInfo.getId());
                            caseRecordApply.setCaseNumber(caseInfo.getCaseNumber()); // hy-新增字段
                            caseRecordApply.setApprovalId(approvalId); //申请流程id
                            caseRecordApply.setSourceType(turnFromPool);//案件来源
                            caseRecordApply.setGoalType(applyCaseInfoRoamParams.getPooltype());//案件去向
                            caseRecordApply.setApplyUser(user.getRealName());//申请人
                            caseRecordApply.setApplyTime(new Date());//申请时间
                            caseRecordApply.setApprovalStatus(0);//待审批状态
                            caseRecordApplyRepository.save(caseRecordApply);//保存流转案件申请
                            //添加流转记录
                            caseTurnModel.setApprovalId(approvalId);//申请流程id
                            caseTurnModel.setApplyName(user.getRealName());
                            saveTurnRecord(caseInfo, applyCaseInfoRoamParams, caseTurnModel);
                        }else{
                            throw new RuntimeException("该案件是协催案件，请在结束协催后再申请");
                        }
                    }else{
                        throw new RuntimeException("该案件已申请协催，不允许申请流转");
                    }
                } else {
                    throw new RuntimeException("案件编号为:" + caseInfo.getCaseNumber() + "的案件已在流转流程中，不允许再次申请");
                }
            } else {
                throw new RuntimeException("该用户不是案件流转申请的角色");
            }
            continue;
        }
    }
    /**
     * 判断该案件是否在协催状态中
     *
     * @param caseNumber
     * @return
     */
    public boolean noCaseAssistApproval(String caseNumber) {
        //查出所有正在审核的申请
        Iterable<FlowApproval> flowApprovals = processBaseService.getFlowApproavalListByCaseNumber(caseNumber);
        if (flowApprovals.iterator().hasNext()) {
            QCaseAssistApply qCaseAssistApply = QCaseAssistApply.caseAssistApply;
            Iterator<CaseAssistApply> caseAssistApplyIterator = caseAssistApplyRepository.findAll(qCaseAssistApply.caseNumber.eq(caseNumber).and(qCaseAssistApply.approveStatus.eq(CaseAssistApply.ApproveStatus.APPROVAL_PENDING.getValue()))).iterator();
            while (caseAssistApplyIterator.hasNext()) {
                CaseAssistApply caseAssistApply = caseAssistApplyIterator.next();
                if (Objects.equals(caseAssistApply.getApproveStatus(), CaseAssistApply.ApproveStatus.APPROVAL_PENDING.getValue())
                        && Objects.equals(caseAssistApply.getApproveOutResult(), null)) {
                    return false;
                }
            }
        } else {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            Iterable<CaseInfo> caseInfo = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(caseNumber).and(qCaseInfo.assistFlag.eq(CaseInfo.AssistFlag.YES_ASSIST.getValue())));
            if (caseInfo.iterator().hasNext()) {
                return false;
            }
        }
        return true;
    }
    /**
     * 判断该案件是否在流转中
     *
     * @param caseInfo
     * @param pooltype
     * @return
     */
    public boolean exitApply(CaseInfo caseInfo, Integer pooltype) {
        //如果当前案件的池类型和申请的去向相同，则不允许。
        Integer turnToPool = saveCaseTurnRecordService.getTurnToPool(pooltype);
        if (caseInfo.getCasePoolType().equals(turnToPool)) {
            return true;
        }
        return exitRecordApply(caseInfo);
    }
    public boolean exitRecordApply(CaseInfo caseInfo) {

        Iterable<FlowApproval> flowApprovals = processBaseService.getFlowApproavalListByCaseNumber(caseInfo.getCaseNumber());
        if (flowApprovals.iterator().hasNext()) {
            //审批通过后将会在申请表中删除对应的申请记录。
//            List<CaseRecordApply> list = caseRecordApplyRepository.findAllByCaseId(caseInfo.getId());
            Iterable<CaseRecordApply> all = caseRecordApplyRepository.findAll(QCaseRecordApply.caseRecordApply.caseNumber.eq(caseInfo.getCaseNumber()));
            List<CaseRecordApply> list = IteratorUtils.toList(all.iterator());
            if (list != null && list.size() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 案件流转审批
     *
     * @param processApprovalModel
     * @param user
     */
    @Transactional
    public void caseRecordApproval(ProcessApprovalModel processApprovalModel, User user) {
        //流程审批走之前的审批链中的节点
        FlowApproval firstFlowApproval = processBaseService.getFlowApprovalById(processApprovalModel.getApprovalId());
        FlowNode berforeFlowNode = new FlowNode();
        if (Objects.nonNull(firstFlowApproval)) {
            //获取走审批之前的节点信息
            berforeFlowNode = processBaseService.getFlowNodeByApproval(firstFlowApproval);
        }
        if (existApproval(user, berforeFlowNode)) {
            taskInfoService.saveFlowApprovalAndHistory(processApprovalModel.getCaseNumber(), processApprovalModel.getNodeState().toString(),
                    processApprovalModel.getNodeOpinion(), processApprovalModel.getStep(), processApprovalModel.getApprovalId(), user);
            CaseRecordApply caseRecordApply = caseRecordApplyRepository.findOne(processApprovalModel.getCaseRecordApplyId());
            if (Objects.nonNull(caseRecordApply)) {
                caseRecordApply.setApprovalStatus(1);
                caseRecordApplyRepository.save(caseRecordApply);
            }
            FlowApproval flowApproval = processBaseService.getFlowApprovalById(processApprovalModel.getApprovalId());
            if (Objects.nonNull(flowApproval)) {
                //获取走审批之后的节点
                FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval);//下一个节点
                if (flowNode.getId().equals(berforeFlowNode.getId())) { //两者相同则是最后一个节点，否则不是
                    if (!processApprovalModel.getNodeState().equals(FlowHistory.NodeState.REFUSE.getValue())) {
                        recordApplyApproval(processApprovalModel, user);
                    }
                } else {
                    if (processApprovalModel.getNodeState().equals(FlowHistory.NodeState.REJECT.getValue())) {
                        recordApplyApproval(processApprovalModel, user);
                    }
                }
            }
        }
    }


    /**
     * 判断该用户是否是审批人
     *
     * @return
     */
    public boolean existApproval(User user, FlowNode flowNode) {
        Set<Role> set = user.getRoles();
        Iterator<Role> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(flowNode.getRoleId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加流转记录
     *
     * @param caseInfo
     * @param applyCaseInfoRoamParams
     * @param caseTurnModel
     */
    public void saveTurnRecord(CaseInfo caseInfo, ApplyCaseInfoRoamParams applyCaseInfoRoamParams, CaseTurnModel caseTurnModel) {
        //添加流转记录
        if (Objects.nonNull(caseInfo)) {
            //判断催收来源
            Integer turnFromPool = saveCaseTurnRecordService.getTurnFromPool(caseInfo);
            caseTurnModel.setCaseNumber(caseInfo.getCaseNumber());
            caseTurnModel.setTurnFromPool(turnFromPool);//流转来源
            caseTurnModel.setCirculationType(CaseTurnRecord.circulationTypeEnum.APPROVAL.getValue());//申请流转
            caseTurnModel.setTurnToPool(applyCaseInfoRoamParams.getPooltype());//流转去向
            caseTurnModel.setTurnApprovalStatus(CaseTurnRecord.TurnApprovalStatus.WAIT_APPROVAL.getValue());//待审批
            caseTurnModel.setTurnDescribe(applyCaseInfoRoamParams.getTurnDescribe());
            saveCaseTurnRecordService.saveCaseTurningRecord(caseTurnModel);
        }
    }

    /**
     * 案件流转审批业务操作
     * 这边好像是没有关于停催数据添加的操作(巩贺斌)
     *
     * @param processApprovalModel
     * @param user
     */
    public void recordApplyApproval(ProcessApprovalModel processApprovalModel, User user) {
        CaseRecordApply caseRecordApply = caseRecordApplyRepository.findOne(processApprovalModel.getCaseRecordApplyId());
       //流转去向判断案件池类型
        if (Objects.nonNull(caseRecordApply)) {
            Integer poolType = saveCaseTurnRecordService.getTurnToPool(caseRecordApply.getGoalType());
            String caseNumber = caseRecordApply.getCaseNumber();
            List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseNumber);
//            String caseId = caseRecordApply.getCaseId();
//            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (byCaseNumber.size() == 0) {
                throw new RuntimeException("未找到相应的原案件!");
            }
            List<CaseRecordApply> applyList = new ArrayList<>();
            applyList.add(caseRecordApply);
            doFlow(applyList,processApprovalModel.getNodeState(),user);
            //删除申请记录
            caseRecordApplyRepository.delete(caseRecordApply.getId());
            saveCaseTurnRecordService.updateCaseTurningRecord(processApprovalModel, user);
        }
    }

    //案件流转操作
    public void doFlow(List<CaseRecordApply> applyList, Integer nodeState, User user){
        //流转去向判断案件池类型
        for (CaseRecordApply caseRecordApply : applyList) {
            if (Objects.nonNull(caseRecordApply)) {
                Integer poolType = null;
                if (caseRecordApply.getPoolType() != null){
                    poolType = caseRecordApply.getPoolType();
                }else {
                    poolType = saveCaseTurnRecordService.getTurnToPool(caseRecordApply.getGoalType());
                }
                String caseNumber = caseRecordApply.getCaseNumber();
                Iterator<CaseInfo> iterator = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(caseNumber).and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))).iterator();
                List<CaseInfo> byCaseNumber = IteratorUtils.toList(iterator);
//            String caseId = caseRecordApply.getCaseId();
//            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
                if (byCaseNumber.size() == 0) {
                    throw new RuntimeException("未找到相应的原案件!");
                }
                if (nodeState == 0) {//同意
                    // 循环修改caseInfo数据
                    for (CaseInfo caseInfo:byCaseNumber) {
                        caseInfo.setCasePoolType(poolType);//修改对应的案件池类型
                        caseInfo.setHoldDays(null); //持案天数
                        caseInfo.setLeftDays(null); //剩余天数
                        caseInfo.setDepartment(null);
                        caseInfo.setLatelyCollector(caseInfo.getCurrentCollector());
                        caseInfo.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());
                        caseInfo.setCurrentCollector(null);
                        caseInfo.setCollectionType(null);
                        caseInfo.setCaseFollowInTime(new Date());//案件流入时间
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());//待分配
                        if (poolType.equals(CaseInfo.CasePoolType.STOP.getValue())) {
                            //停催时间
                            caseInfo.setStopTime(new Date());
                        }
                        caseInfoRepository.save(caseInfo);

                        if (poolType.equals(CaseInfo.CasePoolType.OUTER.getValue())) {
                            OutsourcePool outsourcePool = new OutsourcePool();
                            outsourcePool.setCaseInfo(caseInfo);//案件信息
                            outsourcePool.setCaseNumber(caseInfo.getCaseNumber());
                            outsourcePool.setOutTime(new Date());//委外时间..
                            outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());//委外状态
                            outsourcePool.setCompanyCode(user.getCompanyCode());//..
                            BigDecimal b2 = caseInfo.getRealPayAmount();//实际还款金额
                            if (Objects.isNull(b2)) {
                                b2 = BigDecimal.ZERO;
                            }
                            BigDecimal b1 = caseInfo.getOverdueAmount();//原案件金额
                            outsourcePool.setContractAmt(b1.subtract(b2));
                            outsourcePool.setOutoperationStatus(null);//委外操作状态
                            outsourcePool.setOperator(user.getId());
                            outsourcePool.setOperateTime(new Date());
                            outsourcePool.setOverduePeriods(caseInfo.getPayStatus());
                            //委外案件待分配时，系统根据对每个产品类型设置回收周期，默认生成案件的回收到期时间。
                            QSysParam qSysParam = QSysParam.sysParam;
                            SysParam sysParam = sysParamRepository.findOne(qSysParam.productSeriesId.eq(caseInfo.getProductType()));
                            int days = Integer.parseInt(sysParam.getValue());
                            Date after = ZWDateUtil.getAfter(new Date(), days, null);
                            outsourcePool.setOverOutsourceTime(after);
                            outsourcePoolRepository.save(outsourcePool);
                        }
                    }
                    //来源是委外
                    if (Objects.equals(caseRecordApply.getSourceType(),CaseTurnRecord.TurnFromPool.OUTER.getValue())) {//委外
                        //委外回收池查询
                        QCaseInfoReturn qCaseInfoReturn =  QCaseInfoReturn.caseInfoReturn;
                        BooleanBuilder booleanBuilder = new BooleanBuilder();
                        booleanBuilder.and(qCaseInfoReturn.caseId.id.eq(caseRecordApply.getCaseId()));
                        booleanBuilder.and(qCaseInfoReturn.source.in(CaseInfoReturn.Source.OUTSOURCE.getValue()));
                        Iterator<CaseInfoReturn> iteratorer =  caseInfoReturnRepository.findAll(booleanBuilder).iterator();
                        while (iteratorer.hasNext()){
                            CaseInfoReturn caseInfoReturn = iteratorer.next();
                            caseInfoReturnRepository.delete(caseInfoReturn);
                        }

                        QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
                        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
                        booleanBuilder1.and(qOutsourcePool.caseInfo.id.in(caseRecordApply.getCaseId()));
                        Iterator<OutsourcePool> iteratoreri1 =  outsourcePoolRepository.findAll(booleanBuilder1).iterator();
                        while (iteratoreri1.hasNext()){
                            OutsourcePool outsourcePool =iteratoreri1.next();
                            outsourcePoolRepository.delete(outsourcePool);
                        }
                    }
                }
            }
        }
    }
}
