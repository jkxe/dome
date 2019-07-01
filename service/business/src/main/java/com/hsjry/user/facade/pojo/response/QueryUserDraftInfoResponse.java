/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.UserDraftInfoPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxd14949
 * Date: 2018/5/17
 * Time: 16:13
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryUserDraftInfoResponse implements Serializable {

    private static final long serialVersionUID = -994468870572773988L;

    private List<UserDraftInfoPojo> list;

    public List<UserDraftInfoPojo> getList() {
        return list;
    }

    public void setList(List<UserDraftInfoPojo> list) {
        this.list = list;
    }
}

