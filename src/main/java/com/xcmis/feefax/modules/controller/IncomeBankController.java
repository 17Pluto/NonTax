package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.IncomeBank;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.IncomeBankService;
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
public class IncomeBankController {
	@Autowired
	private IncomeBankService incomeBankService;

	@Autowired
	private RegionService regionService;

	@RequestMapping(value = "/validIncomeBankChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validIncomeBankChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			IncomeBank incomeBank = new IncomeBank();
			incomeBank.setChrCode(chrCode);
			incomeBank = incomeBankService.getIncomeBank(incomeBank);
			if(incomeBank == null){
				return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (incomeBank.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					//前段判断
					return new Result<>(incomeBank.getChrId(), Globals.OP_FAILURE, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("", Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/addIncomeBankDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addIncomeBankDo(IncomeBank incomeBank) {
		try {
			if(incomeBank.getParentId().equals("allTree")){
				incomeBank.setParentId("");
			}
			incomeBank.setSetYear(DateTimeUtils.getCurrentYear());
			incomeBank.setChrCode(incomeBank.getDispCode());
			incomeBank.setCreateDate(DateTimeUtils.getDateTimeStr1());
			incomeBank.setCreateUser(UserUtils.getUserId());
			incomeBank.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			incomeBank.setLatestOpUser(UserUtils.getUserId());
			incomeBank.setLastVer(DateTimeUtils.getDateTimeStr1());
			incomeBank.setRgCode(regionService.get(null).getChrCode());
			incomeBank.setIncomeFlag(1);
			String chrId = incomeBankService.insertReturnId(incomeBank);
			if(!chrId.equals("")){
				return new Result<>(incomeBank.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editIncomeBankDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editIncomeBankDo(IncomeBank incomeBank) {
		try {
			incomeBank.setChrCode(incomeBank.getDispCode());

			boolean b = incomeBankService.update(incomeBank);
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

	@RequestMapping(value = "/delIncomeBank", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delIncomeBank(@RequestParam(value = "chrId") String chrId) {
		try {
			IncomeBank incomeBank = new IncomeBank();
			incomeBank.setChrId(chrId);

			long total = incomeBankService.getIncomeBankListTotal(incomeBank);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}
			boolean b = incomeBankService.delete(incomeBank);
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
	
	
	
	@RequestMapping(value = "/getIncomeBankListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeBankListByCondition(String page, String rows, String sort, String order, IncomeBank incomeBank) {
		if(!incomeBank.getChrCode().equals("allTree")){
			incomeBank.setDispCode(incomeBank.getChrCode());
		}else{
			incomeBank.setChrCode("");
			incomeBank.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = incomeBankService.getIncomeBankListTotal(incomeBank);

		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("incomeBank", incomeBank);
		List<IncomeBank> list = incomeBankService.getIncomeBankListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getIncomeBankList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeBankList(IncomeBank incomeBank) {
		List<IncomeBank> list = incomeBankService.findAllList(incomeBank);
        Map<String,Object> mapOut = new HashMap<String,Object>();
        mapOut.put("url", "getIncomeBankListByCondition|addIncomeBankDo|editIncomeBankDo|delIncomeBank|validIncomeBankChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
