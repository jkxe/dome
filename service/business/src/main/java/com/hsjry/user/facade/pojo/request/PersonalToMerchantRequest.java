/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserOrganBasicInfoPojo;
import com.hsjry.user.facade.pojo.check.UserNameCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: PersonalToMerchantRequest.java, v 1.0 2017年4月6日 下午8:46:25 jiangjd12837 Exp $
 */
public class PersonalToMerchantRequest implements Serializable {

    /**  */
    private static final long                 serialVersionUID = -8720940945507946479L;
    //用户名
    @CheckWith(value = UserNameCheck.class, errorCode = "000100", message = "用户名校验", ignoreIfNull = false)
    private String                            clientName;
    //经营信息
    private UserManageInfoPojo                userManageInfoPojo;
    //证件资源项信息
    @NotNull(errorCode = "000001", message = "证件资源项信息")
    private List<UserCertificateInfoPojo>     userCertificateInfoPojoList;
    //机构基本项信息
    private UserOrganBasicInfoPojo            userOrganBasicInfoPojo;
    //关系人信息
    @NotNull(errorCode = "000001", message = "关系人信息")
    @NotBlank(errorCode = "000001", message = "关系人信息")
    private String                            relationUserId;
    //地址联系点
    private UserAddressContactStationInfoPojo userAddressContactStationInfoPojo;
    //授权人姓名
    @NotNull(errorCode = "000001", message = "授权人姓名")
    @NotBlank(errorCode = "000001", message = "授权人姓名")
    private String                            authorizedName;
    //授权人电话
    @NotNull(errorCode = "000001", message = "授权人电话")
    @NotBlank(errorCode = "000001", message = "授权人电话")
    private String                            authorizedTel;

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
     * Getter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojo
     */
    public UserAddressContactStationInfoPojo getUserAddressContactStationInfoPojo() {
        return userAddressContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @param userAddressContactStationInfoPojo value to be assigned to property userAddressContactStationInfoPojo
     */
    public void setUserAddressContactStationInfoPojo(UserAddressContactStationInfoPojo userAddressContactStationInfoPojo) {
        this.userAddressContactStationInfoPojo = userAddressContactStationInfoPojo;
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

}
