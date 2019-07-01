/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.CheckWith;

import com.hsjry.user.facade.pojo.MarketBasicInfoPojo;
import com.hsjry.user.facade.pojo.MerchantBasicInfoPojo;
import com.hsjry.user.facade.pojo.UserImageDiffDataPojo;
import com.hsjry.user.facade.pojo.UserMerchantFinancialPojo;
import com.hsjry.user.facade.pojo.check.UserNameCheck;

/**
 * 
 * @author zhengqy15963
 * @version $Id: MerchantInsertSecondStepRequest.java, v 1.0 2018年4月27日 下午7:21:38 zhengqy15963 Exp $
 */
public class MerchantInsertSecondStepRequest implements Serializable {

    /**  */
    private static final long           serialVersionUID = 7599324838591283218L;

    /**用户名 */
    @CheckWith(value = UserNameCheck.class, errorCode = "000003", message = "用户名校验", ignoreIfNull = false)
    private String                      userName;
    /**基本信息*/
    private MerchantBasicInfoPojo       merchantBasicInfoPojo;
    /**营销信息*/
    private MarketBasicInfoPojo         marketBasicInfo;
    /**账户信息*/
    @AssertValid
    private UserMerchantFinancialPojo   userMerchantFinancialPojo;
    /**影像信息*/
    private List<UserImageDiffDataPojo> imageBasicInfo;

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
     * Getter method for property <tt>userMerchantFinancialPojo</tt>.
     * 
     * @return property value of userMerchantFinancialPojo
     */
    public UserMerchantFinancialPojo getUserMerchantFinancialPojo() {
        return userMerchantFinancialPojo;
    }

    /**
     * Setter method for property <tt>userMerchantFinancialPojo</tt>.
     * 
     * @param userMerchantFinancialPojo value to be assigned to property userMerchantFinancialPojo
     */
    public void setUserMerchantFinancialPojo(UserMerchantFinancialPojo userMerchantFinancialPojo) {
        this.userMerchantFinancialPojo = userMerchantFinancialPojo;
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
}
