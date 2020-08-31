package com.xcmis.feefax.modules.controller;



import com.xcmis.feefax.modules.entity.BillRecovery;
import com.xcmis.feefax.modules.service.BillRecoveryListService;
import com.xcmis.feefax.modules.service.BillRecoveryService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
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
public class BillRecoveryController {
    @Autowired
    private BillRecoveryService billRecoveryService;

    @Autowired
    private BillRecoveryListService billRecoveryListService;

    @Autowired
    private RegionService regionService;


    @RequestMapping(value = "/addBillRecoveryDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addBillRecoveryDo(@RequestBody BillRecovery billRecovery) {
        try {
            billRecovery.setBillserialNo(DateTimeUtils.getCurrentYear());
            String billserialNo = billRecoveryService.getMaxNo(billRecovery);
            if(billserialNo.equals("")){
                billserialNo = DateTimeUtils.getCurrentYear() + "00000000";
            }else{
                long tmp = Long.parseLong(billserialNo) + 1;
                billserialNo = Long.toString(tmp);
            }

            billRecovery.setSetYear(DateTimeUtils.getCurrentYear());
            billRecovery.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billRecovery.setCreateUser(UserUtils.getUserId());
            billRecovery.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billRecovery.setLastVer(DateTimeUtils.getDateTimeStr1());
            billRecovery.setRgCode(regionService.get(null).getChrCode());
            billRecovery.setBillserialNo(billserialNo);

            boolean b = billRecoveryService.insert(billRecovery);
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



    @RequestMapping(value = "/editBillRecoveryDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillRecoveryDo(@RequestBody BillRecovery billRecovery) {
        try {

            billRecovery.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billRecovery.setLatestOpUser(UserUtils.getUserId());
            billRecovery.setLastVer(DateTimeUtils.getDateTimeStr1());
            billRecovery.setRgCode(regionService.get(null).getChrCode());
            boolean b = billRecoveryService.update(billRecovery);
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

    /*
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
    */


    @RequestMapping(value = "/getBillRecoveryListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillRecoveryListByCondition(String page, String rows, String sort,
                                                                    String order, BillRecovery billRecovery) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billRecoveryService.getBillRecoveryListTotal(billRecovery);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billRecovery", billRecovery);
        List<BillRecovery> list = billRecoveryService.getBillRecoveryListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /*
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
    */
}
