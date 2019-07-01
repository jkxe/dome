package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryManageUserPageUserNamePojo;

public class QueryManageUserPageUserNameResponse implements Serializable{

	/**
	 * 返回包含用户名标识类型的对象
	 */
	private static final long serialVersionUID = 3663078023672124287L;
	public List<QueryManageUserPageUserNamePojo> getQueryUserViewPagePojoUserNameList() {
		return queryUserViewPagePojoUserNameList;
	}
	public void setQueryUserViewPagePojoUserNameList(
			List<QueryManageUserPageUserNamePojo> queryUserViewPagePojoUserNameList) {
		this.queryUserViewPagePojoUserNameList = queryUserViewPagePojoUserNameList;
	}
	private List<QueryManageUserPageUserNamePojo> queryUserViewPagePojoUserNameList;

}
