package com.hsjry.user.facade.pojo;

import java.io.Serializable;
/**
 * 大事件
 * 
 * @author wanglg15468
 * @version $Id: UserBigEventPojo.java, v 1.0 2017-3-16 上午9:26:43 wanglg15468 Exp $
 */
public class UserBigEventPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -3377069392141886461L;
  //资源项ID
    private String resourceId;
    //客户ID
    private String userId;
    //事件名称
    private String eventName;
    //事件类型
    private String eventType;
    //事件分类
    private String eventClassify;
    //事件影响程度
    private String eventInfluenceDegree;
    //事件描述
    private String eventInfo;
    //涉及金额
    private String amountInvolved;
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
     * Getter method for property <tt>eventName</tt>.
     * 
     * @return property value of eventName
     */
    public String getEventName() {
        return eventName;
    }
    /**
     * Setter method for property <tt>eventName</tt>.
     * 
     * @param eventName value to be assigned to property eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    /**
     * Getter method for property <tt>eventType</tt>.
     * 
     * @return property value of eventType
     */
    public String getEventType() {
        return eventType;
    }
    /**
     * Setter method for property <tt>eventType</tt>.
     * 
     * @param eventType value to be assigned to property eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    /**
     * Getter method for property <tt>eventClassify</tt>.
     * 
     * @return property value of eventClassify
     */
    public String getEventClassify() {
        return eventClassify;
    }
    /**
     * Setter method for property <tt>eventClassify</tt>.
     * 
     * @param eventClassify value to be assigned to property eventClassify
     */
    public void setEventClassify(String eventClassify) {
        this.eventClassify = eventClassify;
    }
    /**
     * Getter method for property <tt>eventInfluenceDegree</tt>.
     * 
     * @return property value of eventInfluenceDegree
     */
    public String getEventInfluenceDegree() {
        return eventInfluenceDegree;
    }
    /**
     * Setter method for property <tt>eventInfluenceDegree</tt>.
     * 
     * @param eventInfluenceDegree value to be assigned to property eventInfluenceDegree
     */
    public void setEventInfluenceDegree(String eventInfluenceDegree) {
        this.eventInfluenceDegree = eventInfluenceDegree;
    }
    /**
     * Getter method for property <tt>eventInfo</tt>.
     * 
     * @return property value of eventInfo
     */
    public String getEventInfo() {
        return eventInfo;
    }
    /**
     * Setter method for property <tt>eventInfo</tt>.
     * 
     * @param eventInfo value to be assigned to property eventInfo
     */
    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }
    /**
     * Getter method for property <tt>amountInvolved</tt>.
     * 
     * @return property value of amountInvolved
     */
    public String getAmountInvolved() {
        return amountInvolved;
    }
    /**
     * Setter method for property <tt>amountInvolved</tt>.
     * 
     * @param amountInvolved value to be assigned to property amountInvolved
     */
    public void setAmountInvolved(String amountInvolved) {
        this.amountInvolved = amountInvolved;
    }

}
