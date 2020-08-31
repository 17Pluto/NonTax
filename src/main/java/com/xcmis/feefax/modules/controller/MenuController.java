package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Menu;
import com.xcmis.feefax.modules.entity.MenuModule;
import com.xcmis.feefax.modules.entity.Module;
import com.xcmis.feefax.modules.service.MenuModuleService;
import com.xcmis.feefax.modules.service.MenuService;
import com.xcmis.feefax.modules.service.ModuleService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class MenuController {
	@Autowired
	private MenuModuleService menuModuleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getMenuList(Menu menu) {
		List<Menu> list = menuService.findAllList(menu);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getMenuByMenuId", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getMenuByMenuId(Menu menu) {
		menu = menuService.get(menu);

		MenuModule menuModule = new MenuModule();
        menuModule.setMenuId(menu.getMenuId());
		List<MenuModule> menuModuleList = menuModuleService.getMenuModuleListByMenuId(menuModule);
		menu.setMenuModuleList(menuModuleList);


		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("menu", menu);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/addMenuDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addMenuDo(@RequestBody Menu menu) {
		try {
			menu.setLastVer(DateTimeUtils.getDateTimeStr1());
			long menuId = menuService.insertReturnId(menu);
			if(menuId != 0){
                if(menu.getMenuModuleList() != null){
                    for (MenuModule menuModule : menu.getMenuModuleList()) {
                        menuModule.setMenuId(menuId);
                        menuModule.setModuleId(menuModule.getModuleId());
                        menuModule.setDisplayTitle(menuModule.getDisplayTitle());
                        menuModule.setDisplayOrder(menuModule.getDisplayOrder());
                        menuModule.setLastVer(DateTimeUtils.getDateTimeStr1());
                        menuModuleService.insert(menuModule);
                    }
                }
				return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/editMenuDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editMenuDo(@RequestBody Menu menu) {
		try {
			long menuId = menu.getMenuId();
			menu.setLastVer(DateTimeUtils.getDateTimeStr1());
			boolean b = menuService.update(menu);

			if(b){
				MenuModule menuModule = new MenuModule();
				menuModule.setMenuId(menuId);
				menuModuleService.delete(menuModule);
				if(menu.getMenuModuleList() != null){
					for (MenuModule mm : menu.getMenuModuleList()) {
						mm.setMenuId(menuId);
						mm.setLastVer(DateTimeUtils.getDateTimeStr1());
						menuModuleService.insert(mm);
					}
				}

				return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/delMenu", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delMenu(@RequestParam(value = "menuId") String menuId) {
		try {
			MenuModule menuModule = new MenuModule();
			menuModule.setMenuId(Long.parseLong(menuId));
			menuModuleService.delete(menuModule);

			Menu menu = new Menu();
			menu.setMenuId(Long.parseLong(menuId));
			boolean b = menuService.delete(menu);
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
