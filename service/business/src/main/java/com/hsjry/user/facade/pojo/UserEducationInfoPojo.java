package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumCareerType;
import com.hsjry.user.facade.pojo.enums.EnumEducationType;

/**
 * 个人客户教育信息
 * 
 * @author wanglg15468
 * @version $Id: UserUserEducationInfoPojo.java, v 1.0 2017-3-16 上午9:09:28 wanglg15468 Exp $
 */
public class UserEducationInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -2014241958280314724L;
    //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //就读学校
    private String            university;
    //专业
    private String            specialty;
    //入学年份
    private String            entranceYear;
    //毕业年份
    private String            graduationYear;
    //状态
    private String            status;
    //是否终态
    private String            endFlag;
    //教育类型
    private EnumEducationType educationType;
    //学位
    private EnumCareerType    careerType;

    /**
     * Getter method for property <tt>careerType</tt>.
     * 
     * @return property value of careerType
     */
    public EnumCareerType getCareerType() {
        return careerType;
    }

    /**
     * Setter method for property <tt>careerType</tt>.
     * 
     * @param careerType value to be assigned to property careerType
     */
    public void setCareerType(EnumCareerType careerType) {
        this.careerType = careerType;
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
     * Getter method for property <tt>university</tt>.
     * 
     * @return property value of university
     */
    public String getUniversity() {
        return university;
    }

    /**
     * Setter method for property <tt>university</tt>.
     * 
     * @param university value to be assigned to property university
     */
    public void setUniversity(String university) {
        this.university = university;
    }

    /**
     * Getter method for property <tt>specialty</tt>.
     * 
     * @return property value of specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Setter method for property <tt>specialty</tt>.
     * 
     * @param specialty value to be assigned to property specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Getter method for property <tt>entranceYear</tt>.
     * 
     * @return property value of entranceYear
     */
    public String getEntranceYear() {
        return entranceYear;
    }

    /**
     * Setter method for property <tt>entranceYear</tt>.
     * 
     * @param entranceYear value to be assigned to property entranceYear
     */
    public void setEntranceYear(String entranceYear) {
        this.entranceYear = entranceYear;
    }

    /**
     * Getter method for property <tt>graduationYear</tt>.
     * 
     * @return property value of graduationYear
     */
    public String getGraduationYear() {
        return graduationYear;
    }

    /**
     * Setter method for property <tt>graduationYear</tt>.
     * 
     * @param graduationYear value to be assigned to property graduationYear
     */
    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
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
     * Getter method for property <tt>endFlag</tt>.
     * 
     * @return property value of endFlag
     */
    public String getEndFlag() {
        return endFlag;
    }

    /**
     * Setter method for property <tt>endFlag</tt>.
     * 
     * @param endFlag value to be assigned to property endFlag
     */
    public void setEndFlag(String endFlag) {
        this.endFlag = endFlag;
    }

    /**
     * Getter method for property <tt>educationType</tt>.
     * 
     * @return property value of educationType
     */
    public EnumEducationType getEducationType() {
        return educationType;
    }

    /**
     * Setter method for property <tt>educationType</tt>.
     * 
     * @param educationType value to be assigned to property educationType
     */
    public void setEducationType(EnumEducationType educationType) {
        this.educationType = educationType;
    }

}
