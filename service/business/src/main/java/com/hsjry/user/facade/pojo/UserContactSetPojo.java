/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 联系点集合
 * @author huangbb
 * @version $Id: UserContactSetPojo.java, v 1.0 2017年5月25日 下午8:59:25 huangbb Exp $
 */
public class UserContactSetPojo implements Serializable {

    private static final long serialVersionUID = 5855370064580043704L;

    //通行证ID
    private String authId;
    //地址联系点
    private List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList;
    //邮箱联系点
    private List<UserEmailContactStationInfoPojo>   userEmailContactStationInfoPojoList;
    //互联网社交联系点
    private List<UserSocialContactStationInfoPojo>  userSocialContactStationInfoPojoList;
    //电话联系点
    private List<UserTelContactStationInfoPojo>     userTelContactStationInfoPojoList;
    //网址联系点
    private List<UserWebsiteContactStationInfoPojo> userWebsiteContactStationInfoPojoList;
   
    
    
    /**
     * Getter method for property <tt>authId</tt>.
     * 
     * @return property value of authId
     */
    public String getAuthId() {
        return authId;
    }
    /**
     * Setter method for property <tt>authId</tt>.
     * 
     * @param authId value to be assigned to property authId
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }
    /**
     * Getter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojoList
     */
    public List<UserAddressContactStationInfoPojo> getUserAddressContactStationInfoPojoList() {
        return userAddressContactStationInfoPojoList;
    }
    /**
     * Setter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @param userAddressContactStationInfoPojoList value to be assigned to property userAddressContactStationInfoPojoList
     */
    public void setUserAddressContactStationInfoPojoList(List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList) {
        this.userAddressContactStationInfoPojoList = userAddressContactStationInfoPojoList;
    }
    /**
     * Getter method for property <tt>userEmailContactStationInfoPojoList</tt>.
     * 
     * @return property value of userEmailContactStationInfoPojoList
     */
    public List<UserEmailContactStationInfoPojo> getUserEmailContactStationInfoPojoList() {
        return userEmailContactStationInfoPojoList;
    }
    /**
     * Setter method for property <tt>userEmailContactStationInfoPojoList</tt>.
     * 
     * @param userEmailContactStationInfoPojoList value to be assigned to property userEmailContactStationInfoPojoList
     */
    public void setUserEmailContactStationInfoPojoList(List<UserEmailContactStationInfoPojo> userEmailContactStationInfoPojoList) {
        this.userEmailContactStationInfoPojoList = userEmailContactStationInfoPojoList;
    }
    /**
     * Getter method for property <tt>userSocialContactStationInfoPojoList</tt>.
     * 
     * @return property value of userSocialContactStationInfoPojoList
     */
    public List<UserSocialContactStationInfoPojo> getUserSocialContactStationInfoPojoList() {
        return userSocialContactStationInfoPojoList;
    }
    /**
     * Setter method for property <tt>userSocialContactStationInfoPojoList</tt>.
     * 
     * @param userSocialContactStationInfoPojoList value to be assigned to property userSocialContactStationInfoPojoList
     */
    public void setUserSocialContactStationInfoPojoList(List<UserSocialContactStationInfoPojo> userSocialContactStationInfoPojoList) {
        this.userSocialContactStationInfoPojoList = userSocialContactStationInfoPojoList;
    }
    /**
     * Getter method for property <tt>userTelContactStationInfoPojoList</tt>.
     * 
     * @return property value of userTelContactStationInfoPojoList
     */
    public List<UserTelContactStationInfoPojo> getUserTelContactStationInfoPojoList() {
        return userTelContactStationInfoPojoList;
    }
    /**
     * Setter method for property <tt>userTelContactStationInfoPojoList</tt>.
     * 
     * @param userTelContactStationInfoPojoList value to be assigned to property userTelContactStationInfoPojoList
     */
    public void setUserTelContactStationInfoPojoList(List<UserTelContactStationInfoPojo> userTelContactStationInfoPojoList) {
        this.userTelContactStationInfoPojoList = userTelContactStationInfoPojoList;
    }
    /**
     * Getter method for property <tt>userWebsiteContactStationInfoPojoList</tt>.
     * 
     * @return property value of userWebsiteContactStationInfoPojoList
     */
    public List<UserWebsiteContactStationInfoPojo> getUserWebsiteContactStationInfoPojoList() {
        return userWebsiteContactStationInfoPojoList;
    }
    /**
     * Setter method for property <tt>userWebsiteContactStationInfoPojoList</tt>.
     * 
     * @param userWebsiteContactStationInfoPojoList value to be assigned to property userWebsiteContactStationInfoPojoList
     */
    public void setUserWebsiteContactStationInfoPojoList(List<UserWebsiteContactStationInfoPojo> userWebsiteContactStationInfoPojoList) {
        this.userWebsiteContactStationInfoPojoList = userWebsiteContactStationInfoPojoList;
    }
    
    
}
