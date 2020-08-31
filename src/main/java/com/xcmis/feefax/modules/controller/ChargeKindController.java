package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.ChargeKind;
import com.xcmis.feefax.modules.service.ChargeKindService;
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
public class ChargeKindController {
	@Autowired
	private ChargeKindService chargeKindService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validChargeKindChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validChargeKindChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			ChargeKind chargeKind = new ChargeKind();
			chargeKind.setChrCode(chrCode);
			chargeKind = chargeKindService.get(chargeKind);
			if(chargeKind == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (chargeKind.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(chargeKind.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addChargeKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addChargeKindDo(ChargeKind chargeKind) {
		try {
			if(chargeKind.getParentId().equals("allTree")){
				chargeKind.setParentId("");
			}
			chargeKind.setSetYear(DateTimeUtils.getCurrentYear());
			chargeKind.setChrCode(chargeKind.getDispCode());
			chargeKind.setCreateDate(DateTimeUtils.getDateTimeStr1());
			chargeKind.setCreateUser(UserUtils.getUserId());
			chargeKind.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			chargeKind.setLatestOpUser(UserUtils.getUserId());
			chargeKind.setLastVer(DateTimeUtils.getDateTimeStr1());
			chargeKind.setRgCode(regionService.get(null).getChrCode());
			String chrId = chargeKindService.insertReturnId(chargeKind);
			if(!chrId.equals("")){
				return new Result<>(chargeKind.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editChargeKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editChargeKindDo(ChargeKind chargeKind) {
		try {
			chargeKind.setChrCode(chargeKind.getDispCode());

			boolean b = chargeKindService.update(chargeKind);
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

	@RequestMapping(value = "/delChargeKind", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delChargeKind(@RequestParam(value = "chrId") String chrId) {
		try {
			ChargeKind chargeKind = new ChargeKind();
			chargeKind.setChrId(chrId);

			long total = chargeKindService.getChargeKindListTotal(chargeKind);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}
			boolean b = chargeKindService.delete(chargeKind);
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


	@RequestMapping(value = "/getChargeKindListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getChargeKindListByCondition(String page, String rows, String sort, String order, ChargeKind chargeKind) {
		if(!chargeKind.getChrCode().equals("allTree")){
			chargeKind.setDispCode(chargeKind.getChrCode());
		}else{
			chargeKind.setChrCode("");
			chargeKind.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = chargeKindService.getChargeKindListTotal(chargeKind);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("chargeKind", chargeKind);
		List<ChargeKind> list = chargeKindService.getChargeKindListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getChargeKindList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getChargeKindList(ChargeKind chargeKind) {
		List<ChargeKind> list = chargeKindService.findAllList(chargeKind);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getChargeKindListByCondition|addChargeKindDo|editChargeKindDo|delChargeKind|validChargeKindChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
