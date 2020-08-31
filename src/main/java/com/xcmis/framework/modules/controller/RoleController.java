package com.xcmis.framework.modules.controller;

import com.xcmis.feefax.modules.entity.Button;
import com.xcmis.feefax.modules.entity.Menu;
import com.xcmis.feefax.modules.entity.MenuModule;
import com.xcmis.feefax.modules.service.ButtonService;
import com.xcmis.feefax.modules.service.MenuModuleService;
import com.xcmis.feefax.modules.service.MenuService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.modules.entity.Role;
import com.xcmis.framework.modules.entity.RoleMenu;
import com.xcmis.framework.modules.entity.RoleMenuButton;
import com.xcmis.framework.modules.entity.RoleMenuModule;
import com.xcmis.framework.modules.service.RoleMenuButtonService;
import com.xcmis.framework.modules.service.RoleMenuModuleService;
import com.xcmis.framework.modules.service.RoleMenuService;
import com.xcmis.framework.modules.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuModuleService menuModuleService;

	@Autowired
	private ButtonService buttonService;

	@Autowired
	private RoleMenuService roleMenuService;

	@Autowired
	private RoleMenuModuleService roleMenuModuleService;

	@Autowired
	private RoleMenuButtonService roleMenuButtonService;



	@RequestMapping(value = "/getRoleMenuList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getRoleMenuList(Role role) {
		Menu menu = new Menu();
		List<Menu> list = null;
		if(!role.getUserSysId().equals("000")) {
			menu.setUserSysId(role.getUserSysId());
			list = menuService.findAllList(menu);
		}
		menu.setUserSysId("000");
		List<Menu> list000 = menuService.findAllList(menu);

		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(Long.parseLong(role.getRoleId()));
		List<RoleMenu> roleMenuList = roleMenuService.findAllList(roleMenu);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("list", list);//要以JSON格式返回
		mapOut.put("list000", list000);//要以JSON格式返回
		mapOut.put("roleMenuList", roleMenuList);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

	
	@RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<Role>> getRoleList(Role role) {
		List<Role> list = roleService.findAllList(role);
		return new Result<List<Role>>(list, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/addRoleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addRoleDo(Role role) {
		try {
			role.setSetYear(Integer.parseInt(DateTimeUtils.getCurrentYear()));
			role.setLastVer(DateTimeUtils.getDateTimeStr1());
			role.setRgCode(regionService.get(null).getChrCode());
			boolean b = roleService.insert(role);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}



	@RequestMapping(value = "/editRoleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editRoleDo(Role role) {
		try {
			role.setLastVer(DateTimeUtils.getDateTimeStr1());
			boolean b = roleService.update(role);
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

	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delRole(Role role) {
		try {
			role.setLastVer(DateTimeUtils.getDateTimeStr1());
			boolean b = roleService.delete(role);
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


	@RequestMapping(value = "/addRoleMenuDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addRoleMenuDo(RoleMenu roleMenu) {
		try {
			roleMenu.setSetYear(Integer.parseInt(DateTimeUtils.getCurrentYear()));
			roleMenu.setLastVer(DateTimeUtils.getDateTimeStr1());
			roleMenu.setRgCode(regionService.get(null).getChrCode());
			boolean b = roleMenuService.insert(roleMenu);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/delRoleMenu", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delRoleMenu(RoleMenu roleMenu) {
		try {
			boolean b = roleMenuService.delete(roleMenu);
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


	@RequestMapping(value = "/addRoleMenuModuleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addRoleMenuModuleDo(RoleMenuModule roleMenuModule) {
		try {
			roleMenuModule.setSetYear(Integer.parseInt(DateTimeUtils.getCurrentYear()));
			roleMenuModule.setLastVer(DateTimeUtils.getDateTimeStr1());
			roleMenuModule.setRgCode(regionService.get(null).getChrCode());
			boolean b = roleMenuModuleService.insert(roleMenuModule);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/delRoleMenuModule", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delRoleMenuModule(RoleMenuModule roleMenuModule) {
		try {
			boolean b = roleMenuModuleService.delete(roleMenuModule);
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


	@RequestMapping(value = "/addRoleMenuButtonDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addRoleMenuButtonDo(RoleMenuButton roleMenuButton) {
		try {
			roleMenuButton.setSetYear(Integer.parseInt(DateTimeUtils.getCurrentYear()));
			roleMenuButton.setLastVer(DateTimeUtils.getDateTimeStr1());
			roleMenuButton.setRgCode(regionService.get(null).getChrCode());
			boolean b = roleMenuButtonService.insert(roleMenuButton);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/delRoleMenuButton", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delRoleMenuButton(RoleMenuButton roleMenuButton) {
		try {
			boolean b = roleMenuButtonService.delete(roleMenuButton);
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


	@RequestMapping(value = "/getRoleMenuModuleList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getRoleMenuModuleList(RoleMenuModule roleMenuModule) {
		MenuModule menuModule = new MenuModule();
		menuModule.setMenuId(Long.parseLong(roleMenuModule.getMenuId()));
		List<MenuModule> list = menuModuleService.getMenuModuleListByMenuId(menuModule);

		List<RoleMenuModule> roleMenuModuleList = roleMenuModuleService.findAllList(roleMenuModule);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("list", list);//
		mapOut.put("roleMenuModuleList", roleMenuModuleList);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getRoleMenuButtonList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getRoleMenuButtonList(RoleMenuButton roleMenuButton) {
		Button button = new Button();
		button.setModuleId(Long.parseLong(roleMenuButton.getModuleId()));
		List<Button> list = buttonService.findAllList(button);

		List<RoleMenuButton> roleMenuButtonList = roleMenuButtonService.findAllList(roleMenuButton);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("list", list);//
		mapOut.put("roleMenuButtonList", roleMenuButtonList);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

}

