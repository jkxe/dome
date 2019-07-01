/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryStoreInfoListRequest.java, v 1.0 2018年5月28日 下午4:10:53 zhengqy15963 Exp $
 */
public class QueryStoreInfoListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -4142949794045081302L;
    /**组织id列表  */
    private List<String>      organIdList;

    /**
     * Getter method for property <tt>organIdList</tt>.
     * 
     * @return property value of organIdList
     */
    public List<String> getOrganIdList() {
        return organIdList;
    }

    /**
     * Setter method for property <tt>organIdList</tt>.
     * 
     * @param organIdList value to be assigned to property organIdList
     */
    public void setOrganIdList(List<String> organIdList) {
        this.organIdList = organIdList;
    }

}
