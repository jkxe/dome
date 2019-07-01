/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * @author: xuxd14949
 * Date: 2018/5/10
 * Time: 9:12
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */
public enum EnumPaymentPeriod {
    ONE_MONTH("1", "1个月"),
    TWO_MONTH("2", "2个月"),
    THREE_MONTH("3", "3个月"),
    FOUR_MONTH("4", "4个月"),
    FIVE_MONTH("5", "5个月"),
    SIX_MONTH("6", "6个月"),
    SEVEN_MONTH("7", "7个月"),
    EIGHT_MONTH("8", "8个月"),
    NINE_MONTH("9", "9个月"),
    TEN_MONTH("10", "10个月"),
    ELVEN_MONTH("11", "11个月"),
    ONE_YEAR_MORE("12", "1年及以上");

    /**
     * 状态码
     **/
    private String code;
    /**
     * 状态描述
     **/
    private String description;

    /**
     * 私有构造方法
     *
     * @param code        编码
     * @param description 描述
     **/
    EnumPaymentPeriod(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumPaymentPeriod} 实例
     **/
    public static EnumPaymentPeriod find(String code) {
        for (EnumPaymentPeriod frs : EnumPaymentPeriod.values()) {
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
