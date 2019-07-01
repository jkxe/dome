/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManageImportantInfoResponse.java, v 1.0 2017年9月6日 上午11:18:40 jiangjd12837 Exp $
 */
public class QueryManageImportantInfoResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -7843315850116319551L;
    /**登录账号*/
    private String            identifiers;

    /**
     * Getter method for property <tt>identifiers</tt>.
     * 
     * @return property value of identifiers
     */
    public String getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter method for property <tt>identifiers</tt>.
     * 
     * @param identifiers value to be assigned to property identifiers
     */
    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }
}
