package com.xcmis.feefax.modules.controller;
/**
 * @author fangzhe
 * @date 2020-03-25
 * 执收单位收入项目controller
 */
import com.xcmis.feefax.modules.dao.UnitItemBillFormDao;
import com.xcmis.feefax.modules.dao.UnitItemUserInfoDao;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
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
public class UnitItemController {
    @Autowired
    private UnitItemService unitItemService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UnitItemUserInfoService unitItemUserInfoService;

    @Autowired
    private UnitItemBillFormService unitItemBillFormService;

    @Autowired
    private UnitItemBankService unitItemBankService;

    @Autowired
    private IncomeItemService incomeItemService;

    @Autowired
    private UnitItemAccreditService unitItemAccreditService;




    @RequestMapping(value = "/validUnitItemChrCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> validUnitItemChrCode(String chrCode) {
        try {
            if(chrCode.equals("")){
                return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }
            UnitItem unitItem = new UnitItem();
            unitItem.setChrCode(chrCode);
            unitItem = unitItemService.get(unitItem);
            if(unitItem == null){
                return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else {
                if (unitItem.getChrId().equals("")) {
                    return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                } else {
                    return new Result<>(unitItem.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                }
            }
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
    //新增方法
    @RequestMapping(value = "/addUnitItemDo",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addUnitItemDo(@RequestBody UnitItem unitItem){
        try{
            unitItem.setSetYear(DateTimeUtils.getCurrentYear());
            unitItem.setCreateDate(DateTimeUtils.getDateTimeStr1());
            unitItem.setCreateUser(UserUtils.getUserId());
            unitItem.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItem.setLastestOpUser(UserUtils.getUserId());
            unitItem.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItem.setRgCode(regionService.get(null).getChrCode());
            unitItem.setDispCode(unitItem.getChrCode());
            unitItem.setStateCode("001");


            UnitItem ui = new UnitItem();
            ui.setItemId(unitItem.getItemId());
            ui.setIenId(unitItem.getIenId());
            List<UnitItem> unitItemList = unitItemService.findAllList(ui);
            if(unitItemList != null){
                if(unitItemList.size() > 0){
                    return new Result<Boolean>(false, "收费项目不能重复关联!", Globals.SUCCESS_CODE);
                }
            }

            boolean b = unitItemService.insert(unitItem);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    //编辑方法
    @RequestMapping(value = "/editUnitItemDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUnitItemDo(@RequestBody UnitItem unitItem) {
        try {
            unitItem.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            unitItem.setLatestOpUser(UserUtils.getUserId());
            unitItem.setLastVer(DateTimeUtils.getDateTimeStr1());
            unitItem.setRgCode(regionService.get(null).getChrCode());

            UnitItem ui = new UnitItem();
            ui.setItemId(unitItem.getItemId());
            ui.setIenId(unitItem.getIenId());
            List<UnitItem> unitItemList = unitItemService.findAllList(ui);
            if(unitItemList != null){
                for(UnitItem ui1 : unitItemList){
                    if(!ui1.getChrId().equals(unitItem.getChrId())) {
                        return new Result<Boolean>(false, "收费项目不能重复关联!", Globals.SUCCESS_CODE);
                    }
                }
            }

            boolean b = unitItemService.update(unitItem);
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


    @RequestMapping(value = "/checkUnitItem", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkUnitItem(@RequestParam(value = "chrIds") String chrIds, @RequestParam(value = "stateCode")  String stateCode) {
        try {
            boolean b = true;

            b = unitItemService.checkUnitItem(chrIds, stateCode);
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

    //删除方法
    @RequestMapping(value = "/delUnitItem", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUnitItem(@RequestParam(value = "chrId") String chrId) {
        try {
            UnitItem unitItem = new UnitItem();
            unitItem.setChrId(chrId);
            boolean b = unitItemService.delete(unitItem);
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

    //获取数据
    @RequestMapping(value = "/getUnitItemListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemListByCondition(String page, String rows, String sort, String order, UnitItem unitItem) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = unitItemService.getUnitItemListTotal(unitItem);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("unitItem", unitItem);
        List<UnitItem> list = unitItemService.getUnitItemListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getUnitItemList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItemList(UnitItem unitItem) {
        List<UnitItem> list = unitItemService.findAllList(unitItem);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getUnitItem", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUnitItem(UnitItem unitItem) {
        unitItem = unitItemService.get(unitItem);

        UnitItemUserInfo unitItemUserInfo = new UnitItemUserInfo();
        unitItemUserInfo.setMainId(unitItem.getChrId());
        List<UnitItemUserInfo> unitItemUserInfoList = unitItemUserInfoService.findAllList(unitItemUserInfo);

        UnitItemBillForm unitItemBillForm = new UnitItemBillForm();
        unitItemBillForm.setMainId(unitItem.getChrId());
        List<UnitItemBillForm> unitItemBillFormList = unitItemBillFormService.findAllList(unitItemBillForm);

        UnitItemBank unitItemBank = new UnitItemBank();
        unitItemBank.setMainId(unitItem.getChrId());
        List<UnitItemBank> unitItemBankList = unitItemBankService.findAllList(unitItemBank);


        UnitItemAccredit unitItemAccredit = new UnitItemAccredit();
        unitItemAccredit.setMainId(unitItem.getChrId());
        List<UnitItemAccredit> unitItemAccreditList = unitItemAccreditService.findAllList(unitItemAccredit);

        unitItem.setUnitItemBankList(unitItemBankList);
        unitItem.setUnitItemBillFormList(unitItemBillFormList);
        unitItem.setUnitItemUserInfoList(unitItemUserInfoList);
        unitItem.setUnitItemAccreditList(unitItemAccreditList);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("unitItem", unitItem);//

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/showUnitItem", method = RequestMethod.GET)
    @ResponseBody
    public Result<UnitItem> showUnitItem(String chrId) {
        try {


            UnitItem unitItem = new UnitItem();
            unitItem.setChrId(chrId);
            unitItem = unitItemService.get(unitItem);

            IncomeItem incomeItem = new IncomeItem();
            incomeItem.setChrId(unitItem.getItemId());
            incomeItem = incomeItemService.showIncomeItem(incomeItem);

            unitItem.setIncomeItem(incomeItem);
            return new Result<>(unitItem, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

}
