package cn.fintecher.pangolin.dataimp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;


/**
 * 案件池
 * 
 * @author suyuchao
 * @email null
 * @date 2019-01-26 15:34:27
 */
@Data
@ApiModel("案件池")
public class CaseInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	

    @ApiModelProperty("主键")
	private String id;

    @ApiModelProperty("部门ID")
	private String departId;

    @ApiModelProperty("客户信息ID")
	private String personalId;

    @ApiModelProperty("省份编号")
	private Integer areaId;

    @ApiModelProperty("案件批次号")
	private String batchNumber;

    @ApiModelProperty("案件编号")
	private String caseNumber;

    @ApiModelProperty("催收类型(电催、外访、司法、委外、提醒)")
	private Integer collectionType;

    @ApiModelProperty("产品名称ID")
	private String productId;

    @ApiModelProperty("合同编号")
	private String contractNumber;

    @ApiModelProperty("合同金额")
	private BigDecimal contractAmount;

    @ApiModelProperty("逾期总金额")
	private BigDecimal overdueAmount;

    @ApiModelProperty("剩余本金")
	private BigDecimal leftCapital;

    @ApiModelProperty("剩余利息")
	private BigDecimal leftInterest;

    @ApiModelProperty("逾期本金")
	private BigDecimal overdueCapital;

    @ApiModelProperty("逾期利息")
	private BigDecimal overdueInterest;

    @ApiModelProperty("逾期罚息")
	private BigDecimal overdueFine;

    @ApiModelProperty("逾期滞纳金")
	private BigDecimal overdueDelayFine;

    @ApiModelProperty("还款期数")
	private Integer periods;

    @ApiModelProperty("每期还款日")
	private String perDueDate;

    @ApiModelProperty("每期还款金额")
	private BigDecimal perPayAmount;

    @ApiModelProperty("逾期期数")
	private Integer overduePeriods;

    @ApiModelProperty("逾期天数")
	private Integer overdueDays;

    @ApiModelProperty("逾期日期")
	private Date overDueDate;

    @ApiModelProperty("已还款金额")
	private BigDecimal hasPayAmount;

    @ApiModelProperty("已还款期数")
	private Integer hasPayPeriods;

    @ApiModelProperty("最近还款日期")
	private Date latelyPayDate;

    @ApiModelProperty("最近还款金额")
	private BigDecimal latelyPayAmount;

    @ApiModelProperty("上一个催员")
	private String latelyCollector;

    @ApiModelProperty("当前催员")
	private String currentCollector;

    @ApiModelProperty("协催标志")
	private Integer assistFlag;

    @ApiModelProperty("协催员")
	private String assistCollector;

    @ApiModelProperty("协催状态")
	private Integer assistStatus;

    @ApiModelProperty("协催方式")
	private Integer assistWay;

    @ApiModelProperty("持案天数")
	private Integer holdDays;

    @ApiModelProperty("剩余天数")
	private Integer leftDays;

    @ApiModelProperty("案件类型")
	private Integer caseType;

    @ApiModelProperty("留案标志")
	private Integer leaveCaseFlag;

    @ApiModelProperty("留案操作日期")
	private Date leaveDate;

    @ApiModelProperty("已留案天数")
	private Integer hasLeaveDays;

    @ApiModelProperty("打标标记")
	private Integer caseMark;

    @ApiModelProperty("案件流转次数")
	private Integer followUpNum;

    @ApiModelProperty("流入时间")
	private Date caseFollowInTime;

    @ApiModelProperty("还款状态")
	private String payStatus;

    @ApiModelProperty("委托方ID")
	private String principalId;

    @ApiModelProperty("催收状态")
	private Integer collectionStatus;

    @ApiModelProperty("订单ID")
	private String orderId;

    @ApiModelProperty("委案日期")
	private Date delegationDate;

    @ApiModelProperty("结案日期")
	private Date closeDate;

    @ApiModelProperty("佣金比例")
	private BigDecimal commissionRate;

    @ApiModelProperty("")
	private Integer handNumber;

    @ApiModelProperty("贷款日期")
	private Date loanDate;

    @ApiModelProperty("逾期管理费")
	private BigDecimal overdueManageFee;

    @ApiModelProperty("挂起标识")
	private Integer handUpFlag;

    @ApiModelProperty("减免金额")
	private BigDecimal derateAmt;

    @ApiModelProperty("实际还款金额")
	private BigDecimal realPayAmount;

    @ApiModelProperty("提前结清金额")
	private BigDecimal earlySettleAmt;

    @ApiModelProperty("提前结清实际还款金额")
	private BigDecimal earlyRealSettleAmt;

    @ApiModelProperty("提前结清减免金额")
	private BigDecimal earlyDerateAmt;

    @ApiModelProperty("其他金额")
	private BigDecimal otherAmt;

    @ApiModelProperty("案件评分")
	private BigDecimal score;

    @ApiModelProperty("操作员")
	private String operator;

    @ApiModelProperty("操作时间")
	private Date operatorTime;

    @ApiModelProperty("公司code码")
	private String companyCode;

    @ApiModelProperty("结案说明")
	private String endRemark;

    @ApiModelProperty("最新跟进时间")
	private Date followupTime;

    @ApiModelProperty("催收反馈")
	private Integer followupBack;

    @ApiModelProperty("承诺还款金额")
	private BigDecimal promiseAmt;

    @ApiModelProperty("承诺还款日期")
	private Date promiseTime;

    @ApiModelProperty("结案方式")
	private Integer endType;

    @ApiModelProperty("授信金额")
	private BigDecimal creditAmount;

    @ApiModelProperty("小流转审批状态")
	private Integer circulationStatus;

    @ApiModelProperty("导入案件时的备注")
	private String memo;

    @ApiModelProperty("首次还款日期")
	private Date firstPayDate;

    @ApiModelProperty("")
	private String accountAge;

    @ApiModelProperty("案件到期回收方式（0-自动回收，1-手动回收）")
	private Integer recoverWay;

    @ApiModelProperty("案件回收标识（0-未回收，1-已回收）")
	private Integer recoverRemark;

    @ApiModelProperty("案件池类型（内催-225、委外-226、特殊-801、停催-802、贷后预警-803）")
	private Integer casePoolType;

    @ApiModelProperty("流转来源（816, 内催,817, 电催,818, 外访,819, 委外,820, 特殊,821, 停催,822, 贷后预警,823,诉讼,824,反欺诈,825,核心系统,826,Excel导入）")
	private Integer turnFromPool;

    @ApiModelProperty("")
	private String productType;

    @ApiModelProperty("")
	private String productName;

    @ApiModelProperty("已执行期数")
	private Integer executedPeriods;

    @ApiModelProperty("逾期最大天数")
	private Integer maxOverdueDays;

    @ApiModelProperty("最近一次应还日期")
	private Date latesDateReturn;

    @ApiModelProperty("剩余期数")
	private Integer leftPeriods;

    @ApiModelProperty("未尝还本金")
	private BigDecimal unpaidPrincipal;

    @ApiModelProperty("账户余额")
	private BigDecimal accountBalance;

    @ApiModelProperty("未尝还利息")
	private BigDecimal unpaidInterest;

    @ApiModelProperty("未尝还罚息")
	private BigDecimal unpaidFine;

    @ApiModelProperty("未尝还其他利息")
	private BigDecimal unpaidOtherInterest;

    @ApiModelProperty("未尝还管理费")
	private BigDecimal unpaidMthFee;

    @ApiModelProperty("未尝还其他费用")
	private BigDecimal unpaidOtherFee;

    @ApiModelProperty("未尝还滞纳金")
	private BigDecimal unpaidLpc;

    @ApiModelProperty("当前未结罚息复利")
	private BigDecimal currPnltInterest;

    @ApiModelProperty("剩余月服务费")
	private BigDecimal remainFee;

    @ApiModelProperty("逾期账户数")
	private Integer overdueAccountNumber;

    @ApiModelProperty("內催次数")
	private Integer inColcnt;

    @ApiModelProperty("外包次数")
	private Integer outColcnt;

    @ApiModelProperty("结清金额")
	private BigDecimal settleAmount;

    @ApiModelProperty("归C时间")
	private Date cleanDate;

    @ApiModelProperty("结清时间")
	private Date settleDate;

    @ApiModelProperty("未结利息")
	private BigDecimal pnltInterest;

    @ApiModelProperty("未结罚息")
	private BigDecimal pnltFine;

    @ApiModelProperty("hy-进件申请编号")
	private String intoApplyId;

    @ApiModelProperty("hy-客户id")
	private String customerId;

    @ApiModelProperty("hy-进件时间")
	private Date intoTime;

    @ApiModelProperty("hy-来源渠道(线上线下)")
	private String sourceChannel;

    @ApiModelProperty("hy-催收方式（自催或者第三方）")
	private String collectionMethod;

    @ApiModelProperty("hy-放款时间")
	private Date loanPayTime;

    @ApiModelProperty("hy-银行信贷划分的等级：正常、关注、次级、可疑、损失")
	private String fiveLevel;

    @ApiModelProperty("hy-申请期数")
	private Integer applyPeriod;

    @ApiModelProperty("hy-授信期数")
	private Integer creditPeriod;

    @ApiModelProperty("hy-申请金额")
	private BigDecimal applyAmount;

    @ApiModelProperty("hy-放款期数")
	private Integer loanPeriod;

    @ApiModelProperty("hy-放款金额")
	private BigDecimal loanAmount;

    @ApiModelProperty("hy-逾期本息")
	private BigDecimal overdueCapitalInterest;

    @ApiModelProperty("hy-当期以前逾期利息")
	private BigDecimal overdueInterestBefore;

    @ApiModelProperty("hy-当期逾期利息")
	private BigDecimal overdueInterestCurrent;

    @ApiModelProperty("hy-当期以前未偿还滞纳金")
	private BigDecimal overdueDelayFineBefore;

    @ApiModelProperty("hy-当期剩余应缴滞纳金")
	private BigDecimal overdueDelayFineCurrent;

    @ApiModelProperty("hy-当前欠款总额")
	private BigDecimal currentDebtAmount;

    @ApiModelProperty("hy-贷款用途")
	private String loanPurpose;

    @ApiModelProperty("hy-借据号")
	private String loanInvoiceNumber;

    @ApiModelProperty("hy-当期应扣本金")
	private BigDecimal preRepayPrincipal;

    @ApiModelProperty("hy-垫付标记")
	private String advancesFlag;

    @ApiModelProperty("hy-本期之前逾期管理费（手续费）")
	private BigDecimal overdueManageFeeBefore;

    @ApiModelProperty("hy-本期逾期管理费（手续费）")
	private BigDecimal overdueManageFeeCurrent;

    @ApiModelProperty("hy-应还日期")
	private Date repayDate;

    @ApiModelProperty("hy-回迁标记")
	private String movingBackFlag;

    @ApiModelProperty("hy-核销状态 0：否，1：是")
	private String verficationStatus;

    @ApiModelProperty("hy-创建时间")
	private Date createTime;

    @ApiModelProperty("hy-更新时间")
	private Date updateTime;

    @ApiModelProperty("hy-停催时间")
	private Date stopTime;

    @ApiModelProperty("hy-风险类型(包含1线上高风险，2线上低风险，3线下高风险，4线下低风险)")
	private String riskType;

    @ApiModelProperty("hy-逾期次数")
	private Integer overdueCount;

    @ApiModelProperty("hy-取数日期")
	private Date busDate;

    @ApiModelProperty("hy-队列id")
	private String queueId;

    @ApiModelProperty("hy-队列名称")
	private String queueName;

    @ApiModelProperty("hy-分配时间(自动分案的时候会记录这个时间)")
	private Date allocateTime;

    @ApiModelProperty("hy-异常标记 0:无异常，1：有异常")
	private Integer exceptionFlag;

    @ApiModelProperty("hy-异常检查时间")
	private Date exceptionCheckTime;

    @ApiModelProperty("hy-异常类型")
	private String exceptionType;

}
