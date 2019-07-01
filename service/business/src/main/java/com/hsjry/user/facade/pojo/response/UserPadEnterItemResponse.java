/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.UserPadItemPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxd14949
 * Date: 2018/5/18
 * Time: 16:46
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class UserPadEnterItemResponse implements Serializable {

    private static final long serialVersionUID = 4299086976369666849L;

    private List<UserPadItemPojo> list;

    public List<UserPadItemPojo> getList() {
        return list;
    }

    public void setList(List<UserPadItemPojo> list) {
        this.list = list;
    }
}

