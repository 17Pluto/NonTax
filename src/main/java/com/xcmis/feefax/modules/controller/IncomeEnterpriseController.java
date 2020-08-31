package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Ienusermanag;
import com.xcmis.feefax.modules.entity.IncomeEnterprise;
import com.xcmis.feefax.modules.entity.RgUser;
import com.xcmis.feefax.modules.entity.UnitItem;
import com.xcmis.feefax.modules.service.IenusermanagService;
import com.xcmis.feefax.modules.service.IncomeEnterpriseService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.UnitItemService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.entity.UserOrg;
import com.xcmis.framework.modules.service.UserOrgService;
import com.xcmis.framework.modules.service.UserService;
import com.xcmis.framework.modules.vo.UsermanagerVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value = "/feefax")
public class IncomeEnterpriseController {
    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;

    @Autowired
    private UnitItemService unitItemService;

    @Autowired
    private IenusermanagService ienusermanagService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrgService userOrgService;

    @RequestMapping(value = "/getIncomeEnterpriseListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseListByCondition(String page, String rows, String sort, String order,
                                                                         IncomeEnterprise incomeEnterprise) {
        if(!incomeEnterprise.getChrCode().equals("allTree")){
            incomeEnterprise.setDispCode(incomeEnterprise.getChrCode());
        }else{
            incomeEnterprise.setChrCode("");
            incomeEnterprise.setChrId("");
        }
        Map<String, Object> mapIn = new HashMap<>();

        long total = incomeEnterpriseService.getIncomeEnterpriseListTotal(incomeEnterprise);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);
        mapIn.put("incomeEnterprise", incomeEnterprise);
        List<IncomeEnterprise> list = incomeEnterpriseService.getIncomeEnterpriseListByCondition(mapIn);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);//实际的行数
        mapOut.put("rows", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getIncomeEnterpriseList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseList(IncomeEnterprise incomeEnterprise) {
        List<IncomeEnterprise> list = incomeEnterpriseService.findAllList(incomeEnterprise);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getIncomeEnterpriseByUserId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseByUserId() {
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);

        IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
        incomeEnterprise.setChrId(user.getBelongOrg());

        incomeEnterprise = incomeEnterpriseService.get(incomeEnterprise);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("incomeEnterprise", incomeEnterprise);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getIncomeEnterpriseByChrId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseByChrId() {
        List<IncomeEnterprise> list = new ArrayList<>();

        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);


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
        mapOut.put("belongOrg", user.getBelongOrg());//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getIncomeEnterpriseByUnitId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseByUnitId(String unitId) {
        List<IncomeEnterprise> list = new ArrayList<>();


        UnitItem unitItem = new UnitItem();
        unitItem.setUnitId(unitId);
        List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);


        for (UnitItem ui : unitItemList) {
            IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
            incomeEnterprise.setChrId(ui.getIenId());
            List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
            for (IncomeEnterprise ie : incomeEnterpriseList) {
                boolean b = true;
                if(list.size() == 0){
                    list.add(ie);
                }else {
                    for(IncomeEnterprise i : list) {
                        if (i.getChrId().equals(ie.getChrId())) {
                            b = false;
                            break;
                        }
                    }
                    if(b){
                        list.add(ie);
                    }
                }
            }
        }

        Collections.sort(list);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping(value = "/getIncomeEnterpriseBySelfId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getIncomeEnterpriseBySelfId() {
        List<IncomeEnterprise> list = new ArrayList<>();

        String userId = UserUtils.getUserId();

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);

        if(user.getBelongType().equals("002")) {
            Ienusermanag ienusermanag = new Ienusermanag();
            ienusermanag.setUserId(user.getUserId());
            List<Ienusermanag> IenusermanagList = ienusermanagService.findAllList(ienusermanag);


            for (Ienusermanag i : IenusermanagList) {
                UserOrg userOrg = new UserOrg();
                userOrg.setUserId(user.getUserId());
                userOrg.setOrgId(i.getIenId());
                List<UserOrg> userOrgList = userOrgService.findAllList(userOrg);

                if (userOrgList != null) {
                    if (userOrgList.size() > 0) {
                        IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                        incomeEnterprise.setChrId(i.getIenId());

                        List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                        for (IncomeEnterprise ie : incomeEnterpriseList) {
                            list.add(ie);
                        }
                    }
                }
            }


            UnitItem unitItem = new UnitItem();
            unitItem.setUnitId(user.getBelongOrg());
            List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);


            for (UnitItem ui : unitItemList) {
                IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                incomeEnterprise.setChrId(ui.getIenId());
                List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                for (IncomeEnterprise ie : incomeEnterpriseList) {
                    boolean b = true;
                    if (list.size() == 0) {
                        list.add(ie);
                    } else {
                        for (IncomeEnterprise i : list) {
                            if (i.getChrId().equals(ie.getChrId())) {
                                b = false;
                                break;
                            }
                        }
                        if (b) {
                            list.add(ie);
                        }
                    }
                }
            }
        }else if(user.getBelongType().equals("007")) {
            IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
            list = incomeEnterpriseService.findAllList(incomeEnterprise);
        }

        Collections.sort(list);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("belongOrg", user.getBelongOrg());//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }
}
