/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 电话分类代码
 * 
 * @author jiangjd12837
 * @version $Id: EnumTelephoneClassCode.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumTelephoneClassCode {
    
    INSTITUTIONAL_CUSTOMER_PHONE("100", "机构客户电话"),
    INSTITUTIONAL_CLIENT_FIXED_TELEPHONE("110", "机构客户公务固定电话"),
    OFFICIAL_FAX_CALL("120", "机构客户公务传真电话"),
    PHS_OFFICIAL_INSTITUTIONAL_CLIENTS("130", "机构客户公务无线市话"),
    INSTITUTIONAL_CLIENT_MOBILE_PHONE("140", "机构客户公务移动电话"),
    INSTITUTIONAL_CLIENT_FINANCIAL_TELEPHONE("150", "机构客户财务电话"),
    PERSONAL_CUSTOMER_PHONE("200", "个人客户电话"),
    PERSONAL_CUSTOMER_HOME_PHONE("210", "个人客户住宅电话"),
    PERSONAL_CUSTOMER_RESIDENTIAL_FIXED_TELEPHONE("211", "个人客户住宅固定电话"),
    PERSONAL_CUSTOMER_HOME_FAX("212", "个人客户住宅传真电话"),
    PERSONAL_CUSTOMER_OFFICE_TELEPHONE("220", "个人客户办公电话"),
    PERSONAL_CUSTOMER_OFFICE_FIXED_TELEPHONE("221", "个人客户办公固定电话"),
    PERSONAL_CUSTOMER_OFFICE_FAX("222", "个人客户办公传真电话"),
    INDIVIDUAL_CUSTOMERS_PHS("230", "个人客户无线市话"),
    PERSONAL_CUSTOMER_MOBILE_PHONE("240", "个人客户移动电话"),
    PERSONAL_AGENT_MOBILE_PHONE("241", "个人客户代理电话");
    
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
    private EnumTelephoneClassCode(String code, String description) {
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
    public static EnumTelephoneClassCode find(String code) {
        for (EnumTelephoneClassCode frs : EnumTelephoneClassCode.values()) {
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
