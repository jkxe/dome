package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by sunyanping on 2017/11/6.
 */
@Data
@ApiModel("待分配案件多条件查询参数")
public class CaseInfoDistributeQueryParams {
    @ApiModelProperty("客户姓名")
    private String personalName;
    @ApiModelProperty("手机号码")
    private String mobileNo;
    @ApiModelProperty("申请省份ID")
    private String provinceId;
    @ApiModelProperty("申请城市ID")
    private String cityId;
    @ApiModelProperty("批次号")
    private String caseNumber;
    @ApiModelProperty("逾期天数(最小)")
    private Integer overdueDaysStart;
    @ApiModelProperty("逾期天数(最大)")
    private Integer overdueDaysEnd;
    @ApiModelProperty(notes = "案件金额(最小)")
    private BigDecimal overDueAmountStart;
    @ApiModelProperty(notes = "案件金额(最大)")
    private BigDecimal overDueAmountEnd;
    @ApiModelProperty("案件手数(最小)")
    private Integer handNumberStart;
    @ApiModelProperty("案件手数(最大)")
    private Integer handNumberEnd;
    @ApiModelProperty("委托方ID")
    private String principalId;
    @ApiModelProperty("佣金比例(最小)")
    private BigDecimal commissionRateStart;
    @ApiModelProperty("佣金比例(最大)")
    private BigDecimal commissionRateEnd;
    @ApiModelProperty(value = "公司Code", required = true)
    private String companyCode;
    @ApiModelProperty(notes = "页码", required = true)
    private Integer page = 0;
    @ApiModelProperty(notes = "每页大小", required = true)
    private Integer size = 10;
    @ApiModelProperty("排序")
    private String sort;
    @ApiModelProperty("操作人")
    private String operator;
    @ApiModelProperty("逾期期数")
    private String payStatus;
    @ApiModelProperty("逾期开始时间")
    private String startOverDueDate;
    @ApiModelProperty("逾期结束时间")
    private String endOverDueDate;
    @ApiModelProperty("经销商")
    private String merchantName;
    @ApiModelProperty("来源渠道")
    private String sourceChannel;
    @ApiModelProperty("催收模式")
    private String collectionMethod;
}
