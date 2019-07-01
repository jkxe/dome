/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * 
 * @author zhengqy15963
 * @version $Id: SelectOrInsertMerchantRequest.java, v 1.0 2018年4月21日 上午11:02:52 zhengqy15963 Exp $
 */
public class SelectOrInsertMerchantRequest implements Serializable{

    /**  */
    private static final long serialVersionUID = -1941875311686240494L;
    /** 经销商名称 */
    @NotNull(errorCode = "000001", message = "经销商名称")
    @NotBlank(errorCode = "000001", message = "经销商名称")
    private String merchantName;
    /** 经销商简称 */
    @NotNull(errorCode = "000001", message = "经销商简称")
    @NotBlank(errorCode = "000001", message = "经销商简称")
    private String merchantShortName;
    /** 证件类型 */
    private EnumCertificateKind certificateKind;
    /** 证件号码 */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String certificateNo;
    
    /**
     * Getter method for property <tt>merchantName</tt>.
     * 
     * @return property value of merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }
    /**
     * Setter method for property <tt>merchantName</tt>.
     * 
     * @param merchantName value to be assigned to property merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    /**
     * Getter method for property <tt>merchantShortName</tt>.
     * 
     * @return property value of merchantShortName
     */
    public String getMerchantShortName() {
        return merchantShortName;
    }
    /**
     * Setter method for property <tt>merchantShortName</tt>.
     * 
     * @param merchantShortName value to be assigned to property merchantShortName
     */
    public void setMerchantShortName(String merchantShortName) {
        this.merchantShortName = merchantShortName;
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
