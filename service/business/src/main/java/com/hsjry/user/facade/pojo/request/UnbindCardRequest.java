/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 解绑请求
 * @author huangbb
 * @version $Id: UnbindCardRequest.java, v 1.0 2017年4月14日 下午2:20:38 huangbb Exp $
 */
public class UnbindCardRequest implements Serializable {

    private static final long serialVersionUID = -1118510119060854247L;
    /** 客户ID */
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String userId   ;
    /** 资源项ID */
    @NotNull(errorCode = "000001", message = "资源项ID")
    @NotBlank(errorCode = "000001", message = "资源项ID")
    private String resourceId;
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
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }
    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    

    
    
}
