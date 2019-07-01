package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-08-16-11:37
 */
@Data
public class UserModel {
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("所属部门")
    private String department;
    @ApiModelProperty("用户的催收类型（1.电话 2.外访3.修复...)")
    private String type;
    @ApiModelProperty("是否具有查看下级用户的权限")
    private String manager;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建人")
    private String operator;

    /**
     * 2018-07-06 添加下面字段，这些是导出Excel中原来没有的。
     */

    @ApiModelProperty(notes = "状态")
    private String status;

    @ApiModelProperty(notes = "电话呼叫绑定的电话号码/主叫号码")
    private String callPhone;

    @ApiModelProperty(notes = "创建时间")
    private Date operateTime;

    @ApiModelProperty("催收员等级")
    private String collectionGrade;

    @ApiModelProperty(notes = "用户的登录设备限制（1.pc登录 2.手机登录）")
    private String loginDevice;

    @ApiModelProperty("催收员分案开关(开启:504,关闭:505)")
    private String divisionSwitch;

    @ApiModelProperty(notes = "用户绑定的消息推送的注册标识")
    private String messagePushId;

}
