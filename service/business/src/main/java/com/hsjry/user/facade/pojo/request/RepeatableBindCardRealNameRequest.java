package com.hsjry.user.facade.pojo.request;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.UserBankCardInfoPojo;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @Description:
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年08月09日  14:01
 */
public class RepeatableBindCardRealNameRequest implements Serializable{

    private static final long serialVersionUID = -5376663525736903419L;


    /**预留手机号*/
    @NotNull(errorCode = "000001", message = "预留手机号")
    @NotBlank(errorCode = "000001", message = "预留手机号")
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="预留手机号")
    private String telephone;

    /**校验码*/
    @NotNull(errorCode = "000001", message = "校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    @Length(min=6, max = 6,errorCode="000002",message="校验码")
    @MatchPattern(matchAll=false,pattern={"^\\d{6}$"},errorCode="000003",message="校验码")
    private String authCheckCode;

    /**银行卡信息*/
    @AssertValid(errorCode = "000001", message = "银行卡信息")
    private UserBankCardInfoPojo userBankCardInfoPojo;

    /**证件类型*/
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind certificateKind;

    /**证件号码*/
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              certificateNo;

    /**真实姓名*/
    @NotNull(errorCode = "000001", message = "真实姓名")
    @NotBlank(errorCode = "000001", message = "真实姓名")
    private String realName;


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAuthCheckCode() {
        return authCheckCode;
    }

    public void setAuthCheckCode(String authCheckCode) {
        this.authCheckCode = authCheckCode;
    }

    public UserBankCardInfoPojo getUserBankCardInfoPojo() {
        return userBankCardInfoPojo;
    }

    public void setUserBankCardInfoPojo(UserBankCardInfoPojo userBankCardInfoPojo) {
        this.userBankCardInfoPojo = userBankCardInfoPojo;
    }

    public EnumCertificateKind getCertificateKind() {
        return certificateKind;
    }

    public void setCertificateKind(EnumCertificateKind certificateKind) {
        this.certificateKind = certificateKind;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
