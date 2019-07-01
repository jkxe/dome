/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumGender;
import com.hsjry.user.facade.pojo.enums.EnumMarriageStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: LegalInfoPojo.java, v 1.0 2017年5月17日 下午3:06:09 jiangjd12837 Exp $
 */
public class LegalInfoPojo implements Serializable {

    /**  */
    private static final long   serialVersionUID = -6035322978765290154L;
    //姓名
    private String              clientName;
    //编号
    private String              certificateNo;
    //证件项类型
    private EnumCertificateKind certificateKind;
    //性别
    private EnumGender          sex;
    //婚姻状况
    private EnumMarriageStatus  marriageStatus;
    //电话号
    private String              telephone;

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
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public EnumGender getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(EnumGender sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>marriageStatus</tt>.
     * 
     * @return property value of marriageStatus
     */
    public EnumMarriageStatus getMarriageStatus() {
        return marriageStatus;
    }

    /**
     * Setter method for property <tt>marriageStatus</tt>.
     * 
     * @param marriageStatus value to be assigned to property marriageStatus
     */
    public void setMarriageStatus(EnumMarriageStatus marriageStatus) {
        this.marriageStatus = marriageStatus;
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

}
