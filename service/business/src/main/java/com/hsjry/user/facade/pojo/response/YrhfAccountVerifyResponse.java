/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumAuthStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfCertVerifyResponse.java, v 1.0 2017年5月15日 下午3:40:12 jiangjd12837 Exp $
 */
public class YrhfAccountVerifyResponse implements Serializable {
    /**  */
    private static final long serialVersionUID = -5365381063441069567L;
    /**
     * C 状态
     */
    private EnumAuthStatus    authStatus;

    /**
     * Getter method for property <tt>authStatus</tt>.
     * 
     * @return property value of authStatus
     */
    public EnumAuthStatus getAuthStatus() {
        return authStatus;
    }

    /**
     * Setter method for property <tt>authStatus</tt>.
     * 
     * @param authStatus value to be assigned to property authStatus
     */
    public void setAuthStatus(EnumAuthStatus authStatus) {
        this.authStatus = authStatus;
    }

}
