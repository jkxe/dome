/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * @author zhangxianli
 */
public class CheckLoginPasswordResponse implements Serializable {

    private static final long serialVersionUID = 7796221061387603992L;

    /**冻结时间*/
    private Integer freezingTime;
    
    /**剩余尝试次数*/
    private Integer surplusTrialTimes;

    /**
     * 总尝试次数
     */
    private Integer totalTrialTimes;

    public Integer getTotalTrialTimes() {
        return totalTrialTimes;
    }

    public void setTotalTrialTimes(Integer totalTrialTimes) {
        this.totalTrialTimes = totalTrialTimes;
    }

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
