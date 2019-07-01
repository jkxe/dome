package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryChargeOffResponse {
    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;
    @ApiModelProperty(notes = "借据号")
    private String loanInvoiceNumber;
    @ApiModelProperty(notes = "产品名称")
    private String productName;
    @ApiModelProperty(notes = "还款状态")
    private String payStatus;
    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;
    @ApiModelProperty(notes = "账户余额")
    private BigDecimal accountBalance;
    @ApiModelProperty(notes = "逾期总金额")
    private BigDecimal overdueAmount;
    @ApiModelProperty(value = "客户姓名")
    private String personalName;
    @ApiModelProperty(notes = "手机号码")
    private String mobileNo;
    @ApiModelProperty(notes = "证件号码")
    private String certificatesNumber;
    @ApiModelProperty(notes = "产品系列名称")
    private String seriesName;



}
