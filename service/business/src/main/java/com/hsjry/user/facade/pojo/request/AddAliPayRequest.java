package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.AliPayInfoPojo;

import java.io.Serializable;

/**
 * Created by jingqi17258 on 2017/6/19.
 */
public class AddAliPayRequest implements Serializable {

    private static final long serialVersionUID = -7865764121371076486L;
    /**对象*/
    private AliPayInfoPojo pojo;

    public AliPayInfoPojo getPojo() {
        return pojo;
    }

    public void setPojo(AliPayInfoPojo pojo) {
        this.pojo = pojo;
    }
}
