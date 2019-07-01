package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by ChenChang on 2017/12/20.
 */
@Data
public class RoleModel implements Serializable {

    private static final long serialVersionUID = 3093102410995231266L;

    private String id;

    @ApiModelProperty(notes = "角色名称")
    private String name;

    @ApiModelProperty(notes = "角色状态")
    private Integer status;

    private Set<ResourceModel> resources;
}
