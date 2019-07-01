/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;
import com.hsjry.user.facade.pojo.UserImageDataPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryRegistrationSiteDetailsResponse.java, v 1.0 2017年4月28日 上午10:46:56 jiangjd12837 Exp $
 */
public class QueryRegistrationSiteDetailsResponse implements Serializable {

    /**  */
    private static final long            serialVersionUID = 1105955960221182964L;
    //影像资料
    private UserImageDataPojo            userImageDataPojo;
    //证件文档
    private UserCertificateInfoPojo      userCertificatePojo;
    //金融工具
    private UserFinancialInstrumentsPojo userFinancialInstrumentsPojo;

    /**
     * Getter method for property <tt>userImageDataPojo</tt>.
     * 
     * @return property value of userImageDataPojo
     */
    public UserImageDataPojo getUserImageDataPojo() {
        return userImageDataPojo;
    }

    /**
     * Setter method for property <tt>userImageDataPojo</tt>.
     * 
     * @param userImageDataPojo value to be assigned to property userImageDataPojo
     */
    public void setUserImageDataPojo(UserImageDataPojo userImageDataPojo) {
        this.userImageDataPojo = userImageDataPojo;
    }

    /**
     * Getter method for property <tt>userCertificatePojo</tt>.
     * 
     * @return property value of userCertificatePojo
     */
    public UserCertificateInfoPojo getUserCertificatePojo() {
        return userCertificatePojo;
    }

    /**
     * Setter method for property <tt>userCertificatePojo</tt>.
     * 
     * @param userCertificatePojo value to be assigned to property userCertificatePojo
     */
    public void setUserCertificatePojo(UserCertificateInfoPojo userCertificatePojo) {
        this.userCertificatePojo = userCertificatePojo;
    }

    /**
     * Getter method for property <tt>userFinancialInstrumentsPojo</tt>.
     * 
     * @return property value of userFinancialInstrumentsPojo
     */
    public UserFinancialInstrumentsPojo getUserFinancialInstrumentsPojo() {
        return userFinancialInstrumentsPojo;
    }

    /**
     * Setter method for property <tt>userFinancialInstrumentsPojo</tt>.
     * 
     * @param userFinancialInstrumentsPojo value to be assigned to property userFinancialInstrumentsPojo
     */
    public void setUserFinancialInstrumentsPojo(UserFinancialInstrumentsPojo userFinancialInstrumentsPojo) {
        this.userFinancialInstrumentsPojo = userFinancialInstrumentsPojo;
    }

}
