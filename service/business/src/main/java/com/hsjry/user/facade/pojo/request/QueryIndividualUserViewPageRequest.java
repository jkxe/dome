/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumTelephoneClassCode;

import net.sf.oval.constraint.MatchPattern;

/**
 * 客户视图分页查询
 * @author jiangjd12837
 * @version $Id: QueryUserViewPageRequest.java, v 1.0 2017年3月30日 下午1:39:34 jiangjd12837 Exp $
 */
public class QueryIndividualUserViewPageRequest implements Serializable {

    /**  */
    private static final long      serialVersionUID = -7155938185948659760L;
    //客户名称
    private String                 clientName;
    //证件号码
    private String                 idNo;
    //证件项类型
    private EnumCertificateKind    certificateKind;
    //联系点类型
    private EnumTelephoneClassCode telephoneClassCode;
    //手机号
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String                 mobileTel;
    //实名标志
    private EnumBool               realNameFlag;
    //渠道编号
    private String                 channelCode;

    //开始日期
    private Date                   startDate;
    //结束日期
    private Date                   endDate;

    /**
     * Getter method for property <tt>startDate</tt>.
     * 
     * @return property value of startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter method for property <tt>startDate</tt>.
     * 
     * @param startDate value to be assigned to property startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter method for property <tt>endDate</tt>.
     * 
     * @return property value of endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter method for property <tt>endDate</tt>.
     * 
     * @param endDate value to be assigned to property endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * Getter method for property <tt>realNameFlag</tt>.
     * 
     * @return property value of realNameFlag
     */
    public EnumBool getRealNameFlag() {
        return realNameFlag;
    }

    /**
     * Setter method for property <tt>realNameFlag</tt>.
     * 
     * @param realNameFlag value to be assigned to property realNameFlag
     */
    public void setRealNameFlag(EnumBool realNameFlag) {
        this.realNameFlag = realNameFlag;
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
     * Getter method for property <tt>telephoneClassCode</tt>.
     * 
     * @return property value of telephoneClassCode
     */
    public EnumTelephoneClassCode getTelephoneClassCode() {
        return telephoneClassCode;
    }

    /**
     * Setter method for property <tt>telephoneClassCode</tt>.
     * 
     * @param telephoneClassCode value to be assigned to property telephoneClassCode
     */
    public void setTelephoneClassCode(EnumTelephoneClassCode telephoneClassCode) {
        this.telephoneClassCode = telephoneClassCode;
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

}
