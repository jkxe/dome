/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 根据id查询组织
 * @author hongsj
 * @version $Id: QueryUserOrganByIdRequest.java, v 1.0 2017年3月29日 上午11:15:12 hongsj Exp $
 */
public class QueryUserOrganByIdRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -1146008076944372602L;
    /**组织id*/
    @NotNull(errorCode = "000001", message = "组织id")
    @NotBlank(errorCode = "000001", message = "组织id")
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
