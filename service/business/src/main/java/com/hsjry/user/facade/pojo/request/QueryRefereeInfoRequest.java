/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 查询推荐人信息
 * @author huangbb
 * @version $Id: QueryRefereeInfoRequest.java, v 0.1 2018年1月9日 下午2:51:52 huangbb Exp $
 */
public class QueryRefereeInfoRequest implements Serializable {

    private static final long serialVersionUID = 5740922710499429653L;

    @NotNull(errorCode="000001",message="被推荐人用户ID")
    @NotBlank(errorCode="000001",message="被推荐人用户ID")
    private String userId;

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
