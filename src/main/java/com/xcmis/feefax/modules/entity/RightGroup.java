package com.xcmis.feefax.modules.entity;

import java.util.List;

public class RightGroup {
    private String rightGroupId;
    private String rightGroupDescribe;
    private int rightType;
    private long ruleId;
    private String lastVer;
    private int setYear;
    private String rgCode;


    private List<RightGroupType> rightGroupTypeList;
    private List<RightGroupDetail> rightGroupDetailList;

    public String getRightGroupId() {
        return rightGroupId;
    }

    public void setRightGroupId(String rightGroupId) {
        this.rightGroupId = rightGroupId;
    }

    public String getRightGroupDescribe() {
        return rightGroupDescribe;
    }

    public void setRightGroupDescribe(String rightGroupDescribe) {
        this.rightGroupDescribe = rightGroupDescribe;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public int getSetYear() {
        return setYear;
    }

    public void setSetYear(int setYear) {
        this.setYear = setYear;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }


    public List<RightGroupType> getRightGroupTypeList() {
        return rightGroupTypeList;
    }

    public void setRightGroupTypeList(List<RightGroupType> rightGroupTypeList) {
        this.rightGroupTypeList = rightGroupTypeList;
    }

    public List<RightGroupDetail> getRightGroupDetailList() {
        return rightGroupDetailList;
    }

    public void setRightGroupDetailList(List<RightGroupDetail> rightGroupDetailList) {
        this.rightGroupDetailList = rightGroupDetailList;
    }
}
