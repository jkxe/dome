/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumListStatus;

import net.sf.oval.constraint.NotNull;

/**
 * 指定类型名单列表查询请求类
 * @author hongsj
 * @version $Id: QuerySpecialListRequest.java, v 1.0 2017年3月28日 下午2:01:03 hongsj Exp $
 */
public class QuerySpecialListRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 5364885897525623815L;
    /**名单状态*/
    @NotNull(errorCode = "000001", message = "名单状态")
    private EnumListStatus    listStatus;

    /**
     * Getter method for property <tt>listStatus</tt>.
     * 
     * @return property value of listStatus
     */
    public EnumListStatus getListStatus() {
        return listStatus;
    }

    /**
     * Setter method for property <tt>listStatus</tt>.
     * 
     * @param listStatus value to be assigned to property listStatus
     */
    public void setListStatus(EnumListStatus listStatus) {
        this.listStatus = listStatus;
    }

}
