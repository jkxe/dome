/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 绑定类型
 * 
 * @author wanglg15468
 * @version $Id: EnumBindLoginType.java, v 1.0 2017-3-14 下午6:57:27 wanglg15468 Exp $
 */
public enum EnumBindLoginType {
                               
    GESTURE_PASSWORD("1", "手势密码"),
    FINGERPRINT_IDENTIFICATION("2", "指纹识别"),
    FACE_RECOGNITION("6", "人脸识别");

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
    private EnumBindLoginType(String code, String description) {
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
    public static EnumBindLoginType find(String code) {
        for (EnumBindLoginType frs : EnumBindLoginType.values()) {
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
