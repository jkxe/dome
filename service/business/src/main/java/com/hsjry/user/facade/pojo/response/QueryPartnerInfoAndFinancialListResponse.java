/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryPartnerInfoAndFinancialListPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryPartnerInfoAndFinancialListResponse.java, v 1.0 2017年7月26日 下午1:41:42 jiangjd12837 Exp $
 */
public class QueryPartnerInfoAndFinancialListResponse implements Serializable {

    /**  */
    private static final long                          serialVersionUID = -3551650179264618776L;
    //合作方金融工具信息
    private List<QueryPartnerInfoAndFinancialListPojo> queryPartnerInfoAndFinancialListPojoList;

    /**
     * Getter method for property <tt>queryPartnerInfoAndFinancialListPojoList</tt>.
     * 
     * @return property value of queryPartnerInfoAndFinancialListPojoList
     */
    public List<QueryPartnerInfoAndFinancialListPojo> getQueryPartnerInfoAndFinancialListPojoList() {
        return queryPartnerInfoAndFinancialListPojoList;
    }

    /**
     * Setter method for property <tt>queryPartnerInfoAndFinancialListPojoList</tt>.
     * 
     * @param queryPartnerInfoAndFinancialListPojoList value to be assigned to property queryPartnerInfoAndFinancialListPojoList
     */
    public void setQueryPartnerInfoAndFinancialListPojoList(List<QueryPartnerInfoAndFinancialListPojo> queryPartnerInfoAndFinancialListPojoList) {
        this.queryPartnerInfoAndFinancialListPojoList = queryPartnerInfoAndFinancialListPojoList;
    }

}
