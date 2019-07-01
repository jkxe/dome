/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMarketBasicInfoByUserIdRequest.java, v 1.0 2018年5月29日 下午8:09:48 zhengqy15963 Exp $
 */
public class QueryMarketBasicInfoByUserIdRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -6128276768785025087L;
    /**机构客户id  */
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
