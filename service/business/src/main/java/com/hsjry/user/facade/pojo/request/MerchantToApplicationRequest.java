/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;
import com.hsjry.user.facade.pojo.ModifyRelationPojo;
import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserImageDataPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserOrganBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserTelContactStationInfoPojo;
import com.hsjry.user.facade.pojo.check.UserNameCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: MerchantToApplicationRequest.java, v 1.0 2017年4月5日 下午7:54:39 jiangjd12837 Exp $
 */
public class MerchantToApplicationRequest implements Serializable {

    /**  */
    private static final long                 serialVersionUID = 2663300831670994045L;
    //系统角色
    @NotNull(errorCode = "000001", message = "系统角色")
    private EnumSystemRoleType                systemRoleType;
    //用户名
    @NotNull(errorCode = "000001", message = "用户名")
    @NotBlank(errorCode = "000001", message = "用户名")
    @CheckWith(value = UserNameCheck.class, errorCode = "000003", message = "用户名校验", ignoreIfNull = false)
    private String                            clientName;
    //经营信息
    private UserManageInfoPojo                userManageInfoPojo;
    //证件资源项信息
    private UserCertificateInfoPojo           userCertificatePojo;
    //机构基本项信息
    private UserOrganBasicInfoPojo            userOrganBasicInfoPojo;
    //法人代表关系人信息
    private ModifyRelationPojo                modifyRelationPojo;
    //地址联系点
    private UserAddressContactStationInfoPojo userAddressContactStationInfoPojo;
    //电话联系点
    private UserTelContactStationInfoPojo     userTelContactStationInfoPojo;
    //影像资源项
    private UserImageDataPojo                 userImageDataPojo;
    @NotNull(errorCode = "000001", message = "授权人姓名")
    @NotBlank(errorCode = "000001", message = "授权人姓名")
    private String                            authorizedName;
    //授权人电话
    @NotNull(errorCode = "000001", message = "授权人电话")
    @NotBlank(errorCode = "000001", message = "授权人电话")
    private String                            authorizedTel;

    /**
     * Getter method for property <tt>systemRoleType</tt>.
     * 
     * @return property value of systemRoleType
     */
    public EnumSystemRoleType getSystemRoleType() {
        return systemRoleType;
    }

    /**
     * Setter method for property <tt>systemRoleType</tt>.
     * 
     * @param systemRoleType value to be assigned to property systemRoleType
     */
    public void setSystemRoleType(EnumSystemRoleType systemRoleType) {
        this.systemRoleType = systemRoleType;
    }

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
     * Getter method for property <tt>userCertificatePojo</tt>.
     * 
     * @return property value of userCertificatePojo
     */
    public UserCertificateInfoPojo getUserCertificatePojo() {
        return userCertificatePojo;
    }

    /**
     * Setter method for property <tt>userCertificatePojo</tt>.
     * 
     * @param userCertificatePojo value to be assigned to property userCertificatePojo
     */
    public void setUserCertificatePojo(UserCertificateInfoPojo userCertificatePojo) {
        this.userCertificatePojo = userCertificatePojo;
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
     * Getter method for property <tt>modifyRelationPojo</tt>.
     * 
     * @return property value of modifyRelationPojo
     */
    public ModifyRelationPojo getModifyRelationPojo() {
        return modifyRelationPojo;
    }

    /**
     * Setter method for property <tt>modifyRelationPojo</tt>.
     * 
     * @param modifyRelationPojo value to be assigned to property modifyRelationPojo
     */
    public void setModifyRelationPojo(ModifyRelationPojo modifyRelationPojo) {
        this.modifyRelationPojo = modifyRelationPojo;
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
     * Getter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @return property value of userTelContactStationInfoPojo
     */
    public UserTelContactStationInfoPojo getUserTelContactStationInfoPojo() {
        return userTelContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @param userTelContactStationInfoPojo value to be assigned to property userTelContactStationInfoPojo
     */
    public void setUserTelContactStationInfoPojo(UserTelContactStationInfoPojo userTelContactStationInfoPojo) {
        this.userTelContactStationInfoPojo = userTelContactStationInfoPojo;
    }

    /**
     * Getter method for property <tt>userImageDataPojo</tt>.
     * 
     * @return property value of userImageDataPojo
     */
    public UserImageDataPojo getUserImageDataPojo() {
        return userImageDataPojo;
    }

    /**
     * Setter method for property <tt>userImageDataPojo</tt>.
     * 
     * @param userImageDataPojo value to be assigned to property userImageDataPojo
     */
    public void setUserImageDataPojo(UserImageDataPojo userImageDataPojo) {
        this.userImageDataPojo = userImageDataPojo;
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
