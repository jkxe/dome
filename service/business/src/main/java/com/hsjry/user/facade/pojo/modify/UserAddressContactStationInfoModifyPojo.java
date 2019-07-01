package com.hsjry.user.facade.pojo.modify;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumAddressClassCode;
import com.hsjry.user.facade.pojo.enums.EnumLiveCondition;

import net.sf.oval.constraint.NotNull;

/**
 * 地址联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserAddressContactStationPojo.java, v 1.0 2017年3月13日 下午4:52:13 jiangjd12837 Exp $
 */
public class UserAddressContactStationInfoModifyPojo extends UserContactStationInfoModifyPojo {
    /**  */
    private static final long           serialVersionUID = -6252344460155063358L;
    //地址分类代码
    @NotNull(errorCode = "000001", message = "地址分类代码")
    private EnumAddressClassCode        addressClassCode;
    //省
    private String                      provinceCode;
    //市
    private String                      cityCode;
    //全地址
    private String                      address;
    //经度
    private String                      longitude;
    //纬度
    private String                      latitude;
    //所属名称
    private String                      ascription;
    //邮政编码
    private String                      zipcode;
    //区
    private String                      area;
    //国家
    private String                      nationality;
    //居住情况
    private EnumLiveCondition           liveCondition;
    //是否收货地址
    @NotNull(errorCode = "000001", message = "是否收货地址")
    private EnumBool                    consigneeStationFlag;
    //收货人信息
    private UserConsigneeInfoModifyPojo userConsigneeInfoPojo;

    /**
     * Getter method for property <tt>consigneeStationFlag</tt>.
     * 
     * @return property value of consigneeStationFlag
     */
    public EnumBool getConsigneeStationFlag() {
        return consigneeStationFlag;
    }

    /**
     * Setter method for property <tt>consigneeStationFlag</tt>.
     * 
     * @param consigneeStationFlag value to be assigned to property consigneeStationFlag
     */
    public void setConsigneeStationFlag(EnumBool consigneeStationFlag) {
        this.consigneeStationFlag = consigneeStationFlag;
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
     * Getter method for property <tt>ascription</tt>.
     * 
     * @return property value of ascription
     */
    public String getAscription() {
        return ascription;
    }

    /**
     * Setter method for property <tt>ascription</tt>.
     * 
     * @param ascription value to be assigned to property ascription
     */
    public void setAscription(String ascription) {
        this.ascription = ascription;
    }

    /**
     * Getter method for property <tt>zipcode</tt>.
     * 
     * @return property value of zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Setter method for property <tt>zipcode</tt>.
     * 
     * @param zipcode value to be assigned to property zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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
     * Getter method for property <tt>nationality</tt>.
     * 
     * @return property value of nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Setter method for property <tt>nationality</tt>.
     * 
     * @param nationality value to be assigned to property nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Getter method for property <tt>liveCondition</tt>.
     * 
     * @return property value of liveCondition
     */
    public EnumLiveCondition getLiveCondition() {
        return liveCondition;
    }

    /**
     * Setter method for property <tt>liveCondition</tt>.
     * 
     * @param liveCondition value to be assigned to property liveCondition
     */
    public void setLiveCondition(EnumLiveCondition liveCondition) {
        this.liveCondition = liveCondition;
    }

    /**
     * Getter method for property <tt>userConsigneeInfoPojo</tt>.
     * 
     * @return property value of userConsigneeInfoPojo
     */
    public UserConsigneeInfoModifyPojo getUserConsigneeInfoPojo() {
        return userConsigneeInfoPojo;
    }

    /**
     * Setter method for property <tt>userConsigneeInfoPojo</tt>.
     * 
     * @param userConsigneeInfoPojo value to be assigned to property userConsigneeInfoPojo
     */
    public void setUserConsigneeInfoPojo(UserConsigneeInfoModifyPojo userConsigneeInfoPojo) {
        this.userConsigneeInfoPojo = userConsigneeInfoPojo;
    }
}
