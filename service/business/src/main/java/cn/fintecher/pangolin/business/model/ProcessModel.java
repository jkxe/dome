package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProcessModel {

    @ApiModelProperty(notes = "节点信息")
    private List<NodeModel> list;

    @ApiModelProperty(notes = "任务名称")
    private String taskName;
}
