/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xuxd14949
 * Date: 2018/5/17
 * Time: 16:08
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryUserDraftRequest implements Serializable {

    private static final long serialVersionUID = 7627105011428152094L;

    /**
     * 草稿箱编号
     */
    private String applyId;

    private String userName;

    private String idNo;

    private String mobile;

    private String productId;

    private Date startDate;

    private Date endDate;

    private boolean filterFlag;

    private String managerNo;

    private String xfdApplyId;

    private String productCode;

    public String getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(String managerNo) {
        this.managerNo = managerNo;
    }

    public boolean isFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(boolean filterFlag) {
        this.filterFlag = filterFlag;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

