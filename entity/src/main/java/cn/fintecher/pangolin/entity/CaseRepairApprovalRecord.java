package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ZhangYaJun
 * @Title: CaseRepairApprovalRecord
 * @ProjectName pangolin-server-hzyh
 * @Description:   审批记录
 * @date 2019/3/2 0002下午 23:03
 */
@Data
@Entity
@Table(name = "case_repair_approval_record")
public class CaseRepairApprovalRecord extends BaseEntity {

   @ApiModelProperty(notes = "申请信修案件id")
   private String  repairId;

   @ApiModelProperty(notes = "案件号")
   private String  caseNumber;

   @ApiModelProperty(notes = "借据号")
   private String  loanInvoiceNumber;

   @ApiModelProperty(notes = "流程id")
   private String  approvalId;

   @ApiModelProperty(notes = "审批状态")
   private Integer  approvalStatus;

   @ApiModelProperty(notes = "审批意见")
   private String  approvalOpinion;

   @ApiModelProperty(notes = "审批时间")
   private Date approvalTime;

   @ApiModelProperty(notes = "审批人")
   private String  approvalUser;



}
