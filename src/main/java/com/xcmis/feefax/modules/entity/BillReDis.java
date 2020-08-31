package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * @author fangzhe
 * @date 2020-03-31
 *
 */

public class BillReDis extends CommonEntity{
    private String setYear;
    private String chrId;
    private String createDate;
    private String createUser;
    private String latestOpDate;
    private int isDeleted;
    private String latestOpUser;
    private String rgCode;
    private String billserialNo;
    private String enId;
    private String billdistributer;
    private double orderNum;
    private String remark;
    private String belongOrg;
    private String orgType;
    private String distributeDate;
    private String rcid;
    private int isend;
    private String lastVer;
    private String isLevelbill;
    private String isFromlevel;
    private int isBillBack;

    private String enName;
    private String userName;

    private String billdistributerName;
    private String stateCode;


    private String startDistributeDate;

    private String endDistributeDate;


    private String untaxBillnameId;
    private String bgnBillNo;
    private String endBillNo;

    public String getStartDistributeDate() {
        return startDistributeDate;
    }

    public void setStartDistributeDate(String startDistributeDate) {
        this.startDistributeDate = startDistributeDate;
    }

    public String getEndDistributeDate() {
        return endDistributeDate;
    }

    public void setEndDistributeDate(String endDistributeDate) {
        this.endDistributeDate = endDistributeDate;
    }

    public String getUntaxBillnameId() {
        return untaxBillnameId;
    }

    public void setUntaxBillnameId(String untaxBillnameId) {
        this.untaxBillnameId = untaxBillnameId;
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

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getBilldistributerName() {
        return billdistributerName;
    }

    public void setBilldistributerName(String billdistributerName) {
        this.billdistributerName = billdistributerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }


    private List<BillReDisList> billReDisList;

    public List<BillReDisList> getBillReDisList() {
        return billReDisList;
    }

    public void setBillReDisList(List<BillReDisList> billReDisList) {
        this.billReDisList = billReDisList;
    }

    public int getIsBillBack() {
        return isBillBack;
    }

    public void setIsBillBack(int isBillBack) {
        this.isBillBack = isBillBack;
    }

    public String getIsFromlevel() {
        return isFromlevel;
    }

    public void setIsFromlevel(String isFromlevel) {
        this.isFromlevel = isFromlevel;
    }

    public String getIsLevelbill() {
        return isLevelbill;
    }

    public void setIsLevelbill(String isLevelbill) {
        this.isLevelbill = isLevelbill;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public int getIsend() {
        return isend;
    }

    public void setIsend(int isend) {
        this.isend = isend;
    }

    public String getRcid() {
        return rcid;
    }

    public void setRcid(String rcid) {
        this.rcid = rcid;
    }

    public String getDistributeDate() {
        return distributeDate;
    }

    public void setDistributeDate(String distributeDate) {
        this.distributeDate = distributeDate;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getBelongOrg() {
        return belongOrg;
    }

    public void setBelongOrg(String belongOrg) {
        this.belongOrg = belongOrg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(double orderNum) {
        this.orderNum = orderNum;
    }

    public String getBilldistributer() {
        return billdistributer;
    }

    public void setBilldistributer(String billdistributer) {
        this.billdistributer = billdistributer;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }

    public String getBillserialNo() {
        return billserialNo;
    }

    public void setBillserialNo(String billserialNo) {
        this.billserialNo = billserialNo;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }

    public String getLatestOpUser() {
        return latestOpUser;
    }

    public void setLatestOpUser(String latestOpUser) {
        this.latestOpUser = latestOpUser;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLatestOpDate() {
        return latestOpDate;
    }

    public void setLatestOpDate(String latestOpDate) {
        this.latestOpDate = latestOpDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getSetYear() {
        return setYear;
    }

    public void setSetYear(String setYear) {
        this.setYear = setYear;
    }
}
