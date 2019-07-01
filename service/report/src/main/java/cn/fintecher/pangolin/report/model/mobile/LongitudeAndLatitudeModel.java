package cn.fintecher.pangolin.report.model.mobile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:  存放修改经纬度
 * @Package cn.fintecher.pangolin.report.model.mobile
 * @ClassName: cn.fintecher.pangolin.report.model.mobile.LongitudeAndLatitudeModel
 * @date 2018年10月27日 20:52
 */
@Data
public class LongitudeAndLatitudeModel {

    @ApiModelProperty(notes = "经度")
    private double longitude;

    @ApiModelProperty(notes = "纬度")
    private double latitude;

    @ApiModelProperty(notes = "personal_address 表主键,用来更新经纬度")
    private String pId;

    @ApiModelProperty(notes = "操作人")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime=new Date();

}
