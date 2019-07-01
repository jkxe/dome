/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserCustomerRolePojo;

/**
 * 查询客户角色返回类
 * @author hongsj
 * @version $Id: QueryCustomerRoleResponse.java, v 1.0 2017年3月27日 下午2:02:24 hongsj Exp $
 */
public class QueryCustomerRoleResponse implements Serializable {
    /**  */
    private static final long          serialVersionUID = -7116511533433273996L;
    /** 客户角色列表 */
    private List<UserCustomerRolePojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserCustomerRolePojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserCustomerRolePojo> pojoList) {
        this.pojoList = pojoList;
    }

}
