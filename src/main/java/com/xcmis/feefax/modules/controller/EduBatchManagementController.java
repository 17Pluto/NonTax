package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.EduBatchManagement;
import com.xcmis.feefax.modules.service.EduBatchManagementService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/18 9:31 上午
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/feefax")
public class EduBatchManagementController {
    @Autowired
    private EduBatchManagementService eduBatchManagementService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserService userService;

    /**
     * 批次查询
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @param eduBatchManagement
     * @return
     */
    @GetMapping("/getEduBatchManagementByCondition")
    public Result<Map<String,Object>> getEduBatchManagementByCondition(String page, String rows, String sort,
                                                                       String order, EduBatchManagement eduBatchManagement){

        Map<String, Object> mapIn = new HashMap<>();

        long total = eduBatchManagementService.getEduBatchManagementListTotal(eduBatchManagement);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        eduBatchManagement.setRgCode(regionService.get(null).getChrCode());
        mapIn.put("eduBatchManagement",eduBatchManagement);
        List<EduBatchManagement> list = eduBatchManagementService.getEduBatchManagementListByCondition(mapIn);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);
        mapOut.put("list",list);
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 下拉列表的查询
     * @param eduBatchManagement
     * @return
     */
    @GetMapping("/getEduBatchManagement")
    public Result<Map<String,Object>> getEduBatchManagement(EduBatchManagement eduBatchManagement){

        Map<String, Object> mapIn = new HashMap<>();

        List<EduBatchManagement> list = eduBatchManagementService.getEduBatchManagement(mapIn);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("list",list);
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 新增批次
     * @param eduBatchManagement
     * @return
     */
    @PostMapping("/addBatchDo")
    public Result<Boolean> addBatchDo(@RequestBody EduBatchManagement eduBatchManagement){
        try {
            User user = new User();
            user.setUserId(UserUtils.getUserId());
            user = userService.get(user);
//            eduBatchManagement.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            eduBatchManagement.setRgCode(regionService.get(null).getChrCode());
            eduBatchManagement.setCreateDate(DateTimeUtils.getDateTimeStr1());
            eduBatchManagement.setCreateUser(user.getBelongOrg());
            boolean b = eduBatchManagementService.insert(eduBatchManagement);
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


    /**
     * 修改批次
     * @param eduBatchManagement
     * @return
     */
    @PostMapping("/editBatchDo")
    public Result<Boolean> editBatchDo(@RequestBody EduBatchManagement eduBatchManagement) {
        try {
            eduBatchManagement.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            eduBatchManagement.setRgCode(regionService.get(null).getChrCode());
            boolean b = eduBatchManagementService.update(eduBatchManagement);
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

    /**
     * 启用批次
     * @param chrId
     * @return
     */
    @PostMapping("/editEnableStuBatchStatus")
    public Result<Boolean> editEnableStatus(String chrId){
        boolean b = eduBatchManagementService.editEnableStatus(chrId);
        if(b){
            return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }else{
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    /**
     * 自动停用过期批次
     * @return
     */
    @PostMapping("/checkEnableStatus")
    public Result<Boolean> checkEnableStatus(){
        Map<String, Object> mapIn = new HashMap<>();
        EduBatchManagement eduBatchManagement = new EduBatchManagement();
        eduBatchManagement.setLatestOpDate(DateTimeUtils.getDateTimeStr2());
        mapIn.put("eduBatchManagement",eduBatchManagement);
        boolean b = eduBatchManagementService.checkEnableStatus(mapIn);
        if(b){
            return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }else{
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }

    }

    /**
     * 手动停用批次
     * @param chrIds
     * @return
     */
    @PostMapping("/disableStatus")
    public Result<Boolean> disableStatus(@RequestParam(value = "chrIds") String chrIds){
        try {
            boolean b = eduBatchManagementService.disableStatus(chrIds);
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



    /**
     * 删除批次
     * @param chrIds
     * @return
     */
    @PostMapping("/deleteBatchDo")
    @ResponseBody
    public Result<Boolean> deleteBatchDo(@RequestParam(value = "chrIds") String chrIds) {
        try {
            //校验当前批次是否被使用

            boolean b = eduBatchManagementService.deleteBatchDo(chrIds);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>("该批次已被使用", Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

}

