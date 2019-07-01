/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huangbb
 * @version $Id: QueryUserAppInfoResponse.java, v 1.0 2017年4月20日 下午7:09:45 jiangjd12837 Exp $
 */
public class QueryUserAppInfoResponse implements Serializable {

    private static final long serialVersionUID = 4318148414106819514L;
    //客户ID
    private String userId;
    //联系点ID
    private String stationId;
    //手机号 200
    private String telephone;
    //客户姓名
    private String clientName;
    //头像
    private String headImg;
    //实名认证标志
    private EnumBool realnameFlag;
    //证件资源项ID
    private String certificateResourceId;
    //证件类型
    private EnumCertificateKind certificateKind;
    //证件号码
    private String certificateNo;
    /**
     * 证件失效期
     */
    private Date invalidDate;

    private Integer hasSecondaryAccount;

    public Integer getHasSecondaryAccount() {
        return hasSecondaryAccount;
    }

    public void setHasSecondaryAccount(Integer hasSecondaryAccount) {
        this.hasSecondaryAccount = hasSecondaryAccount;
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
     * Getter method for property <tt>headImg</tt>.
     *
     * @return property value of headImg
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * Setter method for property <tt>headImg</tt>.
     *
     * @param headImg value to be assigned to property headImg
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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
     * Getter method for property <tt>certificateKind</tt>.
     *
     * @return property value of certificateKind
     */
    public EnumCertificateKind getCertificateKind() {
        return certificateKind;
    }

    /**
     * Setter method for property <tt>certificateKind</tt>.
     *
     * @param certificateKind value to be assigned to property certificateKind
     */
    public void setCertificateKind(EnumCertificateKind certificateKind) {
        this.certificateKind = certificateKind;
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

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }
}
