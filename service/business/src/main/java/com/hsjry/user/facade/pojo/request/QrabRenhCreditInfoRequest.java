/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QrabRenhCreditInfoRequest.java, v 1.0 2017年8月9日 下午6:49:40 jiangjd12837 Exp $
 */
public class QrabRenhCreditInfoRequest implements Serializable {

    private static final long serialVersionUID = -2342886310537916625L;
    //业务日期
    private Date               businessDate;
    /**
     * Getter method for property <tt>businessDate</tt>.
     * 
     * @return property value of businessDate
     */
    public Date getBusinessDate() {
        return businessDate;
    }
    /**
     * Setter method for property <tt>businessDate</tt>.
     * 
     * @param businessDate value to be assigned to property businessDate
     */
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    

}
