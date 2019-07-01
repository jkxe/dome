package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * 修改已绑定手机号请求报文
 *
 * @author jiangjd12837
 * @version $Id: ModifyBindingTelephoneRequest.java, v 1.0 2017年3月13日 下午4:54:05 jiangjd12837 Exp $
 */
public class ModifyBindingTelephoneRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -1287297826052805959L;
    /**
     * 客户ID
     */
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String userId;
    /**
     * 原手机号
     */
    @NotNull(errorCode = "000001", message = "原手机号")
    @NotBlank(errorCode = "000001", message = "原手机号")
    @MatchPattern(matchAll = false, pattern = {"^0?(13|15|16|19|18|14|17)[0-9]{9}$"}, errorCode = "000003", message = "原手机号")
    private String oldMobileTel;
    /**
     * 新手机号
     */
    @NotNull(errorCode = "000001", message = "新手机号")
    @NotBlank(errorCode = "000001", message = "新手机号")
    @MatchPattern(matchAll = false, pattern = {"^0?(13|15|16|19|18|14|17)[0-9]{9}$"}, errorCode = "000003", message = "新手机号")
    private String newMobileTel;
    /**
     * 校验码
     */
    @NotNull(errorCode = "000001", message = "校验码")
    @NotBlank(errorCode = "000001", message = "校验码")
    @Length(min = 6, max = 6, errorCode = "000002", message = "校验码")
    @MatchPattern(matchAll = false, pattern = {"^\\d{6}$"}, errorCode = "000003", message = "校验码")
    private String authCheckCode;

    /**
     * 原手机校验码
     */
    @Length(min = 6, max = 6, errorCode = "000002", message = "校验码")
    @MatchPattern(matchAll = false, pattern = {"^\\d{6}$"}, errorCode = "000003", message = "校验码")
    private String oldAuthCheckCode;

    public String getOldAuthCheckCode() {
        return oldAuthCheckCode;
    }

    public void setOldAuthCheckCode(String oldAuthCheckCode) {
        this.oldAuthCheckCode = oldAuthCheckCode;
    }

    public String getOldMobileTel() {
        return oldMobileTel;
    }

    public void setOldMobileTel(String oldMobileTel) {
        this.oldMobileTel = oldMobileTel;
    }

    public String getNewMobileTel() {
        return newMobileTel;
    }

    public void setNewMobileTel(String newMobileTel) {
        this.newMobileTel = newMobileTel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>authCheckCode</tt>.
     *
     * @return property value of authCheckCode
     */
    public String getAuthCheckCode() {
        return authCheckCode;
    }

    /**
     * Setter method for property <tt>authCheckCode</tt>.
     *
     * @param authCheckCode value to be assigned to property authCheckCode
     */
    public void setAuthCheckCode(String authCheckCode) {
        this.authCheckCode = authCheckCode;
    }

}
