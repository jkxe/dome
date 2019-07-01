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
 **@author duanwenwu
 *@Description 司法案件展示模型
 *@Date 18/1/11
 */
@Data
public class CaseInfoJudicialModel {
    private String caseId; //案件ID
    private String caseNumber; //案件编号
    private String batchNumber; //批次号
    private String personalName; //客户姓名
    private String personalId; //客户ID
    private String mobileNo; //手机号
    private String idCard; //身份证号
    private String principalName;//委托方
    private BigDecimal overdueAmount; //案件金额
    private String principalId; //委托方ID
    private String name;//委托方名称
    private String overduePeriods;//逾期期数
    private Integer overdueDays; //逾期天数
    private Date operatorTime;//处理日期
    private String state;//司法说明
    private String loanInvoiceNumber;//hy-借据号
}