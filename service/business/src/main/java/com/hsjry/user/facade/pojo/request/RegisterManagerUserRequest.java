/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumUserType;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 注册管理台用户
 * @author hongsj
 * @version $Id: RecommendRegisterRequest.java, v 1.0 2017年4月19日 上午9:59:23 hongsj Exp $
 */
public class RegisterManagerUserRequest implements Serializable {

    private static final long serialVersionUID = -4948497531146171740L;

    /**客户姓名*/
    @NotNull(errorCode = "000001", message = "客户姓名")
    @NotBlank(errorCode = "000001", message = "客户姓名")
    private String            clientName;

    /**手机号*/
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String            telephone;

    /**登录账号*/
    @NotNull(errorCode = "000001", message = "登录账号")
    @NotBlank(errorCode = "000001", message = "登录账号")
    private String            identifiers;

    /**密码*/
    @NotNull(errorCode = "000001", message = "密码")
    @NotBlank(errorCode = "000001", message = "密码")
    private String            authPassword;
    //组织ID
    @NotNull(errorCode = "000001", message = "组织ID")
    @NotBlank(errorCode = "000001", message = "组织ID")
    private String            organId;
    
    /**用户类型：新增渠道经理传CUSTOMER_MANAGER，默认新增管理员客户*/
    @NotNull(errorCode = "000001", message = "用户类型")
    private EnumUserType userType = EnumUserType.ADMIN_CUSTOMER;
    
    

    /**
     * Getter method for property <tt>userType</tt>.
     * 
     * @return property value of userType
     */
    public EnumUserType getUserType() {
        return userType;
    }

    /**
     * Setter method for property <tt>userType</tt>.
     * 
     * @param userType value to be assigned to property userType
     */
    public void setUserType(EnumUserType userType) {
        this.userType = userType;
    }

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
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
     * Getter method for property <tt>identifiers</tt>.
     * 
     * @return property value of identifiers
     */
    public String getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter method for property <tt>identifiers</tt>.
     * 
     * @param identifiers value to be assigned to property identifiers
     */
    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * Getter method for property <tt>authPassword</tt>.
     * 
     * @return property value of authPassword
     */
    public String getAuthPassword() {
        return authPassword;
    }

    /**
     * Setter method for property <tt>authPassword</tt>.
     * 
     * @param authPassword value to be assigned to property authPassword
     */
    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

}
