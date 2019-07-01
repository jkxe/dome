package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoleModelAll implements Serializable{

    private String id;

    @ApiModelProperty(notes = "特定公司的标识")
    private String companyCode;

    @ApiModelProperty(notes = "角色名称")
    private String name;

    @ApiModelProperty(notes = "角色状态 0：启用 1：停用")
    private Integer status;

    @ApiModelProperty(notes = "描述")
    private String remark;

    @ApiModelProperty(notes = "创建人用户名")
    private String operator;

    @ApiModelProperty(notes = "创建时间")
    private Date operateTime;
}
