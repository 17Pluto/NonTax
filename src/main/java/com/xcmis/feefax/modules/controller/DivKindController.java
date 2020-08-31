package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.DivKind;
import com.xcmis.feefax.modules.service.DivKindService;
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
public class DivKindController {
	@Autowired
	private DivKindService divKindService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validDivKindChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validDivKindChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			DivKind divKind = new DivKind();
			divKind.setChrCode(chrCode);
			divKind = divKindService.get(divKind);
			if(divKind == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (divKind.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(divKind.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addDivKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addDivKindDo(DivKind divKind) {
		try {
			if(divKind.getParentId().equals("allTree")){
				divKind.setParentId("");
			}
			divKind.setSetYear(DateTimeUtils.getCurrentYear());
			divKind.setChrCode(divKind.getDispCode());
			divKind.setCreateDate(DateTimeUtils.getDateTimeStr1());
			divKind.setCreateUser(UserUtils.getUserId());
			divKind.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			divKind.setLatestOpUser(UserUtils.getUserId());
			divKind.setLastVer(DateTimeUtils.getDateTimeStr1());
			divKind.setRgCode(regionService.get(null).getChrCode());
			String chrId = divKindService.insertReturnId(divKind);
			if(!chrId.equals("")){
				return new Result<>(divKind.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editDivKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editDivKindDo(DivKind divKind) {
		try {
			divKind.setChrCode(divKind.getDispCode());

			boolean b = divKindService.update(divKind);
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

	@RequestMapping(value = "/delDivKind", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delDivKind(@RequestParam(value = "chrId") String chrId) {
		try {
			DivKind divKind = new DivKind();
			divKind.setChrId(chrId);

			long total = divKindService.getDivKindListTotal(divKind);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}


			boolean b = divKindService.delete(divKind);
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


	@RequestMapping(value = "/getDivKindListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getDivKindListByCondition(String page, String rows, String sort, String order, DivKind divKind) {
		if(!divKind.getChrCode().equals("allTree")){
			divKind.setDispCode(divKind.getChrCode());
		}else{
			divKind.setChrCode("");
			divKind.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = divKindService.getDivKindListTotal(divKind);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("divKind", divKind);
		List<DivKind> list = divKindService.getDivKindListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getDivKindList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getDivKindList(DivKind divKind) {
		List<DivKind> list = divKindService.findAllList(divKind);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getDivKindListByCondition|addDivKindDo|editDivKindDo|delDivKind|validDivKindChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
