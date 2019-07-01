package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "case_repair_apply")
@Data
public class CaseRepairApply extends  BaseEntity{

    @ApiModelProperty(notes = "审批流程id")
    private String approvalId;

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "借据号")
    private String loanInvoiceNumber;

    @ApiModelProperty(notes = "案件来源")
    private Integer sourceType;

    @ApiModelProperty(notes = "案件去向")
    private Integer goalType;

    @ApiModelProperty(notes = "审批状态(0、代审批，1、审批中，2、审批结束)")
    private Integer approvalStatus;

    @ApiModelProperty(notes = "申请人")
    private String applyUser;

    @ApiModelProperty(notes = "申请时间")
    private Date applyTime;
    @ApiModelProperty(notes = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(notes = "申请说明")
    private String letterDescribe;

    @ApiModelProperty("审批意见")
    private String repairApplyRemark;

    @ApiModelProperty("审批人")
    private String approvalUser;

    public enum AapprovalStatus{

        REFUSE( "1" ,"审批中"),
        WAIT_REG( "0" ,"待审核"),
        SUCCESS("2","审批通过"),
        ERR_FEID("3","拒绝");

        private String value;
        private String remark;

        AapprovalStatus(String value, String remark) {
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
