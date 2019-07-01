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
 * 登录请求
 * @author huangbb
 * @version $Id: LoginRequest.java, v 1.0 2017年3月20日 上午10:25:53 huangbb Exp $
 */
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 7557754352201867178L;
    
    /**联合登录类型 联合登录必传*/
    private EnumIdentifyKind identifyKind;
    
    /**登录账号  联合登录传授权token*/
    @NotNull(errorCode = "000001", message = "登录账号")
    @NotBlank(errorCode = "000001", message = "登录账号")
    private String identifiers;
    
    /**密码 联合登录为授权token*/
    @NotNull(errorCode = "000001", message = "密码")
    @NotBlank(errorCode = "000001", message = "密码")
    private String authPassword;
    
    /**识别来源 给定登录信息如ip、机器型号等 json数据 key参考源数据*/
    @NotNull(errorCode = "000001", message = "识别来源")
    @NotBlank(errorCode = "000001", message = "识别来源")
    private String identifySource;

    /**
     * Getter method for property <tt>identifiers</tt>.
     * 
     * @return property value of identifiers
     */
    public String getIdentifiers() {
        return identifiers;
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
