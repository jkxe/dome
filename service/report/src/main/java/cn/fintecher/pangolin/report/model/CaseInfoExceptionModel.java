/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : duanwenwu
 * @Description : 展示异常案件模型
 * @Date :18/1/15
 */
@Data
public class CaseInfoExceptionModel {
    private String caseNumber; //案件编号
    private String batchNumber; //批次号
    private String principalName; //委托方名称
    private String city; //申请城市
    private String personalName;//客户姓名
    private String mobileNo; // 手机号
    private String overDuePeriods;// 逾期期数
    private Integer overDueDays; //逾期天数
    private Integer caseHandNum; //案件手数
    private BigDecimal overdueAmount; //案件金额
    private BigDecimal commissionRate; //佣金比例
    private Date delegationDate; //委案日期
    private Date closeDate; //到期日期
}