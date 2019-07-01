package cn.fintecher.pangolin.dataimp.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 实体。
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy
 * @date 2018年06月23日 13:10
 */
@Data
@Document
@ApiModel(value = "CollectionQueueRequest", description = "hy-案件催收队列req")
public class CollectionQueueRequest implements Serializable {

    @Id
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("队列编号")
    @NotBlank(message = "队列编号不能为空")
    private String code;

    @ApiModelProperty("队列名称")
    @NotBlank(message = "队列名称不能为空")
    private String name;

    @ApiModelProperty("队列状态(504:有效,505:无效)")
    @NotNull(message = "队列状态不能为空")
    private Integer status;

    @ApiModelProperty("策略JSON对象")  // 对应的对象为 ObtainStrategyJsonModel
    @NotBlank(message = "策略JSON对象不能为空")
    private String strategyJson;

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
