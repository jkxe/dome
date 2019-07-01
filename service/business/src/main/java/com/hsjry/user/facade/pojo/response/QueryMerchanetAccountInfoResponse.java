/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;

/**
 * 商户账户信息查询
 * @author huangbb
 * @version $Id: QueryMerchanetAccountInfoResponse.java, v 1.0 2018年5月15日 下午4:45:55 huangbb Exp $
 */
public class QueryMerchanetAccountInfoResponse implements Serializable {

    private static final long            serialVersionUID = -1041007678295889262L;

    /**账户信息*/
    private UserFinancialInstrumentsPojo financialInstrumentsPojo;


    /**
     * Getter method for property <tt>financialInstrumentsPojo</tt>.
     * 
     * @return property value of financialInstrumentsPojo
     */
    public UserFinancialInstrumentsPojo getFinancialInstrumentsPojo() {
        return financialInstrumentsPojo;
    }

    /**
     * Setter method for property <tt>financialInstrumentsPojo</tt>.
     * 
     * @param financialInstrumentsPojo value to be assigned to property financialInstrumentsPojo
     */
    public void setFinancialInstrumentsPojo(UserFinancialInstrumentsPojo financialInstrumentsPojo) {
        this.financialInstrumentsPojo = financialInstrumentsPojo;
    }
    
    
}
