package com.xcmis.feefax.modules.entity;


public class BillName extends CommonEntity  {
    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public String getBillKind() {
        return billKind;
    }

    public void setBillKind(String billKind) {
        this.billKind = billKind;
    }

    public String getBillKindId() {
        return billKindId;
    }

    public void setBillKindId(String billKindId) {
        this.billKindId = billKindId;
    }

    public String getPrickle() {
        return prickle;
    }

    public void setPrickle(String prickle) {
        this.prickle = prickle;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public int getUnitCouplet() {
        return unitCouplet;
    }

    public void setUnitCouplet(int unitCouplet) {
        this.unitCouplet = unitCouplet;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public String getUsageWay() {
        return usageWay;
    }

    public void setUsageWay(String usageWay) {
        this.usageWay = usageWay;
    }

    public int getBillValue() {
        return billValue;
    }

    public void setBillValue(int billValue) {
        this.billValue = billValue;
    }

    public int getBillLength() {
        return billLength;
    }

    public void setBillLength(int billLength) {
        this.billLength = billLength;
    }

    public int getVerifyLength() {
        return verifyLength;
    }

    public void setVerifyLength(int verifyLength) {
        this.verifyLength = verifyLength;
    }

    public int getStockUpper() {
        return stockUpper;
    }

    public void setStockUpper(int stockUpper) {
        this.stockUpper = stockUpper;
    }

    public int getStockLower() {
        return stockLower;
    }

    public void setStockLower(int stockLower) {
        this.stockLower = stockLower;
    }

    public int getStrict() {
        return strict;
    }

    public void setStrict(int strict) {
        this.strict = strict;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getInforows() {
        return inforows;
    }

    public void setInforows(int inforows) {
        this.inforows = inforows;
    }

    public int getRation() {
        return ration;
    }

    public void setRation(int ration) {
        this.ration = ration;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPacknum() {
        return packnum;
    }

    public void setPacknum(int packnum) {
        this.packnum = packnum;
    }

    public int getCanusemonths() {
        return canusemonths;
    }

    public void setCanusemonths(int canusemonths) {
        this.canusemonths = canusemonths;
    }

    public String getIncitemid() {
        return incitemid;
    }

    public void setIncitemid(String incitemid) {
        this.incitemid = incitemid;
    }

    public String getBillspec() {
        return billspec;
    }

    public void setBillspec(String billspec) {
        this.billspec = billspec;
    }

    public int getIsnotice() {
        return isnotice;
    }

    public void setIsnotice(int isnotice) {
        this.isnotice = isnotice;
    }

    public int getIsmanage() {
        return ismanage;
    }

    public void setIsmanage(int ismanage) {
        this.ismanage = ismanage;
    }

    public int getIsprepay() {
        return isprepay;
    }

    public void setIsprepay(int isprepay) {
        this.isprepay = isprepay;
    }

    public String getBillRule() {
        return billRule;
    }

    public void setBillRule(String billRule) {
        this.billRule = billRule;
    }

    private String parentValue;
    private String billKind;
    private String billKindId;
    private String prickle;
    private int unitNumber;
    private int unitCouplet;
    private int direct;
    private String usageWay;
    private int billValue;
    private int billLength;
    private int verifyLength;
    private int stockUpper;
    private int stockLower;
    private int strict;
    private String remark;
    private int inforows;
    private int ration;
    private String track;
    private String reportId;
    private String endDate;
    private int packnum;
    private int canusemonths;
    private String incitemid;
    private String billspec;
    private int isnotice;
    private int ismanage;
    private int isprepay;
    private String billRule;

    private String mainId;

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
