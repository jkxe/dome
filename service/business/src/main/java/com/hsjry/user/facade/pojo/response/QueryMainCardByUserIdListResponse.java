/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryMainCardByUserIdListPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMainCardByUserIdListResponse.java, v 1.0 2017年7月24日 下午2:16:32 jiangjd12837 Exp $
 */
public class QueryMainCardByUserIdListResponse implements Serializable {

    /**  */
    private static final long                   serialVersionUID = 2058842432239373502L;
    //主卡信息
    private List<QueryMainCardByUserIdListPojo> queryMainCardByUserIdListPojoList;

    /**
     * Getter method for property <tt>queryMainCardByUserIdListPojoList</tt>.
     * 
     * @return property value of queryMainCardByUserIdListPojoList
     */
    public List<QueryMainCardByUserIdListPojo> getQueryMainCardByUserIdListPojoList() {
        return queryMainCardByUserIdListPojoList;
    }

    /**
     * Setter method for property <tt>queryMainCardByUserIdListPojoList</tt>.
     * 
     * @param queryMainCardByUserIdListPojoList value to be assigned to property queryMainCardByUserIdListPojoList
     */
    public void setQueryMainCardByUserIdListPojoList(List<QueryMainCardByUserIdListPojo> queryMainCardByUserIdListPojoList) {
        this.queryMainCardByUserIdListPojoList = queryMainCardByUserIdListPojoList;
    }

}
