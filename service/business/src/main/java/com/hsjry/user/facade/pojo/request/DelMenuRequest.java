/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 删除菜单请求类
 * @author hongsj
 * @version $Id: DelMenuRequest.java, v 1.0 2017年3月16日 下午2:17:52 hongsj Exp $
 */
public class DelMenuRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 8419485771301268804L;
    /**
     * 菜单Id
     */
    @NotNull(errorCode = "000001", message = "菜单Id")
    @NotBlank(errorCode = "000001", message = "菜单Id")
    private String            menuId;

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

}
