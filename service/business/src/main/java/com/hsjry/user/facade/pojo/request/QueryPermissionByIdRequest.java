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
 * @author hongsj
 * @version $Id: QueryPermissionByNameRequest.java, v 1.0 2017年4月28日 下午3:24:26 hongsj Exp $
 */
public class QueryPermissionByIdRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    /**权限id*/
    @NotNull(errorCode = "000001", message = "权限Id")
    @NotBlank(errorCode = "000001", message = "权限Id")
    private String            permissionId;

    /**
     * Getter method for property <tt>permissionId</tt>.
     * 
     * @return property value of permissionId
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * Setter method for property <tt>permissionId</tt>.
     * 
     * @param permissionId value to be assigned to property permissionId
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

}
