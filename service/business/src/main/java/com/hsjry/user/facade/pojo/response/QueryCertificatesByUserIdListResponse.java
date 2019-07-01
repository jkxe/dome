/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryCertificatesByUserIdListPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCertificatesByUserIdListResponse.java, v 1.0 2017年8月21日 下午8:09:28 jiangjd12837 Exp $
 */
public class QueryCertificatesByUserIdListResponse implements Serializable {

    /**  */
    private static final long                       serialVersionUID = -6767478974180403418L;
    private List<QueryCertificatesByUserIdListPojo> queryCertificatesByUserIdListPojoList;

    /**
     * Getter method for property <tt>queryCertificatesByUserIdListPojoList</tt>.
     * 
     * @return property value of queryCertificatesByUserIdListPojoList
     */
    public List<QueryCertificatesByUserIdListPojo> getQueryCertificatesByUserIdListPojoList() {
        return queryCertificatesByUserIdListPojoList;
    }

    /**
     * Setter method for property <tt>queryCertificatesByUserIdListPojoList</tt>.
     * 
     * @param queryCertificatesByUserIdListPojoList value to be assigned to property queryCertificatesByUserIdListPojoList
     */
    public void setQueryCertificatesByUserIdListPojoList(List<QueryCertificatesByUserIdListPojo> queryCertificatesByUserIdListPojoList) {
        this.queryCertificatesByUserIdListPojoList = queryCertificatesByUserIdListPojoList;
    }

}
