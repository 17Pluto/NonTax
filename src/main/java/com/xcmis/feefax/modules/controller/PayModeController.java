package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.PayMode;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.PayModeService;
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
public class PayModeController {
	@Autowired
	private PayModeService payModeService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validPayModeChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validPayModeChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			PayMode payMode = new PayMode();
			payMode.setChrCode(chrCode);
			payMode = payModeService.get(payMode);
			if(payMode == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (payMode.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(payMode.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addPayModeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addPayModeDo(PayMode payMode) {
		try {
			if(payMode.getParentId().equals("allTree")){
				payMode.setParentId("");
			}
			payMode.setSetYear(DateTimeUtils.getCurrentYear());
			payMode.setChrCode(payMode.getDispCode());
			payMode.setCreateDate(DateTimeUtils.getDateTimeStr1());
			payMode.setCreateUser(UserUtils.getUserId());
			payMode.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			payMode.setLatestOpUser(UserUtils.getUserId());
			payMode.setLastVer(DateTimeUtils.getDateTimeStr1());
			payMode.setRgCode(regionService.get(null).getChrCode());
			String chrId = payModeService.insertReturnId(payMode);
			if(!chrId.equals("")){
				return new Result<>(payMode.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editPayModeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editPayModeDo(PayMode payMode) {
		try {
			payMode.setChrCode(payMode.getDispCode());

			boolean b = payModeService.update(payMode);
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

	@RequestMapping(value = "/delPayMode", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delPayMode(@RequestParam(value = "chrId") String chrId) {
		try {
			PayMode payMode = new PayMode();
			payMode.setChrId(chrId);

			long total = payModeService.getPayModeListTotal(payMode);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}

			boolean b = payModeService.delete(payMode);
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


	@RequestMapping(value = "/getPayModeListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getPayModeListByCondition(String page, String rows, String sort, String order, PayMode payMode) {
		if(!payMode.getChrCode().equals("allTree")){
			payMode.setDispCode(payMode.getChrCode());
		}else{
			payMode.setChrCode("");
			payMode.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = payModeService.getPayModeListTotal(payMode);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("payMode", payMode);
		List<PayMode> list = payModeService.getPayModeListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getPayModeList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getPayModeList(PayMode payMode) {
		List<PayMode> list = payModeService.findAllList(payMode);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getPayModeListByCondition|addPayModeDo|editPayModeDo|delPayMode|validPayModeChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
