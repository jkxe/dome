/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserSubassemblyPojo;

/**
 * 查询组件返回类
 * @author hongsj
 * @version $Id: QuerySubassemblyInfoResponse.java, v 1.0 2017年3月28日 下午2:18:36 hongsj Exp $
 */
public class QuerySubassemblyInfoResponse implements Serializable {
    /**  */
    private static final long         serialVersionUID = -3446774122657458786L;
    /**组件列表*/
    private List<UserSubassemblyPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserSubassemblyPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserSubassemblyPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
