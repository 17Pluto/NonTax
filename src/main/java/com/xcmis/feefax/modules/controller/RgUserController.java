package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.RgUser;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.RgUserService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class RgUserController {
    @Autowired
    private RgUserService rgUserService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/getRgUserListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getRgUserListByCondition(String page, String rows, String sort, String order,
                                                               RgUser rgUser) {
        if(!rgUser.getChrCode().equals("allTree")){
            rgUser.setDispCode(rgUser.getChrCode());
        }else{
            rgUser.setChrCode("");
            rgUser.setChrId("");
        }
        Map<String, Object> mapIn = new HashMap<>();

        long total = rgUserService.getRgUserListTotal(rgUser);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("rgUser", rgUser);
        List<RgUser> list = rgUserService.getRgUserListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getRgUserList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getRgUserList(RgUser rgUser) {
        List<RgUser> list = rgUserService.findAllList(rgUser);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
