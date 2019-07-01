/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 关系代码
 * 
 * @author jiangjd12837
 * @version $Id: EnumClientRelation.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumClientRelation {
    
    ONESELF("0", "本人"),
    SPOUSE("1", "配偶"),
    SON("2", "子"),
    DAUGHTER("3", "女"),
    GRANDCHILDREN("4", "孙子女或外孙子女"),
    PARENT("5", "父母"),
    GRANDPARENTS("6", "祖父母或外祖父母"),
    BROTHERS_AND_SISTERS("7", "兄弟姐妹"),
    BROTHERS("71", "兄弟"),
    SISTERS("72", "姐妹"),
    OTHER("8", "其他"),
    SON_AND_DAUGHTER("9", "子女"),
    
    LEGAL_PERSON("11", "法人"),
    LEGAL_REPRESENTATIVE("12", "法人代表人"),
    IMMEDIATE("13", "直系亲属关系"),
    Collateral("14", "旁系亲属关系"),
    BE_SECURED("15", "被担保关系"),
    GUARANTEE("16", "担保关系"),
    INVESTMENT("17", "投资关系"),
    BE_INVESTED("18", "被投资关系"),
    COLLEAGUE("19", "同事"),
    EMERGENCY_CONTACT("20", "紧急联系人关系"),

    SHAREHOLDER("81", "股东"),
    SENIOR_EXECUTIVE("82", "高管"),
    STAFF("83", "员工"),

    FRIEND("84", "朋友"),
    CLASSMATE("85", "同学"),
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
    private EnumClientRelation(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumClientRelation} 实例
    **/
    public static EnumClientRelation find(String code) {
        for (EnumClientRelation frs : EnumClientRelation.values()) {
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
