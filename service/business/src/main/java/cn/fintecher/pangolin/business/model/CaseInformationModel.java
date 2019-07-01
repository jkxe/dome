package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CaseInformationModel {

    @ApiModelProperty(notes = "逾期本金")
    private BigDecimal overdueCapital = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "最大逾期天数")
    private Integer maxOverdueDays;

    @ApiModelProperty(notes = "最近一次应还款日期")
    private Date latesDateReturn;

    @ApiModelProperty(notes = "逾期总金额")
    private BigDecimal overdueAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "已还款期数")
    private Integer hasPayPeriods;

    @ApiModelProperty(notes = "剩余期数")
    private Integer leftPeriods;

    @ApiModelProperty(notes = "未尝还本金")
    private BigDecimal unpaidPrincipal = new BigDecimal(0);

    @ApiModelProperty(notes = "未尝还利息")
    private BigDecimal unpaidInterest = new BigDecimal(0);

    @ApiModelProperty(notes = "未尝还罚息")
    private BigDecimal unpaidFine = new BigDecimal(0);

    @ApiModelProperty(notes = "未尝还其他利息")
    private BigDecimal unpaidOtherInterest = new BigDecimal(0);

    @ApiModelProperty(notes = "未尝还管理费")
    private BigDecimal unpaidMthFee = new BigDecimal(0);

    @ApiModelProperty(notes = "未尝还其他费用")
    private BigDecimal unpaidOtherFee = new BigDecimal(0);

    @ApiModelProperty(notes = "未尝还滞纳金")
    private BigDecimal unpaidLpc = new BigDecimal(0);

    @ApiModelProperty(notes = "当前未结罚息复利")
    private BigDecimal currPnltInterest = new BigDecimal(0);

    @ApiModelProperty(notes = "最近还款日期")
    private Date latelyPayDate;

    @ApiModelProperty(notes = "最近还款金额")
    private BigDecimal latelyPayAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "剩余本金")
    private BigDecimal leftCapital = new BigDecimal(0);

    @ApiModelProperty(notes = "剩余利息")
    private BigDecimal leftInterest = new BigDecimal(0);

    @ApiModelProperty(notes = "剩余月服务费")
    private BigDecimal remainFee = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期账户数")
    private Integer overdueAccountNumber;

    @ApiModelProperty(notes = "内崔次数")
    private Integer inColcnt;

    @ApiModelProperty(notes = "外包次数")
    private Integer outColcnt;

    @ApiModelProperty(notes = "已执行期数")
    private Integer executedPeriods;

    @ApiModelProperty(notes = "产品类型名称")
    private String seriesName;

    @ApiModelProperty(notes = "产品名称")
    private String productName;

    @ApiModelProperty(notes = "案件归属地")
    private String areaName;

    @ApiModelProperty(notes = "还款计划")
    private List<PayPlan> payPlans;

    @ApiModelProperty(notes = "核销还款明细")
    private List<WriteOffDetails> writeOffDetailsList;

    @ApiModelProperty(notes = "订单还款计划")
    private OrderRepaymentPlan orderRepaymentPlan;

    @ApiModelProperty(notes = "订单信息")
    private OrderInfo orderInfo;

    @ApiModelProperty(notes = "客户备注信息")
    private Material material;

    @ApiModelProperty(notes = "hy-借据号")
    private String loanInvoiceNumber;

    @ApiModelProperty(notes = "hy-放款时间")
    private Date loanPayTime;

    @ApiModelProperty(notes = "hy-申请期数")
    private Integer applyPeriod;

    @ApiModelProperty(notes = "hy-申请金额")
    private BigDecimal applyAmount;

    @ApiModelProperty(notes = "hy-授信期数")
    private Integer creditPeriod;

    @ApiModelProperty(notes = "授信金额")
    private BigDecimal creditAmount;

    @ApiModelProperty(notes = "hy-放款期数")
    private Integer loanPeriod;

    @ApiModelProperty(notes = "hy-放款金额")
    private BigDecimal loanAmount;

    @ApiModelProperty(notes = "hy-逾期本息")
    private BigDecimal overdueCapitalInterest;

    @ApiModelProperty(notes = "逾期利息")
    private BigDecimal overdueInterest;

    @ApiModelProperty(notes = "逾期滞纳金")
    private BigDecimal overdueDelayFine;

    @ApiModelProperty(notes = "逾期管理费")
    private BigDecimal overdueManageFee;

    @ApiModelProperty(notes = "逾期罚息")
    private BigDecimal overdueFine;

    @ApiModelProperty(notes = "合同编号")
    private String contractNumber;

    @ApiModelProperty(notes = "hy-当期应扣本金")
    private BigDecimal preRepayPrincipal;

    @ApiModelProperty(notes = "hy-垫付标记")
    private String advancesFlag;

    @ApiModelProperty(notes = "hy-应还日期")
    private Date repayDate;

    @ApiModelProperty(notes = "hy-回迁标记")
    private String movingBackFlag;

    @ApiModelProperty(notes = "hy-核销状态 0：否，1：是")
    private String verficationStatus;

    @ApiModelProperty(notes = "hy-进件时间")
    private Date intoTime;

    @ApiModelProperty(notes = "hy-当前欠款总额")
    private BigDecimal currentDebtAmount;

    @ApiModelProperty(notes = "贷款余额")
    private BigDecimal accountBalance;

    @ApiModelProperty(notes = "hy-还款账号")
    private String repayAccountNo;

    @ApiModelProperty(notes = "hy-还款银行")
    private String repayBank;

    @ApiModelProperty(notes = "hy-贷款用途")
    private String loanPurpose;

    @ApiModelProperty(notes = "hy-更新时间")
    private Date updateTime;

    @ApiModelProperty(notes = "hy-创建时间")
    private Date createTime;


}
