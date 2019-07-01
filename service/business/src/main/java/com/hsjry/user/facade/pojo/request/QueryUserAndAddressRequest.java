package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumAddressClassCode;

public class QueryUserAndAddressRequest implements Serializable {

	
    private static final long serialVersionUID = -6557207948439835921L;

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
	private List<EnumAddressClassCode> addressClassCodeList;

    
    /**
     * Getter method for property <tt>address</tt>.
     * 
     * @return property value of userId
     */
	public String getAddress() {
		return address;
	}

	
	/**
     * Setter method for property <tt>address</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
	public void setAddress(String address) {
		this.address = address;
	}


    /**
     * Getter method for property <tt>addressClassCodeList</tt>.
     * 
     * @return property value of addressClassCodeList
     */
    public List<EnumAddressClassCode> getAddressClassCodeList() {
        return addressClassCodeList;
    }


    /**
     * Setter method for property <tt>addressClassCodeList</tt>.
     * 
     * @param addressClassCodeList value to be assigned to property addressClassCodeList
     */
    public void setAddressClassCodeList(List<EnumAddressClassCode> addressClassCodeList) {
        this.addressClassCodeList = addressClassCodeList;
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
