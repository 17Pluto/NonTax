package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * @Author fangzhe
 * @Date 2020/6/19 3:27 下午
 * @Version 1.0
 */
public class EduImportStuInfo {

    private String chrId;
    private int isDeleted;
    private String createDate;
    private String createUser;
    private String latestOpDate;
    private String remark;
    private String latestOpUser;
    private String rgCode;
    private String stuName;
    private String stuBirth;
    private String stuId;
    private int stuSex;
    private String stuIdentity;
    private String stuInserttime;
    private String stuGrade;
    private int stuClass;
    private double stuChargemoney;
    private String stuIncitemName;
    private String batchid;
    private String isCollection;
    private String stateCode;
    private String enId;
    private String pmId;
    private String billtypeId;
    private String receiverid;
    private String reqBillNo;
    private String billNo;
    private String stuDegree;
    private String receiver;
    private String receiveraccount;
    private String receiverbank;
    private String qrCode;


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiveraccount() {
        return receiveraccount;
    }

    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = receiveraccount;
    }

    public String getReceiverbank() {
        return receiverbank;
    }

    public void setReceiverbank(String receiverbank) {
        this.receiverbank = receiverbank;
    }

    public String getStuDegree() {
        return stuDegree;
    }

    public void setStuDegree(String stuDegree) {
        this.stuDegree = stuDegree;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getReqBillNo() {
        return reqBillNo;
    }

    public void setReqBillNo(String reqBillNo) {
        this.reqBillNo = reqBillNo;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public String getStuBirth() {
        return stuBirth;
    }

    public void setStuBirth(String stuBirth) {
        this.stuBirth = stuBirth;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLatestOpDate() {
        return latestOpDate;
    }

    public void setLatestOpDate(String latestOpDate) {
        this.latestOpDate = latestOpDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLatestOpUser() {
        return latestOpUser;
    }

    public void setLatestOpUser(String latestOpUser) {
        this.latestOpUser = latestOpUser;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public int getStuSex() {
        return stuSex;
    }

    public void setStuSex(int stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuIdentity() {
        return stuIdentity;
    }

    public void setStuIdentity(String stuIdentity) {
        this.stuIdentity = stuIdentity;
    }

    public String getStuInserttime() {
        return stuInserttime;
    }

    public void setStuInserttime(String stuInserttime) {
        this.stuInserttime = stuInserttime;
    }

    public String getStuGrade() {
        return stuGrade;
    }

    public void setStuGrade(String stuGrade) {
        this.stuGrade = stuGrade;
    }

    public int getStuClass() {
        return stuClass;
    }

    public void setStuClass(int stuClass) {
        this.stuClass = stuClass;
    }

    public double getStuChargemoney() {
        return stuChargemoney;
    }

    public void setStuChargemoney(double stuChargemoney) {
        this.stuChargemoney = stuChargemoney;
    }

    public String getStuIncitemName() {
        return stuIncitemName;
    }

    public void setStuIncitemName(String stuIncitemName) {
        this.stuIncitemName = stuIncitemName;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    private List<EduImportStuInfoDetail> eduImportStuInfoDetailList;

    public List<EduImportStuInfoDetail> getEduImportStuInfoDetailList() {
        return eduImportStuInfoDetailList;
    }

    public void setEduImportStuInfoDetailList(List<EduImportStuInfoDetail> eduImportStuInfoDetailList) {
        this.eduImportStuInfoDetailList = eduImportStuInfoDetailList;
    }
}
