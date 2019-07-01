/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumBindLoginType;

/**
 * 登入绑定请求
 * @author huangbb
 * @version $Id: LoginBindRequest.java, v 1.0 2017年3月21日 下午3:04:14 huangbb Exp $
 */
public class LoginBindRequest implements Serializable {

    private static final long serialVersionUID = 6073498275293010193L;

    /** 绑定识别类型 */
    @NotNull(errorCode = "000001", message = "绑定识别类型")
    private EnumBindLoginType identifyType;

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
    
    

}
