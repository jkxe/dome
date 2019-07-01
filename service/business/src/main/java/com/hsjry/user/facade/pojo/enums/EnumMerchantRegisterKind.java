/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 登记点类型
 * 
 * @author wanglg15468
 * @version $Id: EnumMerchantRegisterKind.java, v 1.0 2017-3-14 下午7:19:32 wanglg15468 Exp $
 */
public enum EnumMerchantRegisterKind {
                               
    MERCHANT_BASIC_INFO_CHANGE("5","商户基本信息变更"),
    MERCHANT_ACCOUNT_CHANGE("7","商户资金账户变更");
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
    private EnumMerchantRegisterKind(String code, String description) {
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
    public static EnumMerchantRegisterKind find(String code) {
        for (EnumMerchantRegisterKind frs : EnumMerchantRegisterKind.values()) {
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
