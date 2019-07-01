/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/5/22
 * Time: 11:08
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryUserFaceAuthRequest implements Serializable {

    private static final long serialVersionUID = 1822285681082381582L;

    @NotNull(errorCode="000001",message="客户ID")
    @NotBlank(errorCode="000001",message="客户ID")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

