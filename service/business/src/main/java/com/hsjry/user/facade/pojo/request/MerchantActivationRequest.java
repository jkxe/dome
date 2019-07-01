/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author zhengqy15963
 * @version $Id: MerchantActivationRequest.java, v 1.0 2018年5月2日 下午8:46:19 zhengqy15963 Exp $
 */
public class MerchantActivationRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -8442724102239292556L;
    /** 机构商户id */
    @NotNull(errorCode = "000001", message = "机构商户id")
    @NotBlank(errorCode = "000001", message = "机构商户id")
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
