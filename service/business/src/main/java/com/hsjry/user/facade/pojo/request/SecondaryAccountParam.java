package com.hsjry.user.facade.pojo.request;

import lombok.Data;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2018/11/1
 */
@Data
public class SecondaryAccountParam implements Serializable{

    private static final long serialVersionUID = -8969083934014610175L;

    /**
     * 身份证号
     */
    @NotNull(errorCode = "000001", message = "身份证号")
    @NotBlank(errorCode = "000001", message = "身份证号")
    private String certificateNo;
    /**
     * 用户姓名
     */
    @NotNull(errorCode = "000001", message = "真实姓名")
    @NotBlank(errorCode = "000001", message = "真实姓名")
    private String userName;
    /**
     * 卡号
     */
    @NotBlank(errorCode = "000001", message = "银行卡号")
    @NotNull(errorCode = "000001", message = "银行卡号")
    private String cardNo;
    /**
     * 手机号码
     */
    @NotNull(errorCode = "000001", message = "手机号")
    @MatchPattern(matchAll = false, pattern = {"^0?(13|15|16|19|18|14|17)[0-9]{9}$"}, errorCode = "000003", message = "预留手机号")
    @NotBlank(errorCode = "000001", message = "手机号")
    private String mobile;

}
