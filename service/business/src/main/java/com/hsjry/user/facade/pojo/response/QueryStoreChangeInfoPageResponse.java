/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.StoreChangePagePojo;

/**
 * 
 * @author huangbb
 * @version $Id: QueryStoreChangeInfoPageResponse.java, v 1.0 2018年5月16日 下午3:12:28 huangbb Exp $
 */
public class QueryStoreChangeInfoPageResponse implements Serializable {

    private static final long serialVersionUID = -3680080421970794006L;

    private List<StoreChangePagePojo> list;

    /**
     * Getter method for property <tt>list</tt>.
     * 
     * @return property value of list
     */
    public List<StoreChangePagePojo> getList() {
        return list;
    }

    /**
     * Setter method for property <tt>list</tt>.
     * 
     * @param list value to be assigned to property list
     */
    public void setList(List<StoreChangePagePojo> list) {
        this.list = list;
    }
    
    
    
}
