/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumCheckFaceStatus;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/5/17
 * Time: 14:21
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class UserDraftRequest implements Serializable {

    private static final long serialVersionUID = -2491936849338863625L;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户编号
     */
    private String userNo;
    /**
     * 身份证号
     */
    private String idNo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 产品编号
     */
    private String productId;
    /**
     * 客户经理编号
     */
    private String managerId;

    /**
     * 组织机构
     */
    private String authority;
    /**
     * 身份证地址
     */
    private String idCardUrl;

    private EnumBool isPass;//是否通过联网核查

    /**
     * 消费贷申请编号
     */
    private String xfdApplyId;
    /**
     * 产品类型
     */
    private String productCode;

    private EnumCheckFaceStatus checkFaceStatus;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getIdCardUrl() {
        return idCardUrl;
    }

    public void setIdCardUrl(String idCardUrl) {
        this.idCardUrl = idCardUrl;
    }

    public EnumCheckFaceStatus getCheckFaceStatus() {
        return checkFaceStatus;
    }

    public void setCheckFaceStatus(EnumCheckFaceStatus checkFaceStatus) {
        this.checkFaceStatus = checkFaceStatus;
    }

    public EnumBool getIsPass() {
        return isPass;
    }

    public void setIsPass(EnumBool isPass) {
        this.isPass = isPass;
    }

    public String getXfdApplyId() {
        return xfdApplyId;
    }

    public void setXfdApplyId(String xfdApplyId) {
        this.xfdApplyId = xfdApplyId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}

