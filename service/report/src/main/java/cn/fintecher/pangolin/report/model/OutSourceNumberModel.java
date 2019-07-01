package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OutSourceNumberModel {

    @ApiModelProperty(notes = "委外方id")
    private String outId;

    @ApiModelProperty(notes = "委外方名称")
    private String outName;

    @ApiModelProperty(notes = "委外方数量")
    private Integer outNumber;

    @ApiModelProperty(notes = "地区案件数量信息")
    private List<AreaCaseNumberModel> areaList;
}
