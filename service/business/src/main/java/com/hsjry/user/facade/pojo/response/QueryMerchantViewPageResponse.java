/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryMerchantViewPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMerchantViewPageResponse.java, v 1.0 2017年3月30日 下午3:37:50 jiangjd12837 Exp $
 */
public class QueryMerchantViewPageResponse implements Serializable {

    /**  */
    private static final long               serialVersionUID = 2160148996498584084L;
    private List<QueryMerchantViewPagePojo> queryMerchantViewPagePojoList;

    /**
     * Getter method for property <tt>queryMerchantViewPagePojoList</tt>.
     * 
     * @return property value of queryMerchantViewPagePojoList
     */
    public List<QueryMerchantViewPagePojo> getQueryMerchantViewPagePojoList() {
        return queryMerchantViewPagePojoList;
    }

    /**
     * Setter method for property <tt>queryMerchantViewPagePojoList</tt>.
     * 
     * @param queryMerchantViewPagePojoList value to be assigned to property queryMerchantViewPagePojoList
     */
    public void setQueryMerchantViewPagePojoList(List<QueryMerchantViewPagePojo> queryMerchantViewPagePojoList) {
        this.queryMerchantViewPagePojoList = queryMerchantViewPagePojoList;
    }
}
