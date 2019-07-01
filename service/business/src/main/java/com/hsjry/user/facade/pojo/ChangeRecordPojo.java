/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.user.facade.pojo.enums.EnumRegisterKind;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author zhengqy15963
 * @version $Id: ChangeRecordPojo.java, v 1.0 2018年5月17日 下午7:51:37 zhengqy15963 Exp $
 */
public class ChangeRecordPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -5860641858131682115L;
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
    //变更日期
    private Date              createTime;
    //审核完成日期
    private Date              updateTime;
    //经办人
    private String            operator;

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

    /**
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property <tt>updateTime</tt>.
     * 
     * @return property value of updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Setter method for property <tt>updateTime</tt>.
     * 
     * @param updateTime value to be assigned to property updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     * 
     * @return property value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     * 
     * @param operator value to be assigned to property operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

}
