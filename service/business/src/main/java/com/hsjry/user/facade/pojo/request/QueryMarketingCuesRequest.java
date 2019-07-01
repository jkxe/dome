/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.lang.common.base.enums.user.EnumUserType;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMarketingCuesRequest.java, v 1.0 2017年4月4日 下午3:52:19 jiangjd12837 Exp $
 */
public class QueryMarketingCuesRequest implements Serializable {

    /**  */
    private static final long   serialVersionUID = 5847638396891939825L;
    //客户名称
    private String              clientName;
    //客户类型
    @NotNull(errorCode = "000001", message = "客户类型")
    private EnumUserType        clientCategory;
    //渠道编号
    private String              channelCode;
    //编号
    private String              certificateNo;
    //证件项类型
    private EnumCertificateKind certificateKind;
    //手机号码
    private String              telephone;

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
     * Getter method for property <tt>clientCategory</tt>.
     * 
     * @return property value of clientCategory
     */
    public EnumUserType getClientCategory() {
        return clientCategory;
    }

    /**
     * Setter method for property <tt>clientCategory</tt>.
     * 
     * @param clientCategory value to be assigned to property clientCategory
     */
    public void setClientCategory(EnumUserType clientCategory) {
        this.clientCategory = clientCategory;
    }

    /**
     * Getter method for property <tt>channelCode</tt>.
     * 
     * @return property value of channelCode
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * Setter method for property <tt>channelCode</tt>.
     * 
     * @param channelCode value to be assigned to property channelCode
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

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

}
