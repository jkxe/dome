package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumIndustryLabel;
import com.hsjry.user.facade.pojo.enums.EnumIndustryType;
import com.hsjry.user.facade.pojo.enums.EnumMerchantType;
import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * 经营信息
 * @author liaosq23298
 * @version $Id: UserManageInfoandSourcePojo.java, v 0.1 Nov 22, 2017 3:58:49 PM liaosq23298 Exp $
 */
public class UserManageInfoandSourcePojo implements Serializable{
    /**  */
    private static final long serialVersionUID = -4959510702843615759L;
    //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //商户头像
    private String            merchantHeadImg;
    //商户名称
    private String            merchantName;
    //商户简称
    private String            merchantShortName;
    //商户类别
    private EnumMerchantType  merchantType;
    //商户电话
    private String            merchantPhone;
    //行业类别
    private EnumIndustryType  industryType;
    //行业标签
    private EnumIndustryLabel industryLabel;
    //主营业务范围
    private String            mainBusiness;
    //兼营业务范围
    private String            byworkBusiness;
    //主营商品
    private String            mainCommodity;
    //年营业收入
    private String            annualOperatingRevenue;
    //公积金账号
    private String            providentFundAccount;
    //授权代理人
    private String            authorizedAgent;
    //电费账号
    private String            electricityAccount;
    //水费总户号
    private String            waterAccount;
    //环保公司注册号
    private String            environmentalRegistrationNo;
    //资源来源
    private EnumResourceSource resourceSource;

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
     * Getter method for property <tt>industryLabel</tt>.
     * 
     * @return property value of industryLabel
     */
    public EnumIndustryLabel getIndustryLabel() {
        return industryLabel;
    }

    /**
     * Setter method for property <tt>industryLabel</tt>.
     * 
     * @param industryLabel value to be assigned to property industryLabel
     */
    public void setIndustryLabel(EnumIndustryLabel industryLabel) {
        this.industryLabel = industryLabel;
    }

    /**
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
     * Getter method for property <tt>merchantHeadImg</tt>.
     * 
     * @return property value of merchantHeadImg
     */
    public String getMerchantHeadImg() {
        return merchantHeadImg;
    }

    /**
     * Setter method for property <tt>merchantHeadImg</tt>.
     * 
     * @param merchantHeadImg value to be assigned to property merchantHeadImg
     */
    public void setMerchantHeadImg(String merchantHeadImg) {
        this.merchantHeadImg = merchantHeadImg;
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
     * Getter method for property <tt>mainCommodity</tt>.
     * 
     * @return property value of mainCommodity
     */
    public String getMainCommodity() {
        return mainCommodity;
    }

    /**
     * Setter method for property <tt>mainCommodity</tt>.
     * 
     * @param mainCommodity value to be assigned to property mainCommodity
     */
    public void setMainCommodity(String mainCommodity) {
        this.mainCommodity = mainCommodity;
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
     * Getter method for property <tt>providentFundAccount</tt>.
     * 
     * @return property value of providentFundAccount
     */
    public String getProvidentFundAccount() {
        return providentFundAccount;
    }

    /**
     * Setter method for property <tt>providentFundAccount</tt>.
     * 
     * @param providentFundAccount value to be assigned to property providentFundAccount
     */
    public void setProvidentFundAccount(String providentFundAccount) {
        this.providentFundAccount = providentFundAccount;
    }

    /**
     * Getter method for property <tt>authorizedAgent</tt>.
     * 
     * @return property value of authorizedAgent
     */
    public String getAuthorizedAgent() {
        return authorizedAgent;
    }

    /**
     * Setter method for property <tt>authorizedAgent</tt>.
     * 
     * @param authorizedAgent value to be assigned to property authorizedAgent
     */
    public void setAuthorizedAgent(String authorizedAgent) {
        this.authorizedAgent = authorizedAgent;
    }

    /**
     * Getter method for property <tt>electricityAccount</tt>.
     * 
     * @return property value of electricityAccount
     */
    public String getElectricityAccount() {
        return electricityAccount;
    }

    /**
     * Setter method for property <tt>electricityAccount</tt>.
     * 
     * @param electricityAccount value to be assigned to property electricityAccount
     */
    public void setElectricityAccount(String electricityAccount) {
        this.electricityAccount = electricityAccount;
    }

    /**
     * Getter method for property <tt>waterAccount</tt>.
     * 
     * @return property value of waterAccount
     */
    public String getWaterAccount() {
        return waterAccount;
    }

    /**
     * Setter method for property <tt>waterAccount</tt>.
     * 
     * @param waterAccount value to be assigned to property waterAccount
     */
    public void setWaterAccount(String waterAccount) {
        this.waterAccount = waterAccount;
    }

    /**
     * Getter method for property <tt>environmentalRegistrationNo</tt>.
     * 
     * @return property value of environmentalRegistrationNo
     */
    public String getEnvironmentalRegistrationNo() {
        return environmentalRegistrationNo;
    }

    /**
     * Setter method for property <tt>environmentalRegistrationNo</tt>.
     * 
     * @param environmentalRegistrationNo value to be assigned to property environmentalRegistrationNo
     */
    public void setEnvironmentalRegistrationNo(String environmentalRegistrationNo) {
        this.environmentalRegistrationNo = environmentalRegistrationNo;
    }
    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public EnumResourceSource getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(EnumResourceSource resourceSource) {
        this.resourceSource = resourceSource;
    }
}
