/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumIdentifyType;
import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

/**
 * 通过实名信息重置密码请求
 * @author huangbb
 * @version $Id: ResetPasswordWithCertInfoRequest.java, v 1.0 2017年6月22日 下午4:37:28 huangbb Exp $
 */
public class ResetPasswordWithCertInfoRequest implements Serializable {

    private static final long   serialVersionUID = -1431609969735745888L;

    /**客户名称*/
    private String              clientName;

    /**客户ID*/
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String              userId;

    /**证件类型*/
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind certificateKind;

    /**证件号码*/
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              certificateNo;

    /**识别类型*/
    @NotNull(errorCode = "000001", message = "识别类型")
    private EnumIdentifyType    identifyType;

    /**新密码*/
    @NotNull(errorCode = "000001", message = "新密码")
    @NotBlank(errorCode = "000001", message = "新密码")
    private String              identifyContent;

    /**识别用途*/
    @NotNull(errorCode = "000001", message = "识别用途")
    private EnumPurposeCode     purposeCode;

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
