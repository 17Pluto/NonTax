package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.dao.RgUserDao;
import com.xcmis.feefax.modules.entity.*;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillDistributeController {
    @Autowired
    private BillDistributeService billDistributeService;

    @Autowired
    private BillDistributeListService billDistributeListService;

    @Autowired
    private BillDetailService billDetailService;


    @Autowired
    private RgUserService rgUserService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addBillDistributeDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addBillDistributeDo(@RequestBody BillDistribute billDistribute) {
        try {
            List<BillDistributeList> billDistributeList = billDistribute.getBillDistributeList();
            for(BillDistributeList bd : billDistributeList){
                if(bd.getBgnBillNo().length() != bd.getEndBillNo().length()){
                    return new Result<Boolean>(false, "票号长度不符合要求!", Globals.SUCCESS_CODE);
                }
            }

            boolean isRepeat = isRepeat(billDistribute);
            if(!isRepeat){
                return new Result<Boolean>(false, "当前明细数据起始号码与终止号码区间与已存在的数据重复！", Globals.SUCCESS_CODE);
            }

            billDistribute.setBillserialNo(DateTimeUtils.getCurrentYear());
            String billserialNo = billDistributeService.getMaxNo(billDistribute);
            if(billserialNo.equals("")){
                billserialNo = DateTimeUtils.getCurrentYear() + "00000000";
            }else{
                long tmp = Long.parseLong(billserialNo) + 1;
                billserialNo = Long.toString(tmp);
            }

            billDistribute.setStateCode(StringUtils.stateCodeStr("000"));
            billDistribute.setSetYear(DateTimeUtils.getCurrentYear());
            billDistribute.setCreateDate(DateTimeUtils.getDateTimeStr2());
            billDistribute.setCreateUser(UserUtils.getUserId());
            billDistribute.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
            billDistribute.setLatestOpUser(UserUtils.getUserId());
            billDistribute.setLastVer(DateTimeUtils.getDateTimeStr2());
            billDistribute.setRgCode(regionService.get(null).getChrCode());
            billDistribute.setBillserialNo(billserialNo);
            boolean b = billDistributeService.insert(billDistribute);
            if(b){
               return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editBillDistributeDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editBillDistributeDo(@RequestBody BillDistribute billDistribute) {
        try {
            List<BillDistributeList> billDistributeList = billDistribute.getBillDistributeList();
            for(BillDistributeList bd : billDistributeList){
                if(bd.getBgnBillNo().length() != bd.getEndBillNo().length()){
                    return new Result<Boolean>(false, "票号长度不符合要求!", Globals.SUCCESS_CODE);
                }
            }

            boolean isRepeat = isRepeat(billDistribute);
            if(!isRepeat){
                return new Result<Boolean>(false, "当前明细数据起始号码与终止号码区间与已存在的数据重复", Globals.SUCCESS_CODE);
            }

            billDistribute.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
            billDistribute.setLatestOpUser(UserUtils.getUserId());
            billDistribute.setLastVer(DateTimeUtils.getDateTimeStr2());
            billDistribute.setRgCode(regionService.get(null).getChrCode());
            boolean b = billDistributeService.update(billDistribute);
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

    @RequestMapping(value = "/delBillDistribute", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delBillDistribute(@RequestParam(value = "chrId") String chrId) {
        try {
            BillDistribute billDistribute = new BillDistribute();
            billDistribute.setChrId(chrId);
            boolean b = billDistributeService.delete(billDistribute);
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

    @RequestMapping(value = "/getBillDistributeListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillDistributeListByCondition(String page, String rows, String sort,
                                                                    String order, BillDistribute billDistribute) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = billDistributeService.getBillDistributeListTotal(billDistribute);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("billDistribute", billDistribute);
        List<BillDistribute> list = billDistributeService.getBillDistributeListByCondition(mapIn);

        for(int i = 0; i < list.size(); i++){
            String[] tmpArr = list.get(i).getBilldistributer().split("#");
            String billdistributerName = "";
            if(tmpArr != null){
                for(String tmp : tmpArr) {
                    RgUser rgUser = new RgUser();
                    rgUser.setChrId(tmp);
                    rgUser = rgUserService.get(rgUser);
                    billdistributerName += rgUser.getChrName() + ",";
                }
            }
            list.get(i).setBilldistributerName(billdistributerName);
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getBillDistributeList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getBillDistributeList(BillDistribute billDistribute) {
        List<BillDistribute> list = billDistributeService.findAllList(billDistribute);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getBillDistribute", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillDistribute> getBillDistribute(String chrId) {
        try {
            BillDistribute billDistribute = new BillDistribute();
            billDistribute.setChrId(chrId);
            billDistribute = billDistributeService.get(billDistribute);


            BillDistributeList billDistributeList = new BillDistributeList();
            billDistributeList.setMainId(chrId);
            List<BillDistributeList> billDistributeListList = billDistributeListService.findAllList(billDistributeList);

            billDistribute.setBillDistributeList(billDistributeListList);
            return new Result<>(billDistribute, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    private Boolean isRepeat(BillDistribute billDistribute) {
        boolean b = true;
        List<BillDistributeList> billDistributeList = billDistribute.getBillDistributeList();
        for(BillDistributeList bdl : billDistributeList){
            BillDistributeList tmpbdl = new BillDistributeList();
            tmpbdl.setUntaxBillnameId(bdl.getUntaxBillnameId());
            List<BillDistributeList> toBdlList = billDistributeListService.findAllList(tmpbdl);
            if(toBdlList != null){
                for(BillDistributeList toBdl : toBdlList){
                    if(Math.max(Long.parseLong(bdl.getBgnBillNo()),Long.parseLong(toBdl.getBgnBillNo())) <= Math.min(Long.parseLong(bdl.getEndBillNo()),Long.parseLong(toBdl.getEndBillNo()))){
                        if(bdl.getChrId() != null){
                            if(!bdl.getChrId().equals(toBdl.getChrId())){
                                b = false;
                                break;
                            }
                        }else {
                            b = false;
                            break;
                        }
                    }
                }
                if(!b){
                    break;
                }
            }
        }
        return b;
    }

    @RequestMapping(value = "/checkBillDistribute", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkBillDistribute(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = false;
            b = billDistributeService.checkBillDistribute(chrIds, stateCode);
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

    @RequestMapping(value = "/returnBillDistribute", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> returnBillDistribute(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;

            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                BillDistributeList billDistributeList = new BillDistributeList();
                billDistributeList.setMainId(chrId);
                List<BillDistributeList> billDistributeListList = billDistributeListService.findAllList(billDistributeList);
                for (BillDistributeList bdl : billDistributeListList) {
                    String strFormat = "";
                    for(int k = 0; k < bdl.getBgnBillNo().length(); k++){
                        strFormat += "0";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(strFormat);
                    long bgnBillNo1 = Long.parseLong(bdl.getBgnBillNo());
                    long endBillNo1 = Long.parseLong(bdl.getEndBillNo());

                    for(long i = bgnBillNo1; i <= endBillNo1; i++){
                        BillDetail billDetail = new BillDetail();
                        billDetail.setBillNo(decimalFormat.format(i));
                        billDetail.setUntaxBillnameId(bdl.getUntaxBillnameId());
                        BillDetail bd = billDetailService.get(billDetail);
                        if(bd.getState().equals("1")){
                            b = false;
                            break;
                        }
                    }
                    if(!b){
                        break;
                    }
                }
                if(!b){
                    break;
                }
            }
            if(!b){
                return new Result<>(Globals.OP_FAILURE, Globals.SUCCESS_CODE);
            }else {
                b = billDistributeService.returnBillDistribute(chrIds, stateCode);
                if (b) {
                    return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                } else {
                    return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/testBillDistribute", method = RequestMethod.GET)
    @ResponseBody
    public void testBillDistribute() {
        BillDistribute bd = new BillDistribute();
        billDistributeService.testFindAllList(bd);
    }
}
