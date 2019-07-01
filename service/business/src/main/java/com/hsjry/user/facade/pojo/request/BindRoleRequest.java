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
 * 绑定角色请求类
 * @author hongsj
 * @version $Id: BindRoleRequest.java, v 1.0 2017年3月20日 上午10:16:49 hongsj Exp $
 */
public class BindRoleRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 733921427769516098L;
    /**
     * 父角色Id
     */
    @NotNull(errorCode = "000001", message = "父角色Id")
    @NotBlank(errorCode = "000001", message = "父角色Id")
    private String            parentRoleId;
    /**
     * 子角色Id列表
     */
    @NotNull(errorCode = "000001", message = "子角色Id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "子角色Id列表")
    private List<String>      roleIdList;

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
     * Getter method for property <tt>roleIdList</tt>.
     * 
     * @return property value of roleIdList
     */
    public List<String> getRoleIdList() {
        return roleIdList;
    }

    /**
     * Setter method for property <tt>roleIdList</tt>.
     * 
     * @param roleIdList value to be assigned to property roleIdList
     */
    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

}
