package cn.fintecher.pangolin.report.model.mobile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description: 移动端 案件信息 model
 * @Package cn.fintecher.pangolin.report.model.mobile
 * @ClassName: cn.fintecher.pangolin.report.model.mobile.CaseInfo4MobileVModel
 * @date 2018年09月30日 16:31
 */
@Data
public class CaseInfo4MobileVModel {

    @ApiModelProperty(notes = "主键")
    private String id;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "贷款金额")
    private BigDecimal approvedLoanAmt;

    @ApiModelProperty(notes = "逾期金额")
    private BigDecimal overdueAmount;

    @ApiModelProperty(notes = "贷款期数")
    private int loanTenure;

    @ApiModelProperty(notes = "还款期数")
    private int hasPayPeriods;

    @ApiModelProperty(notes = "逾期期数")
    private int overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private int overdueDays;

    @ApiModelProperty(notes = "逾期本金")
    private BigDecimal overdueCapital;

    @ApiModelProperty(notes = "逾期利息")
    private BigDecimal unpaidInterest;

    @ApiModelProperty(notes = "逾期本息")
    private BigDecimal overdueCapitalInterest;

    @ApiModelProperty(notes = "逾期滞纳金")
    private BigDecimal overdueDelayFine;

    @ApiModelProperty(notes = "逾期手续费")
    private BigDecimal overdueManageFee;

    @ApiModelProperty(notes = "逾期罚息")
    private BigDecimal overdueFine;

    @ApiModelProperty(notes = "未结罚息")
    private BigDecimal pnltFine;

    @ApiModelProperty(notes = "未结利息")
    private BigDecimal pnltInterest;

    @ApiModelProperty(notes = "hy-借据号")
    private String loanInvoiceNumber;

    @ApiModelProperty(notes = "hy-借据号List")
    private List<String> loanInvoiceNumbers;



}
