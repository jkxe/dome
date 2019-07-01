package com.hsjry.user.facade.pojo.modify;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumExtendRelation;

/**
 * 客户关系扩展信息
 * 
 * @author jiangjd12837
 * @version $Id: UserCustomerRelationExtendPojo.java, v 1.0 2017年3月30日 下午4:18:07 jiangjd12837 Exp $
 */
public class UserCustomerRelationExtendModifyPojo implements Serializable {

    /**  */
    private static final long  serialVersionUID = -1626869840046851368L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String             userId;
    //关联人客户ID

    private String             relationUserId;
    //关联扩展类型
    private EnumExtendRelation extendRelation;
    //关联扩展内容
    private String             extendMsg;
    //数据状态
    private String             dataStatus;

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
     * @param relationUserId value to be assigned to property relationUserId
     */
    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    /**
     * Getter method for property <tt>extendRelation</tt>.
     * 
     * @return property value of extendRelation
     */
    public EnumExtendRelation getExtendRelation() {
        return extendRelation;
    }

    /**
     * Setter method for property <tt>extendRelation</tt>.
     * 
     * @param extendRelation value to be assigned to property extendRelation
     */
    public void setExtendRelation(EnumExtendRelation extendRelation) {
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
