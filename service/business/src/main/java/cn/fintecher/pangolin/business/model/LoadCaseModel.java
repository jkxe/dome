package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LoadCaseModel {

    private String caseNumber;
    private String custName;
    private String idCard;
    private String phone;
    private Integer periods;
    private Integer overduePeriods;
    private Integer overDays;
    private BigDecimal overdueCapital = new BigDecimal(0);
    private BigDecimal overAmt = new BigDecimal(0);
    private BigDecimal accountBalance = new BigDecimal(0);
    private Date loanDate;
    private String payStatus;
    private String seriesName;
    private String productName;
    private String city;
    private Integer caseStatus;
    private Integer caseType;
    private String deptName;
    private String currentCollector;
    private Date cleanDate;
    private Date settleDate;
    private String loanInvoiceNumber;
    private BigDecimal approvedLoanAmt = new BigDecimal(0);
    private Integer overdueCount;//逾期次数
}
