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
 * 
 * @author hongsj
 * @version $Id: ModifyPermissionRequest.java, v 1.0 2017年3月17日 上午11:02:11 hongsj Exp $
 */
public class ModifyPermissionRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -1076188282789473991L;

    /**
     * 权限Id
     */
    @NotNull(errorCode = "000001", message = "权限Id")
    @NotBlank(errorCode = "000001", message = "权限Id")
    private String            permissionId;
    /**
     * 权限名称
     */
    @NotNull(errorCode = "000001", message = "权限名称")
    @NotBlank(errorCode = "000001", message = "权限名称")
    @Length(errorCode = "000002", max = 50, message = "权限名称")
    private String            permissionName;

    /**
     * 权限描述
     */
    @Length(errorCode = "000002", max = 250, message = "权限描述")
    private String            permissionDesc;

    /**
     * 菜单id
     */
    private String            menuId;

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

}
