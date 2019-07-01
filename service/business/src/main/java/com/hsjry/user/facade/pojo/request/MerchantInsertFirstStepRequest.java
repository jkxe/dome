/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author zhengqy15963
 * @version $Id: MerchantInsertStepRequest.java, v 1.0 2018年4月25日 下午7:24:09 zhengqy15963 Exp $
 */
public class MerchantInsertFirstStepRequest implements Serializable{

    /**  */
    private static final long serialVersionUID = -1235389990418682638L;
    
    /** 经销商名称 */
    @NotNull(errorCode = "000001", message = "经销商名称")
    @NotBlank(errorCode = "000001", message = "经销商名称")
    private String               organName;
    /** 经销商简称 */
    @NotNull(errorCode = "000001", message = "经销商简称")
    @NotBlank(errorCode = "000001", message = "经销商简称")
    private String                shortName;
    /**证件类型 */
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind  certificateKind;
    /**证件号码 */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String               certificateNo;
    /**
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }
    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }
    /**
     * Getter method for property <tt>shortName</tt>.
     * 
     * @return property value of shortName
     */
    public String getShortName() {
        return shortName;
    }
    /**
     * Setter method for property <tt>shortName</tt>.
     * 
     * @param shortName value to be assigned to property shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
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
    
    
}
