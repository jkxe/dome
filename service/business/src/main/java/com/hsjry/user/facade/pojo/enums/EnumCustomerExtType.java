/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 
 * @author jingqi17258
 * @version $Id: EnumCustomerExtType.java, v 1.0 2017年6月17日 下午4:53:56
 *          jingqi17258 Exp $
 */
public enum EnumCustomerExtType {
	ALIPAY_INFO("1", "客户支付宝信息"),
	JBH_INFO("2","聚宝汇客户信息"),
	IDCARD_REALNAME("3","实名认证"),
	BINDCARD("4","绑卡鉴权"),
	FACE_IDENTIFY("5","身份识别"),
	SET_PASSWORD("6","设置交易密码"),
	CREDIT_INFO_ZX("7","授信资料填写(尊享)"),
	CREDIT_INFO_LK("8","授信资料填写(老客)"),
	CREDIT_INFO_SELF("9","个人信息资料填写"),
	CREDIT_INFO_CAR_SUBMIT("10","授信排查提交(车位贷)"),
	CREDIT_INFO_HOUSE_SUBMIT("11","授信排查提交(家装贷)"),
	ONLIINE_OR_OFFLINE("12","线上线下标志(1.线上 2.线下)")
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
	private EnumCustomerExtType(String code, String description) {
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
	public static EnumCustomerExtType find(String code) {
		for (EnumCustomerExtType frs : EnumCustomerExtType.values()) {
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
