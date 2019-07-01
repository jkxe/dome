/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * @author: xuxd14949
 * Date: 2018/5/17
 * Time: 15:37
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public enum EnumDraftStatus {

    NEW("1", "新增"),
    UPDATE("2", "修改"),
    DELETE("3", "删除"),

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
    private EnumDraftStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     *
     * @param code
     *            编码
     * @return {@link EnumEduLevel} 实例
     **/
    public static EnumDraftStatus find(String code) {
        for (EnumDraftStatus frs : EnumDraftStatus.values()) {
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

