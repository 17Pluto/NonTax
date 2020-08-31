package com.xcmis.framework.modules.entity;

import java.io.Serializable;

public class UserOrg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String orgId;
	private String lastVer;
	private String setYear;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
