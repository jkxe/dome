/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.lang.common.base.enums.user.EnumUserType;
import com.hsjry.user.facade.pojo.enums.EnumListStatus;
import com.hsjry.user.facade.pojo.enums.EnumListVerifyStatus;

/**
 * 特殊名单pojo
 * @author hongsj
 * @version $Id: UserSpecialListPojo.java, v 1.0 2017年3月22日 上午10:30:54 hongsj Exp $
 */
public class UserSpecialListPojo implements Serializable {
    /**  */
    private static final long    serialVersionUID = 4985691672731249424L;
    /**
     * 名单Id
     */
    private String               listId;
    /**
     * 租户Id
     */
    private String               tenantId;
    /**
     * 审核编号
     */
    private String               auditSerialNo;
    /**
     * 用户Id
     */
    private String               userId;
    /**
     * 申请名单
     */
    private EnumListStatus       applyList;
    /**
     * 申请前名单
     */
    private EnumListStatus       originalList;
    
    /**
     * 真实名单
     */
    private EnumListStatus      realList;
    /**
     * 证件类型
     */
    private EnumCertificateKind  idKind;
    /**
     * 证件号码
     */
    private String               idNo;
    /**
     * 客户类型
     */
    private EnumUserType         clientCategory;
    /**
     * 客户名称
     */
    private String               clientName;
    /**
     * 名单来源
     */
    private String               listSource;
    /**
     * 加入时间
     */
    private Date                 buildDate;
    /**
     * 加入理由
     */
    private String               reason;
    /**
     * 加入人客户ID
     */
    private String               acuserId;
    /**
     * 撤销时间
     */
    private Date                 cancelDate;
    /**
     * 撤销理由
     */
    private String               cancelReason;
    /**
     * 撤销人客户ID
     */
    private String               cancelUserId;
    /**
     * 审核状态
     */
    private EnumListVerifyStatus    verifyStatus;

    /**
     * Getter method for property <tt>listId</tt>.
     * 
     * @return property value of listId
     */
    public String getListId() {
        return listId;
    }

    /**
     * Setter method for property <tt>listId</tt>.
     * 
     * @param listId value to be assigned to property listId
     */
    public void setListId(String listId) {
        this.listId = listId;
    }

    /**
     * Getter method for property <tt>tenantId</tt>.
     * 
     * @return property value of tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Setter method for property <tt>tenantId</tt>.
     * 
     * @param tenantId value to be assigned to property tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Getter method for property <tt>auditSerialNo</tt>.
     * 
     * @return property value of auditSerialNo
     */
    public String getAuditSerialNo() {
        return auditSerialNo;
    }

    /**
     * Setter method for property <tt>auditSerialNo</tt>.
     * 
     * @param auditSerialNo value to be assigned to property auditSerialNo
     */
    public void setAuditSerialNo(String auditSerialNo) {
        this.auditSerialNo = auditSerialNo;
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
     * Getter method for property <tt>applyList</tt>.
     * 
     * @return property value of applyList
     */
    public EnumListStatus getApplyList() {
        return applyList;
    }

    /**
     * Setter method for property <tt>applyList</tt>.
     * 
     * @param applyList value to be assigned to property applyList
     */
    public void setApplyList(EnumListStatus applyList) {
        this.applyList = applyList;
    }

    /**
     * Getter method for property <tt>originalList</tt>.
     * 
     * @return property value of originalList
     */
    public EnumListStatus getOriginalList() {
        return originalList;
    }

    /**
     * Setter method for property <tt>originalList</tt>.
     * 
     * @param originalList value to be assigned to property originalList
     */
    public void setOriginalList(EnumListStatus originalList) {
        this.originalList = originalList;
    }

    /**
     * Getter method for property <tt>idKind</tt>.
     * 
     * @return property value of idKind
     */
    public EnumCertificateKind getIdKind() {
        return idKind;
    }

    /**
     * Setter method for property <tt>idKind</tt>.
     * 
     * @param idKind value to be assigned to property idKind
     */
    public void setIdKind(EnumCertificateKind idKind) {
        this.idKind = idKind;
    }

    /**
     * Getter method for property <tt>idNo</tt>.
     * 
     * @return property value of idNo
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Setter method for property <tt>idNo</tt>.
     * 
     * @param idNo value to be assigned to property idNo
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * Getter method for property <tt>clientCategory</tt>.
     * 
     * @return property value of clientCategory
     */
    public EnumUserType getClientCategory() {
        return clientCategory;
    }

    /**
     * Setter method for property <tt>clientCategory</tt>.
     * 
     * @param clientCategory value to be assigned to property clientCategory
     */
    public void setClientCategory(EnumUserType clientCategory) {
        this.clientCategory = clientCategory;
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
     * Getter method for property <tt>listSource</tt>.
     * 
     * @return property value of listSource
     */
    public String getListSource() {
        return listSource;
    }

    /**
     * Setter method for property <tt>listSource</tt>.
     * 
     * @param listSource value to be assigned to property listSource
     */
    public void setListSource(String listSource) {
        this.listSource = listSource;
    }

    /**
     * Getter method for property <tt>buildDate</tt>.
     * 
     * @return property value of buildDate
     */
    public Date getBuildDate() {
        return buildDate;
    }

    /**
     * Setter method for property <tt>buildDate</tt>.
     * 
     * @param buildDate value to be assigned to property buildDate
     */
    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    /**
     * Getter method for property <tt>reason</tt>.
     * 
     * @return property value of reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter method for property <tt>reason</tt>.
     * 
     * @param reason value to be assigned to property reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Getter method for property <tt>acuserId</tt>.
     * 
     * @return property value of acuserId
     */
    public String getAcuserId() {
        return acuserId;
    }

    /**
     * Setter method for property <tt>acuserId</tt>.
     * 
     * @param acuserId value to be assigned to property acuserId
     */
    public void setAcuserId(String acuserId) {
        this.acuserId = acuserId;
    }

    /**
     * Getter method for property <tt>cancelDate</tt>.
     * 
     * @return property value of cancelDate
     */
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * Setter method for property <tt>cancelDate</tt>.
     * 
     * @param cancelDate value to be assigned to property cancelDate
     */
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    /**
     * Getter method for property <tt>cancelReason</tt>.
     * 
     * @return property value of cancelReason
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * Setter method for property <tt>cancelReason</tt>.
     * 
     * @param cancelReason value to be assigned to property cancelReason
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    /**
     * Getter method for property <tt>cancelUserId</tt>.
     * 
     * @return property value of cancelUserId
     */
    public String getCancelUserId() {
        return cancelUserId;
    }

    /**
     * Setter method for property <tt>cancelUserId</tt>.
     * 
     * @param cancelUserId value to be assigned to property cancelUserId
     */
    public void setCancelUserId(String cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    /**
     * Getter method for property <tt>verifyStatus</tt>.
     * 
     * @return property value of verifyStatus
     */
    public EnumListVerifyStatus getVerifyStatus() {
        return verifyStatus;
    }

    /**
     * Setter method for property <tt>verifyStatus</tt>.
     * 
     * @param verifyStatus value to be assigned to property verifyStatus
     */
    public void setVerifyStatus(EnumListVerifyStatus verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    /**
     * Getter method for property <tt>realList</tt>.
     * 
     * @return property value of realList
     */
    public EnumListStatus getRealList() {
        return realList;
    }

    /**
     * Setter method for property <tt>realList</tt>.
     * 
     * @param realList value to be assigned to property realList
     */
    public void setRealList(EnumListStatus realList) {
        this.realList = realList;
    }

}
