/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 修改密码请求返回
 * @author huangbb
 * @version $Id: UpdatePasswordResponse.java, v 1.0 2017年5月17日 上午11:17:19 huangbb Exp $
 */
public class UpdatePasswordResponse implements Serializable {

    
    /**  */
    private static final long serialVersionUID = -8752978721933335135L;

    /**冻结时间*/
    private Integer freezingTime;
    
    /**剩余尝试次数*/
    private Integer surplusTrialTimes;

    /**
     * Getter method for property <tt>freezingTime</tt>.
     * 
     * @return property value of freezingTime
     */
    public Integer getFreezingTime() {
        return freezingTime;
    }

    /**
     * Setter method for property <tt>freezingTime</tt>.
     * 
     * @param freezingTime value to be assigned to property freezingTime
     */
    public void setFreezingTime(Integer freezingTime) {
        this.freezingTime = freezingTime;
    }

    /**
     * Getter method for property <tt>surplusTrialTimes</tt>.
     * 
     * @return property value of surplusTrialTimes
     */
    public Integer getSurplusTrialTimes() {
        return surplusTrialTimes;
    }

    /**
     * Setter method for property <tt>surplusTrialTimes</tt>.
     * 
     * @param surplusTrialTimes value to be assigned to property surplusTrialTimes
     */
    public void setSurplusTrialTimes(Integer surplusTrialTimes) {
        this.surplusTrialTimes = surplusTrialTimes;
    }
    
    
}
