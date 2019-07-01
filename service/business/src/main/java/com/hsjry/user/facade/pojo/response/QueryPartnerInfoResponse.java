/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.PartnerAddressPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserOrganBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserPartnerInfoPojo;
import com.hsjry.user.facade.pojo.enums.EnumPartnerType;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ModifyPartnerInfoRequest.java, v 1.0 2017年7月13日 下午2:51:08 jiangjd12837 Exp $
 */
public class QueryPartnerInfoResponse implements Serializable {

    /**  */
    private static final long             serialVersionUID = 4248311877102525928L;
    //客户ID
    private String                        userId;
    //用户名
    private String                        userName;
    //合作方信息
    private UserPartnerInfoPojo           userPartnerInfoPojo;
    //经营信息
    private UserManageInfoPojo            userManageInfoPojo;
    //合作方类型
    private List<EnumPartnerType>         enumPartnerTypeList;
    //法人客户ID
    private String                        relationUserId;
    //法人姓名
    private String                        relationUserName;
    //法人证件类型
    private EnumCertificateKind           relationIdKind;
    //法人证件号码
    private String                        relationIdNo;
    //授权人姓名
    private String                        authorizedName;
    //授权人姓名
    private String                        authorizedTel;
    //证件资源项信息
    private List<UserCertificateInfoPojo> userCertificateInfoPojoList;
    //机构基本信息
    private UserOrganBasicInfoPojo        userOrganBasicInfoPojo;
    //地址联系点
    private PartnerAddressPojo            partnerAddressPojo;

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
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>userPartnerInfoPojo</tt>.
     * 
     * @return property value of userPartnerInfoPojo
     */
    public UserPartnerInfoPojo getUserPartnerInfoPojo() {
        return userPartnerInfoPojo;
    }

    /**
     * Setter method for property <tt>userPartnerInfoPojo</tt>.
     * 
     * @param userPartnerInfoPojo value to be assigned to property userPartnerInfoPojo
     */
    public void setUserPartnerInfoPojo(UserPartnerInfoPojo userPartnerInfoPojo) {
        this.userPartnerInfoPojo = userPartnerInfoPojo;
    }

    /**
     * Getter method for property <tt>userManageInfoPojo</tt>.
     * 
     * @return property value of userManageInfoPojo
     */
    public UserManageInfoPojo getUserManageInfoPojo() {
        return userManageInfoPojo;
    }

    /**
     * Setter method for property <tt>userManageInfoPojo</tt>.
     * 
     * @param userManageInfoPojo value to be assigned to property userManageInfoPojo
     */
    public void setUserManageInfoPojo(UserManageInfoPojo userManageInfoPojo) {
        this.userManageInfoPojo = userManageInfoPojo;
    }

    /**
     * Getter method for property <tt>enumPartnerTypeList</tt>.
     * 
     * @return property value of enumPartnerTypeList
     */
    public List<EnumPartnerType> getEnumPartnerTypeList() {
        return enumPartnerTypeList;
    }

    /**
     * Setter method for property <tt>enumPartnerTypeList</tt>.
     * 
     * @param enumPartnerTypeList value to be assigned to property enumPartnerTypeList
     */
    public void setEnumPartnerTypeList(List<EnumPartnerType> enumPartnerTypeList) {
        this.enumPartnerTypeList = enumPartnerTypeList;
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
     * Getter method for property <tt>userCertificateInfoPojoList</tt>.
     * 
     * @return property value of userCertificateInfoPojoList
     */
    public List<UserCertificateInfoPojo> getUserCertificateInfoPojoList() {
        return userCertificateInfoPojoList;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojoList</tt>.
     * 
     * @param userCertificateInfoPojoList value to be assigned to property userCertificateInfoPojoList
     */
    public void setUserCertificateInfoPojoList(List<UserCertificateInfoPojo> userCertificateInfoPojoList) {
        this.userCertificateInfoPojoList = userCertificateInfoPojoList;
    }

    /**
     * Getter method for property <tt>userOrganBasicInfoPojo</tt>.
     * 
     * @return property value of userOrganBasicInfoPojo
     */
    public UserOrganBasicInfoPojo getUserOrganBasicInfoPojo() {
        return userOrganBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userOrganBasicInfoPojo</tt>.
     * 
     * @param userOrganBasicInfoPojo value to be assigned to property userOrganBasicInfoPojo
     */
    public void setUserOrganBasicInfoPojo(UserOrganBasicInfoPojo userOrganBasicInfoPojo) {
        this.userOrganBasicInfoPojo = userOrganBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>partnerAddressPojo</tt>.
     * 
     * @return property value of partnerAddressPojo
     */
    public PartnerAddressPojo getPartnerAddressPojo() {
        return partnerAddressPojo;
    }

    /**
     * Setter method for property <tt>partnerAddressPojo</tt>.
     * 
     * @param partnerAddressPojo value to be assigned to property partnerAddressPojo
     */
    public void setPartnerAddressPojo(PartnerAddressPojo partnerAddressPojo) {
        this.partnerAddressPojo = partnerAddressPojo;
    }

}
