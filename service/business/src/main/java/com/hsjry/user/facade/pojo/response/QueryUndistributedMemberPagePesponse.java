/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUndistributedMemberPagePojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUndistributedMemberPagePesponse.java, v 1.0 2017年6月16日 下午5:15:11 jiangjd12837 Exp $
 */
public class QueryUndistributedMemberPagePesponse implements Serializable {

    /**  */
    private static final long                      serialVersionUID = 5498731239764675408L;
    private List<QueryUndistributedMemberPagePojo> queryUndistributedMemberPagePojoList;

    /**
     * Getter method for property <tt>queryUndistributedMemberPagePojoList</tt>.
     * 
     * @return property value of queryUndistributedMemberPagePojoList
     */
    public List<QueryUndistributedMemberPagePojo> getQueryUndistributedMemberPagePojoList() {
        return queryUndistributedMemberPagePojoList;
    }

    /**
     * Setter method for property <tt>queryUndistributedMemberPagePojoList</tt>.
     * 
     * @param queryUndistributedMemberPagePojoList value to be assigned to property queryUndistributedMemberPagePojoList
     */
    public void setQueryUndistributedMemberPagePojoList(List<QueryUndistributedMemberPagePojo> queryUndistributedMemberPagePojoList) {
        this.queryUndistributedMemberPagePojoList = queryUndistributedMemberPagePojoList;
    }

}
