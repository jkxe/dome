package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "case_repair")
public class CaseRepair extends BaseEntity{
  @ApiModelProperty(notes = "主键ID")
  private String id;

  @ApiModelProperty(notes = "修复状态 0-待修复，1-修复完成，2-已分配")
  private Integer repairStatus;

  @ApiModelProperty(notes = "操作员")
  @ManyToOne
  @JoinColumn(name = "operator")
  private User operator;

  @ApiModelProperty(notes = "操作时间")
  private Date operatorTime;

  @ApiModelProperty(notes = "创建时间")
  private Date createTime;

  @ApiModelProperty(notes = "公司code码")
  private String companyCode;

  @ApiModelProperty(notes = "借据号")
  private String loanInvoiceNumber;

  @ApiModelProperty(notes = "案件ID")
  @OneToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "case_id")
  private CaseInfo caseId;

  @ApiModelProperty(notes = "案件修复ID")
  @OneToMany
  @JoinColumn(name = "repair_id")
  private List<CaseRepairRecord> caseRepairRecordList;

  @ApiModelProperty("信修内容")
  private  String repairContent;

  @ApiModelProperty("申请信修id")
   private  String repairApplyId;

  @ApiModelProperty("已读/未读状态")
  private  String readStatus;



  /**
   * 案件修复状态
   */
  public enum CaseRepairStatus{
    REPAIRING(187,"待修复"),REPAIRED(188,"已修复"), DISTRIBUTE(189,"已分配");

    Integer value;
    String code;
    CaseRepairStatus(Integer value,String code){
      this.value=value;
      this.code=code;
    }

    public Integer getValue() {
      return value;
    }

    public void setValue(Integer value) {
      this.value = value;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }

}
