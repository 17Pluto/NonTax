package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillReDisController {
    @Autowired
    private BillReDisService billReDisService;

    @Autowired
    private BillReDisListService billReDisListService;

    @Autowired
    private BillDetailService billDetailService;


    @Autowired
    private RgUserService rgUserService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addBillReDisDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addBillReDisDo(@RequestBody BillReDis billReDis) {
        try {
            boolean isRepeat = isRepeat(billReDis);
            if(!isRepeat){
                return new Result<Boolean>(false, Globals.OP_FAILURE, Globals.SUCCESS_CODE);
            }

            billReDis.setBillserialNo(DateTimeUtils.getCurrentYear());
            String billserialNo = billReDisService.getMaxNo(billReDis);
            if(billserialNo.equals("")){
                billserialNo = DateTimeUtils.getCurrentYear() + "00000000";
            }else{
                long tmp = Long.parseLong(billserialNo) + 1;
                billserialNo = Long.toString(tmp);
            }

            billReDis.setStateCode(StringUtils.stateCodeStr("000"));
            billReDis.setSetYear(DateTimeUtils.getCurrentYear());
            billReDis.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billReDis.setCreateUser(UserUtils.getUserId());
            billReDis.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billReDis.setLatestOpUser(UserUtils.getUserId());
            billReDis.setLastVer(DateTimeUtils.getDateTimeStr1());
            billReDis.setRgCode(regionService.get(null).getChrCode());
            billReDis.setBillserialNo(billserialNo);
            boolean b = billReDisService.insert(billReDis);
            if(b){
               return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillReDisDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillReDisDo(@RequestBody BillReDis billReDis) {
        try {
            boolean isRepeat = isRepeat(billReDis);
            if(!isRepeat){
                return new Result<Boolean>(false, Globals.OP_FAILURE, Globals.SUCCESS_CODE);
            }

            billReDis.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billReDis.setLatestOpUser(UserUtils.getUserId());
            billReDis.setLastVer(DateTimeUtils.getDateTimeStr1());
            billReDis.setRgCode(regionService.get(null).getChrCode());
            boolean b = billReDisService.update(billReDis);
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

    @RequestMapping(value = "/delBillReDis", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillReDis(@RequestParam(value = "chrId") String chrId) {
        try {
            BillReDis billReDis = new BillReDis();
            billReDis.setChrId(chrId);
            boolean b = billReDisService.delete(billReDis);
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

    @RequestMapping(value = "/getBillReDisListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillReDisListByCondition(String page, String rows, String sort,
                                                                    String order, BillReDis billReDis) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billReDisService.getBillReDisListTotal(billReDis);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billReDis", billReDis);
        List<BillReDis> list = billReDisService.getBillReDisListByCondition(mapIn);

        for(int i = 0; i < list.size(); i++){
            String[] tmpArr = list.get(i).getBilldistributer().split("#");
            String billdistributerName = "";
            if(tmpArr != null){
                for(String tmp : tmpArr) {
                    RgUser rgUser = new RgUser();
                    rgUser.setChrId(tmp);
                    rgUser = rgUserService.get(rgUser);
                    billdistributerName += rgUser.getChrName() + ",";
                }
            }
            list.get(i).setBilldistributerName(billdistributerName);
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getBillReDisList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillReDisList(BillReDis billReDis) {
        List<BillReDis> list = billReDisService.findAllList(billReDis);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillReDis", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillReDis> getBillReDis(String chrId) {
        try {
            BillReDis billReDis = new BillReDis();
            billReDis.setChrId(chrId);
            billReDis = billReDisService.get(billReDis);


            BillReDisList billReDisList = new BillReDisList();
            billReDisList.setMainId(chrId);
            List<BillReDisList> billReDisListList = billReDisListService.findAllList(billReDisList);

            billReDis.setBillReDisList(billReDisListList);
            return new Result<>(billReDis, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    private Boolean isRepeat(BillReDis billReDis) {
        boolean b = true;
        List<BillReDisList> billReDisList = billReDis.getBillReDisList();
        for(BillReDisList bdl : billReDisList){

            String strFormat = "";
            for(int k = 0; k < bdl.getBgnBillNo().length(); k++){
                strFormat += "0";
            }
            DecimalFormat decimalFormat = new DecimalFormat(strFormat);

            long bgnBillNo = Long.parseLong(bdl.getBgnBillNo());
            long endBillNo = Long.parseLong(bdl.getEndBillNo());
            for (long j = bgnBillNo; j <= endBillNo; j++) {
                BillDetail billDetail = new BillDetail();
                billDetail.setUntaxBillnameId(bdl.getUntaxBillnameId());
                billDetail.setBillNo(decimalFormat.format(j));
                BillDetail bd = billDetailService.get(billDetail);
                if(bd != null){
                    return false;
                }
            }
            //billDetail.setBillNo(bdl.get);
            //billDetailService.get()
            /*
            BillReDisList tmpbdl = new BillReDisList();
            tmpbdl.setUntaxBillnameId(bdl.getUntaxBillnameId());
            List<BillReDisList> toBdlList = billReDisListService.findAllList(tmpbdl);
            if(toBdlList != null){
                for(BillReDisList toBdl : toBdlList){
                    if(Math.max(Long.parseLong(bdl.getBgnBillNo()),Long.parseLong(toBdl.getBgnBillNo())) <= Math.min(Long.parseLong(bdl.getEndBillNo()),Long.parseLong(toBdl.getEndBillNo()))){
                        if(bdl.getChrId() != null){
                            if(!bdl.getChrId().equals(toBdl.getChrId())){
                                b = false;
                                break;
                            }
                        }else {
                            b = false;
                            break;
                        }
                    }
                }
                if(!b){
                    break;
                }
            }
            */
        }
        return b;
    }

    @RequestMapping(value = "/checkBillReDis", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkBillReDis(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = false;
            b = billReDisService.checkBillReDis(chrIds, stateCode);
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

    @RequestMapping(value = "/returnBillReDis", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> returnBillReDis(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;

            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                BillReDisList billReDisList = new BillReDisList();
                billReDisList.setMainId(chrId);
                List<BillReDisList> billReDisListList = billReDisListService.findAllList(billReDisList);
                for (BillReDisList bdl : billReDisListList) {
                    String strFormat = "";
                    for(int k = 0; k < bdl.getBgnBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);
                    long bgnBillNo1 = Long.parseLong(bdl.getBgnBillNo());
                    long endBillNo1 = Long.parseLong(bdl.getEndBillNo());

                    for(long i = bgnBillNo1; i <= endBillNo1; i++){
                        BillDetail billDetail = new BillDetail();
                        billDetail.setBillNo(decimalFormat.format(i));
                        billDetail.setUntaxBillnameId(bdl.getUntaxBillnameId());
                        BillDetail bd = billDetailService.get(billDetail);
                        if(bd.getState().equals("1")){
                            b = false;
                            break;
                        }
                    }
                    if(!b){
                        break;
                    }
                }
                if(!b){
                    break;
                }
            }
            if(!b){
                return new Result<>(Globals.OP_FAILURE, Globals.SUCCESS_CODE);
            }else {
                b = billReDisService.returnBillReDis(chrIds, stateCode);
                if (b) {
                    return new Result<>(true, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                } else {
                    return new Result<>(false, Globals.OP_FAILURE, Globals.SUCCESS_CODE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
}
