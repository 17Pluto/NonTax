package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.UnitItemBank;
import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import com.xcmis.feefax.modules.service.UnitItemBankService;
import com.xcmis.feefax.modules.service.UnitItemBillFormService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
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
public class UnitItemBankController {
    @Autowired
    private UnitItemBankService unitItemBankService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addUnitItemBankDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addUnitItemBankDo(UnitItemBank unitItemBank) {
        try {
            if(unitItemBank.getParentId().equals("allTree")){
                unitItemBank.setParentId("");
            }
            unitItemBank.setSetYear(DateTimeUtils.getCurrentYear());
            unitItemBank.setCreateDate(DateTimeUtils.getDateTimeStr1());
            unitItemBank.setCreateUser(UserUtils.getUserId());
            unitItemBank.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItemBank.setLatestOpUser(UserUtils.getUserId());
            unitItemBank.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItemBank.setRgCode(unitItemBankService.get(null).getChrCode());

           // unitItemBillForm.setInItematName(null);
            unitItemBank.setDispCode(unitItemBank.getChrCode());
            String chrId = unitItemBankService.insertReturnId(unitItemBank);
            if(!chrId.equals("")){
                return new Result<>(unitItemBank.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/editUnitItemBankDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUnitItemBankDo(UnitItemBank unitItemBank) {
        try {
            unitItemBank.setChrCode(unitItemBank.getDispCode());
            unitItemBank.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItemBank.setLatestOpUser(UserUtils.getUserId());
            unitItemBank.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItemBank.setRgCode(unitItemBankService.get(null).getChrCode());

            //incomeItem.setCreateDate(DateTimeUtils.getDateTimeStr1());
            boolean b = unitItemBankService.update(unitItemBank);
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

    @RequestMapping(value = "/delUnitItemBank", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUnitItemBank(@RequestParam(value = "chrId") String chrId) {
        try {
            UnitItemBank unitItemBank = new UnitItemBank();
            unitItemBank.setChrId(chrId);
            boolean b = unitItemBankService.delete(unitItemBank);
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

    @RequestMapping(value = "/getUnitItemBankListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemBankListByCondition(String page, String rows, String sort,
                                                                         String order, UnitItemBank unitItemBank) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = unitItemBankService.getUnitItemBankListTotal(unitItemBank);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("unitItemBank", unitItemBank);
        List<UnitItemBank> list = unitItemBankService.getUnitItemBankListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getUnitItemBankList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemBankList(UnitItemBank unitItemBank) {
        List<UnitItemBank> list = unitItemBankService.findAllList(unitItemBank);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getUnitItemBankByenIdAndBilltypeId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemBankByenIdAndBilltypeId(UnitItemBank unitItemBank) {
        List<UnitItemBank> list = unitItemBankService.getUnitItemBankByenIdAndBilltypeId(unitItemBank);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getUnitItemBankByenId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemBankByenId(UnitItemBank unitItemBank) {
        String userId = UserUtils.getUserId();
        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);
        //collections.setCreateUser(UserUtils.getUserId());
        unitItemBank.setIenId(user.getBelongOrg());

        List<UnitItemBank> list = unitItemBankService.getUnitItemBankByenId(unitItemBank);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
