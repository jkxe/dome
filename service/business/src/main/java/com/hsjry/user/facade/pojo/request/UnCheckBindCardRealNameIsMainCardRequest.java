/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.UserBankCardInfoPojo;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: UnCheckBindCardRealName.java, v 1.0 2017年6月9日 下午3:14:40 jiangjd12837 Exp $
 */
public class UnCheckBindCardRealNameIsMainCardRequest implements  Serializable{


    /**  */
    private static final long serialVersionUID = 9091255156326037257L;
    /**预留手机号*/
    @NotNull(errorCode = "000001", message = "预留手机号")
    @NotBlank(errorCode = "000001", message = "预留手机号")
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "预留手机号")
    private String               reserveTelephone;
    /**注册手机号*/
    @NotNull(errorCode = "000001", message = "注册手机号")
    @NotBlank(errorCode = "000001", message = "注册手机号")
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "注册手机号")
    private String               telephone;
    /**银行卡信息*/
    @AssertValid(errorCode = "000001", message = "银行卡信息")
    private UserBankCardInfoPojo userBankCardInfoPojo;

    /**证件类型*/
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind  certificateKind;

    /**证件号码*/
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String               certificateNo;

    /**真实姓名*/
    @NotNull(errorCode = "000001", message = "真实姓名")
    @NotBlank(errorCode = "000001", message = "真实姓名")
    private String               realName;
    /**标识位，确认是否走新逻辑(将新卡设置为副卡，主卡不动),如果传YES，则走老逻辑，如果NO，走新逻辑*/
    @NotNull(errorCode = "000001", message = "是否设置为主卡")
    private EnumBool isMainCard;


    public EnumBool getIsMainCard() {
        return isMainCard;
    }

    public void setIsMainCard(EnumBool isMainCard) {
        this.isMainCard = isMainCard;
    }

    /**
     * Getter method for property <tt>reserveTelephone</tt>.
     * 
     * @return property value of reserveTelephone
     */
    public String getReserveTelephone() {
        return reserveTelephone;
    }

    /**
     * Setter method for property <tt>reserveTelephone</tt>.
     * 
     * @param reserveTelephone value to be assigned to property reserveTelephone
     */
    public void setReserveTelephone(String reserveTelephone) {
        this.reserveTelephone = reserveTelephone;
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
     * Getter method for property <tt>userBankCardInfoPojo</tt>.
     * 
     * @return property value of userBankCardInfoPojo
     */
    public UserBankCardInfoPojo getUserBankCardInfoPojo() {
        return userBankCardInfoPojo;
    }

    /**
     * Setter method for property <tt>userBankCardInfoPojo</tt>.
     * 
     * @param userBankCardInfoPojo value to be assigned to property userBankCardInfoPojo
     */
    public void setUserBankCardInfoPojo(UserBankCardInfoPojo userBankCardInfoPojo) {
        this.userBankCardInfoPojo = userBankCardInfoPojo;
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
