/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumActionType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 绑定或解绑权限菜单请求
 * @author hongsj
 * @version $Id: BindOrUnBindPermissionMenuRequest.java, v 1.0 2017年4月6日 下午3:40:26 hongsj Exp $
 */
public class BindOrUnBindPermissionMenuRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -3995104008305459230L;
    /**权限Id*/
    @NotNull(errorCode = "000001", message = "权限Id")
    @NotBlank(errorCode = "000001", message = "权限Id")
    private String            permissionId;
    /**动作类型*/
    @NotNull(errorCode = "000001", message = "动作类型")
    private EnumActionType    actionType;
    /**菜单Id*/
    private String            menuId;

    /**
     * Getter method for property <tt>permissionId</tt>.
     * 
     * @return property value of permissionId
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * Setter method for property <tt>permissionId</tt>.
     * 
     * @param permissionId value to be assigned to property permissionId
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * Getter method for property <tt>menuId</tt>.
     * 
     * @return property value of menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * Setter method for property <tt>menuId</tt>.
     * 
     * @param menuId value to be assigned to property menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * Getter method for property <tt>actionType</tt>.
     * 
     * @return property value of actionType
     */
    public EnumActionType getActionType() {
        return actionType;
    }

    /**
     * Setter method for property <tt>actionType</tt>.
     * 
     * @param actionType value to be assigned to property actionType
     */
    public void setActionType(EnumActionType actionType) {
        this.actionType = actionType;
    }

}
