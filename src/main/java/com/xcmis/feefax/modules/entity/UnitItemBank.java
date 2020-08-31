package com.xcmis.feefax.modules.entity;

public class UnitItemBank extends CommonEntity{
    private String chrId;
    private String account;
    private String accountName;
    private String mainId;
    private String bankId;
    private String orderNum;
    private String accountId;
    private String lastVer;


    private String vurChrName;
    private String vurChrCode;
    private String vurAccountNo;
    private String vurAccountName;


    private String ienId;
    private String billtypeId;

    public String getIenId() {
        return ienId;
    }

    public void setIenId(String ienId) {
        this.ienId = ienId;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    public String getVurChrName() {
        return vurChrName;
    }

    public void setVurChrName(String vurChrName) {
        this.vurChrName = vurChrName;
    }

    public String getVurChrCode() {
        return vurChrCode;
    }

    public void setVurChrCode(String vurChrCode) {
        this.vurChrCode = vurChrCode;
    }

    public String getVurAccountNo() {
        return vurAccountNo;
    }

    public void setVurAccountNo(String vurAccountNo) {
        this.vurAccountNo = vurAccountNo;
    }

    public String getVurAccountName() {
        return vurAccountName;
    }

    public void setVurAccountName(String vurAccountName) {
        this.vurAccountName = vurAccountName;
    }

    @Override
    public String getLastVer() {
        return lastVer;
    }

    @Override
    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
