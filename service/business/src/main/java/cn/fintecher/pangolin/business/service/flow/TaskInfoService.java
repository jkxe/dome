package cn.fintecher.pangolin.business.service.flow;

import cn.fintecher.pangolin.business.model.NodeModel;
import cn.fintecher.pangolin.business.model.ProcessModel;
import cn.fintecher.pangolin.business.model.ProcessNodeModel;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import com.querydsl.core.BooleanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author:  wangzhao
 * @Description
 * @Date  2018/6/14  14:34
 **/
@Service("TaskInfoService")
public class TaskInfoService {

    final Logger log = LoggerFactory.getLogger(TaskInfoService.class);

    @Autowired
    private FlowNodeRepository flowNodeRepository;

    @Autowired
    private FlowApprovalRepository flowApprovalRepository;

    @Autowired
    private FlowHistoryRepository flowHistoryRepository;

    @Autowired
    private FlowTaskRepository flowTaskRepository;

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReminderService reminderService;

    /**
     * 获取该用户下所有的待审批的案件编号及其他信息
     *
     * @param user   当前登录用户
     * @param taskId 任务id
     * @return
     */
    public List<FlowApproval> getCaseNumberByUser(User user, String taskId) {
        List<FlowApproval> flowApprovalList = new ArrayList<>();
        List<FlowApproval> list = new ArrayList<>();
        Iterator<Role> iterator = user.getRoles().iterator();
        while (iterator.hasNext()) {
            String roleId = iterator.next().getId();
            if (taskId == null || taskId.equals("")) {
                //根据角色获取对应的节点信息
                List<FlowNode> flowNodeList = flowNodeRepository.getFlowNodeByRoleId(roleId);
                if (flowNodeList != null && flowNodeList.size() != 0) {
                    for (FlowNode flowNode : flowNodeList) {
                        //获取对应的待审批审批流
                        flowApprovalList = flowApprovalRepository.getFlowApprovalBynodeId(flowNode.getId());
                        //防止多角色的存在
                        list.addAll(flowApprovalList);
                    }
                }
            } else {
                //根据角色和任务id获取对应的节点信息
                FlowNode flowNode = flowNodeRepository.getFlowNodeByRoleIdAndTaskId(roleId, taskId);
                if (Objects.nonNull(flowNode)) {
                    //获取对应的待审批审批流
                    flowApprovalList = flowApprovalRepository.getFlowApprovalBynodeId(flowNode.getId());
                    //防止多角色的存在
                    list.addAll(flowApprovalList);
                }
            }
        }
        return list;
    }

    /**
     * 根据审批id和案件编号查询该案件的审批历史记录
     *
     * @param approvalId 审批id
     * @param caseNumber 案件编号
     * @return
     */
    public List<FlowHistory> getFlowHistoryListByApprovalIdAndCaseNumber(String approvalId, String caseNumber) {

        return flowHistoryRepository.getFlowHistoriesByApprovalIdAndCaseNumber(approvalId, caseNumber);
    }

    /**
     * 保存审批记录
     *
     * @param caseNumber              案件编号
     * @param nodeState               审批状态
     * @param nodeOpinion             审批意见
     * @param step                    回退到第几步
     * @param approvalId              审批id
     * @param user
     */
    @Transactional
    public void saveFlowApprovalAndHistory(String caseNumber, String nodeState,
                                           String nodeOpinion, Integer step, String approvalId,
                                           User user) {
        FlowHistory fowHistory = new FlowHistory();
        fowHistory.setApprovalTime(new Date());  //审批时间
        fowHistory.setApprovalUser(user.getId()); //审批人
        FlowApproval flowApproval = flowApprovalRepository.findOne(approvalId);
        if (Objects.nonNull(flowApproval)) {
            FlowNode flowNode = flowNodeRepository.findOne(flowApproval.getNodeId());//当前用户所在的节点
            if (nodeState.equals(FlowHistory.NodeState.AGREE.getValue())) { //同意
                FlowNode nextFlowNode = flowNodeRepository.getFlowNodeByTaskIdAndStep(flowNode.getTaskId(), flowNode.getStep() + 1);
                if (Objects.nonNull(nextFlowNode)) {
                    flowApproval.setProcessState(FlowApproval.ProcessState.PROCESS_STATE_NORMAL.getValue());
                    flowApproval.setNodeId(nextFlowNode.getId());
                    List<User> list = new ArrayList<>();
                    //正常走下一个节点
                    list = getUserListByNode(nextFlowNode);//获取下一个节点的审批人
                    if (list.size() > 0 && list != null){
                        sendMessage(list, FlowHistory.NodeState.AGREE.getRemark(),nodeOpinion,caseNumber);//推送消息
                    }
                } else {
                    flowApproval.setProcessState(FlowApproval.ProcessState.PROCESS_STATE_END.getValue());
                    flowApproval.setNodeId(flowNode.getId());
                }
                flowApprovalRepository.save(flowApproval);//更新流程表
            }
            if (nodeState.equals(FlowHistory.NodeState.REJECT.getValue())) {  //拒绝
                flowApproval.setNodeId(flowNode.getId());
                flowApproval.setProcessState(FlowApproval.ProcessState.PROCESS_STATE_REJECT.getValue());
                flowApprovalRepository.save(flowApproval);//更新流程表

                List<User> list = getUserRejectByApproval(flowApproval.getId(), flowNode); //获取该节点对应的用户
                sendMessage(list, FlowHistory.NodeState.REJECT.getRemark(),nodeOpinion,caseNumber);//推送消息

            }
            if (nodeState.equals(FlowHistory.NodeState.REFUSE.getValue())) {  //驳回
                //获取驳回到节点信息
                if (step == null) { //默认驳回到上一步
                    step = 1;
                }
                FlowNode upFlowNode = flowNodeRepository.getFlowNodeByTaskIdAndStep(flowNode.getTaskId(), flowNode.getStep() - step);

                FlowNode minFlowNode = flowNodeRepository.getMinFlowNodeByTaskId(flowNode.getTaskId()); //获取该任务最小的节点
                flowApproval.setNodeId(upFlowNode.getId());
                if(Objects.equals(upFlowNode,minFlowNode)){
                    flowApprovalRepository.delete(flowApproval.getId());//更新流程表
                }else{
                    flowApproval.setProcessState(FlowApproval.ProcessState.PROCESS_STATE_NORMAL.getValue());
                    flowApprovalRepository.save(flowApproval);//更新流程表
                    List<User> list = getUserRefuse(upFlowNode, approvalId); //获取该节点对应的用户
                    sendMessage(list, FlowHistory.NodeState.REFUSE.getRemark(),nodeOpinion, caseNumber);//推送消息
                }
            }
            fowHistory.setNodeState(nodeState);//审批状态
            fowHistory.setApprovalId(approvalId); //审批id
            fowHistory.setNodeId(flowNode.getId());//节点id
            fowHistory.setTaskId(flowNode.getTaskId());//任务id
            fowHistory.setCaseNumber(caseNumber);//案件编号
            fowHistory.setNodeOpinion(nodeOpinion);
            flowHistoryRepository.save(fowHistory);
        }
    }

    /**
     * 根据下一个节点信息获取下一个节点对应的用户
     *
     * @param flowNode
     * @return
     */
    public List<User> getUserListByNode(FlowNode flowNode) {
        List<User> list = new ArrayList<>();
        Object[] objects = userRepository.getUserInfoByRoleId(flowNode.getRoleId());
        if (Objects.nonNull(objects)) {
            for (int i = 0; i < objects.length; i++) {
                Object[] userAll = (Object[]) objects[i];
                User user1 = userRepository.findOne(userAll[0].toString()); //下一个节点角色对应的用户
                list.add(user1);
            }
        }
        return list;
    }

    /**
     * 根据驳回到的节点和审批id查询对应的审批人
     *
     * @param flowNode
     * @param approvalId
     * @return
     */
    public List<User> getUserRefuse(FlowNode flowNode, String approvalId) {
        List<User> list = new ArrayList<>();
        List<FlowHistory> flowHistoryList = flowHistoryRepository.getFlowHistoriesByApprovalIdAndNodeId(approvalId, flowNode.getId());

        if (flowHistoryList != null && flowHistoryList.size() != 0) {
            String userId = flowHistoryList.get(0).getApprovalUser();//获取当时审批人
            User user = userRepository.findOne(userId);
            if (!existsApply(flowHistoryList.get(0).getTaskId(), user)) { //判断是否驳回到申请人
                list.add(user);
            } else {
                Object[] objects = userRepository.getUserInfoByRoleId(flowNode.getRoleId());
                if (Objects.nonNull(objects)) {
                    for (int i = 0; i < objects.length; i++) {
                        Object[] userAll = (Object[]) objects[i];
                        User user1 = userRepository.findOne(userAll[0].toString()); //驳回到的节点角色对应的用户
                        if (Objects.nonNull(user1) && user.getDepartment().getId().equals(user1.getDepartment().getId())) {
                            list.add(user1);
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     * 获取该审批任务的申请人
     *
     * @param approvalId
     * @param flowNode
     * @return
     */
    public List<User> getUserRejectByApproval(String approvalId, FlowNode flowNode) {
        List<User> list = new ArrayList<>();
        FlowNode minFlowNode = flowNodeRepository.getMinFlowNodeByTaskId(flowNode.getTaskId()); //获取该任务最小的节点
        if (Objects.nonNull(minFlowNode)) {
            List<FlowHistory> flowHistoryList = flowHistoryRepository.getFlowHistoriesByApprovalIdAndNodeId(approvalId, minFlowNode.getId());
            if (flowHistoryList != null && flowHistoryList.size() != 0) {
                User user = userRepository.findOne(flowHistoryList.get(0).getApprovalUser());
                list.add(user);
            }
        }

        return list;
    }

    /**
     * 消息推送
     *
     * @param userList   用户
     * @param caseNumber 案件编号
     * @param Opinion    审批状态
     *@param mgs 审批意见
     */
    public void sendMessage(List<User> userList, String Opinion,String mgs, String caseNumber) {
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        List<CaseInfo> caseInfoList = caseInfoRepository.findByCaseNumber(caseNumber);
        if(caseInfoList  != null && caseInfoList.size() != 0){
            String personName = caseInfoList.get(0).getPersonalInfo().getName();
                for (User user : userList) {
                    sendReminderMessage.setUserId(user.getId());
                    sendReminderMessage.setTitle("客户 [" + personName + "] 的流程申请审批");
                    sendReminderMessage.setContent(Opinion + ":" + mgs);
                    sendReminderMessage.setType(ReminderType.APPROVAL_MESSAGE);
                    reminderService.sendReminder(sendReminderMessage);
                }
        }
    }

    /**
     * 案件的申请
     * 返回申请流程id，在业务申请表结构中可能会使用
     * @param caseNumber
     * @param taskId
     * @param user
     */
    @Transactional
    public String applyCaseInfo(String caseNumber, String taskId, User user,String description) {
        FlowApproval flow = new FlowApproval();
        if (existsApply(taskId, user)) {
            FlowHistory fowHistory = new FlowHistory();
            fowHistory.setApprovalTime(new Date());  //审批时间
            fowHistory.setApprovalUser(user.getId()); //审批人

            FlowNode flowNode = flowNodeRepository.getMinFlowNodeByTaskId(taskId);//获取当前任务的申请节点
            FlowNode nextFlowNode = flowNodeRepository.getFlowNodeByTaskIdAndStep(flowNode.getTaskId(), flowNode.getStep() + 1);
            FlowApproval flowApproval = new FlowApproval();
            flowApproval.setNodeId(nextFlowNode.getId());
            flowApproval.setCaseNumber(caseNumber);
            flowApproval.setProcessState(FlowApproval.ProcessState.PROCESS_STATE_NORMAL.getValue());
            flow = flowApprovalRepository.save(flowApproval);

            fowHistory.setApprovalId(flowApproval.getId());
            fowHistory.setTaskId(taskId);
            fowHistory.setNodeId(flowNode.getId());
            fowHistory.setCaseNumber(caseNumber);
            fowHistory.setNodeState(FlowHistory.NodeState.SUCCESS.getValue());
            fowHistory.setNodeOpinion(description);
            flowHistoryRepository.save(fowHistory);//保存历史记录

            List<User> list = getUserByApproval(nextFlowNode,user);
            sendMessage(list, "申请成功",description, caseNumber);//推送消息
            return flowApproval.getId();
        }
        return flow.getId();
    }

    /**
     * 申请时获取下一个节点用户
     * @param flowNode
     * @param user
     * @return
     */
    public List<User> getUserByApproval(FlowNode flowNode, User user){
        List<User> list = new ArrayList<>(); //正常节点获取的用户
//        List<User> userList = new ArrayList<>();//最终返回值
        Object[] objects = userRepository.getUserInfoByRoleId(flowNode.getRoleId());
        if (Objects.nonNull(objects)) {
            for (int i = 0; i < objects.length; i++) {
                Object[] userAll = (Object[]) objects[i];
                User user1 = userRepository.findOne(userAll[0].toString()); //下一个节点角色对应的用户
                if (Objects.nonNull(user1)) {
                    list.add(user1);
                }
            }
        }
//        for (User users : list) {
//            if (user.getDepartment().getCode().contains(users.getDepartment().getCode())) {
//                userList.add(user);
//            }
//        }
        return list;
    }

    /**
     * 判断该案件是否已经申请了该审批链
     * 返回true则是已有审批存在
     *
     * @param caseNumber
     * @param taskId
     * @return
     */
    public boolean existApproval(String caseNumber, String taskId) {
        List<FlowNode> flowNodeList = flowNodeRepository.getAllByTaskId(taskId);
        if (flowNodeList != null && flowNodeList.size() != 0) {
            for (FlowNode flowNode : flowNodeList) {
                BooleanBuilder booleanBuilder = new BooleanBuilder();
                QFlowApproval qFlowApproval = QFlowApproval.flowApproval;
                booleanBuilder.and(qFlowApproval.caseNumber.eq(caseNumber));
                booleanBuilder.and(qFlowApproval.nodeId.eq(flowNode.getId()));
                booleanBuilder.and(qFlowApproval.processState.notIn(FlowApproval.ProcessState.PROCESS_STATE_REJECT.getValue()));
                Iterator<FlowApproval> iterator = flowApprovalRepository.findAll(booleanBuilder).iterator();
                if(iterator.hasNext()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断该用户是否是申请角色
     *
     * @param taskId
     * @param user
     * @return
     */
    public boolean existsApply(String taskId, User user) {
        FlowNode flowNode = flowNodeRepository.getMinFlowNodeByTaskId(taskId);
        Iterator<Role> iterator = user.getRoles().iterator();
        while (iterator.hasNext()) {
            String roleId = iterator.next().getId(); //获取角色id
            if (roleId.equals(flowNode.getRoleId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取任务节点列表信息
     *
     * @param taskId
     * @return
     */
    public List<ProcessNodeModel> getTaskInfoList(String taskId) {
        List<ProcessNodeModel> list = new ArrayList<>();
        Object[] objects = null;
        if (taskId == null || taskId.equals("")) {
            objects = flowTaskRepository.getTaskInfoList();
        } else {
            objects = flowTaskRepository.getTaskInfoListByTaskId(taskId);
        }
        if (Objects.nonNull(objects)) {
            for (int i = 0; i < objects.length; i++) {
                Object[] taskNode = (Object[]) objects[i];
                ProcessNodeModel processNodeModel = new ProcessNodeModel();
                processNodeModel.setTaskId(taskNode[0].toString()); //任务id
                processNodeModel.setTaskName(taskNode[1].toString()); //任务名称
                processNodeModel.setNodeId(taskNode[2].toString());  //节点id
                processNodeModel.setRoleId(taskNode[3].toString());  //角色id
                processNodeModel.setTemp(Integer.valueOf(taskNode[4].toString())); //当前步数
                processNodeModel.setRoleName(taskNode[5].toString()); //角色名称
                list.add(processNodeModel);
            }
        }
        return list;
    }

    /**
     * 获取所有的任务（为添加节点时下拉框赋值做准备）
     *
     * @return
     */
    public List<FlowTask> getFlowTaskAll(String taskName) {
        List<FlowTask> flowTaskList = new ArrayList<>();
        if (taskName == null || taskName.equals("")) {
            flowTaskList = flowTaskRepository.findAll();
        } else {
            flowTaskList = flowTaskRepository.findByTaskNameLike(taskName);
        }

        return flowTaskList;
    }

    /**
     * 添加流程配置
     *
     * @param processModel
     */
    @Transactional
    public void saveProcessTask(ProcessModel processModel) {
        FlowTask flowTask = new FlowTask();
        flowTask.setTaskName(processModel.getTaskName());
        flowTask = flowTaskRepository.save(flowTask);//添加流程任务
        if (processModel.getList() != null && processModel.getList().size() != 0) {
            for (NodeModel nodeModel : processModel.getList()) {
                FlowNode flowNode = new FlowNode();
                flowNode.setTaskId(flowTask.getId());//任务id
                flowNode.setRoleId(nodeModel.getRoleId());
                flowNode.setStep(nodeModel.getStep());
                flowNodeRepository.save(flowNode);
            }
        }
    }

    /**
     * 申请撤回流程处理
     *
     * @param approvalId
     * @param caseNumber
     */
    public void deleteApproval(String approvalId, String caseNumber) {
        flowApprovalRepository.delete(approvalId); //删除对应的审批
        flowHistoryRepository.deleteByApprovalIdAndCaseNumber(approvalId, caseNumber);
    }


}
