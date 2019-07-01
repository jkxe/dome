/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.check.ListStringCheck;
import com.hsjry.user.facade.pojo.enums.EnumTelephoneClassCode;
import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryOtherUserByTelListRequest.java, v 1.0 2017年7月28日 下午4:54:26 jiangjd12837 Exp $
 */
public class QueryOtherUserByTelListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -5512315025756586534L;
    // 电话列表
    @NotNull(errorCode = "000001", message = "电话列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "电话列表")
    private List<String>      telList;

    /**
     * 是否联系人查询
     */
    private EnumBool bool;

    /**
     * 查询电话类别
     */
    private EnumTelephoneClassCode type;
    /**
     *
     * Getter method for property <tt>telList</tt>.
     * 
     * @return property value of telList
     */
    public List<String> getTelList() {
        return telList;
    }

    /**
     * Setter method for property <tt>telList</tt>.
     * 
     * @param telList value to be assigned to property telList
     */
    public void setTelList(List<String> telList) {
        this.telList = telList;
    }

    public EnumBool getBool() {
        return bool;
    }

    public void setBool(EnumBool bool) {
        this.bool = bool;
    }

    public EnumTelephoneClassCode getType() {
        return type;
    }

    public void setType(EnumTelephoneClassCode type) {
        this.type = type;
    }
}
