/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserViewPagePojo;

/**
 * 客户视图分页查询
 * @author jiangjd12837
 * @version $Id: QueryUserViewPageResponse.java, v 1.0 2017年3月30日 下午1:53:47 jiangjd12837 Exp $
 */
public class QueryIndividualUserViewPageResponse implements Serializable {

    /**  */
    private static final long           serialVersionUID = 3849162330832346422L;

    private List<QueryUserViewPagePojo> queryUserViewPagePojoList;

    /**
     * Getter method for property <tt>queryUserViewPagePojoList</tt>.
     * 
     * @return property value of queryUserViewPagePojoList
     */
    public List<QueryUserViewPagePojo> getQueryUserViewPagePojoList() {
        return queryUserViewPagePojoList;
    }

    /**
     * Setter method for property <tt>queryUserViewPagePojoList</tt>.
     * 
     * @param queryUserViewPagePojoList value to be assigned to property queryUserViewPagePojoList
     */
    public void setQueryUserViewPagePojoList(List<QueryUserViewPagePojo> queryUserViewPagePojoList) {
        this.queryUserViewPagePojoList = queryUserViewPagePojoList;
    }
}
