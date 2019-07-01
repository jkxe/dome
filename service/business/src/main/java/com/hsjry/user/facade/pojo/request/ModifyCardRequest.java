/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ModifyCardRequest.java, v 1.0 2017年6月8日 下午8:54:50 jiangjd12837 Exp $
 */
public class ModifyCardRequest implements Serializable {

    /**  */
    private static final long   serialVersionUID = -8174528438751245937L;
    //姓名
    @NotNull(errorCode = "000001", message = "姓名")
    @NotBlank(errorCode = "000001", message = "姓名")
    private String              realName;
    //编号
    @NotNull(errorCode = "000001", message = "证件编号")
    @NotBlank(errorCode = "000001", message = "证件编号")
    private String              certificateNo;
    //证件项类型
    @NotNull(errorCode = "000001", message = "证件项类型")
    private EnumCertificateKind certificateKind;
    //新银行卡号
    @NotNull(errorCode = "000001", message = "新银行卡号")
    @NotBlank(errorCode = "000001", message = "新银行卡号")
    private String              newAccount;
    // 原银行卡号 
    @NotNull(errorCode = "000001", message = "原银行卡号 ")
    @NotBlank(errorCode = "000001", message = "原银行卡号 ")
    private String              oldAccount;

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
     * Getter method for property <tt>newAccount</tt>.
     * 
     * @return property value of newAccount
     */
    public String getNewAccount() {
        return newAccount;
    }

    /**
     * Setter method for property <tt>newAccount</tt>.
     * 
     * @param newAccount value to be assigned to property newAccount
     */
    public void setNewAccount(String newAccount) {
        this.newAccount = newAccount;
    }

    /**
     * Getter method for property <tt>oldAccount</tt>.
     * 
     * @return property value of oldAccount
     */
    public String getOldAccount() {
        return oldAccount;
    }

    /**
     * Setter method for property <tt>oldAccount</tt>.
     * 
     * @param oldAccount value to be assigned to property oldAccount
     */
    public void setOldAccount(String oldAccount) {
        this.oldAccount = oldAccount;
    }

}
