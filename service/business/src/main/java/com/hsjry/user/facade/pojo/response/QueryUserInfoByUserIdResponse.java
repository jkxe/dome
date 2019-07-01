/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserInfoByuserIdlistPojo;
import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserInfoByUserIdResponse.java, v 1.0 2017年7月18日 上午9:30:15 jiangjd12837 Exp $
 */
public class QueryUserInfoByUserIdResponse implements Serializable {

    /**  */
    private static final long                  serialVersionUID = 2428241226871619221L;
    private QueryUserInfoByuserIdlistPojo      queryUserInfoByuserIdlistPojo;
    //金融工具
    private List<UserFinancialInstrumentsPojo> userFinancialInstrumentsPojoList;

    /**
     * Getter method for property <tt>queryUserInfoByuserIdlistPojo</tt>.
     * 
     * @return property value of queryUserInfoByuserIdlistPojo
     */
    public QueryUserInfoByuserIdlistPojo getQueryUserInfoByuserIdlistPojo() {
        return queryUserInfoByuserIdlistPojo;
    }

    /**
     * Setter method for property <tt>queryUserInfoByuserIdlistPojo</tt>.
     * 
     * @param queryUserInfoByuserIdlistPojo value to be assigned to property queryUserInfoByuserIdlistPojo
     */
    public void setQueryUserInfoByuserIdlistPojo(QueryUserInfoByuserIdlistPojo queryUserInfoByuserIdlistPojo) {
        this.queryUserInfoByuserIdlistPojo = queryUserInfoByuserIdlistPojo;
    }

    /**
     * Getter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @return property value of userFinancialInstrumentsPojoList
     */
    public List<UserFinancialInstrumentsPojo> getUserFinancialInstrumentsPojoList() {
        return userFinancialInstrumentsPojoList;
    }

    /**
     * Setter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @param userFinancialInstrumentsPojoList value to be assigned to property userFinancialInstrumentsPojoList
     */
    public void setUserFinancialInstrumentsPojoList(List<UserFinancialInstrumentsPojo> userFinancialInstrumentsPojoList) {
        this.userFinancialInstrumentsPojoList = userFinancialInstrumentsPojoList;
    }
}
