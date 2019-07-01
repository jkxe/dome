package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.AddRelationInfoPojo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.List;


/**
 * 客户关系新增接口请求
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @Description:
 * @date 2017年10月22日  21:23
 */
public class AddRelationByRelationTypeRequest implements Serializable {

    private static final long     serialVersionUID = 7154941111558759088L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                userId;
    //关系人信息
    private List<AddRelationInfoPojo> addRelationPojoList;

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public List<AddRelationInfoPojo> getAddRelationPojoList() {
        return addRelationPojoList;
    }

    public void setAddRelationPojoList(List<AddRelationInfoPojo> addRelationPojoList) {
        this.addRelationPojoList = addRelationPojoList;
    }

}
