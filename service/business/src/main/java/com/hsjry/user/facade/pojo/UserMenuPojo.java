/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumMenuType;

/**
 * 菜单返回对象
 * @author hongsj
 * @version $Id: UserMenuPojo.java, v 1.0 2017年3月21日 下午2:44:12 hongsj Exp $
 */
public class UserMenuPojo implements Serializable {
    /**  */
    private static final long serialVersionUID = 2629086783472830270L;
    /**
     * 菜单Id
     */
    private String            menuId;
    /**
     * 租户Id
     */
    private String            tenantId;
    /**
     * 菜单名称
     */
    private String            menuName;
    /**
     * 菜单描述
     */
    private String            menuHint;
    /**
     * 父菜单Id
     */
    private String            parentMenu;
    /**
     * 菜单识别
     */
    private String            menuDisting;
    /**
     * 是否叶子节点
     */
    private EnumBool          leafFlag;
    /**
     * 交易级别
     */
    private String            tradeLevelNo;
    /**
     * 菜单类型
     */
    private EnumMenuType      menuType;
    /**
     * 菜单排序
     */
    private String            menuSite;
    /**
     * 创建时间
     */
    private Date              createTime;
    /**
     * 创建人
     */
    private String            creator;
    /**
     * 修改时间
     */
    private Date              updateTime;
    /**
     * 修改人
     */
    private String            modifier;

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
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property <tt>creator</tt>.
     * 
     * @return property value of creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Setter method for property <tt>creator</tt>.
     * 
     * @param creator value to be assigned to property creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Getter method for property <tt>updateTime</tt>.
     * 
     * @return property value of updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Setter method for property <tt>updateTime</tt>.
     * 
     * @param updateTime value to be assigned to property updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Getter method for property <tt>modifier</tt>.
     * 
     * @return property value of modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * Setter method for property <tt>modifier</tt>.
     * 
     * @param modifier value to be assigned to property modifier
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

}
