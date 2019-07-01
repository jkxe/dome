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
 * @version $Id: QueryRegistrationSiteRequest.java, v 1.0 2017年4月28日 上午10:31:51 jiangjd12837 Exp $
 */
public class QueryRegistrationSiteRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 3232822173247086564L;
    /**客户Id*/
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
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
