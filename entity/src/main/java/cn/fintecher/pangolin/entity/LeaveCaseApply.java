package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "leave_case_apply")
@Entity
public class LeaveCaseApply extends BaseEntity{

    @ApiModelProperty(notes = "审批流程id")
    private String approvalId;

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "审批状态(0、代审批，1、审批中，2、同意，3、拒绝)")
    private Integer approvalStatus;

    @ApiModelProperty(notes = "流案原因")
    private String leaveReason;

    @ApiModelProperty(notes = "申请人")
    private String applyUser;

    @ApiModelProperty(notes = "申请时间")
    private Date applyTime;

    @ApiModelProperty(notes = "结案时间")
    private Date leftDate;
}
