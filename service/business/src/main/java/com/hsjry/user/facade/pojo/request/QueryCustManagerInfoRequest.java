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
 * Time: 10:39
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryCustManagerInfoRequest implements Serializable {

    private static final long serialVersionUID = -8307367790376890706L;

    /**用户编号*/
    @NotNull(errorCode = "000001", message = "用户编号")
    @NotBlank(errorCode = "000001", message = "用户编号")
    private String userNo;

    /**产品编号*/
    @NotNull(errorCode = "000001", message = "产品编号")
    @NotBlank(errorCode = "000001", message = "产品编号")
    private String productId;

    private String applyId;

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
}

