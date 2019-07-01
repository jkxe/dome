/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author huangbb
 * @version $Id: QueryUserInfoByTelephonePojo.java, v 0.1 2018年1月18日 下午8:04:25 huangbb Exp $
 */
public class QueryUserInfoByTelephonePojo implements Serializable {

    private static final long serialVersionUID = 4382416795595419191L;

    private String telephone;
    
    private UserPersonalBasicInfoPojo basicInfo;

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
     * Getter method for property <tt>basicInfo</tt>.
     * 
     * @return property value of basicInfo
     */
    public UserPersonalBasicInfoPojo getBasicInfo() {
        return basicInfo;
    }

    /**
     * Setter method for property <tt>basicInfo</tt>.
     * 
     * @param basicInfo value to be assigned to property basicInfo
     */
    public void setBasicInfo(UserPersonalBasicInfoPojo basicInfo) {
        this.basicInfo = basicInfo;
    }
    
    
    
}
