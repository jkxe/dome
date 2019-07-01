/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumSmsType;

/**
 * 手机号获取短信验证码
 * @author huangbb
 * @version $Id: SendSmsByTelephoneRequest.java, v 1.0 2017年3月20日 上午11:12:05 huangbb Exp $
 */
public class SendSmsByTelephoneRequest implements Serializable {

    private static final long serialVersionUID = 4701761067691765613L;

    /**短信类型*/
    @NotNull(errorCode = "000001", message = "短信类型")
    private EnumSmsType smsType;
    
    /**手机号*/
    @NotNull(errorCode = "000001", message = "手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="手机号")
    private String telephone;
    
    /**是否需要手机未使用*/
    @NotNull(errorCode = "000001", message = "是否需要手机未使用")
    private EnumBool useFlag;

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
     * Getter method for property <tt>useFlag</tt>.
     * 
     * @return property value of useFlag
     */
    public EnumBool getUseFlag() {
        return useFlag;
    }

    /**
     * Setter method for property <tt>useFlag</tt>.
     * 
     * @param useFlag value to be assigned to property useFlag
     */
    public void setUseFlag(EnumBool useFlag) {
        this.useFlag = useFlag;
    }
    
    
}
