package com.xcmis.feefax.modules.controller;
/**
 * @author fangzhe
 * @date 2020-04-01
 *  controller
 */

import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.interceptor.NoRepeatSubmit;
import com.xcmis.framework.common.utils.ChineseNumberUtils;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/feefax")
public class CollectionsController {
    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private EduStuInfoService eduStuInfoService;

    @Autowired
    private CollectionsDetailService collectionsDetailService;

    @Autowired
    private CollectionsGatherService collectionsGatherService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private PrintTableService printTableService;

    @Autowired
    private PrintTableFieldService printTableFieldService;

    @Autowired
    private UntaxExchangeMessageService exchangeMessageService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private CollectionsGatherDetailService collectionsGatherDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private IncomeBankAccountService incomeBankAccountService;

    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;


    @Value("${issubaccount}")
    private boolean isSubAccount;


    //@NoRepeatSubmit
    @RequestMapping(value = "/addCollectionsDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addCollectionsDo(@RequestBody Collections collections) {
        try {
            BillDetail billDetail = new BillDetail();
            billDetail.setBilldistributer(UserUtils.getUserId() + "#");
            billDetail.setUntaxBillnameId(collections.getBilltypeId());
            billDetail.setEnId(collections.getEnId());
            billDetail.setBillNo(collections.getBillNo());
            BillDetail bd = billDetailService.isvalidBillNo(billDetail);
            if(bd == null){
                return new Result<>(false, "票据号码错误!", Globals.SUCCESS_CODE);
            }

            collections.setSetYear(DateTimeUtils.getCurrentYear());
            collections.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collections.setCreateUser(UserUtils.getUserId());
            collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collections.setLastestOpUser(UserUtils.getUserId());
            collections.setLastVer(DateTimeUtils.getDateTimeStr1());
            collections.setRgCode(regionService.get(null).getChrCode());
            collections.setReceivetype(1);

            String maxReqBillNo = collectionsService.getMaxReqBillNo(collections);
            collections.setReqBillNo(StringUtils.getReqBillNo(maxReqBillNo, DateTimeUtils.getCurrentYear(), regionService.get(null).getChrCode()));
            //collections.setInputername(UserUtils.getUserName());
            boolean b = collectionsService.insert(collections);
            if(b){
                return new Result<>(true, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(false,Globals.OP_FAILURE, Globals.SUCCESS_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(false, Globals.OP_FAILURE, Globals.SUCCESS_CODE);
        }
    }

    @RequestMapping(value = "/editCollectionsDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editCollectionsDo(@RequestBody Collections collections) {
        try {
            collections.setChrCode(collections.getDispCode());
            collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collections.setLastestOpUser(UserUtils.getUserId());
            collections.setLastVer(DateTimeUtils.getDateTimeStr1());
            collections.setRgCode(regionService.get(null).getChrCode());
            boolean b = collectionsService.update(collections);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/delCollections", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delCollections(@RequestParam(value = "chrId") String chrId ,@RequestParam(value = "billNo") String billNo, @RequestParam(value = "billtypeId") String billtypeId) {
        try {
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections.setBillNo(billNo);
            collections.setBilltypeId(billtypeId);
            boolean b = collectionsService.delete(collections);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/getCollectionsListByConditionForPrint", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsListByConditionForPrint(String page, String rows, String sort,
                                                                    String order, Collections collections) {
        Map<String, Object> mapIn = new HashMap<>();

        //String stateCode = collections.getStateCode();
        collections.setIsconsign(2);
        collections.setReceivetype(3);
        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        //collections.setCreateUser(UserUtils.getUserId());
        collections.setEnId(user.getBelongOrg());

        long total = collectionsService.getCollectionsListTotal(collections);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collections", collections);
        List<Collections> list = collectionsService.getCollectionsListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getCollectionsListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsListByCondition(String page, String rows, String sort,
                                                                    String order, Collections collections) {

        List<IncomeEnterprise> ieList = new ArrayList<>();
        Map<String, Object> mapIn = new HashMap<>();

        String stateCode = collections.getStateCode();

        collections.setReceivetype(1);
        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        //collections.setCreateUser(UserUtils.getUserId());
        collections.setEnId(user.getBelongOrg());
        if(collections.getIsconsign() == 1){
            String[] consigneeEnIds = collections.getConsigneeEnId().split("#");
            for (String consigneeEnId : consigneeEnIds) {
                IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                incomeEnterprise.setChrId(consigneeEnId);

                List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                for (IncomeEnterprise ie : incomeEnterpriseList) {
                    ieList.add(ie);
                }
            }
        }
        collections.setIncomeEnterpriseList(ieList);
        long total = collectionsService.getCollectionsListTotal(collections);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collections", collections);
        List<Collections> list = collectionsService.getCollectionsListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getCollectionsList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsList(Collections collections) {
        List<Collections> list = collectionsService.findAllList(collections);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getCollections", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollections(Collections collections) {
        collections = collectionsService.get(collections);

        CollectionsDetail collectionsDetail = new CollectionsDetail();
        collectionsDetail.setMainId(collections.getChrId());
        List<CollectionsDetail> collectionsDetailList = collectionsDetailService.findAllList(collectionsDetail);

        collections.setCollectionsDetailList(collectionsDetailList);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("collections", collections);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    //直接解缴作废
    @RequestMapping(value = "/cancelCollections", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> cancelCollections(@RequestParam(value = "chrIds") String chrIds) {
        try {
            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                Collections collections = new Collections();
                collections.setChrId(chrId);
                collections = collectionsService.get(collections);
                if(collections.getNosourceIds() != null){
                    if(!collections.getNosourceIds().equals("")){
                        return new Result<>(false, "票号已经跟不明款项绑定,请先到待查收入认领模块解绑!", Globals.SUCCESS_CODE);
                    }
                }

            }
            boolean b = collectionsService.cancelCollections(chrIds);
            if (b) {
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            } else {
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/printCollections", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> printCollections(@RequestParam(value = "chrIds") String chrIds) {
        List<PrintTable> list = new ArrayList<>();
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr) {
            Collections collections = new Collections();
            collections.setChrId(chrId);
            collections = collectionsService.getCollectionsForPrint(collections);
            collections.setYear(collections.getMakedate().substring(0, 4));
            collections.setMonth(collections.getMakedate().substring(5, 7));
            collections.setDay(collections.getMakedate().substring(8, 10));

            List<CollectionsDetail> collectionsDetailList = new ArrayList<>();

            if(collections.getReceivetype() == 3) {
                CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                collectionsGatherDetail.setCollectId(collections.getChrId());
                List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
                for(CollectionsGatherDetail c : collectionGatherDetailList) {
                    CollectionsDetail collectionsDetail = new CollectionsDetail();
                    collectionsDetail.setIncitemCode(c.getIncitemCode());
                    collectionsDetail.setIncitemName(c.getIncitemName());
                    collectionsDetail.setMeasure(c.getMeasure());
                    collectionsDetail.setChargestandard(c.getChargestandard());
                    collectionsDetail.setChargenum(c.getChargenum());
                    collectionsDetail.setChargemoney(c.getChargemoney());
                    collectionsDetailList.add(collectionsDetail);
                }

                Subject subject = SecurityUtils.getSubject();
                String token = (String)subject.getPrincipal();
                String userId = JwtTokenUtil.getUserId(token);
                BillDetail billDetail = new BillDetail();
                billDetail.setEnId(collections.getEnId());
                billDetail.setUntaxBillnameId(collections.getBilltypeId());
                billDetail.setBilldistributer(userId);
                BillDetail bd = billDetailService.getBillDetailMinNo(billDetail);
                if(bd != null) {
                    collections.setBillNo(bd.getBillNo());
                }
                collections.setCollectionsDetailList(collectionsDetailList);
            }else{
                CollectionsDetail collectionsDetail = new CollectionsDetail();
                collectionsDetail.setMainId(collections.getChrId());
                collectionsDetailList = collectionsDetailService.findAllList(collectionsDetail);

                collections.setCollectionsDetailList(collectionsDetailList);
            }


            PrintTable printTable = new PrintTable();
            printTable.setBilltypeId(collections.getBilltypeId());
            printTable.setUserId(UserUtils.getUserId());
            PrintTable pt = printTableService.get(printTable);
            if (pt == null) {
                printTable.setUserId("all");
                pt = printTableService.get(printTable);
            }


            PrintTableField printTableField = new PrintTableField();
            printTableField.setMainId(pt.getChrId());
            List<PrintTableField> printTableFieldList = printTableFieldService.findAllList(printTableField);


            //目前写死的
            for (PrintTableField ptf : printTableFieldList) {
                String fieldCode = ptf.getFieldCode();
                if (fieldCode.equals("qrCode")) {
                    ptf.setFieldName("http://pay.jscz.gov.cn/fntop/qrcode?billId=" + collections.getReqBillNo());
                }else if (fieldCode.equals("reqBillNo")) {
                    ptf.setFieldName(collections.getReqBillNo());
                }else if (fieldCode.equals("rgCode")) {
                    ptf.setFieldName(regionService.get(null).getChrCode());
                }else if (fieldCode.equals("billNo")) {
                    ptf.setFieldName(collections.getBillNo());
                } else if (fieldCode.equals("enCode")) {
                    if(collections.getIsconsign() == 1){
                        ptf.setFieldName(collections.getConsigneeEnCode());
                    }else{
                        ptf.setFieldName(collections.getEnCode());
                    }

                } else if (fieldCode.equals("enName")) {
                    if(collections.getIsconsign() == 1){
                        ptf.setFieldName(collections.getConsigneeEnName());
                    }else {
                        ptf.setFieldName(collections.getEnName());
                    }
                } else if (fieldCode.equals("year")) {
                    ptf.setFieldName(collections.getYear());
                } else if (fieldCode.equals("month")) {
                    ptf.setFieldName(collections.getMonth());
                } else if (fieldCode.equals("day")) {
                    ptf.setFieldName(collections.getDay());
                } else if (fieldCode.equals("payerName")) {
                    ptf.setFieldName(collections.getPayerName());
                } else if (fieldCode.equals("payeraccount")) {
                    ptf.setFieldName(collections.getPayeraccount());
                } else if (fieldCode.equals("payerbank")) {
                    ptf.setFieldName(collections.getPayerbank());
                } else if (fieldCode.equals("receiver")) {
                    ptf.setFieldName(collections.getReceiver());
                } else if (fieldCode.equals("receiveraccount")) {
                    if(isSubAccount){
                        String account = collections.getReceiveraccount();
                        String enCode = "";


                        if(collections.getIsconsign() == 1){
                            enCode = collections.getConsigneeEnCode();
                        }else{
                            enCode = collections.getEnCode();
                        }

                        if(account.length() == 14) {
                            ptf.setFieldName(account + enCode);
                        }else{
                            if(account.length() == 17) {
                                ptf.setFieldName(account + "0000" + enCode);
                            }
                        }
                    }
                } else if (fieldCode.equals("receiverbank")) {
                    ptf.setFieldName(collections.getReceiverbank());
                } else if (fieldCode.equals("remark")) {
                    ptf.setFieldName(collections.getRemark());
                } else if (fieldCode.equals("pmName")) {
                    ptf.setFieldName(collections.getPmName());
                } else if (fieldCode.equals("userName")) {
                    ptf.setFieldName(collections.getUserName());
                } else if (fieldCode.equals("allmoneycn")) {

                    ptf.setFieldName("人民币" + ChineseNumberUtils.getChineseNumber(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getAllmoney()))
                            )));
                } else if (fieldCode.equals("allmoney")) {
                    ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getAllmoney()))));
                } else if (fieldCode.equals("incitemCode0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(0).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(1).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(2).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(3).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(4).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(0).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(1).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(2).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(3).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(4).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                }
            }

            pt.setPrintTableFieldList(printTableFieldList);

            list.add(pt);
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



    @RequestMapping(value = "/updatePrintflag", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updatePrintflag(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = false;
            b = collectionsService.updatePrintflag(chrIds);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/updatePrintflagForOne", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updatePrintflagForOne(Collections collections) {
        try {
            boolean b = false;
            b = collectionsService.updatePrintflagForOne(collections);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/updateEduSendflag", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateEduSendflag(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "billstats") String billstats) {
        try {
            boolean b = false;

            String[] chrIdArr = chrIds.split(",");

            for(String chrId : chrIdArr){
                Collections collections = new Collections();
                collections.setChrCode4(chrId);
                collections = collectionsService.get(collections);
                collections.setBillstats(billstats);


                List<CollectionsDetail> collectionsDetailList = new ArrayList<>();
                EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                eduImportStuInfoDetail.setStuMainid(collections.getChrCode4());
                Map<String,Object> mapIn = new HashMap<>();
                mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
                List<EduImportStuInfoDetail> eduImportStuInfoDetailList = eduStuInfoService.getEduStuInfoDetailList(mapIn);
                for(EduImportStuInfoDetail c : eduImportStuInfoDetailList) {
                    CollectionsDetail collectionsDetail = new CollectionsDetail();
                    collectionsDetail.setIncitemCode(c.getIncitemid());
                    collectionsDetail.setIncitemName(c.getStuIncitemName());
                    collectionsDetail.setMeasure(c.getMeasure());
                    //collectionsDetail.setChargestandard(c.getChargestandard());
                    collectionsDetail.setChargenum(c.getChargenum());
                    collectionsDetail.setChargemoney(c.getChargemoney());
                    collectionsDetailList.add(collectionsDetail);
                }

                collections.setCollectionsDetailList(collectionsDetailList);


                BizContent bizContent = new BizContent(collections);
                if(isSubAccount){
                    String account = collections.getReceAccountNo();

                    if(account.length() == 14) {
                        bizContent.setRec_acct(account + collections.getEnCode());
                    }else{
                        if(account.length() == 17) {
                            bizContent.setRec_acct(account + "0000" + collections.getEnCode());
                        }
                    }
                }

                b = exchangeMessageService.billSync(bizContent);

                if(b){
                    if(billstats.equals("0")) {
                        collections.setIsSend(1);
                    }else if(billstats.equals("2")) {
                        collections.setIsSend(3);
                    }
                    collectionsService.updateIsSend(collections);
                }
            }
            return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/testUpdateSendflag", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> testUpdateSendflag() {
        try {
            boolean b = false;
            Collections c = new Collections();
            List<Collections> list1 =  collectionsService.testFindAllList(c);


            for(Collections c1 : list1){
                Collections collections = new Collections();
                collections.setChrId(c1.getChrId());
                collections = collectionsService.get(collections);
                collections.setBillstats("0");


                if(collections.getReceivetype() == 1) {
                    CollectionsDetail collectionsDetail = new CollectionsDetail();
                    collectionsDetail.setMainId(c1.getChrId());
                    List<CollectionsDetail> list = collectionsDetailService.findAllList(collectionsDetail);
                    collections.setCollectionsDetailList(list);
                }else{
                    //CollectionsGather collectionsGather = new CollectionsGather();
                    //collectionsGather.setCollectId(chrId);
                    //List<CollectionsGather> collectionGatherList = collectionsGatherService.findAllList(collectionsGather);

                    CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                    collectionsGatherDetail.setCollectId(c1.getChrId());
                    List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
                    collections.setCollectionsGatherDetailList(collectionGatherDetailList);
                }



                BizContent bizContent = new BizContent(collections);
                if(isSubAccount){
                    String account = collections.getReceAccountNo();

                    if(account.length() == 14) {
                        bizContent.setRec_acct(account + collections.getEnCode());
                    }else{
                        if(account.length() == 17) {
                            bizContent.setRec_acct(account + "0000" + collections.getEnCode());
                        }
                    }
                }

                b = exchangeMessageService.billSync(bizContent);

                if(b){
                    collections.setIsSend(1);
                    collectionsService.updateIsSend(collections);
                }
            }
            return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }



    @RequestMapping(value = "/updateSendflag", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateSendflag(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "billstats") String billstats) {
        try {
            boolean b = false;

            String[] chrIdArr = chrIds.split(",");

            for(String chrId : chrIdArr){
                Collections collections = new Collections();
                collections.setChrId(chrId);
                collections = collectionsService.get(collections);
                collections.setBillstats(billstats);


                if(collections.getReceivetype() == 1) {
                    CollectionsDetail collectionsDetail = new CollectionsDetail();
                    collectionsDetail.setMainId(chrId);
                    List<CollectionsDetail> list = collectionsDetailService.findAllList(collectionsDetail);
                    collections.setCollectionsDetailList(list);
                }else{
                    //CollectionsGather collectionsGather = new CollectionsGather();
                    //collectionsGather.setCollectId(chrId);
                    //List<CollectionsGather> collectionGatherList = collectionsGatherService.findAllList(collectionsGather);

                    CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                    collectionsGatherDetail.setCollectId(chrId);
                    List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
                    collections.setCollectionsGatherDetailList(collectionGatherDetailList);
                }



                BizContent bizContent = new BizContent(collections);
                if(isSubAccount){
                    String account = collections.getReceAccountNo();

                    if(account.length() == 14) {
                        bizContent.setRec_acct(account + collections.getEnCode());
                    }else{
                        if(account.length() == 17) {
                            bizContent.setRec_acct(account + "0000" + collections.getEnCode());
                        }
                    }
                }

                b = exchangeMessageService.billSync(bizContent);

                if(b){
                    if(billstats.equals("0")) {
                        collections.setIsSend(1);
                    }else if(billstats.equals("2")) {
                        collections.setIsSend(3);
                    }
                    collectionsService.updateIsSend(collections);
                }
            }
            return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }



    /**
     * 核收数据管理列表
     * @param collections
     * @return
     */
    @RequestMapping(value = "/getCollectionsByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsByCondition(String page, String rows, String sort,
                                                                String order, Collections collections) {
        Map<String, Object> mapIn = new HashMap<>();

        String stateCode = collections.getStateCode();

        //collections.setReceivetype(1) ;
        //collections.setCreateUser(UserUtils.getUserId());
        //核收分页
        long total = collectionsService.getCollectionsTotal(collections);

        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collections", collections);
        //核收数据全部
        List<Collections> list = collectionsService.getCollectionsByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    /**
     * 核收管理获取底部detail数据
     * @param collectId
     * @param receivetype
     * @return
     */
    @RequestMapping(value = "/getCollectionsAndDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsAndDetailList(String collectId,String receivetype) {
        CollectionsGather collectionsGather = new CollectionsGather();
        CollectionsDetail collectionsDetail = new CollectionsDetail();

        //将接收的collectId（chrId）传入 collectionsDetail中匹配mainId
        collectionsDetail.setMainId(collectId);

        collectionsGather.setCollectId(collectId);

        Map<String,Object> mapOut = new HashMap<>();
        if (receivetype.equals("3")){
            //等于3的列表
            List<CollectionsGather> collectionGatherList = collectionsGatherService.findAllList(collectionsGather);

            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
            collectionsGatherDetail.setCollectId(collectId);
            List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
            mapOut.put("list1", collectionGatherList);//要以JSON格式返回
            mapOut.put("list2", collectionGatherDetailList);//要以JSON格式返回
        }else if (receivetype.equals("1")){
            //获取等于1的列表
            List<CollectionsDetail> collectionList = collectionsDetailService.findAllListGroupByCollectId(collectionsDetail);
            //创建空数组
            List<CollectionsGatherDetail> List = new ArrayList<>();

            mapOut.put("list1", List);//要以JSON格式返回
            mapOut.put("list2",collectionList);//返回空数组collectionList

        }else if (receivetype.equals("2")){
            List<CollectionsGatherDetail> List = new ArrayList<>();

            Collections collections = new Collections();
            collections.setChrId(collectId);
            collections = collectionsService.get(collections);

            List<CollectionsDetail> collectionsDetailList = new ArrayList<>();
            EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
            eduImportStuInfoDetail.setStuMainid(collections.getChrCode4());
            Map<String,Object> mapIn = new HashMap<>();
            mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
            List<EduImportStuInfoDetail> eduImportStuInfoDetailList = eduStuInfoService.getEduStuInfoDetailList(mapIn);
            for(EduImportStuInfoDetail c : eduImportStuInfoDetailList) {
                CollectionsDetail cd = new CollectionsDetail();
                cd.setIncitemCode(c.getIncitemid());
                cd.setIncitemName(c.getStuIncitemName());
                cd.setMeasure(c.getMeasure());
                //collectionsDetail.setChargestandard(c.getChargestandard());
                cd.setChargenum(c.getChargenum());
                cd.setChargemoney(c.getChargemoney());
                collectionsDetailList.add(cd);
            }

            //collections.setCollectionsDetailList(collectionsDetailList);

            mapOut.put("list1", List);//要以JSON格式返回
            mapOut.put("list2",collectionsDetailList);//返回空数组collectionList

        }

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    /**
     * 更新核收状态位
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/updateCheckEarne", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateCheckEarne(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = false;
            b = collectionsService.updateCheckEarne(chrIds);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/updatePayflag", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updatePayflag(@RequestParam(value = "chrIds") String chrIds, String payflag) {
        try {
            boolean b = false;
            b = collectionsService.updatePayflag(chrIds, payflag);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/getCollectionsForPay", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsForPay(String page, String rows, String sort,
                                                                String order, Collections collections) {
        Map<String, Object> mapIn = new HashMap<>();
        Map<String, Object> mapOut = new HashMap<>();

        List<Collections> list = new ArrayList<>();

        if(collections.getStateCode().equals("904") && collections.getBilltypeId().equals("") && collections.getBeginBillNo().equals("") && collections.getEndBillNo().equals("") && collections.getChargemoneyStr().equals(""))
        {

            mapOut.put("total", 0);//实际的行数
            mapOut.put("rows", list);//要以JSON格式返回

            return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }else {
            Subject subject = SecurityUtils.getSubject();
            String token = (String) subject.getPrincipal();
            String userId = JwtTokenUtil.getUserId(token);

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);

            IncomeBankAccount incomeBankAccount = new IncomeBankAccount();
            incomeBankAccount.setBankId(user.getBelongOrg());

            List<IncomeBankAccount> incomeBankAccountList = new ArrayList<>();
            incomeBankAccountList = incomeBankAccountService.findAllList(incomeBankAccount);
            collections.setIncomeBankAccountList(incomeBankAccountList);

            long total = collectionsService.getCollectionsForPayTotal(collections);

            int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
            mapIn.put("startrow", startrow);
            mapIn.put("endrow", startrow + Integer.parseInt(rows));
            mapIn.put("sort", sort);
            mapIn.put("order", order);
            mapIn.put("collections", collections);
            //核收数据全部
            list = collectionsService.getCollectionsForPay(mapIn);


            mapOut.put("total", total);//实际的行数
            mapOut.put("rows", list);//要以JSON格式返回

            return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }
    }


    @RequestMapping(value = "/bindCollections", method = RequestMethod.POST)
    @ResponseBody
    public Result<Collections> bindCollections(@RequestBody Collections collections) {
        try {
            collections.setSetYear(DateTimeUtils.getCurrentYear());
            collections.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collections.setCreateUser(UserUtils.getUserId());
            collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collections.setLastestOpUser(UserUtils.getUserId());
            collections.setLastVer(DateTimeUtils.getDateTimeStr1());
            collections.setRgCode(regionService.get(null).getChrCode());
            collections.setReceivetype(1);

            String maxReqBillNo = collectionsService.getMaxReqBillNo(collections);
            collections.setReqBillNo(StringUtils.getReqBillNo(maxReqBillNo, DateTimeUtils.getCurrentYear(), regionService.get(null).getChrCode()));
            Collections c = collectionsService.bindCollections(collections);
            if(c != null){
                return new Result<>(c, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    /**
     * 学校换开打印
     * @param chrIds
     * @param billNo
     * @return
     */
    @RequestMapping(value = "/printStuCollections", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> printStuCollections(@RequestParam(value = "chrIds") String chrIds ,
                                                          String billNo) {


        List<PrintTable> list = new ArrayList<>();
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr) {
            Collections collections = new Collections();
            collections.setChrCode4(chrId);
            collections = collectionsService.getStuCollectionsForPrint(collections);
            collections.setYear(collections.getMakedate().substring(0, 4));
            collections.setMonth(collections.getMakedate().substring(5, 7));
            collections.setDay(collections.getMakedate().substring(8, 10));

            List<CollectionsDetail> collectionsDetailList = new ArrayList<>();



            if(collections.getReceivetype() == 2) {
                EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                eduImportStuInfoDetail.setStuMainid(collections.getChrCode4());
                Map<String,Object> mapIn = new HashMap<>();
                mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
                List<EduImportStuInfoDetail> eduImportStuInfoDetailList =
                        eduStuInfoService.getEduStuInfoDetailList(mapIn);
                for(EduImportStuInfoDetail c : eduImportStuInfoDetailList) {
                    CollectionsDetail collectionsDetail = new CollectionsDetail();
                    collectionsDetail.setIncitemCode(c.getIncitemCode());
                    collectionsDetail.setIncitemName(c.getStuIncitemName());
                    collectionsDetail.setMeasure(c.getMeasure());
                    //collectionsDetail.setChargestandard(c.getChargestandard());
                    collectionsDetail.setChargenum(c.getChargenum());
                    collectionsDetail.setChargemoney(c.getChargemoney());
                    collectionsDetailList.add(collectionsDetail);
                }

                Subject subject = SecurityUtils.getSubject();
                String token = (String)subject.getPrincipal();
                String userId = JwtTokenUtil.getUserId(token);
                BillDetail billDetail = new BillDetail();
                billDetail.setEnId(collections.getEnId());
                billDetail.setUntaxBillnameId(collections.getBilltypeId());
                billDetail.setBilldistributer(userId);
                BillDetail bd = billDetailService.getBillDetailMinNo(billDetail);
                if(bd != null) {
                    collections.setBillNo(billNo);
                }
                collections.setCollectionsDetailList(collectionsDetailList);
            }else{
                CollectionsDetail collectionsDetail = new CollectionsDetail();
                collectionsDetail.setMainId(collections.getChrId());
                collectionsDetailList = collectionsDetailService.findAllList(collectionsDetail);

                collections.setCollectionsDetailList(collectionsDetailList);
            }


            PrintTable printTable = new PrintTable();
            printTable.setBilltypeId(collections.getBilltypeId());
            printTable.setUserId(UserUtils.getUserId());
            PrintTable pt = printTableService.get(printTable);
            if (pt == null) {
                printTable.setUserId("all");
                pt = printTableService.get(printTable);
            }

            PrintTableField printTableField = new PrintTableField();
            printTableField.setMainId(pt.getChrId());
            List<PrintTableField> printTableFieldList = printTableFieldService.findAllList(printTableField);


            //目前写死的
            for (PrintTableField ptf : printTableFieldList) {
                String fieldCode = ptf.getFieldCode();
                if (fieldCode.equals("qrCode")) {
                    ptf.setFieldName("http://pay.jscz.gov.cn/fntop/qrcode?billId=" + collections.getReqBillNo());
                }else if (fieldCode.equals("reqBillNo")) {
                    ptf.setFieldName(collections.getReqBillNo());
                }else if (fieldCode.equals("rgCode")) {
                    ptf.setFieldName(regionService.get(null).getChrCode());
                }else if (fieldCode.equals("billNo")) {
                    ptf.setFieldName(billNo);
                } else if (fieldCode.equals("enCode")) {
                    ptf.setFieldName(collections.getEnCode());
                } else if (fieldCode.equals("enName")) {
                    ptf.setFieldName(collections.getEnName());
                } else if (fieldCode.equals("year")) {
                    ptf.setFieldName(collections.getYear());
                } else if (fieldCode.equals("month")) {
                    ptf.setFieldName(collections.getMonth());
                } else if (fieldCode.equals("day")) {
                    ptf.setFieldName(collections.getDay());
                } else if (fieldCode.equals("payerName")) {
                    ptf.setFieldName(collections.getPayerName());
                } else if (fieldCode.equals("payeraccount")) {
                    ptf.setFieldName(collections.getPayeraccount());
                } else if (fieldCode.equals("payerbank")) {
                    ptf.setFieldName(collections.getPayerbank());
                } else if (fieldCode.equals("receiver")) {
                    ptf.setFieldName(collections.getReceiver());
                } else if (fieldCode.equals("receiveraccount")) {
                    if(isSubAccount){
                        String account = collections.getReceiveraccount();

                        if(account.length() == 14) {
                            ptf.setFieldName(account + collections.getEnCode());
                        }else{
                            if(account.length() == 17) {
                                ptf.setFieldName(account + "0000" + collections.getEnCode());
                            }
                        }
                    }
                } else if (fieldCode.equals("receiverbank")) {
                    ptf.setFieldName(collections.getReceiverbank());
                } else if (fieldCode.equals("remark")) {
                    ptf.setFieldName(collections.getRemark());
                } else if (fieldCode.equals("pmName")) {
                    ptf.setFieldName(collections.getPmName());
                } else if (fieldCode.equals("userName")) {
                    ptf.setFieldName(collections.getUserName());
                } else if (fieldCode.equals("allmoneycn")) {
                    ptf.setFieldName(ChineseNumberUtils.getChineseNumber(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getAllmoney())))));
                } else if (fieldCode.equals("allmoney")) {
                    ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getAllmoney()))));
                } else if (fieldCode.equals("incitemCode0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(0).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(1).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(2).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(3).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(collections.getCollectionsDetailList().get(4).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(0).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(1).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(2).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(3).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(String.valueOf(collections.getCollectionsDetailList().get(4).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney0")) {
                    if (collectionsDetailList.size() > 0) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(0).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney1")) {
                    if (collectionsDetailList.size() > 1) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(1).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney2")) {
                    if (collectionsDetailList.size() > 2) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(2).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney3")) {
                    if (collectionsDetailList.size() > 3) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(3).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney4")) {
                    if (collectionsDetailList.size() > 4) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collections.getCollectionsDetailList().get(4).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                }
            }

            pt.setPrintTableFieldList(printTableFieldList);

            list.add(pt);
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
