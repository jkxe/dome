package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

public class QueryManageUserPageUserNamePojo implements Serializable{

	/**
	 * 返回包含用户名标识类型的对象
	 */
	private static final long serialVersionUID = 778880931459944757L;

	//手机号联系点
    private List<UserTelContactStationInfoPojo> userTelContactStationInfoPojoList;
    //客户信息
    private UserCustomerInfoPojo                userCustomerInfoPojo;
    //个人客户基本信息表
    private UserPersonalBasicInfoPojo           userPersonalBasicInfoPojo;
    //机构基本信息
    private UserOrganBasicInfoPojo              userOrganBasicInfoPojo;
    //用户状态
    private EnumObjectStatus                    idStatus;
    //用户标识类型
    private String								identifiers;
	public List<UserTelContactStationInfoPojo> getUserTelContactStationInfoPojoList() {
		return userTelContactStationInfoPojoList;
	}
	public void setUserTelContactStationInfoPojoList(
			List<UserTelContactStationInfoPojo> userTelContactStationInfoPojoList) {
		this.userTelContactStationInfoPojoList = userTelContactStationInfoPojoList;
	}
	public UserCustomerInfoPojo getUserCustomerInfoPojo() {
		return userCustomerInfoPojo;
	}
	public void setUserCustomerInfoPojo(UserCustomerInfoPojo userCustomerInfoPojo) {
		this.userCustomerInfoPojo = userCustomerInfoPojo;
	}
	public UserPersonalBasicInfoPojo getUserPersonalBasicInfoPojo() {
		return userPersonalBasicInfoPojo;
	}
	public void setUserPersonalBasicInfoPojo(
			UserPersonalBasicInfoPojo userPersonalBasicInfoPojo) {
		this.userPersonalBasicInfoPojo = userPersonalBasicInfoPojo;
	}
	public UserOrganBasicInfoPojo getUserOrganBasicInfoPojo() {
		return userOrganBasicInfoPojo;
	}
	public void setUserOrganBasicInfoPojo(
			UserOrganBasicInfoPojo userOrganBasicInfoPojo) {
		this.userOrganBasicInfoPojo = userOrganBasicInfoPojo;
	}
	public EnumObjectStatus getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(EnumObjectStatus idStatus) {
		this.idStatus = idStatus;
	}
    public String getIdentifiers() {
        return identifiers;
    }
    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }
	
	

}
