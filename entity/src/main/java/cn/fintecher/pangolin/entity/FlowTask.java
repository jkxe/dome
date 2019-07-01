package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flow_task")
@Data
public class FlowTask extends BaseEntity {

    @ApiModelProperty(notes = "任务名称")
    private String taskName;

    public enum TaskType{
        Assist_case(1030,"协催申请"),
        Circulation(1031,"流转申请"),
        Leave_case(1032,"留案申请"),
        Adjustment(1033,"调账申请"),
        Reconsideration(1034,"复议申请"),
        Deception_up(1035,"线上反欺诈"),
        Deception_down(1036,"线下反欺诈"),
        Lawsuit(1037,"诉讼申请"),
        AntiCase(2000,"反欺诈申请");
        private Integer value;
        private String remark;

        TaskType(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }
}

