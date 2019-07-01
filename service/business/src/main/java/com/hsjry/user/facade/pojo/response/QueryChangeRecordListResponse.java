/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.ChangeRecordPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryChangeRecordListResponse.java, v 1.0 2018年5月17日 下午7:47:36 zhengqy15963 Exp $
 */
public class QueryChangeRecordListResponse implements Serializable {

    /**  */
    private static final long      serialVersionUID = 7997734125827755936L;
    /**变更记录列表  */
    private List<ChangeRecordPojo> changeRecordPojos;

    /**
     * Getter method for property <tt>changeRecordPojos</tt>.
     * 
     * @return property value of changeRecordPojos
     */
    public List<ChangeRecordPojo> getChangeRecordPojos() {
        return changeRecordPojos;
    }

    /**
     * Setter method for property <tt>changeRecordPojos</tt>.
     * 
     * @param changeRecordPojos value to be assigned to property changeRecordPojos
     */
    public void setChangeRecordPojos(List<ChangeRecordPojo> changeRecordPojos) {
        this.changeRecordPojos = changeRecordPojos;
    }

}
