/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ModifyUserRolesRequest.java, v 1.0 2017年8月21日 上午11:02:40 jiangjd12837 Exp $
 */
public class ModifyUserRolesRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1753821647506235606L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String            userId;
    /**
     * 角色类型列表
     */
    private List<String>      roleIdList;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
