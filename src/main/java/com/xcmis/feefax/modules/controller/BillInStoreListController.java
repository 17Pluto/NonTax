package com.xcmis.feefax.modules.controller;
/**
 * @author fangzhe
 * @date 2020-03-29
 * BillInStooreList controller
 */

import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.BillInStoreList;
import com.xcmis.feefax.modules.entity.BillPutSaleList;
import com.xcmis.feefax.modules.service.BillInStoreListService;
import com.xcmis.feefax.modules.service.BillInStoreService;
import com.xcmis.feefax.modules.service.BillPutSaleListService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillInStoreListController {
    @Autowired
    private BillInStoreListService billInStoreListService;

    @Autowired
    private BillPutSaleListService billPutSaleListService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addBillInStoreListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addBillInStoreListDo(BillInStoreList billInStoreList) {
        try {
            if(billInStoreList.getParentId().equals("allTree")){
                billInStoreList.setParentId("");
            }
            billInStoreList.setSetYear(DateTimeUtils.getCurrentYear());
            billInStoreList.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billInStoreList.setCreateUser(UserUtils.getUserId());
            billInStoreList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billInStoreList.setLatestOpUser(UserUtils.getUserId());
            billInStoreList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billInStoreList.setRgCode(regionService.get(null).getChrCode());
            String chrId = billInStoreListService.insertReturnId(billInStoreList);
            if(!chrId.equals("")){
               return new Result<>(billInStoreList.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillInStoreListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillInStoreListDo(BillInStoreList billInStoreList) {
        try {
            billInStoreList.setChrCode(billInStoreList.getDispCode());
            billInStoreList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billInStoreList.setLatestOpUser(UserUtils.getUserId());
            billInStoreList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billInStoreList.setRgCode(regionService.get(null).getChrCode());
            boolean b = billInStoreListService.update(billInStoreList);
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

    @RequestMapping(value = "/delBillInListStore", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillInListStore(@RequestParam(value = "chrId") String chrId) {
        try {
            BillInStoreList billInStoreList = new BillInStoreList();
            billInStoreList.setChrId(chrId);
            boolean b = billInStoreListService.delete(billInStoreList);
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

    @RequestMapping(value = "/getBillInStoreListListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillInStoreListListByCondition(String page, String rows, String sort,
                                                                    String order, BillInStoreList billInStoreList) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billInStoreListService.getBillInStoreListListTotal(billInStoreList);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billInStoreList", billInStoreList);
        List<BillInStoreList> list = billInStoreListService.getBillInStoreListListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getBillInStoreListList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillInStoreListList(BillInStoreList billInStoreList) {
        List<BillInStoreList> list = billInStoreListService.findAllList(billInStoreList);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillInStoreListByItemId", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillInStoreList> getBillInStoreListByItemId(String itemId) {
        try {
            BillInStoreList billInStoreList = new BillInStoreList();
            billInStoreList.setChrId(itemId);
            billInStoreList = billInStoreListService.get(billInStoreList);

            return new Result<>(billInStoreList, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/getBillInStoreListByUntaxBillnameId", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<BillInStoreList>> getBillInStoreListByUntaxBillnameId(BillInStoreList billInStoreList) {
        //List<BillInStoreList> newBillInStoreListList = new ArrayList<>();


        try {
            List<BillInStoreList> billInStoreListList = billInStoreListService.getBillInStoreListByUntaxBillnameId(billInStoreList);

            BillPutSaleList billPutSaleList = new BillPutSaleList();
            billPutSaleList.setUntaxBillnameId(billInStoreList.getUntaxBillnameId());
            List<BillPutSaleList> billPutSaleListList = billPutSaleListService.getBillPutSaleListByUntaxBillnameId(billPutSaleList);

            for(int j = 0; j < billPutSaleListList.size(); j++){
                for(int i = 0; i < billInStoreListList.size(); i++){
                    String strFormat = "";
                    for(int k = 0; k < billPutSaleListList.get(j).getBgnBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                    long bgnBillNo1 = Long.parseLong(billPutSaleListList.get(j).getBgnBillNo());
                    long bgnBillNo2 = Long.parseLong(billInStoreListList.get(i).getBgnBillNo());
                    long endBillNo1 = Long.parseLong(billPutSaleListList.get(j).getEndBillNo());
                    long endBillNo2 = Long.parseLong(billInStoreListList.get(i).getEndBillNo());

                    if(bgnBillNo1 == bgnBillNo2 && endBillNo1 == endBillNo2) {
                        billInStoreListList.remove(i);
                        break;
                    }else if(bgnBillNo1 == bgnBillNo2 && endBillNo1 < endBillNo2){

                        String endBillNo = decimalFormat.format(endBillNo1 + 1);
                        billInStoreListList.get(i).setBgnBillNo(endBillNo);
                        //newBillInStoreListList.add(billInStoreListList.get(i));
                        break;
                    }else if(bgnBillNo1 > bgnBillNo2 && endBillNo1 == endBillNo2){
                        String bgnBillNo = decimalFormat.format(bgnBillNo1 - 1);
                        billInStoreListList.get(i).setEndBillNo(bgnBillNo);
                        //newBillInStoreListList.add(billInStoreListList.get(i));
                        break;
                    }else if(bgnBillNo1 > bgnBillNo2 && endBillNo1 < endBillNo2){
                        BillInStoreList newBillInStoreList = new BillInStoreList();
                        BeanUtils.copyProperties(billInStoreListList.get(i), newBillInStoreList);


                        String bgnBillNo = decimalFormat.format(bgnBillNo1 - 1);
                        billInStoreListList.get(i).setEndBillNo(bgnBillNo);
                        //newBillInStoreListList.add(newBillInStoreList);


                        //newBillInStoreList = new BillInStoreList();
                        //BeanUtils.copyProperties(billInStoreListList.get(i), newBillInStoreList);
                        String endBillNo = decimalFormat.format(endBillNo1 + 1);
                        newBillInStoreList.setBgnBillNo(endBillNo);

                        billInStoreListList.add(i+1, newBillInStoreList);

                        break;
                    }
                }
            }
            return new Result<>(billInStoreListList, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
}
