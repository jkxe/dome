/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserCustomerInfoPojo;
import com.hsjry.user.facade.pojo.UserCustomerRelationPojo;
import com.hsjry.user.facade.pojo.UserEducationInfoPojo;
import com.hsjry.user.facade.pojo.UserEmailContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserPersonalBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserProfessionalInfoPojo;
import com.hsjry.user.facade.pojo.UserRealEstateResourcesPojo;
import com.hsjry.user.facade.pojo.UserSocialContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserTelContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserWebsiteContactStationInfoPojo;
import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserCustomerInfoByUserIdResponse.java, v 1.0 2017年4月10日 下午2:05:04 jiangjd12837 Exp $
 */
public class QueryUserCustomerInfoByUserIdResponse implements Serializable {

    /**  */
    private static final long                       serialVersionUID = 3977899883121983039L;
    //客户信息
    private UserCustomerInfoPojo                    userCustomerInfoPojo;
    //客户基本信息
    private UserPersonalBasicInfoPojo               userPersonalBasicInfoPojo;
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
    //客户关系信息列表
    private List<UserCustomerRelationPojo>          userCustomeRelationPojoList;
    //职业信息
    private List<UserProfessionalInfoPojo>          userProfessionalInfoPojoList;
    //证件文档
    private List<UserCertificateInfoPojo>           userCertificatePojoList;
    //不动产资源
    private List<UserRealEstateResourcesPojo>       userRealEstateResourcesPojoList;
    //教育信息
    private UserEducationInfoPojo                   userEducationInfoPojo;
    //营销线索
    private EnumMarketingCues                       marketingCues;
    /**渠道名称 营销线索为其他渠道 才有值*/
    private String                                  channelName;
    /**渠道编号 营销线索为其他渠道 才有值*/
    private String                                  channelNo;

    /**
     * Getter method for property <tt>channelNo</tt>.
     * 
     * @return property value of channelNo
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * Setter method for property <tt>channelNo</tt>.
     * 
     * @param channelNo value to be assigned to property channelNo
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    /**
     * Getter method for property <tt>channelName</tt>.
     * 
     * @return property value of channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Setter method for property <tt>channelName</tt>.
     * 
     * @param channelName value to be assigned to property channelName
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Getter method for property <tt>marketingCues</tt>.
     * 
     * @return property value of marketingCues
     */
    public EnumMarketingCues getMarketingCues() {
        return marketingCues;
    }

    /**
     * Setter method for property <tt>marketingCues</tt>.
     * 
     * @param marketingCues value to be assigned to property marketingCues
     */
    public void setMarketingCues(EnumMarketingCues marketingCues) {
        this.marketingCues = marketingCues;
    }

    /**
     * Getter method for property <tt>userEducationInfoPojo</tt>.
     * 
     * @return property value of userEducationInfoPojo
     */
    public UserEducationInfoPojo getUserEducationInfoPojo() {
        return userEducationInfoPojo;
    }

    /**
     * Setter method for property <tt>userEducationInfoPojo</tt>.
     * 
     * @param userEducationInfoPojo value to be assigned to property userEducationInfoPojo
     */
    public void setUserEducationInfoPojo(UserEducationInfoPojo userEducationInfoPojo) {
        this.userEducationInfoPojo = userEducationInfoPojo;
    }

    /**
     * Getter method for property <tt>userCustomeRelationPojoList</tt>.
     * 
     * @return property value of userCustomeRelationPojoList
     */
    public List<UserCustomerRelationPojo> getUserCustomeRelationPojoList() {
        return userCustomeRelationPojoList;
    }

    /**
     * Setter method for property <tt>userCustomeRelationPojoList</tt>.
     * 
     * @param userCustomeRelationPojoList value to be assigned to property userCustomeRelationPojoList
     */
    public void setUserCustomeRelationPojoList(List<UserCustomerRelationPojo> userCustomeRelationPojoList) {
        this.userCustomeRelationPojoList = userCustomeRelationPojoList;
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

    /**
     * Getter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @return property value of userPersonalBasicInfoPojo
     */
    public UserPersonalBasicInfoPojo getUserPersonalBasicInfoPojo() {
        return userPersonalBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @param userPersonalBasicInfoPojo value to be assigned to property userPersonalBasicInfoPojo
     */
    public void setUserPersonalBasicInfoPojo(UserPersonalBasicInfoPojo userPersonalBasicInfoPojo) {
        this.userPersonalBasicInfoPojo = userPersonalBasicInfoPojo;
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
     * Getter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @return property value of userProfessionalInfoPojoList
     */
    public List<UserProfessionalInfoPojo> getUserProfessionalInfoPojoList() {
        return userProfessionalInfoPojoList;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @param userProfessionalInfoPojoList value to be assigned to property userProfessionalInfoPojoList
     */
    public void setUserProfessionalInfoPojoList(List<UserProfessionalInfoPojo> userProfessionalInfoPojoList) {
        this.userProfessionalInfoPojoList = userProfessionalInfoPojoList;
    }

    /**
     * Getter method for property <tt>userCertificatePojoList</tt>.
     * 
     * @return property value of userCertificatePojoList
     */
    public List<UserCertificateInfoPojo> getUserCertificatePojoList() {
        return userCertificatePojoList;
    }

    /**
     * Setter method for property <tt>userCertificatePojoList</tt>.
     * 
     * @param userCertificatePojoList value to be assigned to property userCertificatePojoList
     */
    public void setUserCertificatePojoList(List<UserCertificateInfoPojo> userCertificatePojoList) {
        this.userCertificatePojoList = userCertificatePojoList;
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
