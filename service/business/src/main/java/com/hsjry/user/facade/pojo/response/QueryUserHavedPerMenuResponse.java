/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.PermissionMenuPojo;

/**
 * 权限菜单返回类
 * @author hongsj
 * @version $Id: QueryUserHavedPerMenuResponse.java, v 1.0 2017年3月28日 下午2:27:35 hongsj Exp $
 */
public class QueryUserHavedPerMenuResponse implements Serializable {
    /**  */
    private static final long        serialVersionUID = 122185439920297338L;
    /**权限菜单列表*/
    private List<PermissionMenuPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<PermissionMenuPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<PermissionMenuPojo> pojoList) {
        this.pojoList = pojoList;
    }
}
