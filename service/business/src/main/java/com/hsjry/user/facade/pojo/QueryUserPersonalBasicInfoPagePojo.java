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
 * @version $Id: QueryUserPersonalBasicInfoPagePojo.java, v 1.0 2017年5月11日 下午4:51:28 jiangjd12837 Exp $
 */
public class QueryUserPersonalBasicInfoPagePojo implements Serializable {

    /**  */
    private static final long         serialVersionUID = -1114023335706287476L;
    //客户基本信息
    private UserPersonalBasicInfoPojo userPersonalBasicInfoPojo;
    //手机号
    private String                    telephone;
    //登录名
    private String                    userName;
    //用户状态
    private EnumObjectStatus          idStatus;

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
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

}
