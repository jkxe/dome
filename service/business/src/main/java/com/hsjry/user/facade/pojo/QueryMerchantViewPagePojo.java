/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumMerchanRoleTypeList;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMerchantViewPagePojo.java, v 1.0 2017年3月30日 下午3:38:10 jiangjd12837 Exp $
 */
public class QueryMerchantViewPagePojo implements Serializable {

    /**  */
    private static final long                 serialVersionUID = 4010516788218393286L;
    //会员
    private UserLeaguerInfoPojo               userLeaguerInfoPojo;
    //经营信息
    private UserManageInfoPojo                userManageInfoPojo;
    //客户角信息
    private UserCustomerRolePojo              userCustomerRolePojo;
    //地址联系点
    private UserAddressContactStationInfoPojo userAddressContactStationInfoPojo;
    // 证件资源项信息
    private List<UserCertificateInfoPojo>     userCertificateInfoPojoList;
    //商户种类 
    private EnumMerchanRoleTypeList           merchanRoleTypeList;

    /**
     * Getter method for property <tt>userLeaguerInfoPojo</tt>.
     * 
     * @return property value of userLeaguerInfoPojo
     */
    public UserLeaguerInfoPojo getUserLeaguerInfoPojo() {
        return userLeaguerInfoPojo;
    }

    /**
     * Setter method for property <tt>userLeaguerInfoPojo</tt>.
     * 
     * @param userLeaguerInfoPojo value to be assigned to property userLeaguerInfoPojo
     */
    public void setUserLeaguerInfoPojo(UserLeaguerInfoPojo userLeaguerInfoPojo) {
        this.userLeaguerInfoPojo = userLeaguerInfoPojo;
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
     * Getter method for property <tt>userCustomerRolePojo</tt>.
     * 
     * @return property value of userCustomerRolePojo
     */
    public UserCustomerRolePojo getUserCustomerRolePojo() {
        return userCustomerRolePojo;
    }

    /**
     * Setter method for property <tt>userCustomerRolePojo</tt>.
     * 
     * @param userCustomerRolePojo value to be assigned to property userCustomerRolePojo
     */
    public void setUserCustomerRolePojo(UserCustomerRolePojo userCustomerRolePojo) {
        this.userCustomerRolePojo = userCustomerRolePojo;
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

    public EnumMerchanRoleTypeList getMerchanRoleTypeList() {
        return merchanRoleTypeList;
    }

    public void setMerchanRoleTypeList(EnumMerchanRoleTypeList merchanRoleTypeList) {
        this.merchanRoleTypeList = merchanRoleTypeList;
    }
}
