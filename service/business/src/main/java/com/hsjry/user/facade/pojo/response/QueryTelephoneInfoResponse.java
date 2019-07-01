/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryTelephoneInfoResponse.java, v 1.0 2017年6月8日 下午7:22:37 jiangjd12837 Exp $
 */
public class QueryTelephoneInfoResponse implements Serializable {

    private static final long serialVersionUID = 2255537301443697408L;
    /**是否注册*/
    private EnumBool          registerFlag;
    /**是否实名*/
    private EnumBool          realnameFlag;
    /**营销线索*/
    private EnumMarketingCues MarketingCues;
    /**渠道名称 营销线索为其他渠道 才有值*/
    private String            channelName;
    /**用户ID*/
    private String            userId;
    /**通行证ID*/
    private String            authId;
    /**是否设置了登陆密码*/
    private EnumBool          hasLoginPassword;

    /**
     * Getter method for property <tt>registerFlag</tt>.
     * 
     * @return property value of registerFlag
     */
    public EnumBool getRegisterFlag() {
        return registerFlag;
    }

    /**
     * Setter method for property <tt>registerFlag</tt>.
     * 
     * @param registerFlag value to be assigned to property registerFlag
     */
    public void setRegisterFlag(EnumBool registerFlag) {
        this.registerFlag = registerFlag;
    }

    /**
     * Getter method for property <tt>realnameFlag</tt>.
     * 
     * @return property value of realnameFlag
     */
    public EnumBool getRealnameFlag() {
        return realnameFlag;
    }

    /**
     * Setter method for property <tt>realnameFlag</tt>.
     * 
     * @param realnameFlag value to be assigned to property realnameFlag
     */
    public void setRealnameFlag(EnumBool realnameFlag) {
        this.realnameFlag = realnameFlag;
    }

    /**
     * Getter method for property <tt>marketingCues</tt>.
     * 
     * @return property value of MarketingCues
     */
    public EnumMarketingCues getMarketingCues() {
        return MarketingCues;
    }

    /**
     * Setter method for property <tt>marketingCues</tt>.
     * 
     * @param MarketingCues value to be assigned to property marketingCues
     */
    public void setMarketingCues(EnumMarketingCues marketingCues) {
        MarketingCues = marketingCues;
    }

    /**
     * Getter method for property <tt>channelName</tt>.
     * 
     * @return property value of channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Setter method for property <tt>channelName</tt>.
     * 
     * @param channelName value to be assigned to property channelName
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
     * Getter method for property <tt>hasLoginPassword</tt>.
     * 
     * @return property value of hasLoginPassword
     */
    public EnumBool getHasLoginPassword() {
        return hasLoginPassword;
    }

    /**
     * Setter method for property <tt>hasLoginPassword</tt>.
     * 
     * @param hasLoginPassword value to be assigned to property hasLoginPassword
     */
    public void setHasLoginPassword(EnumBool hasLoginPassword) {
        this.hasLoginPassword = hasLoginPassword;
    }

    /**
     * Getter method for property <tt>authId</tt>.
     * 
     * @return property value of authId
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Setter method for property <tt>authId</tt>.
     * 
     * @param authId value to be assigned to property authId
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }

}
