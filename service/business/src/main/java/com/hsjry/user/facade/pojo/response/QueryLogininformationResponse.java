/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserCustomerRolePojo;
import com.hsjry.user.facade.pojo.UserPersonalBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserTelContactStationInfoPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryLogininformationResponse.java, v 1.0 2017年4月28日 下午2:01:50 jiangjd12837 Exp $
 */
public class QueryLogininformationResponse implements Serializable {

    /**  */
    private static final long             serialVersionUID = 3680925745612791872L;
    //个人客户基本信息表
    private UserPersonalBasicInfoPojo     userPersonalBasicInfoPojo;
    //手机号联系点
    private UserTelContactStationInfoPojo userTelContactStationInfoPojo;
    //客户角色信息
    private UserCustomerRolePojo          userCustomerRolePojo;
    //登录名
    private String                        loginName;

    /**
     * Getter method for property <tt>loginName</tt>.
     * 
     * @return property value of loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Setter method for property <tt>loginName</tt>.
     * 
     * @param loginName value to be assigned to property loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    /**
     * Getter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @return property value of userPersonalBasicInfoPojo
     */
    public UserPersonalBasicInfoPojo getUserPersonalBasicInfoPojo() {
        return userPersonalBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @param userPersonalBasicInfoPojo value to be assigned to property userPersonalBasicInfoPojo
     */
    public void setUserPersonalBasicInfoPojo(UserPersonalBasicInfoPojo userPersonalBasicInfoPojo) {
        this.userPersonalBasicInfoPojo = userPersonalBasicInfoPojo;
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
     * Getter method for property <tt>userCustomerRolePojo</tt>.
     * 
     * @return property value of userCustomerRolePojo
     */
    public UserCustomerRolePojo getUserCustomerRolePojo() {
        return userCustomerRolePojo;
    }

    /**
     * Setter method for property <tt>userCustomerRolePojo</tt>.
     * 
     * @param userCustomerRolePojo value to be assigned to property userCustomerRolePojo
     */
    public void setUserCustomerRolePojo(UserCustomerRolePojo userCustomerRolePojo) {
        this.userCustomerRolePojo = userCustomerRolePojo;
    }

}
