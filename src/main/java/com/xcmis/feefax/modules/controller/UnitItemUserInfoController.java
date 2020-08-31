package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.UnitItemBillForm;
import com.xcmis.feefax.modules.entity.UnitItemUserInfo;
import com.xcmis.feefax.modules.service.UnitItemBillFormService;
import com.xcmis.feefax.modules.service.UnitItemUserInfoService;
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
public class UnitItemUserInfoController {
    @Autowired
    private UnitItemUserInfoService unitItemUserInfoService;

    @RequestMapping(value = "/addUnitItemUserInfoDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addUnitItemUserInfoDo(UnitItemUserInfo unitItemUserInfo) {
        try {
            if(unitItemUserInfo.getParentId().equals("allTree")){
                unitItemUserInfo.setParentId("");
            }
            unitItemUserInfo.setSetYear(DateTimeUtils.getCurrentYear());
            unitItemUserInfo.setCreateDate(DateTimeUtils.getDateTimeStr1());
            unitItemUserInfo.setCreateUser(UserUtils.getUserId());
            unitItemUserInfo.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItemUserInfo.setLatestOpUser(UserUtils.getUserId());
            unitItemUserInfo.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItemUserInfo.setRgCode(unitItemUserInfoService.get(null).getChrCode());
            unitItemUserInfo.setDispCode(unitItemUserInfo.getChrCode());
            String chrId = unitItemUserInfoService.insertReturnId(unitItemUserInfo);
            if(!chrId.equals("")){
                return new Result<>(unitItemUserInfo.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/editUnitItemUserInfoDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUnitItemUserInfoDo(UnitItemUserInfo unitItemUserInfo) {
        try {
            unitItemUserInfo.setChrCode(unitItemUserInfo.getDispCode());
            unitItemUserInfo.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItemUserInfo.setLatestOpUser(UserUtils.getUserId());
            unitItemUserInfo.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItemUserInfo.setRgCode(unitItemUserInfoService.get(null).getChrCode());

            //incomeItem.setCreateDate(DateTimeUtils.getDateTimeStr1());
            boolean b = unitItemUserInfoService.update(unitItemUserInfo);
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

    @RequestMapping(value = "/delUnitItemUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUnitItemUserInfo(@RequestParam(value = "chrId") String chrId) {
        try {
            UnitItemUserInfo unitItemBillForm = new UnitItemUserInfo();
            unitItemBillForm.setChrId(chrId);
            boolean b = unitItemUserInfoService.delete(unitItemBillForm);
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

    @RequestMapping(value = "/getUnitItemUserInfoListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemUserInfoListByCondition(String page, String rows, String sort,
                                                                         String order, UnitItemUserInfo unitItemUserInfo) {
        if(!unitItemUserInfo.getChrCode().equals("allTree")){
            unitItemUserInfo.setDispCode(unitItemUserInfo.getChrCode());
        }else{
            unitItemUserInfo.setChrCode("");
            unitItemUserInfo.setChrId("");
        }
        Map<String, Object> mapIn = new HashMap<>();

        long total = unitItemUserInfoService.getUnitItemUserInfoListTotal(unitItemUserInfo);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("unitItemUserInfo", unitItemUserInfo);
        List<UnitItemUserInfo> list = unitItemUserInfoService.getUnitItemUserInfoListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getUnitItemUserInfoList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemUserInfoList(UnitItemUserInfo unitItemUserInfo) {
        List<UnitItemUserInfo> list = unitItemUserInfoService.findAllList(unitItemUserInfo);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
