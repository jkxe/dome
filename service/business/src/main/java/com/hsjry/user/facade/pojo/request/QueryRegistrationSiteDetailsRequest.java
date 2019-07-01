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
 * @version $Id: QueryRegistrationSiteDetailsRequest.java, v 1.0 2017年4月28日 上午10:44:49 jiangjd12837 Exp $
 */
public class QueryRegistrationSiteDetailsRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 5512036666420966831L;
    /**登记点ID*/
    @NotNull(errorCode = "000001", message = "登记点ID")
    @NotBlank(errorCode = "000001", message = "登记点ID")
    private String            registrationSiteId;

    /**
     * Getter method for property <tt>registrationSiteId</tt>.
     * 
     * @return property value of registrationSiteId
     */
    public String getRegistrationSiteId() {
        return registrationSiteId;
    }

    /**
     * Setter method for property <tt>registrationSiteId</tt>.
     * 
     * @param registrationSiteId value to be assigned to property registrationSiteId
     */
    public void setRegistrationSiteId(String registrationSiteId) {
        this.registrationSiteId = registrationSiteId;
    }

}
