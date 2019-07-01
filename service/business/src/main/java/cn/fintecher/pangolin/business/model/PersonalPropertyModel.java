package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PersonalPropertyModel {

    @ApiModelProperty(notes = "房屋购置价")
    private BigDecimal housePurchasePrice = new BigDecimal(0);

    @ApiModelProperty(notes = "房屋评估价")
    private BigDecimal houseAssAmt  = new BigDecimal(0);

    @ApiModelProperty(notes = "首付金额")
    private BigDecimal firstPayment  = new BigDecimal(0);

    @ApiModelProperty(notes = "房贷总期数")
    private Integer totalPeriods;

    @ApiModelProperty(notes = "房贷月均还款额")
    private BigDecimal monthPaymentAmount  = new BigDecimal(0);

    @ApiModelProperty(notes = "房贷已还期数")
    private Integer repaymentPeriods;

    @ApiModelProperty(notes = "房贷余额")
    private BigDecimal over  = new BigDecimal(0);

    @ApiModelProperty(notes = "房产地址")
    private String address;

    @ApiModelProperty("房产其他4个地址")
    private List<String> addressList;
}
