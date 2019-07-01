package com.hsjry.user.facade.pojo.modify;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumEmailClassCode;

/**
 * 邮箱联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserEmailContactStationPojo.java, v 1.0 2017年3月13日 下午4:52:49 jiangjd12837 Exp $
 */
public class UserEmailContactStationInfoModifyPojo extends UserContactStationInfoModifyPojo {

    /**  */
    private static final long  serialVersionUID = 4267483595034379049L;
    //邮箱分类代码
    @NotNull(errorCode = "000001", message = "邮箱分类代码")
    private EnumEmailClassCode emailClassCode;
    //邮箱地址
    @NotNull(errorCode = "000001", message = "邮箱地址")
    @NotBlank(errorCode = "000001", message = "邮箱地址")
    private String             email;

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public EnumEmailClassCode getEmailClassCode() {
        return emailClassCode;
    }

    public void setEmailClassCode(EnumEmailClassCode emailClassCode) {
        this.emailClassCode = emailClassCode;
    }
}
