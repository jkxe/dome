package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

/**
 * 客户ID列表查询客户信息
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserInfoByuserIdlistRequest.java, v 1.0 2017年3月13日 下午4:54:23 jiangjd12837 Exp $
 */

public class QueryUserCustomerInfoByuserIdlistRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 5471368135395782583L;
    @NotNull(errorCode = "000001", message = "客户ID列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "客户ID列表")
    private List<String>      userId;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public List<String> getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

}
