/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.YrhfQuerySignStatusPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfQuerySignStatusRequest.java, v 1.0 2017年5月15日 下午4:18:25 jiangjd12837 Exp $
 */
public class YrhfQuerySignStatusRequest implements Serializable {
    /**  */
    private static final long       serialVersionUID = -3251524168503501341L;
    /**
     * C 客户姓名
     */
    private YrhfQuerySignStatusPojo yrhfQuerySignStatusPojo;

    /**
     * Getter method for property <tt>yrhfQuerySignStatusPojo</tt>.
     * 
     * @return property value of yrhfQuerySignStatusPojo
     */
    public YrhfQuerySignStatusPojo getYrhfQuerySignStatusPojo() {
        return yrhfQuerySignStatusPojo;
    }

    /**
     * Setter method for property <tt>yrhfQuerySignStatusPojo</tt>.
     * 
     * @param yrhfQuerySignStatusPojo value to be assigned to property yrhfQuerySignStatusPojo
     */
    public void setYrhfQuerySignStatusPojo(YrhfQuerySignStatusPojo yrhfQuerySignStatusPojo) {
        this.yrhfQuerySignStatusPojo = yrhfQuerySignStatusPojo;
    }

}
