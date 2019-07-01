/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMainCardByUserIdListResponse.java, v 1.0 2017年7月24日 下午2:16:32 jiangjd12837 Exp $
 */
public class QueryPartnerInfoAndFinancialListPojo implements Serializable {

    /**  */
    private static final long                  serialVersionUID = 644943843543864968L;
    //客户ID
    private String                             userId;
    //主卡信息
    private List<UserFinancialInstrumentsPojo> userFinancialInstrumentsPojoList;

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
