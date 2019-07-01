/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumMenuType;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 客户权限菜单pojo
 * @author hongsj
 * @version $Id: PermissionMenuPojo.java, v 1.0 2017年3月13日 下午4:03:51 hongsj Exp $
 */
public class PermissionMenuPojo implements Serializable {
    /**  */
    private static final long serialVersionUID = -7941004584991770455L;
    /**菜单id*/
    private String            menuId;
    /**菜单名称*/
    private String            menuName;
    /**菜单描述*/
    private String            menuHint;
    /**上级菜单*/
    private String            parentMenu;
    /**菜单识别*/
    private String            menuDisting;
    /**是否叶子节点*/
    private EnumBool          leafFlag;
    /**交易级别*/
    private String            tradeLevelNo;
    /**菜单类型*/
    private EnumMenuType      menuType;
    /**菜单排序*/
    private String            menuSite;
    /**权限id*/
    private String            permissionId;
    /**权限名称*/
    private String            permissionName;
    /**权限描述*/
    private String            permissionDesc;
    /**权限状态*/
    private EnumObjectStatus  permissionStatus;
    /**租户id*/
    private String            tenantId;

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
     * Getter method for property <tt>menuName</tt>.
     * 
     * @return property value of menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * Setter method for property <tt>menuName</tt>.
     * 
     * @param menuName value to be assigned to property menuName
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * Getter method for property <tt>menuHint</tt>.
     * 
     * @return property value of menuHint
     */
    public String getMenuHint() {
        return menuHint;
    }

    /**
     * Setter method for property <tt>menuHint</tt>.
     * 
     * @param menuHint value to be assigned to property menuHint
     */
    public void setMenuHint(String menuHint) {
        this.menuHint = menuHint;
    }

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

    /**
     * Getter method for property <tt>tradeLevelNo</tt>.
     * 
     * @return property value of tradeLevelNo
     */
    public String getTradeLevelNo() {
        return tradeLevelNo;
    }

    /**
     * Setter method for property <tt>tradeLevelNo</tt>.
     * 
     * @param tradeLevelNo value to be assigned to property tradeLevelNo
     */
    public void setTradeLevelNo(String tradeLevelNo) {
        this.tradeLevelNo = tradeLevelNo;
    }

    /**
     * Getter method for property <tt>menuSite</tt>.
     * 
     * @return property value of menuSite
     */
    public String getMenuSite() {
        return menuSite;
    }

    /**
     * Setter method for property <tt>menuSite</tt>.
     * 
     * @param menuSite value to be assigned to property menuSite
     */
    public void setMenuSite(String menuSite) {
        this.menuSite = menuSite;
    }

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
     * Getter method for property <tt>permissionName</tt>.
     * 
     * @return property value of permissionName
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Setter method for property <tt>permissionName</tt>.
     * 
     * @param permissionName value to be assigned to property permissionName
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Getter method for property <tt>permissionDesc</tt>.
     * 
     * @return property value of permissionDesc
     */
    public String getPermissionDesc() {
        return permissionDesc;
    }

    /**
     * Setter method for property <tt>permissionDesc</tt>.
     * 
     * @param permissionDesc value to be assigned to property permissionDesc
     */
    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    /**
     * Getter method for property <tt>leafFlag</tt>.
     * 
     * @return property value of leafFlag
     */
    public EnumBool getLeafFlag() {
        return leafFlag;
    }

    /**
     * Setter method for property <tt>leafFlag</tt>.
     * 
     * @param leafFlag value to be assigned to property leafFlag
     */
    public void setLeafFlag(EnumBool leafFlag) {
        this.leafFlag = leafFlag;
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

    /**
     * Getter method for property <tt>permissionStatus</tt>.
     * 
     * @return property value of permissionStatus
     */
    public EnumObjectStatus getPermissionStatus() {
        return permissionStatus;
    }

    /**
     * Setter method for property <tt>permissionStatus</tt>.
     * 
     * @param permissionStatus value to be assigned to property permissionStatus
     */
    public void setPermissionStatus(EnumObjectStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
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

}
