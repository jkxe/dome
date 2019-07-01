package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "task_batch_import_log")
@Data
public class TaskBatchImportLog {
    public TaskBatchImportLog(){
        this.totalCount=0;
        this.successCount=0;
        this.failedCount=0;
        this.bak1=0;
    }

    @Id
    @ApiModelProperty(notes = "id主键")
    private String id;

    @ApiModelProperty(notes = "父任务id")
    private String parentId;

    @ApiModelProperty(notes = "跑批日期")
    private String taskDate;

    @ApiModelProperty(notes = "任务类型")
    private Integer taskType;

    @ApiModelProperty(notes = "任务名称")
    private String taskName;

    @ApiModelProperty(notes = "开始时间")
    private Date startTime;

    @ApiModelProperty(notes = "结束时间")
    private Date endTime;

    @ApiModelProperty(notes = "总数量")
    private Integer totalCount;

    @ApiModelProperty(notes = "成功数量")
    private Integer successCount;

    @ApiModelProperty(notes = "失败数量")
    private Integer failedCount;

    @ApiModelProperty(notes = "失败code")
    private String failedCode;

    @ApiModelProperty(notes = "失败消息")
    private String failedMsg;

    @ApiModelProperty(notes = "任务状态")
    private Integer taskState;

    @ApiModelProperty(notes = "备用字段")
    private Integer bak1;

    /**
     * 批量任务名称
     */
    public enum BatchTaskName {
        BATCH_TATAL_TASK("0", "跑批任务-总任务"),
        BATCH_CUSTOMER_DETAIL_TASK("1", "跑批任务-客户明细"),
        BATCH_OVERDUE_DETAIL_TASK("2", "跑批任务-逾期明细"),
        BATCH_CUSTOMER_ACCOUNT_TASK("3", "跑批任务-客户开户信息"),
        BATCH_CUSTOMER_RELATION_TASK("4", "跑批任务-客户关联人"),
        BATCH_CUSTOMER_IMG_FILE_TASK("5", "跑批任务-客户影像文件"),
        BATCH_CUSTOMER_FILE_ATTACH_TASK("6", "跑批任务-客户文本文件"),
        BATCH_CUSTOMER_SOCIAL_PLAT_TASK("7", "跑批任务-客户社交平台"),
        BATCH_CUSTOMER_ASTOPERCRDT_TASK("8", "跑批任务-客户资产经营征信信息");
        private String value;
        private String remark;

        BatchTaskName(String value, String remark) {
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
    /**
     * 批量任务状态
     */
    public enum BatchTaskState {
        EXECUTED(0, "执行结束"),
        EXECUTING(1, "执行中");
        private Integer value;
        private String remark;

        BatchTaskState(Integer value, String remark) {
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
    /**
     * 批量任务类型
     */
    public enum BatchTaskType {
        MANUAL(0, "手动触发"),
        AUTOMATION(1, "自动定时");
        private Integer value;
        private String remark;

        BatchTaskType(Integer value, String remark) {
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
