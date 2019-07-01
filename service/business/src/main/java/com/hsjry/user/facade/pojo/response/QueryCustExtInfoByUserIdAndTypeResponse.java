/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 扩展信息查询返回
 * @author huangbb
 * @version $Id: QueryCustExtInfoByUserIdAndTypeResponse.java, v 0.1 2018年1月4日 下午11:01:39 huangbb Exp $
 */
public class QueryCustExtInfoByUserIdAndTypeResponse implements Serializable {

    private static final long serialVersionUID = -3336880502796514118L;
    
    /**主键*/
    private String id;
    /**用户ID*/
    private String userId;
    /**内容类型*/
    private String type;
    /**内容(Json)*/
    private String content;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    
}
