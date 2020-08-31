package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.PayerAccount;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.PayerAccountService;
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
public class PayerAccountController {
	@Autowired
	private PayerAccountService payerAccountService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validPayerAccountChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validPayerAccountChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			PayerAccount payerAccount = new PayerAccount();
			payerAccount.setChrCode(chrCode);
			payerAccount = payerAccountService.get(payerAccount);
			if(payerAccount == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (payerAccount.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(payerAccount.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addPayerAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addPayerAccountDo(PayerAccount payerAccount) {
		try {
			if(payerAccount.getParentId().equals("allTree")){
				payerAccount.setParentId("");
			}
			payerAccount.setSetYear(DateTimeUtils.getCurrentYear());
			payerAccount.setChrCode(payerAccount.getDispCode());
			payerAccount.setCreateDate(DateTimeUtils.getDateTimeStr1());
			payerAccount.setCreateUser(UserUtils.getUserId());
			payerAccount.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			payerAccount.setLatestOpUser(UserUtils.getUserId());
			payerAccount.setLastVer(DateTimeUtils.getDateTimeStr1());
			payerAccount.setRgCode(regionService.get(null).getChrCode());
			String chrId = payerAccountService.insertReturnId(payerAccount);
			if(!chrId.equals("")){
				return new Result<>(payerAccount.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editPayerAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editPayerAccountDo(PayerAccount payerAccount) {
		try {
			payerAccount.setChrCode(payerAccount.getDispCode());

			boolean b = payerAccountService.update(payerAccount);
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

	@RequestMapping(value = "/delPayerAccount", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delPayerAccount(@RequestParam(value = "chrId") String chrId) {
		try {
			PayerAccount payerAccount = new PayerAccount();
			payerAccount.setChrId(chrId);
			boolean b = payerAccountService.delete(payerAccount);
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


	@RequestMapping(value = "/getPayerAccountListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getPayerAccountListByCondition(String page, String rows, String sort, String order, PayerAccount payerAccount) {
		if(!payerAccount.getChrCode().equals("allTree")){
			payerAccount.setDispCode(payerAccount.getChrCode());
		}else{
			payerAccount.setChrCode("");
			payerAccount.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = payerAccountService.getPayerAccountListTotal(payerAccount);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("payerAccount", payerAccount);
		List<PayerAccount> list = payerAccountService.getPayerAccountListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getPayerAccountList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getPayerAccountList(PayerAccount payerAccount) {
		List<PayerAccount> list = payerAccountService.findAllList(payerAccount);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getPayerAccountListByCondition|addPayerAccountDo|editPayerAccountDo|delPayerAccount|validPayerAccountChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
