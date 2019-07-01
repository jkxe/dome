package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumKqIdKind;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description:
 * @date 2017年07月19日  21:23
 */
public class ConvertAccountVerifyPojo implements Serializable {

    private static final long serialVersionUID = 2725531843737319992L;

    @NotNull(errorCode = "000001", message = "客户姓名")
    @NotBlank(errorCode = "000001", message = "客户姓名")
    private String clientName;

    @NotNull(errorCode = "000001", message = "银行卡号")
    @NotBlank(errorCode = "000001", message = "银行卡号")
    private String cardNo;

    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String idNo;

    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumKqIdKind idKind;

    @NotNull(errorCode = "000001", message = "手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    private String mobileTel;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public EnumKqIdKind getIdKind() {
        return idKind;
    }

    public void setIdKind(EnumKqIdKind idKind) {
        this.idKind = idKind;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }
}
