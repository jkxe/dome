/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 查询推荐人信息返回
 * @author huangbb
 * @version $Id: QueryRefereeInfoResponse.java, v 0.1 2018年1月9日 下午2:53:38 huangbb Exp $
 */
public class QueryRefereeInfoResponse implements Serializable {

    private static final long serialVersionUID = -4029618468366700777L;

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
