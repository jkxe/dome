/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 超级管理员重置密码
 * @author huangbb
 * @version $Id: ResetPasswordOnlySuperAdminRequest.java, v 0.1 2017年7月25日 下午2:46:00 huangbb Exp $
 */
public class ResetPasswordOnlySuperAdminRequest implements Serializable {

    private static final long serialVersionUID = 5972952680828272565L;

    /**新密码*/
    @NotNull(errorCode = "000001", message = "新密码")
    @NotBlank(errorCode = "000001", message = "新密码")
    private String newPassword;
    
    /**用户ID*/
    @NotNull(errorCode = "000001", message = "用户ID")
    @NotBlank(errorCode = "000001", message = "用户ID")
    private String userId;

    /**
     * Getter method for property <tt>newPassword</tt>.
     * 
     * @return property value of newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Setter method for property <tt>newPassword</tt>.
     * 
     * @param newPassword value to be assigned to property newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
    
    
}
