package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by meteor on 2019/2/27.
 */
@Data
public class DistributedExportParams {

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty("客户姓名")
    private String personalName;

    @ApiModelProperty("手机号码")
    private String mobileNo;

    @ApiModelProperty("委托方ID")
    private String principalId;

    @ApiModelProperty("逾期天数(最小)")
    private Integer overdueDaysStart;

    @ApiModelProperty("逾期天数(最大)")
    private Integer overdueDaysEnd;

    @ApiModelProperty(notes = "案件金额(最小)")
    private BigDecimal overDueAmountStart;

    @ApiModelProperty(notes = "案件金额(最大)")
    private BigDecimal overDueAmountEnd;

    @ApiModelProperty(notes = "逾期期数")
    private String payStatus;

    @ApiModelProperty(notes = "逾期开始时间")
    private String startOverDueDate;

    @ApiModelProperty(notes = "逾期结束时间")
    private String endOverDueDate;

    @ApiModelProperty(notes = "来源渠道")
    private String sourceChannel;//来源渠道

    @ApiModelProperty(notes = "hy-催收方式（自催或者第三方）")
    private String collectionMethod;

    @ApiModelProperty("公司code码")
    private String companyCode;

}
