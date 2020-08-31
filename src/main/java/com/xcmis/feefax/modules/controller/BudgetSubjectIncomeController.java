package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.BudgetSubjectIncome;
import com.xcmis.feefax.modules.service.BudgetSubjectIncomeService;
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
public class BudgetSubjectIncomeController {
	@Autowired
	private BudgetSubjectIncomeService budgetSubjectIncomeService;

	@Autowired
	private RegionService regionService;

	@RequestMapping(value = "/validBudgetSubjectIncomeChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validBudgetSubjectIncomeChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			BudgetSubjectIncome budgetSubjectIncome = new BudgetSubjectIncome();
			budgetSubjectIncome.setChrCode(chrCode);
			budgetSubjectIncome = budgetSubjectIncomeService.getBudgetSubjectIncome(budgetSubjectIncome);
			if(budgetSubjectIncome == null){
				return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (budgetSubjectIncome.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					//前段判断
					return new Result<>(budgetSubjectIncome.getChrId(), Globals.OP_FAILURE, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("", Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/addBudgetSubjectIncomeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addBudgetSubjectIncomeDo(BudgetSubjectIncome budgetSubjectIncome) {
		try {
			if(budgetSubjectIncome.getParentId().equals("allTree")){
				budgetSubjectIncome.setParentId("");
			}
			budgetSubjectIncome.setSetYear(DateTimeUtils.getCurrentYear());
			budgetSubjectIncome.setChrCode(budgetSubjectIncome.getDispCode());
			budgetSubjectIncome.setCreateDate(DateTimeUtils.getDateTimeStr1());
			budgetSubjectIncome.setCreateUser(UserUtils.getUserId());
			budgetSubjectIncome.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			budgetSubjectIncome.setLatestOpUser(UserUtils.getUserId());
			budgetSubjectIncome.setLastVer(DateTimeUtils.getDateTimeStr1());
			budgetSubjectIncome.setRgCode(regionService.get(null).getChrCode());
			String chrId = budgetSubjectIncomeService.insertReturnId(budgetSubjectIncome);
			if(!chrId.equals("")){
				return new Result<>(budgetSubjectIncome.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editBudgetSubjectIncomeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editBudgetSubjectIncomeDo(BudgetSubjectIncome budgetSubjectIncome) {
		try {
			budgetSubjectIncome.setChrCode(budgetSubjectIncome.getDispCode());

			boolean b = budgetSubjectIncomeService.update(budgetSubjectIncome);
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

	@RequestMapping(value = "/delBudgetSubjectIncome", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delBudgetSubjectIncome(@RequestParam(value = "chrId") String chrId) {
		try {
			BudgetSubjectIncome budgetSubjectIncome = new BudgetSubjectIncome();
			budgetSubjectIncome.setChrId(chrId);

			long total = budgetSubjectIncomeService.getBudgetSubjectIncomeListTotal(budgetSubjectIncome);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}
			boolean b = budgetSubjectIncomeService.delete(budgetSubjectIncome);
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
	
	
	
	@RequestMapping(value = "/getBudgetSubjectIncomeListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBudgetSubjectIncomeListByCondition(String page, String rows, String sort, String order, BudgetSubjectIncome budgetSubjectIncome) {
		if(!budgetSubjectIncome.getChrCode().equals("allTree")){
			budgetSubjectIncome.setDispCode(budgetSubjectIncome.getChrCode());
		}else{
			budgetSubjectIncome.setChrCode("");
			budgetSubjectIncome.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = budgetSubjectIncomeService.getBudgetSubjectIncomeListTotal(budgetSubjectIncome);

		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("budgetSubjectIncome", budgetSubjectIncome);
		List<BudgetSubjectIncome> list = budgetSubjectIncomeService.getBudgetSubjectIncomeListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getBudgetSubjectIncomeList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBudgetSubjectIncomeList(BudgetSubjectIncome budgetSubjectIncome) {
		List<BudgetSubjectIncome> list = budgetSubjectIncomeService.findAllList(budgetSubjectIncome);
        Map<String,Object> mapOut = new HashMap<String,Object>();
        mapOut.put("url", "getBudgetSubjectIncomeListByCondition|addBudgetSubjectIncomeDo|editBudgetSubjectIncomeDo|delBudgetSubjectIncome|validBudgetSubjectIncomeChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getBudgetSubjectIncomeListByChrId", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBudgetSubjectIncomeListByChrId(BudgetSubjectIncome budgetSubjectIncome) {

		/*
		budgetSubjectIncome = budgetSubjectIncomeService.getBudgetSubjectIncome(budgetSubjectIncome);

		List<BudgetSubjectIncome> list = budgetSubjectIncomeService.getBudgetSubjectIncomeListByChrId(budgetSubjectIncome);
		*/

		List<BudgetSubjectIncome> list = budgetSubjectIncomeService.findAllList(budgetSubjectIncome);
		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("list", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
