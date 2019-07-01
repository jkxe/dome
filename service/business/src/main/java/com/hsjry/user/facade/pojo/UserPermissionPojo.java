/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 权限返回
 * @author hongsj
 * @version $Id: UserPermissionPojo.java, v 1.0 2017年3月21日 下午3:15:15 hongsj Exp $
 */
public class UserPermissionPojo implements Serializable {
    /**  */
    private static final long serialVersionUID = -2441863672047633465L;
    /**
     * 权限Id
     */
    private String            permissionId;
    /**
     * 租户Id
     */
    private String            tenantId;
    /**
     * 菜单Id
     */
    private String            menuId;
    /**
     * 权限名称
     */
    private String            permissionName;
    /**
     * 权限描述
     */
    private String            permissionDesc;
    /**
     * 权限状态
     */
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
     * Getter method for property <tt>tenantId</tt>.
     * 
     * @return property value of tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Setter method for property <tt>tenantId</tt>.
     * 
     * @param tenantId value to be assigned to property tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
