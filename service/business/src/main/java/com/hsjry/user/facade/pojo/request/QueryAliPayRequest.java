package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * 
 * @author jingqi17258
 * @version $Id: QueryAliPayRequest.java, v 1.0 2017年6月18日 下午5:20:21 jingqi17258 Exp $
 */
public class QueryAliPayRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -3637468154853664457L;
    /**用户编号*/
    @NotNull(errorCode = "000001", message = "用户编号")
    @NotBlank(errorCode = "000001", message = "用户编号")
    private String            userId;

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

}
