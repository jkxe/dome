/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author hongsj
 * @version $Id: AddRoleResponse.java, v 1.0 2017年5月3日 上午11:21:02 hongsj Exp $
 */
public class AddRoleResponse implements Serializable {
    /**  */
    private static final long serialVersionUID = -8116251740525113066L;
    /**角色id*/
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
