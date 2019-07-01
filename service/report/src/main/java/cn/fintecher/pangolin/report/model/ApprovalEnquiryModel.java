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
 * @Description : 多条件查询审批管理
 * @Date : 18/1/16
 */
@Data
public class ApprovalEnquiryModel {
    private String caseNumber; //案件编号
    private String batchNumber; //批次号
    private String personalName; //客户姓名
    private String mobileNo; //手机号
    private String idCard;//身份证号
    private String principalName; //委托方名称
    private Integer approveStatus; //还款状态（审批状态）
    private BigDecimal applyDerateAmt;//减免费用
    private Integer payType; //减免类型（还款类型）
    private Integer approveResult;//审批结果
    private Date applyDate;//申请日期
    private Date applyInvalidTime;//申请失效日期
    private String applyRealName;//申请人
    private Integer overdueDays;//逾期天数
    private String overduePeriods;//逾期期数
    private BigDecimal overdueAmount;//案件金额
    private String applicationReason;//申请理由
    private Integer assistWay;//协催方式
    private Integer approvePhoneResult;//电催审批结果
    private Integer approveOutResult;//外访审批结果
}