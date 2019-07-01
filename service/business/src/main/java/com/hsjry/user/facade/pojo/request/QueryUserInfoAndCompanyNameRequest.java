/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumAddressClassCode;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author lilin22830
 * @version $Id: QueryUserInfoAndUnitNameRequest.java, v 0.1 Aug 21, 2017 9:10:10 AM lilin22830 Exp $
 */
public class QueryUserInfoAndCompanyNameRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -2322030438464756726L;
    
    @NotNull(errorCode = "000001", message = "单位名称 ")
    @NotBlank(errorCode = "000001", message = "单位名称 ")
    private String companyName;
    
    /**地址信息*/
    @NotNull(errorCode = "000001", message = "地址信息")
    @NotBlank(errorCode = "000001", message = "地址信息")
    @Length(min=2,errorCode = "000002", message = "地址信息")
    private String address;
    
    /**省代码*/
    private String provinceCode;
    
    /**市代码*/
    private String cityCode;
    
    /**区代码*/
    private String area;
    
    /**地址分类列表*/
    @NotNull(errorCode = "000001", message = "地址分类列表")
    private EnumAddressClassCode addressClassCode;
    /**
     * Getter method for property <tt>companyName</tt>.
     * 
     * @return property value of companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter method for property <tt>companyName</tt>.
     * 
     * @param companyName value to be assigned to property companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    
}
