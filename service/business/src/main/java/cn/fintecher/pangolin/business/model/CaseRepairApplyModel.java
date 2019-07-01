package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CaseRepairApplyModel {

    private String caseNumber; //案件编号
    private String caseId;//案件id
    private String personalName;//客户姓名
    private String personalMobileNo;//客户手机号
    private String personalIdCard;//客户身份证号码
    private BigDecimal overdueAmount;//逾期总金额
    private Integer overduePeriods;//逾期期数
    private Integer overdueDays;//逾期天数
    private Integer followupBack;//催收反馈
    private String approvalId;//流程审批id
    private String taskId;//任务id
    private String taskName;//任务名称
    private String roleId;//角色id
    private String roleName;//角色名称
    private Integer collectionStatus;//催收状态
    private BigDecimal contractAmount;//合同金额
    private BigDecimal realPayAmount;//实际还款金额
    private Integer holdDays;//持按天数
    private Integer leftDays;//剩余天数
    private String applyUser;//申请人
    private Date applyTime;//申请时间
    private Integer sourceType; //流转来源
    private Integer goalType;//流转去向
    private String caseRepairApplyId;//案件修復申请id
    private Integer turnApprovalStatus;//流转审核状态
    private String turnDescribe;//流转说明
    private String approvalOpinion;//审批意见
    private String loanInvoiceNumber;// 借据号
    private String payStatus; // 逾期阶段
    private  String letterDescribe;//  信修申请说明
    private Integer approvalStatus;// 审核状态
    private  Date  operatorTime;  // 信修时间
    private  String  operator;  // 信修人员
    private  String fileUrl; // 附近地址
    private  String repairContent;// 信修内容
    private  Date approvalTime;// 审批时间
    private  String readStatus;// 未读状态

      private  String  id;  // 主键
}
