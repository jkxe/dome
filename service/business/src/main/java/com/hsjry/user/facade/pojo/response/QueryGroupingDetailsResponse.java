/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryGroupingDetailsResponse.java, v 1.0 2017年6月16日 下午4:49:25 jiangjd12837 Exp $
 */
public class QueryGroupingDetailsResponse implements Serializable {

    /**  */
    private static final long  serialVersionUID = 8409146538918881540L;
    /**
     * 组织Id
     */
    private String             organId;
    /**
     * 角色Id
     */
    private EnumSystemRoleType roleId;
    /**
     * 组织编号
     */
    private String             organNo;
    /**
     *机构客户id
     */
    private String             organCustomerId;
    /**
     * 组织名称
     */
    private String             organName;
    /**
     * 省
     */
    private String             provinceCode;
    /**
     * 市
     */
    private String             cityCode;
    /**
     * 区
     */
    private String             area;
    /**
     * 地址
     */
    private String             address;
    /**
     * 经度
     */
    private String             longitude;
    /**
     * 维度
     */
    private String             latitude;
    /**
     * 组织负责人
     */
    private String             dutyName;
    /**
     * 负责人客户Id
     */
    private String             liableUserId;
    /**
     * 组织联系方式
     */
    private String             contactWay;
    /**
     * 组织描述
     */
    private String             organDesc;
    /**
     * 组织创建时间
     */
    private Date               createTime;
    /**
     * 组织修改时间
     */
    private Date               updateTime;

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }

    /**
     * Getter method for property <tt>roleId</tt>.
     * 
     * @return property value of roleId
     */
    public EnumSystemRoleType getRoleId() {
        return roleId;
    }

    /**
     * Setter method for property <tt>roleId</tt>.
     * 
     * @param roleId value to be assigned to property roleId
     */
    public void setRoleId(EnumSystemRoleType roleId) {
        this.roleId = roleId;
    }

    /**
     * Getter method for property <tt>organNo</tt>.
     * 
     * @return property value of organNo
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * Setter method for property <tt>organNo</tt>.
     * 
     * @param organNo value to be assigned to property organNo
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
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

    /**
     * Getter method for property <tt>address</tt>.
     * 
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     * 
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for property <tt>longitude</tt>.
     * 
     * @return property value of longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter method for property <tt>longitude</tt>.
     * 
     * @param longitude value to be assigned to property longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter method for property <tt>latitude</tt>.
     * 
     * @return property value of latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter method for property <tt>latitude</tt>.
     * 
     * @param latitude value to be assigned to property latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
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
     * Getter method for property <tt>liableUserId</tt>.
     * 
     * @return property value of liableUserId
     */
    public String getLiableUserId() {
        return liableUserId;
    }

    /**
     * Setter method for property <tt>liableUserId</tt>.
     * 
     * @param liableUserId value to be assigned to property liableUserId
     */
    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
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
     * Getter method for property <tt>organDesc</tt>.
     * 
     * @return property value of organDesc
     */
    public String getOrganDesc() {
        return organDesc;
    }

    /**
     * Setter method for property <tt>organDesc</tt>.
     * 
     * @param organDesc value to be assigned to property organDesc
     */
    public void setOrganDesc(String organDesc) {
        this.organDesc = organDesc;
    }

    /**
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property <tt>updateTime</tt>.
     * 
     * @return property value of updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Setter method for property <tt>updateTime</tt>.
     * 
     * @param updateTime value to be assigned to property updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Getter method for property <tt>organCustomerId</tt>.
     * 
     * @return property value of organCustomerId
     */
    public String getOrganCustomerId() {
        return organCustomerId;
    }

    /**
     * Setter method for property <tt>organCustomerId</tt>.
     * 
     * @param organCustomerId value to be assigned to property organCustomerId
     */
    public void setOrganCustomerId(String organCustomerId) {
        this.organCustomerId = organCustomerId;
    }

}
