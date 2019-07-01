/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 用户基本信息
 * @author hongsj
 * @version $Id: UserBasicPojo.java, v 1.0 2017年3月29日 下午3:29:23 hongsj Exp $
 */
public class UserBasicPojo implements Serializable {
    /**  */
    private static final long serialVersionUID = -1308582909475725520L;
    /**用户id*/
    private String            userId;
    /**用户姓名*/
    private String            userName;

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
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
