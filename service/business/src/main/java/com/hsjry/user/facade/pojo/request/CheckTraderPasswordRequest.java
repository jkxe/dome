/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 校验交易密码
 * @author huangbb
 * @version $Id: CheckTraderPasswordRequest.java, v 1.0 2017年3月30日 下午3:30:45 huangbb Exp $
 */
public class CheckTraderPasswordRequest implements Serializable {

    
    private static final long serialVersionUID = 8387131191136930604L;
    
    /**交易密码*/
    @NotNull(errorCode = "000001", message = "交易密码")
    @NotBlank(errorCode = "000001", message = "交易密码")
    private String traderPassword;
    
    /**识别来源 给定登录信息如ip、机器型号等 json数据 key参考源数据*/
    @NotNull(errorCode = "000001", message = "识别来源")
    @NotBlank(errorCode = "000001", message = "识别来源")
    private String identifySource;

    /**
     * Getter method for property <tt>traderPassword</tt>.
     * 
     * @return property value of traderPassword
     */
    public String getTraderPassword() {
        return traderPassword;
    }

    /**
     * Setter method for property <tt>traderPassword</tt>.
     * 
     * @param traderPassword value to be assigned to property traderPassword
     */
    public void setTraderPassword(String traderPassword) {
        this.traderPassword = traderPassword;
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
