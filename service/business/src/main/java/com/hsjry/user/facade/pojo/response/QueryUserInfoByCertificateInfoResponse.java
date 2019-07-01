/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserBasicInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryUserInfoByCertificateInfoResponse.java, v 1.0 2018年6月6日 下午3:49:10 zhengqy15963 Exp $
 */
public class QueryUserInfoByCertificateInfoResponse implements Serializable {

    /**  */
    private static final long            serialVersionUID = -1937351772503686765L;
    /**根据证件信息查询出的个人主要信息列表  */
    private List<QueryUserBasicInfoPojo> basicInfoPojos;

    /**
     * Getter method for property <tt>basicInfoPojos</tt>.
     * 
     * @return property value of basicInfoPojos
     */
    public List<QueryUserBasicInfoPojo> getBasicInfoPojos() {
        return basicInfoPojos;
    }

    /**
     * Setter method for property <tt>basicInfoPojos</tt>.
     * 
     * @param basicInfoPojos value to be assigned to property basicInfoPojos
     */
    public void setBasicInfoPojos(List<QueryUserBasicInfoPojo> basicInfoPojos) {
        this.basicInfoPojos = basicInfoPojos;
    }

}
