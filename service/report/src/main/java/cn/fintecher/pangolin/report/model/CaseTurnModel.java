package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : chenjing
 * @Description : 案件流转记录展示模型
 * @Date : 10:19 2018/6/20
 */

@Data
public class CaseTurnModel {

    private String batchNumber;//批次号
    private String caseNumber;//案件编号
    private String principalName;//委托方
    private String personName;//客户名
    private String mobileNo;//手机号
    private String idCard;//身份证高
    private BigDecimal overdueAmount;//案件金额
    private Integer overduePeriods;//逾期期数
    private Integer overdueDays;//逾期天数
    private Integer holdDays;//持案天数
    private Integer leftDays;//流案天数
    private Integer turnFromPool;//流转来源
    private Integer turnToPool;//流转去向
    private Date operatorTime;//流转时间
    private String turnDescribe;//流转说明
    private Integer turnApprovalStatus;//流转审核状态
    private Integer triggerAction;//触发动作
    private Integer casePoolType;//触发动作
    private String approvalOpinion;//审批意见
    private String payStatus;//逾期阶段
    private Integer followupBack;//催收反馈
    private String currentCollector;//当前催员
    private String latelyCollector;//上一个催员
    private Date followupTime;//跟进时间
    private Date caseFollowInTime;//流入时间
    private String operatorUserName;//审批人
    private String approvalId;//审批流程id
    private String loanInvoiceNumber;//hy-借据号
    private String applyName;//申请人
    private String receiveUserRealName;//接受人名称

}
