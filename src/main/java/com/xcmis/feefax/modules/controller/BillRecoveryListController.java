package com.xcmis.feefax.modules.controller;
/**
 * @author fangzhe
 * @date 2020-03-31
 *  controller
 */

import com.xcmis.feefax.modules.entity.BillReDisList;
import com.xcmis.feefax.modules.entity.BillRecoveryList;
import com.xcmis.feefax.modules.service.BillReDisListService;
import com.xcmis.feefax.modules.service.BillRecoveryListService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillRecoveryListController {
    @Autowired
    private BillRecoveryListService billRecoveryListService;

    @Autowired
    private BillReDisListService billReDisListService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserService userService;


    /*
    @RequestMapping(value = "/addBillPutSaleListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addBillPutSaleListDo(BillPutSaleList billPutSaleList) {
        try {
            if(billPutSaleList.getParentId().equals("allTree")){
                billPutSaleList.setParentId("");
            }
            billPutSaleList.setSetYear(DateTimeUtils.getCurrentYear());
            billPutSaleList.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billPutSaleList.setCreateUser(UserUtils.getUserId());
            billPutSaleList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billPutSaleList.setLatestOpUser(UserUtils.getUserId());
            billPutSaleList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billPutSaleList.setRgCode(regionService.get(null).getChrCode());
            String chrId = billPutSaleListService.insertReturnId(billPutSaleList);
            if(!chrId.equals("")){
               return new Result<>(billPutSaleList.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillPutSaleListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillPutSaleListDo(BillPutSaleList billPutSaleList) {
        try {
            billPutSaleList.setChrCode(billPutSaleList.getDispCode());
            billPutSaleList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billPutSaleList.setLatestOpUser(UserUtils.getUserId());
            billPutSaleList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billPutSaleList.setRgCode(regionService.get(null).getChrCode());
            boolean b = billPutSaleListService.update(billPutSaleList);
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

    @RequestMapping(value = "/delBillPutSaleList", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillPutSaleList(@RequestParam(value = "chrId") String chrId) {
        try {
            BillPutSaleList billPutSaleList = new BillPutSaleList();
            billPutSaleList.setChrId(chrId);
            boolean b = billPutSaleListService.delete(billPutSaleList);
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

    @RequestMapping(value = "/getBillRecoveryListListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillRecoveryListListByCondition(String page, String rows, String sort,
                                                                    String order, BillRecoveryList billRecoveryList) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billRecoveryListService.getBillRecoveryListListTotal(billRecoveryList);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billRecoveryList", billRecoveryList);
        List<BillRecoveryList> list = billRecoveryListService.getBillRecoveryListListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



    @RequestMapping(value = "/getBillRecoveryListList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillRecoveryListList(BillRecoveryList billRecoveryList) {
        List<BillRecoveryList> list = billRecoveryListService.findAllList(billRecoveryList);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /*
    @RequestMapping(value = "/getBillPutSaleListByUntaxBillnameId", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<BillPutSaleList>> getBillPutSaleListByUntaxBillnameId(BillPutSaleList billPutSaleList) {
        try {

            Subject subject = SecurityUtils.getSubject();
            String token = (String) subject.getPrincipal();
            String userId = JwtTokenUtil.getUserId(token);

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);


            billPutSaleList.setIsend(1);
            billPutSaleList.setEnId(user.getBelongOrg());
            List<BillPutSaleList> billPutSaleListList = billPutSaleListService.getBillPutSaleListByUntaxBillnameId(billPutSaleList);

            BillDistributeList billDistributeList = new BillDistributeList();
            billDistributeList.setEnId(user.getBelongOrg());
            billDistributeList.setUntaxBillnameId(billPutSaleList.getUntaxBillnameId());
            List<BillDistributeList> billDistributeListList = billDistributeListService.findAllList(billDistributeList);

            for(int j = 0; j < billDistributeListList.size(); j++){
                for(int i = 0; i < billPutSaleListList.size(); i++){
                    String strFormat = "";
                    for(int k = 0; k < billPutSaleListList.get(i).getBgnBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                    long bgnBillNo1 = Long.parseLong(billDistributeListList.get(j).getBgnBillNo());
                    long bgnBillNo2 = Long.parseLong(billPutSaleListList.get(i).getBgnBillNo());
                    long endBillNo1 = Long.parseLong(billDistributeListList.get(j).getEndBillNo());
                    long endBillNo2 = Long.parseLong(billPutSaleListList.get(i).getEndBillNo());

                    if(bgnBillNo1 == bgnBillNo2 && endBillNo1 == endBillNo2) {
                        billPutSaleListList.remove(i);
                        break;
                    }else if(bgnBillNo1 == bgnBillNo2 && endBillNo1 < endBillNo2){
                        String endBillNo = decimalFormat.format(endBillNo1 + 1);
                        billPutSaleListList.get(i).setBgnBillNo(endBillNo);
                        break;
                    }else if(bgnBillNo1 > bgnBillNo2 && endBillNo1 == endBillNo2){
                        String bgnBillNo = decimalFormat.format(bgnBillNo1 - 1);
                        billPutSaleListList.get(i).setEndBillNo(bgnBillNo);
                        break;
                    }else if(bgnBillNo1 > bgnBillNo2 && endBillNo1 < endBillNo2){
                        BillPutSaleList newBillPutSaleList = new BillPutSaleList();
                        BeanUtils.copyProperties(billPutSaleListList.get(i), newBillPutSaleList);


                        String bgnBillNo = decimalFormat.format(bgnBillNo1 - 1);
                        billPutSaleListList.get(i).setEndBillNo(bgnBillNo);

                        String endBillNo = decimalFormat.format(endBillNo1 + 1);
                        newBillPutSaleList.setBgnBillNo(endBillNo);
                        billPutSaleListList.add(i + 1, newBillPutSaleList);
                        break;
                    }
                }
            }
            return new Result<>(billPutSaleListList, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
    */

    @RequestMapping(value = "/getBillRecoveryListByUntaxBillnameId", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<BillRecoveryList>> getBillRecoveryListByUntaxBillnameId(BillRecoveryList billRecoveryList) {
        try {

            Subject subject = SecurityUtils.getSubject();
            String token = (String) subject.getPrincipal();
            String userId = JwtTokenUtil.getUserId(token);

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);


            //billRecoveryList.setIsend(1);
            billRecoveryList.setEnId(user.getBelongOrg());
            List<BillRecoveryList> billRecoveryListList = billRecoveryListService.getBillRecoveryListByUntaxBillnameId(billRecoveryList);

            BillReDisList billReDisList = new BillReDisList();
            billReDisList.setEnId(user.getBelongOrg());
            billReDisList.setUntaxBillnameId(billRecoveryList.getUntaxBillnameId());
            List<BillReDisList> billReDisListList = billReDisListService.findAllList(billReDisList);

            for(int j = 0; j < billReDisListList.size(); j++){
                for(int i = 0; i < billRecoveryListList.size(); i++){
                    String strFormat = "";
                    for(int k = 0; k < billRecoveryListList.get(i).getBgnBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                    long bgnBillNo1 = Long.parseLong(billReDisListList.get(j).getBgnBillNo());
                    long bgnBillNo2 = Long.parseLong(billRecoveryListList.get(i).getBgnBillNo());
                    long endBillNo1 = Long.parseLong(billReDisListList.get(j).getEndBillNo());
                    long endBillNo2 = Long.parseLong(billRecoveryListList.get(i).getEndBillNo());

                    if(bgnBillNo1 == bgnBillNo2 && endBillNo1 == endBillNo2) {
                        billRecoveryListList.remove(i);
                        break;
                    }else if(bgnBillNo1 == bgnBillNo2 && endBillNo1 < endBillNo2){
                        String endBillNo = decimalFormat.format(endBillNo1 + 1);
                        billRecoveryListList.get(i).setBgnBillNo(endBillNo);
                        break;
                    }else if(bgnBillNo1 > bgnBillNo2 && endBillNo1 == endBillNo2){
                        String bgnBillNo = decimalFormat.format(bgnBillNo1 - 1);
                        billRecoveryListList.get(i).setEndBillNo(bgnBillNo);
                        break;
                    }else if(bgnBillNo1 > bgnBillNo2 && endBillNo1 < endBillNo2){
                        BillRecoveryList newBillRecoveryList = new BillRecoveryList();
                        BeanUtils.copyProperties(billRecoveryListList.get(i), newBillRecoveryList);


                        String bgnBillNo = decimalFormat.format(bgnBillNo1 - 1);
                        billRecoveryListList.get(i).setEndBillNo(bgnBillNo);

                        String endBillNo = decimalFormat.format(endBillNo1 + 1);
                        newBillRecoveryList.setBgnBillNo(endBillNo);
                        billRecoveryListList.add(i + 1, newBillRecoveryList);
                        break;
                    }
                }
            }


            return new Result<>(billRecoveryListList, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
}
