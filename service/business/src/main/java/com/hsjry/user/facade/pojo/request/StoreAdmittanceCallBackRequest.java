/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 
 * @author zhengqy15963
 * @version $Id: StoreAdmittanceCallBackRequest.java, v 1.0 2018年5月9日 下午3:36:56 zhengqy15963 Exp $
 */
public class StoreAdmittanceCallBackRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 538077621060698790L;

    /**门店组织id  */
    @NotNull(errorCode = "000001", message = "门店组织id")
    @NotBlank(errorCode = "000001", message = "门店组织id")
    private String            content;
    /**审批结果*/
    @NotNull(errorCode = "000001", message = "审批结果")
    private String            reply;

    /**
     * Getter method for property <tt>content</tt>.
     * 
     * @return property value of content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     * 
     * @param content value to be assigned to property content
     */
    public void setContent(String content) {
        this.content = content;
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
