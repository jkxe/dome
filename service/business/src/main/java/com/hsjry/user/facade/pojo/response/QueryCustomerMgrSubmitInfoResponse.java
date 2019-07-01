package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.CustomerMgrSubmitInfoPojo;

import java.io.Serializable;

/**
 * Created by zhangxianli on 2018/6/6.
 */
public class QueryCustomerMgrSubmitInfoResponse implements Serializable {
    private CustomerMgrSubmitInfoPojo customerMgrSubmitInfoPojo;

    public CustomerMgrSubmitInfoPojo getCustomerMgrSubmitInfoPojo() {
        return customerMgrSubmitInfoPojo;
    }

    public void setCustomerMgrSubmitInfoPojo(CustomerMgrSubmitInfoPojo customerMgrSubmitInfoPojo) {
        this.customerMgrSubmitInfoPojo = customerMgrSubmitInfoPojo;
    }
}
