package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by james on 2017-12-19.
 */
@Data
public class DepartmentResponse {
    private String id;
    @ApiModelProperty(notes = "机构的名称")
    private String name;

    @ApiModelProperty(notes = "机构类型")
    private Integer type;

    @ApiModelProperty(notes = "机构编号")
    private String code;

    @ApiModelProperty(notes = "公司名称")
    private String companyCode;

    private String parent;
}
