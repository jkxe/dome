/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserPermissionPojo;

/**
 * 请求权限返回类
 * @author hongsj
 * @version $Id: QueryPermissionResponse.java, v 1.0 2017年3月28日 下午2:37:44 hongsj Exp $
 */
public class QueryPermissionResponse implements Serializable {
    /**  */
    private static final long        serialVersionUID = -1080316577501620917L;
    /**权限列表*/
    private List<UserPermissionPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserPermissionPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserPermissionPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
