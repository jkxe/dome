/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 查询自定义扩展信息请求
 * @author huangbb
 * @version $Id: QueryCustExtInfoByUserIdAndTypeRequest.java, v 0.1 2018年1月4日 下午11:06:37 huangbb Exp $
 */
public class QueryCustExtInfoByUserIdAndTypeRequest implements Serializable {

    private static final long serialVersionUID = 8020963149691746029L;
    
    @NotNull(errorCode="000001",message="用户ID")
    @NotBlank(errorCode="000001",message="用户ID")
    private String userId;
    
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
}
