/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 客户角色pojo
 * @author hongsj
 * @version $Id: UserCustomerRolePojo.java, v 1.0 2017年3月27日 下午1:56:59 hongsj Exp $
 */
public class UserCustomerRolePojo implements Serializable {
    /**  */
    private static final long serialVersionUID = 4391272091383075920L;
    /** 客户角色Id */
    private String            custRoleId;
    /** 通行证Id */
    private String            authId;
    /** 客户Id */
    private String            userId;
    /** 组织Id */
    private String            organCode;
    /** 审核状态 */
    private EnumVerifyStatus  verifyStatus;
    /** 角色Id */
    private String            roleId;

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
     * Getter method for property <tt>authId</tt>.
     * 
     * @return property value of authId
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Setter method for property <tt>authId</tt>.
     * 
     * @param authId value to be assigned to property authId
     */
    public void setAuthId(String authId) {
        this.authId = authId;
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
     * Getter method for property <tt>organCode</tt>.
     * 
     * @return property value of organCode
     */
    public String getOrganCode() {
        return organCode;
    }

    /**
     * Setter method for property <tt>organCode</tt>.
     * 
     * @param organCode value to be assigned to property organCode
     */
    public void setOrganCode(String organCode) {
        this.organCode = organCode;
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
     * Getter method for property <tt>roleId</tt>.
     * 
     * @return property value of roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * Setter method for property <tt>roleId</tt>.
     * 
     * @param roleId value to be assigned to property roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
