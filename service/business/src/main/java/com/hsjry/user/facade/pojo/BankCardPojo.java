/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * 
 * @author zhengqy15963
 * @version $Id: BankCardPojo.java, v 1.0 2017年11月27日 下午5:05:13 zhengqy15963 Exp $
 */
public class BankCardPojo implements Serializable {

    /**  */
    private static final long   serialVersionUID = -3070625098789661618L;
    /** 银行卡号ID--不存 */
    private int                 id;
    /** 银行编号 --存联行号*/
    private String              bankNo;
    /** 银行卡号*/
    private String              acctNum;
    /** 证件类型*/
    private EnumCertificateKind cardType;
    /** 证件号码*/
    private String              credNum;
    /** 预留手机号*/
    private String              relationMobile;

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>bankNo</tt>.
     * 
     * @return property value of bankNo
     */
    public String getBankNo() {
        return bankNo;
    }

    /**
     * Setter method for property <tt>bankNo</tt>.
     * 
     * @param bankNo value to be assigned to property bankNo
     */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    /**
     * Getter method for property <tt>acctNum</tt>.
     * 
     * @return property value of acctNum
     */
    public String getAcctNum() {
        return acctNum;
    }

    /**
     * Setter method for property <tt>acctNum</tt>.
     * 
     * @param acctNum value to be assigned to property acctNum
     */
    public void setAcctNum(String acctNum) {
        this.acctNum = acctNum;
    }

    /**
     * Getter method for property <tt>cardType</tt>.
     * 
     * @return property value of cardType
     */
    public EnumCertificateKind getCardType() {
        return cardType;
    }

    /**
     * Setter method for property <tt>cardType</tt>.
     * 
     * @param cardType value to be assigned to property cardType
     */
    public void setCardType(EnumCertificateKind cardType) {
        this.cardType = cardType;
    }

    /**
     * Getter method for property <tt>credNum</tt>.
     * 
     * @return property value of credNum
     */
    public String getCredNum() {
        return credNum;
    }

    /**
     * Setter method for property <tt>credNum</tt>.
     * 
     * @param credNum value to be assigned to property credNum
     */
    public void setCredNum(String credNum) {
        this.credNum = credNum;
    }

    /**
     * Getter method for property <tt>relationMobile</tt>.
     * 
     * @return property value of relationMobile
     */
    public String getRelationMobile() {
        return relationMobile;
    }

    /**
     * Setter method for property <tt>relationMobile</tt>.
     * 
     * @param relationMobile value to be assigned to property relationMobile
     */
    public void setRelationMobile(String relationMobile) {
        this.relationMobile = relationMobile;
    }

}
