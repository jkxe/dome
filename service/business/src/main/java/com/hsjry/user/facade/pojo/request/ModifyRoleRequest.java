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
 * 修改角色请求
 * @author hongsj
 * @version $Id: ModifyRoleRequest.java, v 1.0 2017年3月20日 上午10:07:02 hongsj Exp $
 */
public class ModifyRoleRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -8381688518404114372L;
    /**
     * 角色Id
     */
    @NotNull(errorCode = "000001", message = "角色Id")
    @NotBlank(errorCode = "000001", message = "角色Id")
    private String            roleId;
    /**
     * 角色名称
     */
    @NotNull(errorCode = "000001", message = "角色名称")
    @NotBlank(errorCode = "000001", message = "角色名称")
    @Length(errorCode = "000002", max = 32, message = "角色名称")
    private String            roleName;
    /**
     * 角色描述
     */
    private String            roleDesc;
    /**
     * 上级角色
     */
    private String            parentRoleId;

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

}
