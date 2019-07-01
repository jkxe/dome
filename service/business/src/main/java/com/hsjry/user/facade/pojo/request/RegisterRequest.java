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

import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

/**
 * 正常注册请求
 * @author huangbb
 * @version $Id: RegisterRequest.java, v 1.0 2017年3月21日 下午3:51:17 huangbb Exp $
 */
public class RegisterRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -802430355946638812L;
    
    /**校验码*/
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
    
    /**营销线索*/
    @NotNull(errorCode = "000001", message = "营销线索")
    private EnumMarketingCues marketingCues;
    
    /**密码*/
    @NotNull(errorCode = "000001", message = "密码")
    @NotBlank(errorCode = "000001", message = "密码")
    private String authPassword;
    
    /**邀请码*/
    @Length(min=8,max=8,errorCode = "000002", message = "邀请码")
    private String inviteCode;
    
    /**邀请人手机号*/
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="邀请人手机号")
    private String inviterTelephone;

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
     * Getter method for property <tt>marketingCues</tt>.
     * 
     * @return property value of marketingCues
     */
    public EnumMarketingCues getMarketingCues() {
        return marketingCues;
    }

    /**
     * Setter method for property <tt>marketingCues</tt>.
     * 
     * @param marketingCues value to be assigned to property marketingCues
     */
    public void setMarketingCues(EnumMarketingCues marketingCues) {
        this.marketingCues = marketingCues;
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

    /**
     * Getter method for property <tt>inviteCode</tt>.
     * 
     * @return property value of inviteCode
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * Setter method for property <tt>inviteCode</tt>.
     * 
     * @param inviteCode value to be assigned to property inviteCode
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * Getter method for property <tt>inviterTelephone</tt>.
     * 
     * @return property value of inviterTelephone
     */
    public String getInviterTelephone() {
        return inviterTelephone;
    }

    /**
     * Setter method for property <tt>inviterTelephone</tt>.
     * 
     * @param inviterTelephone value to be assigned to property inviterTelephone
     */
    public void setInviterTelephone(String inviterTelephone) {
        this.inviterTelephone = inviterTelephone;
    }



}
