/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryAppLoginInfoPojo.java, v 1.0 2017年4月28日 下午2:21:30 jiangjd12837 Exp $
 */
public class QueryAppLoginInfoPojo implements Serializable {

    /**  */
    private static final long             serialVersionUID = 1423289924890689278L;
    //个人客户基本信息表
    private UserPersonalBasicInfoPojo     userPersonalBasicInfoPojo;
    //手机号联系点
    private UserTelContactStationInfoPojo userTelContactStationInfoPojo;
    //客户信息
    private UserCustomerRolePojo          userCustomerRolePojo;
    //实名认证标志
    private EnumBool                      realnameFlag;

    /**
     * Getter method for property <tt>realnameFlag</tt>.
     * 
     * @return property value of realnameFlag
     */
    public EnumBool getRealnameFlag() {
        return realnameFlag;
    }

    /**
     * Setter method for property <tt>realnameFlag</tt>.
     * 
     * @param realnameFlag value to be assigned to property realnameFlag
     */
    public void setRealnameFlag(EnumBool realnameFlag) {
        this.realnameFlag = realnameFlag;
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
