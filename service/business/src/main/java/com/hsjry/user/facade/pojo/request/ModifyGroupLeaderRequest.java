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
 * @version $Id: ModifyGroupLeaderRequest.java, v 1.0 2017年6月16日 下午3:55:37 jiangjd12837 Exp $
 */
public class ModifyGroupLeaderRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -795301722609428491L;
    //新组长客户Id
    @NotNull(errorCode = "000001", message = "组长客户Id")
    @NotBlank(errorCode = "000001", message = "组长客户Id")
    private String            userId;

    //组织ID
    @NotNull(errorCode = "000001", message = "组织ID")
    @NotBlank(errorCode = "000001", message = "组织ID")
    private String            organId;

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
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
