package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 客户是否黑名单
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserListStatusResponse.java, v 1.0 2017年3月13日 下午4:55:46 jiangjd12837 Exp $
 */
public class QueryUserListStatusResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -1867882016472802691L;
    private EnumBool          listStatus;
    public EnumBool getListStatus() {
        return listStatus;
    }
    public void setListStatus(EnumBool listStatus) {
        this.listStatus = listStatus;
    }
}
