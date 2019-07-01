package com.hsjry.user.facade.pojo.modify;

import com.hsjry.user.facade.pojo.MetaProfession;
import com.hsjry.user.facade.pojo.enums.*;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * 职业信息
 *
 * @author wanglg15468
 * @version $Id: UserProfessionalInfoPojo.java, v 1.0 2017-3-15 下午7:21:43 wanglg15468 Exp $
 */
public class UserProfessionalInfoModifyPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 7905991238339627672L;
    //资源项ID
    @NotNull(errorCode = "000001", message = "资源项ID")
    @NotBlank(errorCode = "000001", message = "资源项ID")
    private String resourceId;
    //客户ID
    private String userId;
    //就业年限
    private String employmentYear;
    //结束年限
    private String finishYear;
    //单位名称
    private String companyName;
    //单位电话
    private String companyTelephone;
    //行业
    private EnumIndustryType industryCode;
    //职业    
    private Integer profession;
    //职位
    private String jobTitle;
    //职称    
    private EnumTitleType positionalTitles;
    //工作年限
    private String workingYears;
    //状态
    private String status;
    //行政区域
    private String administrativeArea;
    //单位规模
    private String unitScale;
    //薪资范围
    private String salaryRange;
    //职务 
    private EnumPost post;
    //单位性质
    private EnumUnitProperty unitProperty;
    @Deprecated
    private EnumVocationType vocation;

    /**
     * Getter method for property <tt>unitProperty</tt>.
     *
     * @return property value of unitProperty
     */
    public EnumUnitProperty getUnitProperty() {
        return unitProperty;
    }

    /**
     * Setter method for property <tt>unitProperty</tt>.
     *
     * @param unitProperty value to be assigned to property unitProperty
     */
    public void setUnitProperty(EnumUnitProperty unitProperty) {
        this.unitProperty = unitProperty;
    }

    public Integer getProfession() {
        return profession;
    }

    public void setProfession(Integer profession) {
        this.profession = profession;
    }

    /**
     * Getter method for property <tt>positionalTitles</tt>.
     *
     * @return property value of positionalTitles
     */
    public EnumTitleType getPositionalTitles() {
        return positionalTitles;
    }

    /**
     * Setter method for property <tt>positionalTitles</tt>.
     *
     * @param positionalTitles value to be assigned to property positionalTitles
     */
    public void setPositionalTitles(EnumTitleType positionalTitles) {
        this.positionalTitles = positionalTitles;
    }

    /**
     * Getter method for property <tt>post</tt>.
     *
     * @return property value of post
     */
    public EnumPost getPost() {
        return post;
    }

    /**
     * Setter method for property <tt>post</tt>.
     *
     * @param post value to be assigned to property post
     */
    public void setPost(EnumPost post) {
        this.post = post;
    }

    /**
     * Getter method for property <tt>unitScale</tt>.
     *
     * @return property value of unitScale
     */
    public String getUnitScale() {
        return unitScale;
    }

    /**
     * Setter method for property <tt>unitScale</tt>.
     *
     * @param unitScale value to be assigned to property unitScale
     */
    public void setUnitScale(String unitScale) {
        this.unitScale = unitScale;
    }

    /**
     * Getter method for property <tt>salaryRange</tt>.
     *
     * @return property value of salaryRange
     */
    public String getSalaryRange() {
        return salaryRange;
    }

    /**
     * Setter method for property <tt>salaryRange</tt>.
     *
     * @param salaryRange value to be assigned to property salaryRange
     */
    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
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
     * Getter method for property <tt>employmentYear</tt>.
     *
     * @return property value of employmentYear
     */
    public String getEmploymentYear() {
        return employmentYear;
    }

    /**
     * Setter method for property <tt>employmentYear</tt>.
     *
     * @param employmentYear value to be assigned to property employmentYear
     */
    public void setEmploymentYear(String employmentYear) {
        this.employmentYear = employmentYear;
    }

    /**
     * Getter method for property <tt>finishYear</tt>.
     *
     * @return property value of finishYear
     */
    public String getFinishYear() {
        return finishYear;
    }

    /**
     * Setter method for property <tt>finishYear</tt>.
     *
     * @param finishYear value to be assigned to property finishYear
     */
    public void setFinishYear(String finishYear) {
        this.finishYear = finishYear;
    }

    /**
     * Getter method for property <tt>companyName</tt>.
     *
     * @return property value of companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter method for property <tt>companyName</tt>.
     *
     * @param companyName value to be assigned to property companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter method for property <tt>companyTelephone</tt>.
     *
     * @return property value of companyTelephone
     */
    public String getCompanyTelephone() {
        return companyTelephone;
    }

    /**
     * Setter method for property <tt>companyTelephone</tt>.
     *
     * @param companyTelephone value to be assigned to property companyTelephone
     */
    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    /**
     * Getter method for property <tt>industryCode</tt>.
     *
     * @return property value of industryCode
     */
    public EnumIndustryType getIndustryCode() {
        return industryCode;
    }

    /**
     * Setter method for property <tt>industryCode</tt>.
     *
     * @param industryCode value to be assigned to property industryCode
     */
    public void setIndustryCode(EnumIndustryType industryCode) {
        this.industryCode = industryCode;
    }

    /**
     * Getter method for property <tt>jobTitle</tt>.
     *
     * @return property value of jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Setter method for property <tt>jobTitle</tt>.
     *
     * @param jobTitle value to be assigned to property jobTitle
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Getter method for property <tt>workingYears</tt>.
     *
     * @return property value of workingYears
     */
    public String getWorkingYears() {
        return workingYears;
    }

    /**
     * Setter method for property <tt>workingYears</tt>.
     *
     * @param workingYears value to be assigned to property workingYears
     */
    public void setWorkingYears(String workingYears) {
        this.workingYears = workingYears;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>administrativeArea</tt>.
     *
     * @return property value of administrativeArea
     */
    public String getAdministrativeArea() {
        return administrativeArea;
    }

    /**
     * Setter method for property <tt>administrativeArea</tt>.
     *
     * @param administrativeArea value to be assigned to property administrativeArea
     */
    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public EnumVocationType getVocation() {
        return vocation;
    }

    public void setVocation(EnumVocationType vocation) {
        this.vocation = vocation;
    }
}
