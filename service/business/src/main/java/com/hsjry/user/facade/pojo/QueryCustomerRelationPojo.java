/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCustomerRelationPojo.java, v 1.0 2017年4月4日 下午2:15:14 jiangjd12837 Exp $
 */
public class QueryCustomerRelationPojo implements Serializable {

    /**  */
    private static final long                    serialVersionUID = -4629129648228974166L;
    //客户关系信息列表
    private UserCustomeRelationInfoPojo          userCustomeRelationPojo;
    //客户基本信息
    private UserPersonalBasicInfoPojo            userPersonalBasicInfoPojo;
    //客户信息列表
    private UserCustomerInfoPojo                 userCustomerInfoPojoPojo;
    //客户关系扩展信息列表
    private List<UserCustomerRelationExtendPojo> userCustomerRelationExtendPojoList;
    //证件信息
    private List<UserCertificateInfoPojo>        userCertificateInfoPojoList;
    //职业信息
    private List<UserProfessionalInfoPojo>       userProfessionalInfoPojoList;
    //电话联系点
    private List<UserTelContactStationInfoPojo>  userTelContactStationInfoPojoList;

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
     * Getter method for property <tt>userCustomeRelationPojo</tt>.
     * 
     * @return property value of userCustomeRelationPojo
     */
    public UserCustomeRelationInfoPojo getUserCustomeRelationPojo() {
        return userCustomeRelationPojo;
    }

    /**
     * Setter method for property <tt>userCustomeRelationPojo</tt>.
     * 
     * @param userCustomeRelationPojo value to be assigned to property userCustomeRelationPojo
     */
    public void setUserCustomeRelationPojo(UserCustomeRelationInfoPojo userCustomeRelationPojo) {
        this.userCustomeRelationPojo = userCustomeRelationPojo;
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
     * Getter method for property <tt>userCustomerRelationExtendPojoList</tt>.
     * 
     * @return property value of userCustomerRelationExtendPojoList
     */
    public List<UserCustomerRelationExtendPojo> getUserCustomerRelationExtendPojoList() {
        return userCustomerRelationExtendPojoList;
    }

    /**
     * Setter method for property <tt>userCustomerRelationExtendPojoList</tt>.
     * 
     * @param userCustomerRelationExtendPojoList value to be assigned to property userCustomerRelationExtendPojoList
     */
    public void setUserCustomerRelationExtendPojoList(List<UserCustomerRelationExtendPojo> userCustomerRelationExtendPojoList) {
        this.userCustomerRelationExtendPojoList = userCustomerRelationExtendPojoList;
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

}
