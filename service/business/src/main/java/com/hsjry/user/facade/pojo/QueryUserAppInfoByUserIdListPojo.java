/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author huangbb
 * @version $Id: QueryUserAppInfoResponse.java, v 1.0 2017年4月20日 下午7:09:45 jiangjd12837 Exp $
 */
public class QueryUserAppInfoByUserIdListPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 6876288995978264269L;
    //客户ID
    private String            userId;
    //联系点ID
    private String            stationId;
    //手机号 200
    private String            telephone;
    //客户姓名
    private String            clientName;
    //实名认证标志
    private EnumBool          realnameFlag;
    //证件资源项ID
    private String            certificateResourceId;
    //身份证号码
    private String            certificateNo;

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
     * Getter method for property <tt>stationId</tt>.
     * 
     * @return property value of stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Setter method for property <tt>stationId</tt>.
     * 
     * @param stationId value to be assigned to property stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

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
     * Getter method for property <tt>realnameFlag</tt>.
     * 
     * @return property value of realnameFlag
     */
    public EnumBool getRealnameFlag() {
        return realnameFlag;
    }

    /**
     * Setter method for property <tt>realnameFlag</tt>.
     * 
     * @param realnameFlag value to be assigned to property realnameFlag
     */
    public void setRealnameFlag(EnumBool realnameFlag) {
        this.realnameFlag = realnameFlag;
    }

    /**
     * Getter method for property <tt>certificateResourceId</tt>.
     * 
     * @return property value of certificateResourceId
     */
    public String getCertificateResourceId() {
        return certificateResourceId;
    }

    /**
     * Setter method for property <tt>certificateResourceId</tt>.
     * 
     * @param certificateResourceId value to be assigned to property certificateResourceId
     */
    public void setCertificateResourceId(String certificateResourceId) {
        this.certificateResourceId = certificateResourceId;
    }

    /**
     * Getter method for property <tt>certificateNo</tt>.
     * 
     * @return property value of certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * Setter method for property <tt>certificateNo</tt>.
     * 
     * @param certificateNo value to be assigned to property certificateNo
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

}
