package cn.fintecher.pangolin.business.model.out;

import lombok.Data;
/**
 * 还款明细
 */
@Data
public class RepayDetailed {

    private String termNo;//还款期数
    private String pmtDueDate;//还款日期
    //	private String dueNum;//逾期期数
    private String totalCost;//应还金额
    private String principal;//应还本金
    private String interest;//应还利息
    private String mthFee;//应还平台管理费
    private String penaltyInt;//应还罚息
    private String defGold;//应还违约金
    //	private String dpdDays;//逾期天数
//	private String lateCharge;//逾期罚息
//	private String overBreachContract;//逾期违约金
//	private String surplusFine;//剩余罚息
    private String remainPrin;//剩余本金
    private String remainInt;//剩余利息
    private String resManFee;//剩余管理费

    private String otInt;//应还复利
    private String prinPaid;//已偿还本金
    private String intPaid;//已偿还利息
    private String otIntPaid;//已偿还复利
    private String lpcPaid;//已偿还违约金
    private String pnltIntPaid;//已偿还罚息
    private String mthFeePaid;//已偿还账户管理费
    private String repayTotalCost;//还款总额
    private String repaymentType;//还款类型
}
