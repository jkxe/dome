package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author liaosq23298
 * @version $Id: QueryUserIdandRealNameStatusResponse.java, v 0.1 Dec 5, 2017 7:26:08 PM liaosq23298 Exp $
 */
public class QueryUserIdandRealNameFlagResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 3134527653382816570L;
    /**  */
    //客户ID
    private String            userId;
    //实名状态
    private EnumBool 	      realnameFlag;

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
     * Getter method for property <tt>realnameFlag</tt>.
     * 
     * @return property value of realnameFlag
     */
    public EnumBool getRealnameFlag() {
        return realnameFlag;
    }

    /**
     * Setter method for property <tt>realnameFlag</tt>.
     * 
     * @param userId value to be assigned to property realnameFlag
     */
    public void setRealnameFlag(EnumBool realnameFlag) {
        this.realnameFlag = realnameFlag;
    }

}

