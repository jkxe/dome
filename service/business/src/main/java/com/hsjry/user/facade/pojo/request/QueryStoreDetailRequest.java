/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 查询门店详情请求
 * @author huangbb
 * @version $Id: QueryStoreDetailRequest.java, v 1.0 2018年5月4日 上午10:43:23 huangbb Exp $
 */
public class QueryStoreDetailRequest implements Serializable {

    private static final long serialVersionUID = -8370696637993367056L;
    /**
     * 门店ID
     */
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
