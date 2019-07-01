package com.hsjry.user.facade.pojo.request;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.UserBankCardInfoPojo;
import lombok.Data;
import net.sf.oval.constraint.*;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2018/11/1
 */
@Data
public class BindCardParam implements Serializable {
    private static final long serialVersionUID = 1309858934853861076L;

    /**
     * 预留手机号
     */
    @NotNull(errorCode = "000001", message = "预留手机号")
    @MatchPattern(matchAll = false, pattern = {"^0?(13|15|16|19|18|14|17)[0-9]{9}$"}, errorCode = "000003", message = "预留手机号")
    @NotBlank(errorCode = "000001", message = "预留手机号")
    private String telephone;

    /**
     * 校验码, 绑卡需要校验
     */

    private String authCheckCode;

    /**
     * 证件类型
     */
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind certificateKind;

    /**
     * 证件号码
     */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String certificateNo;

    /**
     * 真实姓名
     */
    @NotNull(errorCode = "000001", message = "真实姓名")
    @NotBlank(errorCode = "000001", message = "真实姓名")
    private String realName;

    /**
     * 用户id
     */
    @NotNull(errorCode = "000001", message = "用户id")
    @NotBlank(errorCode = "000001", message = "用户id")
    private String userId;

    @AssertValid(errorCode = "000001", message = "银行卡信息")
    private UserBankCardInfoPojo userBankCardInfoPojo;
}
