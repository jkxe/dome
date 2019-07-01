package cn.fintecher.pangolin.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "anti_fraud_record")
public class AntiFraudRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "主键ID")
    private Integer id;

    @ApiModelProperty(notes = "案件编号（借据号）")
    private String caseNumber;

    @ApiModelProperty(notes = "反欺诈结果（本人贷款830、非本人贷款831）")
    private Integer antiFraudResult;

    @ApiModelProperty(notes = "流转来源（电催817, 818, 外访818, 819, 委外819, 特殊820, 停催821,核心系统825,Excel导入826）")
    private Integer turnFromPool;

    @ApiModelProperty(notes = "案件金额")
    private BigDecimal overdueAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "迟案天数")
    private Integer holdDays;

    @ApiModelProperty(notes = "剩余天数")
    private Integer leftDays;

    @ApiModelProperty(notes = "催收状态")
    private Integer collectionStatus;

    @ApiModelProperty(notes = "催收员")
    private String collector;

    @ApiModelProperty(notes = "回收标识（0-未回收，1-回收）")
    private Integer recoverRemark;

    @ApiModelProperty(notes = "操作人")
    private String approver;

    @ApiModelProperty(notes = "创建时间")
    private Date creationTime;

    /**
     * 反欺诈结果枚举
     * 反欺诈结果（本人贷款830、非本人贷款831）
     */

    public enum AntiFraudResult {
        //本人贷款
        SELF_LOAN(808, "本人贷款"),
        //非本人贷款
        NOT_SELF_LOAN (809, "非本人贷款");
        private Integer value;

        private String remark;

        AntiFraudResult(Integer value, String remark) {
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

    public enum CollectionStatus{

        //待催收
        UNALLOCATED(861, "待分配"),

        FOLLOWUP(862, "跟进中");

        private Integer value;

        private String remark;

        CollectionStatus(Integer value, String remark) {
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
