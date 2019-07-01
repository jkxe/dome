/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: CheckUserNameResponse.java, v 1.0 2017年4月13日 上午11:04:32 jiangjd12837 Exp $
 */
public class PersonalToMerchantResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -4377428540114332781L;
    private String            userId;
    
    private String            custRoleId;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of UserId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param UserId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoleId</tt>.
     * 
     * @param custRoleId value to be assigned to property custRoleId
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
    }

    

}
