/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumClientRelation;

/**
 * 
 * @author jiangjd12837
 * @version $Id: UserCustomerRelationPojo.java, v 1.0 2017年4月10日 下午9:40:00 jiangjd12837 Exp $
 */
public class UserCustomerRelationPojo implements Serializable {

    /**  */
    private static final long             serialVersionUID = -124453616277210633L;
    /**  */

    //客户ID
    private String                        userId;
    //关系人客户ID   
    private String                        relationUserId;
    //客户关系代码
    private EnumClientRelation            clientRelation;
    //关系人姓名
    private String                        clientName;
    //关系人证件
    private UserCertificateInfoPojo       userCertificateInfoPojo;
    //关系人联系方式
    private UserTelContactStationInfoPojo userTelContactStationInfoPojo;

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
     * Getter method for property <tt>relationUserId</tt>.
     * 
     * @return property value of relationUserId
     */
    public String getRelationUserId() {
        return relationUserId;
    }

    /**
     * Setter method for property <tt>relationUserId</tt>.
     * 
     * @param relationUserId value to be assigned to property relationUserId
     */
    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    /**
     * Getter method for property <tt>clientRelation</tt>.
     * 
     * @return property value of clientRelation
     */
    public EnumClientRelation getClientRelation() {
        return clientRelation;
    }

    /**
     * Setter method for property <tt>clientRelation</tt>.
     * 
     * @param clientRelation value to be assigned to property clientRelation
     */
    public void setClientRelation(EnumClientRelation clientRelation) {
        this.clientRelation = clientRelation;
    }

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
     * Getter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @return property value of userTelContactStationInfoPojo
     */
    public UserTelContactStationInfoPojo getUserTelContactStationInfoPojo() {
        return userTelContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @param userTelContactStationInfoPojo value to be assigned to property userTelContactStationInfoPojo
     */
    public void setUserTelContactStationInfoPojo(UserTelContactStationInfoPojo userTelContactStationInfoPojo) {
        this.userTelContactStationInfoPojo = userTelContactStationInfoPojo;
    }

}
