/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 添加权限请求类
 * @author hongsj
 * @version $Id: AddPermissionRequest.java, v 1.0 2017年3月17日 上午10:08:52 hongsj Exp $
 */
public class AddPermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -6488523906275891317L;

    /**
     * 菜单Id
     */
    private String            menuId;

    /**
     * 权限名称
     */
    @NotNull(errorCode = "000001", message = "权限名称")
    @Length(errorCode = "000002", max = 50, message = "权限名称")
    @NotBlank(errorCode = "000001", message = "权限名称")
    private String            permissionName;

    /**
     * 权限描述
     */
    @NotNull(errorCode = "000001", message = "权限描述")
    @Length(errorCode = "000002", max = 250, message = "权限名称")
    @NotBlank(errorCode = "000001", message = "权限描述")
    private String            permissionDesc;

    /**
     * Getter method for property <tt>menuId</tt>.
     * 
     * @return property value of menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * Setter method for property <tt>menuId</tt>.
     * 
     * @param menuId value to be assigned to property menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

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

    /**
     * Getter method for property <tt>permissionDesc</tt>.
     * 
     * @return property value of permissionDesc
     */
    public String getPermissionDesc() {
        return permissionDesc;
    }

    /**
     * Setter method for property <tt>permissionDesc</tt>.
     * 
     * @param permissionDesc value to be assigned to property permissionDesc
     */
    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

}
