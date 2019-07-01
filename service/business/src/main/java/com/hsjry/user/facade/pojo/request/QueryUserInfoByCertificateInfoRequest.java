/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.Size;

import com.hsjry.user.facade.pojo.QueryByCertificateInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryUserInfoByCertificateInfoRequest.java, v 1.0 2018年6月6日 下午3:47:47 zhengqy15963 Exp $
 */
public class QueryUserInfoByCertificateInfoRequest implements Serializable {

    /**  */
    private static final long                serialVersionUID = 408289255939740361L;
    /**证件信息列表  */
    @Size(min = 1, max = 200, errorCode = "000001", message = "证件信息列表数量最少1条，最多200条")
    private List<QueryByCertificateInfoPojo> byCertificateInfoPojos;

    /**
     * Getter method for property <tt>byCertificateInfoPojos</tt>.
     * 
     * @return property value of byCertificateInfoPojos
     */
    public List<QueryByCertificateInfoPojo> getByCertificateInfoPojos() {
        return byCertificateInfoPojos;
    }

    /**
     * Setter method for property <tt>byCertificateInfoPojos</tt>.
     * 
     * @param byCertificateInfoPojos value to be assigned to property byCertificateInfoPojos
     */
    public void setByCertificateInfoPojos(List<QueryByCertificateInfoPojo> byCertificateInfoPojos) {
        this.byCertificateInfoPojos = byCertificateInfoPojos;
    }

}
