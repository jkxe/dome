/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

/**
 * 删除角色权限请求类
 * @author hongsj
 * @version $Id: AddUserPermissionRequest.java, v 1.0 2017年3月17日 上午11:13:41 hongsj Exp $
 */
public class DelRolePermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 7690834456100016163L;
    /**
     * 角色Id
     */
    @NotNull(errorCode = "000001", message = "角色Id")
    @NotBlank(errorCode = "000001", message = "角色Id")
    private String            roleId;
    /**
     * 权限Id列表
     */
    @NotNull(errorCode = "000001", message = "权限Id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "权限Id列表")
    private List<String>      permissionIdList;

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
     * Getter method for property <tt>permissionIdList</tt>.
     * 
     * @return property value of permissionIdList
     */
    public List<String> getPermissionIdList() {
        return permissionIdList;
    }

    /**
     * Setter method for property <tt>permissionIdList</tt>.
     * 
     * @param permissionIdList value to be assigned to property permissionIdList
     */
    public void setPermissionIdList(List<String> permissionIdList) {
        this.permissionIdList = permissionIdList;
    }

}
