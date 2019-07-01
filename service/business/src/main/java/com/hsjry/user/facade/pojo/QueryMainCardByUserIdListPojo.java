/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMainCardByUserIdListResponse.java, v 1.0 2017年7月24日 下午2:16:32 jiangjd12837 Exp $
 */
public class QueryMainCardByUserIdListPojo implements Serializable {

    /**  */
    private static final long            serialVersionUID = 2058842432239373502L;
    //客户ID
    private String                       userId;
    //主卡信息
    private UserFinancialInstrumentsPojo userFinancialInstrumentsPojo;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userFinancialInstrumentsPojo</tt>.
     * 
     * @return property value of userFinancialInstrumentsPojo
     */
    public UserFinancialInstrumentsPojo getUserFinancialInstrumentsPojo() {
        return userFinancialInstrumentsPojo;
    }

    /**
     * Setter method for property <tt>userFinancialInstrumentsPojo</tt>.
     * 
     * @param userFinancialInstrumentsPojo value to be assigned to property userFinancialInstrumentsPojo
     */
    public void setUserFinancialInstrumentsPojo(UserFinancialInstrumentsPojo userFinancialInstrumentsPojo) {
        this.userFinancialInstrumentsPojo = userFinancialInstrumentsPojo;
    }

}
