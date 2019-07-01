/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 找回交易密码前置校验返回
 * @author huangbb
 * @version $Id: RetrieveTraderPwdPrecheckResponse.java, v 1.0 2017年3月30日 下午3:51:44 huangbb Exp $
 */
public class RetrieveTraderPwdPrecheckResponse implements Serializable {

    private static final long serialVersionUID = -1819837507763246396L;
    
    /**客户ID*/
    private String userId;
    
    /**手机号*/
    private String telephone;

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
    
    

}
