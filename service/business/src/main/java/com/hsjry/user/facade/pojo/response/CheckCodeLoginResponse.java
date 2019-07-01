/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author huangbb
 * @version $Id: CheckCodeLoginResponse.java, v 1.0 2017年4月3日 下午3:46:49 huangbb Exp $
 */
public class CheckCodeLoginResponse implements Serializable {

    
    private static final long serialVersionUID = 8060119632087543188L;

    /**通行证ID*/
    private String authId;
    
    /**登录token*/
    private String token;

    /**
     * Getter method for property <tt>authId</tt>.
     * 
     * @return property value of authId
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Setter method for property <tt>authId</tt>.
     * 
     * @param authId value to be assigned to property authId
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }

    /**
     * Getter method for property <tt>token</tt>.
     * 
     * @return property value of token
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter method for property <tt>token</tt>.
     * 
     * @param token value to be assigned to property token
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    
}
