package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserEducationInfoPojo;
import com.hsjry.user.facade.pojo.modify.RelationModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserAddressContactStationInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserCertificateInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserCustomerInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserEmailContactStationInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserPersonalBasicInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserProfessionalInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserRealEstateResourcesModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserSocialContactStationInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserTelContactStationInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserWebsiteContactStationInfoModifyPojo;

public class ModifyPersonalUserInfoRequest implements Serializable {

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
        UserAddressContactStationInfoModifyPojo
        UserEmailContactStationInfoModifyPojo
        UserSocialContactStationInfoModifyPojo
        UserTelContactStationInfoModifyPojo
        UserWebsiteContactStationInfoModifyPojo}统一继承父类UserContactStationInfoModifyPojo
        UserContactStationInfoModifyPojo    主键：stationId
            //联系点类型
            private EnumContactStationTypeCode contactStationType;      Y(子类对应的枚举类型)
            //联系点状态
            private EnumObjectStatus           contactStationStatus;    Y(EnumObjectStatus.ENABLE)
            //是否默认联系点
            private EnumBool                   defaultStationFlag;      Y(EnumBool.NO)
            //是否验
            private EnumBool                   validateFlag;            Y(EnumBool.NO)          
        UserAddressContactStationInfoModifyPojo
            private EnumAddressClassCode        addressClassCode;       Y(枚举中选一个)
            //是否收货地址
            @NotNull(errorCode = "000001", message = "是否收货地址")
            private EnumBool                    consigneeStationFlag;   Y(EnumBool.NO)          
        UserEmailContactStationInfoModifyPojo
            //邮箱分类代码
            @NotNull(errorCode = "000001", message = "邮箱分类代码")
            private EnumEmailClassCode emailClassCode;                  Y(枚举中选一个)
            //邮箱地址
            @NotNull(errorCode = "000001", message = "邮箱地址")
            private String             email;                           Y
        UserSocialContactStationInfoModifyPojo
            //社交类型
            @NotNull(errorCode = "000001", message = "社交类型")
            private EnumSocialType    socialType;                       Y(枚举中选一个)
            //账号
            @NotNull(errorCode = "000001", message = "账号")
            private String            account;                          Y
        UserTelContactStationInfoModifyPojo
            //电话分类代码
            @NotNull(errorCode = "000001", message = "电话分类代码")
            private EnumTelephoneClassCode telephoneClassCode;          Y(枚举中选一个)
            //电话号
            @NotNull(errorCode = "000001", message = "电话号")
            private String                 telephone;                   Y
        UserWebsiteContactStationInfoModifyPojo
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
    private static final long                             serialVersionUID = 7839101742760052848L;
    //客户信息
    private UserCustomerInfoModifyPojo                    userCustomerInfoPojo;
    //个人客户基本信息
    private UserPersonalBasicInfoModifyPojo               userPersonalBasicInfoPojo;
    //地址联系点
    private List<UserAddressContactStationInfoModifyPojo> userAddressContactStationInfoPojoList;
    //邮箱联系点
    private List<UserEmailContactStationInfoModifyPojo>   userEmailContactStationInfoPojoList;
    //互联网社交联系点
    private List<UserSocialContactStationInfoModifyPojo>  userSocialContactStationInfoPojoList;
    //电话联系点
    private List<UserTelContactStationInfoModifyPojo>     userTelContactStationInfoPojoList;
    //网址联系点
    private List<UserWebsiteContactStationInfoModifyPojo> userWebsiteContactStationInfoPojoList;
    //配偶关系人信息
    private RelationModifyPojo                            modifyRelationPojo;
    //职业资源项
    private List<UserProfessionalInfoModifyPojo>          userProfessionalInfoPojoList;
    //不动产资源
    private UserRealEstateResourcesModifyPojo             userRealEstateResourcesPojo;
    //证件文档  
    private UserCertificateInfoModifyPojo                 userCertificateInfoPojo;
    //教育信息
    private UserEducationInfoPojo                         userEducationInfoPojos;

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
     * Getter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojoList
     */
    public List<UserAddressContactStationInfoModifyPojo> getUserAddressContactStationInfoPojoList() {
        return userAddressContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @param userAddressContactStationInfoPojoList value to be assigned to property userAddressContactStationInfoPojoList
     */
    public void setUserAddressContactStationInfoPojoList(List<UserAddressContactStationInfoModifyPojo> userAddressContactStationInfoPojoList) {
        this.userAddressContactStationInfoPojoList = userAddressContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userEmailContactStationInfoPojoList</tt>.
     * 
     * @return property value of userEmailContactStationInfoPojoList
     */
    public List<UserEmailContactStationInfoModifyPojo> getUserEmailContactStationInfoPojoList() {
        return userEmailContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userEmailContactStationInfoPojoList</tt>.
     * 
     * @param userEmailContactStationInfoPojoList value to be assigned to property userEmailContactStationInfoPojoList
     */
    public void setUserEmailContactStationInfoPojoList(List<UserEmailContactStationInfoModifyPojo> userEmailContactStationInfoPojoList) {
        this.userEmailContactStationInfoPojoList = userEmailContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userSocialContactStationInfoPojoList</tt>.
     * 
     * @return property value of userSocialContactStationInfoPojoList
     */
    public List<UserSocialContactStationInfoModifyPojo> getUserSocialContactStationInfoPojoList() {
        return userSocialContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userSocialContactStationInfoPojoList</tt>.
     * 
     * @param userSocialContactStationInfoPojoList value to be assigned to property userSocialContactStationInfoPojoList
     */
    public void setUserSocialContactStationInfoPojoList(List<UserSocialContactStationInfoModifyPojo> userSocialContactStationInfoPojoList) {
        this.userSocialContactStationInfoPojoList = userSocialContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userTelContactStationInfoPojoList</tt>.
     * 
     * @return property value of userTelContactStationInfoPojoList
     */
    public List<UserTelContactStationInfoModifyPojo> getUserTelContactStationInfoPojoList() {
        return userTelContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userTelContactStationInfoPojoList</tt>.
     * 
     * @param userTelContactStationInfoPojoList value to be assigned to property userTelContactStationInfoPojoList
     */
    public void setUserTelContactStationInfoPojoList(List<UserTelContactStationInfoModifyPojo> userTelContactStationInfoPojoList) {
        this.userTelContactStationInfoPojoList = userTelContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userWebsiteContactStationInfoPojoList</tt>.
     * 
     * @return property value of userWebsiteContactStationInfoPojoList
     */
    public List<UserWebsiteContactStationInfoModifyPojo> getUserWebsiteContactStationInfoPojoList() {
        return userWebsiteContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userWebsiteContactStationInfoPojoList</tt>.
     * 
     * @param userWebsiteContactStationInfoPojoList value to be assigned to property userWebsiteContactStationInfoPojoList
     */
    public void setUserWebsiteContactStationInfoPojoList(List<UserWebsiteContactStationInfoModifyPojo> userWebsiteContactStationInfoPojoList) {
        this.userWebsiteContactStationInfoPojoList = userWebsiteContactStationInfoPojoList;
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
