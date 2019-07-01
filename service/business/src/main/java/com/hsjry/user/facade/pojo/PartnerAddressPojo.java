/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: PartnerAddress.java, v 1.0 2017年7月13日 下午3:27:46 jiangjd12837 Exp $
 */
public class PartnerAddressPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -6886997897958158870L;
    //联系点ID
    private String            stationId;
    //省
    private String            provinceCode;
    //市
    private String            cityCode;
    //区
    private String            area;
    //国家
    private String            nationality;
    //经营地址
    private String            manageAddress;

    /**
     * Getter method for property <tt>stationId</tt>.
     * 
     * @return property value of stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Setter method for property <tt>stationId</tt>.
     * 
     * @param stationId value to be assigned to property stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
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
     * Getter method for property <tt>manageAddress</tt>.
     * 
     * @return property value of manageAddress
     */
    public String getManageAddress() {
        return manageAddress;
    }

    /**
     * Setter method for property <tt>manageAddress</tt>.
     * 
     * @param manageAddress value to be assigned to property manageAddress
     */
    public void setManageAddress(String manageAddress) {
        this.manageAddress = manageAddress;
    }

}
