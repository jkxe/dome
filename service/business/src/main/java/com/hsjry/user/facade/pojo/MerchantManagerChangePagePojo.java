/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumMerchantUserType;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author huangbb
 * @version $Id: MerchantManagerChangePagePojo.java, v 1.0 2018年5月16日 下午3:39:09 huangbb Exp $
 */
public class MerchantManagerChangePagePojo implements Serializable {

    private static final long serialVersionUID = -2743269478037910038L;

    /** 经销商编号 */
    private String               userId;

    /** 经销商名称 */
    private String               organName;

    /** 经销商简称 */
    private String               shortName;

    /**经销商分类 */
    private EnumMerchantUserType merchantUserType;

    /**证件类型 */
    private EnumCertificateKind  certificateKind;

    /**证件号码 */
    private String               certificateNo;

    /**客户角色Id */
    private String               custRoleId;

    /**经销商电话 */
    private String               merchantPhone;

    /**渠道经理姓名*/
    private String               channelManager;

    /**门店数量*/
    private int                  storeNum;

    /**最近变更日期*/
    private Date                 lastChangeDate;

    /**最近审核结果*/
    private EnumVerifyStatus     verifyStatus;

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

    /**
     * Getter method for property <tt>shortName</tt>.
     * 
     * @return property value of shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Setter method for property <tt>shortName</tt>.
     * 
     * @param shortName value to be assigned to property shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Getter method for property <tt>merchantUserType</tt>.
     * 
     * @return property value of merchantUserType
     */
    public EnumMerchantUserType getMerchantUserType() {
        return merchantUserType;
    }

    /**
     * Setter method for property <tt>merchantUserType</tt>.
     * 
     * @param merchantUserType value to be assigned to property merchantUserType
     */
    public void setMerchantUserType(EnumMerchantUserType merchantUserType) {
        this.merchantUserType = merchantUserType;
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

    /**
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoleId</tt>.
     * 
     * @param custRoleId value to be assigned to property custRoleId
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
    }

    /**
     * Getter method for property <tt>merchantPhone</tt>.
     * 
     * @return property value of merchantPhone
     */
    public String getMerchantPhone() {
        return merchantPhone;
    }

    /**
     * Setter method for property <tt>merchantPhone</tt>.
     * 
     * @param merchantPhone value to be assigned to property merchantPhone
     */
    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    /**
     * Getter method for property <tt>channelManager</tt>.
     * 
     * @return property value of channelManager
     */
    public String getChannelManager() {
        return channelManager;
    }

    /**
     * Setter method for property <tt>channelManager</tt>.
     * 
     * @param channelManager value to be assigned to property channelManager
     */
    public void setChannelManager(String channelManager) {
        this.channelManager = channelManager;
    }

    /**
     * Getter method for property <tt>storeNum</tt>.
     * 
     * @return property value of storeNum
     */
    public int getStoreNum() {
        return storeNum;
    }

    /**
     * Setter method for property <tt>storeNum</tt>.
     * 
     * @param storeNum value to be assigned to property storeNum
     */
    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    /**
     * Getter method for property <tt>lastChangeDate</tt>.
     * 
     * @return property value of lastChangeDate
     */
    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    /**
     * Setter method for property <tt>lastChangeDate</tt>.
     * 
     * @param lastChangeDate value to be assigned to property lastChangeDate
     */
    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    /**
     * Getter method for property <tt>verifyStatus</tt>.
     * 
     * @return property value of verifyStatus
     */
    public EnumVerifyStatus getVerifyStatus() {
        return verifyStatus;
    }

    /**
     * Setter method for property <tt>verifyStatus</tt>.
     * 
     * @param verifyStatus value to be assigned to property verifyStatus
     */
    public void setVerifyStatus(EnumVerifyStatus verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
    
    
}
