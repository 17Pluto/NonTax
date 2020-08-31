package com.xcmis.feefax.modules.controller;
/**
 * @Author: 方哲
 * @Date：2020-05-19
 * 不明款项2controller
 *
 */

import com.xcmis.feefax.modules.entity.UntaxNosourceCollection;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.UntaxNosourceCollectionService;
import com.xcmis.framework.common.global.Globals;
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
public class UntaxNosourceCollectionController {
    @Autowired
    private UntaxNosourceCollectionService untaxNosourceCollectionService;

//    @Autowired
//    private RegionService regionService;

    //新增
    @RequestMapping(value = "/addUntaxNosourceCollectionDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addUntaxNosourceCollectionDo(@RequestBody UntaxNosourceCollection untaxNosourceCollection) {
        try {

            boolean b = untaxNosourceCollectionService.insert(untaxNosourceCollection);
            if(b){
                return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    //编辑
    @RequestMapping(value = "/editUntaxNosourceCollectionDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUntaxNosourceCollectionDo(@RequestBody UntaxNosourceCollection untaxNosourceCollection) {
        try {

            boolean b = untaxNosourceCollectionService.update(untaxNosourceCollection);
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

    //删除
    @RequestMapping(value = "/delUntaxNosourceCollection", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUntaxNosourceCollection(@RequestParam(value = "chrId") String chrId) {
        try {
            UntaxNosourceCollection untaxNosourceCollection = new UntaxNosourceCollection();
            boolean b = untaxNosourceCollectionService.delete(untaxNosourceCollection);
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

    //查
    @RequestMapping(value = "/getUntaxNosourceCollectionListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxNosourceCollectionListByCondition(String page, String rows, String sort,
                                                                    String order, UntaxNosourceCollection untaxNosourceCollection) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = untaxNosourceCollectionService.getUntaxNosourceCollectionListTotal(untaxNosourceCollection);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("untaxNosourceCollection", untaxNosourceCollection);
        List<UntaxNosourceCollection> list =
                untaxNosourceCollectionService.getUntaxNosourceCollectionListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();

        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getUntaxNosourceCollectionList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxNosourceCollectionList(UntaxNosourceCollection untaxNosourceCollection) {
        List<UntaxNosourceCollection> list = untaxNosourceCollectionService.findAllList(untaxNosourceCollection);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
