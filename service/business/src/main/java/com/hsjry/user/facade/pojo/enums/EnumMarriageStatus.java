/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 婚姻状况
 * 
 * @author jiangjd12837
 * @version $Id: RecordStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumMarriageStatus {
    UNMARRIED("10", "未婚"),
    MARRIED("20", "已婚"),
    FIRST_MARRIAGE("21", "初婚"),
    REMARRIAGE("22", "再婚"),
    REMARRY("23", "复婚"),  
    WIDOWED("30", "丧偶"),
    DIVORCE("40", "离婚"),
    NOT_EXPLAINED("90", "未说明的婚姻状况"),

    MARRIED_HAVE_CHILD("24", "已婚有子女"),
    MARRIED_NO_CHILD("25", "已婚无子女"),
    OTHER("99", "其它"),
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
    private EnumMarriageStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumMarriageStatus} 实例
    **/
    public static EnumMarriageStatus find(String code) {
        for (EnumMarriageStatus frs : EnumMarriageStatus.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public static EnumMarriageStatus findByDescription(String description) {
        for (EnumMarriageStatus frs : EnumMarriageStatus.values()) {
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
