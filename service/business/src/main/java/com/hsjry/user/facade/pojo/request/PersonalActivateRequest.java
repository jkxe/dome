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

/**
 * 个人激活请求
 * @author huangbb
 * @version $Id: PersonalActivateRequest.java, v 1.0 2017年3月21日 下午3:31:02 huangbb Exp $
 */
public class PersonalActivateRequest implements Serializable {

    
    private static final long serialVersionUID = 7554500879432319863L;
    
    /**用户ID*/
    @NotNull(errorCode = "000001", message = "用户ID")
    @NotBlank(errorCode = "000001", message = "用户ID")
    private String userId;
    
    /**手机号*/
    @NotNull(errorCode = "000001", message = "手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="手机号")
    private String telephone;
    
    /**校验码*/
    @NotNull(errorCode = "000001", message = "校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    @Length(min=6, max = 6,errorCode="000002",message="校验码")
    @MatchPattern(matchAll=false,pattern={"^\\d{6}$"},errorCode="000003",message="校验码")
    private String authCheckCode;
    
    /**新密码*/
    @NotNull(errorCode = "000001", message = "新密码")
    @NotBlank(errorCode = "000001", message = "新密码")
    private String authPassword;

    
    
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
     * Getter method for property <tt>authPassword</tt>.
     * 
     * @return property value of authPassword
     */
    public String getAuthPassword() {
        return authPassword;
    }

    /**
     * Setter method for property <tt>authPassword</tt>.
     * 
     * @param authPassword value to be assigned to property authPassword
     */
    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }
    
    

}
