/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 报表类型
 * 
 * @author jiangjd12837
 * @version $Id: EnumActiveStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumReportKind {
    
    ANNUAL_REPORT("1", "年度报表"),
    QUARTERLY_REPORT("2", "季度报表"),
    MONTHLY_REPORT("3", "月度报表");
    
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
    private EnumReportKind(String code, String description) {
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
    public static EnumReportKind find(String code) {
        for (EnumReportKind frs : EnumReportKind.values()) {
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
