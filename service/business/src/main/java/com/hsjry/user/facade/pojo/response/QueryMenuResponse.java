/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserMenuPojo;

/**
 * 菜单列表返回类
 * @author hongsj
 * @version $Id: QueryMenuResponse.java, v 1.0 2017年3月28日 下午3:00:02 hongsj Exp $
 */
public class QueryMenuResponse implements Serializable {
    /**  */
    private static final long  serialVersionUID = -5557449783395544898L;
    /**菜单列表*/
    private List<UserMenuPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserMenuPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserMenuPojo> pojoList) {
        this.pojoList = pojoList;
    }
}
