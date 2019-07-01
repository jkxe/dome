/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * 
 * @author huangbb
 * @version $Id: UserBankCardInfoPojo.java, v 1.0 2017年4月7日 下午4:49:41 huangbb Exp $
 */
public class UserBankCardInfoPojo implements Serializable{

    private static final long serialVersionUID = -1375535875095498436L;
    
    /**开户行*/
    @NotBlank(errorCode = "000001", message = "开户行")
    @NotNull(errorCode = "000001", message = "开户行")
    private String             openBrankCode;
    /**联行号 @ignore_rpc_make_app */
    private String             unionBankId;
    /**银行卡号*/
    @NotBlank(errorCode = "000001", message = "银行卡号")
    @NotNull(errorCode = "000001", message = "银行卡号")
    private String             account;
    /**开户机构名称*/
    private String             branchName;
    /**户名 @ignore_rpc_make_app */
    private String             accountName;
    /**省 @ignore_rpc_make_app */
    private String             provinceCode;
    /**市 @ignore_rpc_make_app */
    private String             cityCode;
    /**地址 @ignore_rpc_make_app */
    private String             address;
    /**
     * Getter method for property <tt>openBrankCode</tt>.
     * 
     * @return property value of openBrankCode
     */
    public String getOpenBrankCode() {
        return openBrankCode;
    }
    /**
     * Setter method for property <tt>openBrankCode</tt>.
     * 
     * @param openBrankCode value to be assigned to property openBrankCode
     */
    public void setOpenBrankCode(String openBrankCode) {
        this.openBrankCode = openBrankCode;
    }
    /**
     * Getter method for property <tt>unionBankId</tt>.
     * 
     * @return property value of unionBankId
     */
    public String getUnionBankId() {
        return unionBankId;
    }
    /**
     * Setter method for property <tt>unionBankId</tt>.
     * 
     * @param unionBankId value to be assigned to property unionBankId
     */
    public void setUnionBankId(String unionBankId) {
        this.unionBankId = unionBankId;
    }
    /**
     * Getter method for property <tt>account</tt>.
     * 
     * @return property value of account
     */
    public String getAccount() {
        return account;
    }
    /**
     * Setter method for property <tt>account</tt>.
     * 
     * @param account value to be assigned to property account
     */
    public void setAccount(String account) {
        this.account = account;
    }
    /**
     * Getter method for property <tt>branchName</tt>.
     * 
     * @return property value of branchName
     */
    public String getBranchName() {
        return branchName;
    }
    /**
     * Setter method for property <tt>branchName</tt>.
     * 
     * @param branchName value to be assigned to property branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    /**
     * Getter method for property <tt>accountName</tt>.
     * 
     * @return property value of accountName
     */
    public String getAccountName() {
        return accountName;
    }
    /**
     * Setter method for property <tt>accountName</tt>.
     * 
     * @param accountName value to be assigned to property accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    /**
     * Getter method for property <tt>provinceCode</tt>.
     * 
     * @return property value of provinceCode
     */
    public String getProvinceCode() {
        return provinceCode;
    }
    /**
     * Setter method for property <tt>provinceCode</tt>.
     * 
     * @param provinceCode value to be assigned to property provinceCode
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    /**
     * Getter method for property <tt>cityCode</tt>.
     * 
     * @return property value of cityCode
     */
    public String getCityCode() {
        return cityCode;
    }
    /**
     * Setter method for property <tt>cityCode</tt>.
     * 
     * @param cityCode value to be assigned to property cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    /**
     * Getter method for property <tt>address</tt>.
     * 
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Setter method for property <tt>address</tt>.
     * 
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Getter method for property <tt>serialversionuid</tt>.
     * 
     * @return property value of serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    
    

}
