package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessNodeModel {

    @ApiModelProperty(notes = "任务id")
    private String taskId;

    @ApiModelProperty(notes = "任务名称")
    private String taskName;

    @ApiModelProperty(notes = "节点id")
    private String nodeId;

    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @ApiModelProperty(notes = "角色名称")
    private String roleName;

    @ApiModelProperty(notes = "当前步数")
    private Integer temp;
}
