package com.hsjry.user.facade.pojo;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import java.io.Serializable;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description:
 * @date 2017年07月28日  19:59
 */
public class QueryUserSubordinatePagePojo implements Serializable {

    private static final long serialVersionUID = -4805889758447865203L;
    //客户ID
    private String userId;
    //客户名称
    private String clientName;
    //客户账号
    private String clientAccount;
    //手机号
    private String mobileTel;
    //用户状态
    private EnumObjectStatus idStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public EnumObjectStatus getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(EnumObjectStatus idStatus) {
        this.idStatus = idStatus;
    }
}
