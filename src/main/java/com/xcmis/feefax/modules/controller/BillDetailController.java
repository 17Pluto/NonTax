package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.BillDetail;
import com.xcmis.feefax.modules.service.BillDetailService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private RegionService regionService;


    @RequestMapping(value = "/addBillDetailDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addBillDetailDo(BillDetail billDetail) {
        try {
            billDetail.setSetYear(DateTimeUtils.getCurrentYear());
            billDetail.setCreateDate(DateTimeUtils.getDateTimeStr1());
            billDetail.setCreateUser(UserUtils.getUserId());
            billDetail.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billDetail.setLatestOpUser(UserUtils.getUserId());
            billDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
            billDetail.setRgCode(regionService.get(null).getChrCode());
            String chrId = billDetailService.insertReturnId(billDetail);
            if(!chrId.equals("")){
               return new Result<>(billDetail.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillDetailDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillDetailDo(BillDetail billDetail) {
        try {
            billDetail.setChrCode(billDetail.getDispCode());
            billDetail.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            billDetail.setLatestOpUser(UserUtils.getUserId());
            billDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
            billDetail.setRgCode(regionService.get(null).getChrCode());
            boolean b = billDetailService.update(billDetail);
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

    @RequestMapping(value = "/delBillDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillDetail(@RequestParam(value = "chrId") String chrId) {
        try {
            BillDetail billDetail = new BillDetail();
            billDetail.setChrId(chrId);
            boolean b = billDetailService.delete(billDetail);
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

    @RequestMapping(value = "/getBillDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillDetailList(BillDetail billDetail) {
        List<BillDetail> list = billDetailService.findAllList(billDetail);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillDetailMinNo", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillDetail> getBillDetailMinNo(BillDetail billDetail) {
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        billDetail.setBilldistributer(userId + "#");
        BillDetail bd = billDetailService.getBillDetailMinNo(billDetail);
        return new Result<>(bd, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/isvalidBillNo", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> isvalidBillNo(BillDetail billDetail) {
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        billDetail.setBilldistributer(userId + "#");
        BillDetail bd = billDetailService.isvalidBillNo(billDetail);
        if(bd == null){
            return new Result<>(false, "票据号码错误!", Globals.SUCCESS_CODE);
        }else{
            return new Result<>(true, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }

    }

    @RequestMapping(value = "/findListByEnIdAndBilltypeId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> findListByEnIdAndBilltypeId(BillDetail billDetail) {
        List<BillDetail> useBillDetailList = new ArrayList<>();
        BillDetail bd = new BillDetail();
        List<BillDetail> list = billDetailService.findListByEnIdAndBilltypeId(billDetail);
        if(list != null) {
            for (int j = 0; j < list.size(); j++) {
                if(j == 0){
                    bd = list.get(j);
                    bd.setBgnBillNo(bd.getBillNo());
                    bd.setEndBillNo(bd.getBillNo());
                }else{
                    String strFormat = "";
                    for(int k = 0; k < list.get(j).getBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                    long bgnBillNo = Long.parseLong(list.get(j).getBillNo());
                    long endBillNo = Long.parseLong(bd.getEndBillNo());
                    if(bgnBillNo == endBillNo + 1){
                        bd.setEndBillNo(decimalFormat.format(bgnBillNo));
                    }else{
                        useBillDetailList.add(bd);
                        bd = new BillDetail();
                        bd = list.get(j);
                        bd.setBgnBillNo(bd.getBillNo());
                        bd.setEndBillNo(bd.getBillNo());
                    }
                }
            }
            if(list.size() > 0){
                useBillDetailList.add(bd);
            }
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", useBillDetailList);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/findListByEnIdAndBilltypeIdAndbillDistributer", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> findListByEnIdAndBilltypeIdAndbillDistributer(BillDetail billDetail) {
        billDetail.setBilldistributer(billDetail.getBilldistributer() + "#");
        List<BillDetail> useBillDetailList = new ArrayList<>();
        BillDetail bd = new BillDetail();
        List<BillDetail> list = billDetailService.findListByEnIdAndBilltypeIdAndbillDistributer(billDetail);
        if(list != null) {
            for (int j = 0; j < list.size(); j++) {
                if(j == 0){
                    bd = list.get(j);
                    bd.setBgnBillNo(bd.getBillNo());
                    bd.setEndBillNo(bd.getBillNo());
                }else{
                    String strFormat = "";
                    for(int k = 0; k < list.get(j).getBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                    long bgnBillNo = Long.parseLong(list.get(j).getBillNo());
                    long endBillNo = Long.parseLong(bd.getEndBillNo());
                    if(bgnBillNo == endBillNo + 1){
                        bd.setEndBillNo(decimalFormat.format(bgnBillNo));
                    }else{
                        useBillDetailList.add(bd);
                        bd = new BillDetail();
                        bd = list.get(j);
                        bd.setBgnBillNo(bd.getBillNo());
                        bd.setEndBillNo(bd.getBillNo());
                    }
                }
            }
            if(list.size() > 0){
                useBillDetailList.add(bd);
            }
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", useBillDetailList);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }
}
