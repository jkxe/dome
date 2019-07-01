package cn.fintecher.pangolin.business.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProcessCaseRepairModel {

    @NonNull
    private String caseRepairApplyId;//案件修复申请id
    @NonNull
    private String caseId;  //案件id
    @NonNull
    private Integer nodeState;  //审批状态（0、同意 1、驳回2、拒绝）
    @NonNull
    private String nodeOpinion; //审批意见
    private Integer step;  //驳回步数
    @NonNull
    private String approvalId;  //审批流程id
}
