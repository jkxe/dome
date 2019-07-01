package cn.fintecher.pangolin.report.model.mobile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description: 修复用户的联系信息 参数model
 * @Package cn.fintecher.pangolin.report.model.mobile
 * @ClassName: cn.fintecher.pangolin.report.model.mobile.RepairPersonInfoParams
 * @date 2018年10月10日 18:20
 */
@Data
public class RepairPersonInfoParams {

    // personal_contact 表属性

    @ApiModelProperty(notes = "id")
    private String id;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "联系电话")
    private String mobileNo;

    @ApiModelProperty(notes = "电话状态")
    private Integer mobileStatus;

    @ApiModelProperty(notes = "邮箱地址")
    private String mail;

    @ApiModelProperty(notes = "数据来源")
    private Integer source;
    // personal_address 表属性

    @ApiModelProperty(notes = "客户id")
    private String personalId;

    @ApiModelProperty(notes = "关系：145-本人，146-配偶，147-父母，148-子女，149-亲属，150-同事，151-朋友，152-其他，153-亲兄弟姐妹，154-单位")
    private Integer relation;

    @ApiModelProperty(notes = "地址类型")
    private Integer type;

    @ApiModelProperty(notes = "地址状态")
    private Integer status;

    @ApiModelProperty(notes = "详细地址")
    private String detail;

    @ApiModelProperty(notes = "经度")
    private double longitude;

    @ApiModelProperty(notes = "纬度")
    private double latitude;

    @ApiModelProperty("操作员")
    private String operator;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("编辑人")
    private String editor;

    @ApiModelProperty("编辑时间")
    private Date editorTime;


}
