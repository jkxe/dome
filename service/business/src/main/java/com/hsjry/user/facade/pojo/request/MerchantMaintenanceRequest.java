/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.AttachmentDataInfoPojo;
import com.hsjry.user.facade.pojo.MarketBasicInfoPojo;
import com.hsjry.user.facade.pojo.MerchantBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;
import com.hsjry.user.facade.pojo.UserImageDiffDataPojo;
import com.hsjry.user.facade.pojo.enums.EnumRegisterKind;

/**
 * 商户维护入参
 * @author zhengqy15963
 * @version $Id: MerchantMaintenanceRequest.java, v 1.0 2018年5月10日 上午11:15:38 zhengqy15963 Exp $
 */
public class MerchantMaintenanceRequest implements Serializable {

    /**  */
    private static final long            serialVersionUID = -2655229922708615270L;
    /**机构用户id */
    private String                       userId;
    /**用户名 */
    private String                       userName;
    /**基本信息*/
    private MerchantBasicInfoPojo        merchantBasicInfoPojo;
    /**营销信息*/
    private MarketBasicInfoPojo          marketBasicInfo;
    /**账户信息*/
    private UserFinancialInstrumentsPojo financialInstrumentsPojo;
    /**影像信息*/
    private List<UserImageDiffDataPojo>  imageBasicInfo;
    /**需删除的账户信息*/
    private UserFinancialInstrumentsPojo delFinancialInstrumentsPojos;
    /**需删除的影像信息*/
    private List<UserImageDiffDataPojo>  delImageBasicInfo;
    /**商户信息变更材料列表*/
    private List<AttachmentDataInfoPojo> merchantDataInfoPojos;
    /**商户资金账户变更材料列表*/
    private List<AttachmentDataInfoPojo> merchantAccountDataInfoPojos;
    /**商户业务经理变更材料列表*/
    private List<AttachmentDataInfoPojo> merchantManagerDataInfoPojos;

    /**信息变更类型*/
    private EnumRegisterKind             changeKind;

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
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>merchantBasicInfoPojo</tt>.
     * 
     * @return property value of merchantBasicInfoPojo
     */
    public MerchantBasicInfoPojo getMerchantBasicInfoPojo() {
        return merchantBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>merchantBasicInfoPojo</tt>.
     * 
     * @param merchantBasicInfoPojo value to be assigned to property merchantBasicInfoPojo
     */
    public void setMerchantBasicInfoPojo(MerchantBasicInfoPojo merchantBasicInfoPojo) {
        this.merchantBasicInfoPojo = merchantBasicInfoPojo;
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
    public UserFinancialInstrumentsPojo getFinancialInstrumentsPojo() {
        return financialInstrumentsPojo;
    }

    /**
     * Setter method for property <tt>financialInstrumentsPojo</tt>.
     * 
     * @param financialInstrumentsPojo value to be assigned to property financialInstrumentsPojo
     */
    public void setFinancialInstrumentsPojo(UserFinancialInstrumentsPojo financialInstrumentsPojo) {
        this.financialInstrumentsPojo = financialInstrumentsPojo;
    }

    /**
     * Getter method for property <tt>imageBasicInfo</tt>.
     * 
     * @return property value of imageBasicInfo
     */
    public List<UserImageDiffDataPojo> getImageBasicInfo() {
        return imageBasicInfo;
    }

    /**
     * Setter method for property <tt>imageBasicInfo</tt>.
     * 
     * @param imageBasicInfo value to be assigned to property imageBasicInfo
     */
    public void setImageBasicInfo(List<UserImageDiffDataPojo> imageBasicInfo) {
        this.imageBasicInfo = imageBasicInfo;
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

    /**
     * Getter method for property <tt>merchantDataInfoPojos</tt>.
     * 
     * @return property value of merchantDataInfoPojos
     */
    public List<AttachmentDataInfoPojo> getMerchantDataInfoPojos() {
        return merchantDataInfoPojos;
    }

    /**
     * Setter method for property <tt>merchantDataInfoPojos</tt>.
     * 
     * @param merchantDataInfoPojos value to be assigned to property merchantDataInfoPojos
     */
    public void setMerchantDataInfoPojos(List<AttachmentDataInfoPojo> merchantDataInfoPojos) {
        this.merchantDataInfoPojos = merchantDataInfoPojos;
    }

    /**
     * Getter method for property <tt>merchantAccountDataInfoPojos</tt>.
     * 
     * @return property value of merchantAccountDataInfoPojos
     */
    public List<AttachmentDataInfoPojo> getMerchantAccountDataInfoPojos() {
        return merchantAccountDataInfoPojos;
    }

    /**
     * Setter method for property <tt>merchantAccountDataInfoPojos</tt>.
     * 
     * @param merchantAccountDataInfoPojos value to be assigned to property merchantAccountDataInfoPojos
     */
    public void setMerchantAccountDataInfoPojos(List<AttachmentDataInfoPojo> merchantAccountDataInfoPojos) {
        this.merchantAccountDataInfoPojos = merchantAccountDataInfoPojos;
    }

    /**
     * Getter method for property <tt>merchantManagerDataInfoPojos</tt>.
     * 
     * @return property value of merchantManagerDataInfoPojos
     */
    public List<AttachmentDataInfoPojo> getMerchantManagerDataInfoPojos() {
        return merchantManagerDataInfoPojos;
    }

    /**
     * Setter method for property <tt>merchantManagerDataInfoPojos</tt>.
     * 
     * @param merchantManagerDataInfoPojos value to be assigned to property merchantManagerDataInfoPojos
     */
    public void setMerchantManagerDataInfoPojos(List<AttachmentDataInfoPojo> merchantManagerDataInfoPojos) {
        this.merchantManagerDataInfoPojos = merchantManagerDataInfoPojos;
    }

    /**
     * Getter method for property <tt>changeKind</tt>.
     * 
     * @return property value of changeKind
     */
    public EnumRegisterKind getChangeKind() {
        return changeKind;
    }

    /**
     * Setter method for property <tt>changeKind</tt>.
     * 
     * @param changeKind value to be assigned to property changeKind
     */
    public void setChangeKind(EnumRegisterKind changeKind) {
        this.changeKind = changeKind;
    }

}
