/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserManageViewPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserManageViewPageResponse.java, v 1.0 2017年9月18日 上午10:04:57 jiangjd12837 Exp $
 */
public class QueryUserManageViewPageResponse implements Serializable {

    /**  */
    private static final long                 serialVersionUID = 7550432692084224289L;
    private List<QueryUserManageViewPagePojo> queryUserManageViewPagePojoList;

    /**
     * Getter method for property <tt>queryUserManageViewPagePojoList</tt>.
     * 
     * @return property value of queryUserManageViewPagePojoList
     */
    public List<QueryUserManageViewPagePojo> getQueryUserManageViewPagePojoList() {
        return queryUserManageViewPagePojoList;
    }

    /**
     * Setter method for property <tt>queryUserManageViewPagePojoList</tt>.
     * 
     * @param queryUserManageViewPagePojoList value to be assigned to property queryUserManageViewPagePojoList
     */
    public void setQueryUserManageViewPagePojoList(List<QueryUserManageViewPagePojo> queryUserManageViewPagePojoList) {
        this.queryUserManageViewPagePojoList = queryUserManageViewPagePojoList;
    }

}
