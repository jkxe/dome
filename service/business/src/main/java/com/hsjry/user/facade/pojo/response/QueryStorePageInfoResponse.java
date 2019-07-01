/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.StorePagePojo;

/**
 * 分页查询门店信息返回出参
 * @author zhengqy15963
 * @version $Id: QueryStorePageInfoResponse.java, v 1.0 2018年5月7日 下午4:49:02 zhengqy15963 Exp $
 */
public class QueryStorePageInfoResponse implements Serializable {

    /**  */
    private static final long   serialVersionUID = 3586670942105656818L;
    /**门店列表  */
    private List<StorePagePojo> storePagePojos;

    /**
     * Getter method for property <tt>storePagePojos</tt>.
     * 
     * @return property value of storePagePojos
     */
    public List<StorePagePojo> getStorePagePojos() {
        return storePagePojos;
    }

    /**
     * Setter method for property <tt>storePagePojos</tt>.
     * 
     * @param storePagePojos value to be assigned to property storePagePojos
     */
    public void setStorePagePojos(List<StorePagePojo> storePagePojos) {
        this.storePagePojos = storePagePojos;
    }

}
