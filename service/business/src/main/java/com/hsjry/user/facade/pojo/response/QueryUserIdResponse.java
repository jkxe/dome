/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserIdResponse.java, v 1.0 2017年6月13日 下午12:32:29 jiangjd12837 Exp $
 */
public class QueryUserIdResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -4598704118392892661L;
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
