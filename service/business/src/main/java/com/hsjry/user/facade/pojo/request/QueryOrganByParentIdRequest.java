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
 * @version $Id: QueryOrganByParentIdRequest.java, v 1.0 2017年5月11日 下午1:37:18 hongsj Exp $
 */
public class QueryOrganByParentIdRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -812745858172219856L;

    /**父组织id*/
    @NotNull(errorCode = "000001", message = "父组织Id")
    @NotBlank(errorCode = "000001", message = "父组织Id")
    private String            parentOrganId;

    /**
     * Getter method for property <tt>parentOrganId</tt>.
     * 
     * @return property value of parentOrganId
     */
    public String getParentOrganId() {
        return parentOrganId;
    }

    /**
     * Setter method for property <tt>parentOrganId</tt>.
     * 
     * @param parentOrganId value to be assigned to property parentOrganId
     */
    public void setParentOrganId(String parentOrganId) {
        this.parentOrganId = parentOrganId;
    }

}
