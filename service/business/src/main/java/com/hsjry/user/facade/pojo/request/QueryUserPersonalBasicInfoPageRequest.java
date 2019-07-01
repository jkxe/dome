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
 * @author jiangjd12837
 * @version $Id: QueryUserPersonalBasicInfoPageRequest.java, v 1.0 2017年5月10日 下午4:32:17 jiangjd12837 Exp $
 */
public class QueryUserPersonalBasicInfoPageRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -3194580329785703212L;
    //组织ID
    @NotNull(errorCode = "000001", message = "组织ID")
    @NotBlank(errorCode = "000001", message = "组织ID")
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
