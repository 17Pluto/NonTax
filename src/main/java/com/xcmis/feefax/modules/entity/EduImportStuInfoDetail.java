package com.xcmis.feefax.modules.entity;

/**
 * @Author fangzhe
 * @Date 2020/6/21 2:51 下午
 * @Version 1.0
 */
public class EduImportStuInfoDetail {

    private String chrId;
    private String stuMainid;
    private String incitemid;
    private String stuIncitemName;
    private double chargemoney;
    private String lastVer;
    private String onePrice;
    private String measure;
    private double chargenum;
    private String incitemCode;
    private String taxNo;
    private String unititemId;

    public String getUnititemId() {
        return unititemId;
    }

    public void setUnititemId(String unititemId) {
        this.unititemId = unititemId;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getIncitemCode() {
        return incitemCode;
    }

    public void setIncitemCode(String incitemCode) {
        this.incitemCode = incitemCode;
    }

    public String getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(String onePrice) {
        this.onePrice = onePrice;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getChargenum() {
        return chargenum;
    }

    public void setChargenum(double chargenum) {
        this.chargenum = chargenum;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getStuMainid() {
        return stuMainid;
    }

    public void setStuMainid(String stuMainid) {
        this.stuMainid = stuMainid;
    }

    public String getIncitemid() {
        return incitemid;
    }

    public void setIncitemid(String incitemid) {
        this.incitemid = incitemid;
    }

    public String getStuIncitemName() {
        return stuIncitemName;
    }

    public void setStuIncitemName(String stuIncitemName) {
        this.stuIncitemName = stuIncitemName;
    }

    public double getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(double chargemoney) {
        this.chargemoney = chargemoney;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }
}
