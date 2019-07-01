/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 查询所属门店信息请求类
 * @author hongsj
 * @version $Id: QueryUserOrganRequest.java, v 1.0 2017年3月25日 下午3:03:21 hongsj Exp $
 */
public class QueryUserOrganRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -754371814067997781L;

    /**商户用户ID*/
    @NotNull(errorCode = "000001", message = "商户用户ID")
    @NotEmpty(errorCode = "000001", message = "商户用户ID")
    private String            userId;
    /** 门店名称 模糊查询*/
    private String            organName;
    /** 省*/
    private String            provinceCode;
    /** 市*/
    private String            cityCode;
    /** 门店区域*/
    private String            area;
    /** 门店代表*/
    private String            dutyName;
    /** 联系方式*/
    private String            contactWay;
    /** 启用状态*/
    private EnumBool          enableStatus;

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

    /**
     * Getter method for property <tt>dutyName</tt>.
     * 
     * @return property value of dutyName
     */
    public String getDutyName() {
        return dutyName;
    }

    /**
     * Setter method for property <tt>dutyName</tt>.
     * 
     * @param dutyName value to be assigned to property dutyName
     */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    /**
     * Getter method for property <tt>contactWay</tt>.
     * 
     * @return property value of contactWay
     */
    public String getContactWay() {
        return contactWay;
    }

    /**
     * Setter method for property <tt>contactWay</tt>.
     * 
     * @param contactWay value to be assigned to property contactWay
     */
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    /**
     * Getter method for property <tt>enableStatus</tt>.
     * 
     * @return property value of enableStatus
     */
    public EnumBool getEnableStatus() {
        return enableStatus;
    }

    /**
     * Setter method for property <tt>enableStatus</tt>.
     * 
     * @param enableStatus value to be assigned to property enableStatus
     */
    public void setEnableStatus(EnumBool enableStatus) {
        this.enableStatus = enableStatus;
    }

}
