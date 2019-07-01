package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhengqy15963
 * @version $$Id: UserRoleAndPermissionPojo.java, v 0.1 2018/11/5 14:16 zhengqy15963 Exp $$
 */
public class UserRoleAndPermissionPojo implements Serializable {
    private static final long serialVersionUID = 5986732525863653239L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户电话
     */
    private String telephone;
    /**
     * 登录账号
     */
    private String authIdentity;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户所属机构名称
     */
    private String organName;
    /**
     * 用户所属角色以及每个角色对应的权限
     */
    private List<String> roleInfoName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAuthIdentity() {
        return authIdentity;
    }

    public void setAuthIdentity(String authIdentity) {
        this.authIdentity = authIdentity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public List<String> getRoleInfoName() {
        return roleInfoName;
    }

    public void setRoleInfoName(List<String> roleInfoName) {
        this.roleInfoName = roleInfoName;
    }
}
