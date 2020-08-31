package com.xcmis.feefax.modules.entity;

import java.io.Serializable;

public class ProjectType implements Serializable{
	private String id;
	private String name;
	private String isAgent;
	private String remark;
	private String isUsed;
	private String isDeleted;
	private int setYear;
	private String bh;

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getSetYear() {
		return setYear;
	}

	public void setSetYear(int setYear) {
		this.setYear = setYear;
	}
}
