package cn.fintecher.pangolin.dataimp.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  逾期案件传递到案件分配策略的对象。根据这些对象中的条件将案件分配到对应的案件池，即设置caseType的属性。
 * @Package cn.fintecher.pangolin.dataimp.model
 * @ClassName: cn.fintecher.pangolin.dataimp.model.CaseDistributedModel
 * @date 2018年06月19日 17:07
 */
@Data
@Document
@ApiModel(value = "CaseDistributedModel", description = "案件数据获取策略")
public class CaseDistributedModel {

    @Id
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty(notes = "产品类型")
    private Integer productType;

    @ApiModelProperty(notes = "产品名称")
    private String productName;

    @ApiModelProperty(notes = "逾期本金")
    private BigDecimal overdueCapital = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "剩余本金")
    private BigDecimal leftCapital = new BigDecimal(0);

    @ApiModelProperty(notes = "省份编号id")
    private Integer provinceId;

    @ApiModelProperty(notes = "城市编号id")
    private Integer cityId;

    @ApiModelProperty(notes = "是否失联")
    private Integer isLost;

}
