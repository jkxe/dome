package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAddressMaintainPojo;
import com.hsjry.user.facade.pojo.UserEducationInfoPojo;
import com.hsjry.user.facade.pojo.UserEmailMaintainPojo;
import com.hsjry.user.facade.pojo.UserSocialMaintainPojo;
import com.hsjry.user.facade.pojo.UserTelMaintainPojo;
import com.hsjry.user.facade.pojo.UserWebsiteMaintainPojo;
import com.hsjry.user.facade.pojo.modify.RelationModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserCertificateInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserCustomerInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserPersonalBasicInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserProfessionalInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserRealEstateResourcesModifyPojo;

public class MaintainPersonalUserInfoRequest implements Serializable {

    /**  */
    private static final long                    serialVersionUID = -4020483186679126126L;
    /**modifyPersonalUserInfo接口文档
    所有对象，需要的时候进行创建，新增的时候不用传主键，修改的时候必须传主键
    各个对象新增时必填参数如下：
    UserCustomerInfoModifyPojo              主键：userId                   
        //客户类型
        private EnumUserType      clientCategory;       Y                   
        //激活状态
        private EnumBool          activeStatus;         Y(EnumBool.YES)     
        //记录状态
        private EnumObjectStatus  recordStatus;         Y(EnumObjectStatus.ENABLE)
        //是否关系人客户
        private EnumBool          custPartnerFlag;      Y(EnumBool.NO)  
        //名单状态
        private EnumListStatus    listStatus;           Y(EnumListStatus.UNCLASSIFIED)
        //信用等级
        private String            creditLevel;          Y
        //实名认证标志
        private EnumBool          realnameFlag;         Y(EnumBool.NO)
        
    UserPersonalBasicInfoModifyPojo     主键：userId
    
    联系点{ 
        UserAddressMaintainPojo
        UserEmailMaintainPojo
        UserSocialMaintainPojo
        UserTelMaintainPojo
        UserWebsiteMaintainPojo
        UserMaintainPojo    主键：stationId
            //联系点类型
            private EnumContactStationTypeCode contactStationType;      Y(子类对应的枚举类型)
            //联系点状态
            private EnumObjectStatus           contactStationStatus;    Y(EnumObjectStatus.ENABLE)
            //是否默认联系点
            private EnumBool                   defaultStationFlag;      Y(EnumBool.NO)
            //是否验
            private EnumBool                   validateFlag;            Y(EnumBool.NO)          
        UserAddressMaintainPojo
            private EnumAddressClassCode        addressClassCode;       Y(枚举中选一个)
            //是否收货地址
            @NotNull(errorCode = "000001", message = "是否收货地址")
            private EnumBool                    consigneeStationFlag;   Y(EnumBool.NO)          
        UserEmailMaintainPojo
            //邮箱分类代码
            @NotNull(errorCode = "000001", message = "邮箱分类代码")
            private EnumEmailClassCode emailClassCode;                  Y(枚举中选一个)
            //邮箱地址
            @NotNull(errorCode = "000001", message = "邮箱地址")
            private String             email;                           Y
        UserSocialMaintainPojo
            //社交类型
            @NotNull(errorCode = "000001", message = "社交类型")
            private EnumSocialType    socialType;                       Y(枚举中选一个)
            //账号
            @NotNull(errorCode = "000001", message = "账号")
            private String            account;                          Y
        UserTelMaintainPojo
            //电话分类代码
            @NotNull(errorCode = "000001", message = "电话分类代码")
            private EnumTelephoneClassCode telephoneClassCode;          Y(枚举中选一个)
            //电话号
            @NotNull(errorCode = "000001", message = "电话号")
            private String                 telephone;                   Y
        UserWebsiteMaintainPojo
        //网址URL
        @NotNull(errorCode = "000001", message = "网址URL")
        private String            website;                              Y
        
    RelationModifyPojo          复合对象
        //关系代码
        private EnumClientRelation                         clientRelation;      Y
        //关系人姓名
        private String                                     clientName;          Y   
        //关系人客户ID
        private String                                     relationId;          Y
        //客户类型
        private EnumUserType                               userType;            Y
    
    UserCertificateInfoModifyPojo   主键：resourceId       
        //编号
        private String              certificateNo;      Y
        //证件项类型
        private EnumCertificateKind certificateKind;    Y(枚举中选一个)
        //证件状态
        private EnumResourceStatus  certificateStatus;  Y(EnumResourceStatus.UNVERIFIED) 
         */
    //客户信息
    private UserCustomerInfoModifyPojo           userCustomerInfoPojo;
    //个人客户基本信息
    private UserPersonalBasicInfoModifyPojo      userPersonalBasicInfoPojo;
    //地址联系点
    private List<UserAddressMaintainPojo>        userAddressMaintainPojoList;
    //邮箱联系点
    private List<UserEmailMaintainPojo>          userEmailMaintainPojoList;
    //互联网社交联系点
    private List<UserSocialMaintainPojo>         userSocialMaintainPojoList;
    //电话联系点
    private List<UserTelMaintainPojo>            userTelMaintainPojoList;
    //网址联系点
    private List<UserWebsiteMaintainPojo>        userWebsiteMaintainPojoList;
    //配偶关系人信息
    private RelationModifyPojo                   modifyRelationPojo;
    //职业资源项
    private List<UserProfessionalInfoModifyPojo> userProfessionalInfoPojoList;
    //不动产资源
    private UserRealEstateResourcesModifyPojo    userRealEstateResourcesPojo;
    //证件文档  
    private UserCertificateInfoModifyPojo        userCertificateInfoPojo;
    //教育信息
    private UserEducationInfoPojo                userEducationInfoPojos;

    /**
     * Getter method for property <tt>userEducationInfoPojos</tt>.
     * 
     * @return property value of userEducationInfoPojos
     */
    public UserEducationInfoPojo getUserEducationInfoPojos() {
        return userEducationInfoPojos;
    }

    /**
     * Setter method for property <tt>userEducationInfoPojos</tt>.
     * 
     * @param userEducationInfoPojos value to be assigned to property userEducationInfoPojos
     */
    public void setUserEducationInfoPojos(UserEducationInfoPojo userEducationInfoPojos) {
        this.userEducationInfoPojos = userEducationInfoPojos;
    }

    /**
     * Getter method for property <tt>userCustomerInfoPojo</tt>.
     * 
     * @return property value of userCustomerInfoPojo
     */
    public UserCustomerInfoModifyPojo getUserCustomerInfoPojo() {
        return userCustomerInfoPojo;
    }

    /**
     * Setter method for property <tt>userCustomerInfoPojo</tt>.
     * 
     * @param userCustomerInfoPojo value to be assigned to property userCustomerInfoPojo
     */
    public void setUserCustomerInfoPojo(UserCustomerInfoModifyPojo userCustomerInfoPojo) {
        this.userCustomerInfoPojo = userCustomerInfoPojo;
    }

    /**
     * Getter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @return property value of userPersonalBasicInfoPojo
     */
    public UserPersonalBasicInfoModifyPojo getUserPersonalBasicInfoPojo() {
        return userPersonalBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @param userPersonalBasicInfoPojo value to be assigned to property userPersonalBasicInfoPojo
     */
    public void setUserPersonalBasicInfoPojo(UserPersonalBasicInfoModifyPojo userPersonalBasicInfoPojo) {
        this.userPersonalBasicInfoPojo = userPersonalBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>userAddressMaintainPojoList</tt>.
     * 
     * @return property value of userAddressMaintainPojoList
     */
    public List<UserAddressMaintainPojo> getUserAddressMaintainPojoList() {
        return userAddressMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userAddressMaintainPojoList</tt>.
     * 
     * @param userAddressMaintainPojoList value to be assigned to property userAddressMaintainPojoList
     */
    public void setUserAddressMaintainPojoList(List<UserAddressMaintainPojo> userAddressMaintainPojoList) {
        this.userAddressMaintainPojoList = userAddressMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userEmailMaintainPojoList</tt>.
     * 
     * @return property value of userEmailMaintainPojoList
     */
    public List<UserEmailMaintainPojo> getUserEmailMaintainPojoList() {
        return userEmailMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userEmailMaintainPojoList</tt>.
     * 
     * @param userEmailMaintainPojoList value to be assigned to property userEmailMaintainPojoList
     */
    public void setUserEmailMaintainPojoList(List<UserEmailMaintainPojo> userEmailMaintainPojoList) {
        this.userEmailMaintainPojoList = userEmailMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userSocialMaintainPojoList</tt>.
     * 
     * @return property value of userSocialMaintainPojoList
     */
    public List<UserSocialMaintainPojo> getUserSocialMaintainPojoList() {
        return userSocialMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userSocialMaintainPojoList</tt>.
     * 
     * @param userSocialMaintainPojoList value to be assigned to property userSocialMaintainPojoList
     */
    public void setUserSocialMaintainPojoList(List<UserSocialMaintainPojo> userSocialMaintainPojoList) {
        this.userSocialMaintainPojoList = userSocialMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userTelMaintainPojoList</tt>.
     * 
     * @return property value of userTelMaintainPojoList
     */
    public List<UserTelMaintainPojo> getUserTelMaintainPojoList() {
        return userTelMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userTelMaintainPojoList</tt>.
     * 
     * @param userTelMaintainPojoList value to be assigned to property userTelMaintainPojoList
     */
    public void setUserTelMaintainPojoList(List<UserTelMaintainPojo> userTelMaintainPojoList) {
        this.userTelMaintainPojoList = userTelMaintainPojoList;
    }

    /**
     * Getter method for property <tt>userWebsiteMaintainPojoList</tt>.
     * 
     * @return property value of userWebsiteMaintainPojoList
     */
    public List<UserWebsiteMaintainPojo> getUserWebsiteMaintainPojoList() {
        return userWebsiteMaintainPojoList;
    }

    /**
     * Setter method for property <tt>userWebsiteMaintainPojoList</tt>.
     * 
     * @param userWebsiteMaintainPojoList value to be assigned to property userWebsiteMaintainPojoList
     */
    public void setUserWebsiteMaintainPojoList(List<UserWebsiteMaintainPojo> userWebsiteMaintainPojoList) {
        this.userWebsiteMaintainPojoList = userWebsiteMaintainPojoList;
    }

    /**
     * Getter method for property <tt>modifyRelationPojo</tt>.
     * 
     * @return property value of modifyRelationPojo
     */
    public RelationModifyPojo getModifyRelationPojo() {
        return modifyRelationPojo;
    }

    /**
     * Setter method for property <tt>modifyRelationPojo</tt>.
     * 
     * @param modifyRelationPojo value to be assigned to property modifyRelationPojo
     */
    public void setModifyRelationPojo(RelationModifyPojo modifyRelationPojo) {
        this.modifyRelationPojo = modifyRelationPojo;
    }

    /**
     * Getter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @return property value of userProfessionalInfoPojoList
     */
    public List<UserProfessionalInfoModifyPojo> getUserProfessionalInfoPojoList() {
        return userProfessionalInfoPojoList;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @param userProfessionalInfoPojoList value to be assigned to property userProfessionalInfoPojoList
     */
    public void setUserProfessionalInfoPojoList(List<UserProfessionalInfoModifyPojo> userProfessionalInfoPojoList) {
        this.userProfessionalInfoPojoList = userProfessionalInfoPojoList;
    }

    /**
     * Getter method for property <tt>userRealEstateResourcesPojo</tt>.
     * 
     * @return property value of userRealEstateResourcesPojo
     */
    public UserRealEstateResourcesModifyPojo getUserRealEstateResourcesPojo() {
        return userRealEstateResourcesPojo;
    }

    /**
     * Setter method for property <tt>userRealEstateResourcesPojo</tt>.
     * 
     * @param userRealEstateResourcesPojo value to be assigned to property userRealEstateResourcesPojo
     */
    public void setUserRealEstateResourcesPojo(UserRealEstateResourcesModifyPojo userRealEstateResourcesPojo) {
        this.userRealEstateResourcesPojo = userRealEstateResourcesPojo;
    }

    /**
     * Getter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @return property value of userCertificateInfoPojo
     */
    public UserCertificateInfoModifyPojo getUserCertificateInfoPojo() {
        return userCertificateInfoPojo;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @param userCertificateInfoPojo value to be assigned to property userCertificateInfoPojo
     */
    public void setUserCertificateInfoPojo(UserCertificateInfoModifyPojo userCertificateInfoPojo) {
        this.userCertificateInfoPojo = userCertificateInfoPojo;
    }

}
