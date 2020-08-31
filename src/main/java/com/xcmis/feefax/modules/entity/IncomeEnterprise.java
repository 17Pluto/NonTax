package com.xcmis.feefax.modules.entity;

public class IncomeEnterprise extends CommonEntity implements Comparable<IncomeEnterprise>{

    private String enId;
    private String indId;
    private String feelicencecode;
    private String unitKind;
    private String unitAddress;
    private String unitPost;
    private String billMan;
    private String billTell;
    private String bursarMan;
    private String bursarTell;
    private String financeMan;
    private String financeTell;
    private String unitMan;
    private String unitTell;
    private String onlybillunit;
    private String organCode;

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOnlybillunit() {
        return onlybillunit;
    }

    public void setOnlybillunit(String onlybillunit) {
        this.onlybillunit = onlybillunit;
    }

    public String getUnitTell() {
        return unitTell;
    }

    public void setUnitTell(String unitTell) {
        this.unitTell = unitTell;
    }

    public String getUnitMan() {
        return unitMan;
    }

    public void setUnitMan(String unitMan) {
        this.unitMan = unitMan;
    }

    public String getFinanceTell() {
        return financeTell;
    }

    public void setFinanceTell(String financeTell) {
        this.financeTell = financeTell;
    }

    public String getFinanceMan() {
        return financeMan;
    }

    public void setFinanceMan(String financeMan) {
        this.financeMan = financeMan;
    }

    public String getBursarTell() {
        return bursarTell;
    }

    public void setBursarTell(String bursarTell) {
        this.bursarTell = bursarTell;
    }

    public String getBursarMan() {
        return bursarMan;
    }

    public void setBursarMan(String bursarMan) {
        this.bursarMan = bursarMan;
    }

    public String getBillTell() {
        return billTell;
    }

    public void setBillTell(String billTell) {
        this.billTell = billTell;
    }

    public String getBillMan() {
        return billMan;
    }

    public void setBillMan(String billMan) {
        this.billMan = billMan;
    }

    public String getUnitPost() {
        return unitPost;
    }

    public void setUnitPost(String unitPost) {
        this.unitPost = unitPost;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getUnitKind() {
        return unitKind;
    }

    public void setUnitKind(String unitKind) {
        this.unitKind = unitKind;
    }

    public String getFeelicencecode() {
        return feelicencecode;
    }

    public void setFeelicencecode(String feelicencecode) {
        this.feelicencecode = feelicencecode;
    }

    public String getIndId() {
        return indId;
    }

    public void setIndId(String indId) {
        this.indId = indId;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }


    @Override
    public int compareTo(IncomeEnterprise o) {
        return this.getChrCode().compareTo(o.getChrCode());
    }
}
