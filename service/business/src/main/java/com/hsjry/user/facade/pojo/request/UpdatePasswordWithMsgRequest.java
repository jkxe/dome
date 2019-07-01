/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

/**
 * 
 * @author zhengqy15963
 * @version $Id: UpdatePasswordWithMsgRequest.java, v 1.0 2018年4月19日 上午11:13:50 zhengqy15963 Exp $
 */
public class UpdatePasswordWithMsgRequest implements Serializable {

    private static final long serialVersionUID = -4103313234517537808L;
    /** 手机号 */
    @NotNull(errorCode = "000001", message = "手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="手机号")
    private String            telephone;
    /** 验证码 */
    @NotNull(errorCode = "000001", message = "验证码")
    @NotBlank(errorCode = "000001", message = "验证码")
    @Length(min=6, max = 6,errorCode="000002",message="验证码")
    @MatchPattern(matchAll=false,pattern={"^\\d{6}$"},errorCode="000003",message="验证码")
    private String            msgCode;
    /** 识别用途 */
    private EnumPurposeCode   purposeCode;
    /** 新密码 */
    @NotNull(errorCode = "000001", message = "新密码")
    @NotBlank(errorCode = "000001", message = "新密码")
    private String            newIdentifyContent;

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
     * Getter method for property <tt>msgCode</tt>.
     * 
     * @return property value of msgCode
     */
    public String getMsgCode() {
        return msgCode;
    }

    /**
     * Setter method for property <tt>msgCode</tt>.
     * 
     * @param msgCode value to be assigned to property msgCode
     */
    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
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
     * Getter method for property <tt>newIdentifyContent</tt>.
     * 
     * @return property value of newIdentifyContent
     */
    public String getNewIdentifyContent() {
        return newIdentifyContent;
    }

    /**
     * Setter method for property <tt>newIdentifyContent</tt>.
     * 
     * @param newIdentifyContent value to be assigned to property newIdentifyContent
     */
    public void setNewIdentifyContent(String newIdentifyContent) {
        this.newIdentifyContent = newIdentifyContent;
    }

}
