package com.xcmis.feefax.modules.entity;

/**
 * 功能
 *
 * @author
 * @see
 */
public class BillEndReportDetail extends CommonEntity{
    private String mainId;
    private String untaxBilltypeId;
    private String bgnBillNo;
    private String endBillNo;
    private int storeNum;
    private String remark;
    private int reportType;
    private double money;
    private String checked;
    private String occurtime;

    private String untaxBillname;

    public String getUntaxBillname() {
        return untaxBillname;
    }

    public void setUntaxBillname(String untaxBillname) {
        this.untaxBillname = untaxBillname;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getUntaxBilltypeId() {
        return untaxBilltypeId;
    }

    public void setUntaxBilltypeId(String untaxBilltypeId) {
        this.untaxBilltypeId = untaxBilltypeId;
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

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getOccurtime() {
        return occurtime;
    }

    public void setOccurtime(String occurtime) {
        this.occurtime = occurtime;
    }
}
