package com.xcmis.feefax.modules.entity;

public class UnitItemUserInfo extends CommonEntity {


    private String chrId;
    private String mainId;
    private String userId;
    private int orderNum;
    private String zsAddress;
    private String remark;
    private String lastVer;
    private String eieChrName;
    private String ruChrCode;

    public String getRuChrCode() {
        return ruChrCode;
    }

    public void setRuChrCode(String ruChrCode) {
        this.ruChrCode = ruChrCode;
    }

    public String getEieChrName() {
        return eieChrName;
    }

    public void setEieChrName(String eieChrName) {
        this.eieChrName = eieChrName;
    }

    @Override
    public String getLastVer() {
        return lastVer;
    }

    @Override
    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZsAddress() {
        return zsAddress;
    }

    public void setZsAddress(String zsAddress) {
        this.zsAddress = zsAddress;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    @Override
    public String getChrId() {
        return chrId;
    }

    @Override
    public void setChrId(String chrId) {
        this.chrId = chrId;
    }
}