package com.hsjry.user.facade.pojo.modify;

import java.io.Serializable;
import java.util.Date;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumOrganType;
import com.hsjry.user.facade.pojo.enums.EnumRecordKind;
import com.hsjry.user.facade.pojo.enums.EnumSurvivalStatus;

/**
 * 机构基本信息
 * 
 * @author jiangjd12837
 * @version $Id: ModifyOrganBasicInfoPojo.java, v 1.0 2017年3月27日 下午1:49:38 jiangjd12837 Exp $
 */
public class UserOrganBasicInfoModifyPojo implements Serializable {

    /**  */
    private static final long  serialVersionUID = -7345504447243148965L;
    /**内部请求报文*/
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String             userId;
    //存续状态
    private EnumSurvivalStatus survivalStatus;
    //上市属性
    private String             marketProp;
    //上市地点
    private String             marketPlace;
    //上市股票代码
    private String             marketCode;
    //注册时间
    private Date               regdate;
    //注册资本
    private String             registerFund;
    //注册登记类型
    private EnumRecordKind     recordKind;
    //机构名称
    private String             organName;
    //机构简称
    private String             shortName;
    //机构英文名称
    private String             englishName;
    //机构类别
    private EnumOrganType      organType;
    //进出口权限
    private String             importExportPermission;
    //经营规模
    private String             workScale;

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
     * Getter method for property <tt>survivalStatus</tt>.
     * 
     * @return property value of survivalStatus
     */
    public EnumSurvivalStatus getSurvivalStatus() {
        return survivalStatus;
    }

    /**
     * Setter method for property <tt>survivalStatus</tt>.
     * 
     * @param survivalStatus value to be assigned to property survivalStatus
     */
    public void setSurvivalStatus(EnumSurvivalStatus survivalStatus) {
        this.survivalStatus = survivalStatus;
    }

    /**
     * Getter method for property <tt>marketProp</tt>.
     * 
     * @return property value of marketProp
     */
    public String getMarketProp() {
        return marketProp;
    }

    /**
     * Setter method for property <tt>marketProp</tt>.
     * 
     * @param marketProp value to be assigned to property marketProp
     */
    public void setMarketProp(String marketProp) {
        this.marketProp = marketProp;
    }

    /**
     * Getter method for property <tt>marketPlace</tt>.
     * 
     * @return property value of marketPlace
     */
    public String getMarketPlace() {
        return marketPlace;
    }

    /**
     * Setter method for property <tt>marketPlace</tt>.
     * 
     * @param marketPlace value to be assigned to property marketPlace
     */
    public void setMarketPlace(String marketPlace) {
        this.marketPlace = marketPlace;
    }

    /**
     * Getter method for property <tt>marketCode</tt>.
     * 
     * @return property value of marketCode
     */
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * Setter method for property <tt>marketCode</tt>.
     * 
     * @param marketCode value to be assigned to property marketCode
     */
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * Getter method for property <tt>regdate</tt>.
     * 
     * @return property value of regdate
     */
    public Date getRegdate() {
        return regdate;
    }

    /**
     * Setter method for property <tt>regdate</tt>.
     * 
     * @param regdate value to be assigned to property regdate
     */
    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    /**
     * Getter method for property <tt>registerFund</tt>.
     * 
     * @return property value of registerFund
     */
    public String getRegisterFund() {
        return registerFund;
    }

    /**
     * Setter method for property <tt>registerFund</tt>.
     * 
     * @param registerFund value to be assigned to property registerFund
     */
    public void setRegisterFund(String registerFund) {
        this.registerFund = registerFund;
    }

    /**
     * Getter method for property <tt>recordKind</tt>.
     * 
     * @return property value of recordKind
     */
    public EnumRecordKind getRecordKind() {
        return recordKind;
    }

    /**
     * Setter method for property <tt>recordKind</tt>.
     * 
     * @param recordKind value to be assigned to property recordKind
     */
    public void setRecordKind(EnumRecordKind recordKind) {
        this.recordKind = recordKind;
    }

    /**
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
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
     * Getter method for property <tt>organType</tt>.
     * 
     * @return property value of organType
     */
    public EnumOrganType getOrganType() {
        return organType;
    }

    /**
     * Setter method for property <tt>organType</tt>.
     * 
     * @param organType value to be assigned to property organType
     */
    public void setOrganType(EnumOrganType organType) {
        this.organType = organType;
    }

    /**
     * Getter method for property <tt>importExportPermission</tt>.
     * 
     * @return property value of importExportPermission
     */
    public String getImportExportPermission() {
        return importExportPermission;
    }

    /**
     * Setter method for property <tt>importExportPermission</tt>.
     * 
     * @param importExportPermission value to be assigned to property importExportPermission
     */
    public void setImportExportPermission(String importExportPermission) {
        this.importExportPermission = importExportPermission;
    }

    /**
     * Getter method for property <tt>workScale</tt>.
     * 
     * @return property value of workScale
     */
    public String getWorkScale() {
        return workScale;
    }

    /**
     * Setter method for property <tt>workScale</tt>.
     * 
     * @param workScale value to be assigned to property workScale
     */
    public void setWorkScale(String workScale) {
        this.workScale = workScale;
    }
}
