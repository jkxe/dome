/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author hongsj
 * @version $Id: QueryRoleByNameRequest.java, v 1.0 2017年4月28日 下午4:38:45 hongsj Exp $
 */
public class QueryRoleByNameRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -8438900272298601905L;
    /**角色名称*/
    private String            roleName;

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

}
