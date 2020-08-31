package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.ManageBranch;
import com.xcmis.feefax.modules.service.ManageBranchService;
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
public class ManageBranchController {
	@Autowired
	private ManageBranchService manageBranchService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validManageBranchChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validManageBranchChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			ManageBranch manageBranch = new ManageBranch();
			manageBranch.setChrCode(chrCode);
			manageBranch = manageBranchService.get(manageBranch);
			if(manageBranch == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (manageBranch.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(manageBranch.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addManageBranchDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addManageBranchDo(ManageBranch manageBranch) {
		try {
			if(manageBranch.getParentId().equals("allTree")){
				manageBranch.setParentId("");
			}
			manageBranch.setSetYear(DateTimeUtils.getCurrentYear());
			manageBranch.setChrCode(manageBranch.getDispCode());
			manageBranch.setCreateDate(DateTimeUtils.getDateTimeStr1());
			manageBranch.setCreateUser(UserUtils.getUserId());
			manageBranch.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			manageBranch.setLatestOpUser(UserUtils.getUserId());
			manageBranch.setLastVer(DateTimeUtils.getDateTimeStr1());
			manageBranch.setRgCode(regionService.get(null).getChrCode());
			String chrId = manageBranchService.insertReturnId(manageBranch);
			if(!chrId.equals("")){
				return new Result<>(manageBranch.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editManageBranchDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editManageBranchDo(ManageBranch manageBranch) {
		try {
			manageBranch.setChrCode(manageBranch.getDispCode());

			boolean b = manageBranchService.update(manageBranch);
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

	@RequestMapping(value = "/delManageBranch", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delManageBranch(@RequestParam(value = "chrId") String chrId) {
		try {
			ManageBranch manageBranch = new ManageBranch();
			manageBranch.setChrId(chrId);

			long total = manageBranchService.getManageBranchListTotal(manageBranch);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}
			boolean b = manageBranchService.delete(manageBranch);
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


	@RequestMapping(value = "/getManageBranchListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getManageBranchListByCondition(String page, String rows, String sort, String order, ManageBranch manageBranch) {
		if(!manageBranch.getChrCode().equals("allTree")){
			manageBranch.setDispCode(manageBranch.getChrCode());
		}else{
			manageBranch.setChrCode("");
			manageBranch.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = manageBranchService.getManageBranchListTotal(manageBranch);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("manageBranch", manageBranch);
		List<ManageBranch> list = manageBranchService.getManageBranchListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getManageBranchList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getManageBranchList(ManageBranch manageBranch) {
		List<ManageBranch> list = manageBranchService.findAllList(manageBranch);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getManageBranchListByCondition|addManageBranchDo|editManageBranchDo|delManageBranch|validManageBranchChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
