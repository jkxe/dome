package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * Created by ChenChang on 2017/12/21.
 */
@Data
public class RoleRequest {
    private String id;

    @ApiModelProperty(notes = "角色名称")
    private String name;

    @ApiModelProperty(notes = "角色状态")
    private Integer status;

    @ApiModelProperty(notes = "描述")
    private String remark;

    private Set<Long> resources;

}
