/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;


/**
 * 注册手机号查询用户信息请求
 * @author huangbb
 * @version $Id: QueryUserInfoByTelephoneRequest.java, v 0.1 2018年1月18日 下午7:54:19 huangbb Exp $
 */
public class QueryUserInfoByTelephoneRequest implements Serializable {

    private static final long serialVersionUID = 3513892293775576529L;
    
    @Size(min=1,errorCode="000002",message="注册手机号列表")
    @NotNull(errorCode="000001",message="注册手机号列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "注册手机号列表")
    private List<String> telList;

    /**
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
    
    
}
