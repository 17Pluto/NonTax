package com.xcmis.feefax.modules.controller;
/**
 * @author fangzhe
 * @date 2020-04-01
 *  controller
 */

import com.xcmis.feefax.modules.entity.CollectionsDetail;
import com.xcmis.feefax.modules.service.CollectionsDetailService;
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
public class CollectionsDetailController {
    @Autowired
    private CollectionsDetailService collectionsDetailService;

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/addCollectionsDetailDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addCollectionsDetailDo(CollectionsDetail collectionsDetail) {
        try {
            collectionsDetail.setSetYear(DateTimeUtils.getCurrentYear());
            collectionsDetail.setCreateDate(DateTimeUtils.getDateTimeStr1());
            collectionsDetail.setCreateUser(UserUtils.getUserId());
            collectionsDetail.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionsDetail.setLatestOpUser(UserUtils.getUserId());
            collectionsDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionsDetail.setRgCode(regionService.get(null).getChrCode());
            String chrId = collectionsDetailService.insertReturnId(collectionsDetail);
            if(!chrId.equals("")){
               return new Result<>(collectionsDetail.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
           }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/editCollectionsDetailDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editCollectionsDetailDo(CollectionsDetail collectionDetail) {
        try {
            collectionDetail.setChrCode(collectionDetail.getDispCode());
            collectionDetail.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            collectionDetail.setLatestOpUser(UserUtils.getUserId());
            collectionDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
            collectionDetail.setRgCode(regionService.get(null).getChrCode());
            boolean b = collectionsDetailService.update(collectionDetail);
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

    @RequestMapping(value = "/delCollectionsDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delCollectionsDetail(@RequestParam(value = "chrId") String chrId) {
        try {
            CollectionsDetail collectionsDetail = new CollectionsDetail();
            collectionsDetail.setChrId(chrId);
            boolean b = collectionsDetailService.delete(collectionsDetail);
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

    @RequestMapping(value = "/getCollectionsDetailListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsDetailListByCondition(String page, String rows, String sort,
                                                                    String order, CollectionsDetail collectionsDetail) {
        Map<String, Object> mapIn = new HashMap<>();

        long total = collectionsDetailService.getCollectionsDetailListTotal(collectionsDetail);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collectionsDetail", collectionsDetail);
        List<CollectionsDetail> list = collectionsDetailService.getCollectionsDetailListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getCollectionsDetailList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsDetailList(CollectionsDetail collectionsDetail) {
        List<CollectionsDetail> list = collectionsDetailService.findAllList(collectionsDetail);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
