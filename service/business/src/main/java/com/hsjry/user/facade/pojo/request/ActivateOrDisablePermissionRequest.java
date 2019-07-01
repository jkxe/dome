/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 激活停用权限
 * @author hongsj
 * @version $Id: ActivateOrDisablePermissionRequest.java, v 1.0 2017年3月17日 上午11:09:21 hongsj Exp $
 */
public class ActivateOrDisablePermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 5545221918888393270L;
    /**
     * 权限Id
     */
    @NotNull(errorCode = "000001", message = "权限Id")
    @NotBlank(errorCode = "000001", message = "权限Id")
    private String            permissionId;

    /**
     * 权限状态
     */
    @NotNull(errorCode = "000001", message = "权限状态")
    private EnumObjectStatus  permissionStatus;

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

    /**
     * Getter method for property <tt>permissionStatus</tt>.
     * 
     * @return property value of permissionStatus
     */
    public EnumObjectStatus getPermissionStatus() {
        return permissionStatus;
    }

    /**
     * Setter method for property <tt>permissionStatus</tt>.
     * 
     * @param permissionStatus value to be assigned to property permissionStatus
     */
    public void setPermissionStatus(EnumObjectStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

}
