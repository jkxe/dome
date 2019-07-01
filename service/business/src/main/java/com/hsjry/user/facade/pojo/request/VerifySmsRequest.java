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

import com.hsjry.lang.common.base.enums.user.EnumSmsType;

/**
 * 校验验证码
 * @author jiangjd12837
 * @version $Id: VerifySmsRequest.java, v 1.0 2017年6月8日 上午11:00:07 jiangjd12837 Exp $
 */
public class VerifySmsRequest implements Serializable {

    private static final long serialVersionUID = -7630089823288177981L;
    
    @NotNull(errorCode="000001",message="短信类型")
    private EnumSmsType       smsType;
    
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

    /**
     * Getter method for property <tt>smsType</tt>.
     * 
     * @return property value of smsType
     */
    public EnumSmsType getSmsType() {
        return smsType;
    }

    /**
     * Setter method for property <tt>smsType</tt>.
     * 
     * @param smsType value to be assigned to property smsType
     */
    public void setSmsType(EnumSmsType smsType) {
        this.smsType = smsType;
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


    
}
