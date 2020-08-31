package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.BillPutSale;
import com.xcmis.feefax.modules.entity.BillPutSaleList;
import com.xcmis.feefax.modules.service.BillDistributeListService;
import com.xcmis.framework.common.utils.StringUtils;
import com.xcmis.feefax.modules.service.BillPutSaleListService;
import com.xcmis.feefax.modules.service.BillPutSaleService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillPutSaleController {
    @Autowired
    private BillPutSaleService billPutSaleService;

    @Autowired
    private BillPutSaleListService billPutSaleListService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BillDistributeListService billDistributeListService;

    @RequestMapping(value = "/addBillPutSaleDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addBillPutSaleDo(@RequestBody BillPutSale billPutSale) {
        try {
            List<BillPutSaleList> billPutSaleList = billPutSale.getBillPutSaleList();
            for(BillPutSaleList bd : billPutSaleList){
                if(bd.getBgnBillNo().length() != bd.getEndBillNo().length()){
                    return new Result<Boolean>(false, "票号长度不符合要求!", Globals.SUCCESS_CODE);
                }
            }
            boolean isRepeat = isRepeat(billPutSale);
            if(!isRepeat){
                return new Result<Boolean>(false, "当前明细数据起始号码与终止号码区间与已存在的数据重复", Globals.SUCCESS_CODE);
            }

            billPutSale.setBillserialNo(DateTimeUtils.getCurrentYear());
            String billserialNo = billPutSaleService.getMaxNo(billPutSale);
            if(billserialNo.equals("")){
                billserialNo = DateTimeUtils.getCurrentYear() + "00000000";
            }else{
                long tmp = Long.parseLong(billserialNo) + 1;
                billserialNo = Long.toString(tmp);
            }
            billPutSale.setStateCode(StringUtils.stateCodeStr("000"));
            billPutSale.setSetYear(DateTimeUtils.getCurrentYear());
            billPutSale.setCreateDate(DateTimeUtils.getDateTimeStr2());
            billPutSale.setCreateUser(UserUtils.getUserId());
            billPutSale.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
            billPutSale.setLastVer(DateTimeUtils.getDateTimeStr2());
            billPutSale.setRgCode(regionService.get(null).getChrCode());
            billPutSale.setBillserialNo(billserialNo);

            boolean b = billPutSaleService.insert(billPutSale);
            if(b){
               return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillPutSaleDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillPutSaleDo(@RequestBody BillPutSale billPutSale) {
        try {
            List<BillPutSaleList> billPutSaleList = billPutSale.getBillPutSaleList();
            for(BillPutSaleList bd : billPutSaleList){
                if(bd.getBgnBillNo().length() != bd.getEndBillNo().length()){
                    return new Result<Boolean>(false, "票号长度不符合要求!", Globals.SUCCESS_CODE);
                }
            }

            boolean isRepeat = isRepeat(billPutSale);
            if(!isRepeat){
                return new Result<Boolean>(false, "当前明细数据起始号码与终止号码区间与已存在的数据重复", Globals.SUCCESS_CODE);
            }

            billPutSale.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
            billPutSale.setLatestOpUser(UserUtils.getUserId());
            billPutSale.setLastVer(DateTimeUtils.getDateTimeStr2());
            billPutSale.setRgCode(regionService.get(null).getChrCode());
            boolean b = billPutSaleService.update(billPutSale);
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

    @RequestMapping(value = "/delBillPutSale", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillPutSale(@RequestParam(value = "chrId") String chrId) {
        try {
            BillPutSale billPutSale = new BillPutSale();
            billPutSale.setChrId(chrId);
            boolean b = billPutSaleService.delete(billPutSale);
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

    @RequestMapping(value = "/getBillPutSaleListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillPutSaleListByCondition(String page, String rows, String sort,
                                                                    String order, BillPutSale billPutSale) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billPutSaleService.getBillPutSaleListTotal(billPutSale);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billPutSale", billPutSale);
        List<BillPutSale> list = billPutSaleService.getBillPutSaleListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getBillPutSaleList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillPutSaleList(BillPutSale billPutSale) {
        List<BillPutSale> list = billPutSaleService.findAllList(billPutSale);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillPutSale", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillPutSale> getBillPutSale(String chrId) {
        try {
            BillPutSale billPutSale = new BillPutSale();
            billPutSale.setChrId(chrId);
            billPutSale = billPutSaleService.get(billPutSale);


            BillPutSaleList billPutSaleList = new BillPutSaleList();
            billPutSaleList.setMainId(chrId);
            List<BillPutSaleList> billPutSaleListList = billPutSaleListService.findAllList(billPutSaleList);

            billPutSale.setBillPutSaleList(billPutSaleListList);
            return new Result<>(billPutSale, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }



    private Boolean isRepeat(BillPutSale billPutSale) {
        boolean b = true;
        List<BillPutSaleList> billPutSaleList = billPutSale.getBillPutSaleList();
        for(BillPutSaleList bpsl : billPutSaleList){
            BillPutSaleList tmpBpsl = new BillPutSaleList();
            tmpBpsl.setUntaxBillnameId(bpsl.getUntaxBillnameId());
            List<BillPutSaleList> toBpslList = billPutSaleListService.findAllList(tmpBpsl);
            if(toBpslList != null){
                for(BillPutSaleList toBpsl : toBpslList){
                    if(Math.max(Long.parseLong(bpsl.getBgnBillNo()),Long.parseLong(toBpsl.getBgnBillNo())) <= Math.min(Long.parseLong(bpsl.getEndBillNo()),Long.parseLong(toBpsl.getEndBillNo()))){
                        if(bpsl.getChrId() != null){
                            if(!bpsl.getChrId().equals(toBpsl.getChrId())){
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
        }
        return b;
    }


    @RequestMapping(value = "/checkBillPutSale", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkBillPutSale(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = false;
            b = billPutSaleService.checkBillPutSale(chrIds, stateCode);
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

    @RequestMapping(value = "/returnBillPutSale", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> returnBillPutSale(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;

            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                BillPutSaleList billPutSaleList = new BillPutSaleList();
                billPutSaleList.setMainId(chrId);
                List<BillPutSaleList> billPutSaleListList = billPutSaleListService.findAllList(billPutSaleList);
                for(BillPutSaleList bpsl : billPutSaleListList){
                    BillDistributeList billDistributeList = new BillDistributeList();
                    billDistributeList.setUntaxBillnameId(bpsl.getUntaxBillnameId());
                    List<BillDistributeList> billDistributeListList = billDistributeListService.findAllList(billDistributeList);
                    for(int j = 0; j < billDistributeListList.size(); j++) {
                        long bgnBillNo1 = Long.parseLong(billDistributeListList.get(j).getBgnBillNo());
                        long bgnBillNo2 = Long.parseLong(bpsl.getBgnBillNo());
                        long endBillNo1 = Long.parseLong(billDistributeListList.get(j).getEndBillNo());
                        long endBillNo2 = Long.parseLong(bpsl.getEndBillNo());

                        if(bgnBillNo1 >= bgnBillNo2 && endBillNo1 <= endBillNo2) {
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
                return new Result<>(Globals.OP_FAILURE, 2);
            }else {
                b = billPutSaleService.returnBillPutSale(chrIds, stateCode);
                if (b) {
                    return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                } else {
                    return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
}
