package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.BillInStoreList;
import com.xcmis.feefax.modules.entity.BillPutSaleList;
import com.xcmis.feefax.modules.service.BillInStoreListService;
import com.xcmis.feefax.modules.service.BillInStoreService;
import com.xcmis.feefax.modules.service.BillPutSaleListService;
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

import java.lang.*;


@Controller
@RequestMapping(value = "/feefax")
public class BillInStoreController {
    @Autowired
    private BillInStoreService billInStoreService;

    @Autowired
    private BillInStoreListService billInStoreListService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BillPutSaleListService billPutSaleListService;

    @RequestMapping(value = "/addBillInStoreDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addBillInStoreDo(@RequestBody BillInStore billInStore) {
        try {
            List<BillInStoreList> billInStoreList = billInStore.getBillInStoreList();
            for(BillInStoreList bd : billInStoreList){
                if(bd.getBgnBillNo().length() != bd.getEndBillNo().length()){
                    return new Result<Boolean>(false, "票号长度不符合要求!", Globals.SUCCESS_CODE);
                }
            }
            boolean isRepeat = isRepeat(billInStore);
            if(!isRepeat){
                return new Result<Boolean>(false, "当前明细数据起始号码与终止号码区间与已存在的数据重复", Globals.SUCCESS_CODE);
            }

            billInStore.setBillserialNo(DateTimeUtils.getCurrentYear());
            String billserialNo = billInStoreService.getMaxNo(billInStore);
            if(billserialNo.equals("")){
                billserialNo = DateTimeUtils.getCurrentYear() + "00000000";
            }else{
                long tmp = Long.parseLong(billserialNo) + 1;
                billserialNo = Long.toString(tmp);
            }
            billInStore.setStateCode(StringUtils.stateCodeStr("000"));
            billInStore.setSetYear(DateTimeUtils.getCurrentYear());
            billInStore.setCreateDate(DateTimeUtils.getDateTimeStr2());
            billInStore.setCreateUser(UserUtils.getUserId());
            billInStore.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
            billInStore.setLastVer(DateTimeUtils.getDateTimeStr2());
            billInStore.setMakeShanDate(DateTimeUtils.getDateTimeStr2());
            billInStore.setRgCode(regionService.get(null).getChrCode());
            billInStore.setBillserialNo(billserialNo);
            billInStore.setDeliverNo(billserialNo);
            boolean b = billInStoreService.insert(billInStore);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillInStoreDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillInStoreDo(@RequestBody BillInStore billInStore) {
        try {
            List<BillInStoreList> billInStoreList = billInStore.getBillInStoreList();
            for(BillInStoreList bd : billInStoreList){
                if(bd.getBgnBillNo().length() != bd.getEndBillNo().length()){
                    return new Result<Boolean>(false, "票号长度不符合要求!", Globals.SUCCESS_CODE);
                }
            }

            boolean isRepeat = isRepeat(billInStore);
            if(!isRepeat){
                return new Result<Boolean>(false, "当前明细数据起始号码与终止号码区间与已存在的数据重复", Globals.SUCCESS_CODE);
            }

            billInStore.setChrCode(billInStore.getDispCode());
            billInStore.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
            billInStore.setLatestOpUser(UserUtils.getUserId());
            billInStore.setLastVer(DateTimeUtils.getDateTimeStr2());
            billInStore.setRgCode(regionService.get(null).getChrCode());
            boolean b = billInStoreService.update(billInStore);
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

    @RequestMapping(value = "/delBillInStore", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillInStore(@RequestParam(value = "chrId") String chrId) {
        try {
            BillInStore billInStore = new BillInStore();
            billInStore.setChrId(chrId);
            boolean b = billInStoreService.delete(billInStore);
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

    @RequestMapping(value = "/getBillInStoreListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillInStoreListByCondition(String page, String rows, String sort,
                                                                    String order, BillInStore billInStore) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billInStoreService.getBillInStoreListTotal(billInStore);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billInStore", billInStore);
        List<BillInStore> list = billInStoreService.getBillInStoreListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getBillInStoreList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillInStoreList(BillInStore billInStore) {
        List<BillInStore> list = billInStoreService.findAllList(billInStore);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillInStore", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillInStore> getBillInStore(String chrId) {
        try {
            BillInStore billInStore = new BillInStore();
            billInStore.setChrId(chrId);
            billInStore = billInStoreService.get(billInStore);


            BillInStoreList billInStoreList = new BillInStoreList();
            billInStoreList.setMainId(chrId);
            List<BillInStoreList> billInStoreListList = billInStoreListService.findAllList(billInStoreList);

            billInStore.setBillInStoreList(billInStoreListList);
            return new Result<>(billInStore, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/checkBillInStore", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkBillInStore(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;

            b = billInStoreService.checkBillInStore(chrIds, stateCode);
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


    @RequestMapping(value = "/returnBillInStore", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> returnBillInStore(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;

            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                BillInStoreList billInStoreList = new BillInStoreList();
                billInStoreList.setMainId(chrId);
                List<BillInStoreList> billInStoreListList = billInStoreListService.findAllList(billInStoreList);
                for(BillInStoreList bisl : billInStoreListList){
                    BillPutSaleList billPutSaleList = new BillPutSaleList();
                    billPutSaleList.setUntaxBillnameId(bisl.getUntaxBillnameId());
                    List<BillPutSaleList> billPutSaleListList = billPutSaleListService.getBillPutSaleListByUntaxBillnameId(billPutSaleList);
                    for(int j = 0; j < billPutSaleListList.size(); j++) {
                        long bgnBillNo1 = Long.parseLong(billPutSaleListList.get(j).getBgnBillNo());
                        long bgnBillNo2 = Long.parseLong(bisl.getBgnBillNo());
                        long endBillNo1 = Long.parseLong(billPutSaleListList.get(j).getEndBillNo());
                        long endBillNo2 = Long.parseLong(bisl.getEndBillNo());

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
                //return new Result<>(Globals.OP_FAILURE, 2);
                return new Result<Boolean>(false, "票据已发售无法撤销", Globals.SUCCESS_CODE);
            }else {
                b = billInStoreService.returnBillInStore(chrIds, stateCode);
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


    private Boolean isRepeat(BillInStore billInStore) {
        boolean b = true;
        List<BillInStoreList> billInStoreList = billInStore.getBillInStoreList();
        for(BillInStoreList bisl : billInStoreList){
            BillInStoreList tmpBisl = new BillInStoreList();
            tmpBisl.setUntaxBillnameId(bisl.getUntaxBillnameId());
            List<BillInStoreList> toBislList = billInStoreListService.findAllList(tmpBisl);
            if(toBislList != null){
                for(BillInStoreList toBisl : toBislList){
                    if(Math.max(Long.parseLong(bisl.getBgnBillNo()),Long.parseLong(toBisl.getBgnBillNo())) <= Math.min(Long.parseLong(bisl.getEndBillNo()),Long.parseLong(toBisl.getEndBillNo()))){
                        if(bisl.getChrId() != null){
                            if(!bisl.getChrId().equals(toBisl.getChrId())){
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

}
