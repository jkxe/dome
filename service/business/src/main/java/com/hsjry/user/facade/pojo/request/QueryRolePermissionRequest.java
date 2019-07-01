/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 根据角色获取权限请求类
 * @author hongsj
 * @version $Id: QueryRolePermissionRequest.java, v 1.0 2017年3月28日 下午2:48:38 hongsj Exp $
 */
public class QueryRolePermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 7540922944741248833L;
    /**角色id*/
    @NotNull(errorCode = "000001", message = "角色id")
    @NotBlank(errorCode = "000001", message = "角色id")
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
