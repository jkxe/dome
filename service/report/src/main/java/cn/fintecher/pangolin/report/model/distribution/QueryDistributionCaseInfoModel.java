package cn.fintecher.pangolin.report.model.distribution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryDistributionCaseInfoModel {

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "客户姓名")
    private String personalName;

    @ApiModelProperty(notes = "客户手机号")
    private String mobileNo;

    @ApiModelProperty(notes = "客户身份证号")
    private String idCard;

    @ApiModelProperty(notes = "省份名称")
    private String areaName;

    @ApiModelProperty(notes = "城市名称")
    private String cityName;

    @ApiModelProperty(notes = "案件金额")
    private BigDecimal overdueAmount;

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "逾期状态")
    private String payStatus;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "产品类型")
    private String seriesName;

    @ApiModelProperty(notes = "委外方名称")
    private String outName;

    @ApiModelProperty(notes = "账户余额")
    private BigDecimal accountBalance;
}
