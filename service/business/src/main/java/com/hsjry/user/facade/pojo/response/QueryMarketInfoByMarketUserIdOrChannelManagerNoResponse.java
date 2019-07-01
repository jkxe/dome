/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.MarketBasicInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMarketInfoByMarketUserIdOrChannelManagerNoResponse.java, v 1.0 2018年6月1日 上午10:45:12 zhengqy15963 Exp $
 */
public class QueryMarketInfoByMarketUserIdOrChannelManagerNoResponse implements Serializable {

    /**  */
    private static final long         serialVersionUID = 5945723297960659870L;

    /**省  */
    private String                    provinceCode;
    /**市  */
    private String                    cityCode;
    /**区  */
    private String                    area;
    /**部门/营销中心*/
    private String                    marketDepartment;
    /**渠道经理姓名*/
    private String                    channelManager;
    /**渠道经理联系电话*/
    private String                    channelManagerTelphone;
    /**渠道经理工号 */
    private String                    channelManagerNo;
    /**渠道经理userId*/
    private String                    marketUserId;
    /**渠道经理层级--(0:业务负责人，1：业务员)*/
    private String                    managerOrganCode;
    /**下级渠道经理列表*/
    private List<MarketBasicInfoPojo> subMarketManagers;
    /**渠道经理所在的组织的组织id*/
    private  String organId;
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
     * Getter method for property <tt>marketDepartment</tt>.
     * 
     * @return property value of marketDepartment
     */
    public String getMarketDepartment() {
        return marketDepartment;
    }

    /**
     * Setter method for property <tt>marketDepartment</tt>.
     * 
     * @param marketDepartment value to be assigned to property marketDepartment
     */
    public void setMarketDepartment(String marketDepartment) {
        this.marketDepartment = marketDepartment;
    }

    /**
     * Getter method for property <tt>channelManager</tt>.
     * 
     * @return property value of channelManager
     */
    public String getChannelManager() {
        return channelManager;
    }

    /**
     * Setter method for property <tt>channelManager</tt>.
     * 
     * @param channelManager value to be assigned to property channelManager
     */
    public void setChannelManager(String channelManager) {
        this.channelManager = channelManager;
    }

    /**
     * Getter method for property <tt>channelManagerTelphone</tt>.
     * 
     * @return property value of channelManagerTelphone
     */
    public String getChannelManagerTelphone() {
        return channelManagerTelphone;
    }

    /**
     * Setter method for property <tt>channelManagerTelphone</tt>.
     * 
     * @param channelManagerTelphone value to be assigned to property channelManagerTelphone
     */
    public void setChannelManagerTelphone(String channelManagerTelphone) {
        this.channelManagerTelphone = channelManagerTelphone;
    }

    /**
     * Getter method for property <tt>channelManagerNo</tt>.
     * 
     * @return property value of channelManagerNo
     */
    public String getChannelManagerNo() {
        return channelManagerNo;
    }

    /**
     * Setter method for property <tt>channelManagerNo</tt>.
     * 
     * @param channelManagerNo value to be assigned to property channelManagerNo
     */
    public void setChannelManagerNo(String channelManagerNo) {
        this.channelManagerNo = channelManagerNo;
    }

    /**
     * Getter method for property <tt>marketUserId</tt>.
     * 
     * @return property value of marketUserId
     */
    public String getMarketUserId() {
        return marketUserId;
    }

    /**
     * Setter method for property <tt>marketUserId</tt>.
     * 
     * @param marketUserId value to be assigned to property marketUserId
     */
    public void setMarketUserId(String marketUserId) {
        this.marketUserId = marketUserId;
    }

    /**
     * Getter method for property <tt>managerOrganCode</tt>.
     * 
     * @return property value of managerOrganCode
     */
    public String getManagerOrganCode() {
        return managerOrganCode;
    }

    /**
     * Setter method for property <tt>managerOrganCode</tt>.
     * 
     * @param managerOrganCode value to be assigned to property managerOrganCode
     */
    public void setManagerOrganCode(String managerOrganCode) {
        this.managerOrganCode = managerOrganCode;
    }

    /**
     * Getter method for property <tt>subMarketManagers</tt>.
     * 
     * @return property value of subMarketManagers
     */
    public List<MarketBasicInfoPojo> getSubMarketManagers() {
        return subMarketManagers;
    }

    /**
     * Setter method for property <tt>subMarketManagers</tt>.
     * 
     * @param subMarketManagers value to be assigned to property subMarketManagers
     */
    public void setSubMarketManagers(List<MarketBasicInfoPojo> subMarketManagers) {
        this.subMarketManagers = subMarketManagers;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }
}
