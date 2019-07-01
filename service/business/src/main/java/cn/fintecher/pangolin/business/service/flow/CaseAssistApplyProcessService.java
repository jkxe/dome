package cn.fintecher.pangolin.business.service.flow;

import cn.fintecher.pangolin.business.model.AssistApplyApprovaleModel;
import cn.fintecher.pangolin.business.model.AssistApplyProcessMode;
import cn.fintecher.pangolin.business.model.AssistApplyProcessServiceMode;
import cn.fintecher.pangolin.business.model.MapModel;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.AccMapService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ListCompareUtil;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: wangzhao
 * @Description
 * @Date 2018/6/14 14:31
 **/
@Service("CaseAssistApplyProcessService")
public class CaseAssistApplyProcessService {

    final Logger log = LoggerFactory.getLogger(CaseAssistApplyProcessService.class);

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    ProcessBaseService processBaseService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CaseAssistApplyRepository caseAssistApplyRepository;

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private CaseAssistRepository caseAssistRepository;

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    AccMapService accMapService;

    @Autowired
    CaseRecordApplyRepository caseRecordApplyRepository;

    /**
     * 获取（该用户下）待审批的协催案件信息
     *
     * @param taskId
     * @param user
     * @return
     */
    public List<AssistApplyProcessServiceMode> getApplyCaseAssistInfo(String taskId, User user,BooleanBuilder booleanBuilder,String sort) {
        List<AssistApplyProcessServiceMode> list = new ArrayList<>();
        //获取所有的审批流程
        List<CaseAssistApply> assistApplies = IterableUtils.toList(caseAssistApplyRepository.findAll(booleanBuilder));
        List<FlowApproval> flowApprovalList = taskInfoService.getCaseNumberByUser(user, taskId);
        if (flowApprovalList != null && !flowApprovalList.isEmpty()) {
            for (FlowApproval flowApproval : flowApprovalList) {
                FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval);
                if (Objects.nonNull(flowNode)) {
                    FlowTask flowTask = processBaseService.getFlowTaskByNode(flowNode);//为了获取任务名称
                    Role role = roleRepository.findOne(flowNode.getRoleId());//为了获取角色名称
                    if (Objects.nonNull(flowTask)) {
                        String caseNumber = flowApproval.getCaseNumber();
                        Iterator<CaseAssistApply> iterator = caseAssistApplyRepository.findAll(QCaseAssistApply.caseAssistApply.caseNumber.eq(caseNumber)
                                .and(QCaseAssistApply.caseAssistApply.approvalId.eq(flowApproval.getId()))).iterator();

                        if (iterator.hasNext()) {
                            CaseAssistApply caseAssistApply = iterator.next();//获取对应的协催申请案件记录
                            if(assistApplies.contains(caseAssistApply)) {
                                AssistApplyProcessServiceMode assistApplyProcessServiceMode = new AssistApplyProcessServiceMode();
                                assistApplyProcessServiceMode.setAssistApplyId(caseAssistApply.getId());//申请协催案件id
//                              assistApplyProcessServiceMode.setCaseId(caseAssistApply.getCaseId());//案件id
                                assistApplyProcessServiceMode.setCaseNumber(caseAssistApply.getCaseNumber());//案件编号
                                assistApplyProcessServiceMode.setPersonalId(caseAssistApply.getPersonalId());//客户信息id
                                assistApplyProcessServiceMode.setPersonalName(caseAssistApply.getPersonalName());//客户姓名
                                assistApplyProcessServiceMode.setPersonalPhone(caseAssistApply.getPersonalPhone());//客户手机号
                                assistApplyProcessServiceMode.setCollectionType(caseAssistApply.getCollectionType());//催收类型
                                assistApplyProcessServiceMode.setDeptCode(caseAssistApply.getDeptCode());//机构编号
                                assistApplyProcessServiceMode.setPrincipalId(caseAssistApply.getPrincipalId());//委托方id
                                assistApplyProcessServiceMode.setPrincipalName(caseAssistApply.getPrincipalName());//委托方名称
                                // 案件信息
                                List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseNumber);
                                if (Objects.nonNull(byCaseNumber)){
                                    BigDecimal over = new BigDecimal(0);
                                    BigDecimal over1 = new BigDecimal(0);
                                    Integer overduePeriods = 0;
                                    Integer overdueDays = 0;
                                    for (CaseInfo caseInfo:byCaseNumber) {
                                        over = over.add(caseInfo.getOverdueAmount());
                                        over1 = over1.add(caseInfo.getAccountBalance());
                                        if (overduePeriods <= caseInfo.getOverduePeriods()){
                                            overduePeriods = caseInfo.getOverduePeriods();
                                        }
                                        if (overdueDays <= caseInfo.getOverdueDays()){
                                            overdueDays = caseInfo.getOverdueDays();
                                        }
                                    }
                                    assistApplyProcessServiceMode.setOverdueAmount(over);//逾期总金额
                                    assistApplyProcessServiceMode.setAccountBalance(over1);
                                    assistApplyProcessServiceMode.setOverdueDays(overdueDays);//逾期天数
                                    assistApplyProcessServiceMode.setOverduePeriods(overduePeriods);//逾期期数
//                                    assistApplyProcessServiceMode.setHoldDays(caseAssistApply.getHoldDays());//持安天数
//                                    assistApplyProcessServiceMode.setLeftDays(caseAssistApply.getLeftDays());//剩余天数
                                }
                                assistApplyProcessServiceMode.setAreaId(caseAssistApply.getAreaId());//省份id
                                assistApplyProcessServiceMode.setAreaName(caseAssistApply.getAreaName());//城市名称
                                assistApplyProcessServiceMode.setApplyUserName(caseAssistApply.getApplyUserName());//申请人
                                assistApplyProcessServiceMode.setApplyRealName(caseAssistApply.getApplyRealName());//申请人姓名
                                assistApplyProcessServiceMode.setApplyDeptName(caseAssistApply.getApplyDeptName());//申请人机构
                                assistApplyProcessServiceMode.setApplyReason(caseAssistApply.getApplyReason());//申请原因
                                assistApplyProcessServiceMode.setApplyDate(caseAssistApply.getApplyDate());//申请时间
                                assistApplyProcessServiceMode.setApplyInvalidTime(caseAssistApply.getApplyInvalidTime());//申请失效时间
                                assistApplyProcessServiceMode.setAssistWay(caseAssistApply.getAssistWay());//协催方式
//                                assistApplyProcessServiceMode.setProductSeries(caseAssistApply.getProductSeries());//产品系列id
//                                assistApplyProcessServiceMode.setProductId(caseAssistApply.getProductId());//产品id
//                                assistApplyProcessServiceMode.setProductSeriesName(caseAssistApply.getProductSeriesName());//产品系列名称
//                                assistApplyProcessServiceMode.setProductName(caseAssistApply.getProductName());//产品名称
                                assistApplyProcessServiceMode.setCompanyCode(caseAssistApply.getCompanyCode());//公司编号
                                assistApplyProcessServiceMode.setApprovalId(caseAssistApply.getApprovalId());//申请流程id

                                assistApplyProcessServiceMode.setRoleId(role.getId());//角色id
                                assistApplyProcessServiceMode.setRoleName(role.getName());//角色名称
                                assistApplyProcessServiceMode.setTaskId(flowTask.getId());//任务id
                                assistApplyProcessServiceMode.setTaskName(flowTask.getTaskName());//任务名称
                                list.add(assistApplyProcessServiceMode);
                            }
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(sort) && sort.contains("applyDate,desc")){
            ListCompareUtil<AssistApplyProcessServiceMode> listCompareUtil = new ListCompareUtil<AssistApplyProcessServiceMode>();
            listCompareUtil.sortByMethod(list,"applyDate",true);
            return list;
        }else if (StringUtils.isNotBlank(sort) && sort.contains("applyDate,asc")){
            ListCompareUtil<AssistApplyProcessServiceMode> listCompareUtil = new ListCompareUtil<AssistApplyProcessServiceMode>();
            listCompareUtil.sortByMethod(list,"applyDate",false);
            return list;
        }
        return list;
    }

    /**
     * 案件协催审批流程和业务方法(申请)
     *
     * @param assistApplyProcessMode
     * @param user
     */
    @Transactional
    public void ApplyCaseAssistApproval(AssistApplyProcessMode assistApplyProcessMode, User user) {
        List<CaseInfo> caseInfos = caseInfoRepository.findByCaseNumber(assistApplyProcessMode.getCaseNumber());
        if (caseInfos.size() == 0) {
            throw new RuntimeException("该案件未找到");
        }
        //判断是否申请角色
        if (taskInfoService.existsApply(assistApplyProcessMode.getTaskId(), user)) {
            //判断该案件是否已经申请过了
            if (noCaseAssistApproval(assistApplyProcessMode.getCaseNumber())) {
                //判断是否留案申请过
                if(exitRoam(assistApplyProcessMode.getCaseNumber(),caseInfos.get(0).getId())) {
                    String approvalId = taskInfoService.applyCaseInfo(assistApplyProcessMode.getCaseNumber(), assistApplyProcessMode.getTaskId(), user,assistApplyProcessMode.getApplyReason());//流程申请
                    saveAssistApply(assistApplyProcessMode, user, approvalId);
                }else{
                    throw new RuntimeException("该案件已申请流转，不允许申请协催");
                }
            } else {
                throw new RuntimeException("该案已在协催流程中，不允许再次申请");
            }
        } else {
            throw new RuntimeException("该用户不是协催案件申请的角色");
        }
    }

    /**
     * 判断该案件是否留案申请
     * @param caseNumber
     * @param caseId
     * @return
     */
    public boolean exitRoam(String caseNumber,String caseId){
        Iterable<FlowApproval> flowApprovals = processBaseService.getFlowApproavalListByCaseNumber(caseNumber);
        if (flowApprovals.iterator().hasNext()) {
            //审批通过后将会在申请表中删除对应的申请记录。
            List<CaseRecordApply> list = caseRecordApplyRepository.findAllByCaseId(caseId);
            if (list != null && list.size() != 0) {
                return false;
            }
        }
        return true;
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
            Iterator<CaseAssistApply> caseAssistApplyIterator = caseAssistApplyRepository.findAll(qCaseAssistApply.caseNumber.eq(caseNumber).and(qCaseAssistApply.approveStatus.in(
                    CaseAssistApply.ApproveStatus.APPROVAL_PENDING.getValue(),CaseAssistApply.ApproveStatus.VISIT_COMPLETE.getValue()))).iterator();
            while (caseAssistApplyIterator.hasNext()) {
                CaseAssistApply caseAssistApply = caseAssistApplyIterator.next();
                if (Objects.equals(caseAssistApply.getApproveStatus(), CaseAssistApply.ApproveStatus.APPROVAL_PENDING.getValue())||
                        Objects.equals(caseAssistApply.getApproveStatus(), CaseAssistApply.ApproveStatus.VISIT_COMPLETE.getValue())){
                    if(Objects.equals(caseAssistApply.getApproveOutResult(), null)) {
                        return false;
                    }
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
     * 案件协催申请业务逻辑
     *
     * @param assistApplyProcessMode
     * @param tokenUser
     * @param approvalId
     */
    @Transactional
    public void saveAssistApply(AssistApplyProcessMode assistApplyProcessMode, User tokenUser, String approvalId) {
//        CaseInfo caseInfo = caseInfoRepository.findOne(assistApplyProcessMode.getCaseId());
        List<CaseInfo> caseInfos = caseInfoRepository.findByCaseNumber(assistApplyProcessMode.getCaseNumber());
        if (caseInfos.size() == 0) {
            throw new RuntimeException("该案件未找到");
        }
//        if (!Objects.equals(caseInfos.get(0).getCollectionType(), CaseInfo.CollectionType.TEL.getValue())) {
//            throw new RuntimeException("非电催案件不允许申请协催");
//        }
        QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
        if (Objects.equals(caseInfos.get(0).getAssistFlag(), 1)) { //有协催标识
            for (CaseInfo obj:caseInfos) {
                CaseAssist caseAssist = caseAssistRepository.findOne(qCaseAssist.caseId.id.eq(obj.getId()).
                        and(qCaseAssist.assistStatus.ne(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue())));
                if (Objects.isNull(caseAssist)) { //有协催申请
                    throw new RuntimeException("该案件已经提交了协催申请，不允许重复提交");
                } else { //有协催案件
                    throw new RuntimeException("该案件正在协催，不允许重复申请");
                }
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
        CaseInfo caseInfo = caseInfos.get(0);
        CaseAssistApply caseAssistApply = new CaseAssistApply();
        caseAssistApply.setCaseNumber(assistApplyProcessMode.getCaseNumber()); //案件编号
        caseAssistApply.setPersonalName(caseInfo.getPersonalInfo().getName()); //客户姓名
        caseAssistApply.setPersonalPhone(caseInfo.getPersonalInfo().getMobileNo()); // 客户电话
        caseAssistApply.setPersonalId(caseInfo.getPersonalInfo().getId()); //客户信息ID
        caseAssistApply.setCollectionType(caseInfo.getCollectionType()); //催收类型
        caseAssistApply.setDeptCode(caseInfo.getDepartment().getCode()); //部门Code
//        caseAssistApply.setPrincipalId(caseInfo.getPrincipalId().getId()); //委托方ID
//        caseAssistApply.setPrincipalName(caseInfo.getPrincipalId().getName()); //委托方名称
//        caseAssistApply.setOverdueAmount(caseInfo.getOverdueAmount()); //逾期总金额
//        caseAssistApply.setOverdueDays(caseInfo.getOverdueDays()); //逾期总天数
//        caseAssistApply.setOverduePeriods(caseInfo.getOverduePeriods()); //逾期期数
//        caseAssistApply.setHoldDays(caseInfo.getHoldDays()); //持案天数
//        caseAssistApply.setLeftDays(caseInfo.getLeftDays()); //剩余天数
        caseAssistApply.setAreaId(ZWStringUtils.isEmpty(caseInfo.getArea()) ? null : caseInfo.getArea().getId()); //省份编号
        caseAssistApply.setAreaName(ZWStringUtils.isEmpty(caseInfo.getArea()) ? null : caseInfo.getArea().getAreaName()); //城市名称
        caseAssistApply.setApplyUserName(tokenUser.getUserName()); //申请人
        caseAssistApply.setApplyRealName(tokenUser.getRealName()); //申请人姓名
        caseAssistApply.setApplyDeptName(tokenUser.getDepartment().getName()); //申请人部门名称
        caseAssistApply.setApplyReason(assistApplyProcessMode.getApplyReason()); //申请原因
        caseAssistApply.setApplyDate(ZWDateUtil.getNowDateTime()); //申请时间
        caseAssistApply.setApplyInvalidTime(applyInvalidTime); //申请失效日期
        caseAssistApply.setAssistWay(assistApplyProcessMode.getAssistWay()); //协催方式
        caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.APPROVAL_PENDING.getValue()); //审批状态 288-待审批
//        caseAssistApply.setProductSeries(Objects.isNull(caseInfo.getProduct()) ? null : Objects.isNull(caseInfo.getProduct().getProductSeries()) ? null : caseInfo.getProduct().getProductSeries().getId()); //产品系列ID
//        caseAssistApply.setProductId(Objects.isNull(caseInfo.getProduct()) ? null : caseInfo.getProduct().getId()); //产品ID
//        caseAssistApply.setProductSeriesName(Objects.isNull(caseInfo.getProduct()) ? null : Objects.isNull(caseInfo.getProduct().getProductSeries()) ? null : caseInfo.getProduct().getProductSeries().getSeriesName()); //产品系列名称
//        caseAssistApply.setProductName(Objects.isNull(caseInfo.getProduct()) ? null : caseInfo.getProduct().getProductName()); //产品名称
//        caseAssistApply.setCompanyCode(caseInfo.getCompanyCode()); //公司code码
        caseAssistApply.setApprovalId(approvalId);//申请流程id
        caseAssistApplyRepository.saveAndFlush(caseAssistApply);
        //更新原案件
        for (CaseInfo obj:caseInfos) {
            obj.setAssistFlag(1); //协催标识
            obj.setAssistWay(assistApplyProcessMode.getAssistWay()); //协催方式
            obj.setAssistStatus(CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue()); //协催状态
            obj.setOperator(tokenUser); //操作人
            obj.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseInfoRepository.saveAndFlush(obj);
        }
    }

    /**
     * 协催案件审批流程（审批）
     *
     * @param assistApplyApprovaleModel
     * @param user
     */
    @Transactional
    public void assistApplyVisitApprove(AssistApplyApprovaleModel assistApplyApprovaleModel, User user) {
        //流程审批走之前的审批链中的节点
        FlowApproval firstFlowApproval = processBaseService.getFlowApprovalById(assistApplyApprovaleModel.getApprovalId());
        FlowNode berforeFlowNode = new FlowNode();
        if (Objects.nonNull(firstFlowApproval)) {
            //获取走审批之前的节点信息
            berforeFlowNode = processBaseService.getFlowNodeByApproval(firstFlowApproval);
        }
        if (existApproval(user, berforeFlowNode)) {
            taskInfoService.saveFlowApprovalAndHistory(assistApplyApprovaleModel.getCaseNumber(), assistApplyApprovaleModel.getNodeState().toString(),
                    assistApplyApprovaleModel.getNodeOpinion(), assistApplyApprovaleModel.getStep(), assistApplyApprovaleModel.getApprovalId(), user); //流程审批

            CaseAssistApply apply = caseAssistApplyRepository.findOne(assistApplyApprovaleModel.getAssistApplyId());
            apply.setApproveStatus(CaseAssistApply.ApproveStatus.VISIT_COMPLETE.getValue());
            caseAssistApplyRepository.save(apply);

            FlowApproval flowApproval = processBaseService.getFlowApprovalById(assistApplyApprovaleModel.getApprovalId());
            if (Objects.nonNull(flowApproval)) {
                //获取走审批之后的节点
                FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval);//下一个节点
                //当前用户所在的节点
                if (flowNode.getId().equals(berforeFlowNode.getId())) { //两者相同则是最后一个节点，否则不是
                    if (!assistApplyApprovaleModel.getNodeState().equals(FlowHistory.NodeState.REFUSE.getValue())) {
                        assistInfoApproval(assistApplyApprovaleModel, user);
                    }
                } else {
                    if (assistApplyApprovaleModel.getNodeState().equals(FlowHistory.NodeState.REJECT.getValue())) {  //不是最后一个人审批，只要是拒绝的，则更新案件申请表数据
                        assistInfoApproval(assistApplyApprovaleModel, user);
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
     * 协催案件审批业务逻辑
     *
     * @param assistApplyApprovaleModel
     * @param user
     */
    public void assistInfoApproval(AssistApplyApprovaleModel assistApplyApprovaleModel, User user) {
        CaseAssistApply apply = caseAssistApplyRepository.findOne(assistApplyApprovaleModel.getAssistApplyId());
        if (Objects.nonNull(apply)) {
            List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(assistApplyApprovaleModel.getCaseNumber());
//            String caseId = apply.getCaseId();
//            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            if (byCaseNumber.size() == 0) {
                throw new RuntimeException("未找到相应的原案件!");
            }
            Integer approveResult = 0;
            Integer approveStatus = 0;
            if (assistApplyApprovaleModel.getNodeState() == 0) { //同意
                approveResult = CaseAssistApply.ApproveResult.APPROVAL_PASS.getValue();
                approveStatus = CaseAssistApply.ApproveStatus.APPROVE.getValue();//协催审批完成
            }
            if (assistApplyApprovaleModel.getNodeState() == 2) { //拒绝
                approveResult = CaseAssistApply.ApproveResult.APPROVAL_REFUSED.getValue();
                approveStatus = CaseAssistApply.ApproveStatus.FAILURE.getValue();//审批失效
            }
            apply.setApproveStatus(approveStatus); //审批状态修改为外访审批完成
            apply.setApproveOutResult(assistApplyApprovaleModel.getNodeState()); //审批结果
            apply.setApproveOutMemo(assistApplyApprovaleModel.getNodeOpinion()); //审批意见
            apply.setApproveOutUser(user.getUserName()); //外访审批人
            apply.setApproveOutName(user.getRealName()); //外访审批人姓名
            apply.setApproveOutDatetime(new Date()); //外访审批时间

            if (approveResult.equals(CaseAssistApply.ApproveResult.APPROVAL_REFUSED.getValue())) { //拒绝
                for (CaseInfo caseInfo:byCaseNumber) {
                    caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_REFUSED.getValue()); //协催状态
                    caseInfo.setAssistFlag(0); //协催标识
                    caseInfo.setAssistWay(null); //协催方式
                    caseInfoRepository.save(caseInfo);
                }
                caseAssistApplyRepository.save(apply);
            }
            // 审批通过
            if (approveResult == CaseAssistApply.ApproveResult.APPROVAL_PASS.getValue()) {
                for (CaseInfo caseInfo:byCaseNumber) {
                    CaseAssist caseAssist = new CaseAssist();
                    // 案件协催表增加记录
                    caseAssist.setCaseId(caseInfo); //案件信息
                    caseAssist.setHoldDays(0); //协催持案天数
                    caseAssist.setHasLeaveDays(0);//已留案天数
                    caseAssist.setMarkId(CaseInfo.Color.NO_COLOR.getValue()); //打标标记-默认无色
                    caseAssist.setCompanyCode(caseInfo.getCompanyCode()); //公司Code
                    caseAssist.setAssistWay(apply.getAssistWay()); //协催方式
                    caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue()); //协催状态（协催待分配）
                    caseAssist.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标识-非留案
                    caseAssist.setCaseFlowinTime(new Date()); //流入时间
                    caseAssist.setOperatorTime(new Date()); // 操作时间
                    caseAssist.setCurrentCollector(caseInfo.getCurrentCollector()); //当前催收员
                    caseAssist.setOperator(user); // 操作员
                    caseAssist.setDeptCode(user.getDepartment().getCode());//添加外访协催审批人的部门code
                    //修该案件中的案件协催状态为协催待分配
                    caseInfo.setAssistStatus(CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue());
                    caseInfo.setAssistFlag(1);  //协催标识
                    caseInfo.setAssistWay(apply.getAssistWay()); //协催方式
                    if (Objects.equals(caseAssist.getAssistWay(), CaseAssist.AssistWay.ONCE_ASSIST.getValue())) {
                        Personal personal = caseInfo.getPersonalInfo();
                        if (Objects.isNull(personal.getLongitude())
                                || Objects.isNull(personal.getLatitude())) {
                            try {
                                MapModel model = accMapService.getAddLngLat(personal.getLocalHomeAddress());
                                personal.setLatitude(BigDecimal.valueOf(model.getLatitude()));
                                personal.setLongitude(BigDecimal.valueOf(model.getLongitude()));
                                caseInfo.setPersonalInfo(personal);
                            } catch (Exception e1) {
                                e1.getMessage();
                            }
                        }
                    }
                    caseAssistApplyRepository.save(apply);
                    // 修改申请表信息
                    if (approveResult == CaseAssistApply.ApproveResult.APPROVAL_PASS.getValue()) {
                        caseAssistRepository.save(caseAssist);
                    }
                    caseInfoRepository.save(caseInfo);
                }
            }
        }
    }
}
