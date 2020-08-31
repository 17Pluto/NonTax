package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.PrintTable;
import com.xcmis.feefax.modules.entity.PrintTableField;
import com.xcmis.feefax.modules.service.PrintTableFieldService;
import com.xcmis.feefax.modules.service.PrintTableService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class PrintTableController {
    @Autowired
    private PrintTableService printTableService;

    @Autowired
    private PrintTableFieldService printTableFieldService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addPrintTableDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addPrintTableDo(@RequestBody PrintTable printTable) {
        try {
            if(printTable.getUserId().equals("")){
                printTable.setUserId(UserUtils.getUserId());
            }
            String chrId = printTableService.insertReturnId(printTable);
            if(!chrId.equals("")) {
                return new Result<>(chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editPrintTableDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editPrintTableDo(@RequestBody PrintTable printTable) {
        try {
            if(printTable.getUserId().equals("")){
                printTable.setUserId(UserUtils.getUserId());
            }

            PrintTable pt = printTableService.get(printTable);


            boolean b = false;
            if(pt == null){
                String chrId = printTableService.insertReturnId(printTable);
                if(!chrId.equals("")) {
                    b = true;
                }
            }else{
                b = printTableService.update(printTable);
            }

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

    @RequestMapping(value = "/delPrintTable", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delPrintTable(@RequestBody  PrintTable printTable) {
        try {
            //PrintTable printTable = new PrintTable();
            //printTable.setChrId(chrId);
            if(!printTable.getUserId().equals("all")) {
                boolean b = printTableService.delete(printTable);
                if (b) {
                    return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                } else {
                    return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
                }
            }else{
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/getPrintTableListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getPrintTableListByCondition(String page, String rows, String sort,
                                                                    String order, PrintTable printTable) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = printTableService.getPrintTableListTotal(printTable);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("printTable", printTable);
        List<PrintTable> list = printTableService.getPrintTableListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getPrintTableList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getPrintTableList(PrintTable printTable) {
        List<PrintTable> list = printTableService.findAllList(printTable);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



    @RequestMapping(value = "/getPrintTable", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getPrintTable(PrintTable printTable) {

        printTable = printTableService.get(printTable);

        PrintTableField printTableField = new PrintTableField();
        printTableField.setMainId(printTable.getChrId());
        List<PrintTableField> printTableFieldList = printTableFieldService.findAllList(printTableField);

        printTable.setPrintTableFieldList(printTableFieldList);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("printTable", printTable);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getPrintTableByUserId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getPrintTableByUserId(PrintTable printTable) {
        printTable.setUserId(UserUtils.getUserId());
        PrintTable pt = printTableService.get(printTable);
        if(pt == null){
            printTable.setUserId("all");
            pt = printTableService.get(printTable);
        }

        PrintTableField printTableField = new PrintTableField();
        printTableField.setMainId(pt.getChrId());
        List<PrintTableField> printTableFieldList = printTableFieldService.findAllList(printTableField);

        pt.setPrintTableFieldList(printTableFieldList);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("printTable", pt);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }
}
