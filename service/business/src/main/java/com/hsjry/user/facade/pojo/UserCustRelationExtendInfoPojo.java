package com.hsjry.user.facade.pojo;

import java.io.Serializable;

public class UserCustRelationExtendInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -4585416919457553377L;
    //客户ID
    private String            userId;
    //关联人客户ID
    private String            relationId;
    //关联扩展类型
    private String            extendRelation;
    //关联扩展内容
    private String            extendMsg;
    //数据状态
    private String            dataStatus;
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
     * Getter method for property <tt>extendRelation</tt>.
     * 
     * @return property value of extendRelation
     */
    public String getExtendRelation() {
        return extendRelation;
    }
    /**
     * Setter method for property <tt>extendRelation</tt>.
     * 
     * @param extendRelation value to be assigned to property extendRelation
     */
    public void setExtendRelation(String extendRelation) {
        this.extendRelation = extendRelation;
    }
    /**
     * Getter method for property <tt>extendMsg</tt>.
     * 
     * @return property value of extendMsg
     */
    public String getExtendMsg() {
        return extendMsg;
    }
    /**
     * Setter method for property <tt>extendMsg</tt>.
     * 
     * @param extendMsg value to be assigned to property extendMsg
     */
    public void setExtendMsg(String extendMsg) {
        this.extendMsg = extendMsg;
    }
    /**
     * Getter method for property <tt>dataStatus</tt>.
     * 
     * @return property value of dataStatus
     */
    public String getDataStatus() {
        return dataStatus;
    }
    /**
     * Setter method for property <tt>dataStatus</tt>.
     * 
     * @param dataStatus value to be assigned to property dataStatus
     */
    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
    
    
}
