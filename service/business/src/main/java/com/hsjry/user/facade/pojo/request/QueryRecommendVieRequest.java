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
 * @version $Id: QueryRecommendVieRequest.java, v 1.0 2017年4月19日 下午4:43:03 jiangjd12837 Exp $
 */
public class QueryRecommendVieRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 4952337024258405075L;
    /**客户Id列表*/
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
