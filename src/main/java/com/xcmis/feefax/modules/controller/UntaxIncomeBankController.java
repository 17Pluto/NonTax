package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.UntaxIncomeBank;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.UntaxIncomeBankService;
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
public class UntaxIncomeBankController {
	@Autowired
	private UntaxIncomeBankService untaxIncomeBankService;

	@Autowired
	private RegionService regionService;

	@RequestMapping(value = "/validUntaxIncomeBankChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validUntaxIncomeBankChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			UntaxIncomeBank untaxIncomeBank = new UntaxIncomeBank();
			untaxIncomeBank.setChrCode(chrCode);
			untaxIncomeBank = untaxIncomeBankService.getUntaxIncomeBank(untaxIncomeBank);
			if(untaxIncomeBank == null){
				return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (untaxIncomeBank.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					//前段判断
					return new Result<>(untaxIncomeBank.getChrId(), Globals.OP_FAILURE, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("", Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/addUntaxIncomeBankDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addUntaxIncomeBankDo(UntaxIncomeBank untaxIncomeBank) {
		try {
			if(untaxIncomeBank.getParentId().equals("allTree")){
				untaxIncomeBank.setParentId("");
			}
			untaxIncomeBank.setSetYear(DateTimeUtils.getCurrentYear());
			untaxIncomeBank.setChrCode(untaxIncomeBank.getDispCode());
			untaxIncomeBank.setCreateDate(DateTimeUtils.getDateTimeStr1());
			untaxIncomeBank.setCreateUser(UserUtils.getUserId());
			untaxIncomeBank.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			untaxIncomeBank.setLatestOpUser(UserUtils.getUserId());
			untaxIncomeBank.setLastVer(DateTimeUtils.getDateTimeStr1());
			untaxIncomeBank.setRgCode(regionService.get(null).getChrCode());
			untaxIncomeBank.setIncomeFlag(1);
			String chrId = untaxIncomeBankService.insertReturnId(untaxIncomeBank);
			if(!chrId.equals("")){
				return new Result<>(untaxIncomeBank.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editUntaxIncomeBankDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editUntaxIncomeBankDo(UntaxIncomeBank untaxIncomeBank) {
		try {
			untaxIncomeBank.setChrCode(untaxIncomeBank.getDispCode());

			boolean b = untaxIncomeBankService.update(untaxIncomeBank);
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

	@RequestMapping(value = "/delUntaxIncomeBank", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delUntaxIncomeBank(@RequestParam(value = "chrId") String chrId) {
		try {
			UntaxIncomeBank untaxIncomeBank = new UntaxIncomeBank();
			untaxIncomeBank.setChrId(chrId);

			long total = untaxIncomeBankService.getUntaxIncomeBankListTotal(untaxIncomeBank);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}

			boolean b = untaxIncomeBankService.delete(untaxIncomeBank);
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
	
	
	
	@RequestMapping(value = "/getUntaxIncomeBankListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getUntaxIncomeBankListByCondition(String page, String rows, String sort, String order, UntaxIncomeBank untaxIncomeBank) {
		if(!untaxIncomeBank.getChrCode().equals("allTree")){
			untaxIncomeBank.setDispCode(untaxIncomeBank.getChrCode());
		}else{
			untaxIncomeBank.setChrCode("");
			untaxIncomeBank.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = untaxIncomeBankService.getUntaxIncomeBankListTotal(untaxIncomeBank);

		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("untaxIncomeBank", untaxIncomeBank);
		List<UntaxIncomeBank> list = untaxIncomeBankService.getUntaxIncomeBankListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getUntaxIncomeBankList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getUntaxIncomeBankList(UntaxIncomeBank untaxIncomeBank) {
		List<UntaxIncomeBank> list = untaxIncomeBankService.findAllList(untaxIncomeBank);
        Map<String,Object> mapOut = new HashMap<String,Object>();
        mapOut.put("url", "getUntaxIncomeBankListByCondition|addUntaxIncomeBankDo|editUntaxIncomeBankDo|delUntaxIncomeBank|validUntaxIncomeBankChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
