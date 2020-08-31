package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class RolePermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6705698433227191683L;
	
    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限集合id
     */
    private String permissionId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

}
