package cn.fintecher.pangolin.dataimp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  存放产品系列ID与名称。将这个值与id作为下拉框的值。
 * @Package cn.fintecher.pangolin.dataimp.model
 * @ClassName: cn.fintecher.pangolin.dataimp.model.ProductSeriesImpModel
 * @date 2018年06月29日 12:38
 */

@Data
public class ProductSeriesImpModel {

    @ApiModelProperty(notes = "id")
    private String id;

    @ApiModelProperty(notes = "产品类型名称")
    private String typeName;

}
