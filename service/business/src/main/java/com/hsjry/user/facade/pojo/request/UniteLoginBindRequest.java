/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumIdentifyKind;

/**
 * 
 * @author huangbb
 * @version $Id: UniteLogInBindRequest.java, v 1.0 2017年3月21日 下午2:54:07 huangbb Exp $
 */
public class UniteLoginBindRequest implements Serializable {

    private static final long serialVersionUID = 3937000197403846652L;

    /**授权token*/
    @NotNull(errorCode = "000001", message = "授权token")
    @NotBlank(errorCode = "000001", message = "授权token")
    private String userToken;
    
    /**标识类型*/
    @NotNull(errorCode = "000001", message = "标识类型")
    private EnumIdentifyKind identifyKind;
    
    /**登录账号*/
    @NotNull(errorCode = "000001", message = "登录账号")
    @NotBlank(errorCode = "000001", message = "登录账号")
    private String identifiers;
    
    /**密码*/
    @NotNull(errorCode = "000001", message = "密码")
    @NotBlank(errorCode = "000001", message = "密码")
    private String authPassword;
    
    /**识别来源 给定登录信息如ip、机器型号等 json数据 key参考源数据*/
    @NotNull(errorCode = "000001", message = "识别来源")
    @NotBlank(errorCode = "000001", message = "识别来源")
    private String identifySource;

    /**
     * Getter method for property <tt>userToken</tt>.
     * 
     * @return property value of userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Setter method for property <tt>userToken</tt>.
     * 
     * @param userToken value to be assigned to property userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * Getter method for property <tt>identifyKind</tt>.
     * 
     * @return property value of identifyKind
     */
    public EnumIdentifyKind getIdentifyKind() {
        return identifyKind;
    }

    /**
     * Setter method for property <tt>identifyKind</tt>.
     * 
     * @param identifyKind value to be assigned to property identifyKind
     */
    public void setIdentifyKind(EnumIdentifyKind identifyKind) {
        this.identifyKind = identifyKind;
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

    /**
     * Getter method for property <tt>identifySource</tt>.
     * 
     * @return property value of identifySource
     */
    public String getIdentifySource() {
        return identifySource;
    }

    /**
     * Setter method for property <tt>identifySource</tt>.
     * 
     * @param identifySource value to be assigned to property identifySource
     */
    public void setIdentifySource(String identifySource) {
        this.identifySource = identifySource;
    }
    
    

}
