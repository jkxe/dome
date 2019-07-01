package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CaseInfoRoamModel {

    private String caseNumber; //案件编号
    private String caseId;//案件id
    private String batchNumber;//批次号
    private String principalName;//委托方名称
    private String personalName;//客户姓名
    private String personalMobileNo;//客户手机号
    private String personalIdCard;//客户身份证号码
    private BigDecimal overdueAmount = new BigDecimal(0);//逾期总金额
    private Integer overduePeriods = 0;//逾期期数
    private Integer overdueDays = 0;//逾期天数
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
    private String caseRecordApplyId;//案件流转申请id
    private Integer turnApprovalStatus;//流转审核状态
    private String turnDescribe;//流转说明
    private String approvalOpinion;//审批意见
}
