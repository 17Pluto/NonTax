package com.xcmis.feefax.modules.entity;

public class Region extends CommonEntity{
	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	private int isValid;
	private int isTop;

}
