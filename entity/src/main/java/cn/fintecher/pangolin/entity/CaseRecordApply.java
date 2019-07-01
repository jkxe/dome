package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "case_record_apply")
@Data
public class CaseRecordApply extends  BaseEntity{

    @ApiModelProperty(notes = "审批流程id")
    private String approvalId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "案件来源")
    private Integer sourceType;

    @ApiModelProperty(notes = "案件去向")
    private Integer goalType;

    @Transient
    @ApiModelProperty(notes = "案件去向-自动分案使用这个字段")
    private Integer poolType;

    @ApiModelProperty(notes = "审批状态(0、代审批，1、审批中，2、审批结束)")
    private Integer approvalStatus;

    @ApiModelProperty(notes = "申请人")
    private String applyUser;

    @ApiModelProperty(notes = "申请时间")
    private Date applyTime;

}
