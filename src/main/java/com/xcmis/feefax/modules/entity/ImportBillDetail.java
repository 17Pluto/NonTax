package com.xcmis.feefax.modules.entity;

/**
 * @Author fangzhe
 * @Date 2020/6/15 9:37 上午
 * @Version 1.0
 */
public class ImportBillDetail {

    private String billNameId;
    private String enId;
    private String pmId;
    private String receiverid;
    private String receiver;
    private String receiveraccount;
    private String receiverbank;

    public String getBillNameId() {
        return billNameId;
    }

    public void setBillNameId(String billNameId) {
        this.billNameId = billNameId;
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

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
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
}
