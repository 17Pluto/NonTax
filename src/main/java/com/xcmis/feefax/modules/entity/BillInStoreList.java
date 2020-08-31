package com.xcmis.feefax.modules.entity;

/**
 * @author fangzhe
 * @date 2020-03-29
 * @UPDATE 2020-03-29
 * BillInStooreList entity
 */

public class BillInStoreList extends CommonEntity {

    private String chrId;
    private String mainId;
    private String untaxBillnameId;
    private String untaxBillpriceId;
    private String measure;
    private String muls;
    private double price;
    private String bgnBillNo;
    private String endBillNo;
    private int storeNum;
    private String remark;
    private double money;
    private int isDeleted;
    private int booknum;

    private String untaxBillname;

    private int packnum;
    private int billLength;

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

    public String getUntaxBillname() {
        return untaxBillname;
    }

    public void setUntaxBillname(String untaxBillname) {
        this.untaxBillname = untaxBillname;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getIsBillBack() {
        return isBillBack;
    }

    public void setIsBillBack(int isBillBack) {
        this.isBillBack = isBillBack;
    }

    public String getCanusedate() {
        return canusedate;
    }

    public void setCanusedate(String canusedate) {
        this.canusedate = canusedate;
    }

    public int getBooknum() {
        return booknum;
    }

    public void setBooknum(int booknum) {
        this.booknum = booknum;
    }

    @Override
    public int getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getEndBillNo() {
        return endBillNo;
    }

    public void setEndBillNo(String endBillNo) {
        this.endBillNo = endBillNo;
    }

    public String getBgnBillNo() {
        return bgnBillNo;
    }

    public void setBgnBillNo(String bgnBillNo) {
        this.bgnBillNo = bgnBillNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMuls() {
        return muls;
    }

    public void setMuls(String muls) {
        this.muls = muls;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getUntaxBillpriceId() {
        return untaxBillpriceId;
    }

    public void setUntaxBillpriceId(String untaxBillpriceId) {
        this.untaxBillpriceId = untaxBillpriceId;
    }

    public String getUntaxBillnameId() {
        return untaxBillnameId;
    }

    public void setUntaxBillnameId(String untaxBillnameId) {
        this.untaxBillnameId = untaxBillnameId;
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

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    private String occurTime;
    private String canusedate;
    private int isBillBack;
    private String sourceId;





}
