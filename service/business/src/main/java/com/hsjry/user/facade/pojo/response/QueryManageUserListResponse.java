/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryManageUserListPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserPersonalBasicInfoPageResponse.java, v 1.0 2017年5月10日 下午4:36:13 jiangjd12837 Exp $
 */
public class QueryManageUserListResponse implements Serializable {

    /**  */
    private static final long             serialVersionUID = -712365198152509769L;
    private List<QueryManageUserListPojo> queryManageUserListPojoList;

    /**
     * Getter method for property <tt>queryManageUserListPojoList</tt>.
     * 
     * @return property value of queryManageUserListPojoList
     */
    public List<QueryManageUserListPojo> getQueryManageUserListPojoList() {
        return queryManageUserListPojoList;
    }

    /**
     * Setter method for property <tt>queryManageUserListPojoList</tt>.
     * 
     * @param queryManageUserListPojoList value to be assigned to property queryManageUserListPojoList
     */
    public void setQueryManageUserListPojoList(List<QueryManageUserListPojo> queryManageUserListPojoList) {
        this.queryManageUserListPojoList = queryManageUserListPojoList;
    }

}
