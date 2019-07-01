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
 * 
 * @author jiangjd12837
 * @version $Id: QueryMainCardByUserIdListRequest.java, v 1.0 2017年7月24日 下午1:56:01 jiangjd12837 Exp $
 */
public class QueryMainCardByUserIdListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -6724522088244741455L;
    //客户ID列表
    @NotNull(errorCode = "000001", message = "客户ID列表")
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
