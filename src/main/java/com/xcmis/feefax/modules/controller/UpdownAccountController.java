package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.UpdownAccount;
import com.xcmis.feefax.modules.service.UpdownAccountService;
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
public class UpdownAccountController {
	@Autowired
	private UpdownAccountService updownAccountService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validUpdownAccountChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validUpdownAccountChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			UpdownAccount updownAccount = new UpdownAccount();
			updownAccount.setChrCode(chrCode);
			updownAccount = updownAccountService.get(updownAccount);
			if(updownAccount == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (updownAccount.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(updownAccount.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addUpdownAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addUpdownAccountDo(UpdownAccount updownAccount) {
		try {
			if(updownAccount.getParentId().equals("allTree")){
				updownAccount.setParentId("");
			}
			updownAccount.setSetYear(DateTimeUtils.getCurrentYear());
			updownAccount.setChrCode(updownAccount.getDispCode());
			updownAccount.setCreateDate(DateTimeUtils.getDateTimeStr1());
			updownAccount.setCreateUser(UserUtils.getUserId());
			updownAccount.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			updownAccount.setLatestOpUser(UserUtils.getUserId());
			updownAccount.setLastVer(DateTimeUtils.getDateTimeStr1());
			updownAccount.setRgCode(regionService.get(null).getChrCode());
			String chrId = updownAccountService.insertReturnId(updownAccount);
			if(!chrId.equals("")){
				return new Result<>(updownAccount.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editUpdownAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editUpdownAccountDo(UpdownAccount updownAccount) {
		try {
			updownAccount.setChrCode(updownAccount.getDispCode());

			boolean b = updownAccountService.update(updownAccount);
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

	@RequestMapping(value = "/delUpdownAccount", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delUpdownAccount(@RequestParam(value = "chrId") String chrId) {
		try {
			UpdownAccount updownAccount = new UpdownAccount();
			updownAccount.setChrId(chrId);
			boolean b = updownAccountService.delete(updownAccount);
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


	@RequestMapping(value = "/getUpdownAccountListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getUpdownAccountListByCondition(String page, String rows, String sort, String order, UpdownAccount updownAccount) {
		if(!updownAccount.getChrCode().equals("allTree")){
			updownAccount.setDispCode(updownAccount.getChrCode());
		}else{
			updownAccount.setChrCode("");
			updownAccount.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = updownAccountService.getUpdownAccountListTotal(updownAccount);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("updownAccount", updownAccount);
		List<UpdownAccount> list = updownAccountService.getUpdownAccountListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getUpdownAccountList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getUpdownAccountList(UpdownAccount updownAccount) {
		List<UpdownAccount> list = updownAccountService.findAllList(updownAccount);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getUpdownAccountListByCondition|addUpdownAccountDo|editUpdownAccountDo|delUpdownAccount|validUpdownAccountChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
