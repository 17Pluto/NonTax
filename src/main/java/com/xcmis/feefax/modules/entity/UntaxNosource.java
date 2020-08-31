package com.xcmis.feefax.modules.entity;

import java.util.List;

/**
 * @Author: 方哲
 * @Date：2020-05-18
 * 不明款项1entity
 */

public class UntaxNosource {

    public UntaxNosource() {

    }
    private String billName;
    private String billNo;
    private String chargemoney;

    private String collectionId;
    private String enName1;
    private int isDeleted;
    //批次号
    private String batchno;
    //查询时间（from xxx to xxx）
    private String startEventtime;
    private String endEventtime;

    //页面状态码
    private String stateCode;
    //查询的金额
    private String searchmoney;
    //待查收入ID
    private String chrId;
    //缴款人名称
    private String payer;
    //缴款人开户行
    private String payerbank;
    //缴款账号
    private String payeraccount;
    //收款人ID
    private String receiverid;
    //收款账户名称
    private String receiver;
    //收款开户行
    private String receiverbank;
    //收款账号
    private String receiveraccount;
    //交易时间
    private String eventtime;
    //交易地点
    private String eventaddress;
    //缴款方式
    private String pmId;
    //金额
    private double checkmoney;
    //备注
    private String remark;
    //记账标记
    private int tallytag;
    //是否绑定或者生成缴款书
    private int isCollect;
    private String createUser;
    private String createDate;
    private String bankNo;
    private int isSend;
    private String lastVer;
    private String rgCode;
    private String setYear;
    private int isEnd;
    private String enId;
    private String enCode;
    private String enName;
    //是否走工作流，默认保存时为0
    private int isWf;
    //是否登记（0：未登记；1：已登记）
    private int confirm;
    private int checkearne;
    private int eraseflag;
    //是否认领（0：未认领；1：认领；2：退付）
    private int isClaim;
    //认领单号
    private String tBillNo;
    //认领备注
    private String remark1;
    //退回备注
    private String remark2;
    //国库登记：1=已登记，0=未登记，国库过账使用
    private int confirmend;
    //核收登记日期
    private String checkearnedate;
    //16位电子缴款码，全国唯一
    private String paycode;
    //支付交易序号
    private String paytraseqno;
    //备注
    private String memo;
    //收款人账户类型(1:财政专户,2：汇缴专户)
    private String recaccttype;
    //拟退付暂存款的流水号，要与2603报文中的流水号一致
    private String deptrano;
    //银行网点编码
    private String bankoutlet;
    private String sourceId;
    //绑定单位ID
    private String bindEnId;
    //是否财政已审核（0：未审核；1：已审核）
    private int isAudit;
    private int isPay;
    private String reqBillNo;

    private List<String> enIds;

    public String getReqBillNo() {
        return reqBillNo;
    }

    public void setReqBillNo(String reqBillNo) {
        this.reqBillNo = reqBillNo;
    }

    public List<String>getEnIds() {
        return enIds;
    }

    public void setEnIds(List<String> enIds) {
        this.enIds = enIds;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(String chargemoney) {
        this.chargemoney = chargemoney;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getEnName1() {
        return enName1;
    }

    public void setEnName1(String enName1) {
        this.enName1 = enName1;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getStartEventtime() {
        return startEventtime;
    }

    public void setStartEventtime(String startEventtime) {
        this.startEventtime = startEventtime;
    }

    public String getEndEventtime() {
        return endEventtime;
    }

    public void setEndEventtime(String endEventtime) {
        this.endEventtime = endEventtime;
    }

    public String getSearchmoney() {
        return searchmoney;
    }

    public void setSearchmoney(String searchmoney) {
        this.searchmoney = searchmoney;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }


    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }


    public String getPayerbank() {
        return payerbank;
    }

    public void setPayerbank(String payerbank) {
        this.payerbank = payerbank;
    }


    public String getPayeraccount() {
        return payeraccount;
    }

    public void setPayeraccount(String payeraccount) {
        this.payeraccount = payeraccount;
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


    public String getReceiverbank() {
        return receiverbank;
    }

    public void setReceiverbank(String receiverbank) {
        this.receiverbank = receiverbank;
    }


    public String getReceiveraccount() {
        return receiveraccount;
    }

    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = receiveraccount;
    }


    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }


    public String getEventaddress() {
        return eventaddress;
    }

    public void setEventaddress(String eventaddress) {
        this.eventaddress = eventaddress;
    }


    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }


    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }


    public String getSetYear() {
        return setYear;
    }

    public void setSetYear(String setYear) {
        this.setYear = setYear;
    }


    public String getEnId() {
        return enId;
    }

    public void setEnId(String enId) {
        this.enId = enId;
    }


    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }


    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getTBillNo() {
        return tBillNo;
    }

    public void setTBillNo(String tBillNo) {
        this.tBillNo = tBillNo;
    }


    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }


    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getCheckearnedate() {
        return checkearnedate;
    }

    public void setCheckearnedate(String checkearnedate) {
        this.checkearnedate = checkearnedate;
    }


    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }


    public String getPaytraseqno() {
        return paytraseqno;
    }

    public void setPaytraseqno(String paytraseqno) {
        this.paytraseqno = paytraseqno;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getRecaccttype() {
        return recaccttype;
    }

    public void setRecaccttype(String recaccttype) {
        this.recaccttype = recaccttype;
    }


    public String getDeptrano() {
        return deptrano;
    }

    public void setDeptrano(String deptrano) {
        this.deptrano = deptrano;
    }


    public String getBankoutlet() {
        return bankoutlet;
    }

    public void setBankoutlet(String bankoutlet) {
        this.bankoutlet = bankoutlet;
    }


    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }


    public String getBindEnId() {
        return bindEnId;
    }

    public void setBindEnId(String bindEnId) {
        this.bindEnId = bindEnId;
    }

    public double getCheckmoney() {
        return checkmoney;
    }

    public void setCheckmoney(double checkmoney) {
        this.checkmoney = checkmoney;
    }

    public int getTallytag() {
        return tallytag;
    }

    public void setTallytag(int tallytag) {
        this.tallytag = tallytag;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public int getIsWf() {
        return isWf;
    }

    public void setIsWf(int isWf) {
        this.isWf = isWf;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public int getCheckearne() {
        return checkearne;
    }

    public void setCheckearne(int checkearne) {
        this.checkearne = checkearne;
    }

    public int getEraseflag() {
        return eraseflag;
    }

    public void setEraseflag(int eraseflag) {
        this.eraseflag = eraseflag;
    }

    public int getIsClaim() {
        return isClaim;
    }

    public void setIsClaim(int isClaim) {
        this.isClaim = isClaim;
    }

    public String gettBillNo() {
        return tBillNo;
    }

    public void settBillNo(String tBillNo) {
        this.tBillNo = tBillNo;
    }

    public int getConfirmend() {
        return confirmend;
    }

    public void setConfirmend(int confirmend) {
        this.confirmend = confirmend;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }
}
