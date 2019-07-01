/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserRolePojo;

/**
 * 获取游离角色返回
 * @author hongsj
 * @version $Id: QueryDissRoleResponse.java, v 1.0 2017年3月28日 上午9:53:08 hongsj Exp $
 */
public class QueryDissRoleResponse implements Serializable {
    /**  */
    private static final long  serialVersionUID = 1717685417270923385L;
    /** 角色列表 */
    private List<UserRolePojo> roleList;

    /**
     * Getter method for property <tt>roleList</tt>.
     * 
     * @return property value of roleList
     */
    public List<UserRolePojo> getRoleList() {
        return roleList;
    }

    /**
     * Setter method for property <tt>roleList</tt>.
     * 
     * @param roleList value to be assigned to property roleList
     */
    public void setRoleList(List<UserRolePojo> roleList) {
        this.roleList = roleList;
    }

}