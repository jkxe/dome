/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserInfoSharePojo;
import com.hsjry.user.facade.pojo.enums.EnumIdentifyKind;
import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 验证码联合登录绑定请求
 * @author huangbb
 * @version $Id: CheckCodeLoginUniteBindRequest.java, v 1.0 2017年5月5日 下午2:18:41 huangbb Exp $
 */
public class CheckCodeLoginUniteBindRequest implements Serializable {

    private static final long serialVersionUID = 146764352306062L;

    /**手机号*/
    @NotNull(errorCode = "000001", message = "手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String            telephone;

    /**校验码*/
    @NotNull(errorCode = "000001", message = "校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    @Length(min = 6, max = 6, errorCode = "000002", message = "校验码")
    @MatchPattern(matchAll = false, pattern = { "^\\d{6}$" }, errorCode = "000003", message = "校验码")
    private String            authCheckCode;

    /**营销线索*/
    @NotNull(errorCode = "000001", message = "营销线索")
    private EnumMarketingCues marketingCues;

    /**识别来源 给定登录信息如ip、机器型号等 json数据 key参考源数据*/
    @NotNull(errorCode = "000001", message = "识别来源")
    @NotBlank(errorCode = "000001", message = "识别来源")
    private String            identifySource;

    /**授权token*/
    @NotNull(errorCode = "000001", message = "授权token")
    @NotBlank(errorCode = "000001", message = "授权token")
    private String            userToken;

    /**标识类型*/
    @NotNull(errorCode = "000001", message = "标识类型")
    private EnumIdentifyKind  identifyKind;

    /**客户分析信息*/
    @AssertValid(errorCode = "000001", message = "授权信息")
    private UserInfoSharePojo userInfoShare;

    /**
     * Getter method for property <tt>userInfoShare</tt>.
     * 
     * @return property value of userInfoShare
     */
    public UserInfoSharePojo getUserInfoShare() {
        return userInfoShare;
    }

    /**
     * Setter method for property <tt>userInfoShare</tt>.
     * 
     * @param userInfoShare value to be assigned to property userInfoShare
     */
    public void setUserInfoShare(UserInfoSharePojo userInfoShare) {
        this.userInfoShare = userInfoShare;
    }

    /**
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>authCheckCode</tt>.
     * 
     * @return property value of authCheckCode
     */
    public String getAuthCheckCode() {
        return authCheckCode;
    }

    /**
     * Setter method for property <tt>authCheckCode</tt>.
     * 
     * @param authCheckCode value to be assigned to property authCheckCode
     */
    public void setAuthCheckCode(String authCheckCode) {
        this.authCheckCode = authCheckCode;
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
     * Getter method for property <tt>identifySource</tt>.
     * 
     * @return property value of identifySource
     */
    public String getIdentifySource() {
        return identifySource;
    }

    /**
     * Setter method for property <tt>identifySource</tt>.
     * 
     * @param identifySource value to be assigned to property identifySource
     */
    public void setIdentifySource(String identifySource) {
        this.identifySource = identifySource;
    }

    /**
     * Getter method for property <tt>userToken</tt>.
     * 
     * @return property value of userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Setter method for property <tt>userToken</tt>.
     * 
     * @param userToken value to be assigned to property userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * Getter method for property <tt>identifyKind</tt>.
     * 
     * @return property value of identifyKind
     */
    public EnumIdentifyKind getIdentifyKind() {
        return identifyKind;
    }

    /**
     * Setter method for property <tt>identifyKind</tt>.
     * 
     * @param identifyKind value to be assigned to property identifyKind
     */
    public void setIdentifyKind(EnumIdentifyKind identifyKind) {
        this.identifyKind = identifyKind;
    }

}
