/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumAddressClassCode;
import com.hsjry.user.facade.pojo.enums.EnumIndustryLabel;
import com.hsjry.user.facade.pojo.enums.EnumMerchanRoleTypeList;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 商户视图分页查询
 * @author jiangjd12837
 * @version $Id: QueryMerchantViewPageRequest.java, v 1.0 2017年3月30日 下午2:41:33 jiangjd12837 Exp $
 */
public class QueryMerchantViewPageRequest implements Serializable {

    /**  */
    private static final long       serialVersionUID = -2825302436382456085L;
    //用户名
    private String                  userName;
    //商户名称
    private String                  merchantName;
    //商户分类
    private EnumIndustryLabel       industryLabel;
    //角色状态
    private EnumVerifyStatus        roleStatus;
    //商户角色列表
    private EnumMerchanRoleTypeList merchanRoleTypeList;
    //地址分类代码
    private EnumAddressClassCode    addressClassCode;
    //省
    private String                  provinceCode;
    //市
    private String                  cityCode;
    //区
    private String                  area;

    /**
     * Getter method for property <tt>merchanRoleTypeList</tt>.
     * 
     * @return property value of merchanRoleTypeList
     */
    public EnumMerchanRoleTypeList getMerchanRoleTypeList() {
        return merchanRoleTypeList;
    }

    /**
     * Setter method for property <tt>merchanRoleTypeList</tt>.
     * 
     * @param merchanRoleTypeList value to be assigned to property merchanRoleTypeList
     */
    public void setMerchanRoleTypeList(EnumMerchanRoleTypeList merchanRoleTypeList) {
        this.merchanRoleTypeList = merchanRoleTypeList;
    }

    /**
     * Getter method for property <tt>addressClassCode</tt>.
     * 
     * @return property value of addressClassCode
     */
    public EnumAddressClassCode getAddressClassCode() {
        return addressClassCode;
    }

    /**
     * Setter method for property <tt>addressClassCode</tt>.
     * 
     * @param addressClassCode value to be assigned to property addressClassCode
     */
    public void setAddressClassCode(EnumAddressClassCode addressClassCode) {
        this.addressClassCode = addressClassCode;
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
     * Getter method for property <tt>merchantName</tt>.
     * 
     * @return property value of merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Setter method for property <tt>merchantName</tt>.
     * 
     * @param merchantName value to be assigned to property merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Getter method for property <tt>industryLabel</tt>.
     * 
     * @return property value of industryLabel
     */
    public EnumIndustryLabel getIndustryLabel() {
        return industryLabel;
    }

    /**
     * Setter method for property <tt>industryLabel</tt>.
     * 
     * @param industryLabel value to be assigned to property industryLabel
     */
    public void setIndustryLabel(EnumIndustryLabel industryLabel) {
        this.industryLabel = industryLabel;
    }

    /**
     * Getter method for property <tt>roleStatus</tt>.
     * 
     * @return property value of roleStatus
     */
    public EnumVerifyStatus getRoleStatus() {
        return roleStatus;
    }

    /**
     * Setter method for property <tt>roleStatus</tt>.
     * 
     * @param roleStatus value to be assigned to property roleStatus
     */
    public void setRoleStatus(EnumVerifyStatus roleStatus) {
        this.roleStatus = roleStatus;
    }

    /**
     * Getter method for property <tt>provinceCode</tt>.
     * 
     * @return property value of provinceCode
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * Setter method for property <tt>provinceCode</tt>.
     * 
     * @param provinceCode value to be assigned to property provinceCode
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * Getter method for property <tt>cityCode</tt>.
     * 
     * @return property value of cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Setter method for property <tt>cityCode</tt>.
     * 
     * @param cityCode value to be assigned to property cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * Getter method for property <tt>area</tt>.
     * 
     * @return property value of area
     */
    public String getArea() {
        return area;
    }

    /**
     * Setter method for property <tt>area</tt>.
     * 
     * @param area value to be assigned to property area
     */
    public void setArea(String area) {
        this.area = area;
    }
}
