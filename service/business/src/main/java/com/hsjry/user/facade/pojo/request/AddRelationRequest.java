package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.AddRelationPojo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 客户关系新增接口请求
 * 
 * @author jiangjd12837
 * @version $Id: RelationAddRequest.java, v 1.0 2017年3月14日 下午2:47:59 jiangjd12837 Exp $
 */
public class AddRelationRequest implements Serializable {

    /**  */
    private static final long     serialVersionUID = 7154941111558759088L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                userId;
    //关系人信息
    private List<AddRelationPojo> addRelationPojoList;

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
     * Getter method for property <tt>addRelationPojoList</tt>.
     * 
     * @return property value of addRelationPojoList
     */
    public List<AddRelationPojo> getAddRelationPojoList() {
        return addRelationPojoList;
    }

    /**
     * Setter method for property <tt>addRelationPojoList</tt>.
     * 
     * @param addRelationPojoList value to be assigned to property addRelationPojoList
     */
    public void setAddRelationPojoList(List<AddRelationPojo> addRelationPojoList) {
        this.addRelationPojoList = addRelationPojoList;
    }

}
