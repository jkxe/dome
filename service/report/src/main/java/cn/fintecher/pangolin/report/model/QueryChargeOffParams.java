package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryChargeOffParams {
    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;
    @ApiModelProperty(notes = "借据号")
    private String loanInvoiceNumber;
    @ApiModelProperty(value = "客户姓名")
    private String personalName;
    @ApiModelProperty(value = "手机号")
    private String mobileNo;
    @ApiModelProperty(value = "逾期期数")
    private String payStatus;
    @ApiModelProperty(notes = "证件号码")
    private String certificatesNumber;
    @ApiModelProperty(value = "逾期天数左区间")
    private Integer overdueDaysStart;
    @ApiModelProperty(value = "逾期天数右区间")
    private Integer overdueDaysEnd;

}
