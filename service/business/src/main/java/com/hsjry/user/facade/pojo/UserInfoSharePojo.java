/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 支付宝授权信息
 * @author jiangjd12837
 * @version $Id: UserInfoShare.java, v 1.0 2017年5月9日 下午8:30:16 jiangjd12837 Exp $
 */
public class UserInfoSharePojo implements Serializable {

    /**  */
    private static final long                       serialVersionUID = 3414249939832957871L;
    //会员信息--用户头像
    private String                                  headImg;
    //昵称
    private String                                  nickName;
    //个人基本信息 客户ID不需要
    private UserPersonalBasicInfoPojo               userPersonalBasicInfoPojo;
    //地址联系点
    private List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList;
    //座机
    private String                                  telephone;
    //证件文档
    private UserCertificateInfoPojo                 userCertificateInfoPojo;
    //客户群名称--是否是学生
    private Boolean                                 isStudent        = false;
    //邮箱
    private String email;
    
    

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>headImg</tt>.
     * 
     * @return property value of headImg
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * Setter method for property <tt>headImg</tt>.
     * 
     * @param headImg value to be assigned to property headImg
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * Getter method for property <tt>nickName</tt>.
     * 
     * @return property value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter method for property <tt>nickName</tt>.
     * 
     * @param nickName value to be assigned to property nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Getter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @return property value of userPersonalBasicInfoPojo
     */
    public UserPersonalBasicInfoPojo getUserPersonalBasicInfoPojo() {
        return userPersonalBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userPersonalBasicInfoPojo</tt>.
     * 
     * @param userPersonalBasicInfoPojo value to be assigned to property userPersonalBasicInfoPojo
     */
    public void setUserPersonalBasicInfoPojo(UserPersonalBasicInfoPojo userPersonalBasicInfoPojo) {
        this.userPersonalBasicInfoPojo = userPersonalBasicInfoPojo;
    }


    /**
     * Getter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojoList
     */
    public List<UserAddressContactStationInfoPojo> getUserAddressContactStationInfoPojoList() {
        return userAddressContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @param userAddressContactStationInfoPojoList value to be assigned to property userAddressContactStationInfoPojoList
     */
    public void setUserAddressContactStationInfoPojoList(List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList) {
        this.userAddressContactStationInfoPojoList = userAddressContactStationInfoPojoList;
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
     * Getter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @return property value of userCertificateInfoPojo
     */
    public UserCertificateInfoPojo getUserCertificateInfoPojo() {
        return userCertificateInfoPojo;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @param userCertificateInfoPojo value to be assigned to property userCertificateInfoPojo
     */
    public void setUserCertificateInfoPojo(UserCertificateInfoPojo userCertificateInfoPojo) {
        this.userCertificateInfoPojo = userCertificateInfoPojo;
    }

    /**
     * Getter method for property <tt>isStudent</tt>.
     * 
     * @return property value of isStudent
     */
    public Boolean getIsStudent() {
        return isStudent;
    }

    /**
     * Setter method for property <tt>isStudent</tt>.
     * 
     * @param isStudent value to be assigned to property isStudent
     */
    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

}
