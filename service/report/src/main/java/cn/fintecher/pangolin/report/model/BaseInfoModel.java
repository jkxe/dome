package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description: 基本信息返回model
 * @Package cn.fintecher.pangolin.report.model
 * @ClassName: cn.fintecher.pangolin.report.model.BaseInfoModel
 * @date 2018年09月30日 14:54
 */
@Data
public class BaseInfoModel {

    @ApiModelProperty(notes = "客户信息")
    private PersonalVModel personalVModel;

    @ApiModelProperty(notes = "地址信息")
    private List<PersonalAddressVModel> personalAddress;

    @ApiModelProperty(notes = "联系人信息")
    private List<ContactVModel> contactInfo;

}
