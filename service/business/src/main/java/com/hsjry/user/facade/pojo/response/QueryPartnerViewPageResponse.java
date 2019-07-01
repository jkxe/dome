/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryPartnerViewPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryPartnerViewPageResponse.java, v 1.0 2017年3月30日 下午6:41:42 jiangjd12837 Exp $
 */
public class QueryPartnerViewPageResponse implements Serializable {

    /**  */
    private static final long              serialVersionUID = -9046896412838402179L;
    private List<QueryPartnerViewPagePojo> queryPartnerViewPagePojoList;
    /**
     * Getter method for property <tt>queryPartnerViewPagePojoList</tt>.
     * 
     * @return property value of queryPartnerViewPagePojoList
     */
    public List<QueryPartnerViewPagePojo> getQueryPartnerViewPagePojoList() {
        return queryPartnerViewPagePojoList;
    }
    /**
     * Setter method for property <tt>queryPartnerViewPagePojoList</tt>.
     * 
     * @param queryPartnerViewPagePojoList value to be assigned to property queryPartnerViewPagePojoList
     */
    public void setQueryPartnerViewPagePojoList(List<QueryPartnerViewPagePojo> queryPartnerViewPagePojoList) {
        this.queryPartnerViewPagePojoList = queryPartnerViewPagePojoList;
    }
}
