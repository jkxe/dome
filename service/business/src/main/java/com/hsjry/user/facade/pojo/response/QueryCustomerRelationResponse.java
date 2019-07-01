/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryCustomerRelationPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCustomerRelationResponse.java, v 1.0 2017年3月30日 下午4:15:55 jiangjd12837 Exp $
 */
public class QueryCustomerRelationResponse implements Serializable {

    /**  */
    private static final long               serialVersionUID = -8184112718281055137L;
    //客户关系人信息
    private List<QueryCustomerRelationPojo> QueryCustomerRelationPojoList;

    /**
     * Getter method for property <tt>queryCustomerRelationPojoList</tt>.
     * 
     * @return property value of QueryCustomerRelationPojoList
     */
    public List<QueryCustomerRelationPojo> getQueryCustomerRelationPojoList() {
        return QueryCustomerRelationPojoList;
    }

    /**
     * Setter method for property <tt>queryCustomerRelationPojoList</tt>.
     * 
     * @param QueryCustomerRelationPojoList value to be assigned to property queryCustomerRelationPojoList
     */
    public void setQueryCustomerRelationPojoList(List<QueryCustomerRelationPojo> queryCustomerRelationPojoList) {
        QueryCustomerRelationPojoList = queryCustomerRelationPojoList;
    }

}
