/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

/**
 * 客户ID列表查询客户信息
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserInfoRequest.java, v 1.0 2017年3月20日 下午12:11:43 jiangjd12837 Exp $
 */
public class QueryUserInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 2836704350036046330L;
    @NotNull(errorCode = "000001", message = "客户Id")
    //客户ID列表
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "客户ID列表")
    private List<String>      userIdList;

    /**
     * Getter method for property <tt>userIdList</tt>.
     * 
     * @return property value of userIdList
     */
    public List<String> getUserIdList() {
        return userIdList;
    }

    /**
     * Setter method for property <tt>userIdList</tt>.
     * 
     * @param userIdList value to be assigned to property userIdList
     */
    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

}
