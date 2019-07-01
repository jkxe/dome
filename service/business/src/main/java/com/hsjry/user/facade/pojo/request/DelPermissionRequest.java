/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 删除权限请求类
 * @author hongsj
 * @version $Id: DelPermissionRequest.java, v 1.0 2017年3月17日 上午11:05:29 hongsj Exp $
 */
public class DelPermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -5680808530657510565L;
    /**
     * 权限Id
     */
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
