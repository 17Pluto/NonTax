package com.xcmis.feefax.modules.entity;
/**
 * author: 方哲
 * date: 2020-04-26
 */

import java.util.List;

public class CollectionsGather extends CommonEntity{
    private String lastestOpUser;
    private String lastestOpDate;
    private String billNo;
    private String verifyBillNo;
    private String verifyNo;
    private String enId;
    private String pmId;
    private String chequeNo;
    private String makedate;
    private String payerid;
    private String payer;
    private String payeraccount;
    private String payerbank;
    private String receiverid;
    private String receiver;
    private String receiveraccount;
    private String receiverbank;
    private int isconsign;
    private String inputername;
    private int printflag;
    private String printdate;
    private int printtimes;
    private int eraseflag;
    private String erasedate;
    private int collectflag;
    private String collectdate;
    private String collectId;
    private int lateflag;
    private String remark;
    private String orgType;
    private String belongOrg;
    private String rcid;
    private int isend;
    private String flowBillNo;
    private String billtypeId;
    private String consignEnId;
    private int switchType;
    private String switchId;
    private int inputflag;
    private int isNotexistsagent;
    private int payflag;
    private String paydate;
    private int erasesure;
    private String lastnanotime;


    private String enName;
    private String enCode;
    private String billName;
    private String payerName;
    private String userName;
    private String pmName;


    private String year;
    private String month;
    private String day;

    private double allmoney;
    private double chargemoney;
    private String allmoneycn;

    private String beginBillNo;
    private String endBillNo;

    private String beginMakedate;
    private String endMakedate;


    private String searchCondition;

    private String untaxBillnameId;

    private String oldBillNo;

    private String incitemid;


    private int returnflag;
    private String returndate;

    private String consigneeEnId;
    private String consigneeEnName;

    private String consigneeEnCode;

    private List<IncomeEnterprise> incomeEnterpriseList;

    public List<IncomeEnterprise> getIncomeEnterpriseList() {
        return incomeEnterpriseList;
    }

    public void setIncomeEnterpriseList(List<IncomeEnterprise> incomeEnterpriseList) {
        this.incomeEnterpriseList = incomeEnterpriseList;
    }

    public String getConsigneeEnCode() {
        return consigneeEnCode;
    }

    public void setConsigneeEnCode(String consigneeEnCode) {
        this.consigneeEnCode = consigneeEnCode;
    }

    public String getReceName() {
        return receName;
    }

    public void setReceName(String receName) {
        this.receName = receName;
    }

    public String getReceAccountNo() {
        return receAccountNo;
    }

    public void setReceAccountNo(String receAccountNo) {
        this.receAccountNo = receAccountNo;
    }

    public String getReceAccountName() {
        return receAccountName;
    }

    public void setReceAccountName(String receAccountName) {
        this.receAccountName = receAccountName;
    }

    public String getReceCode() {
        return receCode;
    }

    public void setReceCode(String receCode) {
        this.receCode = receCode;
    }

    public String getReceBankName() {
        return receBankName;
    }

    public void setReceBankName(String receBankName) {
        this.receBankName = receBankName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private String receName;
    private String receAccountNo;
    private String receAccountName;
    private String receCode;
    private String receBankName;
    private String isbn;

    public String getConsigneeEnName() {
        return consigneeEnName;
    }

    public void setConsigneeEnName(String consigneeEnName) {
        this.consigneeEnName = consigneeEnName;
    }

    public String getConsigneeEnId() {
        return consigneeEnId;
    }

    public void setConsigneeEnId(String consigneeEnId) {
        this.consigneeEnId = consigneeEnId;
    }

    public int getReturnflag() {
        return returnflag;
    }

    public void setReturnflag(int returnflag) {
        this.returnflag = returnflag;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getIncitemid() {
        return incitemid;
    }

    public void setIncitemid(String incitemid) {
        this.incitemid = incitemid;
    }

    public String getBeginBillNo() {
        return beginBillNo;
    }

    public void setBeginBillNo(String beginBillNo) {
        this.beginBillNo = beginBillNo;
    }

    public String getEndBillNo() {
        return endBillNo;
    }

    public void setEndBillNo(String endBillNo) {
        this.endBillNo = endBillNo;
    }

    public String getOldBillNo() {
        return oldBillNo;
    }

    public void setOldBillNo(String oldBillNo) {
        this.oldBillNo = oldBillNo;
    }

    public String getUntaxBillnameId() {
        return untaxBillnameId;
    }

    public void setUntaxBillnameId(String untaxBillnameId) {
        this.untaxBillnameId = untaxBillnameId;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getBeginMakedate() {
        return beginMakedate;
    }

    public void setBeginMakedate(String beginMakedate) {
        this.beginMakedate = beginMakedate;
    }

    public String getEndMakedate() {
        return endMakedate;
    }

    public void setEndMakedate(String endMakedate) {
        this.endMakedate = endMakedate;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(double allmoney) {
        this.allmoney = allmoney;
    }

    public double getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(double chargemoney) {
        this.chargemoney = chargemoney;
    }

    public String getAllmoneycn() {
        return allmoneycn;
    }

    public void setAllmoneycn(String allmoneycn) {
        this.allmoneycn = allmoneycn;
    }

    private String stateCode;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getVerifyBillNo() {
        return verifyBillNo;
    }

    public void setVerifyBillNo(String verifyBillNo) {
        this.verifyBillNo = verifyBillNo;
    }

    public String getVerifyNo() {
        return verifyNo;
    }

    public void setVerifyNo(String verifyNo) {
        this.verifyNo = verifyNo;
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

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getPayerid() {
        return payerid;
    }

    public void setPayerid(String payerid) {
        this.payerid = payerid;
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

    public int getIsconsign() {
        return isconsign;
    }

    public void setIsconsign(int isconsign) {
        this.isconsign = isconsign;
    }

    public String getInputername() {
        return inputername;
    }

    public void setInputername(String inputername) {
        this.inputername = inputername;
    }

    public int getPrintflag() {
        return printflag;
    }

    public void setPrintflag(int printflag) {
        this.printflag = printflag;
    }

    public String getPrintdate() {
        return printdate;
    }

    public void setPrintdate(String printdate) {
        this.printdate = printdate;
    }

    public int getPrinttimes() {
        return printtimes;
    }

    public void setPrinttimes(int printtimes) {
        this.printtimes = printtimes;
    }

    public int getEraseflag() {
        return eraseflag;
    }

    public void setEraseflag(int eraseflag) {
        this.eraseflag = eraseflag;
    }

    public String getErasedate() {
        return erasedate;
    }

    public void setErasedate(String erasedate) {
        this.erasedate = erasedate;
    }

    public int getCollectflag() {
        return collectflag;
    }

    public void setCollectflag(int collectflag) {
        this.collectflag = collectflag;
    }

    public String getCollectdate() {
        return collectdate;
    }

    public void setCollectdate(String collectdate) {
        this.collectdate = collectdate;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getLateflag() {
        return lateflag;
    }

    public void setLateflag(int lateflag) {
        this.lateflag = lateflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getFlowBillNo() {
        return flowBillNo;
    }

    public void setFlowBillNo(String flowBillNo) {
        this.flowBillNo = flowBillNo;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    public String getConsignEnId() {
        return consignEnId;
    }

    public void setConsignEnId(String consignEnId) {
        this.consignEnId = consignEnId;
    }

    public int getSwitchType() {
        return switchType;
    }

    public void setSwitchType(int switchType) {
        this.switchType = switchType;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public int getInputflag() {
        return inputflag;
    }

    public void setInputflag(int inputflag) {
        this.inputflag = inputflag;
    }

    public int getIsNotexistsagent() {
        return isNotexistsagent;
    }

    public void setIsNotexistsagent(int isNotexistsagent) {
        this.isNotexistsagent = isNotexistsagent;
    }

    public int getPayflag() {
        return payflag;
    }

    public void setPayflag(int payflag) {
        this.payflag = payflag;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public int getErasesure() {
        return erasesure;
    }

    public void setErasesure(int erasesure) {
        this.erasesure = erasesure;
    }

    public String getLastnanotime() {
        return lastnanotime;
    }

    public void setLastnanotime(String lastnanotime) {
        this.lastnanotime = lastnanotime;
    }

    private List<CollectionsGatherDetail> collectionsGatherDetailList;

    public List<CollectionsGatherDetail> getCollectionsGatherDetailList(){return collectionsGatherDetailList;}

    public void setCollectionsGatherDetailList(List<CollectionsGatherDetail> collectionsGatherDetailList) {
        this.collectionsGatherDetailList = collectionsGatherDetailList;
    }
}
