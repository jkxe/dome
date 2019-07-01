/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 用户名获取短信验证码返回
 * @author huangbb
 * @version $Id: SendSmsByLoginNameResponse.java, v 1.0 2017年3月31日 下午4:17:41 huangbb Exp $
 */
public class SendSmsByLoginNameResponse implements Serializable {

    private static final long serialVersionUID = -6228864606982498956L;
    
    /**手机号*/
    private String telephone;

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
