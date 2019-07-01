package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flow_node")
@Data
public class FlowNode extends BaseEntity {

    @ApiModelProperty(notes = "任务id")
    private String taskId;

    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @ApiModelProperty(notes = "当前步数")
    private Integer step;

}
