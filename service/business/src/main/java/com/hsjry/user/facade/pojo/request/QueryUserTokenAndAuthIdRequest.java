/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserTokenAndAuthIdRequest.java, v 1.0 2017年8月24日 下午2:31:23 jiangjd12837 Exp $
 */
public class QueryUserTokenAndAuthIdRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -1005175175959831464L;
    //手机号
    private String            telephone;

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

}
