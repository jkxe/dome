/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAddressMaintainPojo;
import com.hsjry.user.facade.pojo.UserEmailMaintainPojo;
import com.hsjry.user.facade.pojo.UserSocialMaintainPojo;
import com.hsjry.user.facade.pojo.UserTelMaintainPojo;
import com.hsjry.user.facade.pojo.UserWebsiteMaintainPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: MaintainContactStationRequest.java, v 1.0 2017年7月3日 下午4:17:04 jiangjd12837 Exp $
 */
public class MaintainContactStationRequest implements Serializable {

    /**  */
    private static final long             serialVersionUID = -889499131725221495L;
    //地址联系点
    private List<UserAddressMaintainPojo> userAddressMaintainPojoList;
    //邮箱联系点
    private List<UserEmailMaintainPojo>   userEmailMaintainPojoList;
    //互联网社交联系点
    private List<UserSocialMaintainPojo>  userSocialMaintainPojoList;
    //电话联系点
    private List<UserTelMaintainPojo>     userTelMaintainPojoList;
    //网址联系点
    private List<UserWebsiteMaintainPojo> userWebsiteMaintainPojoList;

    /**
     * Getter method for property <tt>userAddressMaintainPojoList</tt>.
     * 
     * @return property value of userAddressMaintainPojoList
     */
    public List<UserAddressMaintainPojo> getUserAddressMaintainPojoList() {
        return userAddressMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userAddressMaintainPojoList</tt>.
     * 
     * @param userAddressMaintainPojoList value to be assigned to property userAddressMaintainPojoList
     */
    public void setUserAddressMaintainPojoList(List<UserAddressMaintainPojo> userAddressMaintainPojoList) {
        this.userAddressMaintainPojoList = userAddressMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userEmailMaintainPojoList</tt>.
     * 
     * @return property value of userEmailMaintainPojoList
     */
    public List<UserEmailMaintainPojo> getUserEmailMaintainPojoList() {
        return userEmailMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userEmailMaintainPojoList</tt>.
     * 
     * @param userEmailMaintainPojoList value to be assigned to property userEmailMaintainPojoList
     */
    public void setUserEmailMaintainPojoList(List<UserEmailMaintainPojo> userEmailMaintainPojoList) {
        this.userEmailMaintainPojoList = userEmailMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userSocialMaintainPojoList</tt>.
     * 
     * @return property value of userSocialMaintainPojoList
     */
    public List<UserSocialMaintainPojo> getUserSocialMaintainPojoList() {
        return userSocialMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userSocialMaintainPojoList</tt>.
     * 
     * @param userSocialMaintainPojoList value to be assigned to property userSocialMaintainPojoList
     */
    public void setUserSocialMaintainPojoList(List<UserSocialMaintainPojo> userSocialMaintainPojoList) {
        this.userSocialMaintainPojoList = userSocialMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userTelMaintainPojoList</tt>.
     * 
     * @return property value of userTelMaintainPojoList
     */
    public List<UserTelMaintainPojo> getUserTelMaintainPojoList() {
        return userTelMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userTelMaintainPojoList</tt>.
     * 
     * @param userTelMaintainPojoList value to be assigned to property userTelMaintainPojoList
     */
    public void setUserTelMaintainPojoList(List<UserTelMaintainPojo> userTelMaintainPojoList) {
        this.userTelMaintainPojoList = userTelMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userWebsiteMaintainPojoList</tt>.
     * 
     * @return property value of userWebsiteMaintainPojoList
     */
    public List<UserWebsiteMaintainPojo> getUserWebsiteMaintainPojoList() {
        return userWebsiteMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userWebsiteMaintainPojoList</tt>.
     * 
     * @param userWebsiteMaintainPojoList value to be assigned to property userWebsiteMaintainPojoList
     */
    public void setUserWebsiteMaintainPojoList(List<UserWebsiteMaintainPojo> userWebsiteMaintainPojoList) {
        this.userWebsiteMaintainPojoList = userWebsiteMaintainPojoList;
    }

}
