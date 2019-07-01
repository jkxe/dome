/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author zhengqy15963
 * @version $Id: StoreAdmittanceRequest.java, v 1.0 2018年5月9日 上午9:36:43 zhengqy15963 Exp $
 */
public class StoreAdmittanceRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -662459908672699716L;
    /**门店组织id  */
    @NotNull(errorCode = "000001", message = "门店组织id")
    @NotBlank(errorCode = "000001", message = "门店组织id")
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
