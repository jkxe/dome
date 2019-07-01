/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCertificatesByUserIdListPojo.java, v 1.0 2017年8月21日 下午8:06:34 jiangjd12837 Exp $
 */
public class QueryCertificatesByUserIdListPojo implements Serializable {

    /**  */
    private static final long             serialVersionUID = 4938332510454218063L;
    /**用户ID*/
    private String                        userId;
    /**证件文档*/
    private List<UserCertificateInfoPojo> userCertificatePojoList;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userCertificatePojoList</tt>.
     * 
     * @return property value of userCertificatePojoList
     */
    public List<UserCertificateInfoPojo> getUserCertificatePojoList() {
        return userCertificatePojoList;
    }

    /**
     * Setter method for property <tt>userCertificatePojoList</tt>.
     * 
     * @param userCertificatePojoList value to be assigned to property userCertificatePojoList
     */
    public void setUserCertificatePojoList(List<UserCertificateInfoPojo> userCertificatePojoList) {
        this.userCertificatePojoList = userCertificatePojoList;
    }

}
