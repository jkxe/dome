package cn.fintecher.pangolin.dataimp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  CaseInfoDistributedImp 对象与CaseInfoDistributed一致，区别只是ID生成的规则不一样。
 * 如果使用 CaseInfoDistributed启动报错。
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.CaseInfoDistributedImp
 * @date 2018年06月27日 15:58
 */
@Data
public class CaseInfoDistributedImp {
    @ApiModelProperty("案件批次号")
    private String batchNumber;
    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;
    @ApiModelProperty(notes = "催收类型(电催、外访、司法、委外、提醒)")
    private Integer collectionType;
    @ApiModelProperty(notes = "合同编号")
    private String contractNumber;
    @ApiModelProperty(notes = "合同金额")
    private BigDecimal contractAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期总金额")
    private BigDecimal overdueAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期本金")
    private BigDecimal overdueCapital = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期利息")
    private BigDecimal overdueInterest = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期罚息")
    private BigDecimal overdueFine = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期滞纳金")
    private BigDecimal overdueDelayFine = new BigDecimal(0);
    @ApiModelProperty(notes = "还款期数")
    private Integer periods;
    @ApiModelProperty(notes = "每期还款日")
    private String perDueDate;
    @ApiModelProperty(notes = "每期还款金额")
    private BigDecimal perPayAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;
    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;
    @ApiModelProperty(notes = "逾期已还款金额")
    private BigDecimal hasPayAmount = new BigDecimal(0);
    @ApiModelProperty(notes = "已还款期数")
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
    @ApiModelProperty(notes = "剩余本金")
    private BigDecimal leftCapital = new BigDecimal(0);
    @ApiModelProperty(notes = "剩余利息")
    private BigDecimal leftInterest = new BigDecimal(0);
    @ApiModelProperty(notes = "结案说明")
    private String endRemark;
    @ApiModelProperty(notes = "逾期日期")
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
    @ApiModelProperty(notes = "账户余额")
    private BigDecimal accountBalance = new BigDecimal(0);

}
