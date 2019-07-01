package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class QueryMerchantInfoByUserIdRequest implements Serializable{

    /**  */
    private static final long serialVersionUID = -758627450192982770L;
    // 客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String userId;

    public String getUserId() {
    return userId;
    }

    public void setUserId(String userId) {
    this.userId = userId;
    }
}
