/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;
import com.hsjry.user.facade.pojo.enums.EnumMerchantUserType;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author huangbb
 * @version $Id: MerchantPagePojo.java, v 1.0 2018年5月5日 下午5:14:06 huangbb Exp $
 */
public class MerchantPagePojo implements Serializable {

    private static final long    serialVersionUID = 7264804193204297085L;

    /** 经销商编号 */
    private String               userId;

    /** 经销商名称 */
    private String               organName;

    /**经销商分类 */
    private EnumMerchantUserType merchantUserType;

    /**证件类型 */
    private EnumCertificateKind  certificateKind;

    /**证件号码 */
    private String               certificateNo;

    /**激活状态*/
    private EnumBool             activeStatus;

    /**启用状态*/
    private EnumObjectStatus     recordStatus;

    /**客户角色Id */
    private String               custRoleId;

    /**商户角色*/
    private EnumSystemRoleType   roleType;

    /**审核状态 */
    private EnumVerifyStatus     verifyStatus;

    /**创建日期*/
    private Date                 createTime;

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
     * Getter method for property <tt>activeStatus</tt>.
     * 
     * @return property value of activeStatus
     */
    public EnumBool getActiveStatus() {
        return activeStatus;
    }

    /**
     * Setter method for property <tt>activeStatus</tt>.
     * 
     * @param activeStatus value to be assigned to property activeStatus
     */
    public void setActiveStatus(EnumBool activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * Getter method for property <tt>recordStatus</tt>.
     * 
     * @return property value of recordStatus
     */
    public EnumObjectStatus getRecordStatus() {
        return recordStatus;
    }

    /**
     * Setter method for property <tt>recordStatus</tt>.
     * 
     * @param recordStatus value to be assigned to property recordStatus
     */
    public void setRecordStatus(EnumObjectStatus recordStatus) {
        this.recordStatus = recordStatus;
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
     * Getter method for property <tt>roleType</tt>.
     * 
     * @return property value of roleType
     */
    public EnumSystemRoleType getRoleType() {
        return roleType;
    }

    /**
     * Setter method for property <tt>roleType</tt>.
     * 
     * @param roleType value to be assigned to property roleType
     */
    public void setRoleType(EnumSystemRoleType roleType) {
        this.roleType = roleType;
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

}
