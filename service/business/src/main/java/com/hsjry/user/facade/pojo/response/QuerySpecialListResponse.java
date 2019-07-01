/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.CustomerAndCertificatePojo;

/**
 * 指定类型名单列表查询返回类
 * @author hongsj
 * @version $Id: QuerySpecialListResponse.java, v 1.0 2017年3月28日 下午1:59:05 hongsj Exp $
 */
public class QuerySpecialListResponse implements Serializable {
    /**  */
    private static final long                serialVersionUID = -2301911227695280650L;
    /**客户证件列表 */
    private List<CustomerAndCertificatePojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<CustomerAndCertificatePojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<CustomerAndCertificatePojo> pojoList) {
        this.pojoList = pojoList;
    }

}
