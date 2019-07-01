/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 登记点类型
 * 
 * @author wanglg15468
 * @version $Id: EnumRegisterKind.java, v 1.0 2017-3-14 下午7:19:32 wanglg15468 Exp $
 */
public enum EnumRegisterKind {
                               
    FINANCIAL_INSTRUMENTS("1", "金融工具"),
    IMAGE_DATA("2", "影像资料"),
    DOCUMENT_DOCUMENT("3", "证件文档"),
    MERCHANT_SERVICE_MANAGER_CHANGE("4","商户业务经理变更"),
    MERCHANT_BASIC_INFO_CHANGE("5","商户基本信息变更"),
    ORGAN_BASIC_INFO_CHANGE("6","门店基本信息变更"),
    MERCHANT_ACCOUNT_CHANGE("7","商户资金账户变更"),
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
    private EnumRegisterKind(String code, String description) {
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
    public static EnumRegisterKind find(String code) {
        for (EnumRegisterKind frs : EnumRegisterKind.values()) {
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
