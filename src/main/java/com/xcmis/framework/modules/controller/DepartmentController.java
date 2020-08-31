package com.xcmis.framework.modules.controller;

import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.feefax.modules.entity.Enterprise;
import com.xcmis.framework.modules.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/sys")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	
	@RequestMapping(value = "/getDepartmentList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<Enterprise>> getDepartmentList(Enterprise department) {
		List<Enterprise> list = departmentService.findList(department);
		return new Result<List<Enterprise>>(list, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}

