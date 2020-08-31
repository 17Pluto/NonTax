package com.xcmis.feefax.modules.entity;

public class CollectionsGatherDetail extends CommonEntity{

    private String mainId;
    private String incitemid;
    private String measure;
    private int chargenum;
    private String chargestandard;
    private double chargemoney;
    private String unititemId;
    private int isAgent;
    private String onePrice;
    private String incitemName;
    private String incitemCode;
    private String taxNo;

    private String collectId;
    private String stateCode;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getIncitemName() {
        return incitemName;
    }

    public void setIncitemName(String incitemName) {
        this.incitemName = incitemName;
    }

    public String getIncitemCode() {
        return incitemCode;
    }

    public void setIncitemCode(String incitemCode) {
        this.incitemCode = incitemCode;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getIncitemid() {
        return incitemid;
    }

    public void setIncitemid(String incitemid) {
        this.incitemid = incitemid;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getChargenum() {
        return chargenum;
    }

    public void setChargenum(int chargenum) {
        this.chargenum = chargenum;
    }

    public String getChargestandard() {
        return chargestandard;
    }

    public void setChargestandard(String chargestandard) {
        this.chargestandard = chargestandard;
    }

    public double getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(double chargemoney) {
        this.chargemoney = chargemoney;
    }

    public String getUnititemId() {
        return unititemId;
    }

    public void setUnititemId(String unititemId) {
        this.unititemId = unititemId;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public String getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(String onePrice) {
        this.onePrice = onePrice;
    }
}
