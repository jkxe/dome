/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryResourceResponsePojo.java, v 1.0 2017年4月8日 下午3:23:31 jiangjd12837 Exp $
 */
public class QueryResourceResponsePojo implements Serializable {

    /**  */
    private static final long                     serialVersionUID = 5805117270514786418L;
    //合作方信息
    private List<UserPartnerInfoPojo>             userPartnerInfoPojoList;
    //财务报表
    private List<UserFinancialStatementsInfoPojo> userFinancialStatementsInfoPojoList;
    //公积金信息
    private List<UserAccumulationFundInfoPojo>    userAccumulationFundInfoPojoList;
    //收入信息
    private List<UserIncomeFundInfoPojo>          userIncomeFundInfoPojoList;
    //缴税信息
    private List<UserPayTaxesInfoPojo>            userPayTaxesInfoPojoList;
    //车辆信息
    private List<UserVehicleInfoPojo>             userVehicleInfoPojoList;
    //金融资产
    private List<UserFinancialAssetsPojo>         userFinancialAssetsPojoList;
    //金融工具
    private List<UserFinancialInstrumentsPojo>    userFinancialInstrumentsPojoList;
    //不动产资源
    private List<UserRealEstateResourcesPojo>     userRealEstateResourcesPojoList;
    //职业信息
    private List<UserProfessionalInfoPojo>        userProfessionalInfoPojoList;
    //司法诉讼信息
    private List<UserJudicialLitigationInfoPojo>  userJudicialLitigationInfoPojoList;
    //社保信息
    private List<UserSocialSecurityInfoPojo>      userSocialSecurityInfoPojoList;
    //历史信贷信息
    private List<UserHistoricalCreditInfoPojo>    userHistoricalCreditInfoPojoList;
    //经营信息
    private List<UserManageInfoPojo>              userManageInfoPojoList;
    //征信信息
    private List<UserCreditInfoPojo>              userCreditInfoPojoList;
    //个人客户教育信息
    private List<UserEducationInfoPojo>           UserEducationInfoPojoList;
    //大事件
    private List<UserBigEventPojo>                userBigEventPojoList;
    //影像资料
    private List<UserImageDataPojo>               userImageDataPojoList;
    //证件文档
    private List<UserCertificateInfoPojo>         userCertificatePojoList;
    //授信申请信息
    private UserCreditApplyInfoPojo userCreditApplyInfoPojo;
    /**
     * Getter method for property <tt>userImageDataPojoList</tt>.
     * 
     * @return property value of userImageDataPojoList
     */
    public List<UserImageDataPojo> getUserImageDataPojoList() {
        return userImageDataPojoList;
    }

    /**
     * Setter method for property <tt>userImageDataPojoList</tt>.
     * 
     * @param userImageDataPojoList value to be assigned to property userImageDataPojoList
     */
    public void setUserImageDataPojoList(List<UserImageDataPojo> userImageDataPojoList) {
        this.userImageDataPojoList = userImageDataPojoList;
    }

    /**
     * Getter method for property <tt>userCertificatePojoList</tt>.
     * 
     * @return property value of userCertificatePojoList
     */
    public List<UserCertificateInfoPojo> getUserCertificatePojoList() {
        return userCertificatePojoList;
    }

    /**
     * Setter method for property <tt>userCertificatePojoList</tt>.
     * 
     * @param userCertificatePojoList value to be assigned to property userCertificatePojoList
     */
    public void setUserCertificatePojoList(List<UserCertificateInfoPojo> userCertificatePojoList) {
        this.userCertificatePojoList = userCertificatePojoList;
    }

    /**
     * Getter method for property <tt>userPartnerInfoPojoList</tt>.
     * 
     * @return property value of userPartnerInfoPojoList
     */
    public List<UserPartnerInfoPojo> getUserPartnerInfoPojoList() {
        return userPartnerInfoPojoList;
    }

    /**
     * Setter method for property <tt>userPartnerInfoPojoList</tt>.
     * 
     * @param userPartnerInfoPojoList value to be assigned to property userPartnerInfoPojoList
     */
    public void setUserPartnerInfoPojoList(List<UserPartnerInfoPojo> userPartnerInfoPojoList) {
        this.userPartnerInfoPojoList = userPartnerInfoPojoList;
    }

    /**
     * Getter method for property <tt>userFinancialStatementsInfoPojoList</tt>.
     * 
     * @return property value of userFinancialStatementsInfoPojoList
     */
    public List<UserFinancialStatementsInfoPojo> getUserFinancialStatementsInfoPojoList() {
        return userFinancialStatementsInfoPojoList;
    }

    /**
     * Setter method for property <tt>userFinancialStatementsInfoPojoList</tt>.
     * 
     * @param userFinancialStatementsInfoPojoList value to be assigned to property userFinancialStatementsInfoPojoList
     */
    public void setUserFinancialStatementsInfoPojoList(List<UserFinancialStatementsInfoPojo> userFinancialStatementsInfoPojoList) {
        this.userFinancialStatementsInfoPojoList = userFinancialStatementsInfoPojoList;
    }

    /**
     * Getter method for property <tt>userAccumulationFundInfoPojoList</tt>.
     * 
     * @return property value of userAccumulationFundInfoPojoList
     */
    public List<UserAccumulationFundInfoPojo> getUserAccumulationFundInfoPojoList() {
        return userAccumulationFundInfoPojoList;
    }

    /**
     * Setter method for property <tt>userAccumulationFundInfoPojoList</tt>.
     * 
     * @param userAccumulationFundInfoPojoList value to be assigned to property userAccumulationFundInfoPojoList
     */
    public void setUserAccumulationFundInfoPojoList(List<UserAccumulationFundInfoPojo> userAccumulationFundInfoPojoList) {
        this.userAccumulationFundInfoPojoList = userAccumulationFundInfoPojoList;
    }

    /**
     * Getter method for property <tt>userIncomeFundInfoPojoList</tt>.
     * 
     * @return property value of userIncomeFundInfoPojoList
     */
    public List<UserIncomeFundInfoPojo> getUserIncomeFundInfoPojoList() {
        return userIncomeFundInfoPojoList;
    }

    /**
     * Setter method for property <tt>userIncomeFundInfoPojoList</tt>.
     * 
     * @param userIncomeFundInfoPojoList value to be assigned to property userIncomeFundInfoPojoList
     */
    public void setUserIncomeFundInfoPojoList(List<UserIncomeFundInfoPojo> userIncomeFundInfoPojoList) {
        this.userIncomeFundInfoPojoList = userIncomeFundInfoPojoList;
    }

    /**
     * Getter method for property <tt>userPayTaxesInfoPojoList</tt>.
     * 
     * @return property value of userPayTaxesInfoPojoList
     */
    public List<UserPayTaxesInfoPojo> getUserPayTaxesInfoPojoList() {
        return userPayTaxesInfoPojoList;
    }

    /**
     * Setter method for property <tt>userPayTaxesInfoPojoList</tt>.
     * 
     * @param userPayTaxesInfoPojoList value to be assigned to property userPayTaxesInfoPojoList
     */
    public void setUserPayTaxesInfoPojoList(List<UserPayTaxesInfoPojo> userPayTaxesInfoPojoList) {
        this.userPayTaxesInfoPojoList = userPayTaxesInfoPojoList;
    }

    /**
     * Getter method for property <tt>userVehicleInfoPojoList</tt>.
     * 
     * @return property value of userVehicleInfoPojoList
     */
    public List<UserVehicleInfoPojo> getUserVehicleInfoPojoList() {
        return userVehicleInfoPojoList;
    }

    /**
     * Setter method for property <tt>userVehicleInfoPojoList</tt>.
     * 
     * @param userVehicleInfoPojoList value to be assigned to property userVehicleInfoPojoList
     */
    public void setUserVehicleInfoPojoList(List<UserVehicleInfoPojo> userVehicleInfoPojoList) {
        this.userVehicleInfoPojoList = userVehicleInfoPojoList;
    }

    /**
     * Getter method for property <tt>userFinancialAssetsPojoList</tt>.
     * 
     * @return property value of userFinancialAssetsPojoList
     */
    public List<UserFinancialAssetsPojo> getUserFinancialAssetsPojoList() {
        return userFinancialAssetsPojoList;
    }

    /**
     * Setter method for property <tt>userFinancialAssetsPojoList</tt>.
     * 
     * @param userFinancialAssetsPojoList value to be assigned to property userFinancialAssetsPojoList
     */
    public void setUserFinancialAssetsPojoList(List<UserFinancialAssetsPojo> userFinancialAssetsPojoList) {
        this.userFinancialAssetsPojoList = userFinancialAssetsPojoList;
    }

    /**
     * Getter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @return property value of userFinancialInstrumentsPojoList
     */
    public List<UserFinancialInstrumentsPojo> getUserFinancialInstrumentsPojoList() {
        return userFinancialInstrumentsPojoList;
    }

    /**
     * Setter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @param userFinancialInstrumentsPojoList value to be assigned to property userFinancialInstrumentsPojoList
     */
    public void setUserFinancialInstrumentsPojoList(List<UserFinancialInstrumentsPojo> userFinancialInstrumentsPojoList) {
        this.userFinancialInstrumentsPojoList = userFinancialInstrumentsPojoList;
    }

    /**
     * Getter method for property <tt>userRealEstateResourcesPojoList</tt>.
     * 
     * @return property value of userRealEstateResourcesPojoList
     */
    public List<UserRealEstateResourcesPojo> getUserRealEstateResourcesPojoList() {
        return userRealEstateResourcesPojoList;
    }

    /**
     * Setter method for property <tt>userRealEstateResourcesPojoList</tt>.
     * 
     * @param userRealEstateResourcesPojoList value to be assigned to property userRealEstateResourcesPojoList
     */
    public void setUserRealEstateResourcesPojoList(List<UserRealEstateResourcesPojo> userRealEstateResourcesPojoList) {
        this.userRealEstateResourcesPojoList = userRealEstateResourcesPojoList;
    }

    /**
     * Getter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @return property value of userProfessionalInfoPojoList
     */
    public List<UserProfessionalInfoPojo> getUserProfessionalInfoPojoList() {
        return userProfessionalInfoPojoList;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @param userProfessionalInfoPojoList value to be assigned to property userProfessionalInfoPojoList
     */
    public void setUserProfessionalInfoPojoList(List<UserProfessionalInfoPojo> userProfessionalInfoPojoList) {
        this.userProfessionalInfoPojoList = userProfessionalInfoPojoList;
    }

    /**
     * Getter method for property <tt>userJudicialLitigationInfoPojoList</tt>.
     * 
     * @return property value of userJudicialLitigationInfoPojoList
     */
    public List<UserJudicialLitigationInfoPojo> getUserJudicialLitigationInfoPojoList() {
        return userJudicialLitigationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userJudicialLitigationInfoPojoList</tt>.
     * 
     * @param userJudicialLitigationInfoPojoList value to be assigned to property userJudicialLitigationInfoPojoList
     */
    public void setUserJudicialLitigationInfoPojoList(List<UserJudicialLitigationInfoPojo> userJudicialLitigationInfoPojoList) {
        this.userJudicialLitigationInfoPojoList = userJudicialLitigationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userSocialSecurityInfoPojoList</tt>.
     * 
     * @return property value of userSocialSecurityInfoPojoList
     */
    public List<UserSocialSecurityInfoPojo> getUserSocialSecurityInfoPojoList() {
        return userSocialSecurityInfoPojoList;
    }

    /**
     * Setter method for property <tt>userSocialSecurityInfoPojoList</tt>.
     * 
     * @param userSocialSecurityInfoPojoList value to be assigned to property userSocialSecurityInfoPojoList
     */
    public void setUserSocialSecurityInfoPojoList(List<UserSocialSecurityInfoPojo> userSocialSecurityInfoPojoList) {
        this.userSocialSecurityInfoPojoList = userSocialSecurityInfoPojoList;
    }

    /**
     * Getter method for property <tt>userHistoricalCreditInfoPojoList</tt>.
     * 
     * @return property value of userHistoricalCreditInfoPojoList
     */
    public List<UserHistoricalCreditInfoPojo> getUserHistoricalCreditInfoPojoList() {
        return userHistoricalCreditInfoPojoList;
    }

    /**
     * Setter method for property <tt>userHistoricalCreditInfoPojoList</tt>.
     * 
     * @param userHistoricalCreditInfoPojoList value to be assigned to property userHistoricalCreditInfoPojoList
     */
    public void setUserHistoricalCreditInfoPojoList(List<UserHistoricalCreditInfoPojo> userHistoricalCreditInfoPojoList) {
        this.userHistoricalCreditInfoPojoList = userHistoricalCreditInfoPojoList;
    }

    /**
     * Getter method for property <tt>userManageInfoPojoList</tt>.
     * 
     * @return property value of userManageInfoPojoList
     */
    public List<UserManageInfoPojo> getUserManageInfoPojoList() {
        return userManageInfoPojoList;
    }

    /**
     * Setter method for property <tt>userManageInfoPojoList</tt>.
     * 
     * @param userManageInfoPojoList value to be assigned to property userManageInfoPojoList
     */
    public void setUserManageInfoPojoList(List<UserManageInfoPojo> userManageInfoPojoList) {
        this.userManageInfoPojoList = userManageInfoPojoList;
    }

    /**
     * Getter method for property <tt>userCreditInfoPojoList</tt>.
     * 
     * @return property value of userCreditInfoPojoList
     */
    public List<UserCreditInfoPojo> getUserCreditInfoPojoList() {
        return userCreditInfoPojoList;
    }

    /**
     * Setter method for property <tt>userCreditInfoPojoList</tt>.
     * 
     * @param userCreditInfoPojoList value to be assigned to property userCreditInfoPojoList
     */
    public void setUserCreditInfoPojoList(List<UserCreditInfoPojo> userCreditInfoPojoList) {
        this.userCreditInfoPojoList = userCreditInfoPojoList;
    }

    /**
     * Getter method for property <tt>userEducationInfoPojoList</tt>.
     * 
     * @return property value of UserEducationInfoPojoList
     */
    public List<UserEducationInfoPojo> getUserEducationInfoPojoList() {
        return UserEducationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userEducationInfoPojoList</tt>.
     * 
     * @param UserEducationInfoPojoList value to be assigned to property userEducationInfoPojoList
     */
    public void setUserEducationInfoPojoList(List<UserEducationInfoPojo> userEducationInfoPojoList) {
        UserEducationInfoPojoList = userEducationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userBigEventPojoList</tt>.
     * 
     * @return property value of userBigEventPojoList
     */
    public List<UserBigEventPojo> getUserBigEventPojoList() {
        return userBigEventPojoList;
    }

    /**
     * Setter method for property <tt>userBigEventPojoList</tt>.
     * 
     * @param userBigEventPojoList value to be assigned to property userBigEventPojoList
     */
    public void setUserBigEventPojoList(List<UserBigEventPojo> userBigEventPojoList) {
        this.userBigEventPojoList = userBigEventPojoList;
    }

    public UserCreditApplyInfoPojo getUserCreditApplyInfoPojo() {
        return userCreditApplyInfoPojo;
    }

    public void setUserCreditApplyInfoPojo(UserCreditApplyInfoPojo userCreditApplyInfoPojo) {
        this.userCreditApplyInfoPojo = userCreditApplyInfoPojo;
    }
}
