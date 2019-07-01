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
 * @author jiangjd12837
 * @version $Id: QueryUndistributedMemberPageRequest.java, v 1.0 2017年6月16日 下午5:12:32 jiangjd12837 Exp $
 */
public class QueryUndistributedMemberPageRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -250408195076986458L;
    //客户名称
    private String            clientName;

    @NotNull(errorCode = "000001", message = "当前组长用户ID")
    @NotBlank(errorCode = "000001", message = "当前组长用户ID")
    private String            userId;

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
