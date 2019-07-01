package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.BaseEntity;
import cn.fintecher.pangolin.entity.util.ExcelAnno;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * excel批量导入产品
 */
@Data
public class ProductImport extends BaseEntity {
    @ApiModelProperty(notes = "product_id")
    @ExcelAnno(cellName = "product_id")
    private String id;

    @ApiModelProperty(notes = "product_code")
    @ExcelAnno(cellName = "product_no")
    private String productCode;

    @ApiModelProperty(notes = "产品名称")
    @ExcelAnno(cellName = "产品名称")
    private String productName;

    @ApiModelProperty(notes = "产品系列ID")
    @ExcelAnno(cellName = "显示名称")
    private String productSeriesName;

}
