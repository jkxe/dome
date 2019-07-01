/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryRegisterUserByRelationInfoRequest.java, v 1.0 2017年6月30日 下午4:47:35 jiangjd12837 Exp $
 */
public class QueryRegisterUserByRelationInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -1589615769284905046L;
    //姓名
    private String            clientName;
    //电话
    private String            telephone;

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

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
