/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumIndustryType;
import com.hsjry.user.facade.pojo.enums.EnumMerchantUserType;
import com.hsjry.user.facade.pojo.enums.EnumOrganType;
import com.hsjry.user.facade.pojo.enums.EnumRecordKind;
import com.hsjry.user.facade.pojo.enums.EnumShareHolderType;
import com.hsjry.user.facade.pojo.enums.EnumSurvivalStatus;

/**
 * 
 * @author zhengqy15963
 * @version $Id: MerchantBasicInfoPojo.java, v 1.0 2018年4月23日 上午9:31:06 zhengqy15963 Exp $
 */
public class MerchantBasicInfoPojo implements Serializable {

    /**  */
    private static final long    serialVersionUID = 2485535660262194700L;
    /** 经销商编号 */
    @NotNull(errorCode = "000001", message = "经销商编号")
    @NotBlank(errorCode = "000001", message = "经销商编号")
    private String               userId;
    /** 经销商名称 */
    @NotNull(errorCode = "000001", message = "经销商名称")
    @NotBlank(errorCode = "000001", message = "经销商名称")
    private String               organName;
    /** 经销商简称 */
    @NotNull(errorCode = "000001", message = "经销商简称")
    @NotBlank(errorCode = "000001", message = "经销商简称")
    private String               shortName;
    /** 企业类别 */
    private EnumOrganType        organType;
    /**证件类型 */
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind  certificateKind;
    /**证件号码 */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String               certificateNo;
    /**经销商电话 */
    @NotBlank(errorCode = "000001", message = "经销商电话")
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "经销商电话")
    private String               merchantPhone;
    /**经销商分类 */
    @NotNull(errorCode = "000001", message = "经销商分类")
    private EnumMerchantUserType merchantUserType;
    /**存续状态 */
    private EnumSurvivalStatus   survivalStatus;
    /**发证日期 */
    private Date                 publishDate;
    /**年检日期 */
    private Date                 certificateCheckValidDate;
    /**失效日期 */
    private Date                 invalidDate;
    /**行业类别 */
    @NotNull(errorCode = "000001", message = "行业类别")
    private EnumIndustryType     industryType;
    /**经销商股东性质 */
    @NotNull(errorCode = "000001", message = "经销商股东性质")
    private EnumShareHolderType  shareholdersNature;
    /**注册登记日期 */
    private Date                 regdate;
    /**注册登记类型 */
    private EnumRecordKind       recordKind;
    /**注册资金 */
    @NotBlank(errorCode = "000001", message = "注册资金")
    private String               registerFund;
    /**年营业额 */
    @NotNull(errorCode = "000001", message = "年营业额")
    @NotBlank(errorCode = "000001", message = "年营业额")
    private String               annualOperatingRevenue;
    /**所在地域 */
    @NotNull(errorCode = "000001", message = "所在地域")
    @NotBlank(errorCode = "000001", message = "所在地域")
    private String               merchantArea;
    /**省 */
    @NotNull(errorCode = "000001", message = "省")
    @NotBlank(errorCode = "000001", message = "省")
    private String               provinceCode;
    /**市*/
    @NotNull(errorCode = "000001", message = "市")
    @NotBlank(errorCode = "000001", message = "市")
    private String               cityCode;
    /**区*/
    @NotNull(errorCode = "000001", message = "区")
    @NotBlank(errorCode = "000001", message = "区")
    private String               area;
    /**经营地址 */
    @NotNull(errorCode = "000001", message = "经营地址")
    @NotBlank(errorCode = "000001", message = "经营地址")
    private String               address;
    /**法定代表人ID */
    @NotNull(errorCode = "000001", message = "法定代表人id ")
    @NotBlank(errorCode = "000001", message = "法定代表人id ")
    private String               relationUserId;
    /**法定代表人名称 */
    @NotNull(errorCode = "000001", message = "法定代表人名称 ")
    @NotBlank(errorCode = "000001", message = "法定代表人名称 ")
    private String               relationUserName;
    /**法定代表人证件类型 */
    private EnumCertificateKind  relationIdKind;
    /**法定代表人证件号码 */
    @NotBlank(errorCode = "000001", message = "法定代表人证件号码 ")
    private String               relationIdNo;
    /**法定代表人手机号 */
    @NotBlank(errorCode = "000001", message = "法定代表人手机号")
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "法定代表人手机号")
    private String               telephone;
    /**授权代理人客户id*/
    private String               liableUserId;
    /**授权代理人姓名 */
    @NotNull(errorCode = "000001", message = "授权代理人姓名 ")
    @NotBlank(errorCode = "000001", message = "授权代理人姓名")
    private String               authorizedName;
    /**代理人联系电话 */
    @NotNull(errorCode = "000001", message = "代理人联系电话  ")
    @NotBlank(errorCode = "000001", message = "代理人联系电话 ")
    private String               authorizedTel;
    /**信用评级*/
    @NotNull(errorCode = "000001", message = "信用评级 ")
    @NotBlank(errorCode = "000001", message = "信用评级")
    private String               securedCreditLevel;
    /**背景状况*/
    @NotNull(errorCode = "000001", message = "背景状况 ")
    @NotBlank(errorCode = "000001", message = "背景状况")
    private String               merchantBackground;
    /**经营分析*/
    @NotNull(errorCode = "000001", message = "经营分析 ")
    @NotBlank(errorCode = "000001", message = "经营分析")
    private String               businessAnalysis;
    /**财务分析*/
    @NotNull(errorCode = "000001", message = "财务分析 ")
    @NotBlank(errorCode = "000001", message = "财务分析")
    private String               financialAnalysis;
    /**风控措施*/
    @NotNull(errorCode = "000001", message = "风控措施 ")
    @NotBlank(errorCode = "000001", message = "风控措施")
    private String               riskManagement;

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
     * Getter method for property <tt>organType</tt>.
     * 
     * @return property value of organType
     */
    public EnumOrganType getOrganType() {
        return organType;
    }

    /**
     * Setter method for property <tt>organType</tt>.
     * 
     * @param organType value to be assigned to property organType
     */
    public void setOrganType(EnumOrganType organType) {
        this.organType = organType;
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
     * Getter method for property <tt>merchantUserType</tt>.
     * 
     * @return property value of merchantUserType
     */
    public EnumMerchantUserType getMerchantUserType() {
        return merchantUserType;
    }

    /**
     * Setter method for property <tt>merchantUserType</tt>.
     * 
     * @param merchantUserType value to be assigned to property merchantUserType
     */
    public void setMerchantUserType(EnumMerchantUserType merchantUserType) {
        this.merchantUserType = merchantUserType;
    }

    /**
     * Getter method for property <tt>survivalStatus</tt>.
     * 
     * @return property value of survivalStatus
     */
    public EnumSurvivalStatus getSurvivalStatus() {
        return survivalStatus;
    }

    /**
     * Setter method for property <tt>survivalStatus</tt>.
     * 
     * @param survivalStatus value to be assigned to property survivalStatus
     */
    public void setSurvivalStatus(EnumSurvivalStatus survivalStatus) {
        this.survivalStatus = survivalStatus;
    }

    /**
     * Getter method for property <tt>publishDate</tt>.
     * 
     * @return property value of publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Setter method for property <tt>publishDate</tt>.
     * 
     * @param publishDate value to be assigned to property publishDate
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Getter method for property <tt>certificateCheckValidDate</tt>.
     * 
     * @return property value of certificateCheckValidDate
     */
    public Date getCertificateCheckValidDate() {
        return certificateCheckValidDate;
    }

    /**
     * Setter method for property <tt>certificateCheckValidDate</tt>.
     * 
     * @param certificateCheckValidDate value to be assigned to property certificateCheckValidDate
     */
    public void setCertificateCheckValidDate(Date certificateCheckValidDate) {
        this.certificateCheckValidDate = certificateCheckValidDate;
    }

    /**
     * Getter method for property <tt>invalidDate</tt>.
     * 
     * @return property value of invalidDate
     */
    public Date getInvalidDate() {
        return invalidDate;
    }

    /**
     * Setter method for property <tt>invalidDate</tt>.
     * 
     * @param invalidDate value to be assigned to property invalidDate
     */
    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    /**
     * Getter method for property <tt>industryType</tt>.
     * 
     * @return property value of industryType
     */
    public EnumIndustryType getIndustryType() {
        return industryType;
    }

    /**
     * Setter method for property <tt>industryType</tt>.
     * 
     * @param industryType value to be assigned to property industryType
     */
    public void setIndustryType(EnumIndustryType industryType) {
        this.industryType = industryType;
    }

    /**
     * Getter method for property <tt>shareholdersNature</tt>.
     * 
     * @return property value of shareholdersNature
     */
    public EnumShareHolderType getShareholdersNature() {
        return shareholdersNature;
    }

    /**
     * Setter method for property <tt>shareholdersNature</tt>.
     * 
     * @param shareholdersNature value to be assigned to property shareholdersNature
     */
    public void setShareholdersNature(EnumShareHolderType shareholdersNature) {
        this.shareholdersNature = shareholdersNature;
    }

    /**
     * Getter method for property <tt>regdate</tt>.
     * 
     * @return property value of regdate
     */
    public Date getRegdate() {
        return regdate;
    }

    /**
     * Setter method for property <tt>regdate</tt>.
     * 
     * @param regdate value to be assigned to property regdate
     */
    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    /**
     * Getter method for property <tt>recordKind</tt>.
     * 
     * @return property value of recordKind
     */
    public EnumRecordKind getRecordKind() {
        return recordKind;
    }

    /**
     * Setter method for property <tt>recordKind</tt>.
     * 
     * @param recordKind value to be assigned to property recordKind
     */
    public void setRecordKind(EnumRecordKind recordKind) {
        this.recordKind = recordKind;
    }

    /**
     * Getter method for property <tt>registerFund</tt>.
     * 
     * @return property value of registerFund
     */
    public String getRegisterFund() {
        return registerFund;
    }

    /**
     * Setter method for property <tt>registerFund</tt>.
     * 
     * @param registerFund value to be assigned to property registerFund
     */
    public void setRegisterFund(String registerFund) {
        this.registerFund = registerFund;
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

    /**
     * Getter method for property <tt>merchantArea</tt>.
     * 
     * @return property value of merchantArea
     */
    public String getMerchantArea() {
        return merchantArea;
    }

    /**
     * Setter method for property <tt>merchantArea</tt>.
     * 
     * @param merchantArea value to be assigned to property merchantArea
     */
    public void setMerchantArea(String merchantArea) {
        this.merchantArea = merchantArea;
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
     * Getter method for property <tt>relationUserId</tt>.
     * 
     * @return property value of relationUserId
     */
    public String getRelationUserId() {
        return relationUserId;
    }

    /**
     * Setter method for property <tt>relationUserId</tt>.
     * 
     * @param relationUserId value to be assigned to property relationUserId
     */
    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    /**
     * Getter method for property <tt>relationUserName</tt>.
     * 
     * @return property value of relationUserName
     */
    public String getRelationUserName() {
        return relationUserName;
    }

    /**
     * Setter method for property <tt>relationUserName</tt>.
     * 
     * @param relationUserName value to be assigned to property relationUserName
     */
    public void setRelationUserName(String relationUserName) {
        this.relationUserName = relationUserName;
    }

    /**
     * Getter method for property <tt>relationIdKind</tt>.
     * 
     * @return property value of relationIdKind
     */
    public EnumCertificateKind getRelationIdKind() {
        return relationIdKind;
    }

    /**
     * Setter method for property <tt>relationIdKind</tt>.
     * 
     * @param relationIdKind value to be assigned to property relationIdKind
     */
    public void setRelationIdKind(EnumCertificateKind relationIdKind) {
        this.relationIdKind = relationIdKind;
    }

    /**
     * Getter method for property <tt>relationIdNo</tt>.
     * 
     * @return property value of relationIdNo
     */
    public String getRelationIdNo() {
        return relationIdNo;
    }

    /**
     * Setter method for property <tt>relationIdNo</tt>.
     * 
     * @param relationIdNo value to be assigned to property relationIdNo
     */
    public void setRelationIdNo(String relationIdNo) {
        this.relationIdNo = relationIdNo;
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

    /**
     * Getter method for property <tt>authorizedName</tt>.
     * 
     * @return property value of authorizedName
     */
    public String getAuthorizedName() {
        return authorizedName;
    }

    /**
     * Setter method for property <tt>authorizedName</tt>.
     * 
     * @param authorizedName value to be assigned to property authorizedName
     */
    public void setAuthorizedName(String authorizedName) {
        this.authorizedName = authorizedName;
    }

    /**
     * Getter method for property <tt>authorizedTel</tt>.
     * 
     * @return property value of authorizedTel
     */
    public String getAuthorizedTel() {
        return authorizedTel;
    }

    /**
     * Setter method for property <tt>authorizedTel</tt>.
     * 
     * @param authorizedTel value to be assigned to property authorizedTel
     */
    public void setAuthorizedTel(String authorizedTel) {
        this.authorizedTel = authorizedTel;
    }

    /**
     * Getter method for property <tt>securedCreditLevel</tt>.
     * 
     * @return property value of securedCreditLevel
     */
    public String getSecuredCreditLevel() {
        return securedCreditLevel;
    }

    /**
     * Setter method for property <tt>securedCreditLevel</tt>.
     * 
     * @param securedCreditLevel value to be assigned to property securedCreditLevel
     */
    public void setSecuredCreditLevel(String securedCreditLevel) {
        this.securedCreditLevel = securedCreditLevel;
    }

    /**
     * Getter method for property <tt>merchantBackground</tt>.
     * 
     * @return property value of merchantBackground
     */
    public String getMerchantBackground() {
        return merchantBackground;
    }

    /**
     * Setter method for property <tt>merchantBackground</tt>.
     * 
     * @param merchantBackground value to be assigned to property merchantBackground
     */
    public void setMerchantBackground(String merchantBackground) {
        this.merchantBackground = merchantBackground;
    }

    /**
     * Getter method for property <tt>businessAnalysis</tt>.
     * 
     * @return property value of businessAnalysis
     */
    public String getBusinessAnalysis() {
        return businessAnalysis;
    }

    /**
     * Setter method for property <tt>businessAnalysis</tt>.
     * 
     * @param businessAnalysis value to be assigned to property businessAnalysis
     */
    public void setBusinessAnalysis(String businessAnalysis) {
        this.businessAnalysis = businessAnalysis;
    }

    /**
     * Getter method for property <tt>financialAnalysis</tt>.
     * 
     * @return property value of financialAnalysis
     */
    public String getFinancialAnalysis() {
        return financialAnalysis;
    }

    /**
     * Setter method for property <tt>financialAnalysis</tt>.
     * 
     * @param financialAnalysis value to be assigned to property financialAnalysis
     */
    public void setFinancialAnalysis(String financialAnalysis) {
        this.financialAnalysis = financialAnalysis;
    }

    /**
     * Getter method for property <tt>riskManagement</tt>.
     * 
     * @return property value of riskManagement
     */
    public String getRiskManagement() {
        return riskManagement;
    }

    /**
     * Setter method for property <tt>riskManagement</tt>.
     * 
     * @param riskManagement value to be assigned to property riskManagement
     */
    public void setRiskManagement(String riskManagement) {
        this.riskManagement = riskManagement;
    }

    /**
     * Getter method for property <tt>liableUserId</tt>.
     * 
     * @return property value of liableUserId
     */
    public String getLiableUserId() {
        return liableUserId;
    }

    /**
     * Setter method for property <tt>liableUserId</tt>.
     * 
     * @param liableUserId value to be assigned to property liableUserId
     */
    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
    }

}
