/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryPartnerViewPagePojo.java, v 1.0 2017年3月30日 下午6:42:43 jiangjd12837 Exp $
 */
public class QueryPartnerViewPagePojo implements Serializable {

    /**  */
    private static final long                 serialVersionUID = 5769662832005045165L;
    //客户证件信息
    private UserCertificateInfoPojo           userCertificateInfoPojo;
    //地址联系点
    private UserAddressContactStationInfoPojo userAddressContactStationInfoPojo;
    //联系人
    private String                            authorizedName;
    //联系人电话
    private String                            authorizedTel;
    //客户信息（包含记录状态）
    private UserCustomerInfoPojo              UserCustomerInfoPojo;
    //合作机构编号
    private String                            userId;
    //合作机构名称
    private String                            organName;
    //法人姓名
    private String                            relationName;

    /**
     * Getter method for property <tt>relationName</tt>.
     * 
     * @return property value of relationName
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * Setter method for property <tt>relationName</tt>.
     * 
     * @param relationName value to be assigned to property relationName
     */
    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    /**
     * Getter method for property <tt>authorizedName</tt>.
     * 
     * @return property value of authorizedName
     */
    public String getAuthorizedName() {
        return authorizedName;
    }

    /**
     * Setter method for property <tt>authorizedName</tt>.
     * 
     * @param authorizedName value to be assigned to property authorizedName
     */
    public void setAuthorizedName(String authorizedName) {
        this.authorizedName = authorizedName;
    }

    /**
     * Getter method for property <tt>authorizedTel</tt>.
     * 
     * @return property value of authorizedTel
     */
    public String getAuthorizedTel() {
        return authorizedTel;
    }

    /**
     * Setter method for property <tt>authorizedTel</tt>.
     * 
     * @param authorizedTel value to be assigned to property authorizedTel
     */
    public void setAuthorizedTel(String authorizedTel) {
        this.authorizedTel = authorizedTel;
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
     * Getter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojo
     */
    public UserAddressContactStationInfoPojo getUserAddressContactStationInfoPojo() {
        return userAddressContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @param userAddressContactStationInfoPojo value to be assigned to property userAddressContactStationInfoPojo
     */
    public void setUserAddressContactStationInfoPojo(UserAddressContactStationInfoPojo userAddressContactStationInfoPojo) {
        this.userAddressContactStationInfoPojo = userAddressContactStationInfoPojo;
    }

    /**
     * Getter method for property <tt>userCustomerInfoPojo</tt>.
     * 
     * @return property value of UserCustomerInfoPojo
     */
    public UserCustomerInfoPojo getUserCustomerInfoPojo() {
        return UserCustomerInfoPojo;
    }

    /**
     * Setter method for property <tt>userCustomerInfoPojo</tt>.
     * 
     * @param UserCustomerInfoPojo value to be assigned to property userCustomerInfoPojo
     */
    public void setUserCustomerInfoPojo(UserCustomerInfoPojo userCustomerInfoPojo) {
        UserCustomerInfoPojo = userCustomerInfoPojo;
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
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

}
