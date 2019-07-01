/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.SyncCustomerInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: SyncCustomerInfoRequest.java, v 1.0 2017年11月27日 下午4:30:13 zhengqy15963 Exp $
 */
public class SyncCustomerInfoRequest implements Serializable {

    /**  */
    private static final long    serialVersionUID = 3876474222522808922L;

    private SyncCustomerInfoPojo syncCustomerInfoPojo;

    /**
     * Getter method for property <tt>syncCustomerInfoPojo</tt>.
     * 
     * @return property value of syncCustomerInfoPojo
     */
    public SyncCustomerInfoPojo getSyncCustomerInfoPojo() {
        return syncCustomerInfoPojo;
    }

    /**
     * Setter method for property <tt>syncCustomerInfoPojo</tt>.
     * 
     * @param syncCustomerInfoPojo value to be assigned to property syncCustomerInfoPojo
     */
    public void setSyncCustomerInfoPojo(SyncCustomerInfoPojo syncCustomerInfoPojo) {
        this.syncCustomerInfoPojo = syncCustomerInfoPojo;
    }

    /**
     * 
     */
    public SyncCustomerInfoRequest() {
    }

}
