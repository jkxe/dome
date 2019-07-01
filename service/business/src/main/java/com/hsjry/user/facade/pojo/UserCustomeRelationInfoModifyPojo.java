package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumClientRelation;
import com.hsjry.user.facade.pojo.enums.EnumRelationType;

public class UserCustomeRelationInfoModifyPojo implements Serializable {

    /**  */
    private static final long                    serialVersionUID = -3804513126948859555L;
    //客户ID
    private String                               userId;
    //关系类型
    private EnumRelationType                     relationType;
    //客户关系扩展信息
    private List<UserCustomerRelationExtendPojo> customerRelationExtendPojoList;
    //关系人姓名
    private String                               clientName;
    //关系人证件类型
    private EnumCertificateKind                  certificateKind;
    //关系人证件号码
    private String                               certificateNo;
    //关系人联系方式
    private String                               telephone;
    //关系代码
    private EnumClientRelation                   clientRelation;

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
     * Getter method for property <tt>relationType</tt>.
     * 
     * @return property value of relationType
     */
    public EnumRelationType getRelationType() {
        return relationType;
    }

    /**
     * Setter method for property <tt>relationType</tt>.
     * 
     * @param relationType value to be assigned to property relationType
     */
    public void setRelationType(EnumRelationType relationType) {
        this.relationType = relationType;
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
}
