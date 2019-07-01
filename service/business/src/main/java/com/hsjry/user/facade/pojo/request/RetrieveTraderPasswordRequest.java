/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

/**
 * 找回交易密码
 * @author huangbb
 * @version $Id: RetrieveTraderPasswordRequest.java, v 1.0 2017年3月30日 下午3:55:50 huangbb Exp $
 */
public class RetrieveTraderPasswordRequest implements Serializable {
    
    
    private static final long serialVersionUID = 2644244913145337685L;

    @NotNull(errorCode = "000001", message = "识别用途")
    private EnumPurposeCode purposeCode;
    
    /**校验码 RETRIEVE_TRADER_PASSWORD*/
    @NotNull(errorCode = "000001", message = "校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    @Length(min=6, max = 6,errorCode="000002",message="校验码")
    @MatchPattern(matchAll=false,pattern={"^\\d{6}$"},errorCode="000003",message="校验码")
    private String authCheckCode;
    
    /**手机号*/
    @NotNull(errorCode = "000001", message = "手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="手机号")
    private String telephone;
    
    /**客户ID*/
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String userId;
    
    /**新密码*/
    @NotNull(errorCode = "000001", message = "新密码")
    @NotBlank(errorCode = "000001", message = "新密码")
    private String newPassword;

    
    /**
     * Getter method for property <tt>purposeCode</tt>.
     * 
     * @return property value of purposeCode
     */
    public EnumPurposeCode getPurposeCode() {
        return purposeCode;
    }

    /**
     * Setter method for property <tt>purposeCode</tt>.
     * 
     * @param purposeCode value to be assigned to property purposeCode
     */
    public void setPurposeCode(EnumPurposeCode purposeCode) {
        this.purposeCode = purposeCode;
    }

    /**
     * Getter method for property <tt>authCheckCode</tt>.
     * 
     * @return property value of authCheckCode
     */
    public String getAuthCheckCode() {
        return authCheckCode;
    }

    /**
     * Setter method for property <tt>authCheckCode</tt>.
     * 
     * @param authCheckCode value to be assigned to property authCheckCode
     */
    public void setAuthCheckCode(String authCheckCode) {
        this.authCheckCode = authCheckCode;
    }

    /**
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>newPassword</tt>.
     * 
     * @return property value of newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Setter method for property <tt>newPassword</tt>.
     * 
     * @param newPassword value to be assigned to property newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
