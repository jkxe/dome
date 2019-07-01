package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumUserType;
import com.hsjry.user.facade.pojo.enums.EnumClientRelation;
import com.hsjry.user.facade.pojo.enums.EnumGender;
import com.hsjry.user.facade.pojo.modify.UserCertificateInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserCustomerRelationExtendModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserProfessionalInfoModifyPojo;
import com.hsjry.user.facade.pojo.modify.UserTelContactStationInfoModifyPojo;

public class ModifyRelationPojo implements Serializable {

    /**  */
    private static final long                          serialVersionUID = -7325616968988806999L;
    //关系代码
    private EnumClientRelation                         clientRelation;
    //客户关系扩展信息
    private List<UserCustomerRelationExtendModifyPojo> userCustomerRelationExtendPojoList;
    //关系人姓名
    private String                                     clientName;
    //性别
    private EnumGender                                 sex;
    //证件资源项信息
    private UserCertificateInfoModifyPojo              userCertificateInfoPojo;
    //关系人客户ID
    @NotNull(errorCode = "000001", message = "关系人客户ID")
    @NotBlank(errorCode = "000001", message = "关系人客户ID")
    private String                                     relationId;
    //客户类型
    private EnumUserType                               userType;
    //电话联系点
    private UserTelContactStationInfoModifyPojo        userTelContactStationInfoPojo;
    //职业资源项
    private UserProfessionalInfoModifyPojo             UserProfessionalInfoPojo;

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
     * Getter method for property <tt>userCustomerRelationExtendPojoList</tt>.
     * 
     * @return property value of userCustomerRelationExtendPojoList
     */
    public List<UserCustomerRelationExtendModifyPojo> getUserCustomerRelationExtendPojoList() {
        return userCustomerRelationExtendPojoList;
    }

    /**
     * Setter method for property <tt>userCustomerRelationExtendPojoList</tt>.
     * 
     * @param userCustomerRelationExtendPojoList value to be assigned to property userCustomerRelationExtendPojoList
     */
    public void setUserCustomerRelationExtendPojoList(List<UserCustomerRelationExtendModifyPojo> userCustomerRelationExtendPojoList) {
        this.userCustomerRelationExtendPojoList = userCustomerRelationExtendPojoList;
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
     * Getter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @return property value of userCertificateInfoPojo
     */
    public UserCertificateInfoModifyPojo getUserCertificateInfoPojo() {
        return userCertificateInfoPojo;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojo</tt>.
     * 
     * @param userCertificateInfoPojo value to be assigned to property userCertificateInfoPojo
     */
    public void setUserCertificateInfoPojo(UserCertificateInfoModifyPojo userCertificateInfoPojo) {
        this.userCertificateInfoPojo = userCertificateInfoPojo;
    }

    /**
     * Getter method for property <tt>relationId</tt>.
     * 
     * @return property value of relationId
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * Setter method for property <tt>relationId</tt>.
     * 
     * @param relationId value to be assigned to property relationId
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * Getter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @return property value of userTelContactStationInfoPojo
     */
    public UserTelContactStationInfoModifyPojo getUserTelContactStationInfoPojo() {
        return userTelContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userTelContactStationInfoPojo</tt>.
     * 
     * @param userTelContactStationInfoPojo value to be assigned to property userTelContactStationInfoPojo
     */
    public void setUserTelContactStationInfoPojo(UserTelContactStationInfoModifyPojo userTelContactStationInfoPojo) {
        this.userTelContactStationInfoPojo = userTelContactStationInfoPojo;
    }

    /**
     * Getter method for property <tt>userProfessionalInfoPojo</tt>.
     * 
     * @return property value of UserProfessionalInfoPojo
     */
    public UserProfessionalInfoModifyPojo getUserProfessionalInfoPojo() {
        return UserProfessionalInfoPojo;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoPojo</tt>.
     * 
     * @param UserProfessionalInfoPojo value to be assigned to property userProfessionalInfoPojo
     */
    public void setUserProfessionalInfoPojo(UserProfessionalInfoModifyPojo userProfessionalInfoPojo) {
        UserProfessionalInfoPojo = userProfessionalInfoPojo;
    }

}
