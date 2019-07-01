/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserRegisterAttachmentInfoPojo;
import com.hsjry.user.facade.pojo.UserRegistrationSiteInfoPojo;

/**
 * 查询登记点和登记附件接口出参
 * @author zhengqy15963
 * @version $Id: QueryRegisterSiteAndAttachmentResponse.java, v 1.0 2018年5月17日 下午4:20:46 zhengqy15963 Exp $
 */
public class QueryRegisterSiteAndAttachmentResponse implements Serializable {

    /**  */
    private static final long                    serialVersionUID = -5857907983620435435L;
    /**经办人  */
    private String                               operator;
    /**登记点信息  */
    private UserRegistrationSiteInfoPojo         registrationSiteInfoPojos;
    /**登记点附件信息  */
    private List<UserRegisterAttachmentInfoPojo> registerAttachmentInfoPojos;

    /**
     * Getter method for property <tt>operator</tt>.
     * 
     * @return property value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     * 
     * @param operator value to be assigned to property operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>registrationSiteInfoPojos</tt>.
     * 
     * @return property value of registrationSiteInfoPojos
     */
    public UserRegistrationSiteInfoPojo getRegistrationSiteInfoPojos() {
        return registrationSiteInfoPojos;
    }

    /**
     * Setter method for property <tt>registrationSiteInfoPojos</tt>.
     * 
     * @param registrationSiteInfoPojos value to be assigned to property registrationSiteInfoPojos
     */
    public void setRegistrationSiteInfoPojos(UserRegistrationSiteInfoPojo registrationSiteInfoPojos) {
        this.registrationSiteInfoPojos = registrationSiteInfoPojos;
    }

    /**
     * Getter method for property <tt>registerAttachmentInfoPojos</tt>.
     * 
     * @return property value of registerAttachmentInfoPojos
     */
    public List<UserRegisterAttachmentInfoPojo> getRegisterAttachmentInfoPojos() {
        return registerAttachmentInfoPojos;
    }

    /**
     * Setter method for property <tt>registerAttachmentInfoPojos</tt>.
     * 
     * @param registerAttachmentInfoPojos value to be assigned to property registerAttachmentInfoPojos
     */
    public void setRegisterAttachmentInfoPojos(List<UserRegisterAttachmentInfoPojo> registerAttachmentInfoPojos) {
        this.registerAttachmentInfoPojos = registerAttachmentInfoPojos;
    }

}
