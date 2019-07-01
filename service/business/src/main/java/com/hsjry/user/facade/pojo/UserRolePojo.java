/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;
import com.hsjry.user.facade.pojo.enums.EnumRoleType;

/**
 * 角色pojo
 * @author hongsj
 * @version $Id: UserRolePojo.java, v 1.0 2017年3月23日 下午1:27:05 hongsj Exp $
 */
public class UserRolePojo implements Serializable {
    /**  */
    private static final long serialVersionUID = 3162298920231416257L;
    /**
     * 角色Id
     */
    private String            roleId;
    /**
     * 租户Id
     */
    private String            tenantId;
    /**
     * 父角色Id
     */
    private String            parentRoleId;
    /**
     * 角色名称
     */
    private String            roleName;
    /**
     * 角色描述
     */
    private String            roleDesc;
    /**
     * 角色状态
     */
    private EnumObjectStatus  roleStatus;
    /**
     * 角色类型
     */
    private EnumRoleType      roleKind;

    /**
     * Getter method for property <tt>roleId</tt>.
     * 
     * @return property value of roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * Setter method for property <tt>roleId</tt>.
     * 
     * @param roleId value to be assigned to property roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
     * Getter method for property <tt>parentRoleId</tt>.
     * 
     * @return property value of parentRoleId
     */
    public String getParentRoleId() {
        return parentRoleId;
    }

    /**
     * Setter method for property <tt>parentRoleId</tt>.
     * 
     * @param parentRoleId value to be assigned to property parentRoleId
     */
    public void setParentRoleId(String parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    /**
     * Getter method for property <tt>roleName</tt>.
     * 
     * @return property value of roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Setter method for property <tt>roleName</tt>.
     * 
     * @param roleName value to be assigned to property roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Getter method for property <tt>roleDesc</tt>.
     * 
     * @return property value of roleDesc
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * Setter method for property <tt>roleDesc</tt>.
     * 
     * @param roleDesc value to be assigned to property roleDesc
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * Getter method for property <tt>roleStatus</tt>.
     * 
     * @return property value of roleStatus
     */
    public EnumObjectStatus getRoleStatus() {
        return roleStatus;
    }

    /**
     * Setter method for property <tt>roleStatus</tt>.
     * 
     * @param roleStatus value to be assigned to property roleStatus
     */
    public void setRoleStatus(EnumObjectStatus roleStatus) {
        this.roleStatus = roleStatus;
    }

    /**
     * Getter method for property <tt>roleKind</tt>.
     * 
     * @return property value of roleKind
     */
    public EnumRoleType getRoleKind() {
        return roleKind;
    }

    /**
     * Setter method for property <tt>roleKind</tt>.
     * 
     * @param roleKind value to be assigned to property roleKind
     */
    public void setRoleKind(EnumRoleType roleKind) {
        this.roleKind = roleKind;
    }

}
