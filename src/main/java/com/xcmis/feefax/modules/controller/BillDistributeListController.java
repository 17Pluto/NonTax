package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.BillDetail;
import com.xcmis.feefax.modules.entity.BillDistribute;
import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.feefax.modules.service.BillDetailService;
import com.xcmis.feefax.modules.service.BillDistributeListService;
import com.xcmis.feefax.modules.service.BillDistributeService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillDistributeListController {
    @Autowired
    private BillDistributeListService billDistributeListService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addBillDistributeListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addBillDistributeListDo(BillDistributeList billDistributeList) {
        try {
            billDistributeList.setSetYear(DateTimeUtils.getCurrentYear());
            billDistributeList.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billDistributeList.setCreateUser(UserUtils.getUserId());
            billDistributeList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billDistributeList.setLatestOpUser(UserUtils.getUserId());
            billDistributeList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billDistributeList.setRgCode(regionService.get(null).getChrCode());
            String chrId = billDistributeListService.insertReturnId(billDistributeList);
            if(!chrId.equals("")){
               return new Result<>(billDistributeList.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillDistributeListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillDistributeListDo(BillDistributeList billDistributeList) {
        try {
            billDistributeList.setChrCode(billDistributeList.getDispCode());
            billDistributeList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billDistributeList.setLatestOpUser(UserUtils.getUserId());
            billDistributeList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billDistributeList.setRgCode(regionService.get(null).getChrCode());
            boolean b = billDistributeListService.update(billDistributeList);
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

    @RequestMapping(value = "/delBillDistributeList", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillDistributeList(@RequestParam(value = "chrId") String chrId) {
        try {
            BillDistributeList billDistributeList = new BillDistributeList();
            billDistributeList.setChrId(chrId);
            boolean b = billDistributeListService.delete(billDistributeList);
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

    @RequestMapping(value = "/getBillDistributeListListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillDistributeListListByCondition(String page, String rows, String sort,
                                                                    String order,
                                                                           BillDistributeList billDistributeList) {
        if(!billDistributeList.getChrCode().equals("allTree")){
            billDistributeList.setDispCode(billDistributeList.getChrCode());
        }else{
            billDistributeList.setChrCode("");
            billDistributeList.setChrId("");
        }
        Map<String, Object> mapIn = new HashMap<>();

        long total = billDistributeListService.getBillDistributeListListTotal(billDistributeList);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billPutSale", billDistributeList);
        List<BillDistributeList> list = billDistributeListService.getBillDistributeListListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getBillDistributeListList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillDistributeListList(BillDistributeList billDistributeList) {
        List<BillDistributeList> list = billDistributeListService.findAllList(billDistributeList);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getUntaxBillnameIdListByBilldistributer", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxBillnameIdListByBilldistributer(BillDistributeList billDistributeList) {
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        /*
        billDistributeList.setBilldistributer(userId + "#");

        List<BillDistributeList> list = billDistributeListService.getUntaxBillnameIdListByBilldistributer(billDistributeList);

        BillDetail billDetail = new BillDetail();
        billDetail.setBilldistributer(userId + "#");
        List<BillDetail> billDetailList = billDetailService.findUntaxBill(billDetail);

        if(list == null){
            if(billDetailList != null){
                list = new ArrayList<>();
                for(BillDetail bd : billDetailList){
                    BillDistributeList bdbl = new BillDistributeList();
                    bdbl.setUntaxBillnameId(bd.getUntaxBillnameId());
                    bdbl.setUntaxBillname(bd.getUntaxBillname());
                    list.add(bdbl);
                }
            }
        }else{
            if(billDetailList != null){
                boolean b = false;
                for(BillDistributeList bdbl : list){
                    for(BillDetail bd : billDetailList){
                        if(bdbl.getUntaxBillnameId().equals(bd.getUntaxBillnameId())){

                        }
                    }
                }


            }
        }
        */
        BillDetail billDetail = new BillDetail();
        billDetail.setBilldistributer(userId + "#");
        List<BillDetail> list = billDetailService.findUntaxBill(billDetail);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



}
