package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class UserRoleRule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String roleId;
	private String ruleId;
	private String lastVer;
	private String setYear;
	private String rgCode;

	private String roleIdAll;

	public String getRoleIdAll() {
		return roleIdAll;
	}

	public void setRoleIdAll(String roleIdAll) {
		this.roleIdAll = roleIdAll;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRgCode() {
		return rgCode;
	}

	public void setRgCode(String rgCode) {
		this.rgCode = rgCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastVer() {
		return lastVer;
	}

	public void setLastVer(String lastVer) {
		this.lastVer = lastVer;
	}

	public String getSetYear() {
		return setYear;
	}

	public void setSetYear(String setYear) {
		this.setYear = setYear;
	}
}
