package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description:
 * @date 2017年08月05日  21:49
 */
public class QueryOtherUserByCompanyTelPojo implements Serializable {

    //客户信息
    private UserCustomerInfoPojo userCustomerInfoPojo;

    public UserCustomerInfoPojo getUserCustomerInfoPojo() {
        return userCustomerInfoPojo;
    }

    public void setUserCustomerInfoPojo(UserCustomerInfoPojo userCustomerInfoPojo) {
        this.userCustomerInfoPojo = userCustomerInfoPojo;
    }
}
