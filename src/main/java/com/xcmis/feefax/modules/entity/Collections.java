package com.xcmis.feefax.modules.entity;


import java.util.List;

public class Collections extends CommonEntity{
    public Collections(){

    }


    public Collections(CollectionsGather collectionsGather){
        this.billNo = collectionsGather.getBillNo();
        this.enId = collectionsGather.getEnId();
        this.pmId = collectionsGather.getPmId();
        this.makedate = collectionsGather.getMakedate();
        this.payerid = collectionsGather.getPayerid();
        this.payeraccount = collectionsGather.getPayeraccount();
        this.payerbank = collectionsGather.getPayerbank();
        this.payerName = collectionsGather.getPayerName();
        this.receiverid = collectionsGather.getReceiverid();
        this.billtypeId = collectionsGather.getBilltypeId();
        this.remark = collectionsGather.getRemark();
        this.isconsign = collectionsGather.getIsconsign();
        this.consigneeEnId = collectionsGather.getConsignEnId();
        this.billName = collectionsGather.getBillName();
        this.pmName = collectionsGather.getPmName();
        this.enName = collectionsGather.getEnName();
        this.userName = collectionsGather.getUserName();
        this.stateCode = collectionsGather.getStateCode();
        this.createUser = collectionsGather.getCreateUser();
    }

    private String setYear;
    private String chrId;
    private String rgCode;
    private String createUser;
    private String createDate;
    private String lastestOpUser;
    private String lastestOpDate;
    private int isDeleted;
    private String billNo;
    private String oldBillNo;

    private String beginBillNo;
    private String endBillNo;

    private String beginMakedate;
    private String endMakedate;

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
    private int receivetype;
    private String inputername;
    private int printflag;
    private String printdate;
    private int returnflag;
    private String returndate;
    private int eraseflag;
    private String erasedate;
    private int lateflag;
    private String latedate;
    private int autocollectflag;
    private String remark;
    private String orgType;
    private String belongOrg;
    private String rcid;
    private int isend;
    private String flowBillNo;
    private String billtypeId;
    private int checkearne;
    private String consignEnId;
    private int isSend;
    private int adjustflag;
    private int isGet;
    private String checkdate;
    private String beginCheckdate;
    private String endCheckdate;
    private int tallytag;
    private String bankIndate;
    private String bankAccdate;
    private String bankChildcode;
    private int isOtherplace;
    private int isHandwork;
    private int incomestatus;
    private int bankSupplyFlag;
    private int switchType;
    private String swicthId;
    private int checkflag;
    private String lastVer;
    private String reqBilltypeId;
    private String reqBillNo;
    private String reqFlowBillNo;
    private String consigneeEnId;
    private int isnotice;
    private String eraseafterid;
    private String nosourceId;
    private int unitSendflag;
    private String checkearnedate;
    private String nosourceIds;
    private String supplytempletId;
    private String gatherUser;
    private String gatherConfirmNo;
    private String packConfirmNo;
    private int isdivide;
    private String chrCode1;
    private String chrCode2;
    private String chrCode3;
    private String chrCode4;
    private String chrCode5;
    private String chrCode6;
    private String chrCode7;
    private String chrCode8;
    private String chrCode9;
    private String chrCode10;
    private String sourceId;
    private String remark1;
    private String budgetLevel;
    private String posSkFlag;
    private String posSkDate;
    private String posQsflag;
    private String posQsDate;
    private int eraseIssend;
    private int erasesure;
    private int bankCheckflag;

    private String enName;
    private String enCode;
    private String enAccountNo;
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
    private String stateCode;

    private String receName;
    private String receAccountNo;
    private String receAccountName;
    private String receCode;
    private String receBankName;
    private String isbn;



    private String incitemid;

    private String untaxBillnameId;

    private String paydate;
    private int payflag;

    private String billstats;
    private String bankNo;
    private String isShow;
    private String chargemoneyStr;

    private String incitemNames;



    private String begincheckearnedate;
    private String endcheckearnedate;

    private String errorMsg;
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

    public String getBeginCheckdate() {
        return beginCheckdate;
    }

    public void setBeginCheckdate(String beginCheckdate) {
        this.beginCheckdate = beginCheckdate;
    }

    public String getEndCheckdate() {
        return endCheckdate;
    }

    public void setEndCheckdate(String endCheckdate) {
        this.endCheckdate = endCheckdate;
    }

    public String getConsigneeEnName() {
        return consigneeEnName;
    }

    public void setConsigneeEnName(String consigneeEnName) {
        this.consigneeEnName = consigneeEnName;
    }

    public String getLatedate() {
        return latedate;
    }

    public void setLatedate(String latedate) {
        this.latedate = latedate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private int redRushFlag;
    private String redRushDate;

    public int getRedRushFlag() {
        return redRushFlag;
    }

    public void setRedRushFlag(int redRushFlag) {
        this.redRushFlag = redRushFlag;
    }

    public String getRedRushDate() {
        return redRushDate;
    }

    public void setRedRushDate(String redRushDate) {
        this.redRushDate = redRushDate;
    }

    public String getBegincheckearnedate() {
        return begincheckearnedate;
    }

    public void setBegincheckearnedate(String begincheckearnedate) {
        this.begincheckearnedate = begincheckearnedate;
    }

    public String getEndcheckearnedate() {
        return endcheckearnedate;
    }

    public void setEndcheckearnedate(String endcheckearnedate) {
        this.endcheckearnedate = endcheckearnedate;
    }

    public String getIncitemNames() {
        return incitemNames;
    }

    public void setIncitemNames(String incitemNames) {
        this.incitemNames = incitemNames;
    }

    private List<IncomeBankAccount> incomeBankAccountList;

    public List<IncomeBankAccount> getIncomeBankAccountList() {
        return incomeBankAccountList;
    }

    public void setIncomeBankAccountList(List<IncomeBankAccount> incomeBankAccountList) {
        this.incomeBankAccountList = incomeBankAccountList;
    }

    public String getChargemoneyStr() {
        return chargemoneyStr;
    }

    public void setChargemoneyStr(String chargemoneyStr) {
        this.chargemoneyStr = chargemoneyStr;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBillstats() {
        return billstats;
    }

    public void setBillstats(String billstats) {
        this.billstats = billstats;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public int getPayflag() {
        return payflag;
    }

    public void setPayflag(int payflag) {
        this.payflag = payflag;
    }

    public String getUntaxBillnameId() {
        return untaxBillnameId;
    }

    public void setUntaxBillnameId(String untaxBillnameId) {
        this.untaxBillnameId = untaxBillnameId;
    }

    public String getOldBillNo() {
        return oldBillNo;
    }

    public void setOldBillNo(String oldBillNo) {
        this.oldBillNo = oldBillNo;
    }

    public String getIncitemid() {
        return incitemid;
    }

    public void setIncitemid(String incitemid) {
        this.incitemid = incitemid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReceBankName() {
        return receBankName;
    }

    public void setReceBankName(String receBankName) {
        this.receBankName = receBankName;
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

    public String getEnAccountNo() {
        return enAccountNo;
    }

    public void setEnAccountNo(String enAccountNo) {
        this.enAccountNo = enAccountNo;
    }

    public String getEndMakedate() {
        return endMakedate;
    }

    public void setEndMakedate(String endMakedate) {
        this.endMakedate = endMakedate;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
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

    public String getAllmoneycn() {
        return allmoneycn;
    }

    public void setAllmoneycn(String allmoneycn) {
        this.allmoneycn = allmoneycn;
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

    public String getBeginMakedate() {
        return beginMakedate;
    }

    public void setBeginMakedate(String beginMakedate) {
        this.beginMakedate = beginMakedate;
    }


    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
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

    public double getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(double chargemoney) {
        this.chargemoney = chargemoney;
    }

    private List<CollectionsGatherDetail> collectionsGatherDetailList;

    public List<CollectionsGatherDetail> getCollectionsGatherDetailList(){return collectionsGatherDetailList;}

    public void setCollectionsGatherDetailList(List<CollectionsGatherDetail> collectionsGatherDetailList) {
        this.collectionsGatherDetailList = collectionsGatherDetailList;
    }

    private List<CollectionsDetail> collectionsDetailList;

    public List<CollectionsDetail> getCollectionsDetailList() {
        return collectionsDetailList;
    }

    public void setCollectionsDetailList(List<CollectionsDetail> collectionsDetailList) {
        this.collectionsDetailList = collectionsDetailList;
    }

    public int getIsnotice() {
        return isnotice;
    }
    public String getChrCode8() {
        return chrCode8;
    }


    public int getBankCheckflag() {

        return bankCheckflag;
    }

    public void setBankCheckflag(int bankCheckflag) {
        this.bankCheckflag = bankCheckflag;
    }

    public int getErasesure() {
        return erasesure;
    }

    public void setErasesure(int erasesure) {
        this.erasesure = erasesure;
    }

    public int getEraseIssend() {
        return eraseIssend;
    }

    public void setEraseIssend(int eraseIssend) {
        this.eraseIssend = eraseIssend;
    }

    public String getPosQsDate() {
        return posQsDate;
    }

    public void setPosQsDate(String posQsDate) {
        this.posQsDate = posQsDate;
    }

    public String getPosQsflag() {
        return posQsflag;
    }

    public void setPosQsflag(String posQsflag) {
        this.posQsflag = posQsflag;
    }

    public String getPosSkDate() {
        return posSkDate;
    }

    public void setPosSkDate(String posSkDate) {
        this.posSkDate = posSkDate;
    }

    public String getPosSkFlag() {
        return posSkFlag;
    }

    public void setPosSkFlag(String posSkFlag) {
        this.posSkFlag = posSkFlag;
    }

    public String getBudgetLevel() {
        return budgetLevel;
    }

    public void setBudgetLevel(String budgetLevel) {
        this.budgetLevel = budgetLevel;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getChrCode10() {
        return chrCode10;
    }

    public void setChrCode10(String chrCode10) {
        this.chrCode10 = chrCode10;
    }

    public String getChrCode9() {
        return chrCode9;
    }

    public void setChrCode9(String chrCode9) {
        this.chrCode9 = chrCode9;
    }

    public void setChrCode8(String chrCode8) {
        this.chrCode8 = chrCode8;
    }

    public String getChrCode7() {
        return chrCode7;
    }

    public void setChrCode7(String chrCode7) {
        this.chrCode7 = chrCode7;
    }

    public String getChrCode6() {
        return chrCode6;
    }

    public void setChrCode6(String chrCode6) {
        this.chrCode6 = chrCode6;
    }

    public String getChrCode5() {
        return chrCode5;
    }

    public void setChrCode5(String chrCode5) {
        this.chrCode5 = chrCode5;
    }

    public String getChrCode4() {
        return chrCode4;
    }

    public void setChrCode4(String chrCode4) {
        this.chrCode4 = chrCode4;
    }

    public String getChrCode3() {
        return chrCode3;
    }

    public void setChrCode3(String chrCode3) {
        this.chrCode3 = chrCode3;
    }

    public String getChrCode2() {
        return chrCode2;
    }

    public void setChrCode2(String chrCode2) {
        this.chrCode2 = chrCode2;
    }

    public String getChrCode1() {
        return chrCode1;
    }

    public void setChrCode1(String chrCode1) {
        this.chrCode1 = chrCode1;
    }

    public int getIsdivide() {
        return isdivide;
    }

    public void setIsdivide(int isdivide) {
        this.isdivide = isdivide;
    }

    public String getPackConfirmNo() {
        return packConfirmNo;
    }

    public void setPackConfirmNo(String packConfirmNo) {
        this.packConfirmNo = packConfirmNo;
    }

    public String getGatherConfirmNo() {
        return gatherConfirmNo;
    }

    public void setGatherConfirmNo(String gatherConfirmNo) {
        this.gatherConfirmNo = gatherConfirmNo;
    }

    public String getGatherUser() {
        return gatherUser;
    }

    public void setGatherUser(String gatherUser) {
        this.gatherUser = gatherUser;
    }

    public String getSupplytempletId() {
        return supplytempletId;
    }

    public void setSupplytempletId(String supplytempletId) {
        this.supplytempletId = supplytempletId;
    }

    public String getNosourceIds() {
        return nosourceIds;
    }

    public void setNosourceIds(String nosourceIds) {
        this.nosourceIds = nosourceIds;
    }

    public String getCheckearnedate() {
        return checkearnedate;
    }

    public void setCheckearnedate(String checkearnedate) {
        this.checkearnedate = checkearnedate;
    }

    public int getUnitSendflag() {
        return unitSendflag;
    }

    public void setUnitSendflag(int unitSendflag) {
        this.unitSendflag = unitSendflag;
    }

    public String getNosourceId() {
        return nosourceId;
    }

    public void setNosourceId(String nosourceId) {
        this.nosourceId = nosourceId;
    }

    public String getEraseafterid() {
        return eraseafterid;
    }

    public void setEraseafterid(String eraseafterid) {
        this.eraseafterid = eraseafterid;
    }

    public void setIsnotice(int isnotice) {
        this.isnotice = isnotice;
    }

    public String getConsigneeEnId() {
        return consigneeEnId;
    }

    public void setConsigneeEnId(String consigneeEnId) {
        this.consigneeEnId = consigneeEnId;
    }

    public String getReqFlowBillNo() {
        return reqFlowBillNo;
    }

    public void setReqFlowBillNo(String reqFlowBillNo) {
        this.reqFlowBillNo = reqFlowBillNo;
    }

    public String getReqBillNo() {
        return reqBillNo;
    }

    public void setReqBillNo(String reqBillNo) {
        this.reqBillNo = reqBillNo;
    }

    public String getReqBilltypeId() {
        return reqBilltypeId;
    }

    public void setReqBilltypeId(String reqBilltypeId) {
        this.reqBilltypeId = reqBilltypeId;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public int getCheckflag() {
        return checkflag;
    }

    public void setCheckflag(int checkflag) {
        this.checkflag = checkflag;
    }

    public String getSwicthId() {
        return swicthId;
    }

    public void setSwicthId(String swicthId) {
        this.swicthId = swicthId;
    }

    public int getSwitchType() {
        return switchType;
    }

    public void setSwitchType(int switchType) {
        this.switchType = switchType;
    }

    public int getBankSupplyFlag() {
        return bankSupplyFlag;
    }

    public void setBankSupplyFlag(int bankSupplyFlag) {
        this.bankSupplyFlag = bankSupplyFlag;
    }

    public int getIncomestatus() {
        return incomestatus;
    }

    public void setIncomestatus(int incomestatus) {
        this.incomestatus = incomestatus;
    }

    public int getIsHandwork() {
        return isHandwork;
    }

    public void setIsHandwork(int isHandwork) {
        this.isHandwork = isHandwork;
    }

    public int getIsOtherplace() {
        return isOtherplace;
    }

    public void setIsOtherplace(int isOtherplace) {
        this.isOtherplace = isOtherplace;
    }

    public String getBankChildcode() {
        return bankChildcode;
    }

    public void setBankChildcode(String bankChildcode) {
        this.bankChildcode = bankChildcode;
    }

    public String getBankAccdate() {
        return bankAccdate;
    }

    public void setBankAccdate(String bankAccdate) {
        this.bankAccdate = bankAccdate;
    }

    public String getBankIndate() {
        return bankIndate;
    }

    public void setBankIndate(String bankIndate) {
        this.bankIndate = bankIndate;
    }

    public int getTallytag() {
        return tallytag;
    }

    public void setTallytag(int tallytag) {
        this.tallytag = tallytag;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public int getIsGet() {
        return isGet;
    }

    public void setIsGet(int isGet) {
        this.isGet = isGet;
    }

    public int getAdjustflag() {
        return adjustflag;
    }

    public void setAdjustflag(int adjustflag) {
        this.adjustflag = adjustflag;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getConsignEnId() {
        return consignEnId;
    }

    public void setConsignEnId(String consignEnId) {
        this.consignEnId = consignEnId;
    }

    public int getCheckearne() {
        return checkearne;
    }

    public void setCheckearne(int checkearne) {
        this.checkearne = checkearne;
    }

    public String getBilltypeId() {
        return billtypeId;
    }

    public void setBilltypeId(String billtypeId) {
        this.billtypeId = billtypeId;
    }

    public String getFlowBillNo() {
        return flowBillNo;
    }

    public void setFlowBillNo(String flowBillNo) {
        this.flowBillNo = flowBillNo;
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

    public String getBelongOrg() {
        return belongOrg;
    }

    public void setBelongOrg(String belongOrg) {
        this.belongOrg = belongOrg;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAutocollectflag() {
        return autocollectflag;
    }

    public void setAutocollectflag(int autocollectflag) {
        this.autocollectflag = autocollectflag;
    }

    public int getLateflag() {
        return lateflag;
    }

    public void setLateflag(int lateflag) {
        this.lateflag = lateflag;
    }

    public String getErasedate() {
        return erasedate;
    }

    public void setErasedate(String erasedate) {
        this.erasedate = erasedate;
    }

    public int getEraseflag() {
        return eraseflag;
    }

    public void setEraseflag(int eraseflag) {
        this.eraseflag = eraseflag;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public int getReturnflag() {
        return returnflag;
    }

    public void setReturnflag(int returnflag) {
        this.returnflag = returnflag;
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

    public String getInputername() {
        return inputername;
    }

    public void setInputername(String inputername) {
        this.inputername = inputername;
    }

    public int getReceivetype() {
        return receivetype;
    }

    public void setReceivetype(int receivetype) {
        this.receivetype = receivetype;
    }

    public int getIsconsign() {
        return isconsign;
    }

    public void setIsconsign(int isconsign) {
        this.isconsign = isconsign;
    }

    public String getReceiverbank() {
        return receiverbank;
    }

    public void setReceiverbank(String receiverbank) {
        this.receiverbank = receiverbank;
    }

    public String getVerifyNo() {
        return verifyNo;
    }

    public void setVerifyNo(String verifyNo) {
        this.verifyNo = verifyNo;
    }

    public String getReceiveraccount() {
        return receiveraccount;
    }

    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = receiveraccount;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
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

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerid() {
        return payerid;
    }

    public void setPayerid(String payerid) {
        this.payerid = payerid;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
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


    public String getVerifyBillNo() {
        return verifyBillNo;
    }

    public void setVerifyBillNo(String verifyBillNo) {
        this.verifyBillNo = verifyBillNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLastestOpDate() {
        return lastestOpDate;
    }

    public void setLastestOpDate(String lastestOpDate) {
        this.lastestOpDate = lastestOpDate;
    }

    public String getLastestOpUser() {
        return lastestOpUser;
    }

    public void setLastestOpUser(String lastestOpUser) {
        this.lastestOpUser = lastestOpUser;
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

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
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
