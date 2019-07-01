/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录信息返回
 * @author huangbb
 * @version $Id: LoginResponse.java, v 1.0 2017年3月20日 上午10:31:32 huangbb Exp $
 */
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -6508581047073364108L;
    
    /**通行证ID*/
    private String authId;
    
    /**登录token*/
    private String token;
    
    /**冻结时间*/
    private Integer freezingTime;
    
    /**剩余尝试次数*/
    private Integer surplusTrialTimes;

    /**
     * 是否是初始化密码
     */
    private Boolean isInit;

    /**
     * 失效时间
     */
    private Date expireTime;

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

    /**
     * Getter method for property <tt>token</tt>.
     * 
     * @return property value of token
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter method for property <tt>token</tt>.
     * 
     * @param token value to be assigned to property token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter method for property <tt>freezingTime</tt>.
     * 
     * @return property value of freezingTime
     */
    public Integer getFreezingTime() {
        return freezingTime;
    }

    /**
     * Setter method for property <tt>freezingTime</tt>.
     * 
     * @param freezingTime value to be assigned to property freezingTime
     */
    public void setFreezingTime(Integer freezingTime) {
        this.freezingTime = freezingTime;
    }

    /**
     * Getter method for property <tt>surplusTrialTimes</tt>.
     * 
     * @return property value of surplusTrialTimes
     */
    public Integer getSurplusTrialTimes() {
        return surplusTrialTimes;
    }

    /**
     * Setter method for property <tt>surplusTrialTimes</tt>.
     * 
     * @param surplusTrialTimes value to be assigned to property surplusTrialTimes
     */
    public void setSurplusTrialTimes(Integer surplusTrialTimes) {
        this.surplusTrialTimes = surplusTrialTimes;
    }

    public Boolean getInit() {
        return isInit;
    }

    public void setInit(Boolean init) {
        isInit = init;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
