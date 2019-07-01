/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 登入绑定返回
 * @author huangbb
 * @version $Id: LoginBindResponse.java, v 1.0 2017年3月21日 下午3:09:43 huangbb Exp $
 */
public class LoginBindResponse implements Serializable {

    private static final long serialVersionUID = 4896971554372343927L;
    
    /**授权token*/
    private String userToken;

    /**
     * Getter method for property <tt>userToken</tt>.
     * 
     * @return property value of userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Setter method for property <tt>userToken</tt>.
     * 
     * @param userToken value to be assigned to property userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
