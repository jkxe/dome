/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumAddressClassCode;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryPartnerViewPageRequest.java, v 1.0 2017年3月30日 下午5:43:56 jiangjd12837 Exp $
 */
public class QueryPartnerViewPageRequest implements Serializable {

    /**  */
    private static final long    serialVersionUID = -8036777031202303511L;
    //合作方机构编号
    private String               userId;
    //合作方机构名称
    private String               organName;
    //地址联系点类型
    private EnumAddressClassCode addressClassCode;
    //省
    private String               provinceCode;
    //市
    private String               cityCode;
    //区
    private String               area;
    //记录状态
    private EnumObjectStatus     recordStatus;

    /**
     * Getter method for property <tt>recordStatus</tt>.
     * 
     * @return property value of recordStatus
     */
    public EnumObjectStatus getRecordStatus() {
        return recordStatus;
    }

    /**
     * Setter method for property <tt>recordStatus</tt>.
     * 
     * @param recordStatus value to be assigned to property recordStatus
     */
    public void setRecordStatus(EnumObjectStatus recordStatus) {
        this.recordStatus = recordStatus;
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
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
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
