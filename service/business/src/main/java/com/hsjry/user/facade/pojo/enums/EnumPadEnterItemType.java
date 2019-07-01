/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * @author: xuxd14949
 * Date: 2018/5/18
 * Time: 16:18
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */
public enum EnumPadEnterItemType {

    BASIC_INFO("0", "基本信息"),
    LOAN_INFO("1", "借款信息"),
    WORK_INFO("2","职业信息"),
    CONTACT_INFO("3","联系人信息"),
    HOUSE_MONEY_INFO("4","家庭财产"),
    ACCOUNT_INFO("5","账户信息"),
    ID_CARD_IMAGE_INFO("6","申请表及身份证"),
    CAR_IMAGE_INFO("7","车位影像"),
    HOUSE_IMAGE_INFO("8","房产影像"),
    HY_IMAGE_INFO("9","合影"),
    OTHER_IMAGE_INFO("10","其他附件"),
    COMMENT("11","备注信息");

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
    private EnumPadEnterItemType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     *
     * @param code
     *            编码
     * @return {@link EnumPadEnterItemType} 实例
     **/
    public static EnumPadEnterItemType find(String code) {
        for (EnumPadEnterItemType frs : EnumPadEnterItemType.values()) {
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
