/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 性别
 * 
 * @author jiangjd12837
 * @version $Id: RecordStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumGender {
    
    UNKNOWN("0", "未知的性别"),
    MALE("1", "男"),
    FEMALE("2", "女"),
    NOT_EXPLAINED("9", "未说明的性别");
    
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
    private EnumGender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumGender} 实例
    **/
    public static EnumGender find(String code) {
        for (EnumGender frs : EnumGender.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public static EnumGender findByDescription(String description) {
        for (EnumGender frs : EnumGender.values()) {
            if (frs.getDescription().equals(description)) {
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
