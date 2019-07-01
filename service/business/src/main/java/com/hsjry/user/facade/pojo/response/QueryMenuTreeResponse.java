/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.MenuTreeNode;

/**
 * 菜单树返回类
 * @author hongsj
 * @version $Id: QueryMenuTreeResponse.java, v 1.0 2017年3月29日 下午2:47:39 hongsj Exp $
 */
public class QueryMenuTreeResponse implements Serializable {
    /**  */
    private static final long serialVersionUID = -913777297312419294L;
    /**菜单树节点*/
    private MenuTreeNode      node;

    /**
     * Getter method for property <tt>node</tt>.
     * 
     * @return property value of node
     */
    public MenuTreeNode getNode() {
        return node;
    }

    /**
     * Setter method for property <tt>node</tt>.
     * 
     * @param node value to be assigned to property node
     */
    public void setNode(MenuTreeNode node) {
        this.node = node;
    }

}
