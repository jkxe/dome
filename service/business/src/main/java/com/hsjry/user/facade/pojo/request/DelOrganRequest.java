/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author hongsj
 * @version $Id: DelOrganRequest.java, v 1.0 2017年3月18日 上午10:01:33 hongsj Exp $
 */
public class DelOrganRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -7240835758233512560L;
    /**
     * 组织Id
     */
    @NotNull(errorCode = "000001", message = "组织Id")
    @NotBlank(errorCode = "000001", message = "组织Id")
    private String            organId;

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }

}
