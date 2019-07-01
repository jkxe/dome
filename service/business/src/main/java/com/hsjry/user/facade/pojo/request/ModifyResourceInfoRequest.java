package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.UserAccumulationFundInfoPojo;
import com.hsjry.user.facade.pojo.UserBigEventPojo;
import com.hsjry.user.facade.pojo.UserCreditApplyInfoPojo;
import com.hsjry.user.facade.pojo.UserCreditInfoPojo;
import com.hsjry.user.facade.pojo.UserEducationInfoPojo;
import com.hsjry.user.facade.pojo.UserFinancialAssetsPojo;
import com.hsjry.user.facade.pojo.UserFinancialStatementsInfoPojo;
import com.hsjry.user.facade.pojo.UserHistoricalCreditInfoPojo;
import com.hsjry.user.facade.pojo.UserIncomeFundInfoPojo;
import com.hsjry.user.facade.pojo.UserJudicialLitigationInfoPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserPartnerInfoPojo;
import com.hsjry.user.facade.pojo.UserPayTaxesInfoPojo;
import com.hsjry.user.facade.pojo.UserProfessionalInfoPojo;
import com.hsjry.user.facade.pojo.UserRealEstateResourcesPojo;
import com.hsjry.user.facade.pojo.UserSocialSecurityInfoPojo;
import com.hsjry.user.facade.pojo.UserVehicleInfoPojo;
import com.hsjry.user.facade.pojo.enums.EnumRelationDimension;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * 新建或修改资源项信息请求
 * 
 * @author jiangjd12837
 * @version $Id: ModifyResourceInfoRequest.java, v 1.0 2017年3月21日 上午11:08:01 jiangjd12837 Exp $
 */
public class ModifyResourceInfoRequest implements Serializable {

    /**  */
    private static final long                     serialVersionUID = -7425320367460155575L;
    //关系维度
    @NotNull(errorCode = "000001", message = "关系维度")
    private EnumRelationDimension                 relationDimension;
    //维度ID
    @NotNull(errorCode = "000001", message = "维度ID")
    @NotBlank(errorCode = "000001", message = "维度ID")
    private String                                dimensionId;
    //合作方信息
    private List<UserPartnerInfoPojo>             userPartnerInfoPojoList;
    //财务报表
    private List<UserFinancialStatementsInfoPojo> userFinancialStatementsInfoPojoList;
    //公积金信息
    private List<UserAccumulationFundInfoPojo>    userAccumulationFundInfoPojoList;
    //收入信息
    private List<UserIncomeFundInfoPojo>          userIncomeFundInfoPojoList;
    //车辆信息
    private List<UserVehicleInfoPojo>             userVehicleInfoPojoList;
    //金融资产
    private List<UserFinancialAssetsPojo>         userFinancialAssetsPojoList;
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
    //个人客户教育信息
    private List<UserEducationInfoPojo>           userEducationInfoPojoList;
    //大事件
    private List<UserBigEventPojo>                userBigEventPojoList;
    //征信信息
    private List<UserCreditInfoPojo>              userCreditInfoPojoList;
    //缴税信息
    private List<UserPayTaxesInfoPojo>            userPayTaxesInfoPojoList;
    //客户授信排查申请信息
    private UserCreditApplyInfoPojo userCreditApplyInfoPojo;
    /**
     * Getter method for property <tt>relationDimension</tt>.
     * 
     * @return property value of relationDimension
     */
    public EnumRelationDimension getRelationDimension() {
        return relationDimension;
    }

    /**
     * Setter method for property <tt>relationDimension</tt>.
     * 
     * @param relationDimension value to be assigned to property relationDimension
     */
    public void setRelationDimension(EnumRelationDimension relationDimension) {
        this.relationDimension = relationDimension;
    }

    /**
     * Getter method for property <tt>dimensionId</tt>.
     * 
     * @return property value of dimensionId
     */
    public String getDimensionId() {
        return dimensionId;
    }

    /**
     * Setter method for property <tt>dimensionId</tt>.
     * 
     * @param dimensionId value to be assigned to property dimensionId
     */
    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
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
     * Getter method for property <tt>userEducationInfoPojoList</tt>.
     * 
     * @return property value of userEducationInfoPojoList
     */
    public List<UserEducationInfoPojo> getUserEducationInfoPojoList() {
        return userEducationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userEducationInfoPojoList</tt>.
     * 
     * @param userEducationInfoPojoList value to be assigned to property userEducationInfoPojoList
     */
    public void setUserEducationInfoPojoList(List<UserEducationInfoPojo> userEducationInfoPojoList) {
        this.userEducationInfoPojoList = userEducationInfoPojoList;
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

    public UserCreditApplyInfoPojo getUserCreditApplyInfoPojo() {
        return userCreditApplyInfoPojo;
    }

    public void setUserCreditApplyInfoPojo(UserCreditApplyInfoPojo userCreditApplyInfoPojo) {
        this.userCreditApplyInfoPojo = userCreditApplyInfoPojo;
    }
}
