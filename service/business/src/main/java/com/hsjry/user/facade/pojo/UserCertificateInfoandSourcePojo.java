package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumResourceSource;
import com.hsjry.user.facade.pojo.enums.EnumResourceStatus;

/**证件文档
 * 
 * @author liaosq23298
 * @version $Id: UserCertificateInfoandSourcePojo.java, v 0.1 Nov 22, 2017 4:23:34 PM liaosq23298 Exp $
 */
public class UserCertificateInfoandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = 1942008862326494094L;
    //资源项ID
    private String              resourceId;
    //登记ID
    private String              registerId;
    //名称
    private String              certificateName;
    //编号
    @NotNull(errorCode = "000001", message = "证件编号")
    @NotBlank(errorCode = "000001", message = "证件编号")
    private String              certificateNo;
    //证件项类型
    @NotNull(errorCode = "000001", message = "证件项类型")
    private EnumCertificateKind certificateKind;
    //发证日期
    private Date                publishDate;
    //结束有效日期
    private Date                invalidDate;
    //证件状态
    @NotNull(errorCode = "000001", message = "证件状态")
    private EnumResourceStatus  certificateStatus;
    //发证机构
    private String              issuanceOrg;
    //证件地址
    private String              certificateAddress;
    //年检日期
    private Date                certificateCheckValidDate;
    //资源来源
    private EnumResourceSource  resourceSource;
    /**
     * Getter method for property <tt>certificateStatus</tt>.
     * 
     * @return property value of certificateStatus
     */
    public EnumResourceStatus getCertificateStatus() {
        return certificateStatus;
    }

    /**
     * Setter method for property <tt>certificateStatus</tt>.
     * 
     * @param certificateStatus value to be assigned to property certificateStatus
     */
    public void setCertificateStatus(EnumResourceStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    /**
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * Getter method for property <tt>certificateName</tt>.
     * 
     * @return property value of certificateName
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * Setter method for property <tt>certificateName</tt>.
     * 
     * @param certificateName value to be assigned to property certificateName
     */
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
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
     * Getter method for property <tt>issuanceOrg</tt>.
     * 
     * @return property value of issuanceOrg
     */
    public String getIssuanceOrg() {
        return issuanceOrg;
    }

    /**
     * Setter method for property <tt>issuanceOrg</tt>.
     * 
     * @param issuanceOrg value to be assigned to property issuanceOrg
     */
    public void setIssuanceOrg(String issuanceOrg) {
        this.issuanceOrg = issuanceOrg;
    }

    /**
     * Getter method for property <tt>certificateAddress</tt>.
     * 
     * @return property value of certificateAddress
     */
    public String getCertificateAddress() {
        return certificateAddress;
    }

    /**
     * Setter method for property <tt>certificateAddress</tt>.
     * 
     * @param certificateAddress value to be assigned to property certificateAddress
     */
    public void setCertificateAddress(String certificateAddress) {
        this.certificateAddress = certificateAddress;
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
     * Getter method for property <tt>publishDate</tt>.
     * 
     * @return property value of publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Setter method for property <tt>publishDate</tt>.
     * 
     * @param publishDate value to be assigned to property publishDate
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Getter method for property <tt>invalidDate</tt>.
     * 
     * @return property value of invalidDate
     */
    public Date getInvalidDate() {
        return invalidDate;
    }

    /**
     * Setter method for property <tt>invalidDate</tt>.
     * 
     * @param invalidDate value to be assigned to property invalidDate
     */
    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    /**
     * Getter method for property <tt>certificateCheckValidDate</tt>.
     * 
     * @return property value of certificateCheckValidDate
     */
    public Date getCertificateCheckValidDate() {
        return certificateCheckValidDate;
    }

    /**
     * Setter method for property <tt>certificateCheckValidDate</tt>.
     * 
     * @param certificateCheckValidDate value to be assigned to property certificateCheckValidDate
     */
    public void setCertificateCheckValidDate(Date certificateCheckValidDate) {
        this.certificateCheckValidDate = certificateCheckValidDate;
    }    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public EnumResourceSource getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(EnumResourceSource resourceSource) {
        this.resourceSource = resourceSource;
    }

}
