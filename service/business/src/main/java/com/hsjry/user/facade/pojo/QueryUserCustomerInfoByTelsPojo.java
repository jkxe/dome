/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserCustomerInfoByTelsPojo.java, v 1.0 2017年6月30日 下午2:33:02 jiangjd12837 Exp $
 */
public class QueryUserCustomerInfoByTelsPojo implements Serializable {

    /**  */
    private static final long    serialVersionUID = -7244745187336959468L;
    //手机号
    private String               telephone;
    //客户信息
    private UserCustomerInfoPojo userCustomerInfoPojo;

    /**
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>userCustomerInfoPojo</tt>.
     * 
     * @return property value of userCustomerInfoPojo
     */
    public UserCustomerInfoPojo getUserCustomerInfoPojo() {
        return userCustomerInfoPojo;
    }

    /**
     * Setter method for property <tt>userCustomerInfoPojo</tt>.
     * 
     * @param userCustomerInfoPojo value to be assigned to property userCustomerInfoPojo
     */
    public void setUserCustomerInfoPojo(UserCustomerInfoPojo userCustomerInfoPojo) {
        this.userCustomerInfoPojo = userCustomerInfoPojo;
    }

}
