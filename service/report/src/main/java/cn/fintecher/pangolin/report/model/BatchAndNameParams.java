package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gonghebin
 * @date 2019/3/3 0003下午 9:25
 */
@Data
public class BatchAndNameParams {

    @ApiModelProperty(value = "批次号")
    private String batchNumber;

    @ApiModelProperty(value = "受托方名称")
    private String outsId;
}
