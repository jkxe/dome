package com.hsjry.user.facade.pojo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumSocialType;

/**
 * 互联网社交联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserSocialContactStationPojo.java, v 1.0 2017年3月13日 下午4:52:56 jiangjd12837 Exp $
 */
public class UserSocialContactStationInfoPojo extends UserContactStationInfoPojo {

    /**  */
    private static final long serialVersionUID = -6577240799739437854L;

    //社交类型
    @NotNull(errorCode = "000001", message = "社交类型")
    private EnumSocialType    socialType;
    //账号
    @NotNull(errorCode = "000001", message = "账号")
    @NotBlank(errorCode = "000001", message = "账号")
    private String            account;
    //昵称
    private String            nickName;

    /**
     * Getter method for property <tt>account</tt>.
     * 
     * @return property value of account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter method for property <tt>account</tt>.
     * 
     * @param account value to be assigned to property account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Getter method for property <tt>nickName</tt>.
     * 
     * @return property value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter method for property <tt>nickName</tt>.
     * 
     * @param nickName value to be assigned to property nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public EnumSocialType getSocialType() {
        return socialType;
    }

    public void setSocialType(EnumSocialType socialType) {
        this.socialType = socialType;
    }
}
