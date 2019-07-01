/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserInfoAndCompanyNamePojo;

/**
 * 
 * @author lilin22830
 * @version $Id: QueryUserInfoAndUnitNameResponse.java, v 0.1 Aug 21, 2017 9:07:01 AM lilin22830 Exp $
 */
public class QueryUserInfoAndCompanyNameResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -3207972889061710680L;
    
    private List<UserInfoAndCompanyNamePojo> userInfoAndUnitNamePojolist;

    /**
     * Getter method for property <tt>userInfoAndUnitNamePojolist</tt>.
     * 
     * @return property value of userInfoAndUnitNamePojolist
     */
    public List<UserInfoAndCompanyNamePojo> getUserInfoAndUnitNamePojolist() {
        return userInfoAndUnitNamePojolist;
    }

    /**
     * Setter method for property <tt>userInfoAndUnitNamePojolist</tt>.
     * 
     * @param userInfoAndUnitNamePojolist value to be assigned to property userInfoAndUnitNamePojolist
     */
    public void setUserInfoAndUnitNamePojolist(List<UserInfoAndCompanyNamePojo> userInfoAndUnitNamePojolist) {
        this.userInfoAndUnitNamePojolist = userInfoAndUnitNamePojolist;
    }

   
    

}
