package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 案件待分配池
 */
@Entity
@Table(name = "case_info_distributed")
@Data
public class CaseInfoDistributed extends BaseEntity {
    @ApiModelProperty("案件批次号")
    private String batchNumber;
    @ApiModelProperty(notes = "案件编号", name = "申请号")
    private String caseNumber;
    @ApiModelProperty(notes = "催收类型(电催、外访、司法、委外、提醒)")
    private Integer collectionType;
    @ApiModelProperty(notes = "合同编号" , name ="合同号")
    private String contractNumber;
    @ApiModelProperty(notes = "合同金额", name ="合同金额")
    private BigDecimal contractAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期总金额", name ="逾期总额")
    private BigDecimal overdueAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期本金", name ="逾期本金")
    private BigDecimal overdueCapital = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期利息", name ="逾期利息")
    private BigDecimal overdueInterest = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期罚息", name ="逾期罚息")
    private BigDecimal overdueFine = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期滞纳金", name ="逾期滞纳金")
    private BigDecimal overdueDelayFine = new BigDecimal(0);
    @ApiModelProperty(notes = "还款期数", name ="还款期数")
    private Integer periods;
    @ApiModelProperty(notes = "每期还款日", name ="每期还款日")
    private String perDueDate;
    @ApiModelProperty(notes = "每期还款金额")
    private BigDecimal perPayAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期期数", name ="逾期期数")
    private Integer overduePeriods;
    @ApiModelProperty(notes = "逾期天数", name ="逾期天数")
    private Integer overdueDays;
    @ApiModelProperty(notes = "逾期已还款金额", name ="逾期已还款金额")
    private BigDecimal hasPayAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "已还款期数", name ="已还款期数")
    private Integer hasPayPeriods;
    @ApiModelProperty(notes = "最近还款日期")
    private Date latelyPayDate;
    @ApiModelProperty(notes = "最近还款金额")
    private BigDecimal latelyPayAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "协催标识：0-未协催，1-协催")
    private Integer assistFlag;
    @ApiModelProperty(notes = "协催状态")
    private Integer assistStatus;
    @ApiModelProperty(notes = "协催方式")
    private Integer assistWay;
    @ApiModelProperty(notes = "持案天数")
    private Integer holdDays = 0;
    @ApiModelProperty(notes = "剩余天数")
    private Integer leftDays;
    @ApiModelProperty(notes = "案件类型(0案件分配1电催小流转2电催强制流转3电催提前流转4电催保留流转外访小流转")
    private Integer caseType;
    @ApiModelProperty("0-未留案，1-留案")
    private Integer leaveCaseFlag;
    @ApiModelProperty(notes = "留案日期")
    private Date leaveDate;
    @ApiModelProperty(notes = "已留案天数")
    private Integer hasLeaveDays;
    @ApiModelProperty(notes = "案件流转次数")
    private Integer followUpNum = 0;
    @ApiModelProperty(notes = "流入时间")
    private Date caseFollowInTime;
    @ApiModelProperty(notes = "还款状态")
    private String payStatus;
    @ApiModelProperty(notes = "订单ID")
    private String orderId;
    @ApiModelProperty(notes = "催收状态")
    private Integer collectionStatus;
    @ApiModelProperty("委案日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delegationDate;
    @ApiModelProperty("结案日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeDate;
    @ApiModelProperty(notes = "佣金比例")
    private BigDecimal commissionRate = new BigDecimal(0);
    @ApiModelProperty(notes = "贷款日期")
    private Date loanDate;
    @ApiModelProperty("案件手数")
    private Integer handNumber;
    @ApiModelProperty(notes = "逾期管理费")
    private BigDecimal overdueManageFee = new BigDecimal(0);
    @ApiModelProperty(notes = "挂起标识")
    private Integer handUpFlag;
    @ApiModelProperty(notes = "逾期减免金额")
    private BigDecimal derateAmt = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期实际还款金额")
    private BigDecimal realPayAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "提前结清已还款金额")
    private BigDecimal earlySettleAmt = new BigDecimal(0);
    @ApiModelProperty(notes = "提前结清实际还款金额")
    private BigDecimal earlyRealSettleAmt = new BigDecimal(0);
    @ApiModelProperty(notes = "提前结清减免金额")
    private BigDecimal earlyDerateAmt = new BigDecimal(0);
    @ApiModelProperty(notes = "其他金额")
    private BigDecimal otherAmt = new BigDecimal(0);
    @ApiModelProperty(notes = "案件评分")
    private BigDecimal score = new BigDecimal(0);
    @ApiModelProperty(notes = "公司code码")
    private String companyCode;
    @ApiModelProperty(notes = "剩余本金", name ="剩余本金")
    private BigDecimal leftCapital = new BigDecimal(0);
    @ApiModelProperty(notes = "剩余利息", name ="剩余利息")
    private BigDecimal leftInterest = new BigDecimal(0);
    @ApiModelProperty(notes = "结案说明")
    private String endRemark;
    @ApiModelProperty(notes = "逾期日期", name ="逾期日期")
    private Date overDueDate;

    private Date operatorTime;
    private Integer caseMark;
    @ApiModelProperty("导入案件时Excel中的备注")
    private String memo;
    @ApiModelProperty("首次还款日期")
    private Date firstPayDate;
    @ApiModelProperty("账龄")
    private String accountAge;
    @ApiModelProperty("案件到期回收方式：0-自动回收，1-手动回收")
    private Integer recoverWay;
    @ApiModelProperty("回收标志：0-未回收，1-已回收")
    private Integer recoverRemark;
    @ApiModelProperty("回收说明")
    private String recoverMemo;


    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personalInfo;

    @ManyToOne
    @JoinColumn(name = "depart_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaCode area;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "principal_id")
    private Principal principalId;

    @ManyToOne
    @JoinColumn(name = "operator")
    private User operator;

    @ApiModelProperty(notes = "产品类型", name ="产品类型")
    private String productType;

    @ApiModelProperty(notes = "产品名称", name ="产品名称")
    private String productName;

    @ApiModelProperty(notes = "案件是否处理")
    private String isProcessed;

    @ApiModelProperty("案件类型：内催225、委外226、特殊801、停催802、贷后预警803")
    private Integer casePoolType;

    @ApiModelProperty(notes = "已执行期数")
    private Integer executedPeriods;

    @ApiModelProperty(notes = "最大逾期天数")
    private Integer maxOverdueDays;

    @ApiModelProperty(notes = "最近一次应还款日期")
    private Date latesDateReturn;

    @ApiModelProperty(notes = "剩余期数")
    private Integer leftPeriods;

    @ApiModelProperty(notes = "未尝还本金")
    private BigDecimal unpaidPrincipal;

    @ApiModelProperty(notes = "未尝还利息")
    private BigDecimal unpaidInterest;

    @ApiModelProperty(notes = "未尝还罚息")
    private BigDecimal unpaidFine;

    @ApiModelProperty(notes = "未尝还其他利息")
    private BigDecimal unpaidOtherInterest;

    @ApiModelProperty(notes = "未尝还管理费")
    private BigDecimal unpaidMthFee;

    @ApiModelProperty(notes = "未尝还其他费用")
    private BigDecimal unpaidOtherFee;

    @ApiModelProperty(notes = "未尝还滞纳金")
    private BigDecimal unpaidLpc;

    @ApiModelProperty(notes = "当前未结罚息复利")
    private BigDecimal currPnltInterest;

    @ApiModelProperty(notes = "未结利息")
    private BigDecimal pnltInterest;

    @ApiModelProperty(notes = "未结罚息")
    private BigDecimal pnltFine;

    @ApiModelProperty(notes = "剩余月服务费")
    private BigDecimal remainFee;

    @ApiModelProperty(notes = "逾期账户数")
    private Integer overdueAccountNumber;

    @ApiModelProperty(notes = "内崔次数")
    private Integer inColcnt;

    @ApiModelProperty(notes = "外包次数")
    private Integer outColcnt;

    @ApiModelProperty(notes = "账户余额", name = "账户余额")
    private BigDecimal accountBalance;

    @ApiModelProperty(notes = "结清金额")
    private BigDecimal settleAmount;

    @ApiModelProperty(notes = "结清时间")
    private Date settleDate;

    @ApiModelProperty(notes = "归C时间")
    private Date cleanDate;

    @Transient
    private String certificatesNumber;

    @ApiModelProperty(notes = "hy-进件申请编号")
    private String intoApplyId;

    @ApiModelProperty(notes = "hy-客户id")
    private String customerId;

    @ApiModelProperty(notes = "hy-进件时间")
    private Date intoTime;

    @ApiModelProperty(notes = "hy-来源渠道(线上线下)")
    private String sourceChannel;

    @ApiModelProperty(notes = "hy-催收方式（自催或者第三方）")
    private String collectionMethod;

    @ApiModelProperty(notes = "hy-放款时间")
    private Date loanPayTime;

    @ApiModelProperty(notes = "hy-银行信贷划分的等级：正常、关注、次级、可疑、损失")
    private String fiveLevel;

    @ApiModelProperty(notes = "hy-申请期数")
    private Integer applyPeriod;

    @ApiModelProperty(notes = "hy-授信期数")
    private Integer creditPeriod;

    @ApiModelProperty(notes = "hy-申请金额")
    private BigDecimal applyAmount;

    @ApiModelProperty(notes = "授信金额")
    private BigDecimal creditAmount;

    @ApiModelProperty(notes = "hy-放款期数")
    private Integer loanPeriod;

    @ApiModelProperty(notes = "hy-放款金额")
    private BigDecimal loanAmount;

    @ApiModelProperty(notes = "hy-逾期本息")
    private BigDecimal overdueCapitalInterest;

    @ApiModelProperty(notes = "hy-当期以前逾期利息")
    private BigDecimal overdueInterestBefore;

    @ApiModelProperty(notes = "hy-当期逾期利息")
    private BigDecimal overdueInterestCurrent;

    @ApiModelProperty(notes = "hy-当期以前未偿还滞纳金")
    private BigDecimal overdueDelayFineBefore;

    @ApiModelProperty(notes = "hy-当期剩余应缴滞纳金")
    private BigDecimal overdueDelayFineCurrent;

    @ApiModelProperty(notes = "hy-当前欠款总额")
    private BigDecimal currentDebtAmount;

    @ApiModelProperty(notes = "hy-贷款用途")
    private String loanPurpose;

    @ApiModelProperty(notes = "hy-借据号")
    private String loanInvoiceNumber;

    @ApiModelProperty(notes = "hy-当期应扣本金")
    private BigDecimal preRepayPrincipal;

    @ApiModelProperty(notes = "hy-垫付标记")
    private String advancesFlag;

    @ApiModelProperty(notes = "hy-本期之前逾期管理费（手续费）")
    private BigDecimal overdueManageFeeBefore;

    @ApiModelProperty(notes = "hy-本期逾期管理费（手续费）")
    private BigDecimal overdueManageFeeCurrent;

    @ApiModelProperty(notes = "hy-应还日期")
    private Date repayDate;

    @ApiModelProperty(notes = "hy-回迁标记")
    private String movingBackFlag;

    @ApiModelProperty(notes = "hy-核销状态 0：否，1：是")
    private String verficationStatus;

    @ApiModelProperty(notes = "hy-创建时间")
    private Date createTime;

    @ApiModelProperty(notes = "hy-更新时间")
    private Date updateTime;

    @ApiModelProperty(notes = "hy-停催时间")
    private Date stopTime;

    @ApiModelProperty(notes = "hy-风险类型(包含1线上高风险，2线上低风险，3线下高风险，4线下低风险)")
    private String riskType;

    @ApiModelProperty(notes = "hy-逾期次数")
    private Integer overdueCount;

    @ApiModelProperty(notes = "hy-取数日期")
    private Date busDate;

    @ApiModelProperty(notes = "hy-异常标记 0:无异常，1：有异常")
    private Integer exceptionFlag;

    @ApiModelProperty(notes = "hy-异常检查时间")
    private Date exceptionCheckTime;

    @ApiModelProperty(notes = "hy-异常类型 数据字典type_code:0403")
    private Integer exceptionType;

    @ApiModelProperty(notes = "hy-经销商名称")
    private String merchantName;

    @ApiModelProperty(notes = "hy-门店名称")
    private String storeName;

    @ApiModelProperty(notes = "hy-还款账号")
    private String repayAccountNo;

    @ApiModelProperty(notes = "hy-还款银行")
    private String repayBank;

}
