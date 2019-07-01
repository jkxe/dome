/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryRoleGroupingByuserIdListPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryRoleGroupingByuserIdListResponse.java, v 1.0 2017年7月7日 上午10:50:34 jiangjd12837 Exp $
 */
public class QueryRoleGroupingByuserIdListResponse implements Serializable {

    /**  */
    private static final long                       serialVersionUID = 7288978578270071301L;
    private List<QueryRoleGroupingByuserIdListPojo> queryRoleGroupingByuserIdListPojoList;

    /**
     * Getter method for property <tt>queryRoleGroupingByuserIdListPojoList</tt>.
     * 
     * @return property value of queryRoleGroupingByuserIdListPojoList
     */
    public List<QueryRoleGroupingByuserIdListPojo> getQueryRoleGroupingByuserIdListPojoList() {
        return queryRoleGroupingByuserIdListPojoList;
    }

    /**
     * Setter method for property <tt>queryRoleGroupingByuserIdListPojoList</tt>.
     * 
     * @param queryRoleGroupingByuserIdListPojoList value to be assigned to property queryRoleGroupingByuserIdListPojoList
     */
    public void setQueryRoleGroupingByuserIdListPojoList(List<QueryRoleGroupingByuserIdListPojo> queryRoleGroupingByuserIdListPojoList) {
        this.queryRoleGroupingByuserIdListPojoList = queryRoleGroupingByuserIdListPojoList;
    }

}
