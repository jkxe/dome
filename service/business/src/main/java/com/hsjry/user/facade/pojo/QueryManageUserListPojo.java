/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManageUserListRequest.java, v 1.0 2017年6月15日 上午10:26:43 jiangjd12837 Exp $
 */
public class QueryManageUserListPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -2234959637866752532L;
    //客户ID
    private String            userId;
    //客户名称
    private String            clientName;
    //客户账号
    private String            clientAccount;
    //手机号
    private String            mobileTel;
    //用户状态
    private EnumObjectStatus  idStatus;

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
     * Getter method for property <tt>clientAccount</tt>.
     * 
     * @return property value of clientAccount
     */
    public String getClientAccount() {
        return clientAccount;
    }

    /**
     * Getter method for property <tt>idStatus</tt>.
     * 
     * @return property value of idStatus
     */
    public EnumObjectStatus getIdStatus() {
        return idStatus;
    }

    /**
     * Setter method for property <tt>idStatus</tt>.
     * 
     * @param idStatus value to be assigned to property idStatus
     */
    public void setIdStatus(EnumObjectStatus idStatus) {
        this.idStatus = idStatus;
    }

    /**
     * Setter method for property <tt>clientAccount</tt>.
     * 
     * @param clientAccount value to be assigned to property clientAccount
     */
    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
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
     * Getter method for property <tt>mobileTel</tt>.
     * 
     * @return property value of mobileTel
     */
    public String getMobileTel() {
        return mobileTel;
    }

    /**
     * Setter method for property <tt>mobileTel</tt>.
     * 
     * @param mobileTel value to be assigned to property mobileTel
     */
    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

}
