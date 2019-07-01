/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 组件pojo
 * @author hongsj
 * @version $Id: UserSubassemblyPojo.java, v 1.0 2017年3月21日 下午6:25:38 hongsj Exp $
 */
public class UserSubassemblyPojo implements Serializable {
    /**  */
    private static final long serialVersionUID = 4226143842084065456L;
    /**
     * 组件Id
     */
    private String            subassemblyId;
    /**
     * 租户Id
     */
    private String            tenantId;
    /**
     * 组件名称
     */
    private String            subassemblyName;
    /**
     * 组件描述
     */
    private String            subassemblyDesc;
    /**
     * 组件识别
     */
    private String            subassemblyDisting;
    /**
     * 组件状态
     */
    private EnumObjectStatus  subassemblyStatus;
    /**
     * 菜单Id
     */
    private String            menuId;

    /**
     * Getter method for property <tt>subassemblyId</tt>.
     * 
     * @return property value of subassemblyId
     */
    public String getSubassemblyId() {
        return subassemblyId;
    }

    /**
     * Setter method for property <tt>subassemblyId</tt>.
     * 
     * @param subassemblyId value to be assigned to property subassemblyId
     */
    public void setSubassemblyId(String subassemblyId) {
        this.subassemblyId = subassemblyId;
    }

    /**
     * Getter method for property <tt>tenantId</tt>.
     * 
     * @return property value of tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Setter method for property <tt>tenantId</tt>.
     * 
     * @param tenantId value to be assigned to property tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Getter method for property <tt>subassemblyName</tt>.
     * 
     * @return property value of subassemblyName
     */
    public String getSubassemblyName() {
        return subassemblyName;
    }

    /**
     * Setter method for property <tt>subassemblyName</tt>.
     * 
     * @param subassemblyName value to be assigned to property subassemblyName
     */
    public void setSubassemblyName(String subassemblyName) {
        this.subassemblyName = subassemblyName;
    }

    /**
     * Getter method for property <tt>subassemblyDesc</tt>.
     * 
     * @return property value of subassemblyDesc
     */
    public String getSubassemblyDesc() {
        return subassemblyDesc;
    }

    /**
     * Setter method for property <tt>subassemblyDesc</tt>.
     * 
     * @param subassemblyDesc value to be assigned to property subassemblyDesc
     */
    public void setSubassemblyDesc(String subassemblyDesc) {
        this.subassemblyDesc = subassemblyDesc;
    }

    /**
     * Getter method for property <tt>subassemblyDisting</tt>.
     * 
     * @return property value of subassemblyDisting
     */
    public String getSubassemblyDisting() {
        return subassemblyDisting;
    }

    /**
     * Setter method for property <tt>subassemblyDisting</tt>.
     * 
     * @param subassemblyDisting value to be assigned to property subassemblyDisting
     */
    public void setSubassemblyDisting(String subassemblyDisting) {
        this.subassemblyDisting = subassemblyDisting;
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
