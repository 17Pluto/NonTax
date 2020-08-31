package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.IncomeBankAccount;
import com.xcmis.feefax.modules.entity.IncomeEnterprise;
import com.xcmis.feefax.modules.entity.UnitItem;
import com.xcmis.feefax.modules.service.IncomeEnterpriseService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.IncomeBankAccountService;
import com.xcmis.feefax.modules.service.UnitItemService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class IncomeBankAccountController {
	@Autowired
	private IncomeBankAccountService incomeBankAccountService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private UserService userService;

	@Autowired
	private UnitItemService unitItemService;

	@Value("${issubaccount}")
	private boolean isSubAccount;

	@Autowired
	private IncomeEnterpriseService incomeEnterpriseService;

	@RequestMapping(value = "/validIncomeBankAccountChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validIncomeBankAccountChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			IncomeBankAccount incomeBankAccount = new IncomeBankAccount();
			incomeBankAccount.setChrCode(chrCode);
			incomeBankAccount = incomeBankAccountService.get(incomeBankAccount);
			if(incomeBankAccount == null){
				return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (incomeBankAccount.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					//前段判断
					return new Result<>(incomeBankAccount.getChrId(), Globals.OP_FAILURE, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("", Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/addIncomeBankAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addIncomeBankAccountDo(IncomeBankAccount incomeBankAccount) {
		try {
			if(incomeBankAccount.getParentId().equals("allTree")){
				incomeBankAccount.setParentId("");
			}
			incomeBankAccount.setSetYear(DateTimeUtils.getCurrentYear());
			incomeBankAccount.setChrCode(incomeBankAccount.getDispCode());
			incomeBankAccount.setCreateDate(DateTimeUtils.getDateTimeStr1());
			incomeBankAccount.setCreateUser(UserUtils.getUserId());
			incomeBankAccount.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			incomeBankAccount.setLatestOpUser(UserUtils.getUserId());
			incomeBankAccount.setLastVer(DateTimeUtils.getDateTimeStr1());
			incomeBankAccount.setRgCode(regionService.get(null).getChrCode());
            incomeBankAccount.setAccountType(41);
			String chrId = incomeBankAccountService.insertReturnId(incomeBankAccount);
			if(!chrId.equals("")){
				return new Result<>(incomeBankAccount.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editIncomeBankAccountDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editIncomeBankAccountDo(IncomeBankAccount incomeBankAccount) {
		try {
            incomeBankAccount.setChrCode(incomeBankAccount.getDispCode());

			boolean b = incomeBankAccountService.update(incomeBankAccount);
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

	@RequestMapping(value = "/delIncomeBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delIncomeBankAccount(@RequestParam(value = "chrId") String chrId) {
		try {
            IncomeBankAccount incomeBankAccount = new IncomeBankAccount();
            incomeBankAccount.setChrId(chrId);
			boolean b = incomeBankAccountService.delete(incomeBankAccount);
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
	
	
	
	@RequestMapping(value = "/getIncomeBankAccountListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeBankAccountListByCondition(String page, String rows, String sort, String order, IncomeBankAccount incomeBankAccount) {
		if(!incomeBankAccount.getChrCode().equals("allTree")){
            incomeBankAccount.setDispCode(incomeBankAccount.getChrCode());
		}else{
            incomeBankAccount.setChrCode("");
            incomeBankAccount.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = incomeBankAccountService.getIncomeBankAccountListTotal(incomeBankAccount);

		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("incomeBankAccount", incomeBankAccount);
		List<IncomeBankAccount> list = incomeBankAccountService.getIncomeBankAccountListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getIncomeBankAccountList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeBankAccountList(IncomeBankAccount incomeBankAccount) {
		List<IncomeBankAccount> list = incomeBankAccountService.findAllList(incomeBankAccount);
        Map<String,Object> mapOut = new HashMap<String,Object>();
        mapOut.put("url", "getIncomeBankAccountListByCondition|addIncomeBankAccountDo|editIncomeBankAccountDo|delIncomeBankAccount|validIncomeBankAccountChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

	@RequestMapping(value = "/getIncomeBankSubAccountList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeBankSubAccountList(IncomeBankAccount incomeBankAccount) {
		/**
		 * 获取执收单位
		 */
		List<IncomeBankAccount> list = incomeBankAccountService.findAllList(incomeBankAccount);
		List<IncomeBankAccount> subList = new ArrayList<>();

		Map<String,Object> mapOut = new HashMap<String,Object>();

		Subject subject = SecurityUtils.getSubject();
		String token = (String)subject.getPrincipal();
		String userId = JwtTokenUtil.getUserId(token);

		User user = new User();
		user.setUserId(userId);
		user = userService.get(user);


		if(user.getBelongType().equals("002")) {
			String unitId = user.getBelongOrg();

			UnitItem unitItem = new UnitItem();
			unitItem.setUnitId(unitId);
			List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);

			List<String> enIds = new ArrayList<>();
			enIds.add(unitId);
			for(UnitItem ui : unitItemList){
				enIds.add(ui.getIenId());
			}


			for(IncomeBankAccount iba : list){
				String acct = iba.getAccountNo();
				for(String enId : enIds) {

					IncomeEnterprise ie = new IncomeEnterprise();
					ie.setChrId(enId);
					ie = incomeEnterpriseService.get(ie);
					if(acct.length() == 14){
						iba.setAccountNo(acct + ie.getChrCode());
						//iBankAccount.setAccountNo(acct + ie.getChrCode());
					}else if(acct.length() == 17){
						iba.setAccountNo(acct + "0000" + ie.getChrCode());
					}
					subList.add(iba);
				}
			}
		}

		mapOut.put("list", subList);//要以JSON格式返回
		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

	}

}
