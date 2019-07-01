/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserPersonalBasicInfoPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserPersonalBasicInfoPageResponse.java, v 1.0 2017年5月10日 下午4:36:13 jiangjd12837 Exp $
 */
public class QueryUserPersonalBasicInfoPageResponse implements Serializable {

    /**  */
    private static final long                        serialVersionUID = 7047075465062541197L;

    private List<QueryUserPersonalBasicInfoPagePojo> queryUserPersonalBasicInfoPagePojoList;

    /**
     * Getter method for property <tt>queryUserPersonalBasicInfoPagePojoList</tt>.
     * 
     * @return property value of queryUserPersonalBasicInfoPagePojoList
     */
    public List<QueryUserPersonalBasicInfoPagePojo> getQueryUserPersonalBasicInfoPagePojoList() {
        return queryUserPersonalBasicInfoPagePojoList;
    }

    /**
     * Setter method for property <tt>queryUserPersonalBasicInfoPagePojoList</tt>.
     * 
     * @param queryUserPersonalBasicInfoPagePojoList value to be assigned to property queryUserPersonalBasicInfoPagePojoList
     */
    public void setQueryUserPersonalBasicInfoPagePojoList(List<QueryUserPersonalBasicInfoPagePojo> queryUserPersonalBasicInfoPagePojoList) {
        this.queryUserPersonalBasicInfoPagePojoList = queryUserPersonalBasicInfoPagePojoList;
    }

}
