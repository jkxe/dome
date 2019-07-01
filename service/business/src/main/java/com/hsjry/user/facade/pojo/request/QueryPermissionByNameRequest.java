/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import java.io.Serializable;

/**
 * 
 * @author hongsj
 * @version $Id: QueryPermissionByNameRequest.java, v 1.0 2017年4月28日 下午3:24:26 hongsj Exp $
 */
public class QueryPermissionByNameRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    /**权限名称*/
    private String            permissionName;
    /**权限状态*/
    private EnumObjectStatus status;

    /**
     * Getter method for property <tt>permissionName</tt>.
     * 
     * @return property value of permissionName
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Setter method for property <tt>permissionName</tt>.
     * 
     * @param permissionName value to be assigned to property permissionName
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public EnumObjectStatus getStatus() {
        return status;
    }

    public void setStatus(EnumObjectStatus status) {
        this.status = status;
    }
}
