package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.WholeAllot;
import com.xcmis.feefax.modules.service.WholeAllotService;
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
public class WholeAllotController {
	@Autowired
	private WholeAllotService wholeAllotService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validWholeAllotChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validWholeAllotChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			WholeAllot wholeAllot = new WholeAllot();
			wholeAllot.setChrCode(chrCode);
			wholeAllot = wholeAllotService.get(wholeAllot);
			if(wholeAllot == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (wholeAllot.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(wholeAllot.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addWholeAllotDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addWholeAllotDo(WholeAllot wholeAllot) {
		try {
			if(wholeAllot.getParentId().equals("allTree")){
				wholeAllot.setParentId("");
			}
			wholeAllot.setSetYear(DateTimeUtils.getCurrentYear());
			wholeAllot.setChrCode(wholeAllot.getDispCode());
			wholeAllot.setCreateDate(DateTimeUtils.getDateTimeStr1());
			wholeAllot.setCreateUser(UserUtils.getUserId());
			wholeAllot.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			wholeAllot.setLatestOpUser(UserUtils.getUserId());
			wholeAllot.setLastVer(DateTimeUtils.getDateTimeStr1());
			wholeAllot.setRgCode(regionService.get(null).getChrCode());
			String chrId = wholeAllotService.insertReturnId(wholeAllot);
			if(!chrId.equals("")){
				return new Result<>(wholeAllot.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editWholeAllotDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editWholeAllotDo(WholeAllot wholeAllot) {
		try {
			wholeAllot.setChrCode(wholeAllot.getDispCode());

			boolean b = wholeAllotService.update(wholeAllot);
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

	@RequestMapping(value = "/delWholeAllot", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delWholeAllot(@RequestParam(value = "chrId") String chrId) {
		try {
			WholeAllot wholeAllot = new WholeAllot();
			wholeAllot.setChrId(chrId);
			boolean b = wholeAllotService.delete(wholeAllot);
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


	@RequestMapping(value = "/getWholeAllotListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getWholeAllotListByCondition(String page, String rows, String sort, String order, WholeAllot wholeAllot) {
		if(!wholeAllot.getChrCode().equals("allTree")){
			wholeAllot.setDispCode(wholeAllot.getChrCode());
		}else{
			wholeAllot.setChrCode("");
			wholeAllot.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = wholeAllotService.getWholeAllotListTotal(wholeAllot);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("wholeAllot", wholeAllot);
		List<WholeAllot> list = wholeAllotService.getWholeAllotListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getWholeAllotList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getWholeAllotList(WholeAllot wholeAllot) {
		List<WholeAllot> list = wholeAllotService.findAllList(wholeAllot);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getWholeAllotListByCondition|addWholeAllotDo|editWholeAllotDo|delWholeAllot|validWholeAllotChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
