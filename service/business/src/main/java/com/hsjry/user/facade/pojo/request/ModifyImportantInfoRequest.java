/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ModifyImportantInfoRequest.java, v 1.0 2017年8月29日 下午1:40:18 jiangjd12837 Exp $
 */
public class ModifyImportantInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 6335034198956914968L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String            userId;
    /**客户姓名*/
    private String            clientName;

    /**手机号*/
    @MatchPattern(matchAll = false, pattern = { "(^0?(13|15|16|19|18|14|17)[0-9]{9}$)|(^400[0-9]{7}$)" }, errorCode = "000003", message = "手机号")
    private String            telephone;

    /**登录账号*/
    private String            identifiers;

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

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
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>identifiers</tt>.
     * 
     * @return property value of identifiers
     */
    public String getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter method for property <tt>identifiers</tt>.
     * 
     * @param identifiers value to be assigned to property identifiers
     */
    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

}
