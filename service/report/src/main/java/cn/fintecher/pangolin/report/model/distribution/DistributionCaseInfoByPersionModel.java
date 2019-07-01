package cn.fintecher.pangolin.report.model.distribution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DistributionCaseInfoByPersionModel {

    @ApiModelProperty(notes = "客户id")
    private String personalId;

    @ApiModelProperty(notes = "客户身份证")
    private String idCard;

    @ApiModelProperty(notes = "客户姓名")
    private String personalName;

    @ApiModelProperty(notes = "城市id")
    private Integer areaId;

    @ApiModelProperty(notes = "城市名称")
    private String areaName;

    @ApiModelProperty(notes = "案件id")
    private List<String> caseId;
}
