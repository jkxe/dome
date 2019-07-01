/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.UserFaceAuthPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxd14949
 * Date: 2018/5/22
 * Time: 11:12
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class UserAuthFaceResponse implements Serializable {

    private static final long serialVersionUID = 345827772263331740L;

    private List<UserFaceAuthPojo> list;

    public List<UserFaceAuthPojo> getList() {
        return list;
    }

    public void setList(List<UserFaceAuthPojo> list) {
        this.list = list;
    }
}

