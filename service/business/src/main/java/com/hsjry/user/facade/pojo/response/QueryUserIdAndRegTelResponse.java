package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * QueryUserIdAndRegTelResponse class
 *
 * @author zhuyinjie
 * @date 2018/8/21
 */
public class QueryUserIdAndRegTelResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -4598704118392892661L;
    //客户ID
    private String            userId;
    //注册手机号
    private String            telephone;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
