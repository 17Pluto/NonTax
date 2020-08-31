package com.xcmis.feefax.modules.entity;
/**
 * @author fangzhe
 * @date 2020-04-01
 *  entity
 *
 */
public class CollectionsDetail extends CommonEntity {
    private String chrId;
    private String mainId;
    private String incitemid;
    private String measure;
    private double chargenum;
    private String chargestandard;
    private double chargemoney;
    private String unititemId;
    private String itemChkcode;
    private int checkFlag;
    private String lastVer;
    private String paybillId;
    private int isPay;
    private String onePrice;
    private String incitemName;
    private String incitemCode;
    private String taxNo;

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

    public String getIncitemName() {
        return incitemName;
    }

    public void setIncitemName(String incitemName) {
        this.incitemName = incitemName;
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

    public String getItemChkcode() {
        return itemChkcode;
    }

    public void setItemChkcode(String itemChkcode) {
        this.itemChkcode = itemChkcode;
    }

    public int getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Override
    public String getLastVer() {
        return lastVer;
    }

    @Override
    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getPaybillId() {
        return paybillId;
    }

    public void setPaybillId(String paybillId) {
        this.paybillId = paybillId;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(String onePrice) {
        this.onePrice = onePrice;
    }

    public double getChargenum() {
        return chargenum;
    }

    public void setChargenum(double chargenum) {
        this.chargenum = chargenum;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIncitemid() {
        return incitemid;
    }

    public void setIncitemid(String incitemid) {
        this.incitemid = incitemid;
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
