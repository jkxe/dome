/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 商户提交审核
 * @author huangbb
 * @version $Id: MerchantSubmitAuditRequest.java, v 1.0 2018年5月3日 上午10:46:11 huangbb Exp $
 */
public class MerchantSubmitAuditRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -5877331922968165723L;
    /** 商户用户ID */
    @NotNull(errorCode = "000001", message = "商户用户ID")
    @NotBlank(errorCode = "000001", message = "商户用户ID")
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
