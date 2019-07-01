package cn.fintecher.pangolin.dataimp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  保存案件关联查询出的字段，然后将这2个字段的值设置到case_info_distributed表。
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.CaseDistributedResult
 * @date 2018年06月29日 11:14
 */
@Data
public class CaseDistributedResult {

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "产品类型")
    private String productTypeId;

    @ApiModelProperty(notes = "产品名称")
    private String productName;

    @ApiModelProperty(notes = "催收类型")
    private Integer collectionType;


}
