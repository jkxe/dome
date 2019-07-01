/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 快钱证件类型
 */
public enum EnumKqIdKind {

    RESIDENT_IDENTITY_CARD("0","居民身份证","0");


    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    private String kQCode;

    /**
    * 私有构造方法
    *
    * @param code
    *            编码
    * @param description
    *            描述
    **/
    private EnumKqIdKind(String code, String description, String kQCode) {
        this.code = code;
        this.description = description;
        this.kQCode = kQCode;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code 编码
    **/
    public static EnumKqIdKind find(String code) {
        for (EnumKqIdKind frs : EnumKqIdKind.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getkQCode() {
        return kQCode;
    }

    public void setkQCode(String kQCode) {
        this.kQCode = kQCode;
    }
}
