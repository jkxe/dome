package cn.fintecher.pangolin.report.model.distribution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OutSourceDistributionModel {

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "客户id")
    private String personalId;

    @ApiModelProperty(notes = "客户姓名")
    private String personalName;

    @ApiModelProperty(notes = "客户身份证号码")
    private String idCard;

    @ApiModelProperty(notes = "城市id")
    private Integer areaId;

    @ApiModelProperty(notes = "城市名称")
    private String areaName;

    @ApiModelProperty(notes = "委外方id")
    private String outId;

    @ApiModelProperty(notes = "委外方名称")
    private String outName;
}
