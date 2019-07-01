/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumBindLoginType;

/**
 * 绑定登入校验请求
 * @author huangbb
 * @version $Id: CheckLoginBindRequest.java, v 1.0 2017年3月21日 下午3:12:34 huangbb Exp $
 */
public class CheckLoginBindRequest implements Serializable {

    
    private static final long serialVersionUID = 2593411433461756403L;

    /** 绑定识别类型 */
    @NotNull(errorCode = "000001", message = "绑定识别类型")
    private EnumBindLoginType identifyType;
    
    /** 授权token */
    @NotNull(errorCode = "000001", message = "授权token")
    @NotBlank(errorCode = "000001", message = "授权token")
    private String userToken;
    
    /**识别来源 给定登录信息如ip、机器型号等 json数据*/
    @NotNull(errorCode = "000001", message = "识别来源")
    @NotBlank(errorCode = "000001", message = "识别来源")
    private String identifySource;

    /**
     * Getter method for property <tt>identifyType</tt>.
     * 
     * @return property value of identifyType
     */
    public EnumBindLoginType getIdentifyType() {
        return identifyType;
    }

    /**
     * Setter method for property <tt>identifyType</tt>.
     * 
     * @param identifyType value to be assigned to property identifyType
     */
    public void setIdentifyType(EnumBindLoginType identifyType) {
        this.identifyType = identifyType;
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
    
    

}
