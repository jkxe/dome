/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryGroupingPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryGroupingPageResponse.java, v 1.0 2017年6月16日 下午4:45:16 jiangjd12837 Exp $
 */
public class QueryGroupingPageResponse implements Serializable {

    /**  */
    private static final long           serialVersionUID = 3136311938372932105L;
    private List<QueryGroupingPagePojo> queryGroupingPagePojoList;

    /**
     * Getter method for property <tt>queryGroupingPagePojoList</tt>.
     * 
     * @return property value of queryGroupingPagePojoList
     */
    public List<QueryGroupingPagePojo> getQueryGroupingPagePojoList() {
        return queryGroupingPagePojoList;
    }

    /**
     * Setter method for property <tt>queryGroupingPagePojoList</tt>.
     * 
     * @param queryGroupingPagePojoList value to be assigned to property queryGroupingPagePojoList
     */
    public void setQueryGroupingPagePojoList(List<QueryGroupingPagePojo> queryGroupingPagePojoList) {
        this.queryGroupingPagePojoList = queryGroupingPagePojoList;
    }

}
