/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumMenuType;

/**
 * 根据菜单查询用户列表请求类
 * @author hongsj
 * @version $Id: QueryUserByMenuRequest.java, v 1.0 2017年3月30日 上午10:20:01 hongsj Exp $
 */
public class QueryUserByMenuRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -6419875945109504405L;
    /**菜单类型*/
    @NotNull(errorCode = "000001", message = "菜单类型")
    private EnumMenuType      menuType;
    /**菜单识别*/
    @NotNull(errorCode = "000001", message = "菜单识别")
    @NotBlank(errorCode = "000001", message = "菜单识别")
    private String            menuDisting;

    /**
     * Getter method for property <tt>menuType</tt>.
     * 
     * @return property value of menuType
     */
    public EnumMenuType getMenuType() {
        return menuType;
    }

    /**
     * Setter method for property <tt>menuType</tt>.
     * 
     * @param menuType value to be assigned to property menuType
     */
    public void setMenuType(EnumMenuType menuType) {
        this.menuType = menuType;
    }

    /**
     * Getter method for property <tt>menuDisting</tt>.
     * 
     * @return property value of menuDisting
     */
    public String getMenuDisting() {
        return menuDisting;
    }

    /**
     * Setter method for property <tt>menuDisting</tt>.
     * 
     * @param menuDisting value to be assigned to property menuDisting
     */
    public void setMenuDisting(String menuDisting) {
        this.menuDisting = menuDisting;
    }

}
