/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 学历
 * 
 * @author jiangjd12837
 * @version $Id: EnumEduLevel.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumEduLevel {
    
    GRADUATE_STUDENT("10", "研究生"),
    UNDERGRADUATE("20", "本科"),
    JUNIOR_COLLEGE("30", "大专"),
    SECONDARY_SPECIALIZED("40", "中专"),
    TECHNICAL_CHOOL("50", "技术学校"),
    HIGH_SCHOOL("60", "高中"),
    JUNIOR_MIDDLE_SCHOOL("70", "初中"),
    PRIMARY_SCHOOL("80", "小学"),
    ILLITERACY("90", "文盲"),
    UNKNOWN("99", "未知"),

    MASTER("09", "硕士"),
    DOCTOR("08", "博士"),
    PRIMARY_SCHOOL_DROP("81", "小学肄业"),
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
    private EnumEduLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumEduLevel} 实例
    **/
    public static EnumEduLevel find(String code) {
        for (EnumEduLevel frs : EnumEduLevel.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public static EnumEduLevel findByDescription(String description) {
        for (EnumEduLevel frs : EnumEduLevel.values()) {
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
