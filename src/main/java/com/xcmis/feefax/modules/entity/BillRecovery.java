package com.xcmis.feefax.modules.entity;

import java.util.List;

public class BillRecovery extends CommonEntity{

    private String chrId;
    private String createDate;
    private String createUser;
    private String latestOpDate;
    private int isDeleted;
    private String latestOpUser;
    private String rgCode;
    private String billserialNo;
    private String enId;
    private String receiver;
    private int orderNum;
    private String remark;
    private String belongOrg;
    private String orgType;
    private String putsaleDate;
    private String rcid;
    private int isend;
    private String collectId;
    private int isSend;
    private int receivetype;
    private int printflag;
    private String printdate;
    private String untaxReceivebookId;
    private int areaSource;
    private int isBillBack;

    private String enName;
    private String userName;

    private String stateCode;

    private String startPutsaleDate;

    private String endPutsaleDate;


    private String untaxBillnameId;
    private String bgnBillNo;
    private String endBillNo;

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

    public String getStartPutsaleDate() {
        return startPutsaleDate;
    }

    public void setStartPutsaleDate(String startPutsaleDate) {
        this.startPutsaleDate = startPutsaleDate;
    }

    public String getEndPutsaleDate() {
        return endPutsaleDate;
    }

    public void setEndPutsaleDate(String endPutsaleDate) {
        this.endPutsaleDate = endPutsaleDate;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private List<BillRecoveryList> billRecoveryList;


    public List<BillRecoveryList> getBillRecoveryList() {
        return billRecoveryList;
    }

    public void setBillRecoveryList(List<BillRecoveryList> billRecoveryList) {
        this.billRecoveryList = billRecoveryList;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public int getIsBillBack() {
        return isBillBack;
    }

    public void setIsBillBack(int isBillBack) {
        this.isBillBack = isBillBack;
    }

    public int getAreaSource() {
        return areaSource;
    }

    public void setAreaSource(int areaSource) {
        this.areaSource = areaSource;
    }

    public String getUntaxReceivebookId() {
        return untaxReceivebookId;
    }

    public void setUntaxReceivebookId(String untaxReceivebookId) {
        this.untaxReceivebookId = untaxReceivebookId;
    }

    public String getPrintdate() {
        return printdate;
    }

    public void setPrintdate(String printdate) {
        this.printdate = printdate;
    }

    public int getPrintflag() {
        return printflag;
    }

    public void setPrintflag(int printflag) {
        this.printflag = printflag;
    }

    public int getReceivetype() {
        return receivetype;
    }

    public void setReceivetype(int receivetype) {
        this.receivetype = receivetype;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
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

    public String getPutsaleDate() {
        return putsaleDate;
    }

    public void setPutsaleDate(String putsaleDate) {
        this.putsaleDate = putsaleDate;
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

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

}
