/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.StoreDetailPojo;

/**
 * 
 * @author huangbb
 * @version $Id: QueryStoreDetailResponse.java, v 1.0 2018年5月4日 上午10:42:18 huangbb Exp $
 */
public class QueryStoreDetailResponse implements Serializable {

    private static final long serialVersionUID = 1481853332095077479L;
    
    private StoreDetailPojo storeDetailPojo;

    /**
     * Getter method for property <tt>storeDetailPojo</tt>.
     * 
     * @return property value of storeDetailPojo
     */
    public StoreDetailPojo getStoreDetailPojo() {
        return storeDetailPojo;
    }

    /**
     * Setter method for property <tt>storeDetailPojo</tt>.
     * 
     * @param storeDetailPojo value to be assigned to property storeDetailPojo
     */
    public void setStoreDetailPojo(StoreDetailPojo storeDetailPojo) {
        this.storeDetailPojo = storeDetailPojo;
    }
    
    
    
}
