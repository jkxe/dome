/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryUserInfosByRoleIdResponse.java, v 1.0 2018年7月4日 下午3:46:38 zhengqy15963 Exp $
 */
public class QueryUserInfosByRoleIdResponse implements Serializable {

    /**  */
    private static final long       serialVersionUID = -7546203291367019755L;

    private List<QueryUserInfoPojo> infoPojos;

    /**
     * Getter method for property <tt>infoPojos</tt>.
     * 
     * @return property value of infoPojos
     */
    public List<QueryUserInfoPojo> getInfoPojos() {
        return infoPojos;
    }

    /**
     * Setter method for property <tt>infoPojos</tt>.
     * 
     * @param infoPojos value to be assigned to property infoPojos
     */
    public void setInfoPojos(List<QueryUserInfoPojo> infoPojos) {
        this.infoPojos = infoPojos;
    }

}
