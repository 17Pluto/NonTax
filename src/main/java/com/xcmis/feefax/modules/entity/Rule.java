package com.xcmis.feefax.modules.entity;

public class Rule {
    private long ruleId;
    private String ruleCode;
    private String ruleName;
    private String remark;
    private int setYear;
    private int enabled;
    private String ruleClassify;
    private String sysRemark;
    private int rightType;
    private String createUser;
    private String createDate;
    private String latestOpUser;
    private String latestOpDate;
    private String lastVer;
    private String rgCode;

    private RightGroup rightGroup;

    public RightGroup getRightGroup() {
        return rightGroup;
    }

    public void setRightGroup(RightGroup rightGroup) {
        this.rightGroup = rightGroup;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSetYear() {
        return setYear;
    }

    public void setSetYear(int setYear) {
        this.setYear = setYear;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getRuleClassify() {
        return ruleClassify;
    }

    public void setRuleClassify(String ruleClassify) {
        this.ruleClassify = ruleClassify;
    }

    public String getSysRemark() {
        return sysRemark;
    }

    public void setSysRemark(String sysRemark) {
        this.sysRemark = sysRemark;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLatestOpUser() {
        return latestOpUser;
    }

    public void setLatestOpUser(String latestOpUser) {
        this.latestOpUser = latestOpUser;
    }

    public String getLatestOpDate() {
        return latestOpDate;
    }

    public void setLatestOpDate(String latestOpDate) {
        this.latestOpDate = latestOpDate;
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
