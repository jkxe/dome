/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * 门店新增修改功能中的员工对象
 * @author zhengqy15963
 * @version $Id: StaffInfoPojo.java, v 1.0 2018年5月3日 下午4:56:42 zhengqy15963 Exp $
 */
public class StaffInfoPojo implements Serializable {

    /**  */
    private static final long   serialVersionUID = -2416252358162208573L;
    /**员工用户ID  */
    private String              staffUserId;
    /**员工姓名  */
    @NotNull(errorCode = "000001", message = "员工姓名")
    @NotBlank(errorCode = "000001", message = "员工姓名")
    private String              staffName;
    /**员工职务  */
    @NotNull(errorCode = "000001", message = "员工职务")
    @NotBlank(errorCode = "000001", message = "员工职务")
    private String              staffPost;
    /**员工联系方式  */
    @NotNull(errorCode = "000001", message = "员工联系方式")
    @NotBlank(errorCode = "000001", message = "员工联系方式")
    @MatchPattern(matchAll=false,pattern={"^0?(13|15|16|19|18|14|17)[0-9]{9}$"},errorCode="000003",message="员工联系方式")
    private String              staffTelephone;
    /**证件类型  */
    private EnumCertificateKind certificateKind;
    /**证件号码  */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              certificateNo;
    /**是否门店负责人  */
    @NotNull(errorCode = "000001", message = "门店负责人")
    private EnumBool            isLeader;

    /**
     * Getter method for property <tt>staffUserId</tt>.
     * 
     * @return property value of staffUserId
     */
    public String getStaffUserId() {
        return staffUserId;
    }

    /**
     * Setter method for property <tt>staffUserId</tt>.
     * 
     * @param staffUserId value to be assigned to property staffUserId
     */
    public void setStaffUserId(String staffUserId) {
        this.staffUserId = staffUserId;
    }

    /**
     * Getter method for property <tt>staffName</tt>.
     * 
     * @return property value of staffName
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Setter method for property <tt>staffName</tt>.
     * 
     * @param staffName value to be assigned to property staffName
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * Getter method for property <tt>staffPost</tt>.
     * 
     * @return property value of staffPost
     */
    public String getStaffPost() {
        return staffPost;
    }

    /**
     * Setter method for property <tt>staffPost</tt>.
     * 
     * @param staffPost value to be assigned to property staffPost
     */
    public void setStaffPost(String staffPost) {
        this.staffPost = staffPost;
    }

    /**
     * Getter method for property <tt>staffTelephone</tt>.
     * 
     * @return property value of staffTelephone
     */
    public String getStaffTelephone() {
        return staffTelephone;
    }

    /**
     * Setter method for property <tt>staffTelephone</tt>.
     * 
     * @param staffTelephone value to be assigned to property staffTelephone
     */
    public void setStaffTelephone(String staffTelephone) {
        this.staffTelephone = staffTelephone;
    }

    /**
     * Getter method for property <tt>certificateKind</tt>.
     * 
     * @return property value of certificateKind
     */
    public EnumCertificateKind getCertificateKind() {
        return certificateKind;
    }

    /**
     * Setter method for property <tt>certificateKind</tt>.
     * 
     * @param certificateKind value to be assigned to property certificateKind
     */
    public void setCertificateKind(EnumCertificateKind certificateKind) {
        this.certificateKind = certificateKind;
    }

    /**
     * Getter method for property <tt>certificateNo</tt>.
     * 
     * @return property value of certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * Setter method for property <tt>certificateNo</tt>.
     * 
     * @param certificateNo value to be assigned to property certificateNo
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * Getter method for property <tt>isLeader</tt>.
     * 
     * @return property value of isLeader
     */
    public EnumBool getIsLeader() {
        return isLeader;
    }

    /**
     * Setter method for property <tt>isLeader</tt>.
     * 
     * @param isLeader value to be assigned to property isLeader
     */
    public void setIsLeader(EnumBool isLeader) {
        this.isLeader = isLeader;
    }

}
