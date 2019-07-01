/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryUserInfosByRoleIdRequest.java, v 1.0 2018年7月4日 下午3:36:53 zhengqy15963 Exp $
 */
public class QueryUserInfosByRoleIdRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -4397674074865302221L;
    /**角色id  */
    private String            roleId;

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
