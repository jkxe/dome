/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.MerchantPagePojo;

/**
 * 商户分页信息查询返回
 * @author huangbb
 * @version $Id: QueryMerchantPageInfoResponse.java, v 1.0 2018年5月5日 下午5:19:47 huangbb Exp $
 */
public class QueryMerchantPageInfoResponse implements Serializable {

    private static final long      serialVersionUID = -8248708485215158992L;

    private List<MerchantPagePojo> list;

    /**
     * Getter method for property <tt>list</tt>.
     * 
     * @return property value of list
     */
    public List<MerchantPagePojo> getList() {
        return list;
    }

    /**
     * Setter method for property <tt>list</tt>.
     * 
     * @param list value to be assigned to property list
     */
    public void setList(List<MerchantPagePojo> list) {
        this.list = list;
    }

}
