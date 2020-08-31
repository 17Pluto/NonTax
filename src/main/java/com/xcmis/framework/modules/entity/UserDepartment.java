package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class UserDepartment implements Serializable{

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
    private String departmentId;
    
    private String groupId;
    

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


}
