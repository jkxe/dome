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
 * 分配自定义角色请求类
 * @author hongsj
 * @version $Id: AssignCustomRoleRequest.java, v 1.0 2017年3月20日 上午9:55:03 hongsj Exp $
 */
public class AssignCustomRoleRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 871749897294828929L;
    /**
     * 分配人角色Id
     */
    @NotNull(errorCode = "000001", message = "分配人角色Id")
    @NotBlank(errorCode = "000001", message = "分配人角色Id")
    private String            assignRoleId;
    /**
     * 用户Id列表
     */
    @NotNull(errorCode = "000001", message = "用户Id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "用户Id列表")
    private List<String>      userIdList;
    /**
     * 角色Id
     */
    @NotNull(errorCode = "000001", message = "角色Id")
    @NotBlank(errorCode = "000001", message = "角色Id")
    private String            roleId;

    /**
     * Getter method for property <tt>assignRoleId</tt>.
     * 
     * @return property value of assignRoleId
     */
    public String getAssignRoleId() {
        return assignRoleId;
    }

    /**
     * Setter method for property <tt>assignRoleId</tt>.
     * 
     * @param assignRoleId value to be assigned to property assignRoleId
     */
    public void setAssignRoleId(String assignRoleId) {
        this.assignRoleId = assignRoleId;
    }

    /**
     * Getter method for property <tt>userIdList</tt>.
     * 
     * @return property value of userIdList
     */
    public List<String> getUserIdList() {
        return userIdList;
    }

    /**
     * Setter method for property <tt>userIdList</tt>.
     * 
     * @param userIdList value to be assigned to property userIdList
     */
    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

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

}
