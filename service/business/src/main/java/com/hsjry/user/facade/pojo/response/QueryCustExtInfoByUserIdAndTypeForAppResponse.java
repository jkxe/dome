/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.request.HyxfExtInfoPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxd14949
 * Date: 2018/5/4
 * Time: 15:16
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryCustExtInfoByUserIdAndTypeForAppResponse implements Serializable {

    private static final long serialVersionUID = 5581422258951423519L;

    private List<HyxfExtInfoPojo> list;

    public List<HyxfExtInfoPojo> getList() {
        return list;
    }

    public void setList(List<HyxfExtInfoPojo> list) {
        this.list = list;
    }
}

