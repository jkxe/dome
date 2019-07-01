package cn.fintecher.pangolin.entity.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DivisionCaseParamModel {

    @ApiModelProperty("公司编号")
    private String companyCode;
}
