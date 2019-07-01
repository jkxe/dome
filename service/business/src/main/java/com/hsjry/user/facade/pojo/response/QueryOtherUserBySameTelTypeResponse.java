/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryOtherUserBySameTelTypePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryOtherUserBySameTelTypeResponse.java, v 1.0 2017年6月30日 下午4:42:40 jiangjd12837 Exp $
 */
public class QueryOtherUserBySameTelTypeResponse implements Serializable {

    /**  */
    private static final long                     serialVersionUID = -3104483084829753034L;
    private List<QueryOtherUserBySameTelTypePojo> queryOtherUserBySameTelTypePojoList;

    /**
     * Getter method for property <tt>queryOtherUserBySameTelTypePojoList</tt>.
     * 
     * @return property value of queryOtherUserBySameTelTypePojoList
     */
    public List<QueryOtherUserBySameTelTypePojo> getQueryOtherUserBySameTelTypePojoList() {
        return queryOtherUserBySameTelTypePojoList;
    }

    /**
     * Setter method for property <tt>queryOtherUserBySameTelTypePojoList</tt>.
     * 
     * @param queryOtherUserBySameTelTypePojoList value to be assigned to property queryOtherUserBySameTelTypePojoList
     */
    public void setQueryOtherUserBySameTelTypePojoList(List<QueryOtherUserBySameTelTypePojo> queryOtherUserBySameTelTypePojoList) {
        this.queryOtherUserBySameTelTypePojoList = queryOtherUserBySameTelTypePojoList;
    }

}
