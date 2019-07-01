package cn.fintecher.pangolin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseInfoSignException {

    private List<String> idList;
    @ApiModelProperty(notes = "hy-异常类型 数据字典type_code:0403")
    private Integer exceptionType;
}
