package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserCertificateInfoPojo;
import com.hsjry.user.facade.pojo.UserCustomeRelationInfoPojo;
import com.hsjry.user.facade.pojo.UserManageInfoPojo;
import com.hsjry.user.facade.pojo.UserOrganBasicInfoPojo;

public class QueryMerchantInfoResponse implements Serializable {

    /**  */
    private static final long                 serialVersionUID = 1918745060519924557L;
    /**  */
    // 客户ID
    private String                            userId;
    // 用户名
    private String                            userName;
    // 客户角色ID
    private String                            custRoleId;
    // 经营信息
    private UserManageInfoPojo                userManageInfoPojo;
    //法人客户ID
    private String                            relationUserId;
    //法人姓名
    private String                            relationUserName;
    //法人证件类型
    private EnumCertificateKind               relationIdKind;
    //法人证件号码
    private String                            relationIdNo;
    //法人电话号码
    private String                            telephone;
    // 授权人客户id
    private String                            liableUserId;
    // 授权人姓名
    private String                            authorizedName;
    // 授权人电话
    private String                            authorizedTel;
    // 证件资源项信息
    private List<UserCertificateInfoPojo>     userCertificateInfoPojoList;
    // 机构基本项信息
    private UserOrganBasicInfoPojo            userOrganBasicInfoPojo;
    //客户关系信息列表
    private UserCustomeRelationInfoPojo       userCustomeRelationPojo;
    // 地址联系点
    private UserAddressContactStationInfoPojo userAddressContactStationInfoPojo;

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
     * @param userId
     *            value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUsertName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName
     *            value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoled</tt>.
     * 
     * @param custRoled
     *            value to be assigned to property custRoled
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
    }

    /**
     * Getter method for property <tt>userManageInfoPojo</tt>.
     * 
     * @return property value of userManageInfoPojo
     */

    public UserManageInfoPojo getUserManageInfoPojo() {
        return userManageInfoPojo;
    }

    /**
     * Setter method for property <tt>userManageInfoPojo</tt>.
     * 
     * @param userManageInfoPojo
     *            value to be assigned to property userManageInfoPojo
     */
    public void setUserManageInfoPojo(UserManageInfoPojo userManageInfoPojo) {
        this.userManageInfoPojo = userManageInfoPojo;
    }

    /**
     * Getter method for property <tt>userCertificateInfoPojoList</tt>.
     * 
     * @return property value of userCertificateInfoPojoList
     */
    public List<UserCertificateInfoPojo> getUserCertificateInfoPojoList() {
        return userCertificateInfoPojoList;
    }

    /**
     * Setter method for property <tt>userCertificateInfoPojoList</tt>.
     * 
     * @param userCertificateInfoPojoList
     *            value to be assigned to property userCertificateInfoPojoList
     */
    public void setUserCertificateInfoPojoList(List<UserCertificateInfoPojo> userCertificateInfoPojoList) {
        this.userCertificateInfoPojoList = userCertificateInfoPojoList;
    }

    /**
     * Getter method for property <tt>userOrganBasicInfoPojo</tt>.
     * 
     * @return property value of userOrganBasicInfoPojo
     */
    public UserOrganBasicInfoPojo getUserOrganBasicInfoPojo() {
        return userOrganBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>userOrganBasicInfoPojo</tt>.
     * 
     * @param userOrganBasicInfoPojo
     *            value to be assigned to property userOrganBasicInfoPojo
     */
    public void setUserOrganBasicInfoPojo(UserOrganBasicInfoPojo userOrganBasicInfoPojo) {
        this.userOrganBasicInfoPojo = userOrganBasicInfoPojo;
    }

    /**
     * Getter method for property <tt>relationUserId</tt>.
     * 
     * @return property value of relationUserId
     */
    public String getRelationUserId() {
        return relationUserId;
    }

    /**
     * Setter method for property <tt>relationUserId</tt>.
     * 
     * @param relationUserId
     *            value to be assigned to property relationUserId
     */
    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    /**
     * Getter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojo
     */
    public UserAddressContactStationInfoPojo getUserAddressContactStationInfoPojo() {
        return userAddressContactStationInfoPojo;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojo</tt>.
     * 
     * @param userAddressContactStationInfoPojo
     *            value to be assigned to property
     *            userAddressContactStationInfoPojo
     */
    public void setUserAddressContactStationInfoPojo(UserAddressContactStationInfoPojo userAddressContactStationInfoPojo) {
        this.userAddressContactStationInfoPojo = userAddressContactStationInfoPojo;
    }

    /**
     * Getter method for property <tt>authorizedName</tt>.
     * 
     * @return property value of authorizedName
     */
    public String getAuthorizedName() {
        return authorizedName;
    }

    /**
     * Setter method for property <tt>authorizedName</tt>.
     * 
     * @param authorizedName
     *            value to be assigned to property authorizedName
     */
    public void setAuthorizedName(String authorizedName) {
        this.authorizedName = authorizedName;
    }

    /**
     * Getter method for property <tt>authorizedTel</tt>.
     * 
     * @return property value of authorizedTel
     */
    public String getAuthorizedTel() {
        return authorizedTel;
    }

    /**
     * Setter method for property <tt>authorizedTel</tt>.
     * 
     * @param authorizedTel
     *            value to be assigned to property authorizedTel
     */
    public void setAuthorizedTel(String authorizedTel) {
        this.authorizedTel = authorizedTel;
    }

    public UserCustomeRelationInfoPojo getUserCustomeRelationPojo() {
        return userCustomeRelationPojo;
    }

    public void setUserCustomeRelationPojo(UserCustomeRelationInfoPojo userCustomeRelationPojo) {
        this.userCustomeRelationPojo = userCustomeRelationPojo;
    }

    public String getRelationUserName() {
        return relationUserName;
    }

    public void setRelationUserName(String relationUserName) {
        this.relationUserName = relationUserName;
    }

    public EnumCertificateKind getRelationIdKind() {
        return relationIdKind;
    }

    public void setRelationIdKind(EnumCertificateKind relationIdKind) {
        this.relationIdKind = relationIdKind;
    }

    public String getRelationIdNo() {
        return relationIdNo;
    }

    public void setRelationIdNo(String relationIdNo) {
        this.relationIdNo = relationIdNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>liableUserId</tt>.
     * 
     * @return property value of liableUserId
     */
    public String getLiableUserId() {
        return liableUserId;
    }

    /**
     * Setter method for property <tt>liableUserId</tt>.
     * 
     * @param liableUserId value to be assigned to property liableUserId
     */
    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
    }

}
