package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * 功能
 *
 * @author
 * @see
 */
public class BillEndReport extends CommonEntity{
    private String billserialNo;
    private String makemanId;
    private String makemanName;
    private String makemanDate;
    private String enId;
    private int orderNum;
    private String remark;
    private String rcid;
    private int isend;
    private int dataSource;
    private String stateCode;
    private String untaxBilltypeId;

    private String enName;
    private String userName;

    private String beginCreateDate;
    private String endCreateDate;

    public String getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(String beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUntaxBilltypeId() {
        return untaxBilltypeId;
    }

    public void setUntaxBilltypeId(String untaxBilltypeId) {
        this.untaxBilltypeId = untaxBilltypeId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    private List<BillEndReportDetail> billEndReportDetailList;

    public List<BillEndReportDetail> getBillEndReportDetailList() {
        return billEndReportDetailList;
    }

    public void setBillEndReportDetailList(List<BillEndReportDetail> billEndReportDetailList) {
        this.billEndReportDetailList = billEndReportDetailList;
    }

    public String getBillserialNo() {
        return billserialNo;
    }

    public void setBillserialNo(String billserialNo) {
        this.billserialNo = billserialNo;
    }

    public String getMakemanId() {
        return makemanId;
    }

    public void setMakemanId(String makemanId) {
        this.makemanId = makemanId;
    }

    public String getMakemanName() {
        return makemanName;
    }

    public void setMakemanName(String makemanName) {
        this.makemanName = makemanName;
    }

    public String getMakemanDate() {
        return makemanDate;
    }

    public void setMakemanDate(String makemanDate) {
        this.makemanDate = makemanDate;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
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

    public int getDataSource() {
        return dataSource;
    }

    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }
}
