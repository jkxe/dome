/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumRegisterAttachmentType;

/**
 * 
 * @author zhengqy15963
 * @version $Id: UserRegisterAttachmentInfoPojo.java, v 1.0 2018年5月17日 下午4:27:03 zhengqy15963 Exp $
 */
public class UserRegisterAttachmentInfoPojo implements Serializable {

    /**  */
    private static final long          serialVersionUID = -3429024102369080641L;
    /**登记附件ID  */
    private String                     registerAttachmentId;
    /**登记ID  */
    private String                     registerId;
    /**登记附件类型  */
    private EnumRegisterAttachmentType registerAttachmentType;
    /**附件URL  */
    private String                     attachmentUrl;
    /**附件名称  */
    private String                     attachmentName;

    /**
     * Getter method for property <tt>registerAttachmentId</tt>.
     * 
     * @return property value of registerAttachmentId
     */
    public String getRegisterAttachmentId() {
        return registerAttachmentId;
    }

    /**
     * Setter method for property <tt>registerAttachmentId</tt>.
     * 
     * @param registerAttachmentId value to be assigned to property registerAttachmentId
     */
    public void setRegisterAttachmentId(String registerAttachmentId) {
        this.registerAttachmentId = registerAttachmentId;
    }

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * Getter method for property <tt>registerAttachmentType</tt>.
     * 
     * @return property value of registerAttachmentType
     */
    public EnumRegisterAttachmentType getRegisterAttachmentType() {
        return registerAttachmentType;
    }

    /**
     * Setter method for property <tt>registerAttachmentType</tt>.
     * 
     * @param registerAttachmentType value to be assigned to property registerAttachmentType
     */
    public void setRegisterAttachmentType(EnumRegisterAttachmentType registerAttachmentType) {
        this.registerAttachmentType = registerAttachmentType;
    }

    /**
     * Getter method for property <tt>attachmentUrl</tt>.
     * 
     * @return property value of attachmentUrl
     */
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     * Setter method for property <tt>attachmentUrl</tt>.
     * 
     * @param attachmentUrl value to be assigned to property attachmentUrl
     */
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    /**
     * Getter method for property <tt>attachmentName</tt>.
     * 
     * @return property value of attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * Setter method for property <tt>attachmentName</tt>.
     * 
     * @param attachmentName value to be assigned to property attachmentName
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

}
