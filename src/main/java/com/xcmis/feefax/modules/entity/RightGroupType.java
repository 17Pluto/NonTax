package com.xcmis.feefax.modules.entity;

import java.util.List;

public class RightGroupType {
    private String rightGroupId;
    private String eleCode;
    private int rightType;
    private int setYear;
    private String lastVer;
    private String rgCode;

    public String getRightGroupId() {
        return rightGroupId;
    }

    public void setRightGroupId(String rightGroupId) {
        this.rightGroupId = rightGroupId;
    }

    public String getEleCode() {
        return eleCode;
    }

    public void setEleCode(String eleCode) {
        this.eleCode = eleCode;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public int getSetYear() {
        return setYear;
    }

    public void setSetYear(int setYear) {
        this.setYear = setYear;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }
}
