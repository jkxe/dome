/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

/**
 * 撤销特殊名单请求类
 * @author hongsj
 * @version $Id: CancelSpecialListRequest.java, v 1.0 2017年3月28日 上午10:17:46 hongsj Exp $
 */
public class CancelSpecialListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -477906338750677721L;
    /**名单Id列表*/
    @NotNull(errorCode = "000001", message = "名单Id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "名单Id列表")
    private List<String>      listIdList;
    /**撤销操作人Id*/
    @NotNull(errorCode = "000001", message = "撤销操作人Id")
    @NotBlank(errorCode = "000001", message = "撤销操作人Id")
    private String            cancelUserId;
    /**撤销理由*/
    private String            cancelReason;

    /**
     * Getter method for property <tt>listIdList</tt>.
     * 
     * @return property value of listIdList
     */
    public List<String> getListIdList() {
        return listIdList;
    }

    /**
     * Setter method for property <tt>listIdList</tt>.
     * 
     * @param listIdList value to be assigned to property listIdList
     */
    public void setListIdList(List<String> listIdList) {
        this.listIdList = listIdList;
    }

    /**
     * Getter method for property <tt>cancelUserId</tt>.
     * 
     * @return property value of cancelUserId
     */
    public String getCancelUserId() {
        return cancelUserId;
    }

    /**
     * Setter method for property <tt>cancelUserId</tt>.
     * 
     * @param cancelUserId value to be assigned to property cancelUserId
     */
    public void setCancelUserId(String cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    /**
     * Getter method for property <tt>cancelReason</tt>.
     * 
     * @return property value of cancelReason
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * Setter method for property <tt>cancelReason</tt>.
     * 
     * @param cancelReason value to be assigned to property cancelReason
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

}
