/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserInfoByTelephonePojo;

/**
 * 注册手机号查询用户信息返回
 * @author huangbb
 * @version $Id: QueryUserInfoByTelephoneResponse.java, v 0.1 2018年1月18日 下午8:03:14 huangbb Exp $
 */
public class QueryUserInfoByTelephoneResponse implements Serializable {

    private static final long serialVersionUID = 2412498314681184224L;

    private List<QueryUserInfoByTelephonePojo> list;

    /**
     * Getter method for property <tt>list</tt>.
     * 
     * @return property value of list
     */
    public List<QueryUserInfoByTelephonePojo> getList() {
        return list;
    }

    /**
     * Setter method for property <tt>list</tt>.
     * 
     * @param list value to be assigned to property list
     */
    public void setList(List<QueryUserInfoByTelephonePojo> list) {
        this.list = list;
    }
    
    
}
