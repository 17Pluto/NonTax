package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.feefax.modules.entity.BillReDisList;
import com.xcmis.feefax.modules.service.BillDistributeListService;
import com.xcmis.feefax.modules.service.BillReDisListService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
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
public class BillReDisListController {
    @Autowired
    private BillReDisListService billReDisListService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addBillReDisListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addBillReDisListDo(BillReDisList billReDisList) {
        try {
            billReDisList.setSetYear(DateTimeUtils.getCurrentYear());
            billReDisList.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billReDisList.setCreateUser(UserUtils.getUserId());
            billReDisList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billReDisList.setLatestOpUser(UserUtils.getUserId());
            billReDisList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billReDisList.setRgCode(regionService.get(null).getChrCode());
            String chrId = billReDisListService.insertReturnId(billReDisList);
            if(!chrId.equals("")){
               return new Result<>(billReDisList.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillReDisListDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillReDisListDo(BillReDisList billReDisList) {
        try {
            billReDisList.setChrCode(billReDisList.getDispCode());
            billReDisList.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billReDisList.setLatestOpUser(UserUtils.getUserId());
            billReDisList.setLastVer(DateTimeUtils.getDateTimeStr1());
            billReDisList.setRgCode(regionService.get(null).getChrCode());
            boolean b = billReDisListService.update(billReDisList);
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

    @RequestMapping(value = "/delBillReDisList", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillReDisList(@RequestParam(value = "chrId") String chrId) {
        try {
            BillReDisList billReDisList = new BillReDisList();
            billReDisList.setChrId(chrId);
            boolean b = billReDisListService.delete(billReDisList);
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


    @RequestMapping(value = "/getBillReDisListList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillReDisListList(BillReDisList billReDisList) {
        List<BillReDisList> list = billReDisListService.findAllList(billReDisList);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getUbnameIdListByBilldistributer", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUbnameIdListByBilldistributer(BillReDisList billReDisList) {
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        billReDisList.setBilldistributer(userId);

        List<BillReDisList> list = billReDisListService.getUntaxBillnameIdListByBilldistributer(billReDisList);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



}
