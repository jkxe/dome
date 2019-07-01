/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 激活或禁用角色请求类
 * @author hongsj
 * @version $Id: ActivateOrDisableRoleRequest.java, v 1.0 2017年3月20日 上午10:13:22 hongsj Exp $
 */
public class ActivateOrDisableRoleRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 4593047288388965353L;
    /**
     * 角色Id
     */
    @NotNull(errorCode = "000001", message = "角色Id")
    @NotBlank(errorCode = "000001", message = "角色Id")
    private String            roleId;
    /**
     * 角色状态
     */
    @NotNull(errorCode = "000001", message = "角色状态")
    private EnumObjectStatus  roleStatus;

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
     * Getter method for property <tt>roleStatus</tt>.
     * 
     * @return property value of roleStatus
     */
    public EnumObjectStatus getRoleStatus() {
        return roleStatus;
    }

    /**
     * Setter method for property <tt>roleStatus</tt>.
     * 
     * @param roleStatus value to be assigned to property roleStatus
     */
    public void setRoleStatus(EnumObjectStatus roleStatus) {
        this.roleStatus = roleStatus;
    }

}
