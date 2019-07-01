/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryContactStationByAuthIdRequest.java, v 1.0 2017年4月18日 下午9:17:49 jiangjd12837 Exp $
 */
public class QueryContactStationByAuthIdRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -5380853231990173885L;
    @NotNull(errorCode = "000001", message = "通行证ID")
    @NotBlank(errorCode = "000001", message = "通行证ID")
    private String            authId;

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

}
