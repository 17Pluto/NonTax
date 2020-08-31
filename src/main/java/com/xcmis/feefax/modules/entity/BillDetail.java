package com.xcmis.feefax.modules.entity;

public class BillDetail extends CommonEntity {

    private String untaxBillnameId;
    private String billNo;
    private String state;
    private String enId;
    private String mainId;
    private String billdistributer;
    private String isEndReport;

    private String untaxBillname;

    private String bgnBillNo;
    private String endBillNo;

    private int packnum;
    private int billLength;

    private String isRecovery;

    public String getIsRecovery() {
        return isRecovery;
    }

    public void setIsRecovery(String isRecovery) {
        this.isRecovery = isRecovery;
    }

    public int getBillLength() {
        return billLength;
    }

    public void setBillLength(int billLength) {
        this.billLength = billLength;
    }

    public int getPacknum() {
        return packnum;
    }

    public void setPacknum(int packnum) {
        this.packnum = packnum;
    }

    public String getBgnBillNo() {
        return bgnBillNo;
    }

    public void setBgnBillNo(String bgnBillNo) {
        this.bgnBillNo = bgnBillNo;
    }

    public String getEndBillNo() {
        return endBillNo;
    }

    public void setEndBillNo(String endBillNo) {
        this.endBillNo = endBillNo;
    }

    public String getUntaxBillname() {
        return untaxBillname;
    }

    public void setUntaxBillname(String untaxBillname) {
        this.untaxBillname = untaxBillname;
    }

    public String getBilldistributer() {
        return billdistributer;
    }

    public void setBilldistributer(String billdistributer) {
        this.billdistributer = billdistributer;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }

    public String getUntaxBillnameId() {
        return untaxBillnameId;
    }

    public void setUntaxBillnameId(String untaxBillnameId) {
        this.untaxBillnameId = untaxBillnameId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsEndReport() {
        return isEndReport;
    }

    public void setIsEndReport(String isEndReport) {
        this.isEndReport = isEndReport;
    }
}
