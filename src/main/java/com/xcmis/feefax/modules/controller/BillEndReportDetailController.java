package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.feefax.modules.entity.BillEndReportDetail;
import com.xcmis.feefax.modules.service.BillDistributeListService;
import com.xcmis.feefax.modules.service.BillEndReportDetailService;
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
public class BillEndReportDetailController {
    @Autowired
    private BillEndReportDetailService billEndReportDetailService;

    @Autowired
    private RegionService regionService;


    @RequestMapping(value = "/getBillEndReportDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillEndReportDetailList(BillEndReportDetail billEndReportDetail) {
        List<BillEndReportDetail> list = billEndReportDetailService.findAllList(billEndReportDetail);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/delBillEndReportDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillEndReportDetail(@RequestParam(value = "chrId") String chrId) {
        try {
            BillEndReportDetail billEndReportDetail = new BillEndReportDetail();
            billEndReportDetail.setChrId(chrId);
            boolean b = billEndReportDetailService.delete(billEndReportDetail);
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
