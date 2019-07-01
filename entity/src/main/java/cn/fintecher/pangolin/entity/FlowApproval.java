
package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flow_approval")
@Data
public class FlowApproval extends BaseEntity {

    @ApiModelProperty(notes = "节点id")
    private String nodeId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;


    @ApiModelProperty(notes = "流程状态")
    private String processState;


    public enum ProcessState{

        PROCESS_STATE_NORMAL( "0" ,"正常"),
        PROCESS_STATE_END( "1" ,"结束"),
        PROCESS_STATE_REJECT( "2" ,"拒绝");

        private String value;
        private String remark;

        ProcessState(String value, String remark) {
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
