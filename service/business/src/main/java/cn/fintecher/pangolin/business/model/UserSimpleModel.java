package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data

public class UserSimpleModel {

    @ApiModelProperty(notes = "主键ID")
    private String id;

    @ApiModelProperty(notes = "特定公司的标识")
    private String companyCode;

    @ApiModelProperty(notes = "用户的催收类型（1.电话 2.外访3.修复...)")
    private Integer type;

    @ApiModelProperty(notes = "用户采用的登录方式（1密码登录 2.二维码登录..)")
    private Integer loginType;

    @ApiModelProperty(notes = "用户的登录设备限制（1.pc登录 2.手机登录）")
    private Integer loginDevice;

    @ApiModelProperty(notes = "用户的最后一次登录地址（登录地址改变给出提醒）")
    private String loginAddress;

    @ApiModelProperty(notes = "密码的定时修改（比如3个月后提醒修改密码）")
    private Date passwordInvalidTime;

    @ApiModelProperty(notes = "电话呼叫绑定的电话号码")
    private String callPhone;

    @ApiModelProperty(notes = "电话呼叫绑定的通道号码")
    private String channelNo;

    @ApiModelProperty(notes = "主叫电话的区号")
    private String zoneno;

    @ApiModelProperty(notes = "用户绑定的消息推送的注册标识")
    private String messagePushId;

    @ApiModelProperty(notes = "是否具有查看下级用户的权限")
    private Integer manager;

    @ApiModelProperty(notes = "用户名")
    private String userName;

    @ApiModelProperty(notes = "姓名")
    private String realName;

    @ApiModelProperty(notes = "密码")
    private String password;

    @ApiModelProperty(notes = "性别")
    private Integer sex;

    @ApiModelProperty(notes = "电话")
    private String phone;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "状态")
    private Integer status;

    @ApiModelProperty(notes = "签名")
    private String signature;

    @ApiModelProperty(notes = "特定公司的标识")
    private String remark;

    @ApiModelProperty(notes = "备注")
    private String photo;

    @ApiModelProperty(notes = "创建人用户名")
    private String operator;

    @ApiModelProperty(notes = "创建时间")
    private Date operateTime;

    @ApiModelProperty(notes = "备用字段")
    private String field;

    @ApiModelProperty(notes = "部门ID")
    private String deptId;

    private Set<ResourceModel> resources;

    private Set<ResourceModel> menu = new HashSet<>();

    private Set<Long> resource = new HashSet<>();

    private Set<Role> role = new HashSet<>();
}
