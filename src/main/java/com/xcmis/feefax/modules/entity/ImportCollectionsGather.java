package com.xcmis.feefax.modules.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author fangzhe
 * @Date 2020/6/16 5:47 下午
 * @Version 1.0
 */
public class ImportCollectionsGather implements Serializable{

    private String billNo;
    private String billtypeId;
    private String enId;
    private String pmId;
    private String receiver;
    private String receiveraccount;
    private String receiverbank;
    private String receiverid;
    private String receivetype;
    private String errorMsg;
    private String makedate;
    private String payer;
    private String payeraccount;
    private String payerbank;
    private String remark;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
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

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getReceivetype() {
        return receivetype;
    }

    public void setReceivetype(String receivetype) {
        this.receivetype = receivetype;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayeraccount() {
        return payeraccount;
    }

    public void setPayeraccount(String payeraccount) {
        this.payeraccount = payeraccount;
    }

    public String getPayerbank() {
        return payerbank;
    }

    public void setPayerbank(String payerbank) {
        this.payerbank = payerbank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    private  List<CollectionsGather> collectionsGatherList;

    public List<CollectionsGather> getCollectionsGatherList() {
        return collectionsGatherList;
    }

    public void setCollectionsGatherList(List<CollectionsGather> collectionsGatherList) {
        this.collectionsGatherList = collectionsGatherList;
    }

    private List<CollectionsGatherDetail> collectionsGatherDetailList;

    public List<CollectionsGatherDetail> getCollectionsGatherDetailList(){return collectionsGatherDetailList;}

    public void setCollectionsGatherDetailList(List<CollectionsGatherDetail> collectionsGatherDetailList) {
        this.collectionsGatherDetailList = collectionsGatherDetailList;
    }
}
