package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @Description:根据authId冻结
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年08月07日  16:15
 */
public class FreezeByAuthIdRequest implements Serializable{

    private static final long serialVersionUID = -6902275302552778381L;

    @NotNull(errorCode = "000001", message = "通行证ID")
    @NotBlank(errorCode = "000001", message = "通行证ID")
    private String authId;

    @NotNull(errorCode = "000001", message = "识别用途")
    private EnumPurposeCode purposeCode;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public EnumPurposeCode getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(EnumPurposeCode purposeCode) {
        this.purposeCode = purposeCode;
    }
}
