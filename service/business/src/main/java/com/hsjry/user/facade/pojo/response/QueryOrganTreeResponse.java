/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.OrganTreeNode;

/**
 * 组织树返回类
 * @author hongsj
 * @version $Id: QueryMenuTreeResponse.java, v 1.0 2017年3月29日 下午2:47:39 hongsj Exp $
 */
public class QueryOrganTreeResponse implements Serializable {
    /**  */
    private static final long serialVersionUID = -913777297312419294L;
    /**组织树节点*/
    private OrganTreeNode     node;

    /**
     * Getter method for property <tt>node</tt>.
     * 
     * @return property value of node
     */
    public OrganTreeNode getNode() {
        return node;
    }

    /**
     * Setter method for property <tt>node</tt>.
     * 
     * @param node value to be assigned to property node
     */
    public void setNode(OrganTreeNode node) {
        this.node = node;
    }

}
