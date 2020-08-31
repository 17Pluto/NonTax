package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class UserRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2220747688019301060L;
	
	
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}	

}
