package com.xcmis.feefax.modules.entity;

public class BudgetSubjectIncome extends CommonEntity{
	private int typeId;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getSubitemType() {
		return subitemType;
	}

	public void setSubitemType(int subitemType) {
		this.subitemType = subitemType;
	}

	private int subitemType;
}
