package cn.fintecher.pangolin.business.service.flow;

import cn.fintecher.pangolin.business.repository.FlowApprovalRepository;
import cn.fintecher.pangolin.business.repository.FlowNodeRepository;
import cn.fintecher.pangolin.business.repository.FlowTaskRepository;
import cn.fintecher.pangolin.entity.FlowApproval;
import cn.fintecher.pangolin.entity.FlowNode;
import cn.fintecher.pangolin.entity.FlowTask;
import cn.fintecher.pangolin.entity.QFlowApproval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:  wangzhao
 * @Description
 * @Date  2018/6/14  14:34
 **/
@Service("ProcessBaseService")
public class ProcessBaseService {

    final Logger log = LoggerFactory.getLogger(ProcessBaseService.class);

    @Autowired
    private FlowNodeRepository flowNodeRepository;

    @Autowired
    private FlowApprovalRepository flowApprovalRepository;

    @Autowired
    private FlowTaskRepository flowTaskRepository;

    /**
     * 根据节点获取任务信息
     * @param flowNode
     * @return
     */
    public FlowTask getFlowTaskByNode(FlowNode flowNode){
        return flowTaskRepository.findOne(flowNode.getTaskId());
    }

    /**
     * 根据流程获取节点信息
     * @param flowApproval
     * @return
     */
    public FlowNode getFlowNodeByApproval(FlowApproval flowApproval){
        return flowNodeRepository.findOne(flowApproval.getNodeId());
    }

    /**
     * 根据案件编号查询正在审批的流程
     * @param caseNumber
     */
    public Iterable<FlowApproval> getFlowApproavalListByCaseNumber(String caseNumber){
        QFlowApproval qFlowApproval = QFlowApproval.flowApproval;
        return flowApprovalRepository.findAll(qFlowApproval.caseNumber.eq(caseNumber).and(qFlowApproval.processState.
                eq(FlowApproval.ProcessState.PROCESS_STATE_NORMAL.getValue())));
    }

    /**
     * 根据审批流程id获取对象信息
     * @param id
     * @return
     */
    public FlowApproval getFlowApprovalById(String id){
        return flowApprovalRepository.findOne(id);
    }
}
