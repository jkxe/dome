/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.MerchantManagerChangePagePojo;

/**
 * 商户业务经理变更响应
 * @author huangbb
 * @version $Id: QueryMerchantManagerChangeResponse.java, v 1.0 2018年5月16日 下午3:36:10 huangbb Exp $
 */
public class QueryMerchantManagerChangeResponse implements Serializable {

    
    private static final long serialVersionUID = 8614214641917113187L;
    
    private List<MerchantManagerChangePagePojo> list;

    /**
     * Getter method for property <tt>list</tt>.
     * 
     * @return property value of list
     */
    public List<MerchantManagerChangePagePojo> getList() {
        return list;
    }

    /**
     * Setter method for property <tt>list</tt>.
     * 
     * @param list value to be assigned to property list
     */
    public void setList(List<MerchantManagerChangePagePojo> list) {
        this.list = list;
    }
    
    
}
