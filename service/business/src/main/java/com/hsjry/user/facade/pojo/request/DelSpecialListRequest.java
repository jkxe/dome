/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 删除特殊名单
 * @author hongsj
 * @version $Id: DelSpecialListRequest.java, v 1.0 2017年3月28日 上午10:14:49 hongsj Exp $
 */
public class DelSpecialListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 7736561988455101378L;
    /**特殊名单Id*/
    @NotNull(errorCode = "000001", message = "特殊名单Id")
    @NotBlank(errorCode = "000001", message = "特殊名单Id")
    private String            listId;

    /**
     * Getter method for property <tt>listId</tt>.
     * 
     * @return property value of listId
     */
    public String getListId() {
        return listId;
    }

    /**
     * Setter method for property <tt>listId</tt>.
     * 
     * @param listId value to be assigned to property listId
     */
    public void setListId(String listId) {
        this.listId = listId;
    }
}
