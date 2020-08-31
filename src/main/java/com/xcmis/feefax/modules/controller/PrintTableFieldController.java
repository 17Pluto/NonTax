package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.PrintTableField;
import com.xcmis.feefax.modules.service.PrintTableFieldService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
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
public class PrintTableFieldController {
    @Autowired
    private PrintTableFieldService printTableFieldService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addPrintTableFieldDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addPrintTableFieldDo(PrintTableField printTableField) {
        try {
            String chrId = printTableFieldService.insertReturnId(printTableField);
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editPrintTableFieldDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editPrintTableFieldDo(PrintTableField printTableField) {
        try {
            boolean b = printTableFieldService.update(printTableField);
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

    @RequestMapping(value = "/delPrintTableField", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delPrintTableField(@RequestParam(value = "chrId") String chrId) {
        try {
            PrintTableField printTableField = new PrintTableField();
            printTableField.setChrId(chrId);
            boolean b = printTableFieldService.delete(printTableField);
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

    @RequestMapping(value = "/getPrintTableFieldListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getPrintTableFieldListByCondition(String page, String rows, String sort,
                                                                    String order, PrintTableField printTableField) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = printTableFieldService.getPrintTableFieldListTotal(printTableField);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("printTableField", printTableField);
        List<PrintTableField> list = printTableFieldService.getPrintTableFieldListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getPrintTableFieldList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getPrintTableField(PrintTableField printTableField) {
        List<PrintTableField> list = printTableFieldService.findAllList(printTableField);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



}
