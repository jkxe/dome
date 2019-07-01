/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.lang.common.base.enums.user.EnumUserType;
import com.hsjry.user.facade.pojo.enums.EnumListStatus;
import com.hsjry.user.facade.pojo.enums.EnumListVerifyStatus;

/**
 * 查询特殊名单视图请求类
 * @author hongsj
 * @version $Id: QuerySpecialViewRequest.java, v 1.0 2017年3月28日 下午2:09:29 hongsj Exp $
 */
public class QuerySpecialViewRequest implements Serializable {
    /**  */
    private static final long    serialVersionUID = 4534872433238501645L;
    /**客户类型*/
    private EnumUserType         clientCategory;
    /**客户名称*/
    private String               clientName;
    /**证件类型*/
    private EnumCertificateKind  idKind;
    /**证件号码*/
    private String               idNo;
    /**名单状态*/
    private EnumListStatus       applyList;
    /**名单审核状态*/
    private EnumListVerifyStatus verifyStatus;

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

}
