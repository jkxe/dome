package cn.fintecher.pangolin.dataimp.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 实体。
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy
 * @date 2018年06月23日 13:10
 */
@Data
@Document
@ApiModel(value = "ObtainTaticsStrategyRequest", description = "添加或修改分案策略request")
public class ObtainTaticsStrategyRequest implements Serializable {

    @Id
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("策略名称")
    @NotBlank(message = "策略名称不能为空")
    private String name;

    @ApiModelProperty("hy-策略状态[状态 504:开启,505:关闭]")
    @NotNull(message = "策略状态不能为空")
    private Integer status;

    @ApiModelProperty("hy-案件类型")
    @NotNull(message = "策略状态不能为空")
    private Integer caseType;

    @ApiModelProperty("hy-执行频率[分案频率 (每天,月初)]")
    @NotNull(message = "执行频率不能为空")
    private Integer divisionFrequency;

    @ApiModelProperty("hy-分案策略[催收员案件分配模式 (案件数量均分，案件金额均分，综合均分，人员配比分配....)]")
    private Integer userPattern;

    @ApiModelProperty("hy-催收队列")
    private List<String> collectionQueues;

    @ApiModelProperty(notes = "hy-分配方式[是否分配到催员]")
    @NotNull(message = "分配方式不能为空")
    private Integer allotType;

    /**
     * 案件分配到组的id,保存的是这个机构的上级节点ID与自己的ID.需要的ID为最后一个。
     */
    @ApiModelProperty("hy-分配的机构id[组别]") // department 表的ID 后台需要。
    private String departId;

    @ApiModelProperty("hy-分配的机构code(存储分配的机构路径)[组别]") // department 表的code,前端需要
    private List<String> departCode;

    /**
     * 以下属性是针对分案需要的参数，如果直接分配到组，该值为空；
     * 分到人，是人员的集合，根据 userPattern 再将人员与案件进行关联。
     */
    @ApiModelProperty("hy-分配到的催收员催收员[策略指定的催收员]")
    private List<String> users;

    @ApiModelProperty("公司Code")
    private String companyCode;

    @ApiModelProperty("策略JSON对象")  // 对应的对象为 ObtainStrategyJsonModel
    private String strategyJson;



}
