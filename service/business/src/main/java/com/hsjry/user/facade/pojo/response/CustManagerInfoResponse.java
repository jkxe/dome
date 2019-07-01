/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/7/23
 * Time: 10:02
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class CustManagerInfoResponse implements Serializable {

    private static final long serialVersionUID = 7988070385652296720L;

    private String id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 产品编号
     */
    private String productId;

    /**
     * 申请编号
     */
    private String applyId;
    /**
     * 客户经理编号
     */
    private String managerId;
    /**
     * 客户经理工号
     */
    private String managerNum;
    /**
     * 客户经理名称
     */
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

