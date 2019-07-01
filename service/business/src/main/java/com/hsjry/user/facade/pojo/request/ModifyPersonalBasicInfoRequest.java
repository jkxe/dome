package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumEduLevel;
import com.hsjry.user.facade.pojo.enums.EnumGender;
import com.hsjry.user.facade.pojo.enums.EnumMarriageStatus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 个人客户基本信息请求报文
 * 
 * @author jiangjd12837
 * @version $Id: UserPersonalBasicInfoRequest.java, v 1.0 2017年3月13日 下午4:54:56 jiangjd12837 Exp $
 */
public class ModifyPersonalBasicInfoRequest implements Serializable {

    /**  */
    private static final long  serialVersionUID = 4719401293162392398L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String             userId;
    //客户姓名
    private String             clientName;
    //英文名称
    private String             englishName;
    //简称
    private String             shortName;
    //性别
    private EnumGender         sex;
    //学历
    private EnumEduLevel       eduLevel;
    //政治面貌
    private String             politicalStatus;
    //国籍地区
    private String             nationality;
    //民族
    private String             idNation;
    //宗教
    private String             religion;
    //年龄
    private Integer            clientAgeNum;
    //婚姻状况
    private EnumMarriageStatus marriageStatus;

    /**
     * Getter method for property <tt>clientAgeNum</tt>.
     * 
     * @return property value of clientAgeNum
     */
    public Integer getClientAgeNum() {
        return clientAgeNum;
    }

    /**
     * Setter method for property <tt>clientAgeNum</tt>.
     * 
     * @param clientAgeNum value to be assigned to property clientAgeNum
     */
    public void setClientAgeNum(Integer clientAgeNum) {
        this.clientAgeNum = clientAgeNum;
    }

    /**
     * Getter method for property <tt>eduLevel</tt>.
     * 
     * @return property value of eduLevel
     */
    public EnumEduLevel getEduLevel() {
        return eduLevel;
    }

    /**
     * Setter method for property <tt>eduLevel</tt>.
     * 
     * @param eduLevel value to be assigned to property eduLevel
     */
    public void setEduLevel(EnumEduLevel eduLevel) {
        this.eduLevel = eduLevel;
    }

    /**
     * Getter method for property <tt>marriageStatus</tt>.
     * 
     * @return property value of marriageStatus
     */
    public EnumMarriageStatus getMarriageStatus() {
        return marriageStatus;
    }

    /**
     * Setter method for property <tt>marriageStatus</tt>.
     * 
     * @param marriageStatus value to be assigned to property marriageStatus
     */
    public void setMarriageStatus(EnumMarriageStatus marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

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
     * Getter method for property <tt>englishName</tt>.
     * 
     * @return property value of englishName
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Setter method for property <tt>englishName</tt>.
     * 
     * @param englishName value to be assigned to property englishName
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * Getter method for property <tt>shortName</tt>.
     * 
     * @return property value of shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Setter method for property <tt>shortName</tt>.
     * 
     * @param shortName value to be assigned to property shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Getter method for property <tt>politicalStatus</tt>.
     * 
     * @return property value of politicalStatus
     */
    public String getPoliticalStatus() {
        return politicalStatus;
    }

    /**
     * Setter method for property <tt>politicalStatus</tt>.
     * 
     * @param politicalStatus value to be assigned to property politicalStatus
     */
    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    /**
     * Getter method for property <tt>nationality</tt>.
     * 
     * @return property value of nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Setter method for property <tt>nationality</tt>.
     * 
     * @param nationality value to be assigned to property nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Getter method for property <tt>idNation</tt>.
     * 
     * @return property value of idNation
     */
    public String getIdNation() {
        return idNation;
    }

    /**
     * Setter method for property <tt>idNation</tt>.
     * 
     * @param idNation value to be assigned to property idNation
     */
    public void setIdNation(String idNation) {
        this.idNation = idNation;
    }

    /**
     * Getter method for property <tt>religion</tt>.
     * 
     * @return property value of religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Setter method for property <tt>religion</tt>.
     * 
     * @param religion value to be assigned to property religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

}
