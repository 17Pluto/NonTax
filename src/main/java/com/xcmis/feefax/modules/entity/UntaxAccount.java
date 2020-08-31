package com.xcmis.feefax.modules.entity;

/**
 * author: fangzhe
 * data : 2020-04-16
 */
public class UntaxAccount extends CommonEntity{
    private String accountNo;
    private String accountName;
    private int accountType;
    private String bankId;
    private String bankCode;
    private String bankName;
    private String ienId;
    private int payorreceive;
    private String parentName;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIenId() {
        return ienId;
    }

    public void setIenId(String ienId) {
        this.ienId = ienId;
    }

    public int getPayorreceive() {
        return payorreceive;
    }

    public void setPayorreceive(int payorreceive) {
        this.payorreceive = payorreceive;
    }

    @Override
    public String getParentName() {
        return parentName;
    }

    @Override
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
