/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumMenuType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 获取用户已有的权限菜单请求类
 * @author hongsj
 * @version $Id: QueryUserHavedPerMenuRequest.java, v 1.0 2017年3月28日 下午2:29:55 hongsj Exp $
 */
public class QueryUserHavedPerMenuRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 5322755959355644124L;
    /**客户id*/
    @NotNull(errorCode = "000001", message = "客户id")
    @NotBlank(errorCode = "000001", message = "客户id")
    private String            userId;
    /**菜单类型*/
    @NotNull(errorCode = "000001", message = "菜单类型")
    private EnumMenuType      menuType;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

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

}
