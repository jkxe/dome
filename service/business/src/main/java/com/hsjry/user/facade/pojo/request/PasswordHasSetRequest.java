/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumIdentifyType;
import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author huangbb
 * @version $Id: PasswordHasSetRequest.java, v 1.0 2017年7月4日 下午2:30:45 huangbb Exp $
 */
public class PasswordHasSetRequest implements Serializable {

    private static final long serialVersionUID = -4355807076508297473L;

    /** 通行证ID*/
    @NotNull(errorCode = "000001", message = "通行证ID")
    @NotBlank(errorCode = "000001", message = "通行证ID")
    private String authId;

    /**识别类型*/
    @NotNull(errorCode = "000001", message = "识别类型")
    private EnumIdentifyType identifyType;

    /**识别用途*/
    @NotNull(errorCode = "000001", message = "识别用途")
    private EnumPurposeCode purposeCode;


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
     * Getter method for property <tt>purposeCode</tt>.
     * 
     * @return property value of purposeCode
     */
    public EnumPurposeCode getPurposeCode() {
        return purposeCode;
    }

    /**
     * Setter method for property <tt>purposeCode</tt>.
     * 
     * @param purposeCode value to be assigned to property purposeCode
     */
    public void setPurposeCode(EnumPurposeCode purposeCode) {
        this.purposeCode = purposeCode;
    }

    /**
     * Getter method for property <tt>identifyType</tt>.
     * 
     * @return property value of identifyType
     */
    public EnumIdentifyType getIdentifyType() {
        return identifyType;
    }

    /**
     * Setter method for property <tt>identifyType</tt>.
     * 
     * @param identifyType value to be assigned to property identifyType
     */
    public void setIdentifyType(EnumIdentifyType identifyType) {
        this.identifyType = identifyType;
    }
    
    
}
