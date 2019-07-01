/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManageImportantInfoRequest.java, v 1.0 2017年9月6日 上午11:16:49 jiangjd12837 Exp $
 */
public class QueryManageImportantInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 7217814699436310824L;
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
