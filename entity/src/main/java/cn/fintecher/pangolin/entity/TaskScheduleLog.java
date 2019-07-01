package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task_schedule_log")
@Data
public class TaskScheduleLog implements Serializable {

    @Id
    @ApiModelProperty(notes = "执行日期")
    private String execKey;


//    @ApiModelProperty(notes = "执行日期")
//    private String execDate;
//
//    @ApiModelProperty(notes = "任务编码")
//    private String taskCode;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    /**
     * 关系
     */
    public enum TaskCode {
        BATCH_SYNC_DATA("1", "核心同步导入跑批任务");
        private String value;
        private String remark;

        TaskCode(String value, String remark) {
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
