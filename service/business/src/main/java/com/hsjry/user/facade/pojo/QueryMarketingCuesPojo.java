/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMarketingCuesPojo.java, v 1.0 2017年4月5日 上午10:03:02 jiangjd12837 Exp $
 */
public class QueryMarketingCuesPojo implements Serializable {

    /**  */
    private static final long         serialVersionUID = 357222287527213716L;
    //会员
    private UserLeaguerInfoPojo       userLeaguerInfoPojo;
    //客户信息列表
    private UserCustomerInfoPojo      userCustomerInfoPojoPojo;
    //证件信息
    private UserCertificateInfoPojo   userCertificateInfoPojo;
    //个人客户基本信息表
    private UserPersonalBasicInfoPojo userPersonalBasicInfoPojo;
    //推荐人姓名
    private String                    recommenderName;
    //手机号码
    private String                    telephone;

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
     * Getter method for property <tt>userLeaguerInfoPojo</tt>.
     * 
     * @return property value of userLeaguerInfoPojo
     */
    public UserLeaguerInfoPojo getUserLeaguerInfoPojo() {
        return userLeaguerInfoPojo;
    }

    /**
     * Setter method for property <tt>userLeaguerInfoPojo</tt>.
     * 
     * @param userLeaguerInfoPojo value to be assigned to property userLeaguerInfoPojo
     */
    public void setUserLeaguerInfoPojo(UserLeaguerInfoPojo userLeaguerInfoPojo) {
        this.userLeaguerInfoPojo = userLeaguerInfoPojo;
    }

    /**
     * Getter method for property <tt>userCustomerInfoPojoPojo</tt>.
     * 
     * @return property value of userCustomerInfoPojoPojo
     */
    public UserCustomerInfoPojo getUserCustomerInfoPojoPojo() {
        return userCustomerInfoPojoPojo;
    }

    /**
     * Setter method for property <tt>userCustomerInfoPojoPojo</tt>.
     * 
     * @param userCustomerInfoPojoPojo value to be assigned to property userCustomerInfoPojoPojo
     */
    public void setUserCustomerInfoPojoPojo(UserCustomerInfoPojo userCustomerInfoPojoPojo) {
        this.userCustomerInfoPojoPojo = userCustomerInfoPojoPojo;
    }

    /**
     * Getter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @return property value of userCertificateInfoPojo
     */
    public UserCertificateInfoPojo getUserCertificateInfoPojo() {
        return userCertificateInfoPojo;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @param userCertificateInfoPojo value to be assigned to property userCertificateInfoPojo
     */
    public void setUserCertificateInfoPojo(UserCertificateInfoPojo userCertificateInfoPojo) {
        this.userCertificateInfoPojo = userCertificateInfoPojo;
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
     * Getter method for property <tt>recommenderName</tt>.
     * 
     * @return property value of recommenderName
     */
    public String getRecommenderName() {
        return recommenderName;
    }

    /**
     * Setter method for property <tt>recommenderName</tt>.
     * 
     * @param recommenderName value to be assigned to property recommenderName
     */
    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

}
