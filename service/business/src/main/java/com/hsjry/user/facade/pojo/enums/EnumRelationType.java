/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 关系类型
 * 
 * @author jiangjd12837
 * @version $Id: EnumRelationType.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumRelationType {
    
    BASIC_RELATION("1", "基本关系"),
    EXTENDED_RELATION("2", "扩展关系"),

    DIRCT_RELATION("3", "直系关系"),
    URGENCY_RELATION("4", "紧急关系"),
    COLLEAGUE_RELATION("5", "同事关系"),
    ;
    
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
    private EnumRelationType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumRelationType} 实例
    **/
    public static EnumRelationType find(String code) {
        for (EnumRelationType frs : EnumRelationType.values()) {
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
