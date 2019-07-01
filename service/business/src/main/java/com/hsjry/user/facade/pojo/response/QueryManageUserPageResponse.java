/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryManageUserPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManageUserPageResponse.java, v 1.0 2017年4月28日 下午4:05:28 jiangjd12837 Exp $
 */
public class QueryManageUserPageResponse implements Serializable {

    /**  */
    private static final long             serialVersionUID = -8964472719578737619L;
    private List<QueryManageUserPagePojo> queryUserViewPagePojoList;

    /**
     * Getter method for property <tt>queryUserViewPagePojoList</tt>.
     * 
     * @return property value of queryUserViewPagePojoList
     */
    public List<QueryManageUserPagePojo> getQueryUserViewPagePojoList() {
        return queryUserViewPagePojoList;
    }

    /**
     * Setter method for property <tt>queryUserViewPagePojoList</tt>.
     * 
     * @param queryUserViewPagePojoList value to be assigned to property queryUserViewPagePojoList
     */
    public void setQueryUserViewPagePojoList(List<QueryManageUserPagePojo> queryUserViewPagePojoList) {
        this.queryUserViewPagePojoList = queryUserViewPagePojoList;
    }

}
