package com.xcmis.feefax.modules.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能
 *
 * @author
 * @see
 */
public class BizContent {
    private String billno;
    private String crccode;
    private String billdate;
    private BigDecimal pay_amount;
    private BigDecimal total_amount;
    private BigDecimal delay_amount;
    private String billstats;
    private String chg_code;
    private String chg_name;
    private String payer_name;
    private String payer_acct;
    private String payer_opbk;
    private String rec_acctype;
    private String rec_name;
    private String rec_acct;
    private String rec_opbk;
    private String rec_bkcode;
    private String rec_real_acct;
    private String interbank;
    private String remark;
    private String paylistfmt;
    private List<Paylist> paylist;

    private String attach_info;

    public BizContent(){

    }

    public BizContent(Collections c){
        billno = c.getReqBillNo();
        crccode = "";
        billdate  = c.getMakedate().substring(0,10).replace("-","");
        pay_amount = new BigDecimal(new DecimalFormat("0.00").format(c.getChargemoney()));
        total_amount = new BigDecimal(new DecimalFormat("0.00").format(c.getChargemoney()));
        delay_amount = new BigDecimal(new DecimalFormat("0.00").format(0));
        billstats = c.getBillstats();
        chg_code = c.getEnCode();
        chg_name = c.getEnName();
        payer_name = c.getPayerName();
        payer_acct = c.getEnAccountNo();
        payer_opbk = "";
        rec_acctype = "1";
        rec_name = c.getReceName();
        rec_acct = c.getReceAccountNo();
        rec_opbk = c.getReceiverbank();
        rec_bkcode = c.getIsbn();
        rec_real_acct = "";
        interbank = "0";
        remark = "";
        paylistfmt = "01";
        attach_info = "";

        paylist = new ArrayList<>();

        if(c.getReceivetype() == 1 || c.getReceivetype() == 2) {
            for (CollectionsDetail cd : c.getCollectionsDetailList()) {
                Paylist pl = new Paylist();
                pl.setItem_code(cd.getIncitemCode());
                pl.setItem_name(cd.getIncitemName());
                pl.setItem_amount(new BigDecimal(new DecimalFormat("0.00").format(cd.getChargemoney())));
                if (cd.getMeasure() == null) {
                    pl.setUnit("个");
                } else {
                    if (cd.getMeasure().equals("")) {
                        pl.setUnit("个");
                    } else {
                        pl.setUnit(cd.getMeasure());
                    }
                }
                pl.setNum(cd.getChargenum());
                pl.setStdtype("空");
                pl.setStandard("空");
                paylist.add(pl);
            }
        }else{
            for (CollectionsGatherDetail cd : c.getCollectionsGatherDetailList()) {
                Paylist pl = new Paylist();
                pl.setItem_code(cd.getIncitemCode());
                pl.setItem_name(cd.getIncitemName());
                pl.setItem_amount(new BigDecimal(new DecimalFormat("0.00").format(cd.getChargemoney())));
                if (cd.getMeasure() == null) {
                    pl.setUnit("个");
                } else {
                    if (cd.getMeasure().equals("")) {
                        pl.setUnit("个");
                    } else {
                        pl.setUnit(cd.getMeasure());
                    }
                }
                pl.setNum(cd.getChargenum());
                pl.setStdtype("空");
                pl.setStandard("空");
                paylist.add(pl);
            }
        }

    }


    public String getCrccode() {
        return crccode;
    }

    public void setCrccode(String crccode) {
        this.crccode = crccode;
    }

    public String getPayer_opbk() {
        return payer_opbk;
    }

    public void setPayer_opbk(String payer_opbk) {
        this.payer_opbk = payer_opbk;
    }

    public String getRec_name() {
        return rec_name;
    }

    public void setRec_name(String rec_name) {
        this.rec_name = rec_name;
    }

    public String getRec_opbk() {
        return rec_opbk;
    }

    public void setRec_opbk(String rec_opbk) {
        this.rec_opbk = rec_opbk;
    }

    public String getRec_real_acct() {
        return rec_real_acct;
    }

    public void setRec_real_acct(String rec_real_acct) {
        this.rec_real_acct = rec_real_acct;
    }

    public String getInterbank() {
        return interbank;
    }

    public void setInterbank(String interbank) {
        this.interbank = interbank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttach_info() {
        return attach_info;
    }

    public void setAttach_info(String attach_info) {
        this.attach_info = attach_info;
    }

    public String getRec_acct() {
        return rec_acct;
    }

    public void setRec_acct(String rec_acct) {
        this.rec_acct = rec_acct;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public BigDecimal getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(BigDecimal pay_amount) {
        this.pay_amount = pay_amount;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getDelay_amount() {
        return delay_amount;
    }

    public void setDelay_amount(BigDecimal delay_amount) {
        this.delay_amount = delay_amount;
    }

    public String getBillstats() {
        return billstats;
    }

    public void setBillstats(String billstats) {
        this.billstats = billstats;
    }

    public String getChg_code() {
        return chg_code;
    }

    public void setChg_code(String chg_code) {
        this.chg_code = chg_code;
    }

    public String getChg_name() {
        return chg_name;
    }

    public void setChg_name(String chg_name) {
        this.chg_name = chg_name;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public void setPayer_name(String payer_name) {
        this.payer_name = payer_name;
    }

    public String getPayer_acct() {
        return payer_acct;
    }

    public void setPayer_acct(String payer_acct) {
        this.payer_acct = payer_acct;
    }

    public String getRec_acctype() {
        return rec_acctype;
    }

    public void setRec_acctype(String rec_acctype) {
        this.rec_acctype = rec_acctype;
    }

    public String getRec_bkcode() {
        return rec_bkcode;
    }

    public void setRec_bkcode(String rec_bkcode) {
        this.rec_bkcode = rec_bkcode;
    }

    public String getPaylistfmt() {
        return paylistfmt;
    }

    public void setPaylistfmt(String paylistfmt) {
        this.paylistfmt = paylistfmt;
    }

    public List<Paylist> getPaylist() {
        return paylist;
    }

    public void setPaylist(List<Paylist> paylist) {
        this.paylist = paylist;
    }
}
