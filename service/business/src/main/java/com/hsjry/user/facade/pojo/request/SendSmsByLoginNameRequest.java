/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumSmsType;

/**
 * 用户名获取短信验证码
 * @author huangbb
 * @version $Id: SendSmsByLoginNameRequest.java, v 1.0 2017年3月20日 上午11:12:19 huangbb Exp $
 */
public class SendSmsByLoginNameRequest implements Serializable {

    private static final long serialVersionUID = 7359486944413357418L;

    /**短信类型*/
    @NotNull(errorCode = "000001", message = "短信类型")
    private EnumSmsType smsType;
    
    /**用户名*/
    @NotNull(errorCode = "000001", message = "用户名")
    @NotBlank(errorCode = "000001", message = "用户名")
    private String loginName;

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
     * Getter method for property <tt>loginName</tt>.
     * 
     * @return property value of loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Setter method for property <tt>loginName</tt>.
     * 
     * @param loginName value to be assigned to property loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    
}
