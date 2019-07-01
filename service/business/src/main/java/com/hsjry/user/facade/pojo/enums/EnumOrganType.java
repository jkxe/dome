/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 机构类别
 * 
 * @author jiangjd12837
 * @version $Id: EnumOrganType.java, v 1.0 2017年3月18日 上午9:42:22 jiangjd12837 Exp $
 */
public enum EnumOrganType {

    SELF_EMPLOYED_INDUSTRIAL_AND_COMMERCIAL_HOUSEHOLD("1", "个体经营户"),
    INDIVIDUAL_INDUSTRIAL_AND_COMMERCIAL_HOUSEHOLDS("2", "个体工商户"),
    PRIVATE_ENTERPRISE_LEGAL_PERSON("3", "民营企业法人"),
    PRIVATE_ENTERPRISE_NON_LEGAL_PERSON("4", "民营企业非法人"),
    STATE_OWNED_ENTERPRISE_LEGAL_PERSON("5", "国有企业法人"),
    NON_STATE_OWNED_ENTERPRISE("6", "国有企业非法人"),
    HMT_INVESTMENT_ENTERPRISES("7", "港澳台商投资企业法人"),
    HMT_ENTERPRISES("8", "港澳台商投资企业非法人"),
    FOREIGN_INVESTMENT_ENTERPRISE("9", "外商企业投资法人"),
    FOREIGN_ENTERPRISES_TO_INVEST_IN_NON_LEGAL_PERSON("10", "外商企业投资非法人"),
    FINANCIAL_INSTITUTIONS("11", "金融同业机构"),
    GUARANTEE_CORPORATION("12", "担保公司"),
    LEGAL_PERSON("13", "机关法人"),
    UNINCORPORATED_ORGANIZATION("14", "机关非法人"),
    ADMINISTRATIVE_CORPORATION("15", "行政法人"),
    ADMINISTRATIVE_ILLEGAL_PERSON("16", "行政非法人"),
    NOT_FOR_PROFIT_CORPORATION("17", "事业法人"),
    ENTERPRISE_LEGAL_PERSON("18", "事业非法人"),
    CORPORATION("19", "社团法人"),
    UNINCORPORATED_SOCIETY("20", "社团非法人"),
    OTHER("x", "其他"),
    OVERSEAS_INSTITUTIONS("21", "境外机构");

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
    private EnumOrganType(String code, String description) {
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
    public static EnumOrganType find(String code) {
        for (EnumOrganType frs : EnumOrganType.values()) {
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
