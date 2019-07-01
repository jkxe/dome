/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserAppInfoByUserIdListPojo;

/**
 * 
 * @author huangbb
 * @version $Id: QueryUserAppInfoResponse.java, v 1.0 2017年4月20日 下午7:09:45 jiangjd12837 Exp $
 */
public class QueryUserAppInfoByUserIdListResponse implements Serializable {

    /**  */
    private static final long                      serialVersionUID = -7253898215774811348L;
    //客户ID
    private List<QueryUserAppInfoByUserIdListPojo> queryUserAppInfoByUserIdListPojoList;

    /**
     * Getter method for property <tt>queryUserAppInfoByUserIdListPojoList</tt>.
     * 
     * @return property value of queryUserAppInfoByUserIdListPojoList
     */
    public List<QueryUserAppInfoByUserIdListPojo> getQueryUserAppInfoByUserIdListPojoList() {
        return queryUserAppInfoByUserIdListPojoList;
    }

    /**
     * Setter method for property <tt>queryUserAppInfoByUserIdListPojoList</tt>.
     * 
     * @param queryUserAppInfoByUserIdListPojoList value to be assigned to property queryUserAppInfoByUserIdListPojoList
     */
    public void setQueryUserAppInfoByUserIdListPojoList(List<QueryUserAppInfoByUserIdListPojo> queryUserAppInfoByUserIdListPojoList) {
        this.queryUserAppInfoByUserIdListPojoList = queryUserAppInfoByUserIdListPojoList;
    }

}
