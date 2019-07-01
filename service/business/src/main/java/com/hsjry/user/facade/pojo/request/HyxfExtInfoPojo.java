/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/5/4
 * Time: 15:18
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class HyxfExtInfoPojo implements Serializable {

    private static final long serialVersionUID = -2349004449083357739L;

    /**主键*/
    private String id;
    /**用户ID*/
    private String userId;
    /**内容类型*/
    private String type;
    /**内容状态*/
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

