/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import com.hsjry.user.facade.pojo.enums.EnumClientRelation;
import com.hsjry.user.facade.pojo.enums.EnumRelationType;

import java.io.Serializable;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description: 联系人关系信息
 * @date 2017年10月23日  14:39
 */
public class QueryUserCustomerRelationPojo implements Serializable {

    private static final long serialVersionUID = -124453616277210633L;

    //客户ID
    private String userId;
    //关系人客户ID   
    private String relationUserId;
    //客户关系代码
    private EnumClientRelation clientRelation;
    //客户关系类型
    private EnumRelationType relationType;
    //关系人姓名
    private String clientName;
    //关系人证件
    private UserCertificateInfoPojo userCertificateInfoPojo;
    //关系人联系方式
    private UserTelContactStationInfoPojo userTelContactStationInfoPojo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelationUserId() {
        return relationUserId;
    }

    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    public EnumClientRelation getClientRelation() {
        return clientRelation;
    }

    public void setClientRelation(EnumClientRelation clientRelation) {
        this.clientRelation = clientRelation;
    }

    public EnumRelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(EnumRelationType relationType) {
        this.relationType = relationType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public UserCertificateInfoPojo getUserCertificateInfoPojo() {
        return userCertificateInfoPojo;
    }

    public void setUserCertificateInfoPojo(UserCertificateInfoPojo userCertificateInfoPojo) {
        this.userCertificateInfoPojo = userCertificateInfoPojo;
    }

    public UserTelContactStationInfoPojo getUserTelContactStationInfoPojo() {
        return userTelContactStationInfoPojo;
    }

    public void setUserTelContactStationInfoPojo(UserTelContactStationInfoPojo userTelContactStationInfoPojo) {
        this.userTelContactStationInfoPojo = userTelContactStationInfoPojo;
    }
}
