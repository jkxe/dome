/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author jingqi17258
 * @version $Id: AliPayInfoPojo.java, v 1.0 2017年6月17日 下午5:31:16 jingqi17258 Exp $
 */
public class AliPayInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -3844132128419999215L;

    /**  */
    /** 客户编号                    */
    private String            userId;
    /** 地址信息.省份             */
    private String            province;
    /** 地址信息.城市             */
    private String            city;
    /** 账户共享信息.是否通过实名认证 */
    private String            ifRealname;
    /** 账户共享信息.是否是学生            */
    private String            ifStudent;
    /** 账户共享信息.是否为银行卡认证 */
    private String            ifBankCardCheck;
    /** 账户共享信息.是否为手机号认证 */
    private String            ifMoileCheck;
    /** 账户共享信息.省份名称         */
    private String            provinceName;
    /** 账户共享信息.市名称          */
    private String            cityName;
    /** 账户共享信息.区县名称         */
    private String            zoneName;
    /** 账户共享信息.详细地址         */
    private String            address;
    /** 账户共享信息.身份证号         */
    private String            idNo;
    /** 注册页面完成耗时                */
    private Long              registerTime;
    /** 申请资料页面完成耗时          */
    private Long              applyTime;
    /** 用户状态*/
    private String            userStatus;

    /**
     * Getter method for property <tt>userStatus</tt>.
     * 
     * @return property value of userStatus
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * Setter method for property <tt>userStatus</tt>.
     * 
     * @param userStatus value to be assigned to property userStatus
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
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
     * Getter method for property <tt>province</tt>.
     * 
     * @return property value of province
     */
    public String getProvince() {
        return province;
    }

    /**
     * Setter method for property <tt>province</tt>.
     * 
     * @param province value to be assigned to property province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Getter method for property <tt>city</tt>.
     * 
     * @return property value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for property <tt>city</tt>.
     * 
     * @param city value to be assigned to property city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for property <tt>ifRealname</tt>.
     * 
     * @return property value of ifRealname
     */
    public String getIfRealname() {
        return ifRealname;
    }

    /**
     * Setter method for property <tt>ifRealname</tt>.
     * 
     * @param ifRealname value to be assigned to property ifRealname
     */
    public void setIfRealname(String ifRealname) {
        this.ifRealname = ifRealname;
    }

    /**
     * Getter method for property <tt>ifStudent</tt>.
     * 
     * @return property value of ifStudent
     */
    public String getIfStudent() {
        return ifStudent;
    }

    /**
     * Setter method for property <tt>ifStudent</tt>.
     * 
     * @param ifStudent value to be assigned to property ifStudent
     */
    public void setIfStudent(String ifStudent) {
        this.ifStudent = ifStudent;
    }

    /**
     * Getter method for property <tt>ifBankCardCheck</tt>.
     * 
     * @return property value of ifBankCardCheck
     */
    public String getIfBankCardCheck() {
        return ifBankCardCheck;
    }

    /**
     * Setter method for property <tt>ifBankCardCheck</tt>.
     * 
     * @param ifBankCardCheck value to be assigned to property ifBankCardCheck
     */
    public void setIfBankCardCheck(String ifBankCardCheck) {
        this.ifBankCardCheck = ifBankCardCheck;
    }

    /**
     * Getter method for property <tt>ifMoileCheck</tt>.
     * 
     * @return property value of ifMoileCheck
     */
    public String getIfMoileCheck() {
        return ifMoileCheck;
    }

    /**
     * Setter method for property <tt>ifMoileCheck</tt>.
     * 
     * @param ifMoileCheck value to be assigned to property ifMoileCheck
     */
    public void setIfMoileCheck(String ifMoileCheck) {
        this.ifMoileCheck = ifMoileCheck;
    }

    /**
     * Getter method for property <tt>provinceName</tt>.
     * 
     * @return property value of provinceName
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * Setter method for property <tt>provinceName</tt>.
     * 
     * @param provinceName value to be assigned to property provinceName
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * Getter method for property <tt>cityName</tt>.
     * 
     * @return property value of cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Setter method for property <tt>cityName</tt>.
     * 
     * @param cityName value to be assigned to property cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Getter method for property <tt>zoneName</tt>.
     * 
     * @return property value of zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * Setter method for property <tt>zoneName</tt>.
     * 
     * @param zoneName value to be assigned to property zoneName
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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
     * Getter method for property <tt>idNo</tt>.
     * 
     * @return property value of idNo
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Setter method for property <tt>idNo</tt>.
     * 
     * @param idNo value to be assigned to property idNo
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * Getter method for property <tt>registerTime</tt>.
     * 
     * @return property value of registerTime
     */
    public Long getRegisterTime() {
        return registerTime;
    }

    /**
     * Setter method for property <tt>registerTime</tt>.
     * 
     * @param registerTime value to be assigned to property registerTime
     */
    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * Getter method for property <tt>applyTime</tt>.
     * 
     * @return property value of applyTime
     */
    public Long getApplyTime() {
        return applyTime;
    }

    /**
     * Setter method for property <tt>applyTime</tt>.
     * 
     * @param applyTime value to be assigned to property applyTime
     */
    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

}
