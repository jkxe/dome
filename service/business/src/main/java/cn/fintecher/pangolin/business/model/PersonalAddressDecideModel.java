package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用来做去重的模型类")
public class PersonalAddressDecideModel {
    @ApiModelProperty(notes = "id")
    private String id;
    @ApiModelProperty(notes = "关系")
    private Integer relation;
    @ApiModelProperty(notes = "地址类型")
    private Integer type;
    @ApiModelProperty(notes = "客户ID")
    private String personalId;
    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;
}
