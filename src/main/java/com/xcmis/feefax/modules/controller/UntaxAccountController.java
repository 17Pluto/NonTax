package com.xcmis.feefax.modules.controller;

/**
 * @author fangzhe
 * @date 2020-04-16
 */
import com.xcmis.feefax.modules.entity.BillInStore;
import com.xcmis.feefax.modules.entity.UntaxAccount;
import com.xcmis.feefax.modules.service.BillInStoreService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.UntaxAccountService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class UntaxAccountController {
    @Autowired
    private UntaxAccountService untaxAccountService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addUntaxAccountDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<UntaxAccount> addUntaxAccountDo(UntaxAccount untaxAccount) {
        try {
            untaxAccount.setSetYear(DateTimeUtils.getCurrentYear());
            untaxAccount.setCreateDate(DateTimeUtils.getDateTimeStr1());
            untaxAccount.setCreateUser(UserUtils.getUserId());
            untaxAccount.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            untaxAccount.setLatestOpUser(UserUtils.getUserId());
            untaxAccount.setLastVer(DateTimeUtils.getDateTimeStr1());
            untaxAccount.setRgCode(regionService.get(null).getChrCode());
            untaxAccount.setChrName(untaxAccount.getAccountName());
            String chrId = untaxAccountService.insertReturnId(untaxAccount);
            if(!chrId.equals("")){
               return new Result<>(untaxAccount, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editUntaxAccountDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUntaxAccountDo(UntaxAccount untaxAccount) {
        try {
            untaxAccount.setChrName(untaxAccount.getAccountName());
            untaxAccount.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            untaxAccount.setLatestOpUser(UserUtils.getUserId());
            untaxAccount.setLastVer(DateTimeUtils.getDateTimeStr1());
            untaxAccount.setRgCode(regionService.get(null).getChrCode());
            boolean b = untaxAccountService.update(untaxAccount);
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

    @RequestMapping(value = "/delUntaxAccount", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUntaxAccount(@RequestParam(value = "chrId") String chrId) {
        try {
            UntaxAccount untaxAccount = new UntaxAccount();
            untaxAccount.setChrId(chrId);
            boolean b = untaxAccountService.delete(untaxAccount);
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

    @RequestMapping(value = "/getUntaxAccountListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxAccountListByCondition(String page, String rows, String sort,
                                                                    String order, UntaxAccount untaxAccount) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = untaxAccountService.getUntaxAccountListTotal(untaxAccount);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("untaxAccount", untaxAccount);
        List<UntaxAccount> list = untaxAccountService.getUntaxAccountListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getUntaxAccountList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxAccountList(UntaxAccount untaxAccount) {
        List<UntaxAccount> list = untaxAccountService.findAllList(untaxAccount);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


//    @RequestMapping(value = "/getBillInStoreByItemId", method = RequestMethod.GET)
//    @ResponseBody
//    public Result<BillInStore> getBillInStoreByItemId(String itemId) {
//        try {
//            BillInStore billInStore = new BillInStore();
//            billInStore.setChrId(itemId);
//            billInStore = billInStoreService.get(billInStore);
//
//            return new Result<>(billInStore, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
//
//        } catch (AuthenticationException e) {
//            //e.printStackTrace();
//            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
//        }
//    }


}
