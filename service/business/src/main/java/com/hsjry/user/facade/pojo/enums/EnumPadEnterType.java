/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * @author: xuxd14949
 * Date: 2018/5/18
 * Time: 16:14
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public enum EnumPadEnterType {

    INTERNET_CHECK("0", "联网核查"),
    CREATE_QRCODE("1", "二维码生成"),
    WRITE_INFO("2","信息录入"),
    COLLECT_IMAGE_INFO("3","图像采集");

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
    private EnumPadEnterType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     *
     * @param code
     *            编码
     * @return {@link EnumPadEnterType} 实例
     **/
    public static EnumPadEnterType find(String code) {
        for (EnumPadEnterType frs : EnumPadEnterType.values()) {
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

