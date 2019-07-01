/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserTokenAndAuthIdResponse.java, v 1.0 2017年8月24日 下午2:35:43 jiangjd12837 Exp $
 */
public class QueryUserTokenAndAuthIdResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -6498902160762203102L;
    /** 通行证ID*/
    private String            authId;
    /**签名-渠道端的用户认证ID*/
    private String            token;

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
