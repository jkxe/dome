/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 不动产类型
 * 
 * @author wanglg15468
 * @version $Id: EnumImmovableType.java, v 1.0 2017-3-15 下午7:38:05 wanglg15468 Exp $
 */
public enum EnumImmovableType {
                               
    BUILD_BY_SELF("1","自置"),
    MORTGAGE("2", "按揭"),
    RELATIVES_BUILDING("3","亲属楼宇"),
    DORMITORY("4","集体宿舍"),
    LEASE("5","租房"),
    SHARED_HOUSING("6","共有住宅"),
    OTHER("7","其他"),
    UNKNOWN("9","未知");
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
    private EnumImmovableType(String code, String description) {
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
    public static EnumImmovableType find(String code) {
        for (EnumImmovableType frs : EnumImmovableType.values()) {
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
