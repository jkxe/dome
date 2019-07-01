package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ChenChang on 2017/12/20.
 */
@Data
public class ResourceModel implements Serializable {
    public ResourceModel() {
    }
    private static final long serialVersionUID = 3093102410995231267L;
    private Long id;
    @ApiModelProperty(notes = "资源名称")
    private String name;
    @ApiModelProperty("类型")
    private Integer type;
    @ApiModelProperty("地址")
    private String url;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("父功能")
    private Long parentId;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("是否显示,0-显示 1-隐藏")
    private Integer show;
    @ApiModelProperty("备注")
    private String remark;


    public ResourceModel(Long  id, String name, Integer type, String url, String icon, Long parentId, Integer sort, String remark, Integer show) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.icon = icon;
        this.parentId = parentId;
        this.sort = sort;
        this.remark = remark;
        this.show = show;
    }

}
