/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;

/**
 * 
 * @author huangbb
 * @version $Id: QueryHasPermissionUserRequest.java, v 1.0 2017年5月30日 下午4:06:11 huangbb Exp $
 */
public class QueryHasPermissionUserRequest implements Serializable{

    private static final long serialVersionUID = -6314273473038681437L;
    
    @NotNull(errorCode = "000001", message = "权限ID列表")
    @Size(min=1 ,errorCode = "000003", message = "权限ID列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "权限ID列表错误")
    private List<String> permissionIds;

    /**
     * Getter method for property <tt>permissionIds</tt>.
     * 
     * @return property value of permissionIds
     */
    public List<String> getPermissionIds() {
        return permissionIds;
    }

    /**
     * Setter method for property <tt>permissionIds</tt>.
     * 
     * @param permissionIds value to be assigned to property permissionIds
     */
    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }

    
    
    
}
