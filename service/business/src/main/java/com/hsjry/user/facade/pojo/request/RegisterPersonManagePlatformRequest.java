/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.*;
import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

import java.io.Serializable;
import java.util.List;

/**
 * 管理台个人客户注册请求类
 *
 * @author hongsj
 * @version $Id: RegisterPersonManagePlatformRequest.java, v 1.0 2017年4月9日 上午9:50:25 hongsj Exp $
 */
public class RegisterPersonManagePlatformRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -7219534883351333905L;
    /**
     * 个人客户基本信息
     */
    private UserPersonalBasicInfoPojo userPersonalBasicInfoPojo;
    /**
     * 证件信息
     */
    private List<UserCertificateInfoPojo> userCertificateInfoPojoList;
    /**
     * 地址联系点信息
     */
    private List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList;
    /**
     * 邮箱联系点信息
     */
    private List<UserEmailContactStationInfoPojo> userEmailContactStationInfoPojoList;
    /**
     * 互联网社交联系点信息
     */
    private List<UserSocialContactStationInfoPojo> userSocialContactStationInfoPojoList;
    /**
     * 电话联系点信息
     */
    private List<UserTelContactStationInfoPojo> userTelContactStationInfoPojoList;
    /**
     * 网址联系点信息
     */
    private List<UserWebsiteContactStationInfoPojo> userWebsiteContactStationInfoPojoList;
    /**
     * 配偶信息
     */
    private UserCustomeRelationInfoModifyPojo userCustomeRelationPojo;
    /**
     * 职业信息
     */
    private List<UserProfessionalInfoPojo> userProfessionalInfoPojoList;
    /**
     * 不动产信息
     */
    private List<UserRealEstateResourcesPojo> userRealEstateResourcesPojoList;
    //教育信息
    private UserEducationInfoPojo userEducationInfoPojo;
    //营销线索
    private EnumMarketingCues marketingCues;
    //推荐人客户ID
    private String recommenderUserId;

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
     * Getter method for property <tt>userCustomeRelationPojo</tt>.
     *
     * @return property value of userCustomeRelationPojo
     */
    public UserCustomeRelationInfoModifyPojo getUserCustomeRelationPojo() {
        return userCustomeRelationPojo;
    }

    /**
     * Setter method for property <tt>userCustomeRelationPojo</tt>.
     *
     * @param userCustomeRelationPojo value to be assigned to property userCustomeRelationPojo
     */
    public void setUserCustomeRelationPojo(UserCustomeRelationInfoModifyPojo userCustomeRelationPojo) {
        this.userCustomeRelationPojo = userCustomeRelationPojo;
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

    /**
     * Getter method for property <tt>recommenderUserId</tt>.
     *
     * @return property value of recommenderUserId
     */
    public String getRecommenderUserId() {
        return recommenderUserId;
    }

    /**
     * Setter method for property <tt>recommenderUserId</tt>.
     *
     * @param recommenderUserId value to be assigned to property recommenderUserId
     */
    public void setRecommenderUserId(String recommenderUserId) {
        this.recommenderUserId = recommenderUserId;
    }

}
