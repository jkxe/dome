/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserBasicPojo;

/**
 * 根据菜单获取用户列表返回类
 * @author hongsj
 * @version $Id: QueryUserByMenuResponse.java, v 1.0 2017年3月30日 上午10:12:03 hongsj Exp $
 */
public class QueryUserByMenuResponse implements Serializable {
    /**  */
    private static final long   serialVersionUID = -4157892231700710075L;
    /**用户基本信息*/
    private List<UserBasicPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<UserBasicPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<UserBasicPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
