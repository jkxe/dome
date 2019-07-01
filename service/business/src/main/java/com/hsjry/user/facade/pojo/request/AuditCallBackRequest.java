/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 统一审核回调接口入参
 * @author zhengqy15963
 * @version $Id: AuditCallBackRequest.java, v 1.0 2018年5月11日 上午11:06:06 zhengqy15963 Exp $
 */
public class AuditCallBackRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 9016114971675877674L;
    @NotNull(errorCode = "000001", message = "登记点id")
    @NotBlank(errorCode = "000001", message = "登记点id")
    /**登记点id  */
    private String            registerId;
    /**审核结果  */
    @NotNull(errorCode = "000001", message = "审核结果")
    @NotBlank(errorCode = "000001", message = "审核结果")
    private String            reply;

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * Getter method for property <tt>reply</tt>.
     * 
     * @return property value of reply
     */
    public String getReply() {
        return reply;
    }

    /**
     * Setter method for property <tt>reply</tt>.
     * 
     * @param reply value to be assigned to property reply
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

}
