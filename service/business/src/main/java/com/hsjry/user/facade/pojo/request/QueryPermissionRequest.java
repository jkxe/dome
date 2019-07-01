/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 查询权限请求类
 * @author hongsj
 * @version $Id: queryUserHavedPerRequest.java, v 1.0 2017年3月28日 下午2:42:26 hongsj Exp $
 */
public class QueryPermissionRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 8769860194379662509L;
    /**客户id*/
    @NotNull(errorCode = "000001", message = "客户id")
    @NotBlank(errorCode = "000001", message = "客户id")
    private String            userId;

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
