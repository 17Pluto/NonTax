package com.xcmis.feefax.modules.entity;

public class UnitItemAccredit extends CommonEntity{

    private String chrId;
    private String mainId;
    private String unitId;
    private String unitName;
    private String lastVer;
    private int orderNum;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String getChrId() {
        return chrId;
    }

    @Override
    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getLastVer() {
        return lastVer;
    }

    @Override
    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
