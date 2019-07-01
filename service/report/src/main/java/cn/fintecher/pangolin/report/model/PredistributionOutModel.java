package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PredistributionOutModel {

    @ApiModelProperty(notes = "城市id")
    private Integer areaId;

    @ApiModelProperty(notes = "城市名称")
    private String areaName;

    @ApiModelProperty(notes = "委外方名称")
    private String outName;

    @ApiModelProperty(notes = "委外方id")
    private String outId;

    @ApiModelProperty(notes = "回收率")
    private BigDecimal returnRare = new BigDecimal(0);

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;
}
