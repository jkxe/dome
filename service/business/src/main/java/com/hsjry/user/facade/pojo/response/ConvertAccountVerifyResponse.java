/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.enums.EnumAuthStatus;

import java.io.Serializable;


public class ConvertAccountVerifyResponse implements Serializable {

    private static final long serialVersionUID = 5189776913221706641L;

    /**
     * 状态
     */
    private EnumAuthStatus    authStatus;

    public EnumAuthStatus getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(EnumAuthStatus authStatus) {
        this.authStatus = authStatus;
    }
}
