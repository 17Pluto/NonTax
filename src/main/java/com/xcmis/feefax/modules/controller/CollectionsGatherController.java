package com.xcmis.feefax.modules.controller;
/**
 * @author 方哲
 * @date 2020-04-26
 *  controller
 */

import com.xcmis.feefax.modules.entity.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/feefax")
public class CollectionsGatherController {
    @Autowired
    private CollectionsGatherService collectionsGatherService;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private CollectionsGatherDetailService collectionsGatherDetailService;

    @Autowired
    private PrintTableService printTableService;

    @Autowired
    private PrintTableFieldService printTableFieldService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;

    @Value("${issubaccount}")
    private boolean isSubAccount;

    @RequestMapping(value = "/addCollectionsGatherDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addCollectionsGatherDo(@RequestBody CollectionsGather collectionsGather) {
        try {
            BillDetail billDetail = new BillDetail();
            billDetail.setBilldistributer(UserUtils.getUserId() + "#");
            billDetail.setUntaxBillnameId(collectionsGather.getBilltypeId());
            billDetail.setEnId(collectionsGather.getEnId());
            billDetail.setBillNo(collectionsGather.getBillNo());
            BillDetail bd = billDetailService.isvalidBillNo(billDetail);
            if(bd == null){
                return new Result<>(false, "票据号码错误!", Globals.SUCCESS_CODE);
            }

            collectionsGather.setSetYear(DateTimeUtils.getCurrentYear());
            collectionsGather.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setCreateUser(UserUtils.getUserId());
            collectionsGather.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setLastestOpUser(UserUtils.getUserId());
            collectionsGather.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setRgCode(regionService.get(null).getChrCode());
            //collections.setInputername(UserUtils.getUserName());
            boolean b = collectionsGatherService.insert(collectionsGather);
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

    @RequestMapping(value = "/editCollectionsGatherDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editCollectionsGatherDo(@RequestBody CollectionsGather collectionsGather) {
        try {
            collectionsGather.setChrCode(collectionsGather.getDispCode());
            collectionsGather.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setLastestOpUser(UserUtils.getUserId());
            collectionsGather.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionsGather.setRgCode(regionService.get(null).getChrCode());
            boolean b = collectionsGatherService.update(collectionsGather);
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

    @RequestMapping(value = "/delCollectionsGather", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delCollectionsGather(@RequestParam(value = "chrId") String chrId) {
        try {
            CollectionsGather collectionsGather = new CollectionsGather();
            collectionsGather.setChrId(chrId);
            boolean b = collectionsGatherService.delete(collectionsGather);
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


    //集中汇缴明细生成专用
    @RequestMapping(value = "/getCollectionsGatherListByConditionNew", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGatherListByConditionNew(String page, String rows, String sort,
                                                                          String order, CollectionsGather collectionsGather) {
        Map<String, Object> mapIn = new HashMap<>();

        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        //collections.setCreateUser(UserUtils.getUserId());
        collectionsGather.setEnId(user.getBelongOrg());



        long total = collectionsGatherService.getCollectionsGatherListTotalNew(collectionsGather);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collectionsGather", collectionsGather);
        List<CollectionsGather> list = collectionsGatherService.getCollectionsGatherListByConditionNew(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



    @RequestMapping(value = "/getCollectionsGatherListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGatherListByCondition(String page, String rows, String sort,
                                                                    String order, CollectionsGather collectionsGather) {
        List<IncomeEnterprise> ieList = new ArrayList<>();
        Map<String, Object> mapIn = new HashMap<>();

        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        //collections.setCreateUser(UserUtils.getUserId());
        collectionsGather.setEnId(user.getBelongOrg());

        if(collectionsGather.getIsconsign() == 1){
            String[] consignEnIds = collectionsGather.getConsignEnId().split("#");
            for (String consignEnId : consignEnIds) {
                IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                incomeEnterprise.setChrId(consignEnId);

                List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                for (IncomeEnterprise ie : incomeEnterpriseList) {
                    ieList.add(ie);
                }
            }
        }
        collectionsGather.setIncomeEnterpriseList(ieList);

        Map<String, Object> mapOut = new HashMap<>();
        //if(collectionsGather.getStateCode().equals("000") || collectionsGather.getStateCode().equals("901")) {
        long total = collectionsGatherService.getCollectionsGatherListTotal(collectionsGather);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collectionsGather", collectionsGather);
        List<CollectionsGather> list = collectionsGatherService.getCollectionsGatherListByCondition(mapIn);


        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回
        /*
        }else{
            Collections collections = new Collections(collectionsGather);
            collections.setReceivetype(2);
            long total = collectionsService.getCollectionsListTotal(collections);
            int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
            mapIn.put("startrow", startrow);
            mapIn.put("endrow", startrow + Integer.parseInt(rows));
            mapIn.put("sort", sort);
            mapIn.put("order", order);
            mapIn.put("collections", collections);
            List<Collections> list = collectionsService.getCollectionsListByCondition(mapIn);


            mapOut.put("total", total);//实际的行数
            mapOut.put("rows", list);//要以JSON格式返回
        }
        */
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getCollectionsGatherListByNoPrint", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGatherListByNoPrint(String page, String rows, String sort,
                                                                          String order, CollectionsGather collectionsGather) {
        Map<String, Object> mapIn = new HashMap<>();
        List<IncomeEnterprise> ieList = new ArrayList<>();

        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        collectionsGather.setEnId(user.getBelongOrg());


        if(collectionsGather.getStateCode().equals("002")){
            Collections collections = new Collections();
            collections.setReceivetype(3);
            collections.setStateCode("002");
            collections.setEnId(user.getBelongOrg());
            collections.setBilltypeId(collectionsGather.getBilltypeId());
            collections.setPayerName(collectionsGather.getPayerName());
            collections.setBeginBillNo(collectionsGather.getBeginBillNo());
            collections.setEndBillNo(collectionsGather.getEndBillNo());
            collections.setPmId(collectionsGather.getPmId());
            collections.setBeginMakedate(collectionsGather.getBeginMakedate());
            collections.setEndMakedate(collectionsGather.getEndMakedate());
            collections.setIncitemid(collectionsGather.getIncitemid());
            //collections.setCreateUser(collectionsGather.getCreateUser());


            collections.setIsconsign(collectionsGather.getIsconsign());

            if(collectionsGather.getIsconsign() == 1){
                String[] consigneeEnIds = collectionsGather.getConsigneeEnId().split("#");
                for (String consigneeEnId : consigneeEnIds) {
                    IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                    incomeEnterprise.setChrId(consigneeEnId);

                    List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                    for (IncomeEnterprise ie : incomeEnterpriseList) {
                        ieList.add(ie);
                    }
                }
                collections.setConsigneeEnId(collectionsGather.getConsigneeEnId());
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

            Map<String, Object> mapOut = new HashMap<>();
            mapOut.put("total", total);//实际的行数
            mapOut.put("rows", list);//要以JSON格式返回

            return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);


        }else {
            if(collectionsGather.getIsconsign() == 1){
                collectionsGather.setConsignEnId(collectionsGather.getConsigneeEnId());
                String[] consignEnIds = collectionsGather.getConsigneeEnId().split("#");
                for (String consignEnId : consignEnIds) {
                    IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                    incomeEnterprise.setChrId(consignEnId);

                    List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                    for (IncomeEnterprise ie : incomeEnterpriseList) {
                        ieList.add(ie);
                    }
                }
            }
            collectionsGather.setIncomeEnterpriseList(ieList);


            long total = collectionsGatherService.getCollectionsGatherListTotalNew(collectionsGather);
            int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
            mapIn.put("startrow", startrow);
            mapIn.put("endrow", startrow + Integer.parseInt(rows));
            mapIn.put("sort", sort);
            mapIn.put("order", order);
            mapIn.put("collectionsGather", collectionsGather);
            List<CollectionsGather> list = collectionsGatherService.getCollectionsGatherListByConditionNew(mapIn);

            Map<String, Object> mapOut = new HashMap<>();
            mapOut.put("total", total);//实际的行数
            mapOut.put("rows", list);//要以JSON格式返回

            return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }
    }




    @RequestMapping(value = "/getCollectionGatherList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGatherList(CollectionsGather collectionsGather) {
        List<CollectionsGather> list = collectionsGatherService.findAllList(collectionsGather);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getCollectionGatherAndDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionGatherAndDetailList(CollectionsGather collectionsGather) {
        List<CollectionsGather> collectionGatherList = collectionsGatherService.findAllList(collectionsGather);

        CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
        collectionsGatherDetail.setCollectId(collectionsGather.getCollectId());
        List<CollectionsGatherDetail> collectionGatherDetailList = collectionsGatherDetailService.findAllListGroupByCollectId(collectionsGatherDetail);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("collectionGatherList", collectionGatherList);//要以JSON格式返回
        mapOut.put("collectionGatherDetailList", collectionGatherDetailList);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getCollectionsGather", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGather(CollectionsGather collectionsGather) {
        collectionsGather = collectionsGatherService.get(collectionsGather);

        CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
        collectionsGatherDetail.setMainId(collectionsGather.getChrId());
        List<CollectionsGatherDetail> collectionsGatherDetailList =
                collectionsGatherDetailService.findAllList(collectionsGatherDetail);

        collectionsGather.setCollectionsGatherDetailList(collectionsGatherDetailList);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("collectionsGather", collectionsGather);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getCollectionsGathered", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGathered(CollectionsGather collectionsGather) {
        List<CollectionsDetail> collectionsDetailList = new ArrayList<>();

        Collections collections = new Collections();
        collections.setChrId(collectionsGather.getChrId());
        collections = collectionsService.get(collections);

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

        collections.setCollectionsDetailList(collectionsDetailList);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("collections", collections);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



    @RequestMapping(value = "/cancelCollectionsGather", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> cancelCollectionsGather(@RequestParam(value = "chrIds") String chrIds) {
        try {
            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                CollectionsGather collectionsGather = new CollectionsGather();
                collectionsGather.setChrId(chrId);
                collectionsGather = collectionsGatherService.get(collectionsGather);
                if(collectionsGather.getCollectflag() != 0){
                    return new Result<>(false, "票号已汇缴,请先撤销集中汇缴!", Globals.SUCCESS_CODE);
                }

            }
            boolean b = collectionsGatherService.cancelCollectionsGather(chrIds);
            if (b) {
                return new Result<>(true,"", Globals.SUCCESS_CODE);
            } else {
                return new Result<>(false, "处理失败", Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/getEnCode", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> getEnCode(@RequestParam(value = "chrIds") String chrIds) {
        try {
            String billtypeId = "";
            String pmId = "";
            int isconsign = -1, num = 0;
            String consignEnId = "";
            String enCode = "";
            String enId = "";
            Map<String, String> map = new HashMap<>();
            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                CollectionsGather cg = new CollectionsGather();
                cg.setChrId(chrId);
                CollectionsGather collectionsGather = collectionsGatherService.get(cg);
                if(collectionsGather.getPrintflag() == 0){
                    return new Result<>("false", "集中汇缴记录没有打印,无法汇缴!", Globals.SUCCESS_CODE);
                }
                if(num == 0){
                    billtypeId = collectionsGather.getBilltypeId();
                    pmId = collectionsGather.getPmId();
                    isconsign = collectionsGather.getIsconsign();
                    consignEnId = collectionsGather.getConsigneeEnId();
                    enId = collectionsGather.getEnId();
                    enCode = collectionsGather.getEnCode();
                    num++;
                }else{
                    if(isconsign != collectionsGather.getIsconsign()){
                        return new Result<>("false", "集中汇缴和委托集中汇缴,无法一起汇缴!", Globals.SUCCESS_CODE);
                    }
                    if(!billtypeId.equals(collectionsGather.getBilltypeId())){
                        return new Result<>("false", "集中汇缴记录票据样式不一致,无法汇缴!", Globals.SUCCESS_CODE);
                    }
                    if(!pmId.equals(collectionsGather.getPmId())){
                        return new Result<>("false", "集中汇缴记录缴款方式不一致,无法汇缴!", Globals.SUCCESS_CODE);
                    }
                    if(isconsign == 1) {
                        if (!consignEnId.equals(collectionsGather.getConsigneeEnId())) {
                            return new Result<>("false", "委托集中汇缴的委托单位不一致,无法一起汇缴!", Globals.SUCCESS_CODE);
                        }
                    }
                }
                CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                collectionsGatherDetail.setMainId(chrId);
                List<CollectionsGatherDetail> cgdList = collectionsGatherDetailService.findAllList(collectionsGatherDetail);
                for(CollectionsGatherDetail cgd : cgdList){
                    if(map.get(cgd.getIncitemCode()) == null){
                        map.put(cgd.getIncitemCode(), cgd.getIncitemCode());
                    }
                }
            }

            if(map.size() > 5){
                return new Result<>("false", "集中汇缴收入项目不能超过5个!", Globals.SUCCESS_CODE);
            }


            if(isconsign == 0) {
                return new Result<>(enCode + "," + enId, "", Globals.SUCCESS_CODE);
            }else{
                IncomeEnterprise ie = new IncomeEnterprise();
                ie.setChrId(consignEnId);
                ie = incomeEnterpriseService.get(ie);
                return new Result<>(ie.getChrCode() + "," + consignEnId, "", Globals.SUCCESS_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @NoRepeatSubmit
    @RequestMapping(value = "/generateCollectionsGather", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> generateCollectionsGather(@RequestParam(value = "chrIds") String chrIds, Collections collections) {
        try {
            String billtypeId = "";
            String pmId = "";
            int isconsign = -1, num = 0;
            String consignEnId = "";
            Map<String, String> map = new HashMap<>();
            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                CollectionsGather cg = new CollectionsGather();
                cg.setChrId(chrId);
                CollectionsGather collectionsGather = collectionsGatherService.get(cg);
                if(collectionsGather.getPrintflag() == 0){
                    return new Result<>(false, "集中汇缴记录没有打印,无法汇缴!", Globals.SUCCESS_CODE);
                }
                if(num == 0){
                    billtypeId = collectionsGather.getBilltypeId();
                    pmId = collectionsGather.getPmId();
                    isconsign = collectionsGather.getIsconsign();
                    consignEnId = collectionsGather.getConsigneeEnId();
                    num++;
                }else{
                    if(isconsign != collectionsGather.getIsconsign()){
                        return new Result<>(false, "集中汇缴和委托集中汇缴,无法一起汇缴!", Globals.SUCCESS_CODE);
                    }
                    if(!billtypeId.equals(collectionsGather.getBilltypeId())){
                        return new Result<>(false, "集中汇缴记录票据样式不一致,无法汇缴!", Globals.SUCCESS_CODE);
                    }
                    if(!pmId.equals(collectionsGather.getPmId())){
                        return new Result<>(false, "集中汇缴记录缴款方式不一致,无法汇缴!", Globals.SUCCESS_CODE);
                    }
                    if(isconsign == 1) {
                        if (!consignEnId.equals(collectionsGather.getConsigneeEnId())) {
                            return new Result<>(false, "委托集中汇缴的委托单位不一致,无法一起汇缴!", Globals.SUCCESS_CODE);
                        }
                    }
                }
                CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                collectionsGatherDetail.setMainId(chrId);
                List<CollectionsGatherDetail> cgdList = collectionsGatherDetailService.findAllList(collectionsGatherDetail);
                for(CollectionsGatherDetail cgd : cgdList){
                    if(map.get(cgd.getIncitemCode()) == null){
                        map.put(cgd.getIncitemCode(), cgd.getIncitemCode());
                    }
                }
            }

            if(map.size() > 5){
                return new Result<>(false, "集中汇缴收入项目不能超过5个!", Globals.SUCCESS_CODE);
            }


            Subject subject = SecurityUtils.getSubject();
            String token = (String)subject.getPrincipal();
            String userId = JwtTokenUtil.getUserId(token);

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);

            collections.setSetYear(DateTimeUtils.getCurrentYear());
            collections.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collections.setCreateUser(UserUtils.getUserId());
            collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            collections.setLastestOpUser(UserUtils.getUserId());
            collections.setLastVer(DateTimeUtils.getDateTimeStr1());
            collections.setRgCode(regionService.get(null).getChrCode());
            collections.setReceivetype(3);
            //collections.setPayerid(user.getBelongOrg());

            //collections.setIsconsign(0);

            collections.setIsconsign(isconsign);
            collections.setConsigneeEnId(consignEnId);

            collections.setEnId(user.getBelongOrg());
            collections.setMakedate(DateTimeUtils.getDateTimeStr2());

            //Enterprise enterprise = new Enterprise();
            //enterprise.setChrId(user.getBelongOrg());
            //enterprise = enterpriseService.get(enterprise);
            //collections.setPayerName(enterprise.getChrName());

            String maxReqBillNo = collectionsService.getMaxReqBillNo(collections);
            collections.setReqBillNo(StringUtils.getReqBillNo(maxReqBillNo, DateTimeUtils.getCurrentYear(), regionService.get(null).getChrCode()));

            boolean b = collectionsGatherService.generateCollectionsGather(collections, chrIds);
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


    @RequestMapping(value = "/printCollectionsGather", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> printCollectionsGather(@RequestParam(value = "chrIds") String chrIds) {
        List<PrintTable> list = new ArrayList<>();
        String[] chrIdArr = chrIds.split(",");
        for(String chrId : chrIdArr) {
            CollectionsGather collectionsGather = new CollectionsGather();
            collectionsGather.setChrId(chrId);
            collectionsGather = collectionsGatherService.getCollectionsGatherForPrint(collectionsGather);
            collectionsGather.setYear(collectionsGather.getMakedate().substring(0, 4));
            collectionsGather.setMonth(collectionsGather.getMakedate().substring(5, 7));
            collectionsGather.setDay(collectionsGather.getMakedate().substring(8, 10));


            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
            collectionsGatherDetail.setMainId(collectionsGather.getChrId());
            List<CollectionsGatherDetail> collectionsGatherDetailList = collectionsGatherDetailService.findAllList(collectionsGatherDetail);

            collectionsGather.setCollectionsGatherDetailList(collectionsGatherDetailList);

            PrintTable printTable = new PrintTable();
            printTable.setBilltypeId(collectionsGather.getBilltypeId());
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
                    ptf.setFieldName("http://pay.jscz.gov.cn/fntop/qrcode?billId=");
                }else if (fieldCode.equals("reqBillNo")) {
                    ptf.setFieldName("");
                }else if (fieldCode.equals("rgCode")) {
                    ptf.setFieldName(regionService.get(null).getChrCode());
                }else if (fieldCode.equals("billNo")) {
                    ptf.setFieldName(collectionsGather.getBillNo());
                } else if (fieldCode.equals("enCode")) {
                    if(collectionsGather.getIsconsign() == 1){
                        ptf.setFieldName(collectionsGather.getConsigneeEnCode());
                    }else{
                        ptf.setFieldName(collectionsGather.getEnCode());
                    }
                } else if (fieldCode.equals("enName")) {
                    if(collectionsGather.getIsconsign() == 1){
                        ptf.setFieldName(collectionsGather.getConsigneeEnName());
                    }else {
                        ptf.setFieldName(collectionsGather.getEnName());
                    }
                } else if (fieldCode.equals("year")) {
                    ptf.setFieldName(collectionsGather.getYear());
                } else if (fieldCode.equals("month")) {
                    ptf.setFieldName(collectionsGather.getMonth());
                } else if (fieldCode.equals("day")) {
                    ptf.setFieldName(collectionsGather.getDay());
                } else if (fieldCode.equals("payerName")) {
                    ptf.setFieldName(collectionsGather.getPayerName());
                } else if (fieldCode.equals("payeraccount")) {
                    ptf.setFieldName(collectionsGather.getPayeraccount());
                } else if (fieldCode.equals("payerbank")) {
                    ptf.setFieldName(collectionsGather.getPayerbank());
                } else if (fieldCode.equals("receiver")) {
                    ptf.setFieldName(collectionsGather.getReceiver());
                } else if (fieldCode.equals("receiveraccount")) {
                    if(isSubAccount){
                        String account = collectionsGather.getReceiveraccount();
                        String enCode = "";


                        if(collectionsGather.getIsconsign() == 1){
                            enCode = collectionsGather.getConsigneeEnCode();
                        }else{
                            enCode = collectionsGather.getEnCode();
                        }

                        if(account.length() == 14) {
                            ptf.setFieldName(account + enCode);
                        }else{
                            if(account.length() == 17) {
                                ptf.setFieldName(account + "0000" + enCode);
                            }
                        }
                    }
                    /*
                    if(isSubAccount){
                        String account = collectionsGather.getReceiveraccount();

                        if(account.length() == 14) {
                            ptf.setFieldName(account + collectionsGather.getEnCode());
                        }else{
                            if(account.length() == 17) {
                                ptf.setFieldName(account + "0000" + collectionsGather.getEnCode());
                            }
                        }
                    }
                    */
                } else if (fieldCode.equals("receiverbank")) {
                    ptf.setFieldName(collectionsGather.getReceiverbank());
                } else if (fieldCode.equals("remark")) {
                    ptf.setFieldName(collectionsGather.getRemark());
                } else if (fieldCode.equals("pmName")) {
                    ptf.setFieldName(collectionsGather.getPmName());
                } else if (fieldCode.equals("userName")) {
                    ptf.setFieldName(collectionsGather.getUserName());
                } else if (fieldCode.equals("allmoneycn")) {
                    ptf.setFieldName("人民币" + ChineseNumberUtils.getChineseNumber(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getAllmoney())))));
                } else if (fieldCode.equals("allmoney")) {
                    ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getAllmoney()))));
                } else if (fieldCode.equals("incitemCode0")) {
                    if (collectionsGatherDetailList.size() > 0) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(0).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode1")) {
                    if (collectionsGatherDetailList.size() > 1) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(1).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode2")) {
                    if (collectionsGatherDetailList.size() > 2) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(2).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode3")) {
                    if (collectionsGatherDetailList.size() > 3) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(3).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemCode4")) {
                    if (collectionsGatherDetailList.size() > 4) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(4).getIncitemCode());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName0")) {
                    if (collectionsGatherDetailList.size() > 0) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(0).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName1")) {
                    if (collectionsGatherDetailList.size() > 1) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(1).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName2")) {
                    if (collectionsGatherDetailList.size() > 2) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(2).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName3")) {
                    if (collectionsGatherDetailList.size() > 3) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(3).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("incitemName4")) {
                    if (collectionsGatherDetailList.size() > 4) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(4).getIncitemName());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure0")) {
                    if (collectionsGatherDetailList.size() > 0) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(0).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure1")) {
                    if (collectionsGatherDetailList.size() > 1) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(1).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure2")) {
                    if (collectionsGatherDetailList.size() > 2) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(2).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure3")) {
                    if (collectionsGatherDetailList.size() > 3) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(3).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("measure4")) {
                    if (collectionsGatherDetailList.size() > 4) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(4).getMeasure());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard0")) {
                    if (collectionsGatherDetailList.size() > 0) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(0).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard1")) {
                    if (collectionsGatherDetailList.size() > 1) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(1).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard2")) {
                    if (collectionsGatherDetailList.size() > 2) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(2).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard3")) {
                    if (collectionsGatherDetailList.size() > 3) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(3).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargestandard4")) {
                    if (collectionsGatherDetailList.size() > 4) {
                        ptf.setFieldName(collectionsGather.getCollectionsGatherDetailList().get(4).getChargestandard());
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum0")) {
                    if (collectionsGatherDetailList.size() > 0) {
                        ptf.setFieldName(String.valueOf(collectionsGather.getCollectionsGatherDetailList().get(0).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum1")) {
                    if (collectionsGatherDetailList.size() > 1) {
                        ptf.setFieldName(String.valueOf(collectionsGather.getCollectionsGatherDetailList().get(1).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum2")) {
                    if (collectionsGatherDetailList.size() > 2) {
                        ptf.setFieldName(String.valueOf(collectionsGather.getCollectionsGatherDetailList().get(2).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum3")) {
                    if (collectionsGatherDetailList.size() > 3) {
                        ptf.setFieldName(String.valueOf(collectionsGather.getCollectionsGatherDetailList().get(3).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargenum4")) {
                    if (collectionsGatherDetailList.size() > 4) {
                        ptf.setFieldName(String.valueOf(collectionsGather.getCollectionsGatherDetailList().get(4).getChargenum()));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney0")) {
                    if (collectionsGatherDetailList.size() > 0) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getCollectionsGatherDetailList().get(0).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney1")) {
                    if (collectionsGatherDetailList.size() > 1) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getCollectionsGatherDetailList().get(1).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney2")) {
                    if (collectionsGatherDetailList.size() > 2) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getCollectionsGatherDetailList().get(2).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney3")) {
                    if (collectionsGatherDetailList.size() > 3) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getCollectionsGatherDetailList().get(3).getChargemoney()))));
                    } else {
                        ptf.setFieldName("");
                    }
                } else if (fieldCode.equals("chargemoney4")) {
                    if (collectionsGatherDetailList.size() > 4) {
                        ptf.setFieldName(String.valueOf(new BigDecimal(new DecimalFormat("0.00").format(collectionsGather.getCollectionsGatherDetailList().get(4).getChargemoney()))));
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


    @RequestMapping(value = "/updateCollectionsGatherPrintflag", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateCollectionsGatherPrintflag(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = false;
            b = collectionsGatherService.updatePrintflag(chrIds);
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


    @RequestMapping(value = "/updateCollectionsGatherPrintflagForOne", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateCollectionsGatherPrintflagForOne(CollectionsGather collectionsGather) {
        try {
            boolean b = false;
            b = collectionsGatherService.updatePrintflagForOne(collectionsGather);
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


    @RequestMapping(value = "/revokeCollectionsGather", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> revokeCollectionsGather(@RequestParam(value = "chrIds") String chrIds) {
        try {
            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                //CollectionsGather collectionsGather = new CollectionsGather();
                //collectionsGather.setChrId(chrId);
                //collectionsGather.setCollectId(chrId);
                //collectionsGather = collectionsGatherService.get(collectionsGather);
                //if(collectionsGather.getCollectflag() != 0){
                    Collections c = new Collections();
                    c.setChrId(chrId);
                    c = collectionsService.get(c);
                    if(c.getPrintflag() != 0) {
                        return new Result<>(false, "票号已汇缴打印,请先集中汇缴作废!", Globals.SUCCESS_CODE);
                    }
                //}

            }

            boolean b = collectionsGatherService.revokeCollectionsGather(chrIds);
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


    @RequestMapping(value = "/revokeCollections", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> revokeCollections(@RequestParam(value = "chrIds") String chrIds) {
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

            boolean b = collectionsGatherService.revokeCollections(chrIds);
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
}
