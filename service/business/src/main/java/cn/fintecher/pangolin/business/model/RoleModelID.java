package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RoleModelID implements Serializable {
    private String id;

    @ApiModelProperty(notes = "角色名称")
    private String name;

    @ApiModelProperty(notes = "角色状态")
    private Integer status;
    @ApiModelProperty(notes = "菜单资源")
    private Set<Long> resourcesSet;
    @ApiModelProperty(notes = "控件资源")
    private Set<Long> resourceModelButton;
}
