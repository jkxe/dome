/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserOrganBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserTelContactStationInfoPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: EnterpriseCustomerToBusinessPojo.java, v 1.0 2017年4月6日 下午6:57:54 jiangjd12837 Exp $
 */
public class EnterpriseCustomerToBusinessRequest implements Serializable {

    /**  */
    private static final long                 serialVersionUID = 6447584429988616212L;
    //客户ID
    private String                            userId;
    //用户名
    private String                            userName;
    //经营信息
    private UserManageInfoPojo                usermanageInfoPojo;
    //证件资源项信息
    private UserCertificateInfoPojo           userCertificateInfoPojo;
    //机构基本信息
    private UserOrganBasicInfoPojo            userorganBasicInfoPojo;
    //关系人ID
    private String                            relationUserId;
    //电话联系点
    private UserTelContactStationInfoPojo     userTelContactStationInfoPojo;
    //地址联系点
    private UserAddressContactStationInfoPojo userAddressContactStationInfoPojo;
    //代理人姓名
    private String                            AgentName;
    //代理人电话
    private String                            AgentTelephone;

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
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>usermanageInfoPojo</tt>.
     * 
     * @return property value of usermanageInfoPojo
     */
    public UserManageInfoPojo getUsermanageInfoPojo() {
        return usermanageInfoPojo;
    }

    /**
     * Setter method for property <tt>usermanageInfoPojo</tt>.
     * 
     * @param usermanageInfoPojo value to be assigned to property usermanageInfoPojo
     */
    public void setUsermanageInfoPojo(UserManageInfoPojo usermanageInfoPojo) {
        this.usermanageInfoPojo = usermanageInfoPojo;
    }

    /**
     * Getter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @return property value of userCertificateInfoPojo
     */
    public UserCertificateInfoPojo getUserCertificateInfoPojo() {
        return userCertificateInfoPojo;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @param userCertificateInfoPojo value to be assigned to property userCertificateInfoPojo
     */
    public void setUserCertificateInfoPojo(UserCertificateInfoPojo userCertificateInfoPojo) {
        this.userCertificateInfoPojo = userCertificateInfoPojo;
    }

    /**
     * Getter method for property <tt>userorganBasicInfoPojo</tt>.
     * 
     * @return property value of userorganBasicInfoPojo
     */
    public UserOrganBasicInfoPojo getUserorganBasicInfoPojo() {
        return userorganBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userorganBasicInfoPojo</tt>.
     * 
     * @param userorganBasicInfoPojo value to be assigned to property userorganBasicInfoPojo
     */
    public void setUserorganBasicInfoPojo(UserOrganBasicInfoPojo userorganBasicInfoPojo) {
        this.userorganBasicInfoPojo = userorganBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>relationUserId</tt>.
     * 
     * @return property value of relationUserId
     */
    public String getRelationUserId() {
        return relationUserId;
    }

    /**
     * Setter method for property <tt>relationUserId</tt>.
     * 
     * @param relationUserId value to be assigned to property relationUserId
     */
    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    /**
     * Getter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @return property value of userTelContactStationInfoPojo
     */
    public UserTelContactStationInfoPojo getUserTelContactStationInfoPojo() {
        return userTelContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @param userTelContactStationInfoPojo value to be assigned to property userTelContactStationInfoPojo
     */
    public void setUserTelContactStationInfoPojo(UserTelContactStationInfoPojo userTelContactStationInfoPojo) {
        this.userTelContactStationInfoPojo = userTelContactStationInfoPojo;
    }

    /**
     * Getter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojo
     */
    public UserAddressContactStationInfoPojo getUserAddressContactStationInfoPojo() {
        return userAddressContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @param userAddressContactStationInfoPojo value to be assigned to property userAddressContactStationInfoPojo
     */
    public void setUserAddressContactStationInfoPojo(UserAddressContactStationInfoPojo userAddressContactStationInfoPojo) {
        this.userAddressContactStationInfoPojo = userAddressContactStationInfoPojo;
    }

    /**
     * Getter method for property <tt>agentName</tt>.
     * 
     * @return property value of AgentName
     */
    public String getAgentName() {
        return AgentName;
    }

    /**
     * Setter method for property <tt>agentName</tt>.
     * 
     * @param AgentName value to be assigned to property agentName
     */
    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    /**
     * Getter method for property <tt>agentTelephone</tt>.
     * 
     * @return property value of AgentTelephone
     */
    public String getAgentTelephone() {
        return AgentTelephone;
    }

    /**
     * Setter method for property <tt>agentTelephone</tt>.
     * 
     * @param AgentTelephone value to be assigned to property agentTelephone
     */
    public void setAgentTelephone(String agentTelephone) {
        AgentTelephone = agentTelephone;
    }
}
