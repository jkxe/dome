/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * 个人支付宝实名绑卡
 * @author huangbb
 * @version $Id: AlipayRealNameRequest.java, v 1.0 2017年4月3日 下午5:03:46 huangbb Exp $
 */
public class AlipayRealNameRequest implements Serializable {

    
    private static final long serialVersionUID = 6567234137493415581L;

    /**校验码*/
    @NotNull(errorCode = "000001", message = "校验码")
    @Length(min=6, max = 6,errorCode="000002",message="校验码")
    @MatchPattern(matchAll=false,pattern={"^\\d{6}$"},errorCode="000003",message="校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    private String authCheckCode;
    
    /**支付宝账号*/
    @NotNull(errorCode = "000001", message = "支付宝账号")
    @NotBlank(errorCode = "000001", message = "支付宝账号")
    private String alipayAccount;
    
    /**证件类型*/
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind certificateKind;
    
    /**证件号码*/
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              certificateNo;
    
    /**真实姓名*/
    @NotNull(errorCode = "000001", message = "真实姓名")
    @NotBlank(errorCode = "000001", message = "真实姓名")
    private String realName;

    /**
     * Getter method for property <tt>authCheckCode</tt>.
     * 
     * @return property value of authCheckCode
     */
    public String getAuthCheckCode() {
        return authCheckCode;
    }

    /**
     * Setter method for property <tt>authCheckCode</tt>.
     * 
     * @param authCheckCode value to be assigned to property authCheckCode
     */
    public void setAuthCheckCode(String authCheckCode) {
        this.authCheckCode = authCheckCode;
    }

    /**
     * Getter method for property <tt>alipayAccount</tt>.
     * 
     * @return property value of alipayAccount
     */
    public String getAlipayAccount() {
        return alipayAccount;
    }

    /**
     * Setter method for property <tt>alipayAccount</tt>.
     * 
     * @param alipayAccount value to be assigned to property alipayAccount
     */
    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    /**
     * Getter method for property <tt>certificateKind</tt>.
     * 
     * @return property value of certificateKind
     */
    public EnumCertificateKind getCertificateKind() {
        return certificateKind;
    }

    /**
     * Setter method for property <tt>certificateKind</tt>.
     * 
     * @param certificateKind value to be assigned to property certificateKind
     */
    public void setCertificateKind(EnumCertificateKind certificateKind) {
        this.certificateKind = certificateKind;
    }

    /**
     * Getter method for property <tt>certificateNo</tt>.
     * 
     * @return property value of certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * Setter method for property <tt>certificateNo</tt>.
     * 
     * @param certificateNo value to be assigned to property certificateNo
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * Getter method for property <tt>realName</tt>.
     * 
     * @return property value of realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Setter method for property <tt>realName</tt>.
     * 
     * @param realName value to be assigned to property realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    
}
