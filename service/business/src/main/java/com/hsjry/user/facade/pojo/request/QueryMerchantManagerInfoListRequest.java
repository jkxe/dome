/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.check.ListStringCheck;
import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMerchantManagerInfoListRequest.java, v 1.0 2018年5月28日 下午3:25:43 zhengqy15963 Exp $
 */
public class QueryMerchantManagerInfoListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -7881905027104481529L;
    /**机构客户id列表  */
    @NotNull(errorCode = "000001", message = "机构客户id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "机构客户id列表")
    private List<String>      userIdList;

    /**
     * Getter method for property <tt>userIdList</tt>.
     * 
     * @return property value of userIdList
     */
    public List<String> getUserIdList() {
        return userIdList;
    }

    /**
     * Setter method for property <tt>userIdList</tt>.
     * 
     * @param userIdList value to be assigned to property userIdList
     */
    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

}
