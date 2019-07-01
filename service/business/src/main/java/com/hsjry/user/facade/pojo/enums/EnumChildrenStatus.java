/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * @author: xuxd14949
 * Date: 2018/5/9
 * Time: 19:22
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */
public enum EnumChildrenStatus {
    NO("1", "无"),
    YES("2", "有"),
    EDU("3", "求学"),
    WORK("4", "工作")

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
    private EnumChildrenStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     *
     * @param code
     *            编码
     * @return {@link EnumMarriageStatus} 实例
     **/
    public static EnumChildrenStatus find(String code) {
        for (EnumChildrenStatus frs : EnumChildrenStatus.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public static EnumMarriageStatus findByDescription(String description) {
        for (EnumMarriageStatus frs : EnumMarriageStatus.values()) {
            if (frs.getDescription().equals(description)) {
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
