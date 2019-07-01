/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/7/23
 * Time: 9:50
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class SubmitCustInfoRequest implements Serializable {

    private static final long serialVersionUID = -82297272617558844L;

    private String id;
    /**
     * 用户编号
     */
    @NotNull(errorCode = "000001", message = "用户编号")
    @NotBlank(errorCode = "000001", message = "用户编号")
    private String userNo;

    /**
     * 产品编号
     */
    @NotNull(errorCode = "000001", message = "产品编号")
    @NotBlank(errorCode = "000001", message = "产品编号")
    private String productId;

    private String applyId;

    /**
     * 客户经理编号
     */
    @NotNull(errorCode = "000001", message = "客户经理编号")
    @NotBlank(errorCode = "000001", message = "客户经理编号")
    private String managerId;
    /**
     * 客户经理工号
     */
    @NotNull(errorCode = "000001", message = "客户经理工号")
    @NotBlank(errorCode = "000001", message = "客户经理工号")
    private String managerNum;


    private String managerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerNum() {
        return managerNum;
    }

    public void setManagerNum(String managerNum) {
        this.managerNum = managerNum;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}

