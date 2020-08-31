package com.xcmis.feefax.modules.entity;

public class UntaxCoDetailHistory {

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
  private String taxNo;

  /**
   * 新增保存收入项目名称
   */
  private String incitemName;


  public String getChrId() {
    return chrId;
  }

  public void setChrId(String chrId) {
    this.chrId = chrId;
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

  public double getChargenum() {
    return chargenum;
  }

  public void setChargenum(double chargenum) {
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

  public String getLastVer() {
    return lastVer;
  }

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

  public String getTaxNo() {
    return taxNo;
  }

  public void setTaxNo(String taxNo) {
    this.taxNo = taxNo;
  }

  public String getIncitemName() {
    return incitemName;
  }

  public void setIncitemName(String incitemName) {
    this.incitemName = incitemName;
  }
}
