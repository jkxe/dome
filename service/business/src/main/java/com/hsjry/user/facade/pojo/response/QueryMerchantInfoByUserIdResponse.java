/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.MarketBasicInfoPojo;
import com.hsjry.user.facade.pojo.MerchantBasicInfoPojo;
import com.hsjry.user.facade.pojo.OrganAndImageDiffDataPojo;
import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;
import com.hsjry.user.facade.pojo.UserImageDiffDataPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMerchantInfoByUserIdResponse.java, v 1.0 2018年4月23日 上午9:24:38 zhengqy15963 Exp $
 */
public class QueryMerchantInfoByUserIdResponse implements Serializable {

    private static final long               serialVersionUID = -4527519685110650406L;
    /**机构用户id */
    private String                          userId;
    /**客户角色id */
    private String                          custRoleId;
    /**用户名 */
    private String                          userName;
    /**基本信息*/
    private MerchantBasicInfoPojo           merchantBasicInfoPojo;
    /**营销信息*/
    private MarketBasicInfoPojo             marketBasicInfo;
    /**账户信息*/
    private UserFinancialInstrumentsPojo    financialInstrumentsPojo;
    /**影像信息*/
    private List<UserImageDiffDataPojo>     imageBasicInfo;
    /**门店信息*/
    private List<OrganAndImageDiffDataPojo> infos;

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
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoleId</tt>.
     * 
     * @param custRoleId value to be assigned to property custRoleId
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
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
     * Getter method for property <tt>infos</tt>.
     * 
     * @return property value of infos
     */
    public List<OrganAndImageDiffDataPojo> getInfos() {
        return infos;
    }

    /**
     * Setter method for property <tt>infos</tt>.
     * 
     * @param infos value to be assigned to property infos
     */
    public void setInfos(List<OrganAndImageDiffDataPojo> infos) {
        this.infos = infos;
    }

}
