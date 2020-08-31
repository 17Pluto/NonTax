package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.ProjectType;
import com.xcmis.feefax.modules.service.ProjectTypeService;
import com.xcmis.framework.common.global.Globals;
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
public class ProjectTypeController {
	@Autowired
	private ProjectTypeService projectTypeService;
	

	@RequestMapping(value = "/addProjectTypeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addProjectTypeDo(ProjectType projectType) {
		try {
			boolean b = projectTypeService.insert(projectType);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editProjectTypeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editProjectTypeDo(ProjectType projectType) {
		try {
			boolean b = projectTypeService.update(projectType);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/delProjectType", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delProjectType(@RequestParam(value = "id") String id) {
		try {
			ProjectType projectType = new ProjectType();
			projectType.setId(id);
			boolean b = projectTypeService.delete(projectType);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	
	
	@RequestMapping(value = "/getProjectTypeListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getProjectTypeListByCondition(String page, String rows, String sort, String order) {
		ProjectType projectType = new ProjectType();

		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = projectTypeService.getProjectTypeListTotal(projectType);
		mapIn.put("startrow", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
		mapIn.put("endrow", (Integer.parseInt(page) - 1) * Integer.parseInt(rows) + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		List<ProjectType> list = projectTypeService.getProjectTypeListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getProjectTypeList", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectType> getProjectTypeList() {
		List<ProjectType> list = projectTypeService.findAllList(null);
		return list;	
	}
}
