package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumAccountKind;
import com.hsjry.user.facade.pojo.enums.EnumAccountType;
import com.hsjry.user.facade.pojo.enums.EnumResourceSource;
import com.hsjry.user.facade.pojo.enums.EnumResourceStatus;

/**
 * 金融工具
 * @author liaosq23298
 * @version $Id: UserFinancialInstrumentsandSourcePojo.java, v 0.1 Nov 22, 2017 3:29:30 PM liaosq23298 Exp $
 */
public class UserFinancialInstrumentsandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = -4806628768435296106L;
    //资源项ID
    private String             resourceId;
    //登记ID
    private String             registerId;
    //开户行
    private String             openBrankCode;
    //联行号
    private String             unionBankId;
    //账号
    @NotNull(errorCode = "000001", message = "账号")
    @NotBlank(errorCode = "000001", message = "账号")
    private String             account;
    //账号类型
    @NotNull(errorCode = "000001", message = "账号类型")
    private EnumAccountKind    accountKind;
    //账户类别
    @NotNull(errorCode = "000001", message = "账户类别")
    private EnumAccountType    accountType;
    //账户状态
    @NotNull(errorCode = "000001", message = "账户状态")
    private EnumResourceStatus accountStatus;
    //账号创建日期
    private Date               buildDate;
    //有效截止日期
    private Date               endDate;
    //开户机构名称
    private String             branchName;
    //户名
    private String             accountName;
    //省
    private String             provinceCode;
    //市
    private String             cityCode;
    //地址
    private String             address;
    //预留手机号
    private String             relationMobile;
    //是否默认绑卡
    @NotNull(errorCode = "000001", message = "是否默认绑卡")
    private EnumBool           bindCardFlag;
    //绑卡流水
    private String             bindCardJour;

    private Date createTime;

    private Date  updateTime;
    //资源来源
    private EnumResourceSource  resourceSource;

    /**
     * Getter method for property <tt>accountStatus</tt>.
     * 
     * @return property value of accountStatus
     */
    public EnumResourceStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * Setter method for property <tt>accountStatus</tt>.
     * 
     * @param accountStatus value to be assigned to property accountStatus
     */
    public void setAccountStatus(EnumResourceStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

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
     * Getter method for property <tt>accountKind</tt>.
     * 
     * @return property value of accountKind
     */
    public EnumAccountKind getAccountKind() {
        return accountKind;
    }

    /**
     * Setter method for property <tt>accountKind</tt>.
     * 
     * @param accountKind value to be assigned to property accountKind
     */
    public void setAccountKind(EnumAccountKind accountKind) {
        this.accountKind = accountKind;
    }

    /**
     * Getter method for property <tt>accountType</tt>.
     * 
     * @return property value of accountType
     */
    public EnumAccountType getAccountType() {
        return accountType;
    }

    /**
     * Setter method for property <tt>accountType</tt>.
     * 
     * @param accountType value to be assigned to property accountType
     */
    public void setAccountType(EnumAccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter method for property <tt>buildDate</tt>.
     * 
     * @return property value of buildDate
     */
    public Date getBuildDate() {
        return buildDate;
    }

    /**
     * Setter method for property <tt>buildDate</tt>.
     * 
     * @param buildDate value to be assigned to property buildDate
     */
    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    /**
     * Getter method for property <tt>endDate</tt>.
     * 
     * @return property value of endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter method for property <tt>endDate</tt>.
     * 
     * @param endDate value to be assigned to property endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    /**
     * Getter method for property <tt>bindCardFlag</tt>.
     * 
     * @return property value of bindCardFlag
     */
    public EnumBool getBindCardFlag() {
        return bindCardFlag;
    }

    /**
     * Setter method for property <tt>bindCardFlag</tt>.
     * 
     * @param bindCardFlag value to be assigned to property bindCardFlag
     */
    public void setBindCardFlag(EnumBool bindCardFlag) {
        this.bindCardFlag = bindCardFlag;
    }

    /**
     * Getter method for property <tt>bindCardJour</tt>.
     * 
     * @return property value of bindCardJour
     */
    public String getBindCardJour() {
        return bindCardJour;
    }

    /**
     * Setter method for property <tt>bindCardJour</tt>.
     * 
     * @param bindCardJour value to be assigned to property bindCardJour
     */
    public void setBindCardJour(String bindCardJour) {
        this.bindCardJour = bindCardJour;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public EnumResourceSource getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(EnumResourceSource resourceSource) {
        this.resourceSource = resourceSource;
    }
}
