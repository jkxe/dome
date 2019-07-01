/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 职务 
 * 
 * @author jiangjd12837
 * @version $Id: EnumActiveStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumPost {
    
    SENIOR_MANAGEMENT("A", "高级管理人员/厅局级及以上"),
    MIDDLE_MANAGEMENT("B", "中层管理人员/县处级"),
    LOWER_MANAGEMENT("C", "低层管理人员/科级"),
    GENERAL_STAFF("D", "一般员工/科员"),
    OFFICE("E", "内勤"),
    LOGISTICS("F", "后勤"),
    WORKER("G", "工人"),
    SALES_AGENCY_("H", "销售/中介/业务代表"),
    SALES_WAITER("I", "营业员/服务员"),
    MINISTERIAL_LEVEL("J", "正部级"),
    VICE_MINISTERIAL_LEVEL("K", "副部级"),
    DEPUTY("L", "正厅级"),
    DEPUTY_DEPARTMENT("M", "副厅级"),
    GRADE_DIVISION("N", "正处级"),
    SUB_DIVISION("O", "副处级"),
    FAMILY_DIVISION("P", "正科级"),
    VICE_SECTION("Q", "副科级"),
    CLASS_IS("R", "正股级"),
    VICE_OFFICE_LEVEL("S", "副股级"),
    OTHER("Z", "其他"),
    STAFF("STAFF", "普通员工"),
    MANAGER("MANAGER", "管理人员");
    
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
    EnumPost(String code, String description) {
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
    public static EnumPost find(String code) {
        for (EnumPost frs : EnumPost.values()) {
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
