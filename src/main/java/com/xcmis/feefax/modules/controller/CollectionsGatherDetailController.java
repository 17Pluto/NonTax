package com.xcmis.feefax.modules.controller;
/**
 * @author 方哲
 * @date 2020-04-26
 *  controller
 */

import com.xcmis.feefax.modules.entity.CollectionsDetail;
import com.xcmis.feefax.modules.entity.CollectionsGather;
import com.xcmis.feefax.modules.entity.CollectionsGatherDetail;
import com.xcmis.feefax.modules.service.CollectionsDetailService;
import com.xcmis.feefax.modules.service.CollectionsGatherDetailService;
import com.xcmis.feefax.modules.service.CollectionsGatherService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
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
public class CollectionsGatherDetailController {
    @Autowired
    private CollectionsGatherDetailService collectionsGatherDetailService;

    @Autowired
    private CollectionsGatherService collectionsGatherService;

    @Autowired
    private CollectionsDetailService collectionsDetailService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addCollectionsGatherDetailDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addCollectionsGatherDetailDo(CollectionsGatherDetail collectionsGatherDetail) {
        try {
            collectionsGatherDetail.setSetYear(DateTimeUtils.getCurrentYear());
            collectionsGatherDetail.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collectionsGatherDetail.setCreateUser(UserUtils.getUserId());
            collectionsGatherDetail.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionsGatherDetail.setLatestOpUser(UserUtils.getUserId());
            collectionsGatherDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionsGatherDetail.setRgCode(regionService.get(null).getChrCode());
            String chrId = collectionsGatherDetailService.insertReturnId(collectionsGatherDetail);
            if(!chrId.equals("")){
               return new Result<>(collectionsGatherDetail.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editCollectionsGatherDetailDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editCollectionsGatherDetailDo(CollectionsGatherDetail collectionsGatherDetail) {
        try {
            collectionsGatherDetail.setChrCode(collectionsGatherDetail.getDispCode());
            collectionsGatherDetail.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionsGatherDetail.setLatestOpUser(UserUtils.getUserId());
            collectionsGatherDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionsGatherDetail.setRgCode(regionService.get(null).getChrCode());
            boolean b = collectionsGatherDetailService.update(collectionsGatherDetail);
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

    @RequestMapping(value = "/delCollectionsGatherDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delCollectionsGatherDetail(@RequestParam(value = "chrId") String chrId) {
        try {
            CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
            collectionsGatherDetail.setChrId(chrId);
            boolean b = collectionsGatherDetailService.delete(collectionsGatherDetail);
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

    @RequestMapping(value = "/getCollectionsGatherDetailListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGatherDetailListByCondition(String page, String rows, String sort,
                                                                    String order, CollectionsGatherDetail collectionsGatherDetail) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = collectionsGatherDetailService.getCollectionsGatherDetailListTotal(collectionsGatherDetail);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collectionGatherDetail", collectionsGatherDetail);
        List<CollectionsGatherDetail> list = collectionsGatherDetailService.getCollectionsGatherDetailListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getCollectionsGatherDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsGatherDetailList(CollectionsGatherDetail collectionsGatherDetail) {
        Map<String, Object> mapOut = new HashMap<>();
        //if(collectionsGatherDetail.getStateCode().equals("000") || collectionsGatherDetail.getStateCode().equals("901")) {
            List<CollectionsGatherDetail> list = collectionsGatherDetailService.findAllList(collectionsGatherDetail);
            mapOut.put("url", "");//实际的行数
            mapOut.put("list", list);//要以JSON格式返回
        /*
        }else{
            CollectionsGather collectionsGather = new CollectionsGather();
            collectionsGather.setCollectId(collectionsGatherDetail.getMainId());
            List<CollectionsGather> collectionsGatherList = collectionsGatherService.findAllList(collectionsGather);

            if(collectionsGatherList != null) {
                collectionsGatherDetail.setMainId(collectionsGatherList.get(0).getChrId());
            }
            List<CollectionsGatherDetail> list = collectionsGatherDetailService.findAllList(collectionsGatherDetail);
            mapOut.put("url", "");//实际的行数
            mapOut.put("list", list);//要以JSON格式返回
        }
        */
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
