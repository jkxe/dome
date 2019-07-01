/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 变更材料信息
 * @author zhengqy15963
 * @version $Id: ChangeDataInfoPojo.java, v 1.0 2018年5月3日 下午7:01:38 zhengqy15963 Exp $
 */
public class AttachmentDataInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -4697205055289268490L;
    /**变更材料附件名称  */
    @NotNull(errorCode = "000001", message = "变更材料附件名称")
    @NotBlank(errorCode = "000001", message = "变更材料附件名称")
    private String            attachmentName;
    /**变更材料附件URL */
    @NotNull(errorCode = "000001", message = "变更材料附件URL")
    @NotBlank(errorCode = "000001", message = "变更材料附件URL")
    private String            attachmentUrl;

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

}
