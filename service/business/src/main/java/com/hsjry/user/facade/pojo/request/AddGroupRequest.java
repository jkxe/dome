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
 * @version $Id: AddGroupRequest.java, v 1.0 2017年6月16日 下午3:51:49 jiangjd12837 Exp $
 */
public class AddGroupRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -3660514073267205993L;
    //组长客户Id
    @NotNull(errorCode = "000001", message = "组长客户Id")
    @NotBlank(errorCode = "000001", message = "组长客户Id")
    private String            userId;
    //组长名称
    private String            organName;

    /**
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

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
