/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhengqy15963
 * @version $Id: MerchantContentPojo.java, v 1.0 2018年5月15日 下午7:42:20 zhengqy15963 Exp $
 */
public class MerchantContentPojo implements Serializable {

    /**  */
    private static final long            serialVersionUID = -4069107069069462208L;
    /**机构客户id */
    private String                       userId;
    /**修改前用户名 */
    private String                       oldUserName;
    /**修改后用户名 */
    private String                       newUserName;
    /**修改前基本信息*/
    private MerchantBasicInfoPojo        oldMerchantBasicInfoPojo;
    /**修改后基本信息*/
    private MerchantBasicInfoPojo        newMerchantBasicInfoPojo;
    /**修改前营销信息*/
    private MarketBasicInfoPojo          oldMarketBasicInfo;
    /**修改后营销信息*/
    private MarketBasicInfoPojo          newMarketBasicInfo;
    /**修改前账户信息*/
    private UserFinancialInstrumentsPojo oldFinancialInstrumentsPojo;
    /**修改后账户信息*/
    private UserFinancialInstrumentsPojo newFinancialInstrumentsPojo;
    /**修改前影像信息*/
    private List<UserImageDiffDataPojo>  oldImageBasicInfo;
    /**修改后影像信息*/
    private List<UserImageDiffDataPojo>  newImageBasicInfo;
    /**需删除的账户信息*/
    private UserFinancialInstrumentsPojo delFinancialInstrumentsPojos;
    /**需删除的影像信息*/
    private List<UserImageDiffDataPojo>  delImageBasicInfo;

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
     * Getter method for property <tt>oldUserName</tt>.
     * 
     * @return property value of oldUserName
     */
    public String getOldUserName() {
        return oldUserName;
    }

    /**
     * Setter method for property <tt>oldUserName</tt>.
     * 
     * @param oldUserName value to be assigned to property oldUserName
     */
    public void setOldUserName(String oldUserName) {
        this.oldUserName = oldUserName;
    }

    /**
     * Getter method for property <tt>newUserName</tt>.
     * 
     * @return property value of newUserName
     */
    public String getNewUserName() {
        return newUserName;
    }

    /**
     * Setter method for property <tt>newUserName</tt>.
     * 
     * @param newUserName value to be assigned to property newUserName
     */
    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    /**
     * Getter method for property <tt>oldMerchantBasicInfoPojo</tt>.
     * 
     * @return property value of oldMerchantBasicInfoPojo
     */
    public MerchantBasicInfoPojo getOldMerchantBasicInfoPojo() {
        return oldMerchantBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>oldMerchantBasicInfoPojo</tt>.
     * 
     * @param oldMerchantBasicInfoPojo value to be assigned to property oldMerchantBasicInfoPojo
     */
    public void setOldMerchantBasicInfoPojo(MerchantBasicInfoPojo oldMerchantBasicInfoPojo) {
        this.oldMerchantBasicInfoPojo = oldMerchantBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>newMerchantBasicInfoPojo</tt>.
     * 
     * @return property value of newMerchantBasicInfoPojo
     */
    public MerchantBasicInfoPojo getNewMerchantBasicInfoPojo() {
        return newMerchantBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>newMerchantBasicInfoPojo</tt>.
     * 
     * @param newMerchantBasicInfoPojo value to be assigned to property newMerchantBasicInfoPojo
     */
    public void setNewMerchantBasicInfoPojo(MerchantBasicInfoPojo newMerchantBasicInfoPojo) {
        this.newMerchantBasicInfoPojo = newMerchantBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>oldMarketBasicInfo</tt>.
     * 
     * @return property value of oldMarketBasicInfo
     */
    public MarketBasicInfoPojo getOldMarketBasicInfo() {
        return oldMarketBasicInfo;
    }

    /**
     * Setter method for property <tt>oldMarketBasicInfo</tt>.
     * 
     * @param oldMarketBasicInfo value to be assigned to property oldMarketBasicInfo
     */
    public void setOldMarketBasicInfo(MarketBasicInfoPojo oldMarketBasicInfo) {
        this.oldMarketBasicInfo = oldMarketBasicInfo;
    }

    /**
     * Getter method for property <tt>newMarketBasicInfo</tt>.
     * 
     * @return property value of newMarketBasicInfo
     */
    public MarketBasicInfoPojo getNewMarketBasicInfo() {
        return newMarketBasicInfo;
    }

    /**
     * Setter method for property <tt>newMarketBasicInfo</tt>.
     * 
     * @param newMarketBasicInfo value to be assigned to property newMarketBasicInfo
     */
    public void setNewMarketBasicInfo(MarketBasicInfoPojo newMarketBasicInfo) {
        this.newMarketBasicInfo = newMarketBasicInfo;
    }

    /**
     * Getter method for property <tt>oldFinancialInstrumentsPojo</tt>.
     * 
     * @return property value of oldFinancialInstrumentsPojo
     */
    public UserFinancialInstrumentsPojo getOldFinancialInstrumentsPojo() {
        return oldFinancialInstrumentsPojo;
    }

    /**
     * Setter method for property <tt>oldFinancialInstrumentsPojo</tt>.
     * 
     * @param oldFinancialInstrumentsPojo value to be assigned to property oldFinancialInstrumentsPojo
     */
    public void setOldFinancialInstrumentsPojo(UserFinancialInstrumentsPojo oldFinancialInstrumentsPojo) {
        this.oldFinancialInstrumentsPojo = oldFinancialInstrumentsPojo;
    }

    /**
     * Getter method for property <tt>newFinancialInstrumentsPojo</tt>.
     * 
     * @return property value of newFinancialInstrumentsPojo
     */
    public UserFinancialInstrumentsPojo getNewFinancialInstrumentsPojo() {
        return newFinancialInstrumentsPojo;
    }

    /**
     * Setter method for property <tt>newFinancialInstrumentsPojo</tt>.
     * 
     * @param newFinancialInstrumentsPojo value to be assigned to property newFinancialInstrumentsPojo
     */
    public void setNewFinancialInstrumentsPojo(UserFinancialInstrumentsPojo newFinancialInstrumentsPojo) {
        this.newFinancialInstrumentsPojo = newFinancialInstrumentsPojo;
    }

    /**
     * Getter method for property <tt>oldImageBasicInfo</tt>.
     * 
     * @return property value of oldImageBasicInfo
     */
    public List<UserImageDiffDataPojo> getOldImageBasicInfo() {
        return oldImageBasicInfo;
    }

    /**
     * Setter method for property <tt>oldImageBasicInfo</tt>.
     * 
     * @param oldImageBasicInfo value to be assigned to property oldImageBasicInfo
     */
    public void setOldImageBasicInfo(List<UserImageDiffDataPojo> oldImageBasicInfo) {
        this.oldImageBasicInfo = oldImageBasicInfo;
    }

    /**
     * Getter method for property <tt>newImageBasicInfo</tt>.
     * 
     * @return property value of newImageBasicInfo
     */
    public List<UserImageDiffDataPojo> getNewImageBasicInfo() {
        return newImageBasicInfo;
    }

    /**
     * Setter method for property <tt>newImageBasicInfo</tt>.
     * 
     * @param newImageBasicInfo value to be assigned to property newImageBasicInfo
     */
    public void setNewImageBasicInfo(List<UserImageDiffDataPojo> newImageBasicInfo) {
        this.newImageBasicInfo = newImageBasicInfo;
    }

    /**
     * Getter method for property <tt>delFinancialInstrumentsPojos</tt>.
     * 
     * @return property value of delFinancialInstrumentsPojos
     */
    public UserFinancialInstrumentsPojo getDelFinancialInstrumentsPojos() {
        return delFinancialInstrumentsPojos;
    }

    /**
     * Setter method for property <tt>delFinancialInstrumentsPojos</tt>.
     * 
     * @param delFinancialInstrumentsPojos value to be assigned to property delFinancialInstrumentsPojos
     */
    public void setDelFinancialInstrumentsPojos(UserFinancialInstrumentsPojo delFinancialInstrumentsPojos) {
        this.delFinancialInstrumentsPojos = delFinancialInstrumentsPojos;
    }

    /**
     * Getter method for property <tt>delImageBasicInfo</tt>.
     * 
     * @return property value of delImageBasicInfo
     */
    public List<UserImageDiffDataPojo> getDelImageBasicInfo() {
        return delImageBasicInfo;
    }

    /**
     * Setter method for property <tt>delImageBasicInfo</tt>.
     * 
     * @param delImageBasicInfo value to be assigned to property delImageBasicInfo
     */
    public void setDelImageBasicInfo(List<UserImageDiffDataPojo> delImageBasicInfo) {
        this.delImageBasicInfo = delImageBasicInfo;
    }

}
