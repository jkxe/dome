package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author zhangxianli
 */
public class CheckLoginPasswordRequest implements Serializable {


    private static final long serialVersionUID = 8387131191136930604L;

    /**
     * 登录密码
     */
    @NotNull(errorCode = "000001", message = "登录密码")
    @NotBlank(errorCode = "000001", message = "登录密码")
    private String loginPassword;

    /**识别来源 给定登录信息如ip、机器型号等 json数据 key参考源数据*/
    @NotNull(errorCode = "000001", message = "识别来源")
    @NotBlank(errorCode = "000001", message = "识别来源")
    private String identifySource;

    public String getIdentifySource() {
        return identifySource;
    }

    public void setIdentifySource(String identifySource) {
        this.identifySource = identifySource;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
