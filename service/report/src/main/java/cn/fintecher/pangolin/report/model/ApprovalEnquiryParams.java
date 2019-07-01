/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author : duanwenwu
 * @Description : 多条件查询审批管理
 * @Date : 18/1/16
 */
@Data
public class ApprovalEnquiryParams {
    private String personalName; //客户姓名
    private String mobileNo; //客户手机号
    private String idCard; //身份证
    private String batchNumber; //批次号
    private String principalId; //委托方ID
    private String principalName; //委托方名称
    private BigDecimal applyDerateMaxAmt;//减免最大费用
    private BigDecimal applyDerateMinAmt;//减免最小费用
    private Integer page; //页数
    private Integer size; //每页条数
    private String companyCode;//公司code码
    private BigDecimal overdueMaxAmount;//最大案件金额
    private BigDecimal overdueMinAmount;//最小案件金额
    private Integer overdueMaxDays;//最大逾期天数
    private Integer overdueMinDays;//最小逾期天数
    private Integer approvalStatus;//审批状态
    private String applicationDate;//申请日期
    private String applyRealName;//申请人

}