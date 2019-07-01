/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.MerchantChangePagePojo;

/**
 * 商户基本信息变更查询
 * @author huangbb
 * @version $Id: QueryMerchantInfoChangeResponse.java, v 1.0 2018年5月16日 上午10:28:51 huangbb Exp $
 */
public class QueryMerchantInfoChangeResponse implements Serializable {

    private static final long serialVersionUID = -6934665338736269797L;

    private List<MerchantChangePagePojo> list;

    /**
     * Getter method for property <tt>list</tt>.
     * 
     * @return property value of list
     */
    public List<MerchantChangePagePojo> getList() {
        return list;
    }

    /**
     * Setter method for property <tt>list</tt>.
     * 
     * @param list value to be assigned to property list
     */
    public void setList(List<MerchantChangePagePojo> list) {
        this.list = list;
    }
    
    
}
