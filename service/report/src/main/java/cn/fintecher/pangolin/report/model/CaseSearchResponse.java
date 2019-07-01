package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sunyanping on 2019/5/15
 */
@Data
public class CaseSearchResponse {
    private String id;
    @ApiModelProperty(notes = "批次号")
    private String batchNumber;
    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;
    @ApiModelProperty("客户姓名")
    private String personalName;
    @ApiModelProperty("手机号码")
    private String mobileNo;
    @ApiModelProperty(notes = "逾期总金额")
    private BigDecimal overdueAmount;
    @ApiModelProperty(notes = "还款期数")
    private Integer overduePeriods;
    @ApiModelProperty(notes = "催收反馈")
    private Integer followupBack;
    @ApiModelProperty(notes = "承诺还款金额")
    private BigDecimal promiseAmt;
    @ApiModelProperty(notes = "承诺还款日期")
    private Date promiseTime;
    @ApiModelProperty("机构名称")
    private String deptName;
    @ApiModelProperty(notes = "产品系列")
    private String seriesName;
    @ApiModelProperty(notes = "产品名称")
    private String productName;
    @ApiModelProperty(notes = "账户余额")
    private BigDecimal accountBalance;
    @ApiModelProperty(notes = "结清时间")
    private Date settleDate;
    @ApiModelProperty(notes = "hy-客户id")
    private String customerId;
    @ApiModelProperty(notes = "hy-来源渠道(线上线下)")
    private String sourceChannel;
    @ApiModelProperty(notes = "hy-催收方式（自催或者第三方）")
    private String collectionMethod;
    @ApiModelProperty(notes = "hy-放款期数")
    private Integer loanPeriod;
    @ApiModelProperty(notes = "hy-放款金额")
    private BigDecimal loanAmount;
    @ApiModelProperty(notes = "hy-当前欠款总额")
    private BigDecimal currentDebtAmount;
    @ApiModelProperty(notes = "hy-借据号")
    private String loanInvoiceNumber;
    @ApiModelProperty(notes = "hy-经销商名称")
    private String merchantName;
    @ApiModelProperty("逾期阶段")
    private String payStatus;
    @ApiModelProperty("身份证号码")
    private String certificatesNumber;
    @ApiModelProperty("逾期开始时间")
    private Date overDueDate;
    private Date repayDate;
    @ApiModelProperty("催收员")
    private String collectorName;
    @ApiModelProperty("逾期天数")
    private Integer overdueDays;

}
