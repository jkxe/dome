package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class UserNameCheckRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -9163465631020472564L;
    //用户名
    @NotNull(errorCode = "000001", message = "用户名")
    @NotBlank(errorCode = "000001", message = "用户名")
    private String            userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
