/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 注册返回结果
 * @author hongsj
 * @version $Id: RegisterResponse.java, v 1.0 2017年4月18日 下午5:40:24 hongsj Exp $
 */
public class RegisterResponse implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    /** 用户id */
    private String            userId;

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

}
