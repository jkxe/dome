/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserRecommendSerialPojo;

/**
 * 查询推荐流水返回类
 * @author hongsj
 * @version $Id: QueryRecommendSerialResponse.java, v 1.0 2017年3月31日 上午10:31:38 hongsj Exp $
 */
public class QueryRecommendSerialResponse implements Serializable {
    /**  */
    private static final long             serialVersionUID = -6699659974828488372L;
    /**推荐流水列表*/
    private List<UserRecommendSerialPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserRecommendSerialPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserRecommendSerialPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
