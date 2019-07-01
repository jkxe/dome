/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumIdKind;
import com.hsjry.user.facade.pojo.enums.EnumPayBankacctType;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfCollectionSignRequest.java, v 1.0 2017年5月15日 下午3:54:43 jiangjd12837 Exp $
 */
public class YrhfCollectionUnsignPojo implements Serializable {

    /**  */
    private static final long   serialVersionUID = 7661391446546964130L;
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
     * C 银行卡/账户类型
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
    @NotNull(errorCode = "000001", message = "手机号码")
    @NotBlank(errorCode = "000001", message = "手机号码")
    private String              mobileTel;
    /**
     * C 扩展域
     */
    @NotNull(errorCode = "000001", message = "扩展域")
    private ExternInfoPojo      externInfo;

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
     * Getter method for property <tt>externInfo</tt>.
     * 
     * @return property value of externInfo
     */
    public ExternInfoPojo getExternInfo() {
        return externInfo;
    }

    /**
     * Setter method for property <tt>externInfo</tt>.
     * 
     * @param externInfo value to be assigned to property externInfo
     */
    public void setExternInfo(ExternInfoPojo externInfo) {
        this.externInfo = externInfo;
    }

}
