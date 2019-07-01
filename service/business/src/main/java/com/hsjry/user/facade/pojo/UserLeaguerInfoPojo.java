/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

/**
 * 
 * @author jiangjd12837
 * @version $Id: LeaguerInfoPojo.java, v 1.0 2017年4月1日 下午2:33:26 jiangjd12837 Exp $
 */
public class UserLeaguerInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -3030306439820570253L;
    //会员ID
    private String            leaguerId;
    //客户角色ID
    private String            custRoleId;
    //客户ID
    private String            userId;
    //用户名
    private String            userName;
    //昵称
    private String            nickName;
    //头像
    private String            headImg;
    //认证等级
    private Integer           authLevel;
    //安全级别
    private Integer           riskLevel;
    //生命周期状态
    private String            lifecycleStatus;
    //渠道来源
    private String            channelSource;
    //营销线索
    private EnumMarketingCues marketingCues;
    //邀请码
    private String            inviteCode;
    //推荐人会员ID
    private String            recommenderId;

    /**
     * Getter method for property <tt>leaguerId</tt>.
     * 
     * @return property value of leaguerId
     */
    public String getLeaguerId() {
        return leaguerId;
    }

    /**
     * Setter method for property <tt>leaguerId</tt>.
     * 
     * @param leaguerId value to be assigned to property leaguerId
     */
    public void setLeaguerId(String leaguerId) {
        this.leaguerId = leaguerId;
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
     * Getter method for property <tt>nickName</tt>.
     * 
     * @return property value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter method for property <tt>nickName</tt>.
     * 
     * @param nickName value to be assigned to property nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Getter method for property <tt>headImg</tt>.
     * 
     * @return property value of headImg
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * Setter method for property <tt>headImg</tt>.
     * 
     * @param headImg value to be assigned to property headImg
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * Getter method for property <tt>authLevel</tt>.
     * 
     * @return property value of authLevel
     */
    public Integer getAuthLevel() {
        return authLevel;
    }

    /**
     * Setter method for property <tt>authLevel</tt>.
     * 
     * @param authLevel value to be assigned to property authLevel
     */
    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    /**
     * Getter method for property <tt>riskLevel</tt>.
     * 
     * @return property value of riskLevel
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * Setter method for property <tt>riskLevel</tt>.
     * 
     * @param riskLevel value to be assigned to property riskLevel
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * Getter method for property <tt>lifecycleStatus</tt>.
     * 
     * @return property value of lifecycleStatus
     */
    public String getLifecycleStatus() {
        return lifecycleStatus;
    }

    /**
     * Setter method for property <tt>lifecycleStatus</tt>.
     * 
     * @param lifecycleStatus value to be assigned to property lifecycleStatus
     */
    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    /**
     * Getter method for property <tt>channelSource</tt>.
     * 
     * @return property value of channelSource
     */
    public String getChannelSource() {
        return channelSource;
    }

    /**
     * Setter method for property <tt>channelSource</tt>.
     * 
     * @param channelSource value to be assigned to property channelSource
     */
    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }

    /**
     * Getter method for property <tt>marketingCues</tt>.
     * 
     * @return property value of marketingCues
     */
    public EnumMarketingCues getMarketingCues() {
        return marketingCues;
    }

    /**
     * Setter method for property <tt>marketingCues</tt>.
     * 
     * @param marketingCues value to be assigned to property marketingCues
     */
    public void setMarketingCues(EnumMarketingCues marketingCues) {
        this.marketingCues = marketingCues;
    }

    /**
     * Getter method for property <tt>inviteCode</tt>.
     * 
     * @return property value of inviteCode
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * Setter method for property <tt>inviteCode</tt>.
     * 
     * @param inviteCode value to be assigned to property inviteCode
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * Getter method for property <tt>recommenderId</tt>.
     * 
     * @return property value of recommenderId
     */
    public String getRecommenderId() {
        return recommenderId;
    }

    /**
     * Setter method for property <tt>recommenderId</tt>.
     * 
     * @param recommenderId value to be assigned to property recommenderId
     */
    public void setRecommenderId(String recommenderId) {
        this.recommenderId = recommenderId;
    }
}
