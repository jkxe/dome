/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 通行证识别类型
 * 
 * @author wanglg15468
 * @version $Id: EnumIdentifyType.java, v 1.0 2017-3-14 下午6:57:27 wanglg15468 Exp $
 */
public enum EnumIdentifyType {
                               
    PASSWORD("0", "密码","3"),
    GESTURE_PASSWORD("1", "手势密码","2"),
    FINGERPRINT_IDENTIFICATION("2", "指纹识别","2"),
    VERIFICATION_CODE("3", "验证码","3"),
    TRUST_LOGIN("4", "信任登陆","3"),
    SCAN_LANDING("5", "扫码登陆","3"),
    FACE_RECOGNITION("6", "人脸识别","1"),
    SECURITY_QUESTIONS("7", "安全问题答案","2"),
    UKEY_IDENTIFICATION("8", "UKEY识别","3"),
    OTHER_IDENTIFICATION("X", "其他识别","1");

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;
    
    private String level;

    /**
    * 私有构造方法
    * 
    * @param code
    *            编码
    * @param description
    *            描述
    **/
    private EnumIdentifyType(String code, String description,String level) {
        this.code = code;
        this.description = description;
        this.level = level;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumBearingMode} 实例
    **/
    public static EnumIdentifyType find(String code) {
        for (EnumIdentifyType frs : EnumIdentifyType.values()) {
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

    /**
     * Getter method for property <tt>level</tt>.
     * 
     * @return property value of level
     */
    public String getLevel() {
        return level;
    }

    
}
