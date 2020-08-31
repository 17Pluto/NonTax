package com.xcmis.framework.modules.controller;

import java.util.*;


import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.modules.entity.Element;
import com.xcmis.framework.modules.service.ElementService;

@Controller
@RequestMapping(value = "/sys")
public class ElementController {
	@Autowired
	private ElementService elementService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/getElement", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> getElement() {
		Element element = new Element();
		element.setChrId("1");
		element = elementService.getElement(element);
		return new Result<>(element.getCodeRule(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/addElementDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addElementDo(Element element) {
		try {
			element.setSetYear(DateTimeUtils.getCurrentYear());
			element.setCreateDate(DateTimeUtils.getDateTimeStr1());
			element.setCreateUser(UserUtils.getUserId());
			element.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			element.setLatestOpUser(UserUtils.getUserId());
			element.setLastVer(DateTimeUtils.getDateTimeStr1());
			element.setRgCode(regionService.get(null).getChrCode());
			boolean b = elementService.insert(element);
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


	@RequestMapping(value = "/updateIsRightfilter", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> updateIsRightfilter(@RequestBody String list) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(list);
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("list"));
			List<Element> elementList = JSONArray.toList(jsonArray, new Element(), new JsonConfig());
			boolean b = elementService.updateIsRightfilter(elementList);
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

	

	@RequestMapping(value = "/editElementDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editElementDo(Element element) {
		try {
			boolean b = elementService.update(element);
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

	@RequestMapping(value = "/delElement", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delElement(@RequestParam(value = "chrId") String chrId) {
		try {
			Element e = new Element();
			e.setChrId(chrId);
			boolean b = elementService.delete(e);
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
	
	
	
	@RequestMapping(value = "/getElementListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getElementListByCondition(String page, String rows, String sort, String order) {
		Element element = new Element();

		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = elementService.getElementListTotal(element);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		List<Element> list = elementService.getElementListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getElementList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<Element>> getElementList(String condition, String isRightfilter) {
		Element element = new Element();
		if(isRightfilter != null) {
			if (!isRightfilter.equals("")) {
				element.setIsRightfilter(Integer.parseInt(isRightfilter));
			}
		}
		element.setEleCode(condition);
		element.setEleName(condition);
		List<Element> list = elementService.findAllList(element);
		return new Result<List<Element>>(list, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}

