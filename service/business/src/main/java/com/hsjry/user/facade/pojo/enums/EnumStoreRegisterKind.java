/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 登记点类型
 * 
 * @author wanglg15468
 * @version $Id: EnumStoreRegisterKind.java, v 1.0 2017-3-14 下午7:19:32 wanglg15468 Exp $
 */
public enum EnumStoreRegisterKind {
                               
    ORGAN_BASIC_INFO_CHANGE("6","门店基本信息变更"),
    ORGAN_ACCOUNT_CHANGE("8","门店资金账户变更");
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
    private EnumStoreRegisterKind(String code, String description) {
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
    public static EnumStoreRegisterKind find(String code) {
        for (EnumStoreRegisterKind frs : EnumStoreRegisterKind.values()) {
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
