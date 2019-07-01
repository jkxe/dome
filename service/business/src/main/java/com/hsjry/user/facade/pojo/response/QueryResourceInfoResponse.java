package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAccumulationFundInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserBigEventandSourcePojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserCreditInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserEducationInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserFinancialAssetsandSourcePojo;
import com.hsjry.user.facade.pojo.UserFinancialInstrumentsandSourcePojo;
import com.hsjry.user.facade.pojo.UserFinancialStatementsInfoPojo;
import com.hsjry.user.facade.pojo.UserHistoricalCreditInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserImageDataandSourcePojo;
import com.hsjry.user.facade.pojo.UserIncomeFundInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserJudicialLitigationInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserManageInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserPartnerInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserPayTaxesInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserProfessionalInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserRealEstateResourcesandSourcePojo;
import com.hsjry.user.facade.pojo.UserSocialSecurityInfoandSourcePojo;
import com.hsjry.user.facade.pojo.UserVehicleInfoandSourcePojo;


/**
 * 
 * @author liaosq23298
 * @version $Id: QueryResourceInfoResponse.java, v 0.1 Nov 22, 2017 5:56:25 PM liaosq23298 Exp $
 */
public class QueryResourceInfoResponse implements Serializable{

    /**  */
    private static final long serialVersionUID = -1921535349082762689L;

    //合作方信息
    private List<UserPartnerInfoandSourcePojo>           userPartnerInfoandSourcePojoList;
    //财务报表
    private List<UserFinancialStatementsInfoPojo>        userFinancialStatementsInfoPojoList;
    //公积金信息
    private List<UserAccumulationFundInfoandSourcePojo>  userAccumulationFundInfoandSourcePojoList;
    //收入信息
    private List<UserIncomeFundInfoandSourcePojo>        userIncomeFundInfoandSourcePojoList;
    //缴税信息
    private List<UserPayTaxesInfoandSourcePojo>          userPayTaxesInfoandSourcePojoList;
    //车辆信息
    private List<UserVehicleInfoandSourcePojo>           userVehicleInfoandSourcePojoList;
    //金融资产
    private List<UserFinancialAssetsandSourcePojo>       userFinancialAssetsandSourcePojoList;
    //金融工具
    private List<UserFinancialInstrumentsandSourcePojo>  userFinancialInstrumentsandSourcePojoList;
    //不动产资源
    private List<UserRealEstateResourcesandSourcePojo>   userRealEstateResourcesandSourcePojoList;
    //职业信息
    private List<UserProfessionalInfoandSourcePojo>      userProfessionalInfoandSourcePojoList;
    //司法诉讼信息
    private List<UserJudicialLitigationInfoandSourcePojo> userJudicialLitigationInfoandSourcePojoList;
    //社保信息 
    private List<UserSocialSecurityInfoandSourcePojo>     userSocialSecurityInfoandSourcePojoList;
    //历史信贷信息
    private List<UserHistoricalCreditInfoandSourcePojo>   userHistoricalCreditInfoandSourcePojoList;
    //经营信息
    private List<UserManageInfoandSourcePojo>             userManageInfoandSourcePojoList;
    //征信信息
    private List<UserCreditInfoandSourcePojo>             userCreditInfoandSourcePojoList;
    //个人客户教育信息
    private List<UserEducationInfoandSourcePojo>          userEducationInfoandSourcePojoList;
    //大事件
    private List<UserBigEventandSourcePojo>               userBigEventandSourcePojoList;
    //影像资料
    private List<UserImageDataandSourcePojo>              userImageDataandSourcePojoList;
    //证件文档
    private List<UserCertificateInfoandSourcePojo>        userCertificateandSourcePojoList;
   
    /**
     * Getter method for property <tt>userImageDataandSourcePojoList</tt>.
     * 
     * @return property value of userImageDataandSourcePojoList
     */
    public List<UserImageDataandSourcePojo> getUserImageDataandSourcePojoList() {
        return userImageDataandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userImageDataandSourcePojoList</tt>.
     * 
     * @param userImageDataandSourcePojoList value to be assigned to property userImageDataandSourcePojoList
     */
    public void setUserImageDataandSourcePojoList(List<UserImageDataandSourcePojo> userImageDataandSourcePojoList) {
        this.userImageDataandSourcePojoList = userImageDataandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userCertificateandSourcePojoList</tt>.
     * 
     * @return property value of userCertificateandSourcePojoList
     */
    public List<UserCertificateInfoandSourcePojo> getUserCertificateandSourcePojoList() {
        return userCertificateandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userCertificateandSourcePojoList</tt>.
     * 
     * @param userCertificateandSourcePojoList value to be assigned to property userCertificateandSourcePojoList
     */
    public void setUserCertificateandSourcePojoList(List<UserCertificateInfoandSourcePojo> userCertificateandSourcePojoList) {
        this.userCertificateandSourcePojoList = userCertificateandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userPartnerInfoandSourcePojoList</tt>.
     * 
     * @return property value of userPartnerInfoandSourcePojoList
     */
    public List<UserPartnerInfoandSourcePojo> getUserPartnerInfoandSourcePojoList() {
        return userPartnerInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userPartnerInfoandSourcePojoList</tt>.
     * 
     * @param userPartnerInfoandSourcePojoList value to be assigned to property userPartnerInfoandSourcePojoList
     */
    public void setUserPartnerInfoandSourcePojoList(List<UserPartnerInfoandSourcePojo> userPartnerInfoandSourcePojoList) {
        this.userPartnerInfoandSourcePojoList = userPartnerInfoandSourcePojoList;
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
     * Getter method for property <tt>userAccumulationFundInfoandSourcePojoList</tt>.
     * 
     * @return property value of userAccumulationFundInfoandSourcePojoList
     */
    public List<UserAccumulationFundInfoandSourcePojo> getUserAccumulationFundInfoandSourcePojoList() {
        return userAccumulationFundInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userAccumulationFundInfoandSourcePojoList</tt>.
     * 
     * @param userAccumulationFundInfoandSourcePojoList value to be assigned to property userAccumulationFundInfoandSourcePojoList
     */
    public void setUserAccumulationFundInfoandSourcePojoList(List<UserAccumulationFundInfoandSourcePojo> userAccumulationFundInfoandSourcePojoList) {
        this.userAccumulationFundInfoandSourcePojoList = userAccumulationFundInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userIncomeFundInfoandSourcePojoList</tt>.
     * 
     * @return property value of userIncomeFundInfoandSourcePojoList
     */
    public List<UserIncomeFundInfoandSourcePojo> getUserIncomeFundInfoandSourcePojoList() {
        return userIncomeFundInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userIncomeFundInfoandSourcePojoList</tt>.
     * 
     * @param userIncomeFundInfoandSourcePojoList value to be assigned to property userIncomeFundInfoandSourcePojoList
     */
    public void setUserIncomeFundInfoPojoList(List<UserIncomeFundInfoandSourcePojo> userIncomeFundInfoPojoList) {
        this.userIncomeFundInfoandSourcePojoList = userIncomeFundInfoPojoList;
    }

    /**
     * Getter method for property <tt>userPayTaxesInfoandSourcePojoList</tt>.
     * 
     * @return property value of userPayTaxesInfoandSourcePojoList
     */
    public List<UserPayTaxesInfoandSourcePojo> getUserPayTaxesInfoandSourcePojoList() {
        return userPayTaxesInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userPayTaxesInfoandSourcePojoList</tt>.
     * 
     * @param userPayTaxesInfoandSourcePojoList value to be assigned to property userPayTaxesInfoandSourcePojoList
     */
    public void setUserPayTaxesInfoPojoList(List<UserPayTaxesInfoandSourcePojo> userPayTaxesInfoPojoList) {
        this.userPayTaxesInfoandSourcePojoList = userPayTaxesInfoPojoList;
    }

    /**
     * Getter method for property <tt>userVehicleInfoandSourcePojoList</tt>.
     * 
     * @return property value of userVehicleInfoandSourcePojoList
     */
    public List<UserVehicleInfoandSourcePojo> getUserVehicleInfoandSourcePojoList() {
        return userVehicleInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userVehicleInfoandSourcePojoList</tt>.
     * 
     * @param userVehicleInfoandSourcePojoList value to be assigned to property userVehicleInfoandSourcePojoList
     */
    public void setUserVehicleInfoandSourcePojoList(List<UserVehicleInfoandSourcePojo> userVehicleInfoandSourcePojoList) {
        this.userVehicleInfoandSourcePojoList = userVehicleInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userFinancialAssetsandSourcePojoList</tt>.
     * 
     * @return property value of userFinancialAssetsandSourcePojoList
     */
    public List<UserFinancialAssetsandSourcePojo> getUserFinancialAssetsandSourcePojoList() {
        return userFinancialAssetsandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userFinancialAssetsandSourcePojoList</tt>.
     * 
     * @param userFinancialAssetsandSourcePojoList value to be assigned to property userFinancialAssetsandSourcePojoList
     */
    public void setUserFinancialAssetsandSourcePojoList(List<UserFinancialAssetsandSourcePojo> userFinancialAssetsandSourcePojoList) {
        this.userFinancialAssetsandSourcePojoList = userFinancialAssetsandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @return property value of userFinancialInstrumentsPojoList
     */
    public List<UserFinancialInstrumentsandSourcePojo> getUserFinancialInstrumentsandSourcePojoList() {
        return userFinancialInstrumentsandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @param userFinancialInstrumentsPojoList value to be assigned to property userFinancialInstrumentsPojoList
     */
    public void setUserFinancialInstrumentsPojoList(List<UserFinancialInstrumentsandSourcePojo> userFinancialInstrumentsandSourcePojoList) {
        this.userFinancialInstrumentsandSourcePojoList = userFinancialInstrumentsandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userRealEstateResourcesandSourcePojoList</tt>.
     * 
     * @return property value of userRealEstateResourcesandSourcePojoList
     */
    public List<UserRealEstateResourcesandSourcePojo> getUserRealEstateResourcesandSourcePojoList() {
        return userRealEstateResourcesandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userRealEstateResourcesandSourcePojoList</tt>.
     * 
     * @param userRealEstateResourcesandSourcePojoList value to be assigned to property userRealEstateResourcesandSourcePojoList
     */
    public void setUserRealEstateResourcesandSourcePojoList(List<UserRealEstateResourcesandSourcePojo> userRealEstateResourcesandSourcePojoList) {
        this.userRealEstateResourcesandSourcePojoList = userRealEstateResourcesandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userProfessionalInfoandSourcePojoList</tt>.
     * 
     * @return property value of userProfessionalInfoandSourcePojoList
     */
    public List<UserProfessionalInfoandSourcePojo> getUserProfessionalInfoandSourcePojoList() {
        return userProfessionalInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoandSourcePojoList</tt>.
     * 
     * @param userProfessionalInfoandSourcePojoList value to be assigned to property userProfessionalInfoandSourcePojoList
     */
    public void setUserProfessionalInfoPojoList(List<UserProfessionalInfoandSourcePojo> userProfessionalInfoandSourcePojoList) {
        this.userProfessionalInfoandSourcePojoList = userProfessionalInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userJudicialLitigationInfoandSourcePojoList</tt>.
     * 
     * @return property value of userJudicialLitigationInfoandSourcePojoList
     */
    public List<UserJudicialLitigationInfoandSourcePojo> getUserJudicialLitigationInfoandSourcePojoList() {
        return userJudicialLitigationInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userJudicialLitigationInfoandSourcePojoList</tt>.
     * 
     * @param userJudicialLitigationInfoandSourcePojoList value to be assigned to property userJudicialLitigationInfoandSourcePojoList
     */
    public void setUserJudicialLitigationInfoandSourcePojoList(List<UserJudicialLitigationInfoandSourcePojo> userJudicialLitigationInfoandSourcePojoList) {
        this.userJudicialLitigationInfoandSourcePojoList = userJudicialLitigationInfoandSourcePojoList;
    }
    /**
     * Getter method for property <tt>userSocialSecurityInfoandSourcePojoList</tt>.
     * 
     * @return property value of userSocialSecurityInfoandSourcePojoList
     */
    public List<UserSocialSecurityInfoandSourcePojo> getUserSocialSecurityInfoandSourcePojoList() {
        return userSocialSecurityInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userSocialSecurityInfoandSourcePojoList</tt>.
     * 
     * @param userSocialSecurityInfoandSourcePojoList value to be assigned to property userSocialSecurityInfoandSourcePojoList
     */
    public void setUserSocialSecurityInfoandSourcePojoList(List<UserSocialSecurityInfoandSourcePojo> userSocialSecurityInfoandSourcePojoList) {
        this.userSocialSecurityInfoandSourcePojoList = userSocialSecurityInfoandSourcePojoList;
    }

    
    
    

    /**
     * Getter method for property <tt>userHistoricalCreditInfoandSourcePojoList</tt>.
     * 
     * @return property value of userHistoricalCreditInfoandSourcePojoList
     */
    public List<UserHistoricalCreditInfoandSourcePojo> getUserHistoricalCreditInfoandSourcePojoList() {
        return userHistoricalCreditInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userHistoricalCreditInfoandSourcePojoList</tt>.
     * 
     * @param userHistoricalCreditInfoandSourcePojoList value to be assigned to property userHistoricalCreditInfoandSourcePojoList
     */
    public void setUserHistoricalCreditInfoPojoList(List<UserHistoricalCreditInfoandSourcePojo> userHistoricalCreditInfoandSourcePojoList) {
        this.userHistoricalCreditInfoandSourcePojoList = userHistoricalCreditInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userManageInfoandSourcePojoList</tt>.
     * 
     * @return property value of userManageInfoandSourcePojoList
     */
    public List<UserManageInfoandSourcePojo> getUserManageInfoandSourcePojoList() {
        return userManageInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userManageInfoandSourcePojoList</tt>.
     * 
     * @param userManageInfoandSourcePojoList value to be assigned to property userManageInfoandSourcePojoList
     */
    public void setUserManageInfoPojoList(List<UserManageInfoandSourcePojo> userManageInfoandSourcePojoList) {
        this.userManageInfoandSourcePojoList = userManageInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userCreditInfoandSourcePojoList</tt>.
     * 
     * @return property value of userCreditInfoandSourcePojoList
     */
    public List<UserCreditInfoandSourcePojo> getUserCreditInfoandSourcePojoList() {
        return userCreditInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userCreditInfoandSourcePojoList</tt>.
     * 
     * @param userCreditInfoandSourcePojoList value to be assigned to property userCreditInfoandSourcePojoList
     */
    public void setUserCreditInfoandSourcePojoList(List<UserCreditInfoandSourcePojo> userCreditInfoandSourcePojoList) {
        this.userCreditInfoandSourcePojoList = userCreditInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userEducationInfoandSourcePojoList</tt>.
     * 
     * @return property value of UserEducationInfoandSourcePojoList
     */
    public List<UserEducationInfoandSourcePojo> getUserEducationInfoandSourcePojoList() {
        return userEducationInfoandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userEducationInfoandSourcePojoList</tt>.
     * 
     * @param UserEducationInfoandSourcePojoList value to be assigned to property userEducationInfoandSourcePojoList
     */
    public void setUserEducationInfoandSourcePojoList(List<UserEducationInfoandSourcePojo> userEducationInfoandSourcePojoList) {
        this.userEducationInfoandSourcePojoList = userEducationInfoandSourcePojoList;
    }

    /**
     * Getter method for property <tt>userBigEventandSourcePojoList</tt>.
     * 
     * @return property value of userBigEventandSourcePojoList
     */
    public List<UserBigEventandSourcePojo> getUserBigEventandSourcePojoList() {
        return userBigEventandSourcePojoList;
    }

    /**
     * Setter method for property <tt>userBigEventandSourcePojoList</tt>.
     * 
     * @param userBigEventandSourcePojoList value to be assigned to property userBigEventandSourcePojoList
     */
    public void setUserBigEventandSourcePojoList(List<UserBigEventandSourcePojo> userBigEventandSourcePojoList) {
        this.userBigEventandSourcePojoList = userBigEventandSourcePojoList;
    }
    
}