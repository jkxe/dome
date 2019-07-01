/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserSpecialListPojo;

/**
 * 查询特殊名单视图返回类
 * @author hongsj
 * @version $Id: QuerySpecialViewResponse.java, v 1.0 2017年3月28日 下午2:07:47 hongsj Exp $
 */
public class QuerySpecialViewResponse implements Serializable {
    /**  */
    private static final long         serialVersionUID = -4375379386821163649L;
    /**特殊名单列表*/
    private List<UserSpecialListPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserSpecialListPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserSpecialListPojo> pojoList) {
        this.pojoList = pojoList;
    }
}
