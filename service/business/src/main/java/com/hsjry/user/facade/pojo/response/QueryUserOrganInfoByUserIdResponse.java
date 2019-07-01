/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.LegalInfoPojo;
import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserEmailContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserOrganBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserRealEstateResourcesPojo;
import com.hsjry.user.facade.pojo.UserSocialContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserTelContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserWebsiteContactStationInfoPojo;

/**
 * 企业客户注册请求类
 * @author hongsj
 * @version $Id: RegisterCompanyManagePlatformRequest.java, v 1.0 2017年4月11日 上午9:52:40 hongsj Exp $
 */
public class QueryUserOrganInfoByUserIdResponse implements Serializable {
    /**  */
    private static final long                       serialVersionUID = -5512461627838270597L;
    /**
     * 机构基本信息
     */
    private UserOrganBasicInfoPojo                  userOrganBasicInfoPojo;
    /**
     * 证件信息
     */
    private List<UserCertificateInfoPojo>           userCertificateInfoPojoList;
    /**
     * 地址联系点信息
     */
    private List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList;
    /**
     * 邮箱联系点信息
     */
    private List<UserEmailContactStationInfoPojo>   userEmailContactStationInfoPojoList;
    /**
     * 互联网社交联系点信息
     */
    private List<UserSocialContactStationInfoPojo>  userSocialContactStationInfoPojoList;
    /**
     * 电话联系点信息
     */
    private List<UserTelContactStationInfoPojo>     userTelContactStationInfoPojoList;
    /**
     * 网址联系点信息
     */
    private List<UserWebsiteContactStationInfoPojo> userWebsiteContactStationInfoPojoList;
    /**
     * 关系人客户id
     */
    private String                                  userId;
    /**
     * 经营信息
     */
    private UserManageInfoPojo                      userManageInfoPojo;
    /**
     * 不动产信息
     */
    private List<UserRealEstateResourcesPojo>       userRealEstateResourcesPojoList;
    /**
     * 法人信息
     */
    private LegalInfoPojo                           legalInfoPojo;

    /**
     * Getter method for property <tt>legalInfoPojo</tt>.
     * 
     * @return property value of legalInfoPojo
     */
    public LegalInfoPojo getLegalInfoPojo() {
        return legalInfoPojo;
    }

    /**
     * Setter method for property <tt>legalInfoPojo</tt>.
     * 
     * @param legalInfoPojo value to be assigned to property legalInfoPojo
     */
    public void setLegalInfoPojo(LegalInfoPojo legalInfoPojo) {
        this.legalInfoPojo = legalInfoPojo;
    }

    /**
     * Getter method for property <tt>userOrganBasicInfoPojo</tt>.
     * 
     * @return property value of userOrganBasicInfoPojo
     */
    public UserOrganBasicInfoPojo getUserOrganBasicInfoPojo() {
        return userOrganBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userOrganBasicInfoPojo</tt>.
     * 
     * @param userOrganBasicInfoPojo value to be assigned to property userOrganBasicInfoPojo
     */
    public void setUserOrganBasicInfoPojo(UserOrganBasicInfoPojo userOrganBasicInfoPojo) {
        this.userOrganBasicInfoPojo = userOrganBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>userCertificateInfoPojoList</tt>.
     * 
     * @return property value of userCertificateInfoPojoList
     */
    public List<UserCertificateInfoPojo> getUserCertificateInfoPojoList() {
        return userCertificateInfoPojoList;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojoList</tt>.
     * 
     * @param userCertificateInfoPojoList value to be assigned to property userCertificateInfoPojoList
     */
    public void setUserCertificateInfoPojoList(List<UserCertificateInfoPojo> userCertificateInfoPojoList) {
        this.userCertificateInfoPojoList = userCertificateInfoPojoList;
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

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userManageInfoPojo</tt>.
     * 
     * @return property value of userManageInfoPojo
     */
    public UserManageInfoPojo getUserManageInfoPojo() {
        return userManageInfoPojo;
    }

    /**
     * Setter method for property <tt>userManageInfoPojo</tt>.
     * 
     * @param userManageInfoPojo value to be assigned to property userManageInfoPojo
     */
    public void setUserManageInfoPojo(UserManageInfoPojo userManageInfoPojo) {
        this.userManageInfoPojo = userManageInfoPojo;
    }

    /**
     * Getter method for property <tt>userRealEstateResourcesPojoList</tt>.
     * 
     * @return property value of userRealEstateResourcesPojoList
     */
    public List<UserRealEstateResourcesPojo> getUserRealEstateResourcesPojoList() {
        return userRealEstateResourcesPojoList;
    }

    /**
     * Setter method for property <tt>userRealEstateResourcesPojoList</tt>.
     * 
     * @param userRealEstateResourcesPojoList value to be assigned to property userRealEstateResourcesPojoList
     */
    public void setUserRealEstateResourcesPojoList(List<UserRealEstateResourcesPojo> userRealEstateResourcesPojoList) {
        this.userRealEstateResourcesPojoList = userRealEstateResourcesPojoList;
    }

}
