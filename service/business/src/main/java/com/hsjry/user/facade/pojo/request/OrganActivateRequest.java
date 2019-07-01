/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 机构激活请求
 * @author huangbb
 * @version $Id: OrganActivateRequest.java, v 1.0 2017年3月21日 下午3:34:33 huangbb Exp $
 */
public class OrganActivateRequest implements Serializable {

    
    private static final long serialVersionUID = 1708733215714178767L;

    /**用户ID*/
    @NotNull(errorCode = "000001", message = "用户ID")
    @NotBlank(errorCode = "000001", message = "用户ID")
    private String userId;
    
    /**旧密码*/
    @NotNull(errorCode = "000001", message = "旧密码")
    @NotBlank(errorCode = "000001", message = "旧密码")
    private String authPassword;
    
    /**新密码*/
    @NotNull(errorCode = "000001", message = "新密码")
    @NotBlank(errorCode = "000001", message = "新密码")
    private String newPassword;

    
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
     * Getter method for property <tt>authPassword</tt>.
     * 
     * @return property value of authPassword
     */
    public String getAuthPassword() {
        return authPassword;
    }

    /**
     * Setter method for property <tt>authPassword</tt>.
     * 
     * @param authPassword value to be assigned to property authPassword
     */
    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

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
    
    

}
