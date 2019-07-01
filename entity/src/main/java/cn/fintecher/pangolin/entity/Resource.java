package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ChenChang on 2017/7/10.
 */
@Entity
@Table(name = "resource")
@Data
@ApiModel(value = "resource", description = "资源管理")
public class Resource implements Serializable {

    @Id
    @ApiModelProperty(notes = "主键ID")
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Resource parent;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否显示,0-显示 1-隐藏")
    private Integer show;


    public enum Type {
        ONE_MENU(17, "一级菜单"),
        TWO_MENU(18, "二级菜单"),
        BUTTON(19, "控件");

        private Integer value;
        private String chinese;

        Type(Integer value, String chinese) {
            this.value = value;
            this.chinese = chinese;
        }

        public Integer getValue() {
            return value;
        }

        public String getChinese() {
            return chinese;
        }
    }

}
