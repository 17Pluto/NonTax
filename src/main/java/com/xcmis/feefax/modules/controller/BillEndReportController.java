package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.BillEndReport;
import com.xcmis.feefax.modules.entity.BillEndReportDetail;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/feefax")
public class BillEndReportController {
    @Autowired
    private BillEndReportService billEndReportService;

    @Autowired
    private BillEndReportDetailService billEndReportDetailService;

    @Autowired
    private RegionService regionService;


    @RequestMapping(value = "/addBillEndReportDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addBillEndReportDo(@RequestBody BillEndReport billEndReport) {
        try {
            String billserialNo = billEndReportService.getMaxNo(billEndReport);
            if(billserialNo.equals("")){
                billserialNo = DateTimeUtils.getCurrentYear() + "00000000";
            }else{
                long tmp = Long.parseLong(billserialNo) + 1;
                billserialNo = Long.toString(tmp);
            }
            if(billEndReport.getStateCode().equals("")) {
                billEndReport.setStateCode(StringUtils.stateCodeStr("000"));
            }
            billEndReport.setSetYear(DateTimeUtils.getCurrentYear());
            billEndReport.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billEndReport.setCreateUser(UserUtils.getUserId());
            billEndReport.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billEndReport.setLatestOpUser(UserUtils.getUserId());
            billEndReport.setLastVer(DateTimeUtils.getDateTimeStr1());
            billEndReport.setRgCode(regionService.get(null).getChrCode());
            billEndReport.setBillserialNo(billserialNo);
            billEndReport.setIsend(1);
            boolean b = billEndReportService.insert(billEndReport);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/getBillEndReportListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillEndReportListByCondition(String page, String rows, String sort,
                                                                      String order, BillEndReport billEndReport) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billEndReportService.getBillEndReportListTotal(billEndReport);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billEndReport", billEndReport);
        List<BillEndReport> list = billEndReportService.getBillEndReportListByCondition(mapIn);


        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillEndReport", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillEndReport> getBillEndReport(String chrId) {
        try {
            BillEndReport billEndReport = new BillEndReport();
            billEndReport.setChrId(chrId);
            billEndReport = billEndReportService.get(billEndReport);


            BillEndReportDetail billEndReportDetail = new BillEndReportDetail();
            billEndReportDetail.setMainId(chrId);
            List<BillEndReportDetail> billEndReportDetailList = billEndReportDetailService.findAllList(billEndReportDetail);

            billEndReport.setBillEndReportDetailList(billEndReportDetailList);
            return new Result<>(billEndReport, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillEndReportDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillEndReportDo(@RequestBody BillEndReport billEndReport) {
        try {
            billEndReport.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billEndReport.setLatestOpUser(UserUtils.getUserId());
            billEndReport.setLastVer(DateTimeUtils.getDateTimeStr1());
            billEndReport.setRgCode(regionService.get(null).getChrCode());
            boolean b = billEndReportService.update(billEndReport);
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

    @RequestMapping(value = "/delBillEndReport", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillEndReport(@RequestParam(value = "chrId") String chrId) {
        try {
            BillEndReport billEndReport = new BillEndReport();
            billEndReport.setChrId(chrId);
            boolean b = billEndReportService.delete(billEndReport);
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

    @RequestMapping(value = "/returnBillEndReport", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> returnBillEndReport(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;
            b = billEndReportService.returnBillEndReport(chrIds, stateCode);
            if (b) {
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            } else {
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }



    @RequestMapping(value = "/checkBillEndReport", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkBillEndReport(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = false;
            b = billEndReportService.checkBillEndReport(chrIds, stateCode);
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
}
