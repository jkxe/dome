/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 登记附件类型
 * @author zhengqy15963
 * @version $Id: EnumRegisterAttachmentKind.java, v 1.0 2018年5月9日 下午7:17:42 zhengqy15963 Exp $
 */
public enum EnumRegisterAttachmentType {


    MERCHANT_SERVICE_MANAGER("1","商户业务经理附件"),
    MERCHANT_BASIC_INFO("2","商户基本信息附件"),
    ORGAN_BASIC_INFO("3","门店基本信息附件"),
    MERCHANT_ACCOUNT("4","商户资金账户附件"),
    ORGAN_ACCOUNT("5","门店资金账户附件");
    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    /**
    * 私有构造方法
    * 
    * @param code
    *            编码
    * @param description
    *            描述
    **/
    private EnumRegisterAttachmentType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumBearingMode} 实例
    **/
    public static EnumRegisterAttachmentType find(String code) {
        for (EnumRegisterAttachmentType frs : EnumRegisterAttachmentType.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    /**
    * Getter method for property <tt>code</tt>.
    * 
    * @return property value of code
    **/
    public String getCode() {
        return code;
    }

    /**
    * Getter method for property <tt>description</tt>.
    * 
    * @return property value of description
    **/
    public String getDescription() {
        return description;
    }


}
