/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.ConvertAccountVerifyPojo;

import java.io.Serializable;


public class ConvertAccountVerifyRequest implements Serializable {

    private static final long serialVersionUID = -2895481657970065880L;

    private ConvertAccountVerifyPojo convertAccountVerifyPojo;

    public ConvertAccountVerifyPojo getConvertAccountVerifyPojo() {
        return convertAccountVerifyPojo;
    }

    public void setConvertAccountVerifyPojo(ConvertAccountVerifyPojo convertAccountVerifyPojo) {
        this.convertAccountVerifyPojo = convertAccountVerifyPojo;
    }
}
