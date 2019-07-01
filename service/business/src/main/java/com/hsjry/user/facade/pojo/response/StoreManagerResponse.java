/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author zhengqy15963
 * @version $Id: StoreManagerResponse.java, v 1.0 2018年5月9日 下午3:44:44 zhengqy15963 Exp $
 */
public class StoreManagerResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 8524251361065661494L;
    /**门店id  */
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
