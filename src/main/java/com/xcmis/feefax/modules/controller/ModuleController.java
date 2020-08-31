package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;

    @Autowired
    private ModuleStatusService moduleStatusService;

    @Autowired
    private ModuleStatusButtonService moduleStatusButtonService;

	@Autowired
	private ButtonService buttonService;

	@Autowired
	private StatusService statusService;
	
	@RequestMapping(value = "/getModuleList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getModuleList(Module module) {
		List<Module> list = moduleService.findAllList(module);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getModuleByModuleId", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getModuleByModuleId(Module module) {
		module = moduleService.get(module);

		Button button = new Button();
		button.setModuleId(module.getModuleId());
		List<Button> buttonList = buttonService.findAllList(button);
		module.setButtonList(buttonList);

		Status status = new Status();
		status.setModuleId(module.getModuleId());
		List<Status> statusList = statusService.getStatusListByModuleId(status);
		module.setStatusList(statusList);


		ModuleStatusButton moduleStatusButton = new ModuleStatusButton();
		moduleStatusButton.setModuleId(module.getModuleId());
		List<ModuleStatusButton> moduleStatusButtonList = moduleStatusButtonService.findAllList(moduleStatusButton);
		module.setModuleStatusButtonList(moduleStatusButtonList);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("module", module);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/addModuleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addModuleDo(@RequestBody Module module) {
		Map<String, String> buttonMap = new HashMap<>();
		try {
			module.setLastVer(DateTimeUtils.getDateTimeStr1());
			module.setEnabled(1);
            long moduleId = moduleService.insertReturnId(module);
			if(moduleId != 0){
			    if(module.getButtonList() != null){
                    int order = 1;
                    for (Button button : module.getButtonList()) {
                        button.setModuleId(moduleId);
                        button.setDisplayTitle(button.getButtonDisplayTitle());
                        button.setLastVer(DateTimeUtils.getDateTimeStr1());
                        button.setDisplayOrder(order);
                        String buttonId = buttonService.insertReturnId(button);
						buttonMap.put(button.getActionId(), buttonId);
                        order++;
                    }
                }
                if(module.getStatusList() != null){
                    int order = 1;
                    for (Status status : module.getStatusList()) {
                        ModuleStatus ms = new ModuleStatus();
                        ms.setModuleId(moduleId);
                        ms.setLastVer(DateTimeUtils.getDateTimeStr1());
                        ms.setStatusId(status.getStatusId());
                        ms.setDisplayOrder(order);
                        ms.setDisplayTitle(status.getDisplayTitle());
                        moduleStatusService.insert(ms);
                        order++;
                    }
                }
                if(module.getModuleStatusButtonList() != null){
                    for (ModuleStatusButton moduleStatusButton : module.getModuleStatusButtonList()) {
                        moduleStatusButton.setModuleId(moduleId);
                        moduleStatusButton.setLastVer(DateTimeUtils.getDateTimeStr1());
                        moduleStatusButton.setButtonId(buttonMap.get(moduleStatusButton.getActionId()));
                        if(moduleStatusButton.getButtonId() != null) {
							moduleStatusButtonService.insert(moduleStatusButton);
						}
                    }
                }
				return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/editModuleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editModuleDo(@RequestBody Module module) {
		Map<String, String> buttonMap = new HashMap<>();
		try {
			long moduleId = module.getModuleId();
			module.setLastVer(DateTimeUtils.getDateTimeStr1());
			boolean b = moduleService.update(module);
			if(b){
				Button bt= new Button();
				bt.setModuleId(moduleId);
				buttonService.delete(bt);

				ModuleStatus ms = new ModuleStatus();
				ms.setModuleId(moduleId);
				moduleStatusService.delete(ms);

				ModuleStatusButton msb = new ModuleStatusButton();
				msb.setModuleId(moduleId);
				moduleStatusButtonService.delete(msb);

				if(module.getButtonList() != null){
					int order = 1;
					for (Button button : module.getButtonList()) {
						button.setModuleId(moduleId);
						button.setDisplayTitle(button.getButtonDisplayTitle());
						button.setLastVer(DateTimeUtils.getDateTimeStr1());
						button.setDisplayOrder(order);
						String buttonId = buttonService.insertReturnId(button);
						buttonMap.put(button.getActionId(), buttonId);
						order++;
					}
				}
				if(module.getStatusList() != null){
					int order = 1;
					for (Status status : module.getStatusList()) {
						ModuleStatus moduleStatus = new ModuleStatus();
						moduleStatus.setModuleId(moduleId);
						moduleStatus.setLastVer(DateTimeUtils.getDateTimeStr1());
						moduleStatus.setStatusId(status.getStatusId());
						moduleStatus.setDisplayOrder(order);
						moduleStatus.setDisplayTitle(status.getDisplayTitle());
						moduleStatusService.insert(moduleStatus);
						order++;
					}
				}
				if(module.getModuleStatusButtonList() != null){
					for (ModuleStatusButton moduleStatusButton : module.getModuleStatusButtonList()) {
						moduleStatusButton.setModuleId(moduleId);
						moduleStatusButton.setLastVer(DateTimeUtils.getDateTimeStr1());
						moduleStatusButton.setButtonId(buttonMap.get(moduleStatusButton.getActionId()));
						moduleStatusButtonService.insert(moduleStatusButton);
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

	@RequestMapping(value = "/delModule", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delModule(@RequestParam(value = "moduleId") String moduleId) {
		try {
            long moduleIdLong = Long.parseLong(moduleId);

            Button bt= new Button();
            bt.setModuleId(moduleIdLong);
            buttonService.delete(bt);

            ModuleStatus ms = new ModuleStatus();
            ms.setModuleId(moduleIdLong);
            moduleStatusService.delete(ms);

            ModuleStatusButton msb = new ModuleStatusButton();
            msb.setModuleId(moduleIdLong);
            moduleStatusButtonService.delete(msb);

			Module module = new Module();
			module.setModuleId(moduleIdLong);
			boolean b = moduleService.delete(module);
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
