/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryRecommendViePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryRecommendVieResponse.java, v 1.0 2017年4月19日 下午4:48:18 jiangjd12837 Exp $
 */
public class QueryRecommendVieResponse implements Serializable {

    /**  */
    private static final long           serialVersionUID = 3923093835213119840L;
    //电话联系点
    private List<QueryRecommendViePojo> queryRecommendViePojoList;

    /**
     * Getter method for property <tt>queryRecommendViePojoList</tt>.
     * 
     * @return property value of queryRecommendViePojoList
     */
    public List<QueryRecommendViePojo> getQueryRecommendViePojoList() {
        return queryRecommendViePojoList;
    }

    /**
     * Setter method for property <tt>queryRecommendViePojoList</tt>.
     * 
     * @param queryRecommendViePojoList value to be assigned to property queryRecommendViePojoList
     */
    public void setQueryRecommendViePojoList(List<QueryRecommendViePojo> queryRecommendViePojoList) {
        this.queryRecommendViePojoList = queryRecommendViePojoList;
    }

}
