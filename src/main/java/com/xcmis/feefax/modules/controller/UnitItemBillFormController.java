package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.IncomeItem;
import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import com.xcmis.feefax.modules.service.UnitItemBillFormService;
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
public class UnitItemBillFormController {
    @Autowired
    private UnitItemBillFormService unitItemBillFormService;

    @RequestMapping(value = "/addUnitItemBillFormDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addUnitItemBillFormDo(UnitItemBillForm unitItemBillForm) {
        try {
            if(unitItemBillForm.getParentId().equals("allTree")){
                unitItemBillForm.setParentId("");
            }
            unitItemBillForm.setSetYear(DateTimeUtils.getCurrentYear());
            unitItemBillForm.setCreateDate(DateTimeUtils.getDateTimeStr1());
            unitItemBillForm.setCreateUser(UserUtils.getUserId());
            unitItemBillForm.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItemBillForm.setLatestOpUser(UserUtils.getUserId());
            unitItemBillForm.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItemBillForm.setRgCode(unitItemBillFormService.get(null).getChrCode());

           // unitItemBillForm.setInItematName(null);
            unitItemBillForm.setDispCode(unitItemBillForm.getChrCode());
            String chrId = unitItemBillFormService.insertReturnId(unitItemBillForm);
            if(!chrId.equals("")){
                return new Result<>(unitItemBillForm.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/editUnitItemBillFormDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUnitItemBillFormDo(UnitItemBillForm unitItemBillForm) {
        try {
            unitItemBillForm.setChrCode(unitItemBillForm.getDispCode());
            unitItemBillForm.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItemBillForm.setLatestOpUser(UserUtils.getUserId());
            unitItemBillForm.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItemBillForm.setRgCode(unitItemBillFormService.get(null).getChrCode());

            //incomeItem.setCreateDate(DateTimeUtils.getDateTimeStr1());
            boolean b = unitItemBillFormService.update(unitItemBillForm);
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

    @RequestMapping(value = "/delUnitItemBillForm", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUnitItemBillForm(@RequestParam(value = "chrId") String chrId) {
        try {
            UnitItemBillForm unitItemBillForm = new UnitItemBillForm();
            unitItemBillForm.setChrId(chrId);
            boolean b = unitItemBillFormService.delete(unitItemBillForm);
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

    @RequestMapping(value = "/getUnitItemBillFormListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemBillFormListByCondition(String page, String rows, String sort,
                                                                         String order, UnitItemBillForm unitItemBillForm) {
        if(!unitItemBillForm.getChrCode().equals("allTree")){
            unitItemBillForm.setDispCode(unitItemBillForm.getChrCode());
        }else{
            unitItemBillForm.setChrCode("");
            unitItemBillForm.setChrId("");
        }
        Map<String, Object> mapIn = new HashMap<>();

        long total = unitItemBillFormService.getUnitItemBillFormListTotal(unitItemBillForm);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("unitItemBillForm", unitItemBillForm);
        List<UnitItemBillForm> list = unitItemBillFormService.getUnitItemBillFormListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getUnitItemBillFormList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemBillFormList(UnitItemBillForm unitItemBillForm) {
        List<UnitItemBillForm> list = unitItemBillFormService.findAllList(unitItemBillForm);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
