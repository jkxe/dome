package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;

@Data
@ApiModel("协催申请审批对象")
public class AssistApplyApprovaleModel {

    @NonNull
    private String assistApplyId;//申请协催案件id
    @NonNull
    private String caseNumber;  //案件编号
    @NonNull
    private Integer nodeState;  //审批状态（0、同意 1、驳回2、拒绝）
    @NonNull
    private String nodeOpinion; //审批意见
    private Integer step;  //驳回步数
    @NonNull
    private String approvalId;  //审批流程id
}
