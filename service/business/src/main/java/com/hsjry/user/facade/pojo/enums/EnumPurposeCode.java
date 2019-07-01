/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 识别用途
 * @author huangbb
 * @version $Id: EnumFreezType.java, v 1.0 2017年3月23日 上午11:06:49 huangbb Exp $
 */
public enum EnumPurposeCode {
    /** 0-登录 */
    LOGIN("0", "登录"),
    /** 1-交易 */
    TRADE("1", "交易"),
    /** 2-查询 */
    QUERY("2", "查询");
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
    private EnumPurposeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     * 
     * @param code
     *            编码
     * @return 实例
     **/
    public static EnumPurposeCode find(String code) {
        for (EnumPurposeCode frs : EnumPurposeCode.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
        // throw new Exception("错误码", "根据code=" + code+ "获取渠道标示失败");
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
