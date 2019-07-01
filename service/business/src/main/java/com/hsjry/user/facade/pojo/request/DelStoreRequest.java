/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 删除门店接口入参
 * @author zhengqy15963
 * @version $Id: DelStoreRequest.java, v 1.0 2018年5月12日 上午11:21:07 zhengqy15963 Exp $
 */
public class DelStoreRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -893156505623446107L;
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
