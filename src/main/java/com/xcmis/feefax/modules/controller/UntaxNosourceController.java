package com.xcmis.feefax.modules.controller;
/**
 * @Author: 方哲
 * @Date：2020-05-18
 * 不明款项controller
 */

import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.entity.UserOrg;
import com.xcmis.framework.modules.service.UserOrgService;
import com.xcmis.framework.modules.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping(value = "/feefax")
public class UntaxNosourceController {
    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;

    @Autowired
    private UserOrgService userOrgService;

    @Autowired
    private IenusermanagService ienusermanagService;

    @Autowired
    private UserService userService;

    @Autowired
    private UntaxNosourceService untaxNosourceService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UnitItemService unitItemService;

    @Autowired
    private GlZwService glZwService;

    @Value("${issubaccount}")
    private boolean isSubAccount;

    //不明款项插入方法
    @RequestMapping(value = "/addUntaxNosourceDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addUntaxNosourceDo(@RequestBody UntaxNosource untaxNosource) {
        try {
            //插入时间
            untaxNosource.setSetYear(DateTimeUtils.getCurrentYear());
            //
            untaxNosource.setCreateDate(DateTimeUtils.getDateTimeStr1());
            //
            untaxNosource.setCreateUser(UserUtils.getUserId());
            //
            untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
            //
            untaxNosource.setRgCode(regionService.get(null).getChrCode());
            untaxNosource.setIsDeleted(0);
            if(isSubAccount){
                untaxNosource.setIsAudit(1);
                untaxNosource.setIsClaim(1);
                String account = untaxNosource.getReceiveraccount();
                if(account.length() == 20){
                    untaxNosource.setReceiveraccount(account.substring(0, 14));
                }else if(account.length() > 20){
                    untaxNosource.setReceiveraccount(account.substring(0, 17));
                }
                untaxNosource.setBankNo(account);
            }else{
                untaxNosource.setIsAudit(0);
                untaxNosource.setIsClaim(0);
            }

            boolean b = untaxNosourceService.insert(untaxNosource);
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

    //不明款项修改页面
    @RequestMapping(value = "/editUntaxNosourceDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUntaxNosourceDo(@RequestBody UntaxNosource untaxNosource) {
        try {

            untaxNosource.setLastVer(DateTimeUtils.getDateTimeStr1());
            untaxNosource.setRgCode(regionService.get(null).getChrCode());
            untaxNosource.setBankNo(untaxNosource.getReceiveraccount());
            boolean b = untaxNosourceService.update(untaxNosource);
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
     * 不明款项认领
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/editIsClaimDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editIsClaimDo(@RequestParam(value = "chrIds") String chrIds,UntaxNosource untaxNosource) {
        try {
            boolean b = untaxNosourceService.editIsClaimDo(chrIds,untaxNosource);
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
     * 不明款项认领 执收单位
     * @return
     */
    @RequestMapping(value = "/getIncomeEnterpriseByChrIdInClaim", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseByChrIdInClaim() {
        List<IncomeEnterprise> list = new ArrayList<>();

        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);

        user.getBelongOrg();

        Ienusermanag ienusermanag = new Ienusermanag();
        ienusermanag.setUserId(user.getUserId());
        List<Ienusermanag> IenusermanagList = ienusermanagService.findAllList(ienusermanag);


        for(Ienusermanag i : IenusermanagList){
            UserOrg userOrg = new UserOrg();
            userOrg.setUserId(user.getUserId());
            userOrg.setOrgId(i.getIenId());
            List<UserOrg> userOrgList = userOrgService.findAllList(userOrg);

            if(userOrgList != null) {
                if(userOrgList.size() > 0) {
                    IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                    incomeEnterprise.setChrId(i.getIenId());

                    List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);

                    for (IncomeEnterprise ie : incomeEnterpriseList) {
                        list.add(ie);
                    }
                }
            }
        }


        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("belongOrg", user.getBelongOrg());
//        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    /**
     * 不明款项认领撤销
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/cancelIsClaim", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> cancelIsClaim(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = untaxNosourceService.cancelIsClaim(chrIds);
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
     * 撤回送审
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/cancelStateCode", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> cancelStateCode(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = untaxNosourceService.cancelStateCode(chrIds);
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
     * 不明款项送审 状态改变
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/changeStateCode", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> changeStateCode(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = untaxNosourceService.changeStateCode(chrIds);
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
     * 不明款项审核
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/editIsAuditDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editIsAuditDo(@RequestParam(value = "chrIds") String chrIds) {
        try {
            boolean b = untaxNosourceService.editIsAuditDo(chrIds);
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
     * 撤回审核
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/cancelAudit", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> cancelAudit(@RequestParam(value = "chrIds") String chrIds) {
        try {

            boolean b = untaxNosourceService.cancelAudit(chrIds);
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
    //不明款项删除方法
    @RequestMapping(value = "/delUntaxNosource", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUntaxNosource(@RequestParam(value = "chrId") String chrId) {
        try {
            UntaxNosource untaxNosource = new UntaxNosource();
            untaxNosource.setChrId(chrId);
            untaxNosource = untaxNosourceService.get(untaxNosource);

            GlZw glZw = new GlZw();
            glZw.setBillId(untaxNosource.getChrId() + untaxNosource.getBatchno());
            List<GlZw> list = glZwService.findAllList(glZw);

            if(list != null){
                if(list.size() > 0){
                    return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
                }
            }

            if(untaxNosource.getIsCollect() == 1){
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }

            boolean b = untaxNosourceService.delete(untaxNosource);
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

    //不明款项查询方法
    @RequestMapping(value = "/getUntaxNosourceListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxNosourceListByCondition(String page, String rows, String sort,
                                                                    String order, UntaxNosource untaxNosource) {
        Map<String, Object> mapIn = new HashMap<>();

        //页面传入查询格式 yyyy-mm-dd ————> 拼接成 yyyy-mm-dd hh:mm:ss （数据库保存的格式）
        if (untaxNosource.getStartEventtime() != null && untaxNosource.getStartEventtime() != ""){
            String startEventtime = untaxNosource.getStartEventtime().concat(" 00:00:00");
            untaxNosource.setStartEventtime(startEventtime);
        }
        if (untaxNosource.getEndEventtime() != null && untaxNosource.getEndEventtime() != ""){
            String endEventtime = untaxNosource.getEndEventtime().concat(" 23:59:59");
            untaxNosource.setEndEventtime(endEventtime);
        }


        if (untaxNosource.getSearchmoney() != "" && untaxNosource.getSearchmoney() != null){
            String searchmoney = untaxNosource.getSearchmoney();
            Double checkmoney = Double.parseDouble(searchmoney);
            untaxNosource.setCheckmoney(checkmoney);
        }

        String stateCode = untaxNosource.getStateCode();

        /**
         * 获取执收单位
         */
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);
        //执收单位id：user.getBelongOrg();
        //untaxNosource.setEnName1(user.getBelongOrg());

        String enCode = "";
        if(isSubAccount){
            List<String> enIdArray = new ArrayList<>();
            if(untaxNosource.getEnId() != null){
                if (!untaxNosource.getEnId().equals("")) {
                    String[] enIds = untaxNosource.getEnId().split("#");
                    for (String enId : enIds) {
                        enIdArray.add(enId);
                        untaxNosource.setEnIds(enIdArray);
                    }
                } else {
                    if (user.getBelongType().equals("002")) {
                        String unitId = user.getBelongOrg();

                        IncomeEnterprise ie = new IncomeEnterprise();
                        ie.setChrId(unitId);
                        ie = incomeEnterpriseService.get(ie);
                        enCode = ie.getChrCode();

                        UnitItem unitItem = new UnitItem();
                        unitItem.setUnitId(unitId);
                        List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);

                        List<String> enIds = new ArrayList<>();
                        enIds.add(unitId);
                        for (UnitItem ui : unitItemList) {
                            enIds.add(ui.getIenId());
                        }

                        untaxNosource.setEnIds(enIds);
                    } else {
                        if (untaxNosource.getEnId() != null) {
                            if (!untaxNosource.getEnId().equals("")) {
                                List<String> enIds = new ArrayList<>();
                                enIds.add(untaxNosource.getEnId());
                                untaxNosource.setEnIds(enIds);
                            }
                        }


                    }
                }

            }else {

                if (user.getBelongType().equals("002")) {
                    String unitId = user.getBelongOrg();

                    IncomeEnterprise ie = new IncomeEnterprise();
                    ie.setChrId(unitId);
                    ie = incomeEnterpriseService.get(ie);
                    enCode = ie.getChrCode();

                    UnitItem unitItem = new UnitItem();
                    unitItem.setUnitId(unitId);
                    List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);

                    List<String> enIds = new ArrayList<>();
                    enIds.add(unitId);
                    for (UnitItem ui : unitItemList) {
                        enIds.add(ui.getIenId());
                    }

                    untaxNosource.setEnIds(enIds);
                } else {
                    if (untaxNosource.getEnId() != null) {
                        if (!untaxNosource.getEnId().equals("")) {
                            List<String> enIds = new ArrayList<>();
                            enIds.add(untaxNosource.getEnId());
                            untaxNosource.setEnIds(enIds);
                        }
                    }


                }
            }
        }

        long total = untaxNosourceService.getUntaxNosourceListTotal(untaxNosource);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("untaxNosource", untaxNosource);
        List<UntaxNosource> list = untaxNosourceService.getUntaxNosourceListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();

        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回
        mapOut.put("isSubAccount", isSubAccount);
        mapOut.put("belongOrg", user.getBelongOrg());
        mapOut.put("enCode", enCode);

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getUntaxNosourceList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxNosourceList(UntaxNosource untaxNosource) {
        List<UntaxNosource> list = untaxNosourceService.findAllList(untaxNosource);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    /**
     * 绑定缴款书查询方法
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @param collections
     * @return
     */
    @RequestMapping(value = "/getCollectionsNosource", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getCollectionsNosource(String page, String rows, String sort,
                                                                      String order, Collections collections) {
        Map<String, Object> mapIn = new HashMap<>();


        /**
         * 获取执收单位
         */
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);
        //执收单位id：user.getBelongOrg();
        if(collections.getEnId().equals(user.getBelongOrg())){
            collections.setIsconsign(0);
        }else{
            collections.setConsigneeEnId(collections.getEnId());
            collections.setEnId(user.getBelongOrg());
            collections.setIsconsign(1);
        }

        //untaxNosource.setEnName1(user.getBelongOrg());

        //分页
        long total = untaxNosourceService.getCollectionsListTotal(collections);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("collections", collections);

        //获取票据列表
        List<Collections> list = untaxNosourceService.getCollections(mapIn);

        Map<String,Object> mapOut = new HashMap<>();

        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回
        //mapOut.put("belongOrg", user.getBelongOrg());

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 绑定缴款书保存方法
     * 不明款项多选 票据单选
     * @param
     * @return
     */
    @RequestMapping(value = "/saveIsCollectForNosourceIds", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> saveIsCollectForNosourceIds(@RequestParam(value = "nosourceIds") String nosourceIds ,
                                         String collectionIds) {
        try {


            boolean b = untaxNosourceService.saveIsCollectForNosourceIds(nosourceIds,collectionIds);
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
     * 绑定缴款书保存方法
     * 票据多选 不明款项单选
     * @param
     * @return
     */
    @RequestMapping(value = "/saveIsCollectForCollectionIds", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> saveIsCollectForCollectionIds( String nosourceIds ,
                                                       @RequestParam(value = "collectionIds") String collectionIds) {
        try {

            boolean b = untaxNosourceService.saveIsCollectForCollectionIds(nosourceIds,collectionIds);
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
     * 不明款项打印方法
     * @param
     * @return
     */
    @RequestMapping(value = "/getUntaxNosourceListForPrint", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUntaxNosourceListForPrint(@RequestParam(value = "chrIds") String chrIds
                                                                   ) {
        Map<String, Object> mapIn = new HashMap<>();
        Map<String,Object> mapOut = new HashMap<>();

        String[] chrIdArr = chrIds.split(",");
       // System.out.println(UserUtils.getUserId());
        String userId = UserUtils.getUserId();
        String userName = untaxNosourceService.getUserNamebyId(userId);
        //System.out.println(userName);
        List<UntaxNosource> list = new ArrayList<>();
        for(String chrId : chrIdArr){
            List<UntaxNosource> list1 = new ArrayList<>();
            list1 = untaxNosourceService.getUntaxNosourceForPrint(chrId);
            for (UntaxNosource un : list1){
                list.add(un);
            }
        }
        mapOut.put("list", list);//要以JSON格式返回

        mapOut.put("userName",userName);
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    /**
     * 生成缴款书保存方法
     *
     */
    @RequestMapping(value = "/updateBindingTime", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateBindingTime(@RequestParam(value = "chrIds") String chrIds) {
        try {
            String createDate = DateTimeUtils.getDateTimeStr1();
            String createUser = UserUtils.getUserId();

            boolean b = untaxNosourceService.updateBindingTime(chrIds, createDate, createUser);
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



    @RequestMapping(value = "/insertAndAddCollectionsDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> insertAndAddCollectionsDo(@RequestBody Collections collections) {
        try {
            boolean b = untaxNosourceService.insertAndAddCollectionsDo(collections);
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

    /**
     * 不明款项绑定撤销
     * @param chrIds
     * @return
     */
    @RequestMapping(value = "/cancelIsCollection", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> cancelIsCollection(@RequestParam(value = "chrIds") String chrIds , @RequestParam(value =
            "collectionIds") String collectionIds) {
        try {
            boolean b = untaxNosourceService.cancelIsCollection(chrIds,collectionIds);
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
