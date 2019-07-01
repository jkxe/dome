/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryGroupMemberPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryGroupMemberPageResponse.java, v 1.0 2017年6月16日 下午4:58:29 jiangjd12837 Exp $
 */
public class QueryGroupMemberPageResponse implements Serializable {

    /**  */
    private static final long              serialVersionUID = -697135399530708805L;
    private List<QueryGroupMemberPagePojo> queryGroupMemberPagePojoList;

    /**
     * Getter method for property <tt>queryGroupMemberPagePojoList</tt>.
     * 
     * @return property value of queryGroupMemberPagePojoList
     */
    public List<QueryGroupMemberPagePojo> getQueryGroupMemberPagePojoList() {
        return queryGroupMemberPagePojoList;
    }

    /**
     * Setter method for property <tt>queryGroupMemberPagePojoList</tt>.
     * 
     * @param queryGroupMemberPagePojoList value to be assigned to property queryGroupMemberPagePojoList
     */
    public void setQueryGroupMemberPagePojoList(List<QueryGroupMemberPagePojo> queryGroupMemberPagePojoList) {
        this.queryGroupMemberPagePojoList = queryGroupMemberPagePojoList;
    }

}
