/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumRootId;

import net.sf.oval.constraint.NotNull;

/**
 * 获取菜单树请求类
 * @author hongsj
 * @version $Id: QueryMenuTreeRequest.java, v 1.0 2017年3月29日 下午2:52:03 hongsj Exp $
 */
public class QueryOrganTreeRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -4065170061003477442L;

    /**树根节点id*/
    @NotNull(errorCode = "000001", message = "树根节点id")
    private EnumRootId        rootId;

    /**
     * Getter method for property <tt>rootId</tt>.
     * 
     * @return property value of rootId
     */
    public EnumRootId getRootId() {
        return rootId;
    }

    /**
     * Setter method for property <tt>rootId</tt>.
     * 
     * @param rootId value to be assigned to property rootId
     */
    public void setRootId(EnumRootId rootId) {
        this.rootId = rootId;
    }

}
