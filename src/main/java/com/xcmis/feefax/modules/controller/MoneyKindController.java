package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.MoneyKind;
import com.xcmis.feefax.modules.service.MoneyKindService;
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
public class MoneyKindController {
	@Autowired
	private MoneyKindService moneyKindService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validMoneyKindChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validMoneyKindChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
            MoneyKind moneyKind = new MoneyKind();
            moneyKind.setChrCode(chrCode);
            moneyKind = moneyKindService.get(moneyKind);
			if(moneyKind == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (moneyKind.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(moneyKind.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addMoneyKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addMoneyKindDo(MoneyKind moneyKind) {
		try {
			if(moneyKind.getParentId().equals("allTree")){
                moneyKind.setParentId("");
			}
            moneyKind.setSetYear(DateTimeUtils.getCurrentYear());
            moneyKind.setChrCode(moneyKind.getDispCode());
            moneyKind.setCreateDate(DateTimeUtils.getDateTimeStr1());
            moneyKind.setCreateUser(UserUtils.getUserId());
            moneyKind.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            moneyKind.setLatestOpUser(UserUtils.getUserId());
            moneyKind.setLastVer(DateTimeUtils.getDateTimeStr1());
            moneyKind.setRgCode(regionService.get(null).getChrCode());
			String chrId = moneyKindService.insertReturnId(moneyKind);
			if(!chrId.equals("")){
				return new Result<>(moneyKind.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editMoneyKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editMoneyKindDo(MoneyKind moneyKind) {
		try {
            moneyKind.setChrCode(moneyKind.getDispCode());

			boolean b = moneyKindService.update(moneyKind);
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

	@RequestMapping(value = "/delMoneyKind", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delMoneyKind(@RequestParam(value = "chrId") String chrId) {
		try {
            MoneyKind moneyKind = new MoneyKind();
            moneyKind.setChrId(chrId);

			long total = moneyKindService.getMoneyKindListTotal(moneyKind);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}

			boolean b = moneyKindService.delete(moneyKind);
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


	@RequestMapping(value = "/getMoneyKindListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getMoneyKindListByCondition(String page, String rows, String sort, String order, MoneyKind moneyKind) {
		if(!moneyKind.getChrCode().equals("allTree")){
            moneyKind.setDispCode(moneyKind.getChrCode());
		}else{
            moneyKind.setChrCode("");
            moneyKind.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = moneyKindService.getMoneyKindListTotal(moneyKind);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("moneyKind", moneyKind);
		List<MoneyKind> list = moneyKindService.getMoneyKindListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getMoneyKindList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getMoneyKindList(MoneyKind moneyKind) {
		List<MoneyKind> list = moneyKindService.findAllList(moneyKind);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getMoneyKindListByCondition|addMoneyKindDo|editMoneyKindDo|delMoneyKind|validMoneyKindChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
