package cn.fintecher.pangolin.dataimp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 实体。
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy
 * @date 2018年06月23日 13:10
 */
@Data
@Document
@ApiModel(value = "CollectionQueue", description = "hy-案件催收队列")
public class CollectionQueue implements Serializable {

    @Id
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("队列编号")
    private String code;

    @ApiModelProperty("队列名称")
    private String name;

    @ApiModelProperty("队列状态(504:有效,505:无效)")
    private Integer status;

    @ApiModelProperty("策略JSON对象")  // 对应的对象为 ObtainStrategyJsonModel
    private String strategyJson;

    @ApiModelProperty("策略公式")
    private String strategyText;

    @ApiModelProperty("hy-创建人")
    private String creator;

    @ApiModelProperty("hy-创建人ID")
    private String creatorId;

    @ApiModelProperty("hy-创建日期")
    private Date createTime;

    @ApiModelProperty("hy-更新人")
    private String updateBy;

    @ApiModelProperty("hy-更新人ID")
    private String updateId;

    @ApiModelProperty("hy-更新时间")
    private Date updateTime;

    public enum Status {
        OPEN(504, "开启"),
        CLOSED(505, "关闭");

        private Integer value;
        private String remark;

        Status(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }

}
