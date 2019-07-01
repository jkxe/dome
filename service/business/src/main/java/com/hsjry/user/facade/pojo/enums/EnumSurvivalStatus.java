/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 存续状态
 * 
 * @author wanglg15468
 * @version $Id: EnumSurvivalStatus.java, v 1.0 2017-3-14 下午7:58:26 wanglg15468 Exp $
 */
public enum EnumSurvivalStatus {
    
    DO_BUSINESS("0", "营业"),
    OUT_OF_BUSINESS("1", "停业"),
    CLOSE("2", "关闭"),
    BUILDING("3","筹建"),
    OTHER("X", "其他");
    
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
    private EnumSurvivalStatus(String code, String description) {
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
    public static EnumSurvivalStatus find(String code) {
        for (EnumSurvivalStatus frs : EnumSurvivalStatus.values()) {
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
