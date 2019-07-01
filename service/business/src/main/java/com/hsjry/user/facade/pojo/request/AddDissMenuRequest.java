/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

/**
 * 添加游离菜单请求类
 * @author hongsj
 * @version $Id: AddDissMenuRequest.java, v 1.0 2017年3月16日 下午2:21:50 hongsj Exp $
 */
public class AddDissMenuRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1802479279849940092L;
    /**
     * 父菜单Id
     */
    @NotNull(errorCode = "000001", message = "父菜单Id")
    @NotBlank(errorCode = "000001", message = "父菜单Id")
    private String            parentMenu;
    /**
     * 子菜单Id列表
     */
    @MinSize(value = 1)
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "子菜单Id列表")
    private List<String>      menuIdList;

    /**
     * Getter method for property <tt>parentMenu</tt>.
     * 
     * @return property value of parentMenu
     */
    public String getParentMenu() {
        return parentMenu;
    }

    /**
     * Setter method for property <tt>parentMenu</tt>.
     * 
     * @param parentMenu value to be assigned to property parentMenu
     */
    public void setParentMenu(String parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * Getter method for property <tt>menuIdList</tt>.
     * 
     * @return property value of menuIdList
     */
    public List<String> getMenuIdList() {
        return menuIdList;
    }

    /**
     * Setter method for property <tt>menuIdList</tt>.
     * 
     * @param menuIdList value to be assigned to property menuIdList
     */
    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

}
