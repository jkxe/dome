package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 逾期信息展示模型
 */
@Data
public class OverdueInfoModel {

    private Integer payPeriod = new Integer(0);//贷款期数(还款期数)payPlan

    private Integer hasPayPeriods = new Integer(0);//还款期数(已还期数)caseInfo

    private Integer overduePeriods = new Integer(0);//逾期期数caseInfo

    private Integer OverdueDays = new Integer(0);//逾期天数caseInfo

    private BigDecimal ApprovedLoanAmt = new BigDecimal(0);//贷款金额(批准贷款金额)orderInfo

    private BigDecimal overdueAmount = new BigDecimal(0);//逾期金额caseInfo

    private BigDecimal overdueCapital = new BigDecimal(0);//逾期本金

    private BigDecimal overdueInterest = new BigDecimal(0);//逾期利息

    private BigDecimal unpaidOtherFee = new BigDecimal(0);//逾期其他费用(未尝还其他费用)

    private String cardAddress ;//身份证地址personal

    private BigDecimal unpaidFine = new BigDecimal(0);//未结罚息(未尝还罚息)

    private BigDecimal unpaidInterest = new BigDecimal(0);//未结利息(未偿还利息)

    private Date operatorTime;//案件更新时间

    private String verficationStatus;//hy-核销状态 0：否，1：是
    private BigDecimal overdueCapitalInterest= new BigDecimal(0); //逾期本息
    private BigDecimal overdueDelayFine= new BigDecimal(0);//逾期滞纳金
    private BigDecimal overdueFine= new BigDecimal(0);//逾期罚息
    private Integer loanPeriod;//贷款期数
    private BigDecimal accountBalance= new BigDecimal(0);//账户余额
    private BigDecimal overdueManageFee= new BigDecimal(0); //逾期手续费



}
