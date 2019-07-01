/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.Date;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * 人脸识别信息请求
 * @author huangbb
 * @version $Id: AddFaceAuthInfoRequest.java, v 0.1 2017年10月25日 下午5:42:20 huangbb Exp $
 */
public class AddFaceAuthInfoRequest implements Serializable {

    private static final long serialVersionUID = 8555174787600993315L;

    @NotNull(errorCode="000001",message="客户ID")
    @NotBlank(errorCode="000001",message="客户ID")
    private String userId;

    @NotNull(errorCode="000001",message="客户姓名")
    @NotBlank(errorCode="000001",message="客户姓名")
    private String clientName;

    @NotNull(errorCode="000001",message="证件号码")
    @NotBlank(errorCode="000001",message="证件号码")
    private String idNo;
    
    @NotNull(errorCode="000001",message="证件类型")
    @NotBlank(errorCode="000001",message="证件类型")
    private EnumCertificateKind idKind;

    @NotNull(errorCode="000001",message="是否通过")
    @NotBlank(errorCode="000001",message="是否通过")
    private EnumBool resultType;

    /**识别结果内容*/
    private String resultContent;

    @NotNull(errorCode="000001",message="申请验证日期")
    private Date applyDate;

    /**其他信息*/
    private String otherInfo;
    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * Getter method for property <tt>idNo</tt>.
     * 
     * @return property value of idNo
     */
    public String getIdNo() {
        return idNo;
    }
    /**
     * Setter method for property <tt>idNo</tt>.
     * 
     * @param idNo value to be assigned to property idNo
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    /**
     * Getter method for property <tt>idKind</tt>.
     * 
     * @return property value of idKind
     */
    public EnumCertificateKind getIdKind() {
        return idKind;
    }
    /**
     * Setter method for property <tt>idKind</tt>.
     * 
     * @param idKind value to be assigned to property idKind
     */
    public void setIdKind(EnumCertificateKind idKind) {
        this.idKind = idKind;
    }
    /**
     * Getter method for property <tt>resultType</tt>.
     * 
     * @return property value of resultType
     */
    public EnumBool getResultType() {
        return resultType;
    }
    /**
     * Setter method for property <tt>resultType</tt>.
     * 
     * @param resultType value to be assigned to property resultType
     */
    public void setResultType(EnumBool resultType) {
        this.resultType = resultType;
    }
    /**
     * Getter method for property <tt>resultContent</tt>.
     * 
     * @return property value of resultContent
     */
    public String getResultContent() {
        return resultContent;
    }
    /**
     * Setter method for property <tt>resultContent</tt>.
     * 
     * @param resultContent value to be assigned to property resultContent
     */
    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }
    /**
     * Getter method for property <tt>applyDate</tt>.
     * 
     * @return property value of applyDate
     */
    public Date getApplyDate() {
        return applyDate;
    }
    /**
     * Setter method for property <tt>applyDate</tt>.
     * 
     * @param applyDate value to be assigned to property applyDate
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    /**
     * Getter method for property <tt>otherInfo</tt>.
     * 
     * @return property value of otherInfo
     */
    public String getOtherInfo() {
        return otherInfo;
    }
    /**
     * Setter method for property <tt>otherInfo</tt>.
     * 
     * @param otherInfo value to be assigned to property otherInfo
     */
    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
    
    
}
