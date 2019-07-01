/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author huangbb
 * @version $Id: QueryHasPermissionUserResponse.java, v 1.0 2017年5月30日 下午4:05:12 huangbb Exp $
 */
public class QueryHasPermissionUserResponse implements Serializable {

    
    private static final long serialVersionUID = 8219595856715616737L;
    
    private List<String> userIds;

    /**
     * Getter method for property <tt>userIds</tt>.
     * 
     * @return property value of userIds
     */
    public List<String> getUserIds() {
        return userIds;
    }

    /**
     * Setter method for property <tt>userIds</tt>.
     * 
     * @param userIds value to be assigned to property userIds
     */
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
    
    
}
