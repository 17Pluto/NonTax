package com.xcmis.feefax.modules.entity;

import java.io.Serializable;

public class PaymentType implements Serializable{
	private String id;
	private String name;
	private String isMoney;
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

	public String getIsMoney() {
		return isMoney;
	}

	public void setIsMoney(String isMoney) {
		this.isMoney = isMoney;
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
