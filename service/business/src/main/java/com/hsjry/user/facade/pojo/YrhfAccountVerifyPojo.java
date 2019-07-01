/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumFuncCode;
import com.hsjry.user.facade.pojo.enums.EnumIdKind;
import com.hsjry.user.facade.pojo.enums.EnumPayBankacctType;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfCertVerifyRequest.java, v 1.0 2017年5月15日 下午3:19:39 jiangjd12837 Exp $
 */
public class YrhfAccountVerifyPojo implements Serializable {
    /**  */
    private static final long   serialVersionUID = 5967964018910557309L;
    /**
     * C 客户姓名
     */
    @NotNull(errorCode = "000001", message = "客户姓名")
    @NotBlank(errorCode = "000001", message = "客户姓名")
    private String              clientName;
    /**
     * C 银行卡号/支付宝账户
     */
    @NotNull(errorCode = "000001", message = "银行卡号/支付宝账户")
    @NotBlank(errorCode = "000001", message = "银行卡号/支付宝账户")
    private String              cardNo;
    /**
     * C cvv2码 信用卡填
     */
    private String              cvv2;
    /**
     * C 有效日期 信用卡填
     */
    private String              validDate;
    /**
     * C  银行卡/账户类型
     */
    @NotNull(errorCode = "000001", message = "银行卡/账户类型")
    private EnumPayBankacctType payBankacctType;
    /**
     * C 证件类型
     */
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumIdKind idKind;
    /**
     * C 证件号码
     */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              idNo;
    /**
     * C 手机号码
     */
    private String              mobileTel;
    /**
     * C 功能编码
     */
    private EnumFuncCode        funcCode;

    private String userId;//用户编号
    private String code;//验证码
    private EnumBool enumBooll;//是否杭银绑卡
    private String bankId;//行号
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
     * Getter method for property <tt>cardNo</tt>.
     * 
     * @return property value of cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Setter method for property <tt>cardNo</tt>.
     * 
     * @param cardNo value to be assigned to property cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * Getter method for property <tt>cvv2</tt>.
     * 
     * @return property value of cvv2
     */
    public String getCvv2() {
        return cvv2;
    }

    /**
     * Setter method for property <tt>cvv2</tt>.
     * 
     * @param cvv2 value to be assigned to property cvv2
     */
    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    /**
     * Getter method for property <tt>validDate</tt>.
     * 
     * @return property value of validDate
     */
    public String getValidDate() {
        return validDate;
    }

    /**
     * Setter method for property <tt>validDate</tt>.
     * 
     * @param validDate value to be assigned to property validDate
     */
    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    /**
     * Getter method for property <tt>payBankacctType</tt>.
     * 
     * @return property value of payBankacctType
     */
    public EnumPayBankacctType getPayBankacctType() {
        return payBankacctType;
    }

    /**
     * Setter method for property <tt>payBankacctType</tt>.
     * 
     * @param payBankacctType value to be assigned to property payBankacctType
     */
    public void setPayBankacctType(EnumPayBankacctType payBankacctType) {
        this.payBankacctType = payBankacctType;
    }

    
    /**
     * Getter method for property <tt>idKind</tt>.
     * 
     * @return property value of idKind
     */
    public EnumIdKind getIdKind() {
        return idKind;
    }

    /**
     * Setter method for property <tt>idKind</tt>.
     * 
     * @param idKind value to be assigned to property idKind
     */
    public void setIdKind(EnumIdKind idKind) {
        this.idKind = idKind;
    }

    /**
     * Getter method for property <tt>idNo</tt>.
     * 
     * @return property value of idNo
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Setter method for property <tt>idNo</tt>.
     * 
     * @param idNo value to be assigned to property idNo
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * Getter method for property <tt>mobileTel</tt>.
     * 
     * @return property value of mobileTel
     */
    public String getMobileTel() {
        return mobileTel;
    }

    /**
     * Setter method for property <tt>mobileTel</tt>.
     * 
     * @param mobileTel value to be assigned to property mobileTel
     */
    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    /**
     * Getter method for property <tt>funcCode</tt>.
     * 
     * @return property value of funcCode
     */
    public EnumFuncCode getFuncCode() {
        return funcCode;
    }

    /**
     * Setter method for property <tt>funcCode</tt>.
     * 
     * @param funcCode value to be assigned to property funcCode
     */
    public void setFuncCode(EnumFuncCode funcCode) {
        this.funcCode = funcCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EnumBool getEnumBooll() {
        return enumBooll;
    }

    public void setEnumBooll(EnumBool enumBooll) {
        this.enumBooll = enumBooll;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
