/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserRegistrationSiteInfoPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryRegistrationSiteResponse.java, v 1.0 2017年4月28日 上午10:33:03 jiangjd12837 Exp $
 */
public class QueryRegistrationSiteResponse implements Serializable {

    /**  */
    private static final long                  serialVersionUID = 8865475991765875162L;
    //登记点列表
    private List<UserRegistrationSiteInfoPojo> userRegistrationSiteInfoPojoList;

    /**
     * Getter method for property <tt>userRegistrationSiteInfoPojoList</tt>.
     * 
     * @return property value of userRegistrationSiteInfoPojoList
     */
    public List<UserRegistrationSiteInfoPojo> getUserRegistrationSiteInfoPojoList() {
        return userRegistrationSiteInfoPojoList;
    }

    /**
     * Setter method for property <tt>userRegistrationSiteInfoPojoList</tt>.
     * 
     * @param userRegistrationSiteInfoPojoList value to be assigned to property userRegistrationSiteInfoPojoList
     */
    public void setUserRegistrationSiteInfoPojoList(List<UserRegistrationSiteInfoPojo> userRegistrationSiteInfoPojoList) {
        this.userRegistrationSiteInfoPojoList = userRegistrationSiteInfoPojoList;
    }

}
