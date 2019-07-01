/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserChannelSourcePojo;

/**
 * 查询渠道来源返回类
 * @author hongsj
 * @version $Id: QueryChannelSourceListResponse.java, v 1.0 2017年3月28日 下午1:38:44 hongsj Exp $
 */
public class QueryChannelSourceListResponse implements Serializable {
    /**  */
    private static final long           serialVersionUID = 1682855329623693761L;
    /**渠道来源列表*/
    private List<UserChannelSourcePojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserChannelSourcePojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserChannelSourcePojo> pojoList) {
        this.pojoList = pojoList;
    }
}
