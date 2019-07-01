/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author hongsj
 * @version $Id: VerifyTradingPermissionRequest.java, v 1.0 2017年3月17日 上午11:22:57 hongsj Exp $
 */
public class VerifyTradingPermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 7641333409040031605L;
    /**
     * 用户Id
     */
    @NotNull(errorCode = "000001", message = "用户Id")
    @NotBlank(errorCode = "000001", message = "用户Id")
    private String            userId;
    /**
     * 交易功能号
     */
    @NotNull(errorCode = "000001", message = "交易功能号")
    @NotBlank(errorCode = "000001", message = "交易功能号")
    private String            menuDisting;

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
     * Getter method for property <tt>menuDisting</tt>.
     * 
     * @return property value of menuDisting
     */
    public String getMenuDisting() {
        return menuDisting;
    }

    /**
     * Setter method for property <tt>menuDisting</tt>.
     * 
     * @param menuDisting value to be assigned to property menuDisting
     */
    public void setMenuDisting(String menuDisting) {
        this.menuDisting = menuDisting;
    }

}
