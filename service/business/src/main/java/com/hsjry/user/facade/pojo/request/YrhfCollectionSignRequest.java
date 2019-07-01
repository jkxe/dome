/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.YrhfCollectionSignPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfCollectionSignRequest.java, v 1.0 2017年5月15日 下午3:54:43 jiangjd12837 Exp $
 */
public class YrhfCollectionSignRequest implements Serializable {
    /**  */
    private static final long      serialVersionUID = -1708943266553067376L;
    /**
     * C 
     */
    private YrhfCollectionSignPojo yrhfCollectionSignPojo;

    /**
     * Getter method for property <tt>yrhfCollectionSignPojo</tt>.
     * 
     * @return property value of yrhfCollectionSignPojo
     */
    public YrhfCollectionSignPojo getYrhfCollectionSignPojo() {
        return yrhfCollectionSignPojo;
    }

    /**
     * Setter method for property <tt>yrhfCollectionSignPojo</tt>.
     * 
     * @param yrhfCollectionSignPojo value to be assigned to property yrhfCollectionSignPojo
     */
    public void setYrhfCollectionSignPojo(YrhfCollectionSignPojo yrhfCollectionSignPojo) {
        this.yrhfCollectionSignPojo = yrhfCollectionSignPojo;
    }

}
