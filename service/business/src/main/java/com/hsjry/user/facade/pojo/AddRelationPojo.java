/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.lang.common.base.enums.user.EnumUserType;
import com.hsjry.user.facade.pojo.enums.EnumClientRelation;
import com.hsjry.user.facade.pojo.enums.EnumGender;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: AddRelationPojo.java, v 1.0 2017年4月20日 下午2:53:59 jiangjd12837 Exp $
 */
public class AddRelationPojo implements Serializable {

    /**  */
    private static final long                    serialVersionUID = 3297421481255074581L;
    //客户类型
    private EnumUserType                         userType;
    //关系类型
    @NotNull(errorCode = "000001", message = "关系类型")
    private EnumClientRelation                   clientRelation;
    //关系人姓名
    private String                               clientName;
    //性别
    private EnumGender                           sex;
    //关系人证件类型
    private EnumCertificateKind                  certificateKind;
    //关系人证件号码
    private String                               certificateNo;
    //关系人联系方式
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String                               telephone;
    //关系人扩展信息
    private List<UserCustomerRelationExtendPojo> customerRelationExtendPojoList;
    //职业资源项
    private UserProfessionalInfoPojo             UserProfessionalInfoPojo;

    /**
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public EnumGender getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(EnumGender sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>userType</tt>.
     * 
     * @return property value of userType
     */
    public EnumUserType getUserType() {
        return userType;
    }

    /**
     * Setter method for property <tt>userType</tt>.
     * 
     * @param userType value to be assigned to property userType
     */
    public void setUserType(EnumUserType userType) {
        this.userType = userType;
    }

    /**
     * Getter method for property <tt>clientRelation</tt>.
     * 
     * @return property value of clientRelation
     */
    public EnumClientRelation getClientRelation() {
        return clientRelation;
    }

    /**
     * Setter method for property <tt>clientRelation</tt>.
     * 
     * @param clientRelation value to be assigned to property clientRelation
     */
    public void setClientRelation(EnumClientRelation clientRelation) {
        this.clientRelation = clientRelation;
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
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>customerRelationExtendPojoList</tt>.
     * 
     * @return property value of customerRelationExtendPojoList
     */
    public List<UserCustomerRelationExtendPojo> getCustomerRelationExtendPojoList() {
        return customerRelationExtendPojoList;
    }

    /**
     * Setter method for property <tt>customerRelationExtendPojoList</tt>.
     * 
     * @param customerRelationExtendPojoList value to be assigned to property customerRelationExtendPojoList
     */
    public void setCustomerRelationExtendPojoList(List<UserCustomerRelationExtendPojo> customerRelationExtendPojoList) {
        this.customerRelationExtendPojoList = customerRelationExtendPojoList;
    }

    /**
     * Getter method for property <tt>userProfessionalInfoPojo</tt>.
     * 
     * @return property value of UserProfessionalInfoPojo
     */
    public UserProfessionalInfoPojo getUserProfessionalInfoPojo() {
        return UserProfessionalInfoPojo;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoPojo</tt>.
     * 
     * @param UserProfessionalInfoPojo value to be assigned to property userProfessionalInfoPojo
     */
    public void setUserProfessionalInfoPojo(UserProfessionalInfoPojo userProfessionalInfoPojo) {
        UserProfessionalInfoPojo = userProfessionalInfoPojo;
    }

}
