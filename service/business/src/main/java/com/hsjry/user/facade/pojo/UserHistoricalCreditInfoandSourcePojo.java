package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumCreditType;
import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * 历史信贷信息
 * @author liaosq23298
 * @version $Id: UserHistoricalCreditInfoandSourcePojo.java, v 0.1 Nov 22, 2017 3:55:22 PM liaosq23298 Exp $
 */
public class UserHistoricalCreditInfoandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = -3040884780691801594L;
  //资源项ID
    private String resourceId;
    //客户ID
    private String userId;
    //信贷类型
    private EnumCreditType creditType;
    //信贷类别
    private String creditKind;
    //贷款总额
    private String loanAmount;
    //还款总额
    private String repaymentAmount;
    //逾期金额
    private String overdueAmount;
    //最长逾期时间
    private String longestOverdueDate;
    //信息来源
    private String infoSource;
    //资源来源
    private EnumResourceSource resourceSource;
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
     * Getter method for property <tt>creditType</tt>.
     * 
     * @return property value of creditType
     */
    public EnumCreditType getCreditType() {
        return creditType;
    }
    /**
     * Setter method for property <tt>creditType</tt>.
     * 
     * @param creditType value to be assigned to property creditType
     */
    public void setCreditType(EnumCreditType creditType) {
        this.creditType = creditType;
    }
    /**
     * Getter method for property <tt>creditKind</tt>.
     * 
     * @return property value of creditKind
     */
    public String getCreditKind() {
        return creditKind;
    }
    /**
     * Setter method for property <tt>creditKind</tt>.
     * 
     * @param creditKind value to be assigned to property creditKind
     */
    public void setCreditKind(String creditKind) {
        this.creditKind = creditKind;
    }
    /**
     * Getter method for property <tt>loanAmount</tt>.
     * 
     * @return property value of loanAmount
     */
    public String getLoanAmount() {
        return loanAmount;
    }
    /**
     * Setter method for property <tt>loanAmount</tt>.
     * 
     * @param loanAmount value to be assigned to property loanAmount
     */
    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }
    /**
     * Getter method for property <tt>repaymentAmount</tt>.
     * 
     * @return property value of repaymentAmount
     */
    public String getRepaymentAmount() {
        return repaymentAmount;
    }
    /**
     * Setter method for property <tt>repaymentAmount</tt>.
     * 
     * @param repaymentAmount value to be assigned to property repaymentAmount
     */
    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }
    /**
     * Getter method for property <tt>overdueAmount</tt>.
     * 
     * @return property value of overdueAmount
     */
    public String getOverdueAmount() {
        return overdueAmount;
    }
    /**
     * Setter method for property <tt>overdueAmount</tt>.
     * 
     * @param overdueAmount value to be assigned to property overdueAmount
     */
    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }
    /**
     * Getter method for property <tt>longestOverdueDate</tt>.
     * 
     * @return property value of longestOverdueDate
     */
    public String getLongestOverdueDate() {
        return longestOverdueDate;
    }
    /**
     * Setter method for property <tt>longestOverdueDate</tt>.
     * 
     * @param longestOverdueDate value to be assigned to property longestOverdueDate
     */
    public void setLongestOverdueDate(String longestOverdueDate) {
        this.longestOverdueDate = longestOverdueDate;
    }
    /**
     * Getter method for property <tt>infoSource</tt>.
     * 
     * @return property value of infoSource
     */
    public String getInfoSource() {
        return infoSource;
    }
    /**
     * Setter method for property <tt>infoSource</tt>.
     * 
     * @param infoSource value to be assigned to property infoSource
     */
    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }
    /**
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
