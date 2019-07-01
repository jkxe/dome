package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pay_plan")
@Data
public class PayPlan extends BaseEntity {

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "还款期数")
    private Integer payPeriod;

    @ApiModelProperty(notes = "还款日期")
    private Date payDate;

//    @ApiModelProperty(notes = "逾期期数")
//    private Integer overduePeriod;

    @ApiModelProperty(notes = "应还金额")
    private BigDecimal payAmt;

    @ApiModelProperty(notes = "应还本金")
    private BigDecimal payPrincipal;

    @ApiModelProperty(notes = "应还利息")
    private BigDecimal payInterest;

    @ApiModelProperty(notes = "应还平台管理费")
    private BigDecimal payPlatformFee;

    @ApiModelProperty(notes = "应还罚息")
    private BigDecimal payFine;

    @ApiModelProperty(notes = "应还违约金")
    private BigDecimal payLiquidated;

//    @ApiModelProperty(notes = "逾期天数")
//    private Integer overDays;

//    @ApiModelProperty(notes = "逾期罚息")
//    private BigDecimal overFine;

//    @ApiModelProperty(notes = "逾期违约金")
//    private BigDecimal overLiquidated;

//    @ApiModelProperty(notes = "剩余罚息")
//    private BigDecimal surplusFine;

    @ApiModelProperty(notes = "剩余本金")
    private BigDecimal surplusPrincipal;

    @ApiModelProperty(notes = "剩余利息")
    private BigDecimal surplusInterest;

    @ApiModelProperty(notes = "剩余管理费")
    private BigDecimal surplusPlatformFee;

    @ApiModelProperty(notes = "应还复利")
    private BigDecimal payNoun;

    @ApiModelProperty(notes = "已偿还本金")
    private BigDecimal hasPayPrincipal;

    @ApiModelProperty(notes = "已偿还利息")
    private BigDecimal hasPayInterest;

    @ApiModelProperty(notes = "已偿还复利")
    private BigDecimal hasPayNoun;

    @ApiModelProperty(notes = "已偿还违约金")
    private BigDecimal hasPayLiquidated;

    @ApiModelProperty(notes = "已偿还罚息")
    private BigDecimal hasPayFine;

    @ApiModelProperty(notes = "已偿还账号管理费")
    private BigDecimal hasPayManagement;

    @ApiModelProperty(notes = "还款总额")
    private BigDecimal payAmount;

    @ApiModelProperty(notes = "还款类型")
    private Integer payType;
}
