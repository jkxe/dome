package cn.fintecher.pangolin.business.service.flow;




import cn.fintecher.pangolin.business.model.ApplyLeaveCaseParam;
import cn.fintecher.pangolin.business.model.LeaveCaseInfoModel;
import cn.fintecher.pangolin.business.model.ProcessLeaveParam;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("leaveCaseService")
public class LeaveCaseService {

    final Logger log = LoggerFactory.getLogger(LeaveCaseService.class);

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private LeaveCaseApplyRepository leaveCaseApplyRepository;

    @Autowired
    private ProcessBaseService processBaseService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CaseTurnRecordRepository caseTurnRecordRepository;


    @Autowired
    private OutsourcePoolRepository outsourcePoolRepository;

    /**
     * 获取该用户名下所有待审批的留案案件
     * @param taskId
     * @param user
     * @return
     */
    public List<LeaveCaseInfoModel> getLeaveCaseInfo(String taskId, User user) {
        List<LeaveCaseInfoModel> leaveCaseInfoModels = new ArrayList<>();
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
                        if (caseInfoList != null &&!caseInfoList.isEmpty()) {
                            CaseInfo caseInfo = caseInfoList.get(0);//案件信息
                            List<LeaveCaseApply> leaveCaseApplyList = leaveCaseApplyRepository.queryAllByCaseId(caseInfo.getId());
                            if(leaveCaseApplyList != null && leaveCaseApplyList.size() > 0){
                                LeaveCaseApply leaveCaseApply = leaveCaseApplyList.get(0);
                                LeaveCaseInfoModel leaveCaseInfoModel = new LeaveCaseInfoModel();
                                leaveCaseInfoModel.setCaseId(caseInfo.getId());
                                Iterable<OutsourcePool> all = outsourcePoolRepository.findAll(QOutsourcePool.outsourcePool.caseInfo.id.eq(caseInfo.getId()));
                                if (all.iterator().hasNext()){
                                    if(Objects.nonNull(all.iterator().next().getOutsource()) && Objects.nonNull(all.iterator().next().getOutsource().getOutsName())){
                                        leaveCaseInfoModel.setPrincipalName(all.iterator().next().getOutsource().getOutsName());
                                    }
                                }
                                leaveCaseInfoModel.setCaseNumber(caseInfo.getCaseNumber());
                                leaveCaseInfoModel.setBatchNumber(caseInfo.getBatchNumber());
//                                leaveCaseInfoModel.setPrincipalName(caseInfo.getPrincipalId().getName());
                                leaveCaseInfoModel.setPersonalName(caseInfo.getPersonalInfo().getName());
                                leaveCaseInfoModel.setMobileNo(caseInfo.getPersonalInfo().getMobileNo());
                                leaveCaseInfoModel.setIdCard(caseInfo.getPersonalInfo().getCertificatesNumber());
                                List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseInfo.getCaseNumber());
                                Integer op = 0;
                                for (int i = 0; i < byCaseNumber.size(); i++) {
                                    if (Objects.nonNull(byCaseNumber.get(i).getOverdueAmount())){
                                        leaveCaseInfoModel.setOverdueAmount(leaveCaseInfoModel.getOverdueAmount().add(byCaseNumber.get(i).getOverdueAmount()));//逾期总金额
                                    }
                                    if (Objects.nonNull(byCaseNumber.get(i).getOverduePeriods()) && op < byCaseNumber.get(i).getOverduePeriods()){
                                        op = byCaseNumber.get(i).getOverduePeriods();//逾期期数
                                    }
                                    if (Objects.nonNull(leaveCaseInfoModel.getOverdueDays()) && Objects.nonNull(byCaseNumber.get(i).getOverdueDays()) && leaveCaseInfoModel.getOverdueDays() < byCaseNumber.get(i).getOverdueDays()){
                                        leaveCaseInfoModel.setOverdueDays(byCaseNumber.get(i).getOverdueDays());//逾期天数
                                    }
                                }
//                                leaveCaseInfoModel.setOverdueAmount(caseInfo.getOverdueAmount());
                                leaveCaseInfoModel.setOverduePeriods("M"+op);
//                                leaveCaseInfoModel.setOverdueDays(caseInfo.getOverdueDays());
                                if (ZWStringUtils.isNotEmpty(caseInfo.getFollowupBack())){
                                    leaveCaseInfoModel.setFollowupBack(caseInfo.getFollowupBack());
                                }
                                leaveCaseInfoModel.setHoldDays(caseInfo.getHoldDays());
                                if (caseInfo.getLeftDays() != null){
                                    leaveCaseInfoModel.setLeftDays(caseInfo.getLeftDays());
                                }

//                                leaveCaseInfoModel.setLeaveCaseExpireTime(String.valueOf(leaveCaseApply.getLeftDate().getTime()/1000));
                                leaveCaseInfoModel.setLeaveCaseExpireTime(leaveCaseApply.getLeftDate());
                                leaveCaseInfoModel.setApplyUser(leaveCaseApply.getApplyUser());
                                leaveCaseInfoModel.setApplyTime(leaveCaseApply.getApplyTime());
                                leaveCaseInfoModel.setApprovalId(leaveCaseApply.getApprovalId());
                                leaveCaseInfoModel.setLeaveReason(leaveCaseApply.getLeaveReason());
                                leaveCaseInfoModel.setTaskId(flowTask.getId());
                                leaveCaseInfoModel.setTaskName(flowTask.getTaskName());
                                leaveCaseInfoModel.setRoleId(role.getId());
                                leaveCaseInfoModel.setRoleName(role.getName());
                                leaveCaseInfoModel.setLeaveId(leaveCaseApply.getId());
                                //只有外访和委外有留案
                                if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.INNER.getValue())){
                                    leaveCaseInfoModel.setLeaveCaseType("外访");
                                }else if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.OUTER.getValue())){
                                    leaveCaseInfoModel.setLeaveCaseType("委外");
                                }
                                leaveCaseInfoModels.add(leaveCaseInfoModel);
                            }
                        }
                    }
                }
            }
        }
        return leaveCaseInfoModels;
    }

    /**
     * 留案案件申请
     * @param applyLeaveCaseParam
     */
    @Transactional
    public void applyLeaveCase(ApplyLeaveCaseParam applyLeaveCaseParam, User user){
        List<String> caseList = applyLeaveCaseParam.getCaseIdList();//案件id
        for(String caseId : caseList){
            CaseInfo info = caseInfoRepository.findOne(caseId);
            if (Objects.isNull(info)) {
                throw new RuntimeException("该案件未找到");
            }
            if(info.getLeaveCaseFlag().equals(1)){
                throw new RuntimeException("该案件本身就是留案案件");
            }
            //判断是否申请角色
            if (taskInfoService.existsApply(applyLeaveCaseParam.getTaskId(), user)) {
                //判断该案件是否正在审批
                if(exitApply(info)){
                    List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(info.getCaseNumber());
                    String approvalId = taskInfoService.applyCaseInfo(info.getCaseNumber(), applyLeaveCaseParam.getTaskId(), user,applyLeaveCaseParam.getLeaveReason());//流程申请
                    LeaveCaseApply leaveCaseApply = new LeaveCaseApply();
                    leaveCaseApply.setApprovalId(approvalId);//审批流程id
                    leaveCaseApply.setCaseId(info.getId());//案件id
                    leaveCaseApply.setApprovalStatus(0);//待审批状态
                    leaveCaseApply.setLeaveReason(applyLeaveCaseParam.getLeaveReason());//流案原因
                    leaveCaseApply.setApplyUser(user.getRealName());
                    leaveCaseApply.setApplyTime(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        leaveCaseApply.setLeftDate(sdf.parse(applyLeaveCaseParam.getLeftDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException("留案时间出错了..");
                    }
                    leaveCaseApplyRepository.save(leaveCaseApply);//保存案件申请记录表
                    // 留案申请完成
                    List<CaseInfo> list = new ArrayList<>();
                    /*caseInfoRepository.save(list);
                    for (CaseInfo caseInfo:byCaseNumber) {
                        caseInfo.setLeaveCaseFlag(1);
                        list.add(caseInfo);
                    }*/
                }else{
                    throw new RuntimeException("该案件正在留案审批之中");
                }
            }else{
                throw new RuntimeException("不是留案申请角色");
            }
        }
    }

    /**
     * 业务上判断是否正在审批
     * @param caseInfo
     * @return
     */
    public boolean exitApply(CaseInfo caseInfo){
        //判断流程中是否正在审批
        Iterator<FlowApproval> flowApprovals = processBaseService.getFlowApproavalListByCaseNumber(caseInfo.getCaseNumber()).iterator();
        if (flowApprovals.hasNext()) {
            //查询该次申请的案件是否正在审批
            List<LeaveCaseApply> leaveCaseApplyList = leaveCaseApplyRepository.queryAllByCaseId(caseInfo.getId());
            if(leaveCaseApplyList != null && leaveCaseApplyList.size() != 0){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }


    /**
     * 留案审批
     */
    @Transactional
    public void saveApprovalLeavecase(ProcessLeaveParam processLeaveParam, User user){
        //流程审批走之前的审批链中的节点
        FlowApproval firstFlowApproval = processBaseService.getFlowApprovalById(processLeaveParam.getApprovalId());
        FlowNode berforeFlowNode = new FlowNode();
        if (Objects.nonNull(firstFlowApproval)) {
            //获取走审批之前的节点信息
            berforeFlowNode = processBaseService.getFlowNodeByApproval(firstFlowApproval);
        }
        if (existApproval(user, berforeFlowNode)) {
            taskInfoService.saveFlowApprovalAndHistory(processLeaveParam.getCaseNumber(), processLeaveParam.getNodeState().toString(),
                    processLeaveParam.getNodeOpinion(), processLeaveParam.getStep(), processLeaveParam.getApprovalId(), user); //流程审批

            LeaveCaseApply leaveCaseApply  = leaveCaseApplyRepository.findOne(processLeaveParam.getLeaveId());
            if(Objects.nonNull(leaveCaseApply)){
                leaveCaseApply.setApprovalStatus(1);
                leaveCaseApplyRepository.save(leaveCaseApply);
            }
            FlowApproval flowApproval = processBaseService.getFlowApprovalById(processLeaveParam.getApprovalId());
            if (Objects.nonNull(flowApproval)) {
                //获取走审批之后的节点
                FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval);//下一个节点
                if (flowNode.getId().equals(berforeFlowNode.getId())) { //两者相同则是最后一个节点，否则不是
                    if (!processLeaveParam.getNodeState().equals(FlowHistory.NodeState.REFUSE.getValue())) {
                        saveLeave(processLeaveParam,user);
                    }
                } else {
                    if (processLeaveParam.getNodeState().equals(FlowHistory.NodeState.REJECT.getValue())) {
                        saveLeave(processLeaveParam, user);
                    }
                }
            }
        }else{
            throw new RuntimeException("该用户不是留案审批的角色");
        }
    }

    /**
     * 留案业务处理
     */
    public void saveLeave(ProcessLeaveParam processLeaveParam, User user){
        LeaveCaseApply leaveCaseApply  = leaveCaseApplyRepository.findOne(processLeaveParam.getLeaveId());

        if(Objects.nonNull(leaveCaseApply)){
            CaseInfo caseInfo = caseInfoRepository.findOne(leaveCaseApply.getCaseId());
            List<CaseInfo> caseInfos = caseInfoRepository.findByCaseNumber(caseInfo.getCaseNumber());
            List<CaseInfo> list = new ArrayList<>();
            //同意
            if(processLeaveParam.getNodeState().equals(Integer.valueOf(FlowHistory.NodeState.AGREE.getValue()))){
                leaveCaseApply.setApprovalStatus(2);
                for (int i = 0; i < caseInfos.size(); i++) {
                    caseInfo.setLeaveCaseFlag(1);
                    caseInfo.setCloseDate(leaveCaseApply.getLeftDate());
                    list.add(caseInfo);
                }
                caseInfoRepository.save(list);
                //判断是否是委外案件
                if(processLeaveParam.getNodeState().equals(Integer.valueOf(FlowHistory.NodeState.AGREE.getValue()))){
                    OutsourcePool outsourcePool = outsourcePoolRepository.findOne(QOutsourcePool.outsourcePool.caseInfo.id.eq(caseInfo.getId()));
                    if(Objects.nonNull(outsourcePool)){
                        outsourcePool.setOverOutsourceTime(leaveCaseApply.getLeftDate());
                        outsourcePoolRepository.save(outsourcePool);
                    }
                }
            }
            //拒绝
            if(processLeaveParam.getNodeState().equals(Integer.valueOf(FlowHistory.NodeState.REJECT.getValue()))){
                leaveCaseApply.setApprovalStatus(3);
            }
            //流程走完了,审批表删除记录
            leaveCaseApplyRepository.save(leaveCaseApply);


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
}
