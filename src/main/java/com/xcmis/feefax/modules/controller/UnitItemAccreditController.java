package com.xcmis.feefax.modules.controller;

/**
 * @author fangzhe
 * @date 2020-04-10
 * AccreditDao service
 */
import com.xcmis.feefax.modules.entity.UnitItemAccredit;
import com.xcmis.feefax.modules.service.UnitItemAccreditService;
import com.xcmis.feefax.modules.service.RegionService;
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
public class UnitItemAccreditController {
    @Autowired
    private UnitItemAccreditService accreditService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addAccreditDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addAccreditDo(UnitItemAccredit accredit) {
        try {
            accredit.setSetYear(DateTimeUtils.getCurrentYear());
            accredit.setCreateDate(DateTimeUtils.getDateTimeStr1());
            accredit.setCreateUser(UserUtils.getUserId());
            accredit.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            accredit.setLatestOpUser(UserUtils.getUserId());
            accredit.setLastVer(DateTimeUtils.getDateTimeStr1());
            accredit.setRgCode(regionService.get(null).getChrCode());
            String chrId = accreditService.insertReturnId(accredit);
            if(!chrId.equals("")){
               return new Result<>(accredit.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editAccreditDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editAccreditDo(UnitItemAccredit accredit) {
        try {
            accredit.setChrCode(accredit.getDispCode());
            accredit.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            accredit.setLatestOpUser(UserUtils.getUserId());
            accredit.setLastVer(DateTimeUtils.getDateTimeStr1());
            accredit.setRgCode(regionService.get(null).getChrCode());
            boolean b = accreditService.update(accredit);
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

    @RequestMapping(value = "/delAccredit", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delAccredit(@RequestParam(value = "chrId") String chrId) {
        try {
            UnitItemAccredit accredit = new UnitItemAccredit();
            accredit.setChrId(chrId);
            boolean b = accreditService.delete(accredit);
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

    @RequestMapping(value = "/getAccreditListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getAccreditListByCondition(String page, String rows, String sort,
                                                                    String order, UnitItemAccredit accredit) {
        if(!accredit.getChrCode().equals("allTree")){
            accredit.setDispCode(accredit.getChrCode());
        }else{
            accredit.setChrCode("");
            accredit.setChrId("");
        }
        Map<String, Object> mapIn = new HashMap<>();

        long total = accreditService.getAccreditListTotal(accredit);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billInStore", accredit);
        List<UnitItemAccredit> list = accreditService.getAccreditListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getAccreditList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getAccreditList(UnitItemAccredit accredit) {
        List<UnitItemAccredit> list = accreditService.findAllList(accredit);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



}
