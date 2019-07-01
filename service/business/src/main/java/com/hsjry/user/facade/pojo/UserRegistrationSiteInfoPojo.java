/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.user.facade.pojo.enums.EnumRegisterKind;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: UserRegistrationSiteInfoPojo.java, v 1.0 2017年4月28日 上午11:09:59 jiangjd12837 Exp $
 */
public class UserRegistrationSiteInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -52418222436492790L;
    //客户角色ID
    private String            custRoleId;
    //登记类型
    private EnumRegisterKind  registerKind;
    //登记ID
    private String            registerId;
    //登记渠道
    private String            registrationChannel;
    //登记项生效时间
    private Date              effectDate;
    //登记项失效时间
    private Date              invalidDate;
    //登记状态
    private String            registerStatus;
    //登记内容
    private String            registerMemo;
    //审核状态
    private EnumVerifyStatus  verifyStatus;

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
     * Getter method for property <tt>registerKind</tt>.
     * 
     * @return property value of registerKind
     */
    public EnumRegisterKind getRegisterKind() {
        return registerKind;
    }

    /**
     * Setter method for property <tt>registerKind</tt>.
     * 
     * @param registerKind value to be assigned to property registerKind
     */
    public void setRegisterKind(EnumRegisterKind registerKind) {
        this.registerKind = registerKind;
    }

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * Getter method for property <tt>registrationChannel</tt>.
     * 
     * @return property value of registrationChannel
     */
    public String getRegistrationChannel() {
        return registrationChannel;
    }

    /**
     * Setter method for property <tt>registrationChannel</tt>.
     * 
     * @param registrationChannel value to be assigned to property registrationChannel
     */
    public void setRegistrationChannel(String registrationChannel) {
        this.registrationChannel = registrationChannel;
    }

    /**
     * Getter method for property <tt>effectDate</tt>.
     * 
     * @return property value of effectDate
     */
    public Date getEffectDate() {
        return effectDate;
    }

    /**
     * Setter method for property <tt>effectDate</tt>.
     * 
     * @param effectDate value to be assigned to property effectDate
     */
    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    /**
     * Getter method for property <tt>invalidDate</tt>.
     * 
     * @return property value of invalidDate
     */
    public Date getInvalidDate() {
        return invalidDate;
    }

    /**
     * Setter method for property <tt>invalidDate</tt>.
     * 
     * @param invalidDate value to be assigned to property invalidDate
     */
    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    /**
     * Getter method for property <tt>registerStatus</tt>.
     * 
     * @return property value of registerStatus
     */
    public String getRegisterStatus() {
        return registerStatus;
    }

    /**
     * Setter method for property <tt>registerStatus</tt>.
     * 
     * @param registerStatus value to be assigned to property registerStatus
     */
    public void setRegisterStatus(String registerStatus) {
        this.registerStatus = registerStatus;
    }

    /**
     * Getter method for property <tt>registerMemo</tt>.
     * 
     * @return property value of registerMemo
     */
    public String getRegisterMemo() {
        return registerMemo;
    }

    /**
     * Setter method for property <tt>registerMemo</tt>.
     * 
     * @param registerMemo value to be assigned to property registerMemo
     */
    public void setRegisterMemo(String registerMemo) {
        this.registerMemo = registerMemo;
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
