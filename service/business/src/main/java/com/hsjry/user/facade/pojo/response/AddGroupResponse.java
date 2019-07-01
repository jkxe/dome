/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: AddGroupResponse.java, v 1.0 2017年6月26日 下午4:12:29 jiangjd12837 Exp $
 */
public class AddGroupResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 1335445971412214925L;
    //组织ID
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

}
