/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ExternInfoPojo.java, v 1.0 2017年5月16日 下午2:43:16 jiangjd12837 Exp $
 */
public class ExternInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 5453036560572161091L;
    //扩展域
    private String            creditNo;

    //协议号
    private String            contractNo;

    //缴费编号
    private String            portno;

    /**
     * Getter method for property <tt>creditNo</tt>.
     * 
     * @return property value of creditNo
     */
    public String getCreditNo() {
        return creditNo;
    }

    /**
     * Setter method for property <tt>creditNo</tt>.
     * 
     * @param creditNo value to be assigned to property creditNo
     */
    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPortno() {
        return portno;
    }

    public void setPortno(String portno) {
        this.portno = portno;
    }
}
