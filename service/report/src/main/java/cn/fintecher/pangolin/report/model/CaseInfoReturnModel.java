/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@author duanwenwu
 *@Description 回收案件展示模型
 *@Date 18/1/11
 */
@Data
public class CaseInfoReturnModel {
    private String caseId; //案件ID
    private String caseNumber; //案件编号
    private String batchNumber; //批次号
    private String personalName; //客户姓名
    private String personalId; //客户ID
    private String mobileNo; //手机号
    private String idCard; //身份证号
    private String outsName;// 委外方名称
    private BigDecimal overdueAmount; //案件金额
    private BigDecimal hasPayAmount; //已还款金额
    private Integer overduePeriods;//逾期期数
    private Integer overdueDays; //逾期天数
    private Date operatorTime;//回收时间
    private String reason;//回收说明
    private Date outTime;//委案日期
    private Date overOutsourceTime;//委案到期日期
    private String loanInvoiceNumber;//hy-借据号



}