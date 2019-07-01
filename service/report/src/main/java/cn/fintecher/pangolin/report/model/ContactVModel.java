package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:  APP-基本信息-联系人信息 model
 * @Package cn.fintecher.pangolin.report.model
 * @ClassName: cn.fintecher.pangolin.report.model.ContactVModel
 * @date 2018年10月08日 11:50
 */
@Data
public class ContactVModel {

    @ApiModelProperty(notes = "关系：145-本人，146-配偶，147-父母，148-子女，149-亲属，150-同事，151-朋友，152-其他，153-亲兄弟姐妹，154-单位")
    private Integer relation;

    @ApiModelProperty(notes = "关系名称")
    private String relationName;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "电话")
    private String mobileNo;

    @ApiModelProperty(notes = "电话状态")
    private String mobileStatus;

    @ApiModelProperty(notes = "电话状态名称")
    private String mobileStatusName;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

}
