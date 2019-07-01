package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "flow_history")
@Data
public class FlowHistory extends BaseEntity {

    @ApiModelProperty(notes = "审批id")
    private String approvalId;

    @ApiModelProperty(notes = "任务id")
    private String taskId;

    @ApiModelProperty(notes = "节点id")
    private String nodeId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "审批状态")
    private String nodeState;

    @ApiModelProperty(notes = "审批意见")
    private String nodeOpinion;

    @ApiModelProperty(notes = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(notes = "审批人")
    private String approvalUser;

    public enum NodeState{

        REFUSE( "1" ,"驳回"),
        AGREE( "0" ,"同意"),
        REJECT( "2" ,"拒绝"),
        SUCCESS("3","申请成功");

        private String value;
        private String remark;

        NodeState(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public String getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }
}
