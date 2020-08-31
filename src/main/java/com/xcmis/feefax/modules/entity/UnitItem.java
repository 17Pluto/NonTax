package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * @author fangzhe
 * @date 2020-03-25
 * 执收单位收入项目entity
 */
public class UnitItem extends CommonEntity{
    private String itemId;
    private String ienId;
    private String accountId;
    private String accountBank;
    private String account;
    private String accountName;
    private String bsId;
    private String mkId;
    private String beginTime;
    private String endTime;
    private String inBsId;
    private double uplimit;
    private double lowlimit;
    private String rcid;
    private double isend;
    private double feestandardtype;
    private String chargekindId;
    private double enableUsedate;
    private String verifyNo;
    private String accflag;
    private String inAdId;
    private String fileNo;
    private String incode;
    private String inItematId;
    private String lastestOpUser;
    private String lastestOpDate;


    private String eiiChrCode;
    private String eeChrCode;
    private String eeName;
    private String eiiChrName;
    private String chargekindName;
    private String itemsortName;

    private String billtypeId;
    private String compareDate;

    public String getCompareDate() {
        return compareDate;
    }

    public void setCompareDate(String compareDate) {
        this.compareDate = compareDate;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    private IncomeItem incomeItem;
    private List<UnitItemBank> unitItemBankList;
    private List<UnitItemBillForm> unitItemBillFormList;
    private List<UnitItemUserInfo> unitItemUserInfoList;
    private List<UnitItemAccredit> unitItemAccreditList;

    public List<UnitItemAccredit> getUnitItemAccreditList() {
        return unitItemAccreditList;
    }

    public void setUnitItemAccreditList(List<UnitItemAccredit> unitItemAccreditList) {
        this.unitItemAccreditList = unitItemAccreditList;
    }

    private String ienIdAll;
    private String mkIdAll;
    private String itemIdAll;
    private String inBsIdAll;
    private String chargekindIdAll;


    private String unitId;
    private String stateCode;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public IncomeItem getIncomeItem() {
        return incomeItem;
    }

    public void setIncomeItem(IncomeItem incomeItem) {
        this.incomeItem = incomeItem;
    }

    public String getIenIdAll() {
        return ienIdAll;
    }

    public void setIenIdAll(String ienIdAll) {
        this.ienIdAll = ienIdAll;
    }

    public String getMkIdAll() {
        return mkIdAll;
    }

    public void setMkIdAll(String mkIdAll) {
        this.mkIdAll = mkIdAll;
    }

    public String getItemIdAll() {
        return itemIdAll;
    }

    public void setItemIdAll(String itemIdAll) {
        this.itemIdAll = itemIdAll;
    }

    public String getInBsIdAll() {
        return inBsIdAll;
    }

    public void setInBsIdAll(String inBsIdAll) {
        this.inBsIdAll = inBsIdAll;
    }

    public String getChargekindIdAll() {
        return chargekindIdAll;
    }

    public void setChargekindIdAll(String chargekindIdAll) {
        this.chargekindIdAll = chargekindIdAll;
    }

    public String getEiiChrCode() {
        return eiiChrCode;
    }

    public void setEiiChrCode(String eiiChrCode) {
        this.eiiChrCode = eiiChrCode;
    }

    public String getEeChrCode() {
        return eeChrCode;
    }

    public void setEeChrCode(String eeChrCode) {
        this.eeChrCode = eeChrCode;
    }

    public String getEeName() {
        return eeName;
    }

    public void setEeName(String eeName) {
        this.eeName = eeName;
    }

    public String getEiiChrName() {
        return eiiChrName;
    }

    public void setEiiChrName(String eiiChrName) {
        this.eiiChrName = eiiChrName;
    }

    public String getChargekindName() {
        return chargekindName;
    }

    public void setChargekindName(String chargekindName) {
        this.chargekindName = chargekindName;
    }

    public String getItemsortName() {
        return itemsortName;
    }

    public void setItemsortName(String itemsortName) {
        this.itemsortName = itemsortName;
    }

    public String getLastestOpUser() {
        return lastestOpUser;
    }

    public void setLastestOpUser(String lastestOpUser) {
        this.lastestOpUser = lastestOpUser;
    }

    public String getLastestOpDate() {
        return lastestOpDate;
    }

    public void setLastestOpDate(String lastestOpDate) {
        this.lastestOpDate = lastestOpDate;
    }

    public List<UnitItemBank> getUnitItemBankList() {
        return unitItemBankList;
    }

    public void setUnitItemBankList(List<UnitItemBank> unitItemBankList) {
        this.unitItemBankList = unitItemBankList;
    }

    public List<UnitItemBillForm> getUnitItemBillFormList() {
        return unitItemBillFormList;
    }

    public void setUnitItemBillFormList(List<UnitItemBillForm> unitItemBillFormList) {
        this.unitItemBillFormList = unitItemBillFormList;
    }

    public List<UnitItemUserInfo> getUnitItemUserInfoList() {
        return unitItemUserInfoList;
    }

    public void setUnitItemUserInfoList(List<UnitItemUserInfo> unitItemUserInfoList) {
        this.unitItemUserInfoList = unitItemUserInfoList;
    }

    public String getInItematId() {
        return inItematId;
    }

    public void setInItematId(String inItematId) {
        this.inItematId = inItematId;
    }

    public String getIncode() {
        return incode;
    }

    public void setIncode(String incode) {
        this.incode = incode;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getInAdId() {
        return inAdId;
    }

    public void setInAdId(String inAdId) {
        this.inAdId = inAdId;
    }

    public String getAccflag() {
        return accflag;
    }

    public void setAccflag(String accflag) {
        this.accflag = accflag;
    }

    public String getVerifyNo() {
        return verifyNo;
    }

    public void setVerifyNo(String verifyNo) {
        this.verifyNo = verifyNo;
    }

    public double getEnableUsedate() {
        return enableUsedate;
    }

    public void setEnableUsedate(double enableUsedate) {
        this.enableUsedate = enableUsedate;
    }

    public String getChargekindId() {
        return chargekindId;
    }

    public void setChargekindId(String chargekindId) {
        this.chargekindId = chargekindId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getFeestandardtype() {
        return feestandardtype;
    }

    public void setFeestandardtype(double feestandardtype) {
        this.feestandardtype = feestandardtype;
    }

    public double getIsend() {
        return isend;
    }

    public void setIsend(double isend) {
        this.isend = isend;
    }

    public String getRcid() {
        return rcid;
    }

    public void setRcid(String rcid) {
        this.rcid = rcid;
    }

    public double getLowlimit() {
        return lowlimit;
    }

    public void setLowlimit(double lowlimit) {
        this.lowlimit = lowlimit;
    }

    public double getUplimit() {
        return uplimit;
    }

    public void setUplimit(double uplimit) {
        this.uplimit = uplimit;
    }

    public String getInBsId() {
        return inBsId;
    }

    public void setInBsId(String inBsId) {
        this.inBsId = inBsId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getMkId() {
        return mkId;
    }

    public void setMkId(String mkId) {
        this.mkId = mkId;
    }

    public String getBsId() {
        return bsId;
    }

    public void setBsId(String bsId) {
        this.bsId = bsId;
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

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getIenId() {
        return ienId;
    }

    public void setIenId(String ienId) {
        this.ienId = ienId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
