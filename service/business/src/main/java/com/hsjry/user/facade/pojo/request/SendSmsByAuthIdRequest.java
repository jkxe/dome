/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumSmsType;

import net.sf.oval.constraint.NotNull;

/**
 * 通行证获取短信验证码
 * @author huangbb
 * @version $Id: SendSmsByAuthIdRequest.java, v 1.0 2017年3月20日 上午11:09:13 huangbb Exp $
 */
public class SendSmsByAuthIdRequest implements Serializable {

    private static final long serialVersionUID = -5792998152962880782L;

    /**短信类型*/
    @NotNull(errorCode = "000001", message = "短信类型")
    private EnumSmsType       smsType;

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

}
