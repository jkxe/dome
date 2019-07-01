/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserCustomerInfoByTelsPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserCustomerInfoByTelsResponse.java, v 1.0 2017年6月30日 下午2:36:17 jiangjd12837 Exp $
 */
public class QueryUserCustomerInfoByTelsResponse implements Serializable {

    /**  */
    private static final long                     serialVersionUID = -6335405913065997627L;
    private List<QueryUserCustomerInfoByTelsPojo> queryUserCustomerInfoByTelsPojoList;

    /**
     * Getter method for property <tt>queryUserCustomerInfoByTelsPojoList</tt>.
     * 
     * @return property value of queryUserCustomerInfoByTelsPojoList
     */
    public List<QueryUserCustomerInfoByTelsPojo> getQueryUserCustomerInfoByTelsPojoList() {
        return queryUserCustomerInfoByTelsPojoList;
    }

    /**
     * Setter method for property <tt>queryUserCustomerInfoByTelsPojoList</tt>.
     * 
     * @param queryUserCustomerInfoByTelsPojoList value to be assigned to property queryUserCustomerInfoByTelsPojoList
     */
    public void setQueryUserCustomerInfoByTelsPojoList(List<QueryUserCustomerInfoByTelsPojo> queryUserCustomerInfoByTelsPojoList) {
        this.queryUserCustomerInfoByTelsPojoList = queryUserCustomerInfoByTelsPojoList;
    }

}
