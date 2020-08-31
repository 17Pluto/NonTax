package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.ReceiverAccount;
import com.xcmis.feefax.modules.service.ReceiverAccountService;
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
public class ReceiverAccountController {
	@Autowired
	private ReceiverAccountService receiverAccountService;

	@Autowired
	private RegionService regionService;

	@RequestMapping(value = "/validReceiverAccountChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validReceiverAccountChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			ReceiverAccount receiverAccount = new ReceiverAccount();
			receiverAccount.setChrCode(chrCode);
			receiverAccount = receiverAccountService.get(receiverAccount);
			if(receiverAccount == null){
				return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (receiverAccount.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					//前段判断
					return new Result<>(receiverAccount.getChrId(), Globals.OP_FAILURE, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("", Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/addReceiverAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addReceiverAccountDo(ReceiverAccount receiverAccount) {
		try {
			if(receiverAccount.getParentId().equals("allTree")){
				receiverAccount.setParentId("");
			}
			receiverAccount.setSetYear(DateTimeUtils.getCurrentYear());
			receiverAccount.setChrCode(receiverAccount.getDispCode());
			receiverAccount.setCreateDate(DateTimeUtils.getDateTimeStr1());
			receiverAccount.setCreateUser(UserUtils.getUserId());
			receiverAccount.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			receiverAccount.setLatestOpUser(UserUtils.getUserId());
			receiverAccount.setLastVer(DateTimeUtils.getDateTimeStr1());
			receiverAccount.setRgCode(regionService.get(null).getChrCode());
			receiverAccount.setAccountType(41);
			String chrId = receiverAccountService.insertReturnId(receiverAccount);
			if(!chrId.equals("")){
				return new Result<>(receiverAccount.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editReceiverAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editReceiverAccountDo(ReceiverAccount receiverAccount) {
		try {
			receiverAccount.setChrCode(receiverAccount.getDispCode());

			boolean b = receiverAccountService.update(receiverAccount);
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

	@RequestMapping(value = "/delReceiverAccount", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delReceiverAccount(@RequestParam(value = "chrId") String chrId) {
		try {
			ReceiverAccount receiverAccount = new ReceiverAccount();
			receiverAccount.setChrId(chrId);
			boolean b = receiverAccountService.delete(receiverAccount);
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
	
	
	
	@RequestMapping(value = "/getReceiverAccountListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getReceiverAccountListByCondition(String page, String rows, String sort, String order, ReceiverAccount receiverAccount) {
		if(!receiverAccount.getChrCode().equals("allTree")){
			receiverAccount.setDispCode(receiverAccount.getChrCode());
		}else{
			receiverAccount.setChrCode("");
			receiverAccount.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = receiverAccountService.getReceiverAccountListTotal(receiverAccount);

		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("receiverAccount", receiverAccount);
		List<ReceiverAccount> list = receiverAccountService.getReceiverAccountListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getReceiverAccountList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getReceiverAccountList(ReceiverAccount receiverAccount) {
		List<ReceiverAccount> list = receiverAccountService.findAllList(receiverAccount);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getReceiverAccountListByCondition|addReceiverAccountDo|editReceiverAccountDo|delReceiverAccount|validReceiverAccountChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
