package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能
 *
 * @author
 * @see
 */

@Controller
@RequestMapping(value = "/commonplatform")
public class UntaxExchangeMessageController {
    @Autowired
    private RegionService regionService;

    @Autowired
    private BankInfoService bankInfoService;

    @Autowired
    private EduStuInfoService eduStuInfoService;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private CollectionsDetailService collectionsDetailService;

    @Autowired
    private CollectionsGatherDetailService collectionsGatherDetailService;

    @Autowired
    private UntaxExchangeMessageService untaxExchangeMessageService;

    @Autowired
    private IncomeBankAccountService incomeBankAccountService;

    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;

    @Autowired
    private UntaxNosourceService untaxNosourceService;


    @Value("${issubaccount}")
    private boolean isSubAccount;

    @RequestMapping(value = "/untaxReceiveInfo", method = RequestMethod.POST)
    public void untaxReceiveInfo(HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        String str = "";
        PrintWriter out = null;
        byte[] bytes = new byte[1024 * 1024];

        StringBuilder buffer = new StringBuilder("");
        try {
            request.setCharacterEncoding("GBK");


            /*
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            str = buffer.toString();
            */


            is = request.getInputStream();
            int nRead = 1;
            int nTotalRead = 0;
            while (nRead > 0) {
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                if (nRead > 0)
                    nTotalRead = nTotalRead + nRead;
            }
            str = new String(bytes, 0, nTotalRead, "GBK");

            BankInfo bi = new BankInfo();
            bi.setCreateType("in");
            bi.setCreateDate(DateTimeUtils.getDateTimeStr1());
            bi.setContent(str);
            bankInfoService.insert(bi);

            JSONObject jsonObject = JSONObject.fromObject(str);

            Map<String, String> map = new HashMap<>();

            Iterator<String> ir = jsonObject.keys();
            while (ir.hasNext()) {
                String keyName = (String) ir.next();
                map.put(keyName, jsonObject.getString(keyName));
            }


            //设定类容为json的格式
            response.setContentType("application/json;charset=GBK");
            out = response.getWriter();

            //写到客户端
            JSONObject postData = new JSONObject();


            if ("wp.outer.bill.pay.query".equals(map.get("method"))) {
                String bizContent = map.get("biz_content");
                JSONObject bizContentObject = JSONObject.fromObject(bizContent);
                String reqBillNo = bizContentObject.getString("billno");


                com.xcmis.feefax.modules.entity.Collections collections = new com.xcmis.feefax.modules.entity.Collections();
                collections.setReqBillNo(reqBillNo);
                com.xcmis.feefax.modules.entity.Collections c = collectionsService.get(collections);


                BizResponseContent bizResponseContent;
                //后期状态编码要全部判断
                /*
                11001	不存在的缴款码/非税缴款书编号查询
                11002	缴款码/非税缴款书校验码校验失败
                11003	缴款书超期限
                11004	缴款书已作废
                11005	区划[XXXXXX]在银行[XX]当前没有有效的财政账号
                11006	该缴款书此银行无法查缴
                12001	重复缴款确认
                12002	缴款确认码与财政不一致
                12003	收款账户与财政不一致
                12004	收款金额与财政不一致
                12005	未被查询的缴款书做缴款确认
                12006	缴款日期不在被查询日期范围内的缴款确认
                13001	该流水号的未匹配资金到账已存在，但是内容不一致。
                13002	单位汇缴户在有效日期内不存在
                14001	该流水号在途资金到账已存在，但是内容不一致
                14002	在途资金到账金额与缴款书合计金额不符
                */
                if (c == null) {
                    bizResponseContent = new BizResponseContent("11001", "不存在的缴款码/非税缴款书编号查询");
                } else {
                    if (c.getEraseflag() == 1) {
                        c.setBillstats("2");
                    } else if (c.getPayflag() == 1) {
                        c.setBillstats("1");
                    } else {
                        c.setBillstats("0");
                    }


                    if(c.getReceivetype() == 1) {
                        CollectionsDetail collectionsDetail = new CollectionsDetail();
                        collectionsDetail.setMainId(c.getChrId());
                        List<CollectionsDetail> list = collectionsDetailService.findAllList(collectionsDetail);
                        c.setCollectionsDetailList(list);
                    }else if(c.getReceivetype() == 3){
                        CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                        collectionsGatherDetail.setCollectId(c.getChrId());
                        List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
                        c.setCollectionsGatherDetailList(collectionGatherDetailList);
                    }else{
                        List<CollectionsDetail> collectionsDetailList = new ArrayList<>();
                        EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                        eduImportStuInfoDetail.setStuMainid(c.getChrCode4());
                        Map<String,Object> mapIn = new HashMap<>();
                        mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
                        List<EduImportStuInfoDetail> eduImportStuInfoDetailList = eduStuInfoService.getEduStuInfoDetailList(mapIn);
                        for(EduImportStuInfoDetail eisid : eduImportStuInfoDetailList) {
                            CollectionsDetail collectionsDetail = new CollectionsDetail();
                            collectionsDetail.setIncitemCode(eisid.getIncitemCode());
                            collectionsDetail.setIncitemName(eisid.getStuIncitemName());
                            collectionsDetail.setMeasure(eisid.getMeasure());
                            collectionsDetail.setChargenum(eisid.getChargenum());
                            collectionsDetail.setChargemoney(eisid.getChargemoney());
                            collectionsDetailList.add(collectionsDetail);
                        }

                        c.setCollectionsDetailList(collectionsDetailList);
                    }

                    bizResponseContent = new BizResponseContent(c);

                    String remark = "";
                    if(c.getNosourceIds() != null){
                        if(!c.getNosourceIds().equals("")){
                            String nosourceIds = c.getNosourceIds();
                            String[] nosourceIdArray = nosourceIds.split(",");
                            for(String nosourceId : nosourceIdArray) {
                                UntaxNosource untaxNosource = new UntaxNosource();
                                untaxNosource.setChrId(nosourceId);
                                untaxNosource = untaxNosourceService.get(untaxNosource);
                                remark += untaxNosource.getBatchno() + ",";
                            }

                            remark = remark.substring(0, remark.length() - 1);
                        }
                    }

                    bizResponseContent.setRemark(remark);
                    if(c.getReceivetype() == 2){
                        bizResponseContent.setRec_acctype("9");
                    }

                    if(isSubAccount){
                        String account = c.getReceAccountNo();

                        if(account.length() == 14) {
                            bizResponseContent.setRec_acct(account + c.getEnCode());
                        }else{
                            if(account.length() == 17) {
                                bizResponseContent.setRec_acct(account + "0000" + c.getEnCode());
                            }
                        }
                    }

                }

                postData.put("wp_outer_bill_pay_query_response", bizResponseContent);
                System.out.println(postData.toString());
                out.write(postData.toString());
                out.flush();
            } else if ("wp.outer.bill.pay".equals(map.get("method"))) {
                boolean validSend = false;
                String bizContent = map.get("biz_content");
                JSONObject bizContentObject = JSONObject.fromObject(bizContent);
                String reqBillNo = bizContentObject.getString("billno");
                String payDatetime = bizContentObject.getString("pay_datetime");
                String payAmount = bizContentObject.getString("pay_amount");
                String paymode = bizContentObject.getString("paymode");

                String attach_info = bizContentObject.getString("attach_info");
                String hold1 = "";
                if(attach_info != null){
                    if(!attach_info.equals("")){
                        JSONArray attachInfoArray = JSONArray.fromObject(attach_info);
                        JSONObject jsonObj = attachInfoArray.getJSONObject(0);
                        String value = jsonObj.getString("value");
                        JSONObject valueObj = JSONObject.fromObject(value);
                        hold1 = valueObj.getString("param");
                    }
                }


                com.xcmis.feefax.modules.entity.Collections collections = new com.xcmis.feefax.modules.entity.Collections();
                collections.setReqBillNo(reqBillNo);
                com.xcmis.feefax.modules.entity.Collections c = collectionsService.get(collections);

                BizResponseContent bizResponseContent;
                if (c == null) {
                    bizResponseContent = new BizResponseContent("11001", "不存在的缴款码/非税缴款书编号查询");
                }else{
                    if (c.getEraseflag() == 1) {
                        bizResponseContent = new BizResponseContent("11004", "缴款书已作废");
                    } else if (c.getPayflag() == 1) {
                        if(paymode.equals("5") || paymode.equals("13") || paymode.equals("51") || paymode.equals("52")){
                            bizResponseContent = new BizResponseContent("00000", "成功");
                        }else {
                            bizResponseContent = new BizResponseContent("12001", "重复缴款确认");
                        }
                    }else if (!new BigDecimal(new DecimalFormat("0.00").format(c.getChargemoney())).toString().equals(new BigDecimal(new DecimalFormat("0.00").format(Double.valueOf(payAmount))).toString())){
                        bizResponseContent = new BizResponseContent("12004", "收款金额与财政不一致");
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date dtf = format.parse(payDatetime);
                        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
                        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                        c.setPaydate(sdf.format(dtf));
                        c.setPayflag(1);

                        if(c.getReceivetype() == 1) {
                            CollectionsDetail collectionsDetail = new CollectionsDetail();
                            collectionsDetail.setMainId(c.getChrId());
                            List<CollectionsDetail> list = collectionsDetailService.findAllList(collectionsDetail);
                            c.setCollectionsDetailList(list);
                        }else if(c.getReceivetype() == 3){
                            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                            collectionsGatherDetail.setCollectId(c.getChrId());
                            List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
                            c.setCollectionsGatherDetailList(collectionGatherDetailList);
                        }else{
                            List<CollectionsDetail> collectionsDetailList = new ArrayList<>();
                            EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                            eduImportStuInfoDetail.setStuMainid(c.getChrCode4());
                            Map<String,Object> mapIn = new HashMap<>();
                            mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
                            List<EduImportStuInfoDetail> eduImportStuInfoDetailList = eduStuInfoService.getEduStuInfoDetailList(mapIn);
                            for(EduImportStuInfoDetail eisid : eduImportStuInfoDetailList) {
                                CollectionsDetail collectionsDetail = new CollectionsDetail();
                                collectionsDetail.setIncitemCode(eisid.getIncitemCode());
                                collectionsDetail.setIncitemName(eisid.getStuIncitemName());
                                collectionsDetail.setMeasure(eisid.getMeasure());
                                collectionsDetail.setChargenum(eisid.getChargenum());
                                collectionsDetail.setChargemoney(eisid.getChargemoney());
                                collectionsDetailList.add(collectionsDetail);
                            }

                            c.setCollectionsDetailList(collectionsDetailList);
                        }


                        boolean b = false;
                        if(hold1.equals("19")){
                            if(c.getNosourceIds() != null){
                                if(!c.getNosourceIds().equals("")){
                                    c.setPaymode(paymode);
                                    b = collectionsService.updatePayflag(c);
                                }
                            }
                        }else {
                            c.setPaymode(paymode);
                            b = collectionsService.updatePayflag(c);
                        }
                        if (!b) {
                            bizResponseContent = new BizResponseContent("99999", "失败");
                        } else {
                            if(c.getReceivetype() == 2) {
                                eduStuInfoService.updateStuPayflag(c.getChrCode4());
                            }

                            if(hold1.equals("19")){
                                c.setLatedate(sdf.format(dtf));
                                c.setLateflag(1);
                                collectionsService.updateLateflag(c);

                                c.setCheckdate(sdf.format(dtf));
                                c.setCheckflag(1);
                                collectionsService.updateCheckflag(c);

                                if(c.getNosourceIds().indexOf(",") > -1){
                                    untaxNosourceService.updateConfirm(c.getNosourceIds());
                                }else{
                                    boolean isUpdate = true;
                                    List<Collections> collectionsList = collectionsService.findAllList(c);
                                    for(Collections cs : collectionsList){
                                        int lateflag = cs.getLateflag();
                                        if(lateflag == 0){
                                            isUpdate = false;
                                            break;
                                        }
                                    }
                                    if(isUpdate){
                                        untaxNosourceService.updateConfirm(c.getNosourceIds());
                                    }
                                }


                            }
                            bizResponseContent = new BizResponseContent("00000", "成功");
                            validSend = true;
                        }
                    }
                }
                postData.put("wp_outer_bill_pay_response", bizResponseContent);
                System.out.println(postData.toString());

                if(validSend) {
                    c.setBillstats("1");
                    BizContent bc = new BizContent(c);
                    if (untaxExchangeMessageService.billSync(bc)) {
                        c.setIsSend(2);
                        collectionsService.updateIsSend(c);
                    }
                }

                out.write(postData.toString());
                out.flush();
            }else if ("wp.outer.account.billfund".equals(map.get("method"))) {
                /*
                15001	财政端记录信息和对账消息中明细金额不一致
                15002	日间交易已传送但日终对账单中缺失
                15003	日间交易未传送但日终对账单中存在
                15010	对账重复发送，拒绝接收
                */
                BizResponseContent bizResponseContent;
                String bizContent = map.get("biz_content");
                JSONObject bizContentObject = JSONObject.fromObject(bizContent);

                String checkdate = bizContentObject.getString("date");
                String accconfirmNo = bizContentObject.getString("accconfirm_no");
                //System.out.println(accconfirmNo);
                String bankindex = "";

                String translist = bizContentObject.getString("translist");
                //System.out.println(translist);

                int succeedcount = 0, failcount = 0;

                List<Fail> failList = new ArrayList<>();

                JSONArray jsonArray = JSONArray.fromObject(translist);
                long trade_no = System.currentTimeMillis();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String reqBillNo = jsonObj.getString("billno");
                    String amount = jsonObj.getString("amount");

                    com.xcmis.feefax.modules.entity.Collections collections = new com.xcmis.feefax.modules.entity.Collections();
                    collections.setReqBillNo(reqBillNo);
                    com.xcmis.feefax.modules.entity.Collections c = collectionsService.get(collections);
                    bankindex = c.getBankNo();

                    Fail fail = new Fail();

                    if (c.getEraseflag() == 1) {
                        fail.setCode("15003");
                        fail.setMsg("日间交易未传送但日终对账单中存在");
                        failcount++;
                    } else if (c.getCheckflag() == 1) {
                        fail.setCode("15010");
                        fail.setMsg("对账重复发送，拒绝接收");
                        failcount++;
                    }else if (!new BigDecimal(new DecimalFormat("0.00").format(c.getChargemoney())).toString().equals(new BigDecimal(new DecimalFormat("0.00").format(Double.valueOf(amount))).toString())){
                        fail.setCode("15001");
                        fail.setMsg("财政端记录信息和对账消息中明细金额不一致");
                        failcount++;
                    }else{
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        Date dtf = format.parse(checkdate);
                        String strDateFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                        collections.setCheckdate(sdf.format(dtf));
                        collections.setCheckflag(1);
                        boolean b = collectionsService.updateCheckflag(collections);
                        if(b){
                            if(c.getReceivetype() == 2){
                                c.setCheckearne(1);
                                c.setCheckearnedate(sdf.format(dtf));
                                collectionsService.updateCheckEarne(c);
                            }
                            fail.setCode("00000");
                            fail.setMsg("成功");
                            succeedcount++;
                        }else{
                            fail.setCode("15003");
                            fail.setMsg("日间交易未传送但日终对账单中存在");
                            failcount++;
                        }


                    }
                    fail.setTrade_no(String.valueOf(trade_no + i));
                    failList.add(fail);

                }

                bizResponseContent = new BizResponseContent("00000", "成功");
                postData.put("wp_outer_account_billfund_response", bizResponseContent);
                System.out.println(postData.toString());
                out.write(postData.toString());
                out.flush();

                BillfundConfirm billfundConfirm = new BillfundConfirm();
                billfundConfirm.setDate(checkdate);
                billfundConfirm.setAccconfirm_no(accconfirmNo);
                billfundConfirm.setSucceedcount(succeedcount);
                billfundConfirm.setFailcount(failcount);
                billfundConfirm.setFaillist(failList);

                untaxExchangeMessageService.billfundConfirm(billfundConfirm, bankindex);
            }else if ("wp.outer.bill.new".equals(map.get("method"))) {

            }else if ("wp.outer.account.arrival".equals(map.get("method"))) {
                BizResponseContent bizResponseContent;
                String bizContent = map.get("biz_content");
                JSONObject bizContentObject = JSONObject.fromObject(bizContent);
                System.out.println(bizContentObject.toString());

                String arrivalTime = bizContentObject.getString("arrival_time");
                String tradeNo = bizContentObject.getString("trade_no");
                String acctype = bizContentObject.getString("rec_acctype");
                String recAcct = bizContentObject.getString("rec_acct");

                String amount = bizContentObject.getString("amt");
                String payer = bizContentObject.getString("payer_name");
                String payeraccount = bizContentObject.getString("payer_acct");
                String payerbank = bizContentObject.getString("payer_opbk");

                if(acctype.equals("6")) {
                    if (payer != null) {
                        if (!payer.equals("")) {
                            UntaxNosource untaxNosource = new UntaxNosource();
                            IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                            IncomeBankAccount incomeBankAccount = new IncomeBankAccount();
                            if (recAcct.length() == 20) {
                                incomeBankAccount.setChrCode(recAcct.substring(0, 14));
                                incomeEnterprise.setChrCode(recAcct.substring(14, 20));
                            } else if (recAcct.length() == 27) {
                                incomeBankAccount.setChrCode(recAcct.substring(0, 17));
                                incomeEnterprise.setChrCode(recAcct.substring(21, 27));
                            } else {
                                incomeBankAccount.setChrCode(recAcct);
                            }
                            if (incomeEnterprise.getChrCode() != null) {
                                if (!incomeEnterprise.getChrCode().equals("")) {
                                    incomeEnterprise = incomeEnterpriseService.get(incomeEnterprise);
                                }
                            }
                            incomeBankAccount = incomeBankAccountService.get(incomeBankAccount);

                            untaxNosource.setPayer(payer);
                            untaxNosource.setPayerbank(payerbank);
                            untaxNosource.setPayeraccount(payeraccount);
                            untaxNosource.setReceiverid(incomeBankAccount.getChrId());
                            untaxNosource.setReceiver(incomeBankAccount.getAccountName());
                            untaxNosource.setReceiverbank(incomeBankAccount.getBankName());
                            untaxNosource.setReceiveraccount(incomeBankAccount.getAccountNo());
                            untaxNosource.setBankNo(recAcct);
                            untaxNosource.setIsClaim(1);
                            untaxNosource.setIsAudit(1);

                            //目前润州专用
                            if (recAcct.equals("00025690055012") || recAcct.equals("10310901040005922")) {
                                untaxNosource.setEnId("{3B03B9E1-B580-11E6-845C-8BAB5125F985}");
                            } else {
                                untaxNosource.setEnId(incomeEnterprise.getChrId());
                            }

                            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date dtf = format.parse(arrivalTime);
                            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
                            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                            untaxNosource.setEventtime(sdf.format(dtf));
                            untaxNosource.setCheckmoney(Double.valueOf(amount));

                            untaxNosource.setSetYear(DateTimeUtils.getCurrentYear());
                            untaxNosource.setCreateDate(DateTimeUtils.getDateTimeStr1());
                            untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
                            untaxNosource.setRgCode(regionService.get(null).getChrCode());
                            untaxNosource.setBatchno(tradeNo);

                            untaxNosourceService.insert(untaxNosource);

                            bizResponseContent = new BizResponseContent("00000", "成功");
                            postData.put("wp_outer_account_arrival_response", bizResponseContent);
                            System.out.println(postData.toString());
                            out.write(postData.toString());
                            out.flush();
                        }
                    }
                }
            }else if ("wp.outer.bill.refund".equals(map.get("method"))) {
                BizResponseContent bizResponseContent;
                String bizContent = map.get("biz_content");
                System.out.println(bizContent);
                JSONObject bizContentObject = JSONObject.fromObject(bizContent);

                String reqBillNo = bizContentObject.getString("billno");
                String amount = bizContentObject.getString("pay_amount");
                String billdate = bizContentObject.getString("billdate");


                com.xcmis.feefax.modules.entity.Collections collections = new com.xcmis.feefax.modules.entity.Collections();
                collections.setReqBillNo(reqBillNo);
                com.xcmis.feefax.modules.entity.Collections c = collectionsService.get(collections);



                if (c == null) {
                    bizResponseContent = new BizResponseContent("11001", "不存在的缴款码/非税缴款书编号查询");
                } else {
                    if (c.getEraseflag() == 1) {
                        bizResponseContent = new BizResponseContent("11004", "缴款书已作废");
                    } else if (c.getPayflag() == 0) {
                        bizResponseContent = new BizResponseContent("99999", "失败");
                    }else if (!new BigDecimal(new DecimalFormat("0.00").format(c.getChargemoney())).toString().equals(new BigDecimal(new DecimalFormat("0.00").format(Double.valueOf(amount))).toString())){
                        bizResponseContent = new BizResponseContent("12004", "收款金额与财政不一致");
                    }else{
                        if(c.getReceivetype() == 1) {
                            CollectionsDetail collectionsDetail = new CollectionsDetail();
                            collectionsDetail.setMainId(c.getChrId());
                            List<CollectionsDetail> list = collectionsDetailService.findAllList(collectionsDetail);
                            c.setCollectionsDetailList(list);
                        }else if(c.getReceivetype() == 3){
                            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                            collectionsGatherDetail.setCollectId(c.getChrId());
                            List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
                            c.setCollectionsGatherDetailList(collectionGatherDetailList);
                        }else{
                            List<CollectionsDetail> collectionsDetailList = new ArrayList<>();
                            EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                            eduImportStuInfoDetail.setStuMainid(c.getChrCode4());
                            Map<String,Object> mapIn = new HashMap<>();
                            mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
                            List<EduImportStuInfoDetail> eduImportStuInfoDetailList = eduStuInfoService.getEduStuInfoDetailList(mapIn);
                            for(EduImportStuInfoDetail eisid : eduImportStuInfoDetailList) {
                                CollectionsDetail collectionsDetail = new CollectionsDetail();
                                collectionsDetail.setIncitemCode(eisid.getIncitemCode());
                                collectionsDetail.setIncitemName(eisid.getStuIncitemName());
                                collectionsDetail.setMeasure(eisid.getMeasure());
                                collectionsDetail.setChargenum(eisid.getChargenum());
                                collectionsDetail.setChargemoney(eisid.getChargemoney());
                                collectionsDetailList.add(collectionsDetail);
                            }

                            c.setCollectionsDetailList(collectionsDetailList);
                        }

                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        Date dtf = format.parse(billdate);
                        String strDateFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                        c.setRedRushDate(sdf.format(dtf));
                        c.setRedRushFlag(1);

                        boolean b = collectionsService.updateRedRushFlag(c);
                        if (!b) {
                            bizResponseContent = new BizResponseContent("99999", "失败");
                        } else {
                            bizResponseContent = new BizResponseContent("00000", "成功");
                            c.setBillstats("1");
                            BizContent bc = new BizContent(c);
                            untaxExchangeMessageService.billSync(bc);
                        }
                    }
                }
                postData.put("wp_outer_bill_refund_response", bizResponseContent);
                System.out.println(postData.toString());
                out.write(postData.toString());
                out.flush();

            }
            /*
            else if ("wp.outer.account.flow".equals(map.get("method"))) {
                BizResponseContent bizResponseContent;
                String bizContent = map.get("biz_content");
                System.out.println(bizContent);
                JSONObject bizContentObject = JSONObject.fromObject(bizContent);

                String eventtime = bizContentObject.getString("date");
                String acctype = bizContentObject.getString("acctype");
                String acct = bizContentObject.getString("acct");


                String count = bizContentObject.getString("count");

                String accflowNo = bizContentObject.getString("accflow_no");

                String bankindex = "";
                int succeedcount = 0, failcount = 0;
                List<Fail> failList = new ArrayList<>();
                succeedcount = Integer.parseInt(count);

                String translist = bizContentObject.getString("translist");
                JSONArray jsonArray = JSONArray.fromObject(translist);

                if(acctype.equals("1")) {
                    IncomeBankAccount incomeBankAccount = new IncomeBankAccount();
                    incomeBankAccount.setChrCode(acct);
                    incomeBankAccount = incomeBankAccountService.get(incomeBankAccount);
                    bankindex = incomeBankAccount.getBankNo();

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        String amount = jsonObj.getString("income_amount");
                        String payer = jsonObj.getString("acctname");
                        String payeraccount = jsonObj.getString("acct");
                        String payerbank = jsonObj.getString("acct_opbk");
                        String batchno = jsonObj.getString("batchno");
                        String addWord = jsonObj.getString("addWord");

                        if ("".equals(batchno)) {
                            if (addWord.indexOf("市非税") < 0) {
                                UntaxNosource untaxNosource = new UntaxNosource();
                                untaxNosource.setPayer(payer);
                                untaxNosource.setPayerbank(payerbank);
                                untaxNosource.setPayeraccount(payeraccount);
                                untaxNosource.setReceiverid(incomeBankAccount.getChrId());
                                untaxNosource.setReceiver(incomeBankAccount.getAccountName());
                                untaxNosource.setReceiverbank(incomeBankAccount.getBankName());
                                untaxNosource.setReceiveraccount(incomeBankAccount.getAccountNo());

                                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                Date dtf = format.parse(eventtime);
                                String strDateFormat = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                                untaxNosource.setEventtime(sdf.format(dtf));
                                untaxNosource.setCheckmoney(Double.valueOf(amount));

                                untaxNosource.setSetYear(DateTimeUtils.getCurrentYear());
                                untaxNosource.setCreateDate(DateTimeUtils.getDateTimeStr1());
                                untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
                                untaxNosource.setRgCode(regionService.get(null).getChrCode());
                                untaxNosource.setBatchno(accflowNo);

                                untaxNosourceService.insert(untaxNosource);
                            }
                        }
                    }
                }else if(acctype.equals("6")){
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);

                        String paytype = jsonObj.getString("paytype");
                        if(paytype.substring(0,1).equals("1")){
                            String subacct = jsonObj.getString("subacct");
                            String amount = jsonObj.getString("income_amount");
                            String payer = jsonObj.getString("acctname");
                            String payeraccount = jsonObj.getString("acct");
                            String payerbank = jsonObj.getString("acct_opbk");
                            String batchno = jsonObj.getString("batchno");
                            String addWord = jsonObj.getString("addWord");


                            if ("".equals(batchno)) {
                                if (addWord.indexOf("市非税") < 0) {

                                    IncomeBankAccount incomeBankAccount = new IncomeBankAccount();

                                    if(bankindex.equals("")){
                                        if(subacct.length() == 20){
                                            incomeBankAccount.setChrCode(subacct.substring(0, 14));
                                        }else{
                                            incomeBankAccount.setChrCode(subacct.substring(0, 17));
                                        }

                                        incomeBankAccount = incomeBankAccountService.get(incomeBankAccount);
                                        bankindex = incomeBankAccount.getBankNo();
                                    }


                                    UntaxNosource untaxNosource = new UntaxNosource();
                                    untaxNosource.setPayer(payer);
                                    untaxNosource.setPayerbank(payerbank);
                                    untaxNosource.setPayeraccount(payeraccount);
                                    untaxNosource.setReceiverid(incomeBankAccount.getChrId());
                                    untaxNosource.setReceiver(incomeBankAccount.getAccountName());
                                    untaxNosource.setReceiverbank(incomeBankAccount.getBankName());
                                    untaxNosource.setReceiveraccount(incomeBankAccount.getAccountNo());
                                    untaxNosource.setBankNo(subacct);

                                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                    Date dtf = format.parse(eventtime);
                                    String strDateFormat = "yyyy-MM-dd";
                                    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                                    untaxNosource.setEventtime(sdf.format(dtf));
                                    untaxNosource.setCheckmoney(Double.valueOf(amount));

                                    untaxNosource.setSetYear(DateTimeUtils.getCurrentYear());
                                    untaxNosource.setCreateDate(DateTimeUtils.getDateTimeStr1());
                                    untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
                                    untaxNosource.setRgCode(regionService.get(null).getChrCode());
                                    untaxNosource.setBatchno(accflowNo);

                                    untaxNosourceService.insert(untaxNosource);
                                }
                            }
                        }
                    }
                }

                bizResponseContent = new BizResponseContent("00000", "成功");
                postData.put("wp_outer_account_flow_response", bizResponseContent);
                System.out.println(postData.toString());
                out.write(postData.toString());
                out.flush();

                //目前测试,后面生产要修改
                FlowConfirm flowConfirm = new FlowConfirm();
                flowConfirm.setDate(eventtime);
                flowConfirm.setAccflow_no(accflowNo);
                flowConfirm.setSucceedcount(succeedcount);
                flowConfirm.setFailcount(failcount);
                flowConfirm.setFaillist(failList);

                untaxExchangeMessageService.flowConfirm(flowConfirm, bankindex);

            }
            */
        }catch(Exception e){
            e.printStackTrace();
        }
        out.close();
    }
}
