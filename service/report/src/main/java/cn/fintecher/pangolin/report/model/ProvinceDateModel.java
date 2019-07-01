package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : huyanmin
 * @Description :
 * @Date : 2017/11/11.
 */
@Data
public class ProvinceDateModel {

    @ApiModelProperty(notes = "催收中总金额")
    private BigDecimal collectingAmt = BigDecimal.ZERO;
    @ApiModelProperty(notes = "催收中总数量")
    private Integer collectingCount;
}
