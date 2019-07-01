package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.enums.EnumResellerType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangxianli on 2018/6/6.
 */
public class UserCustomerMgrSubmitInfoRequest implements Serializable {
    /**
     * 客户经理暂存信息编号
     */
    private String id;
    /**
     * 草稿箱编号
     */
    private String userDraftInfoId;
    /**
     * 客户姓名
     */
    private String userName;
    /**
     * 客户手机号
     */
    private String mobile;
    /**
     * 经销商编号
     */
    private String resellerId;
    /**
     * 经销商名字
     */
    private String resellerName;

    /**
     * 经销商类型
     */
    private EnumResellerType enumResellerType;
    /**
     * 产品编号
     */
    private String productId;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 首付
     */
    private BigDecimal payment;
    /**
     * 申请金额
     */
    private BigDecimal applyAmount;
    /**
     * 客户身份证号
     */
    private String idNo;
    /**
     * 状态
     */
    private Byte state;

    public EnumResellerType getEnumResellerType() {
        return enumResellerType;
    }

    public void setEnumResellerType(EnumResellerType enumResellerType) {
        this.enumResellerType = enumResellerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserDraftInfoId() {
        return userDraftInfoId;
    }

    public void setUserDraftInfoId(String userDraftInfoId) {
        this.userDraftInfoId = userDraftInfoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResellerId() {
        return resellerId;
    }

    public void setResellerId(String resellerId) {
        this.resellerId = resellerId;
    }

    public String getResellerName() {
        return resellerName;
    }

    public void setResellerName(String resellerName) {
        this.resellerName = resellerName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
