/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumMerchantType;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserManageInfoResponse.java, v 1.0 2017年4月20日 下午7:09:45 jiangjd12837 Exp $
 */
public class QueryUserManageInfoPojo implements Serializable {

    /**  */
    private static final long   serialVersionUID = -7306291172433793997L;
    //客户ID
    private String              userId;
    //联系点ID
    private String              stationId;
    //资源项ID
    private String              manageResourceId;
    //资源项ID
    private String              certificateResourceId;
    //商户名称
    private String              merchantName;
    //商户类别
    private EnumMerchantType    merchantType;
    //证件类型
    private EnumCertificateKind certificateKind;
    //证件号码
    private String              certificateNo;
    //商户简称
    private String              merchantShortName;
    //省
    private String              provinceCode;
    //市
    private String              cityCode;
    //区
    private String              area;
    //全地址
    private String              address;
    //主营业务
    private String              mainBusiness;
    //兼营业务
    private String              byworkBusiness;
    //商户电话
    private String              merchantPhone;
    //年营业收入
    private String              annualOperatingRevenue;

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
     * Getter method for property <tt>stationId</tt>.
     * 
     * @return property value of stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Setter method for property <tt>stationId</tt>.
     * 
     * @param stationId value to be assigned to property stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * Getter method for property <tt>manageResourceId</tt>.
     * 
     * @return property value of manageResourceId
     */
    public String getManageResourceId() {
        return manageResourceId;
    }

    /**
     * Setter method for property <tt>manageResourceId</tt>.
     * 
     * @param manageResourceId value to be assigned to property manageResourceId
     */
    public void setManageResourceId(String manageResourceId) {
        this.manageResourceId = manageResourceId;
    }

    /**
     * Getter method for property <tt>certificateResourceId</tt>.
     * 
     * @return property value of certificateResourceId
     */
    public String getCertificateResourceId() {
        return certificateResourceId;
    }

    /**
     * Setter method for property <tt>certificateResourceId</tt>.
     * 
     * @param certificateResourceId value to be assigned to property certificateResourceId
     */
    public void setCertificateResourceId(String certificateResourceId) {
        this.certificateResourceId = certificateResourceId;
    }

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
     * Getter method for property <tt>merchantType</tt>.
     * 
     * @return property value of merchantType
     */
    public EnumMerchantType getMerchantType() {
        return merchantType;
    }

    /**
     * Setter method for property <tt>merchantType</tt>.
     * 
     * @param merchantType value to be assigned to property merchantType
     */
    public void setMerchantType(EnumMerchantType merchantType) {
        this.merchantType = merchantType;
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
     * Getter method for property <tt>provinceCode</tt>.
     * 
     * @return property value of provinceCode
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * Setter method for property <tt>provinceCode</tt>.
     * 
     * @param provinceCode value to be assigned to property provinceCode
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * Getter method for property <tt>cityCode</tt>.
     * 
     * @return property value of cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Setter method for property <tt>cityCode</tt>.
     * 
     * @param cityCode value to be assigned to property cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * Getter method for property <tt>area</tt>.
     * 
     * @return property value of area
     */
    public String getArea() {
        return area;
    }

    /**
     * Setter method for property <tt>area</tt>.
     * 
     * @param area value to be assigned to property area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Getter method for property <tt>address</tt>.
     * 
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     * 
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for property <tt>mainBusiness</tt>.
     * 
     * @return property value of mainBusiness
     */
    public String getMainBusiness() {
        return mainBusiness;
    }

    /**
     * Setter method for property <tt>mainBusiness</tt>.
     * 
     * @param mainBusiness value to be assigned to property mainBusiness
     */
    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    /**
     * Getter method for property <tt>byworkBusiness</tt>.
     * 
     * @return property value of byworkBusiness
     */
    public String getByworkBusiness() {
        return byworkBusiness;
    }

    /**
     * Setter method for property <tt>byworkBusiness</tt>.
     * 
     * @param byworkBusiness value to be assigned to property byworkBusiness
     */
    public void setByworkBusiness(String byworkBusiness) {
        this.byworkBusiness = byworkBusiness;
    }

    /**
     * Getter method for property <tt>merchantPhone</tt>.
     * 
     * @return property value of merchantPhone
     */
    public String getMerchantPhone() {
        return merchantPhone;
    }

    /**
     * Setter method for property <tt>merchantPhone</tt>.
     * 
     * @param merchantPhone value to be assigned to property merchantPhone
     */
    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    /**
     * Getter method for property <tt>annualOperatingRevenue</tt>.
     * 
     * @return property value of annualOperatingRevenue
     */
    public String getAnnualOperatingRevenue() {
        return annualOperatingRevenue;
    }

    /**
     * Setter method for property <tt>annualOperatingRevenue</tt>.
     * 
     * @param annualOperatingRevenue value to be assigned to property annualOperatingRevenue
     */
    public void setAnnualOperatingRevenue(String annualOperatingRevenue) {
        this.annualOperatingRevenue = annualOperatingRevenue;
    }

}
