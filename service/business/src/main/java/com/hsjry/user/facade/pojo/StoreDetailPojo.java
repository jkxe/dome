/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 门店详情
 * @author huangbb
 * @version $Id: StoreDetailPojo.java, v 1.0 2018年5月4日 下午3:32:24 huangbb Exp $
 */
public class StoreDetailPojo implements Serializable {

    private static final long serialVersionUID = 6964139352946064540L;
    /** 商户用户ID */
    private String                             userId;
    /** 门店信息 */
    private UserStorePojo                      userStorePojo;
    /** 员工信息 */
    private List<StaffInfoPojo>                staffInfoPojos;
    /** 影像信息 */
    private List<UserImageDiffDataPojo>        dataPojos;
    /**营销信息*/
    private MarketBasicInfoPojo                marketBasicInfo;
    /**账户信息*/
    private List<UserFinancialInstrumentsPojo> financialInstrumentsPojo;
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
     * Getter method for property <tt>userStorePojo</tt>.
     * 
     * @return property value of userStorePojo
     */
    public UserStorePojo getUserStorePojo() {
        return userStorePojo;
    }
    /**
     * Setter method for property <tt>userStorePojo</tt>.
     * 
     * @param userStorePojo value to be assigned to property userStorePojo
     */
    public void setUserStorePojo(UserStorePojo userStorePojo) {
        this.userStorePojo = userStorePojo;
    }
    /**
     * Getter method for property <tt>staffInfoPojos</tt>.
     * 
     * @return property value of staffInfoPojos
     */
    public List<StaffInfoPojo> getStaffInfoPojos() {
        return staffInfoPojos;
    }
    /**
     * Setter method for property <tt>staffInfoPojos</tt>.
     * 
     * @param staffInfoPojos value to be assigned to property staffInfoPojos
     */
    public void setStaffInfoPojos(List<StaffInfoPojo> staffInfoPojos) {
        this.staffInfoPojos = staffInfoPojos;
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
    /**
     * Getter method for property <tt>marketBasicInfo</tt>.
     * 
     * @return property value of marketBasicInfo
     */
    public MarketBasicInfoPojo getMarketBasicInfo() {
        return marketBasicInfo;
    }
    /**
     * Setter method for property <tt>marketBasicInfo</tt>.
     * 
     * @param marketBasicInfo value to be assigned to property marketBasicInfo
     */
    public void setMarketBasicInfo(MarketBasicInfoPojo marketBasicInfo) {
        this.marketBasicInfo = marketBasicInfo;
    }
    /**
     * Getter method for property <tt>financialInstrumentsPojo</tt>.
     * 
     * @return property value of financialInstrumentsPojo
     */
    public List<UserFinancialInstrumentsPojo> getFinancialInstrumentsPojo() {
        return financialInstrumentsPojo;
    }
    /**
     * Setter method for property <tt>financialInstrumentsPojo</tt>.
     * 
     * @param financialInstrumentsPojo value to be assigned to property financialInstrumentsPojo
     */
    public void setFinancialInstrumentsPojo(List<UserFinancialInstrumentsPojo> financialInstrumentsPojo) {
        this.financialInstrumentsPojo = financialInstrumentsPojo;
    }
    
    
}
