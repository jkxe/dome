/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/5/9
 * Time: 20:13
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class UserCreditApplyInfoPojo implements Serializable {

    private static final long serialVersionUID = -6953208891264336795L;

    //资源项ID
    private String resourceId;
    //客户ID
    private String userId;
    //推荐人手机号
    private String referMobile;
    //借款金额
    private String applyAmount;
    //其他资产
    private String otherAsset;
    //推荐人名称
    private String referName;
    //备注信息
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferMobile() {
        return referMobile;
    }

    public void setReferMobile(String referMobile) {
        this.referMobile = referMobile;
    }


    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getOtherAsset() {
        return otherAsset;
    }

    public void setOtherAsset(String otherAsset) {
        this.otherAsset = otherAsset;
    }

    public String getReferName() {
        return referName;
    }

    public void setReferName(String referName) {
        this.referName = referName;
    }

}

