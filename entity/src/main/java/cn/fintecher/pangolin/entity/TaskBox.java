package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "task_box")
@Data
@ApiModel(value = "taskBox", description = "任务盒子表")
public class TaskBox extends BaseEntity {

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "开始时间")
    private Date beginDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "任务状态")
    private Integer taskStatus;

    @ApiModelProperty(value = "任务描述")
    private String taskDescribe;

    @ApiModelProperty(value = "备注 || 导出文件URL地址")
    private String remark;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(value = "公司码")
    private String companyCode;


    /**
     * 任务类型
     */
    public enum Type {
        CHECK(294, "查看"), EXPORT(295, "导出"), IMPORT(296, "导入"), SYNC(297, "手工同步");
        private Integer value;
        private String remark;

        Type(Integer value, String remark) {
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
     * 任务状态
     */
    public enum Status {
        UN_FINISH(298, "未完成"), FINISHED(299, "已完成"), FAILURE(300, "失败"), UN_KNOW(301, "未知");
        private Integer value;
        private String remark;

        Status(Integer value, String remark) {
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
