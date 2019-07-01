package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 客户是否黑名单
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserListStatusRequest.java, v 1.0 2017年3月13日 下午4:54:31 jiangjd12837 Exp $
 */
public class QueryUserListStatusRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -5345887575974104727L;
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String            userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
