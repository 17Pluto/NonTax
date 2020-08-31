package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.BugetLevel;
import com.xcmis.feefax.modules.service.BugetLevelService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
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
public class BugetLevelController {
	@Autowired
	private BugetLevelService bugetLevelService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validBugetLevelChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validBugetLevelChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			BugetLevel bugetLevel = new BugetLevel();
			bugetLevel.setChrCode(chrCode);
			bugetLevel = bugetLevelService.get(bugetLevel);
			if(bugetLevel == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (bugetLevel.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(bugetLevel.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addBugetLevelDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addBugetLevelDo(BugetLevel bugetLevel) {
		try {
			if(bugetLevel.getParentId().equals("allTree")){
				bugetLevel.setParentId("");
			}
			bugetLevel.setSetYear(DateTimeUtils.getCurrentYear());
			bugetLevel.setChrCode(bugetLevel.getDispCode());
			bugetLevel.setCreateDate(DateTimeUtils.getDateTimeStr1());
			bugetLevel.setCreateUser(UserUtils.getUserId());
			bugetLevel.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			bugetLevel.setLatestOpUser(UserUtils.getUserId());
			bugetLevel.setLastVer(DateTimeUtils.getDateTimeStr1());
			bugetLevel.setRgCode(regionService.get(null).getChrCode());
			String chrId = bugetLevelService.insertReturnId(bugetLevel);
			if(!chrId.equals("")){
				return new Result<>(bugetLevel.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editBugetLevelDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editBugetLevelDo(BugetLevel bugetLevel) {
		try {
			bugetLevel.setChrCode(bugetLevel.getDispCode());

			boolean b = bugetLevelService.update(bugetLevel);
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

	@RequestMapping(value = "/delBugetLevel", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delBugetLevel(@RequestParam(value = "chrId") String chrId) {
		try {
			BugetLevel bugetLevel = new BugetLevel();
			bugetLevel.setChrId(chrId);
			boolean b = bugetLevelService.delete(bugetLevel);
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


	@RequestMapping(value = "/getBugetLevelListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBugetLevelListByCondition(String page, String rows, String sort, String order, BugetLevel bugetLevel) {
		if(!bugetLevel.getChrCode().equals("allTree")){
			bugetLevel.setDispCode(bugetLevel.getChrCode());
		}else{
			bugetLevel.setChrCode("");
			bugetLevel.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = bugetLevelService.getBugetLevelListTotal(bugetLevel);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("bugetLevel", bugetLevel);
		List<BugetLevel> list = bugetLevelService.getBugetLevelListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getBugetLevelList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBugetLevelList(BugetLevel bugetLevel) {
		List<BugetLevel> list = bugetLevelService.findAllList(bugetLevel);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getBugetLevelListByCondition|addBugetLevelDo|editBugetLevelDo|delBugetLevel|validBugetLevelChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
