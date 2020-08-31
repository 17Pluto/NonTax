package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * @author fangzhe
 * @date 2020-03-26
 * @UPDATE 2020-03-29
 * BillInStoore entity
 */
public class BillInStore extends CommonEntity{
    //数据库表：UNTAX_BILLINSTORE
    private String billserialNo;
    private String makeShanRenName;
    private String makeShanRenId;
    private String makeShanDate;
    private String enId;
    private String untaxBillputtypeId;
    private String untaxBillsourceId;
    private String deliverNo;
    private int orderNum;
    private String remark;
    private String belongOrg;
    private String orgType;
    private String instoreDate;

    private String startInstoreDate;
    private String endInstoreDate;

    private String rcid;
    private int isend;
    private int isBalance;
    private int isBillBack;
    private String userName;
    private String stateCode;


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

    public String getStartInstoreDate() {
        return startInstoreDate;
    }

    public void setStartInstoreDate(String startInstoreDate) {
        this.startInstoreDate = startInstoreDate;
    }

    public String getEndInstoreDate() {
        return endInstoreDate;
    }

    public void setEndInstoreDate(String endInstoreDate) {
        this.endInstoreDate = endInstoreDate;
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

    private List<BillInStoreList> billInStoreList;

    public List<BillInStoreList> getBillInStoreList() {
        return billInStoreList;
    }

    public void setBillInStoreList(List<BillInStoreList> billInStoreList) {
        this.billInStoreList = billInStoreList;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getInstoreDate() {
        return instoreDate;
    }

    public void setInstoreDate(String instoreDate) {
        this.instoreDate = instoreDate;
    }

    public String getRcid() {
        return rcid;
    }

    public void setRcid(String rcid) {
        this.rcid = rcid;
    }

    public int getIsend() {
        return isend;
    }

    public void setIsend(int isend) {
        this.isend = isend;
    }

    public int getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(int isBalance) {
        this.isBalance = isBalance;
    }

    public int getIsBillBack() {
        return isBillBack;
    }

    public void setIsBillBack(int isBillBack) {
        this.isBillBack = isBillBack;
    }

    public String getBillserialNo() {
        return billserialNo;
    }

    public void setBillserialNo(String billserialNo) {
        this.billserialNo = billserialNo;
    }

    public String getMakeShanRenName() {
        return makeShanRenName;
    }

    public void setMakeShanRenName(String makeShanRenName) {
        this.makeShanRenName = makeShanRenName;
    }

    public String getMakeShanRenId() {
        return makeShanRenId;
    }

    public void setMakeShanRenId(String makeShanRenId) {
        this.makeShanRenId = makeShanRenId;
    }

    public String getMakeShanDate() {
        return makeShanDate;
    }

    public void setMakeShanDate(String makeShanDate) {
        this.makeShanDate = makeShanDate;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }

    public String getUntaxBillputtypeId() {
        return untaxBillputtypeId;
    }

    public void setUntaxBillputtypeId(String untaxBillputtypeId) {
        this.untaxBillputtypeId = untaxBillputtypeId;
    }

    public String getUntaxBillsourceId() {
        return untaxBillsourceId;
    }

    public void setUntaxBillsourceId(String untaxBillsourceId) {
        this.untaxBillsourceId = untaxBillsourceId;
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongOrg() {
        return belongOrg;
    }

    public void setBelongOrg(String belongOrg) {
        this.belongOrg = belongOrg;
    }
}
