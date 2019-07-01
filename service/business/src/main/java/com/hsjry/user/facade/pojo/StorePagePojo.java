/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author zhengqy15963
 * @version $Id: StorePagePojo.java, v 1.0 2018年5月7日 下午4:50:59 zhengqy15963 Exp $
 */
public class StorePagePojo implements Serializable {

    /**  */
    private static final long           serialVersionUID = -8603187778577702789L;
    /**门店id  */
    private String                      organId;
    /**门店名称  */
    private String                      organName;
    /**所属经销商  */
    private String                      merchantName;
    /**省  */
    private String                      provinceCode;
    /**市  */
    private String                      cityCode;
    /**区  */
    private String                      area;
    /**地址  */
    private String                      address;
    /**门店电话  */
    private String                      contactWay;
    /**门店负责人  */
    private String                      dutyName;
    /**门店负责人电话  */
    private String                      dutyTelphone;
    /**员工人数  */
    private int                         staffNum;
    /**审核状态 */
    private EnumVerifyStatus            auditStatus;
    /**启用状态*/
    private EnumObjectStatus            enableStatus;
    /**创建日期*/
    private Date                        createTime;
    /**门店影像信息*/
    private List<UserImageDiffDataPojo> dataPojos;

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
     * Getter method for property <tt>dutyTelphone</tt>.
     * 
     * @return property value of dutyTelphone
     */
    public String getDutyTelphone() {
        return dutyTelphone;
    }

    /**
     * Setter method for property <tt>dutyTelphone</tt>.
     * 
     * @param dutyTelphone value to be assigned to property dutyTelphone
     */
    public void setDutyTelphone(String dutyTelphone) {
        this.dutyTelphone = dutyTelphone;
    }

    /**
     * Getter method for property <tt>staffNum</tt>.
     * 
     * @return property value of staffNum
     */
    public int getStaffNum() {
        return staffNum;
    }

    /**
     * Setter method for property <tt>staffNum</tt>.
     * 
     * @param staffNum value to be assigned to property staffNum
     */
    public void setStaffNum(int staffNum) {
        this.staffNum = staffNum;
    }

    /**
     * Getter method for property <tt>auditStatus</tt>.
     * 
     * @return property value of auditStatus
     */
    public EnumVerifyStatus getAuditStatus() {
        return auditStatus;
    }

    /**
     * Setter method for property <tt>auditStatus</tt>.
     * 
     * @param auditStatus value to be assigned to property auditStatus
     */
    public void setAuditStatus(EnumVerifyStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * Getter method for property <tt>enableStatus</tt>.
     * 
     * @return property value of enableStatus
     */
    public EnumObjectStatus getEnableStatus() {
        return enableStatus;
    }

    /**
     * Setter method for property <tt>enableStatus</tt>.
     * 
     * @param enableStatus value to be assigned to property enableStatus
     */
    public void setEnableStatus(EnumObjectStatus enableStatus) {
        this.enableStatus = enableStatus;
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
     * Getter method for property <tt>dataPojos</tt>.
     * 
     * @return property value of dataPojos
     */
    public List<UserImageDiffDataPojo> getDataPojos() {
        return dataPojos;
    }

    /**
     * Setter method for property <tt>dataPojos</tt>.
     * 
     * @param dataPojos value to be assigned to property dataPojos
     */
    public void setDataPojos(List<UserImageDiffDataPojo> dataPojos) {
        this.dataPojos = dataPojos;
    }

}
