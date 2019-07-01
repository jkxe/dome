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
 * @version $Id: QueryUserViewPagePojo.java, v 1.0 2017年3月30日 下午2:29:21 jiangjd12837 Exp $
 */
public class QueryUserViewPagePojo implements Serializable {

    /**  */
    private static final long                   serialVersionUID = 5410661094522733605L;
    //证件文档
    private List<UserCertificateInfoPojo>       userCertificatePojoList;
    //手机号联系点
    private List<UserTelContactStationInfoPojo> userTelContactStationInfoPojoList;
    //客户信息
    private UserCustomerInfoPojo                userCustomerInfoPojo;
    //个人客户基本信息表
    private UserPersonalBasicInfoPojo           userPersonalBasicInfoPojo;
    //机构基本信息
    private UserOrganBasicInfoPojo              userOrganBasicInfoPojo;

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
}
