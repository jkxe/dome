package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeModel {

    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @ApiModelProperty(notes = "当前步数")
    private Integer step;

}
