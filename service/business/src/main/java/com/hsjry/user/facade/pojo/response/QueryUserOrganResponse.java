/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.OrganAndImageDataPojo;

/**
 * 查询所属门店返回类
 * @author hongsj
 * @version $Id: QueryUserOrganResponse.java, v 1.0 2017年3月25日 下午3:08:17 hongsj Exp $
 */
public class QueryUserOrganResponse implements Serializable {

    /**  */
    private static final long           serialVersionUID = -5683582865469778405L;

    /**
     * 门店和影像信息列表
     */
    private List<OrganAndImageDataPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<OrganAndImageDataPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<OrganAndImageDataPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
