/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.report.model;

import cn.fintecher.pangolin.entity.CaseInfoReturn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@author duanwenwu
 *@description 委外案件的待分配查询结果
 *@date 18/2/28
 */
@Data
public class OutSourceDistModel {

    @ApiModelProperty(value = "案件ID")
    private String caseId;

    @ApiModelProperty(value = "委外ID")
    private String outId;

    @ApiModelProperty(value = "回收案件ID")
    private String id;

    @ApiModelProperty(value = "案件编号")
    private String caseNumber;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "批次号")
    private String batchNumber;

    @ApiModelProperty(value = "客户姓名")
    private String personalName;

    @ApiModelProperty(value = "客户id")
    private String personalId;

    @ApiModelProperty(value = "手机号")
    private String mobileNo;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "省份")
    private String areaName;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "逾期期数")
    private String payStatus;

    @ApiModelProperty(value = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(value = "逾期总金额")
    private BigDecimal overdueAmount;

    @ApiModelProperty(value = "佣金比率")
    private Double commissionRate;

    @ApiModelProperty(value = "委外日期")
    private Date outTime;

    @ApiModelProperty(value = "到期日期")
    private Date overOutSourceTime;

    @ApiModelProperty(value = "结案日期")
    private Date endOutsourceTime;

    @ApiModelProperty(value = "回收时间")
    private Date returnDate;

    @ApiModelProperty(value = "回收说明")
    private String returnReason;

    @ApiModelProperty(value = "受托方名称")
    private String outSourceName;

    @ApiModelProperty(value = "委外回款金额")
    private BigDecimal outBackAmt;

    @ApiModelProperty(value = "案件金额")
    private BigDecimal contractAmt;

    @ApiModelProperty(value = "产品类型")
    private String seriesName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "委托方名称")
    private String principalName;

    @ApiModelProperty(value = "催收员姓名")
    private String collector;

    @ApiModelProperty(value = "留案标识")
    private Integer leaveCaseFlag;

    @ApiModelProperty(value = "跟进时间")
    private Date followupTime;

    @ApiModelProperty(value = "催收反馈")
    private Integer followupBack;

    @ApiModelProperty(value = "持案天数")
    private Integer holdDays;

    @ApiModelProperty(value = "剩余天数")
    private Integer leftDays;

    @ApiModelProperty(value = "流转来源")
    private Integer turnFromPool;

    @ApiModelProperty(value = "案件流入时间")
    private Date caseFollowInTime;

    @ApiModelProperty(value = "诉讼阶段")
    private Integer lawsuitResult;

    @ApiModelProperty(value = "反欺诈结果")
    private Integer antiFraudResult;

    @ApiModelProperty(value = "流转去向")
    private Integer turnToPool;

    @ApiModelProperty(value = "案件分配时间")
    private String distributeTime;

    @ApiModelProperty(value = "流转状态")
    private Integer turnStatus;

    @ApiModelProperty(value = "留案到期日期")
    private Date closeDate;

    @ApiModelProperty(notes = "账户余额")
    private BigDecimal accountBalance;

    @ApiModelProperty("hy-借据号")
    private String loanInvoiceNumber;

    private String sourceChannel;// 来源渠道
    private String collectionMethod;// 催收方式
    private Integer overdueCount;//逾期次数

    @ApiModelProperty(notes = "hy-回收类型 0自动回收 1手动回收")
    private Integer returnType;

    @ApiModelProperty("hy-队列名称")
    private String queueName;

    public String getReturnTypeValue() {
        return CaseInfoReturn.ReturnType.getEnumByCode(this.returnType) != null
                ? CaseInfoReturn.ReturnType.getEnumByCode(this.returnType).getRemark() : "";
    }
}