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
 * @author zhengqy15963
 * @version $Id: QueryCustomerExtInfoRequest.java, v 1.0 2017年12月5日 下午7:21:20 zhengqy15963 Exp $
 */
public class QueryCustomerExtInfoRequest implements Serializable{

    /**  */
    private static final long serialVersionUID = -3918125705054081714L;
    
    /**用户编号*/
    @NotNull(errorCode = "000001", message = "用户编号")
    @NotBlank(errorCode = "000001", message = "用户编号")
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
    /**
     * 
     */
    public QueryCustomerExtInfoRequest() {
    }

}
