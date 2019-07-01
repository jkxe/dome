/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.YrhfAccountVerifyPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfCertVerifyRequest.java, v 1.0 2017年5月15日 下午3:19:39 jiangjd12837 Exp $
 */
public class YrhfAccountVerifyRequest implements Serializable {
    /**  */
    private static final long     serialVersionUID = -840061434068406125L;
    /**
     * 
     */
    private YrhfAccountVerifyPojo yrhfAccountVerifyPojo;

    /**
     * Getter method for property <tt>yrhfAccountVerifyPojo</tt>.
     * 
     * @return property value of yrhfAccountVerifyPojo
     */
    public YrhfAccountVerifyPojo getYrhfAccountVerifyPojo() {
        return yrhfAccountVerifyPojo;
    }

    /**
     * Setter method for property <tt>yrhfAccountVerifyPojo</tt>.
     * 
     * @param yrhfAccountVerifyPojo value to be assigned to property yrhfAccountVerifyPojo
     */
    public void setYrhfAccountVerifyPojo(YrhfAccountVerifyPojo yrhfAccountVerifyPojo) {
        this.yrhfAccountVerifyPojo = yrhfAccountVerifyPojo;
    }

}
