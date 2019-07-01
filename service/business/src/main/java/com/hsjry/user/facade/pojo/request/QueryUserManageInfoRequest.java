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
 * @version $Id: QueryUserManageInfoRequest.java, v 1.0 2017年4月20日 下午7:07:55 jiangjd12837 Exp $
 */
public class QueryUserManageInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 3321392648230396024L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
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
