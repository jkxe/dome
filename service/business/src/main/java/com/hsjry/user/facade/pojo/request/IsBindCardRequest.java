/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author zhengqy15963
 * @version $Id: IsBindCardRequest.java, v 1.0 2018年4月17日 上午10:23:50 zhengqy15963 Exp $
 */
public class IsBindCardRequest implements Serializable {

    private static final long serialVersionUID = 8399766030955884314L;
    @NotNull(errorCode = "000001", message = "银行卡号 ")
    @NotBlank(errorCode = "000001", message = "银行卡号 ")
    /**银行卡号  */
    private String            cardNo;

    /**
     * Getter method for property <tt>cardNo</tt>.
     * 
     * @return property value of cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Setter method for property <tt>cardNo</tt>.
     * 
     * @param cardNo value to be assigned to property cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

}
