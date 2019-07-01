/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 索引枚举
 * 
 * @author jiangjd12837
 * @version $Id: EnumNeedIndex.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumNeedIndex {
    
    MERCHANTNAME("merchantName", "商户名称"),
    INDUSTRYTYPE("industryType", "行业类别"),
    INDUSTRYLABEL("industryLabel", "行业标签"),
    COMPANYTELEPHONE("companyTelephone","单位固话"),
    COMPANY_NAME("companyName","单位名称"),
    MERCHANTUSERTYPE("merchantUserType","经销商分类")
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
    private EnumNeedIndex(String code, String description) {
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
    public static EnumNeedIndex find(String code) {
        for (EnumNeedIndex frs : EnumNeedIndex.values()) {
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
