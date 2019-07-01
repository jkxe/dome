/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumMenuType;

/**
 * 菜单树节点
 * @author hongsj
 * @version $Id: MenuTreeNode.java, v 1.0 2017年3月29日 上午11:07:16 hongsj Exp $
 */
public class MenuTreeNode implements Serializable {
    /**  */
    private static final long  serialVersionUID = 2087832437047398155L;
    /**菜单id*/
    private String             menuId;
    /**菜单名称*/
    private String             menuName;
    /**上级菜单id*/
    private String             parentMenuId;
    /**菜单类型*/
    private EnumMenuType       menuType;
    /**url*/
    private String             url;
    /**是否有子节点*/
    private boolean            hasChild;
    /**菜单排序*/
    private String             menuSite;
    /**权限Id*/
    private String             permissionId;
    /**权限名称*/
    private String             permissionName;
    /**子节点列表*/
    private List<MenuTreeNode> childNodes;

    
    
    
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
     * Getter method for property <tt>parentMenuId</tt>.
     * 
     * @return property value of parentMenuId
     */
    public String getParentMenuId() {
        return parentMenuId;
    }

    /**
     * Setter method for property <tt>parentMenuId</tt>.
     * 
     * @param parentMenuId value to be assigned to property parentMenuId
     */
    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    /**
     * Getter method for property <tt>url</tt>.
     * 
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     * 
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * Getter method for property <tt>childNodes</tt>.
     * 
     * @return property value of childNodes
     */
    public List<MenuTreeNode> getChildNodes() {
        return childNodes;
    }

    /**
     * Setter method for property <tt>childNodes</tt>.
     * 
     * @param childNodes value to be assigned to property childNodes
     */
    public void setChildNodes(List<MenuTreeNode> childNodes) {
        this.childNodes = childNodes;
    }

    /**
     * Getter method for property <tt>hasChild</tt>.
     * 
     * @return property value of hasChild
     */
    public boolean isHasChild() {
        return hasChild;
    }

    /**
     * Setter method for property <tt>hasChild</tt>.
     * 
     * @param hasChild value to be assigned to property hasChild
     */
    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
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

}
