/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 维护用户扩展信息
 * 
 * @author huangbb
 * @version $Id: DealCustomerExtInfoRequest.java, v 0.1 2018年1月4日 下午10:41:17 huangbb Exp $
 */
public class DealCustomerExtInfoRequest implements Serializable {

    private static final long serialVersionUID = -8776937136911279538L;
    
    @NotNull(errorCode="000001",message="用户ID")
    @NotBlank(errorCode="000001",message="用户ID")
    private String userId;
    
    @NotNull(errorCode="000001",message="内容(Json)")
    @NotBlank(errorCode="000001",message="内容(Json)")
    private String content;
    
    /**请避免使用EnumCustomerExtType中的code，以免信息被覆盖了*/
    @Length(min=1,max=2,errorCode="000002",message="内容类型")
    @NotNull(errorCode="000001",message="内容类型")
    @NotBlank(errorCode="000001",message="内容类型")
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    

}
