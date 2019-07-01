/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.HashMap;

import com.hsjry.user.facade.pojo.UserQueryStoreInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryStoreInfoListResponse.java, v 1.0 2018年5月28日 下午4:12:08 zhengqy15963 Exp $
 */
public class QueryStoreInfoListResponse implements Serializable {

    /**  */
    private static final long                       serialVersionUID = 2453003385034770826L;

    private HashMap<String, UserQueryStoreInfoPojo> StoreInfoListHashMap;

    /**
     * Getter method for property <tt>storeInfoListHashMap</tt>.
     * 
     * @return property value of StoreInfoListHashMap
     */
    public HashMap<String, UserQueryStoreInfoPojo> getStoreInfoListHashMap() {
        return StoreInfoListHashMap;
    }

    /**
     * Setter method for property <tt>storeInfoListHashMap</tt>.
     * 
     * @param StoreInfoListHashMap value to be assigned to property storeInfoListHashMap
     */
    public void setStoreInfoListHashMap(HashMap<String, UserQueryStoreInfoPojo> storeInfoListHashMap) {
        StoreInfoListHashMap = storeInfoListHashMap;
    }

}
