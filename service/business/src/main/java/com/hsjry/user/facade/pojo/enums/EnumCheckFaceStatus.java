/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * @author: xuxd14949
 * Date: 2018/5/19
 * Time: 10:24
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */
public enum EnumCheckFaceStatus {

    NAME_ID_SAME("1", "姓名和身份证号码一致"),
    NAME_ID_NOT_SAME("2", "姓名和身份证号码不一致 "),
    NO_PEOPLE("3", " 查无此人"),

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
    private EnumCheckFaceStatus(String code, String description) {
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
    public static EnumCheckFaceStatus find(String code) {
        for (EnumCheckFaceStatus frs : EnumCheckFaceStatus.values()) {
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
