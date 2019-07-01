/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryByAccountAndCertificateNoResponse.java, v 1.0 2017年9月12日 上午11:33:14 jiangjd12837 Exp $
 */
public class QueryByAccountAndCertificateNoResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -6253377264905135694L;
    //用户ID列表
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
