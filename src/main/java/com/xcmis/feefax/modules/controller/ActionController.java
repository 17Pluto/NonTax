package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Action;
import com.xcmis.feefax.modules.service.ActionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class ActionController {
	@Autowired
	private ActionService actionService;
	
	@RequestMapping(value = "/getActionList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getActionList(Action action) {
		List<Action> list = actionService.findAllList(action);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

	@RequestMapping(value = "/addActionDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<String,Object>> addActionDo(Action action) {
		Map<String,Object> mapOut = new HashMap<>();

		try {
			if(action.getParentActionId().equals("allTree")){
				action.setParentActionId("");
			}
			action.setLastVer(DateTimeUtils.getDateTimeStr1());
			String actionId = actionService.insertReturnId(action);
			if(!actionId.equals("")){
				List<Action> list = actionService.findAllList(action);
				mapOut.put("list", list);
				return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
}
