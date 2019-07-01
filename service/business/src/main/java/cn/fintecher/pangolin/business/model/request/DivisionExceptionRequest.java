package cn.fintecher.pangolin.business.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DivisionExceptionRequest {
    @ApiModelProperty("第几页")
    private Integer page;

    @ApiModelProperty("每页多少条")
    private Integer size;

    @ApiModelProperty("案件编号")
    private String caseNumber;

    @ApiModelProperty("异常类型 数据字典type_code:0403")
    private Integer exceptionType;

    @ApiModelProperty("异常检查时间")
    private String exceptionCheckTimeStart;

    @ApiModelProperty("异常检查时间")
    private String exceptionCheckTimeEnd;

}
