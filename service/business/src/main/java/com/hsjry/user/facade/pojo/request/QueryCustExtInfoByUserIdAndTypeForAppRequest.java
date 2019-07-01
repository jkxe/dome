/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxd14949
 * Date: 2018/5/4
 * Time: 15:13
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryCustExtInfoByUserIdAndTypeForAppRequest implements Serializable {

    private static final long serialVersionUID = -3410095996906098659L;

    @NotNull(errorCode="000001",message="authId")
    @NotBlank(errorCode="000001",message="authId")
    private String authId;

    @NotNull(errorCode="000001",message="内容类型")
    private List<String> type;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}

