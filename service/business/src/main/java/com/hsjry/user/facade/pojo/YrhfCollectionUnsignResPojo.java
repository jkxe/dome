/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumTransactionStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfQuerySignStatusResponse.java, v 1.0 2017年5月15日 下午4:20:00 jiangjd12837 Exp $
 */
public class YrhfCollectionUnsignResPojo implements Serializable {
    /**  */
    private static final long     serialVersionUID = -5650774578867517477L;
    /**
     * C 状态
     */
    private EnumTransactionStatus status;

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public EnumTransactionStatus getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(EnumTransactionStatus status) {
        this.status = status;
    }

}
