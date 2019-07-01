/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author huangbb
 * @version $Id: StoreChangePagePojo.java, v 1.0 2018年5月16日 下午3:16:36 huangbb Exp $
 */
public class StoreChangePagePojo implements Serializable {

    private static final long serialVersionUID = -867482553803963688L;

    /**门店id  */
    private String            organId;
    /**门店名称  */
    private String            organName;
    /**所属经销商  */
    private String            merchantName;
    /**门店电话  */
    private String            contactWay;
    /**门店负责人  */
    private String            dutyName;
    /**门店账号*/
    private String            account;
    /**最近变更日期*/
    private Date              lastChangeDate;
    /**最近审核结果*/
    private EnumVerifyStatus  verifyStatus;

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
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
     * Getter method for property <tt>merchantName</tt>.
     * 
     * @return property value of merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Setter method for property <tt>merchantName</tt>.
     * 
     * @param merchantName value to be assigned to property merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Getter method for property <tt>contactWay</tt>.
     * 
     * @return property value of contactWay
     */
    public String getContactWay() {
        return contactWay;
    }

    /**
     * Setter method for property <tt>contactWay</tt>.
     * 
     * @param contactWay value to be assigned to property contactWay
     */
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    /**
     * Getter method for property <tt>dutyName</tt>.
     * 
     * @return property value of dutyName
     */
    public String getDutyName() {
        return dutyName;
    }

    /**
     * Setter method for property <tt>dutyName</tt>.
     * 
     * @param dutyName value to be assigned to property dutyName
     */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    /**
     * Getter method for property <tt>account</tt>.
     * 
     * @return property value of account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter method for property <tt>account</tt>.
     * 
     * @param account value to be assigned to property account
     */
    public void setAccount(String account) {
        this.account = account;
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

