package com.hsjry.user.facade.pojo;

import java.io.Serializable;

public class FinancialStatementsInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -2261908829634129705L;
    //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //资源标识
    private String            resourchheId;
    //资源内容
    private String            resourceSubstance;
    //资源来源
    private String            resourceSource;
    //资源状态
    private String            resourceStatus;
    //创建日期
    private String            createTime;

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
     * Getter method for property <tt>resourchheId</tt>.
     * 
     * @return property value of resourchheId
     */
    public String getResourchheId() {
        return resourchheId;
    }

    /**
     * Setter method for property <tt>resourchheId</tt>.
     * 
     * @param resourchheId value to be assigned to property resourchheId
     */
    public void setResourchheId(String resourchheId) {
        this.resourchheId = resourchheId;
    }

    /**
     * Getter method for property <tt>resourceSubstance</tt>.
     * 
     * @return property value of resourceSubstance
     */
    public String getResourceSubstance() {
        return resourceSubstance;
    }

    /**
     * Setter method for property <tt>resourceSubstance</tt>.
     * 
     * @param resourceSubstance value to be assigned to property resourceSubstance
     */
    public void setResourceSubstance(String resourceSubstance) {
        this.resourceSubstance = resourceSubstance;
    }

    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public String getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(String resourceSource) {
        this.resourceSource = resourceSource;
    }

    /**
     * Getter method for property <tt>resourceStatus</tt>.
     * 
     * @return property value of resourceStatus
     */
    public String getResourceStatus() {
        return resourceStatus;
    }

    /**
     * Setter method for property <tt>resourceStatus</tt>.
     * 
     * @param resourceStatus value to be assigned to property resourceStatus
     */
    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    /**
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
