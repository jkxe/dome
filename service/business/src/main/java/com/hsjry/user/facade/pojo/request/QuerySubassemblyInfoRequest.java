/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 查询组件请求类
 * @author hongsj
 * @version $Id: QuerySubassemblyInfoRequest.java, v 1.0 2017年3月28日 下午2:19:58 hongsj Exp $
 */
public class QuerySubassemblyInfoRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 3512173641441902114L;
    /**菜单id*/
    @NotNull(errorCode = "000001", message = "菜单id")
    @NotBlank(errorCode = "000001", message = "菜单id")
    private String            menuId;
    /**组件状态*/
    @NotNull(errorCode = "000001", message = "组件状态")
    private EnumObjectStatus  subassemblyStatus;

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
     * Getter method for property <tt>subassemblyStatus</tt>.
     * 
     * @return property value of subassemblyStatus
     */
    public EnumObjectStatus getSubassemblyStatus() {
        return subassemblyStatus;
    }

    /**
     * Setter method for property <tt>subassemblyStatus</tt>.
     * 
     * @param subassemblyStatus value to be assigned to property subassemblyStatus
     */
    public void setSubassemblyStatus(EnumObjectStatus subassemblyStatus) {
        this.subassemblyStatus = subassemblyStatus;
    }

}
