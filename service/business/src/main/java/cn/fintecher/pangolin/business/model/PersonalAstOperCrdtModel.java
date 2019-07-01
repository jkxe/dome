package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author gonghebin
 * @date 2019/1/16 0016下午 5:27
 */
@Data
public class PersonalAstOperCrdtModel {

    @ApiModelProperty(notes = "客户id")
    private String customerId;

    @ApiModelProperty(notes = "资源项id")
    private String resourceId;

    @ApiModelProperty(notes = "资源项类型")
    private String resourceType;

    @ApiModelProperty(notes = "数据体")
    private String originalData;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
