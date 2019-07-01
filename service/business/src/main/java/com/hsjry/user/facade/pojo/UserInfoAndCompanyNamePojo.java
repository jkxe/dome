/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author lilin22830
 * @version $Id: UserInfoAndCompanyNamePojo.java, v 0.1 Aug 21, 2017 9:16:12 AM lilin22830 Exp $
 */
public class UserInfoAndCompanyNamePojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -3816138779482554336L;
    
    private String userId;
    
    private String userName;
    
    private String companyName;

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

    /**
     * Getter method for property <tt>companyName</tt>.
     * 
     * @return property value of companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter method for property <tt>companyName</tt>.
     * 
     * @param companyName value to be assigned to property companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    

}
