/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryMarketingCuesPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMarketingCuesResponse.java, v 1.0 2017年4月5日 上午10:01:40 jiangjd12837 Exp $
 */
public class QueryMarketingCuesResponse implements Serializable {

    /**  */
    private static final long            serialVersionUID = 3233847068256517462L;
    private List<QueryMarketingCuesPojo> queryMarketingCuesPojoList;

    /**
     * Getter method for property <tt>queryMarketingCuesPojoList</tt>.
     * 
     * @return property value of queryMarketingCuesPojoList
     */
    public List<QueryMarketingCuesPojo> getQueryMarketingCuesPojoList() {
        return queryMarketingCuesPojoList;
    }

    /**
     * Setter method for property <tt>queryMarketingCuesPojoList</tt>.
     * 
     * @param queryMarketingCuesPojoList value to be assigned to property queryMarketingCuesPojoList
     */
    public void setQueryMarketingCuesPojoList(List<QueryMarketingCuesPojo> queryMarketingCuesPojoList) {
        this.queryMarketingCuesPojoList = queryMarketingCuesPojoList;
    }

}
