/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryPartnerInfoRequest.java, v 1.0 2017年7月14日 上午9:23:10 jiangjd12837 Exp $
 */
public class QueryPartnerInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 4611757787530627104L;
    //客户ID
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
