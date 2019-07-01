/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumGender;
import com.hsjry.user.facade.pojo.enums.EnumMarriageStatus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author zhengqy15963
 * @version $Id: UserLegalPersonalInfoPojo.java, v 1.0 2018年4月3日 下午3:35:46 zhengqy15963 Exp $
 */
public class UserLegalPersonalInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 4400265018132076631L;
    /**法人代表姓名  */
    private String            legalName;
    @NotNull(errorCode = "000001", message = "法人代表证件类型")
    @NotBlank(errorCode = "000001", message = "法人代表证件类型")
    /**法人代表证件类型  */
    private EnumCertificateKind            legalCertificateType;
    @NotNull(errorCode = "000001", message = "法人代表证件号码")
    @NotBlank(errorCode = "000001", message = "法人代表证件号码")
    /**法人代表证件号码  */
    private String            legalCertificateNo;
    /**法人代表性别  */
    private EnumGender              legalSex;
    /**法人代表婚姻状况  */
    private EnumMarriageStatus            legalMarriage;
    /**法人代表手机号码  */
    @NotNull(errorCode = "000001", message = "法人代表手机号码")
    @NotBlank(errorCode = "000001", message = "法人代表手机号码")
    private String            legalTelephone;

    /**
     * Getter method for property <tt>legalName</tt>.
     * 
     * @return property value of legalName
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * Setter method for property <tt>legalName</tt>.
     * 
     * @param legalName value to be assigned to property legalName
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

   

    /**
     * Getter method for property <tt>legalCertificateType</tt>.
     * 
     * @return property value of legalCertificateType
     */
    public EnumCertificateKind getLegalCertificateType() {
        return legalCertificateType;
    }

    /**
     * Setter method for property <tt>legalCertificateType</tt>.
     * 
     * @param legalCertificateType value to be assigned to property legalCertificateType
     */
    public void setLegalCertificateType(EnumCertificateKind legalCertificateType) {
        this.legalCertificateType = legalCertificateType;
    }

    /**
     * Getter method for property <tt>legalCertificateNo</tt>.
     * 
     * @return property value of legalCertificateNo
     */
    public String getLegalCertificateNo() {
        return legalCertificateNo;
    }

    /**
     * Setter method for property <tt>legalCertificateNo</tt>.
     * 
     * @param legalCertificateNo value to be assigned to property legalCertificateNo
     */
    public void setLegalCertificateNo(String legalCertificateNo) {
        this.legalCertificateNo = legalCertificateNo;
    }

  

    /**
     * Getter method for property <tt>legalSex</tt>.
     * 
     * @return property value of legalSex
     */
    public EnumGender getLegalSex() {
        return legalSex;
    }

    /**
     * Setter method for property <tt>legalSex</tt>.
     * 
     * @param legalSex value to be assigned to property legalSex
     */
    public void setLegalSex(EnumGender legalSex) {
        this.legalSex = legalSex;
    }

    /**
     * Getter method for property <tt>legalMarriage</tt>.
     * 
     * @return property value of legalMarriage
     */
    public EnumMarriageStatus getLegalMarriage() {
        return legalMarriage;
    }

    /**
     * Setter method for property <tt>legalMarriage</tt>.
     * 
     * @param legalMarriage value to be assigned to property legalMarriage
     */
    public void setLegalMarriage(EnumMarriageStatus legalMarriage) {
        this.legalMarriage = legalMarriage;
    }

    /**
     * Getter method for property <tt>legalTelephone</tt>.
     * 
     * @return property value of legalTelephone
     */
    public String getLegalTelephone() {
        return legalTelephone;
    }

    /**
     * Setter method for property <tt>legalTelephone</tt>.
     * 
     * @param legalTelephone value to be assigned to property legalTelephone
     */
    public void setLegalTelephone(String legalTelephone) {
        this.legalTelephone = legalTelephone;
    }

}
