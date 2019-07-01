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
 * 修改主卡信息
 * @author huangbb
 * @version $Id: ChangeMainCardInfoRequest.java, v 0.1 2017年7月13日 下午5:13:53 huangbb Exp $
 */
public class ChangeMainCardInfoRequest implements Serializable {

    private static final long   serialVersionUID = 7621111125890734179L;

    /** 客户ID */
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String              userId;
    /**预留手机号*/
    @NotNull(errorCode = "000001", message = "预留手机号")
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "预留手机号")
    @NotBlank(errorCode = "000001", message = "预留手机号")
    private String              telephone;
    /**校验码EnumSmsType.BIND_CARD*/
    @NotNull(errorCode = "000001", message = "校验码")
    @Length(min = 6, max = 6, errorCode = "000002", message = "校验码")
    @MatchPattern(matchAll = false, pattern = { "^\\d{6}$" }, errorCode = "000003", message = "校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    private String              authCheckCode;
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
    private String              realName;
    /**银行卡号*/
    @NotNull(errorCode = "000001", message = "银行卡号")
    @NotBlank(errorCode = "000001", message = "银行卡号")
    private String              account;

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

    /**
     * Getter method for property <tt>account</tt>.
     * 
     * @return property value of account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter method for property <tt>account</tt>.
     * 
     * @param account value to be assigned to property account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
