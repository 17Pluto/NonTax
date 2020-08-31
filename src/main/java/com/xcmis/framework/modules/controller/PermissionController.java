package com.xcmis.framework.modules.controller;

import com.xcmis.feefax.modules.entity.Button;
import com.xcmis.feefax.modules.entity.Menu;
import com.xcmis.feefax.modules.entity.MenuModule;
import com.xcmis.feefax.modules.entity.Status;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.modules.service.UserRoleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能
 *
 * @author
 * @see
 */

@Controller
@RequestMapping(value = "/sys")
public class PermissionController {
    @Autowired
    private UserRoleRuleService userRoleRuleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuModuleService menuModuleService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ButtonService buttonService;


    //moduleOrder为了匹配菜单下的功能
    @RequestMapping(value = "/getButtonListByCondition", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getButtonListByCondition(String screentype, String statusId, String moduleOrder) {
        List<Button> list = new ArrayList<>();

        Menu menu = new Menu();
        menu.setResourceName(screentype);
        List<Menu> menuList = menuService.findAllList(menu);

        if(menuList != null){
            if(menuList.size() > 0){
                menu = menuList.get(0);
                MenuModule menuModule = new MenuModule();
                menuModule.setDisplayOrder(Integer.parseInt(moduleOrder));
                menuModule.setMenuId(menu.getMenuId());
                List<MenuModule> menuModuleList = menuModuleService.getMenuModuleListByMenuIdAndDisplayOrder(menuModule);
                if(menuModuleList != null) {
                    if (menuModuleList.size() > 0) {
                        menuModule = menuModuleList.get(0);
                        Button button = new Button();
                        button.setModuleId(menuModule.getModuleId());
                        button.setStatusId(statusId);
                        list = buttonService.getButtonListByStatusId(button);
                    }
                }
            }
        }

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/getStatusListByModuleId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getStatusListByModuleId(String screentype, String moduleOrder) {
        List<Status> list = new ArrayList<>();

        Menu menu = new Menu();
        menu.setResourceName(screentype);
        List<Menu> menuList = menuService.findAllList(menu);

        if(menuList != null){
            if(menuList.size() > 0){
                menu = menuList.get(0);
                MenuModule menuModule = new MenuModule();
                menuModule.setDisplayOrder(Integer.parseInt(moduleOrder));
                menuModule.setMenuId(menu.getMenuId());
                List<MenuModule> menuModuleList = menuModuleService.getMenuModuleListByMenuIdAndDisplayOrder(menuModule);
                if(menuModuleList != null) {
                    if (menuModuleList.size() > 0) {
                        menuModule = menuModuleList.get(0);
                        Status status = new Status();
                        status.setModuleId(menuModule.getModuleId());
                        list = statusService.getStatusListByModuleId(status);
                    }
                }
            }
        }
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

}
