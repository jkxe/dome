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
 * @version $Id: QueryUserCustomerInfoByTelsRequest.java, v 1.0 2017年6月30日 下午2:26:15 jiangjd12837 Exp $
 */
public class QueryUserCustomerInfoByTelsRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 461774485377481385L;
    // 手机号列表
    @NotNull(errorCode = "000001", message = "手机号列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "手机号列表")
    private List<String>      telephoneList;

    /**
     * Getter method for property <tt>telephoneList</tt>.
     * 
     * @return property value of telephoneList
     */
    public List<String> getTelephoneList() {
        return telephoneList;
    }

    /**
     * Setter method for property <tt>telephoneList</tt>.
     * 
     * @param telephoneList value to be assigned to property telephoneList
     */
    public void setTelephoneList(List<String> telephoneList) {
        this.telephoneList = telephoneList;
    }

}
