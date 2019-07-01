/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumIdentifyType;
import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

/**
 * 设置密码
 * @author huangbb
 * @version $Id: SetPasswordRequest.java, v 1.0 2017年3月30日 下午3:16:42 huangbb Exp $
 */
public class SetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 4558227098879444227L;

    /**识别类型*/
    @NotNull(errorCode = "000001", message = "识别类型")
    private EnumIdentifyType identifyType;
    
    /**新密码*/
    @NotNull(errorCode = "000001", message = "密码")
    @NotBlank(errorCode = "000001", message = "密码")
    private String identifyContent;
    
    /**识别用途*/
    @NotNull(errorCode = "000001", message = "识别用途")
    private EnumPurposeCode purposeCode;

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

    /**
     * Getter method for property <tt>identifyContent</tt>.
     * 
     * @return property value of identifyContent
     */
    public String getIdentifyContent() {
        return identifyContent;
    }

    /**
     * Setter method for property <tt>identifyContent</tt>.
     * 
     * @param identifyContent value to be assigned to property identifyContent
     */
    public void setIdentifyContent(String identifyContent) {
        this.identifyContent = identifyContent;
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
    
    
}
