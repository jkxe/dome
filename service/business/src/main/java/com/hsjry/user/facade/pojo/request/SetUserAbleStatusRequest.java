package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 基础信息维护请求
 * 
 * @author jiangjd12837
 * @version $Id: UserAbleStatusSetRequest.java, v 1.0 2017年3月13日 下午4:54:47 jiangjd12837 Exp $
 */
public class SetUserAbleStatusRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 4427239061539765585L;
    /**客户ID*/
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String            userId;
    /**记录状态*/
    @NotNull(errorCode = "000001", message = "记录状态")
    private EnumObjectStatus  recordStatus;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>recordStatus</tt>.
     * 
     * @return property value of recordStatus
     */
    public EnumObjectStatus getRecordStatus() {
        return recordStatus;
    }

    /**
     * Setter method for property <tt>recordStatus</tt>.
     * 
     * @param recordStatus value to be assigned to property recordStatus
     */
    public void setRecordStatus(EnumObjectStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

}
