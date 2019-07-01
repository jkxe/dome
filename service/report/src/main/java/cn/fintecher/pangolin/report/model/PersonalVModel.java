package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:  客户信息视图
 * @Package cn.fintecher.pangolin.report.model
 * @ClassName: cn.fintecher.pangolin.report.model.PersonalVModel
 * @date 2018年09月30日 14:59
 */
@Data
public class PersonalVModel {

    @ApiModelProperty(notes = "主键")
    private String id;

    @ApiModelProperty(notes = "客户姓名")
    private String personalName;

    @ApiModelProperty(notes = "性别")
    private int sex;

    @ApiModelProperty(notes = "性别名称")
    private String sexName;

    @ApiModelProperty(notes = "婚姻状态")
    private int marital;

    @ApiModelProperty(notes = "婚姻状态名称")
    private String maritalName;

    @ApiModelProperty(notes = "年龄")
    private int age;

    @ApiModelProperty(notes = "手机号码")
    private String mobileNo;

    @ApiModelProperty(notes = "手机号码状态")
    private int  mobileStatus;

    @ApiModelProperty(notes = "手机号码状态名称")
    private String mobileStatusName;

    @ApiModelProperty(notes = "身份证号码")
    private String idCard;


}
