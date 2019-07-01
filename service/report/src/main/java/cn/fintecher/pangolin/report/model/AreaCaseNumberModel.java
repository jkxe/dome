package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class AreaCaseNumberModel {

    @ApiModelProperty(notes = "地区id")
    private Integer areaId;

    @ApiModelProperty(notes = "地区名称")
    private String areaName;

    @ApiModelProperty(notes = "案件数量")
    private Integer number;
}
