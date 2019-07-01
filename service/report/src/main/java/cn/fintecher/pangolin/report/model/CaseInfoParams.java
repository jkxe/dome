package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/8/22.
 */
@Data
public class CaseInfoParams {
    private String deptCode;
    private String companyCode;
    private String collector;
    private String name;
    private String address;

    @ApiModelProperty("案件编号")
    private String caseNumber;
    @ApiModelProperty("客户姓名")
    private String personName;
    @ApiModelProperty("客户手机号")
    private String mobileNo;
    @ApiModelProperty("委托方id")
    private String principalId;
    @ApiModelProperty("所在城市id")
    private String areaId;
    @ApiModelProperty("机构父id(省份id)")
    private String parentAreaId;
    @ApiModelProperty("经销商")
    private String merchantName;
    @ApiModelProperty("逾期开始时间")
    private String startOverDueDate;
    @ApiModelProperty("逾期开始时间2")
    private String endOverDueDate;
    @ApiModelProperty(notes = "逾期最小天数")
    private int startOverDueDays;
    @ApiModelProperty(notes = "逾期最大天数")
    private int endOverDueDays;
    @ApiModelProperty("逾期金额(大")
    private BigDecimal endOverDueAmount;
    @ApiModelProperty("逾期金额(小")
    private BigDecimal startOverDueAmount;
    @ApiModelProperty("催收员")
    private String currentCollector;
    @ApiModelProperty("逾期期数")
    private String payStatus;
    @ApiModelProperty("来源渠道")
    private String sourceChannel;
    @ApiModelProperty("模式")
    private String collectionMethod;
    @ApiModelProperty("出催开始日期")
    private String settleStartDate;
    @ApiModelProperty("出催结束日期")
    private String settleEndDate;



}
