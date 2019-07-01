/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 客户角色审核状态枚举定义
 * @author hongsj
 * @version $Id: EnumVerifyStatusCode.java, v 1.0 2017年3月7日 下午7:14:13 hongsj Exp $
 */
public enum EnumVerifyStatus {

    NOT_AUDIT("0", "未审核"), 
    UNDER_REVIEW("1", "审核中"),
    PASS_AUDIT("2","通过"),
    VETOED("3","否决");

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
    private EnumVerifyStatus(String code, String description) {
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
    public static EnumVerifyStatus find(String code) {
        for (EnumVerifyStatus frs : EnumVerifyStatus.values()) {
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
